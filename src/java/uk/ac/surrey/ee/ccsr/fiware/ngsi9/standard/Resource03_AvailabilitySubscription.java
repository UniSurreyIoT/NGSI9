/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard;

import eu.fiware.iot.ngsi.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.SubscribeMarshaller;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o.SubscriptionStoreAccess;

/**
 * Resource which has only one representation.
 */
public class Resource03_AvailabilitySubscription extends ServerResource {

    @Post
    public Representation subscribeToDescription(Representation entity) throws ResourceException, IOException, JAXBException {

        InputStream description = new ByteArrayInputStream(entity.getText().getBytes());
        String respMsg = subscribeToContext(description);

        System.out.println(respMsg);
        StringRepresentation result = new StringRepresentation(respMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
    }

    public String subscribeToContext(InputStream description) throws ResourceException, IOException, JAXBException {

        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");

        StatusCode sc = new StatusCode(200, "OK", "Stored");

        SubscribeMarshaller subMar = new SubscribeMarshaller();
        SubscribeContextAvailabilityRequest req = new SubscribeContextAvailabilityRequest();
        SubscribeContextAvailabilityResponse subResp = new SubscribeContextAvailabilityResponse();

        //unmarshall XML request
        try {
            req = subMar.unmarshallRequest(description);
            System.out.println("Receievd XML Request: \n" + subMar.marshallRequest(req));
        } catch (JAXBException je) {
            //Error with XML structure
            Logger.getLogger(Resource03_AvailabilitySubscription.class.getName()).log(Level.SEVERE, null, je);
            sc = new StatusCode(400, "Bad Request", "Error in XML structure");
            subResp.setErrorCode(sc);
            subResp.setSubscriptionId("");
            String respMsg = null;
            try {
                respMsg = subMar.marshallResponse(subResp);
            } catch (JAXBException ex2) {
                Logger.getLogger(Resource03_AvailabilitySubscription.class.getName()).log(Level.SEVERE, null, ex2);
            }
            System.out.println("Respose To Send: \n" + respMsg);
            return respMsg;
        }

        //if subs ID is provided then attempt update -> delete, then register
        boolean doUpdate = false;
        String subId = req.getSubscriptionId();
        if (subId.startsWith("UniS_")) {
            SubscriptionStoreAccess ss = new SubscriptionStoreAccess(context);
            ss.openDb4o();
            boolean deleted = ss.deleteSubscription(subId);
            ss.closeDb4o();
            if (deleted) {
                doUpdate = true;
            }
        }

        SubscriptionStoreAccess regStore = new SubscriptionStoreAccess(context);
        //store request
        try {
            if (!doUpdate) {
                //create new registration ID
                req.setSubscriptionId("UniS_" + RandomStringUtils.randomAlphanumeric(10));
            }
            regStore.storeSubscription(req);
            subResp.setDuration(req.getDuration());
        } catch (Exception e) {
            e.printStackTrace();
            sc = new StatusCode(500, "Internal Error", "result");
            req.setSubscriptionId("");
        }

        subResp.setErrorCode(sc);
        subResp.setSubscriptionId(req.getSubscriptionId());
        String regRespMsg = null;
        regRespMsg = subMar.marshallResponse(subResp);

        return regRespMsg;

    }

}
