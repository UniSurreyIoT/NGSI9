/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import eu.fiware.iot.ngsi.*;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.DiscoveryMarshaller;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o.RegisterStoreAccess;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 */
public class Resource05_Type extends ServerResource {

    @Get
    public Representation getDescription() throws JAXBException {

        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        String eType = (String) getRequest().getAttributes().get("typeName");
        StatusCode sc = new StatusCode(200, "OK", "result");
        
        //retrieve using entity id
        DiscoveryContextAvailabilityResponse discContResp = new DiscoveryContextAvailabilityResponse();
        try {
            RegisterStoreAccess regStore = new RegisterStoreAccess(context);
            regStore.openDb4o();
            List<RegisterContextRequest> result = regStore.getRegByEntityType(eType);
            ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();
            crrl = regStore.getContRegHasEntityType(result, eType);
            regStore.closeDb4o();
            discContResp.setContextRegistrationResponseList(crrl);
            discContResp = regStore.removeSharedEntityType(discContResp, eType);
        } catch (Exception e) {
            sc = new StatusCode(500,"Internal Error","result");            
        }
        discContResp.setErrorCode(sc);
        String discRespMsg = "";
        DiscoveryMarshaller dcam = new DiscoveryMarshaller();
        discRespMsg = dcam.marshallResponse(discContResp);
        System.out.println("Response To Send: \n" + discRespMsg);
        StringRepresentation result = new StringRepresentation(discRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;

    }
}
