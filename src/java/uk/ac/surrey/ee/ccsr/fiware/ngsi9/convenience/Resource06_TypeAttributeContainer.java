/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import java.io.IOException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 */
public class Resource06_TypeAttributeContainer extends ServerResource {

    @Get
    public String getReq() {       

        return "METHOD IMPLEMENTED IN CO_5";
    }
    
}
