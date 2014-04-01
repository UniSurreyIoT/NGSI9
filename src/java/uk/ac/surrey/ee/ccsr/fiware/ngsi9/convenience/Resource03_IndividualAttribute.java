/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import eu.fiware.iot.ngsi.*;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard.Resource02_Discovery;

/**
 * Resource which has only one representation.
 */
public class Resource03_IndividualAttribute extends ServerResource {

    @Get
    public Representation getDescription() throws JAXBException {
        
        
        EntityId eId = new EntityId();
        String eIdString = (String) getRequest().getAttributes().get("EntityID");
        eId.setId(eIdString);
        ArrayList<EntityId> discEidList = new ArrayList<EntityId>();
        discEidList.add(eId);
        String attribute = (String) getRequest().getAttributes().get("attributeName");
        ArrayList<String> discAttrList = new ArrayList<String>();
        ArrayList<OperationScope> opScopeList = new ArrayList<OperationScope>();
        discAttrList.add(attribute);
        
        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        
        
        Resource02_Discovery resDisc = new Resource02_Discovery();
        String discRespMsg = resDisc.discoverContext(context, discEidList, discAttrList, opScopeList);
        System.out.println("Response To Send: \n" + discRespMsg);
        StringRepresentation result = new StringRepresentation(discRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
    }
    
}
