/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import java.io.IOException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 */
public class Resource09_AvailabilitySubscriptionContainer extends ServerResource {
    
    @Post
    public Representation publishDescription(Representation entity) throws ResourceException, IOException {

        //String description = entity.getText();
        //String objType = (String) getRequest().getResourceRef().getPath();

        return new StringRepresentation("POST Resource for "+this.toString()+ ": Create a new availability subscription...\n\ncoming soon!");
    }
    
}
