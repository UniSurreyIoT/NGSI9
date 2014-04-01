/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard;

import eu.fiware.iot.ngsi.*;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.RegisterMarshaller;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o.RegisterStoreAccess;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.RandomStringUtils;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.notify.AvailabilityNotifier;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o.SubscriptionStoreAccess;


/**
 * Resource which has only one representation.
 */
public class Resource01_ContextRegistration extends ServerResource {

    @Post
    public Representation registerDescription(Representation entity) throws ResourceException, IOException, JAXBException {

        //read NGSI description
        InputStream description = new ByteArrayInputStream(entity.getText().getBytes());
        
        //get servlet context
        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");

        //register description
        String regRespMsg = registerContext(context, description);
        System.out.println("Respose To Send: \n" + regRespMsg);
        StringRepresentation result = new StringRepresentation(regRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
    }

    public String registerContext(ServletContext context, InputStream description) throws ResourceException, IOException, JAXBException {

        //set status code to default
        StatusCode sc = new StatusCode(200, "OK", "Stored");

        //instantiate registration marshaller/unmarshaller, request, and response
        RegisterMarshaller regMar = new RegisterMarshaller();
        RegisterContextRequest req = new RegisterContextRequest();
        RegisterContextResponse regResp = new RegisterContextResponse();

        //unmarshall XML request
        try {
            req = regMar.unmarshallRequest(description);
            System.out.println("Receievd XML Request: \n" + regMar.marshallRequest(req));
        } catch (JAXBException je) {
            //je.printStackTrace();
            System.out.println(je.getLinkedException().getLocalizedMessage());
            //Error with XML structure, return message
            Logger.getLogger(Resource01_ContextRegistration.class.getName()).log(Level.SEVERE, null, je);
            sc = new StatusCode(400, "Bad Request", "Error in XML structure");
            regResp.setErrorCode(sc);
            String regRespMsg = "";
            try {
                regRespMsg = regMar.marshallResponse(regResp);
            } catch (JAXBException ex2) {
                Logger.getLogger(Resource01_ContextRegistration.class.getName()).log(Level.SEVERE, null, ex2);
            }            
            return regRespMsg;
        }

        //instantiate registration storage handler
        RegisterStoreAccess regStore = new RegisterStoreAccess(context);

        //check if update is required
        boolean doUpdate = false;
        String regId = req.getRegistrationId();
        //does description have a registration ID that has the prefix "UniS_"?
        try {
            if (regId.startsWith("UniS_")) {
                //attempt to delete stored registration with this ID.
                boolean deleted = regStore.deleteRegistration(req);
                if (deleted) {
                    //perform update
                    doUpdate = true;
                } else {
                    // no registration with this ID found
                    sc = new StatusCode(404, "Resource not Found", "No Context Registration with ID: " + regId);
                    regResp.setErrorCode(sc);
                    String regRespMsg = null;
                    try {
                        regRespMsg = regMar.marshallResponse(regResp);
                    } catch (JAXBException ex2) {
                        Logger.getLogger(Resource01_ContextRegistration.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                    System.out.println("Respose To Send: \n" + regRespMsg);
                    return regRespMsg;
                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            System.out.println("Registration ID element missing");
        }
        // store/update registration
        try {
            if (!doUpdate) {
                // create new registration ID
                boolean regIdUsed = false;
                do {
                    //check if generated ID is already used
                    String idGenerated = "UniS_" + RandomStringUtils.randomAlphanumeric(10);
                    req.setRegistrationId(idGenerated);
                    regIdUsed = regStore.checkRegIdUsed(idGenerated);
                } while (regIdUsed);
            }
            //store registration
            regStore.storeRegistration(req);
            regResp.setDuration(req.getDuration());
        } catch (Exception e) {
            //internal error with storage
            e.printStackTrace();
            sc = new StatusCode(500, "Internal Error", "result");
            req.setRegistrationId("");
        }

        regResp.setErrorCode(sc);
        //set registration ID
        regResp.setRegistrationId(req.getRegistrationId());
        String regRespMsg = null;
        //marshal response message
        regRespMsg = regMar.marshallResponse(regResp);
        
        

        //notify Subscribers
        SubscriptionStoreAccess subStore = new SubscriptionStoreAccess(context);
        try {
            List<SubscribeContextAvailabilityRequest> subsMatched = subStore.matchSubscriptions(req);
            Thread notifySubs = new Thread(new AvailabilityNotifier(req, subsMatched));
            notifySubs.start();
            subStore.closeDb4o();
        } catch (NullPointerException npe) {
            System.out.println("Matched Subscriptions: "+ npe.getMessage());
        }
        return regRespMsg;

    }
}
