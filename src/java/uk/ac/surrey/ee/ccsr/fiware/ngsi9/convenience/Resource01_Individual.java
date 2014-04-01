/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import eu.fiware.iot.ngsi.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.*;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard.Resource01_ContextRegistration;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard.Resource02_Discovery;

/**
 * Resource which has only one representation.
 */
public class Resource01_Individual extends ServerResource {
    
    @Get
    public Representation getDescription() throws JAXBException {        
        
        //get entity ID from URL
        String eIdString = (String) getRequest().getAttributes().get("EntityID");
        EntityId eId = new EntityId();
        eId.setId(eIdString);        
        ArrayList<EntityId> discEidList = new ArrayList<EntityId>();
        //these 2 arrays are left empty
        ArrayList<String> discAttrList = new ArrayList<String>();
        ArrayList<OperationScope> opScopeList = new ArrayList<OperationScope>();
        //add the entity ID to the array list
        discEidList.add(eId);
        
        //get servlet context
        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        
        //use discover method to retreive description
        Resource02_Discovery resDisc = new Resource02_Discovery();        
        String discRespMsg = resDisc.discoverContext(context, discEidList, discAttrList, opScopeList);
       
        System.out.println("Response To Send: \n" + discRespMsg);
        StringRepresentation result=new StringRepresentation(discRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
    }

    @Post
    public Representation registerDescription(Representation entity) throws ResourceException, IOException, JAXBException {
        
        //get registerContextRequest
        InputStream description = new ByteArrayInputStream(entity.getText().getBytes());
        Resource01_ContextRegistration regConHandler = new Resource01_ContextRegistration();
        
        //get servlet context
        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        
        //register context provider
        String regRespMsg = regConHandler.registerContext(context,description);        
        System.out.println(regRespMsg);
        StringRepresentation result=new StringRepresentation(regRespMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
    }
    
}
