package uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling;

import eu.fiware.iot.ngsi.DiscoveryContextAvailabilityRequest;
import eu.fiware.iot.ngsi.DiscoveryContextAvailabilityResponse;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;



public class DiscoveryMarshaller {

       // REQUEST UNMARSHALL (XML TO OBJECT) *MESSAGE*
    public DiscoveryContextAvailabilityRequest unmarshallRequest(InputStream reqXmlMsg)
            throws JAXBException {

        JAXBContext context = JAXBContext
                .newInstance("eu.fiware.iot.ngsi");
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement<DiscoveryContextAvailabilityRequest> element = 
                (JAXBElement<DiscoveryContextAvailabilityRequest>) unmarshaller.unmarshal((reqXmlMsg));
        DiscoveryContextAvailabilityRequest req = element.getValue();

        return req;
    }
    
    // REQUEST MARSHALL (OBJECT TO XML)
    public String marshallRequest(DiscoveryContextAvailabilityRequest discContReq) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance("eu.fiware.iot.ngsi");
        Marshaller marshaller = context.createMarshaller();
        //marshaller.marshal(entity, System.out);
        StringWriter w = new StringWriter();
        marshaller.marshal(discContReq, w);//(entity, System.out);
        String result = prettyFormat(w.toString());
        //System.out.println(result);
        return result;
    }

    // RESPONSE MARSHALL (OBJECT TO XML) *MESSAGE*
    public String marshallResponse(DiscoveryContextAvailabilityResponse discContResp) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance("eu.fiware.iot.ngsi");
        Marshaller marshaller = context.createMarshaller();
        //marshaller.marshal(entity, System.out);
        StringWriter w = new StringWriter();
        marshaller.marshal(discContResp, w);//(entity, System.out);
        String result = prettyFormat(w.toString());
        //System.out.println(result);
        return result;

    }

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }
    
     // REQUEST UNMARSHALL (XML TO OBJECT) *FILE*
    public DiscoveryContextAvailabilityRequest unmarshallRequest(String fullFilePath)
            throws JAXBException {

        JAXBContext context = JAXBContext
                .newInstance("eu.fiware.iot.ngsi");
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement<DiscoveryContextAvailabilityRequest> element = unmarshaller.unmarshal(
                new StreamSource(new File(System.getProperty("user.dir")
                                + File.separator + fullFilePath)),
                DiscoveryContextAvailabilityRequest.class);
        DiscoveryContextAvailabilityRequest req = element.getValue();

        return req;

    }

}
