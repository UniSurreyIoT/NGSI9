/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import eu.fiware.iot.ngsi.*;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.DiscoveryMarshaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Resource04_IndividualAttributeDomain extends ServerResource {

    @Get
    public Representation getDescription() throws JAXBException {

        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");

        EntityId eId = new EntityId();
        String eIdString = (String) getRequest().getAttributes().get("EntityID");
        eId.setId(eIdString);
        ArrayList<EntityId> discEidList = new ArrayList<EntityId>();
        discEidList.add(eId);
        String attrDomain = (String) getRequest().getAttributes().get("attributeDomainName");

        StatusCode sc = new StatusCode();
        sc.setCode(200);
        sc.setReasonPhrase("OK");
        sc.setDetails("result");

        //RETRIEVE USING ATTRIBUTE DOMAIN
        DiscoveryMarshaller dcam = new DiscoveryMarshaller();
        DiscoveryContextAvailabilityResponse discContResp = new DiscoveryContextAvailabilityResponse();
        try {
            RegisterStoreAccess regStore = new RegisterStoreAccess(context);
            regStore.openDb4o();
            List<RegisterContextRequest> result = regStore.getRegByEntityID(eId);
            ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();
            crrl = regStore.getContRegHasEntityIdAttrDomain(result, eIdString, attrDomain);
            regStore.closeDb4o();
            discContResp.setContextRegistrationResponseList(crrl);
            discContResp = regStore.removeSharedAttrDomain(discContResp, attrDomain);
            discContResp.setErrorCode(sc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc = new StatusCode(500, "Internal Error", "result");
            discContResp.setErrorCode(sc);
        }

        String discRespMsg = "";

        discRespMsg = dcam.marshallResponse(discContResp);
        System.out.println("Response To Send: \n" + discRespMsg);
        StringRepresentation result = new StringRepresentation(discRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;

    }
}
