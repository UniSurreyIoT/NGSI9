/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.rest.restlet;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


/**
 * Resource which has only one representation.
 */
public class SanityCheckResource extends ServerResource {

    
    @Get
    public String getDescription() {

        //get servlet context for getting files
        String result = "<sanityCheck>\n"
                + "  <name>IoT Discovery</name>\n"
                + "  <type>Sanity Check</type>\n"
                + "  <version>Version: 2.3.0.SNAPSHOT</version>\n"
                + "  </sanityCheck>";

        return result;
    }
}