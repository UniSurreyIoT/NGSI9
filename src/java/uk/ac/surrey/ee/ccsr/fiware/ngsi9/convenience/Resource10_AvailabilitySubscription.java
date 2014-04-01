/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.convenience;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard.Resource04_AvailabilitySubscriptionUpdate;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.standard.Resource05_AvailabilitySubscriptionDeletion;

/**
 * Resource which has only one representation.
 */
public class Resource10_AvailabilitySubscription extends ServerResource {

    @Put
    public Representation subscribeToDescription(Representation entity) throws ResourceException, IOException, JAXBException {

        InputStream description = new ByteArrayInputStream(entity.getText().getBytes());
        Resource04_AvailabilitySubscriptionUpdate ras = new Resource04_AvailabilitySubscriptionUpdate();
        String respMsg = ras.subscribeToContext(description);

        System.out.println(respMsg);
        StringRepresentation result = new StringRepresentation(respMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;
    }

    @Delete
    public Representation removeDescription() throws JAXBException, ResourceException, IOException {

        String subsId = (String) getRequest().getAttributes().get("subscriptionID");
        Resource05_AvailabilitySubscriptionDeletion ras = new Resource05_AvailabilitySubscriptionDeletion();

        ServletContext context = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");

        String respMsg = ras.unsubscribeToContext(context, subsId);

        StringRepresentation result = new StringRepresentation(respMsg);
        result.setMediaType(MediaType.APPLICATION_XML);
        return result;

    }

}
