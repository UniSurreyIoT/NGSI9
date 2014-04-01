/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import javax.servlet.ServletContext;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o.RegisterStoreAccess;

/**
 * Resource which has only one representation.
 */
public class Resource_testUnitDb extends ServerResource {
   
    @Get
    public Representation setUpTestDb() {

        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        RegisterStoreAccess rs = new RegisterStoreAccess(context);
        rs.deleteDb();
        
            return new StringRepresentation("NGSI-9 SERVER READY");
    }

    
    
    
}
