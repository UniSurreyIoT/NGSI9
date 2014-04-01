/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard;

import eu.fiware.iot.ngsi.*;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.DiscoveryMarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 */
public class Resource02_Discovery extends ServerResource {

    @Post
    public Representation discoverDescription(Representation entity) throws ResourceException, IOException {

        //get NGSI discovery request
        String description = entity.getText();
        InputStream discReqIs = new ByteArrayInputStream(description.getBytes());

        String discRespMsg = "";

        //unmarshall request
        DiscoveryMarshaller discMar = new DiscoveryMarshaller();
        DiscoveryContextAvailabilityRequest discReq = new DiscoveryContextAvailabilityRequest();

        try {
            discReq = discMar.unmarshallRequest(discReqIs);
            System.out.println("Received Request: \n" + discMar.marshallRequest(discReq));

            //get query parameters
            //entityId        
            ArrayList<EntityId> discEIdList = new ArrayList<EntityId>();
            int discEntityIdListSize = discReq.getEntityIdList().getEntityId().size();
            for (int i = 0; i < discEntityIdListSize; i++) {
                EntityId eId = discReq.getEntityIdList().getEntityId().get(i);
                discEIdList.add(eId);
            }

            //attributes
            ArrayList<String> discAttrList = new ArrayList<String>();
            int discAttrListSize = discReq.getAttributeList().getAttribute().size();
            for (int i = 0; i < discAttrListSize; i++) {
                String discAttrId = discReq.getAttributeList().getAttribute().get(i);
                discAttrList.add(discAttrId);
            }

            //operation scopes
            ArrayList<OperationScope> opScopeList = new ArrayList<OperationScope>();
            int opScopeListSize = discReq.getRestriction().getScope().getOperationScope().size();
            for (int i = 0; i < opScopeListSize; i++) {
                OperationScope opScope = discReq.getRestriction().getScope().getOperationScope().get(i);
                opScopeList.add(opScope);
            }

            //get context path
            ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");

            //process discovery request
            discRespMsg = discoverContext(context, discEIdList, discAttrList, opScopeList);

        } catch (JAXBException ex) {
            //request structure invalid
            Logger.getLogger(Resource02_Discovery.class.getName()).log(Level.SEVERE, null, ex);
            DiscoveryContextAvailabilityResponse discContResp = new DiscoveryContextAvailabilityResponse();
            StatusCode sc = new StatusCode(400, "Bad Request", "Error in XML structure");
            discContResp.setErrorCode(sc);
            discRespMsg = "";
            try {
                discRespMsg = discMar.marshallResponse(discContResp);
            } catch (JAXBException ex2) {
                Logger.getLogger(Resource02_Discovery.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }

        System.out.println("Response To Send: \n" + discRespMsg);
        StringRepresentation result = new StringRepresentation(discRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;

    }

    public String discoverContext(ServletContext context, ArrayList<EntityId> discEIdList, ArrayList<String> discAttrList, ArrayList<OperationScope> opScopeList) {

        //instatiate response message
        DiscoveryContextAvailabilityResponse discContResp = new DiscoveryContextAvailabilityResponse();
        DiscoveryMarshaller discMar = new DiscoveryMarshaller();

        //set status code to default
        StatusCode sc = new StatusCode(200, "OK", "Result");

        //instantiate registration storage handler
        RegisterStoreAccess regStore = new RegisterStoreAccess(context);

        try {
            //create context registration response and response list
            ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();
            ContextRegistrationResponse crr = new ContextRegistrationResponse();

            for (int i = 0; i < discEIdList.size(); i++) {

                List<RegisterContextRequest> result;

                if (opScopeList.size() > 0) {
                    //check if discovery request is for associations
                    if (opScopeList.get(0).getScopeType().equalsIgnoreCase("IncludeAssociations")) {

                        //retrieve using entity ID, attribute, and association
                        String scopeValue = opScopeList.get(0).getScopeValue();
                        //open access to store
                        regStore.openDb4o();
                        result = regStore.getAssociations(discEIdList.get(i), scopeValue, discAttrList);
                        EntityId eId = new EntityId();

                        for (int z = 0; z < result.size(); z++) {
                            int regMetaSize = result.get(z).getContextRegistrationList().getContextRegistration().size();
                            for (int rm = 0; rm < regMetaSize; rm++) {
                                int contMetaSize = result.get(z).getContextRegistrationList().getContextRegistration().size();
                                for (int cm = 0; cm < contMetaSize; cm++) {
                                    if (scopeValue.equalsIgnoreCase("SOURCES")) {
                                        //if field specifies "SOURCES", then get the sourceEntityId
                                        eId = result.get(z).getContextRegistrationList().getContextRegistration().get(rm)
                                                .getRegistrationMetadata().getContextMetadata().get(cm).getValue().getSourceEntityId();
                                        System.out.println("source id is: " + eId.getId());
                                    } else if (scopeValue.equalsIgnoreCase("TARGETS")) {
                                        //if field specifies "TARGETS", then get the targetEntityId
                                        eId = result.get(z).getContextRegistrationList().getContextRegistration().get(rm)
                                                .getRegistrationMetadata().getContextMetadata().get(cm).getValue().getTargetEntityId();
                                    }
                                    crr.setContextRegistration(result.get(z).getContextRegistrationList().getContextRegistration().get(0));
                                    crrl.getContextRegistrationResponse().add(crr);
                                    List<RegisterContextRequest> result2 = regStore.getRegByEntityID(eId);
                                    crr = regStore.getContRegHasEntityId(result2, eId.getId());
                                    crrl.getContextRegistrationResponse().add(crr);
                                }
                            }
                        }
                        //close access to store
                        regStore.closeDb4o();
                        discContResp.setContextRegistrationResponseList(crrl);
                    } else {
                        //discovery request is for context provider 

                        //open access to store
                        regStore.openDb4o();
                        //retreive using IDs
                        result = regStore.getRegByEntityID(discEIdList.get(i));
                        //retrieve using attributes
                        crr = regStore.getContRegRespContainsEIdAttribute(result, discEIdList.get(i), discAttrList);
                        //close access to store
                        regStore.closeDb4o();
                        //add retrieve response to response list
                        try {
                            crr.getContextRegistration().getEntityIdList();
                            crrl.getContextRegistrationResponse().add(crr);
                            discContResp.setContextRegistrationResponseList(crrl);
                            discContResp = regStore.removeSharedEntityID(discContResp, discEIdList);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc = new StatusCode(500, "Internal Error", "result");
            discContResp.setErrorCode(sc);
            String discRespMsg = null;
            try {
                discRespMsg = discMar.marshallResponse(discContResp);
            } catch (JAXBException ex2) {
                Logger.getLogger(Resource02_Discovery.class.getName()).log(Level.SEVERE, null, ex2);
            }
            regStore.closeDb4o();
            
            return discRespMsg;
        }
        
        //set 404 if no entity is found
        try {
            int contRegRespSize = discContResp.getContextRegistrationResponseList().getContextRegistrationResponse().size();
        } catch (NullPointerException npe) {
            sc = new StatusCode(404, "Context Element Not Found", "result");
        }
        discContResp.setErrorCode(sc);
        String discRespMsg = "";
        DiscoveryMarshaller dcam = new DiscoveryMarshaller();
        //marshall response
        try {
            discRespMsg = dcam.marshallResponse(discContResp);
        } catch (JAXBException ex) {
            Logger.getLogger(Resource02_Discovery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return discRespMsg;
    }
}
