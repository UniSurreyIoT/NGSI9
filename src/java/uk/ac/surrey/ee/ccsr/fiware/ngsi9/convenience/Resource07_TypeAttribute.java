/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import eu.fiware.iot.ngsi.*;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.DiscoveryMarshaller;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o.RegisterStoreAccess;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 */
public class Resource07_TypeAttribute extends ServerResource {

    @Get
    public Representation getDescription() {

        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");

        String eType = (String) getRequest().getAttributes().get("typeName");
        String attribute = (String) getRequest().getAttributes().get("attributeName");

        StatusCode sc = new StatusCode();
        sc.setCode(200);
        sc.setReasonPhrase("OK");
        sc.setDetails("result");

        DiscoveryContextAvailabilityResponse discContResp = new DiscoveryContextAvailabilityResponse();

        try {
            RegisterStoreAccess regStore = new RegisterStoreAccess(context);
            regStore.openDb4o();
            List<RegisterContextRequest> result = regStore.getRegByEntityType(eType);
            ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();
            crrl = regStore.getContRegHasEntityTypeAttr(result, eType, attribute);
            regStore.closeDb4o();

            discContResp.setContextRegistrationResponseList(crrl);
            discContResp = regStore.removeSharedEntityType(discContResp, eType);
        } catch (Exception e) {
            sc = new StatusCode();
            sc.setCode(500);
            sc.setReasonPhrase("Internal Error");
            sc.setDetails("result");
        }

        String discRespMsg = null;

        DiscoveryMarshaller dcam = new DiscoveryMarshaller();
        try {
            discRespMsg = dcam.marshallResponse(discContResp);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println("final: \n" + discRespMsg);
        StringRepresentation result = new StringRepresentation(discRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
        //return "GET Resource for " + this.toString() + " not yet implemented";
    }
    
}
