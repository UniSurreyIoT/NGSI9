/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.rest.restlet;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


/**
 * Resource which has only one representation.
 */
public class indexResource extends ServerResource {

    
    @Get
    public Representation getDescription() {

        //get servlet context for getting files
        
        //get servlet context for getting files
        ServletContext sc = (ServletContext) getContext().getServerDispatcher().getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        System.out.println(sc.getRealPath("")+"index.html");
        String htmlContent="";
        try {
            htmlContent = FileUtils.readFileToString(new File(sc.getRealPath("/")+"index.html"));
        } catch (IOException ex) {
            Logger.getLogger(indexResource.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        StringRepresentation result = new StringRepresentation(htmlContent, MediaType.TEXT_HTML);
       

        return result;
    }
}