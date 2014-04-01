//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.03 at 10:28:59 PM GMT 
//


package eu.fiware.iot.ngsi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.fiware.iot.ngsi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RegisterContextRequest_QNAME = new QName("", "registerContextRequest");
    private final static QName _SubscribeContextAvailabilityRequest_QNAME = new QName("", "subscribeContextAvailabilityRequest");
    private final static QName _EntityId_QNAME = new QName("", "entityId");
    private final static QName _ContextRegistrationAttribute_QNAME = new QName("", "contextRegistrationAttribute");
    private final static QName _ContextMetadata_QNAME = new QName("", "contextMetadata");
    private final static QName _StatusCode_QNAME = new QName("", "statusCode");
    private final static QName _ContextAttribute_QNAME = new QName("", "contextAttribute");
    private final static QName _DiscoverContextAvailabilityResponse_QNAME = new QName("", "discoverContextAvailabilityResponse");
    private final static QName _UpdateContextAvailabilitySubscriptionRequest_QNAME = new QName("", "updateContextAvailabilitySubscriptionRequest");
    private final static QName _UnsubscribeContextAvailabilityResponse_QNAME = new QName("", "unsubscribeContextAvailabilityResponse");
    private final static QName _OperationScope_QNAME = new QName("", "operationScope");
    private final static QName _ContextElementResponse_QNAME = new QName("", "contextElementResponse");
    private final static QName _NotifyContextAvailabilityRequest_QNAME = new QName("", "notifyContextAvailabilityRequest");
    private final static QName _Restriction_QNAME = new QName("", "restriction");
    private final static QName _SubscribeContextAvailabilityResponse_QNAME = new QName("", "subscribeContextAvailabilityResponse");
    private final static QName _DiscoverContextAvailabilityRequest_QNAME = new QName("", "discoverContextAvailabilityRequest");
    private final static QName _NotifyConditionType_QNAME = new QName("", "notifyConditionType");
    private final static QName _NotifyContextAvailabilitytResponse_QNAME = new QName("", "notifyContextAvailabilitytResponse");
    private final static QName _UnsubscribeContextAvailabilityRequest_QNAME = new QName("", "unsubscribeContextAvailabilityRequest");
    private final static QName _ContextElement_QNAME = new QName("", "contextElement");
    private final static QName _SubscribeResponse_QNAME = new QName("", "subscribeResponse");
    private final static QName _RegisterContextResponse_QNAME = new QName("", "registerContextResponse");
    private final static QName _ContextRegistration_QNAME = new QName("", "contextRegistration");
    private final static QName _NotifyCondition_QNAME = new QName("", "notifyCondition");
    private final static QName _RegistrationMetadata_QNAME = new QName("", "registrationMetadata");
    private final static QName _SubscribeError_QNAME = new QName("", "subscribeError");
    private final static QName _UpdateContextAvailabilitySubscriptionResponse_QNAME = new QName("", "updateContextAvailabilitySubscriptionResponse");
    private final static QName _ContextRegistrationResponse_QNAME = new QName("", "contextRegistrationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.fiware.iot.ngsi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegisterContextRequest }
     * 
     */
    public RegisterContextRequest createRegisterContextRequest() {
        return new RegisterContextRequest();
    }

    /**
     * Create an instance of {@link SubscribeContextAvailabilityRequest }
     * 
     */
    public SubscribeContextAvailabilityRequest createSubscribeContextAvailabilityRequest() {
        return new SubscribeContextAvailabilityRequest();
    }

    /**
     * Create an instance of {@link EntityId }
     * 
     */
    public EntityId createEntityId() {
        return new EntityId();
    }

    /**
     * Create an instance of {@link ContextRegistrationAttribute }
     * 
     */
    public ContextRegistrationAttribute createContextRegistrationAttribute() {
        return new ContextRegistrationAttribute();
    }

    /**
     * Create an instance of {@link ContextMetadata }
     * 
     */
    public ContextMetadata createContextMetadata() {
        return new ContextMetadata();
    }

    /**
     * Create an instance of {@link StatusCode }
     * 
     */
    public StatusCode createStatusCode() {
        return new StatusCode();
    }

    /**
     * Create an instance of {@link ContextAttribute }
     * 
     */
    public ContextAttribute createContextAttribute() {
        return new ContextAttribute();
    }

    /**
     * Create an instance of {@link DiscoveryContextAvailabilityResponse }
     * 
     */
    public DiscoveryContextAvailabilityResponse createDiscoveryContextAvailabilityResponse() {
        return new DiscoveryContextAvailabilityResponse();
    }

    /**
     * Create an instance of {@link UpdateContextAvailabilitySubscriptionRequest }
     * 
     */
    public UpdateContextAvailabilitySubscriptionRequest createUpdateContextAvailabilitySubscriptionRequest() {
        return new UpdateContextAvailabilitySubscriptionRequest();
    }

    /**
     * Create an instance of {@link UnsubscribeContextAvailabilityResponse }
     * 
     */
    public UnsubscribeContextAvailabilityResponse createUnsubscribeContextAvailabilityResponse() {
        return new UnsubscribeContextAvailabilityResponse();
    }

    /**
     * Create an instance of {@link OperationScope }
     * 
     */
    public OperationScope createOperationScope() {
        return new OperationScope();
    }

    /**
     * Create an instance of {@link ContextElementResponse }
     * 
     */
    public ContextElementResponse createContextElementResponse() {
        return new ContextElementResponse();
    }

    /**
     * Create an instance of {@link NotifyContextAvailabilityRequest }
     * 
     */
    public NotifyContextAvailabilityRequest createNotifyContextAvailabilityRequest() {
        return new NotifyContextAvailabilityRequest();
    }

    /**
     * Create an instance of {@link Restriction }
     * 
     */
    public Restriction createRestriction() {
        return new Restriction();
    }

    /**
     * Create an instance of {@link SubscribeContextAvailabilityResponse }
     * 
     */
    public SubscribeContextAvailabilityResponse createSubscribeContextAvailabilityResponse() {
        return new SubscribeContextAvailabilityResponse();
    }

    /**
     * Create an instance of {@link DiscoveryContextAvailabilityRequest }
     * 
     */
    public DiscoveryContextAvailabilityRequest createDiscoveryContextAvailabilityRequest() {
        return new DiscoveryContextAvailabilityRequest();
    }

    /**
     * Create an instance of {@link NotifyContextAvailabilityResponse }
     * 
     */
    public NotifyContextAvailabilityResponse createNotifyContextAvailabilityResponse() {
        return new NotifyContextAvailabilityResponse();
    }

    /**
     * Create an instance of {@link UnsubscribeContextAvailabilityRequest }
     * 
     */
    public UnsubscribeContextAvailabilityRequest createUnsubscribeContextAvailabilityRequest() {
        return new UnsubscribeContextAvailabilityRequest();
    }

    /**
     * Create an instance of {@link ContextElement }
     * 
     */
    public ContextElement createContextElement() {
        return new ContextElement();
    }

    /**
     * Create an instance of {@link SubscribeResponse }
     * 
     */
    public SubscribeResponse createSubscribeResponse() {
        return new SubscribeResponse();
    }

    /**
     * Create an instance of {@link RegisterContextResponse }
     * 
     */
    public RegisterContextResponse createRegisterContextResponse() {
        return new RegisterContextResponse();
    }

    /**
     * Create an instance of {@link ContextRegistration }
     * 
     */
    public ContextRegistration createContextRegistration() {
        return new ContextRegistration();
    }

    /**
     * Create an instance of {@link NotifyCondition }
     * 
     */
    public NotifyCondition createNotifyCondition() {
        return new NotifyCondition();
    }

    /**
     * Create an instance of {@link RegistrationMetadata }
     * 
     */
    public RegistrationMetadata createRegistrationMetadata() {
        return new RegistrationMetadata();
    }

    /**
     * Create an instance of {@link SubscribeError }
     * 
     */
    public SubscribeError createSubscribeError() {
        return new SubscribeError();
    }

    /**
     * Create an instance of {@link ContextRegistrationResponse }
     * 
     */
    public ContextRegistrationResponse createContextRegistrationResponse() {
        return new ContextRegistrationResponse();
    }

    /**
     * Create an instance of {@link UpdateContextAvailabilitySubscriptionResponse }
     * 
     */
    public UpdateContextAvailabilitySubscriptionResponse createUpdateContextAvailabilitySubscriptionResponse() {
        return new UpdateContextAvailabilitySubscriptionResponse();
    }

    /**
     * Create an instance of {@link ContextElementList }
     * 
     */
    public ContextElementList createContextElementList() {
        return new ContextElementList();
    }

    /**
     * Create an instance of {@link ContextRegistrationResponseList }
     * 
     */
    public ContextRegistrationResponseList createContextRegistrationResponseList() {
        return new ContextRegistrationResponseList();
    }

    /**
     * Create an instance of {@link AttributeList }
     * 
     */
    public AttributeList createAttributeList() {
        return new AttributeList();
    }

    /**
     * Create an instance of {@link Association }
     * 
     */
    public Association createAssociation() {
        return new Association();
    }

    /**
     * Create an instance of {@link AttributeAssociationList }
     * 
     */
    public AttributeAssociationList createAttributeAssociationList() {
        return new AttributeAssociationList();
    }

    /**
     * Create an instance of {@link ContextElementResponseList }
     * 
     */
    public ContextElementResponseList createContextElementResponseList() {
        return new ContextElementResponseList();
    }

    /**
     * Create an instance of {@link CondValueList }
     * 
     */
    public CondValueList createCondValueList() {
        return new CondValueList();
    }

    /**
     * Create an instance of {@link NotifyConditionList }
     * 
     */
    public NotifyConditionList createNotifyConditionList() {
        return new NotifyConditionList();
    }

    /**
     * Create an instance of {@link ContextRegistrationList }
     * 
     */
    public ContextRegistrationList createContextRegistrationList() {
        return new ContextRegistrationList();
    }

    /**
     * Create an instance of {@link ContextAttributeList }
     * 
     */
    public ContextAttributeList createContextAttributeList() {
        return new ContextAttributeList();
    }

    /**
     * Create an instance of {@link EntityIdList }
     * 
     */
    public EntityIdList createEntityIdList() {
        return new EntityIdList();
    }

    /**
     * Create an instance of {@link OperationScopeList }
     * 
     */
    public OperationScopeList createOperationScopeList() {
        return new OperationScopeList();
    }

    /**
     * Create an instance of {@link ContextRegistrationAttributeList }
     * 
     */
    public ContextRegistrationAttributeList createContextRegistrationAttributeList() {
        return new ContextRegistrationAttributeList();
    }

    /**
     * Create an instance of {@link ContextMetadataList }
     * 
     */
    public ContextMetadataList createContextMetadataList() {
        return new ContextMetadataList();
    }

    /**
     * Create an instance of {@link AttributeAssociation }
     * 
     */
    public AttributeAssociation createAttributeAssociation() {
        return new AttributeAssociation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterContextRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "registerContextRequest")
    public JAXBElement<RegisterContextRequest> createRegisterContextRequest(RegisterContextRequest value) {
        return new JAXBElement<RegisterContextRequest>(_RegisterContextRequest_QNAME, RegisterContextRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeContextAvailabilityRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "subscribeContextAvailabilityRequest")
    public JAXBElement<SubscribeContextAvailabilityRequest> createSubscribeContextAvailabilityRequest(SubscribeContextAvailabilityRequest value) {
        return new JAXBElement<SubscribeContextAvailabilityRequest>(_SubscribeContextAvailabilityRequest_QNAME, SubscribeContextAvailabilityRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntityId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "entityId")
    public JAXBElement<EntityId> createEntityId(EntityId value) {
        return new JAXBElement<EntityId>(_EntityId_QNAME, EntityId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextRegistrationAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextRegistrationAttribute")
    public JAXBElement<ContextRegistrationAttribute> createContextRegistrationAttribute(ContextRegistrationAttribute value) {
        return new JAXBElement<ContextRegistrationAttribute>(_ContextRegistrationAttribute_QNAME, ContextRegistrationAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextMetadata }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextMetadata")
    public JAXBElement<ContextMetadata> createContextMetadata(ContextMetadata value) {
        return new JAXBElement<ContextMetadata>(_ContextMetadata_QNAME, ContextMetadata.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "statusCode")
    public JAXBElement<StatusCode> createStatusCode(StatusCode value) {
        return new JAXBElement<StatusCode>(_StatusCode_QNAME, StatusCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextAttribute")
    public JAXBElement<ContextAttribute> createContextAttribute(ContextAttribute value) {
        return new JAXBElement<ContextAttribute>(_ContextAttribute_QNAME, ContextAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscoveryContextAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "discoverContextAvailabilityResponse")
    public JAXBElement<DiscoveryContextAvailabilityResponse> createDiscoverContextAvailabilityResponse(DiscoveryContextAvailabilityResponse value) {
        return new JAXBElement<DiscoveryContextAvailabilityResponse>(_DiscoverContextAvailabilityResponse_QNAME, DiscoveryContextAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContextAvailabilitySubscriptionRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "updateContextAvailabilitySubscriptionRequest")
    public JAXBElement<UpdateContextAvailabilitySubscriptionRequest> createUpdateContextAvailabilitySubscriptionRequest(UpdateContextAvailabilitySubscriptionRequest value) {
        return new JAXBElement<UpdateContextAvailabilitySubscriptionRequest>(_UpdateContextAvailabilitySubscriptionRequest_QNAME, UpdateContextAvailabilitySubscriptionRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsubscribeContextAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "unsubscribeContextAvailabilityResponse")
    public JAXBElement<UnsubscribeContextAvailabilityResponse> createUnsubscribeContextAvailabilityResponse(UnsubscribeContextAvailabilityResponse value) {
        return new JAXBElement<UnsubscribeContextAvailabilityResponse>(_UnsubscribeContextAvailabilityResponse_QNAME, UnsubscribeContextAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationScope }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "operationScope")
    public JAXBElement<OperationScope> createOperationScope(OperationScope value) {
        return new JAXBElement<OperationScope>(_OperationScope_QNAME, OperationScope.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextElementResponse")
    public JAXBElement<ContextElementResponse> createContextElementResponse(ContextElementResponse value) {
        return new JAXBElement<ContextElementResponse>(_ContextElementResponse_QNAME, ContextElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyContextAvailabilityRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "notifyContextAvailabilityRequest")
    public JAXBElement<NotifyContextAvailabilityRequest> createNotifyContextAvailabilityRequest(NotifyContextAvailabilityRequest value) {
        return new JAXBElement<NotifyContextAvailabilityRequest>(_NotifyContextAvailabilityRequest_QNAME, NotifyContextAvailabilityRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Restriction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "restriction")
    public JAXBElement<Restriction> createRestriction(Restriction value) {
        return new JAXBElement<Restriction>(_Restriction_QNAME, Restriction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeContextAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "subscribeContextAvailabilityResponse")
    public JAXBElement<SubscribeContextAvailabilityResponse> createSubscribeContextAvailabilityResponse(SubscribeContextAvailabilityResponse value) {
        return new JAXBElement<SubscribeContextAvailabilityResponse>(_SubscribeContextAvailabilityResponse_QNAME, SubscribeContextAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscoveryContextAvailabilityRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "discoverContextAvailabilityRequest")
    public JAXBElement<DiscoveryContextAvailabilityRequest> createDiscoverContextAvailabilityRequest(DiscoveryContextAvailabilityRequest value) {
        return new JAXBElement<DiscoveryContextAvailabilityRequest>(_DiscoverContextAvailabilityRequest_QNAME, DiscoveryContextAvailabilityRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyConditionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "notifyConditionType")
    public JAXBElement<NotifyConditionType> createNotifyConditionType(NotifyConditionType value) {
        return new JAXBElement<NotifyConditionType>(_NotifyConditionType_QNAME, NotifyConditionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyContextAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "notifyContextAvailabilitytResponse")
    public JAXBElement<NotifyContextAvailabilityResponse> createNotifyContextAvailabilitytResponse(NotifyContextAvailabilityResponse value) {
        return new JAXBElement<NotifyContextAvailabilityResponse>(_NotifyContextAvailabilitytResponse_QNAME, NotifyContextAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsubscribeContextAvailabilityRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "unsubscribeContextAvailabilityRequest")
    public JAXBElement<UnsubscribeContextAvailabilityRequest> createUnsubscribeContextAvailabilityRequest(UnsubscribeContextAvailabilityRequest value) {
        return new JAXBElement<UnsubscribeContextAvailabilityRequest>(_UnsubscribeContextAvailabilityRequest_QNAME, UnsubscribeContextAvailabilityRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextElement")
    public JAXBElement<ContextElement> createContextElement(ContextElement value) {
        return new JAXBElement<ContextElement>(_ContextElement_QNAME, ContextElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "subscribeResponse")
    public JAXBElement<SubscribeResponse> createSubscribeResponse(SubscribeResponse value) {
        return new JAXBElement<SubscribeResponse>(_SubscribeResponse_QNAME, SubscribeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterContextResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "registerContextResponse")
    public JAXBElement<RegisterContextResponse> createRegisterContextResponse(RegisterContextResponse value) {
        return new JAXBElement<RegisterContextResponse>(_RegisterContextResponse_QNAME, RegisterContextResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextRegistration")
    public JAXBElement<ContextRegistration> createContextRegistration(ContextRegistration value) {
        return new JAXBElement<ContextRegistration>(_ContextRegistration_QNAME, ContextRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyCondition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "notifyCondition")
    public JAXBElement<NotifyCondition> createNotifyCondition(NotifyCondition value) {
        return new JAXBElement<NotifyCondition>(_NotifyCondition_QNAME, NotifyCondition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrationMetadata }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "registrationMetadata")
    public JAXBElement<RegistrationMetadata> createRegistrationMetadata(RegistrationMetadata value) {
        return new JAXBElement<RegistrationMetadata>(_RegistrationMetadata_QNAME, RegistrationMetadata.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "subscribeError")
    public JAXBElement<SubscribeError> createSubscribeError(SubscribeError value) {
        return new JAXBElement<SubscribeError>(_SubscribeError_QNAME, SubscribeError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContextAvailabilitySubscriptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "updateContextAvailabilitySubscriptionResponse")
    public JAXBElement<UpdateContextAvailabilitySubscriptionResponse> createUpdateContextAvailabilitySubscriptionResponse(UpdateContextAvailabilitySubscriptionResponse value) {
        return new JAXBElement<UpdateContextAvailabilitySubscriptionResponse>(_UpdateContextAvailabilitySubscriptionResponse_QNAME, UpdateContextAvailabilitySubscriptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextRegistrationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contextRegistrationResponse")
    public JAXBElement<ContextRegistrationResponse> createContextRegistrationResponse(ContextRegistrationResponse value) {
        return new JAXBElement<ContextRegistrationResponse>(_ContextRegistrationResponse_QNAME, ContextRegistrationResponse.class, null, value);
    }

}