//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.25 at 06:50:50 PM BST 
//


package eu.fiware.iot.ngsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiscoveryContextAvailabilityRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiscoveryContextAvailabilityRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entityIdList" type="{}EntityIdList"/>
 *         &lt;element name="attributeList" type="{}AttributeList" minOccurs="0"/>
 *         &lt;element name="restriction" type="{}Restriction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscoveryContextAvailabilityRequest", propOrder = {
    "entityIdList",
    "attributeList",
    "restriction"
})
@XmlRootElement(name = "discoverContextAvailabilityRequest")
public class DiscoveryContextAvailabilityRequest {

    @XmlElement(required = true)
    protected EntityIdList entityIdList;
    protected AttributeList attributeList;
    protected Restriction restriction;

    /**
     * Gets the value of the entityIdList property.
     * 
     * @return
     *     possible object is
     *     {@link EntityIdList }
     *     
     */
    public EntityIdList getEntityIdList() {
        return entityIdList;
    }

    /**
     * Sets the value of the entityIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityIdList }
     *     
     */
    public void setEntityIdList(EntityIdList value) {
        this.entityIdList = value;
    }

    /**
     * Gets the value of the attributeList property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeList }
     *     
     */
    public AttributeList getAttributeList() {
        return attributeList;
    }

    /**
     * Sets the value of the attributeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeList }
     *     
     */
    public void setAttributeList(AttributeList value) {
        this.attributeList = value;
    }

    /**
     * Gets the value of the restriction property.
     * 
     * @return
     *     possible object is
     *     {@link Restriction }
     *     
     */
    public Restriction getRestriction() {
        return restriction;
    }

    /**
     * Sets the value of the restriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Restriction }
     *     
     */
    public void setRestriction(Restriction value) {
        this.restriction = value;
    }

}