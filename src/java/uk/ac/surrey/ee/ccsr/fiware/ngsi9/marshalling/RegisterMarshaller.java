package uk.ac.surrey.ee.ccsr.fiware.ngsi9.marshalling;

import eu.fiware.iot.ngsi.RegisterContextRequest;
import eu.fiware.iot.ngsi.RegisterContextResponse;
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



public class RegisterMarshaller {

    // REQUEST UNMARSHALL (XML TO OBJECT)
    public RegisterContextRequest unmarshallRequest(InputStream reqXmlMsg)
            throws JAXBException {

        JAXBContext context = JAXBContext
                .newInstance("eu.fiware.iot.ngsi");
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement<RegisterContextRequest> element = 
                (JAXBElement<RegisterContextRequest>) unmarshaller.unmarshal((reqXmlMsg));
        RegisterContextRequest req = element.getValue();
        //RegisterContextRequest req = (RegisterContextRequest) unmarshaller.unmarshal(reqXmlMsg);			

        return req;
    }

    // REQUEST MARSHALL (OBJECT TO XML)
    public String marshallRequest(RegisterContextRequest regContReq) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance("eu.fiware.iot.ngsi");
        Marshaller marshaller = context.createMarshaller();
        //marshaller.marshal(entity, System.out);
        StringWriter w = new StringWriter();
        marshaller.marshal(regContReq, w);//(entity, System.out);
        String result = prettyFormat(w.toString());
        //System.out.println(result);
        return result;
    }

    // RESPONSE MARSHALL (OBJECT TO XML)
    public String marshallResponse(RegisterContextResponse regContResp) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance("eu.fiware.iot.ngsi");
        Marshaller marshaller = context.createMarshaller();
        //marshaller.marshal(entity, System.out);
        StringWriter w = new StringWriter();
        marshaller.marshal(regContResp, w);//(entity, System.out);
        String result = prettyFormat(w.toString());
        //System.out.println(result);
        return result;
    }

//REQUEST UNMARSHALL (from file)
    public RegisterContextRequest unmarshallRequest(String fullFilePath)
            throws JAXBException {

        JAXBContext context = JAXBContext
                .newInstance("eu.fiware.iot.ngsi");
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement<RegisterContextRequest> element = unmarshaller.unmarshal(
                new StreamSource(new File(System.getProperty("user.dir")
                                + File.separator + fullFilePath)),
                RegisterContextRequest.class);
        RegisterContextRequest req = element.getValue();

        return req;

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

}
