//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.25 at 06:50:50 PM BST 
//


package eu.fiware.iot.ngsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiscoveryContextAvailabilityResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiscoveryContextAvailabilityResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contextRegistrationResponseList" type="{}ContextRegistrationResponseList" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{}StatusCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscoveryContextAvailabilityResponse", propOrder = {
    "contextRegistrationResponseList",
    "errorCode"
})
@XmlRootElement(name = "discoverContextAvailabilityResponse")
public class DiscoveryContextAvailabilityResponse {

    protected ContextRegistrationResponseList contextRegistrationResponseList;
    protected StatusCode errorCode;

    /**
     * Gets the value of the contextRegistrationResponseList property.
     * 
     * @return
     *     possible object is
     *     {@link ContextRegistrationResponseList }
     *     
     */
    public ContextRegistrationResponseList getContextRegistrationResponseList() {
        return contextRegistrationResponseList;
    }

    /**
     * Sets the value of the contextRegistrationResponseList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextRegistrationResponseList }
     *     
     */
    public void setContextRegistrationResponseList(ContextRegistrationResponseList value) {
        this.contextRegistrationResponseList = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link StatusCode }
     *     
     */
    public StatusCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusCode }
     *     
     */
    public void setErrorCode(StatusCode value) {
        this.errorCode = value;
    }

}
