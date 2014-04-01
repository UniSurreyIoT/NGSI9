/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.fiware.ngsi9.notify;

import eu.fiware.iot.ngsi.ContextRegistration;
import eu.fiware.iot.ngsi.ContextRegistrationResponse;
import eu.fiware.iot.ngsi.ContextRegistrationResponseList;
import eu.fiware.iot.ngsi.NotifyContextAvailabilityRequest;
import eu.fiware.iot.ngsi.RegisterContextRequest;
import eu.fiware.iot.ngsi.SubscribeContextAvailabilityRequest;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.restlet.resource.ClientResource;
import uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling.NotifyMarshaller;

/**
 *
 * @author te0003
 */
public class AvailabilityNotifier implements Runnable{
    
    public RegisterContextRequest regReq;
    public List<SubscribeContextAvailabilityRequest> subReq;
    
    public AvailabilityNotifier(RegisterContextRequest regRequest, List<SubscribeContextAvailabilityRequest> subRequest){
        
        this.regReq=regRequest;
        this.subReq=subRequest;
    
    }

    public void run() {

        int subReqListSize = subReq.size();
        
        List<ContextRegistration> cr = regReq.getContextRegistrationList().getContextRegistration();

        //iterate through each subscription
        for (int i = 0; i < subReqListSize; i++) {
            
            NotifyContextAvailabilityRequest ncar = new NotifyContextAvailabilityRequest();
            ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();

            int crListSize = cr.size();

            //iterate through each context registration
            for (int j = 0; j < crListSize; j++) {
                ContextRegistrationResponse crr = new ContextRegistrationResponse();
                crr.setContextRegistration(cr.get(j));
                crrl.getContextRegistrationResponse().add(crr);
            }
            
            //marshal request
            NotifyMarshaller nm = new NotifyMarshaller();

            String notifMsg = null;
            try {
                notifMsg = nm.marshallRequest(ncar);
            } catch (JAXBException ex) {
                Logger.getLogger(AvailabilityNotifier.class.getName()).log(Level.SEVERE, null, ex);
            }

            //notify subscriber
            String uri = subReq.get(i).getReference();
            ClientResource resource = new ClientResource(uri);
            String payload = notifMsg;
            try {
                resource.post(payload).write(System.out); //Response is not handled for now
            } catch (IOException ex) {
                Logger.getLogger(AvailabilityNotifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
