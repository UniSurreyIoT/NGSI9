package uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import eu.fiware.iot.ngsi.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class RegisterStoreAccess {

    public ObjectContainer db;
    public static String DB4OFILENAME = "";

    public RegisterStoreAccess() {

        DB4OFILENAME = System.getProperty("user.dir") + "/repository/ngsiRegRepo.db4o";
        //user.home
    }

    public RegisterStoreAccess(ServletContext context) {

        DB4OFILENAME = context.getRealPath("/repository/ngsiRegRepo.db4o");
    }

    public void openDb4o() {

        System.out.println(DB4OFILENAME);

        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(15); // set activation depth
        db = Db4oEmbedded.openFile(configuration, DB4OFILENAME);
    }

    public void closeDb4o() {
        db.close();

    }

    public void deleteDb() {

        new File(DB4OFILENAME).delete();
        System.out.println("db deleted: " + DB4OFILENAME);
    }

    public void storeRegistration(RegisterContextRequest regreq) {

        openDb4o();

        try {
            db.store(regreq);
        } finally {
            db.close();
            System.out.println("RegisterContextRequest Stored: " + regreq.getRegistrationId());
        }
    }

    public boolean checkRegIdUsed(final String idGenerated) {

        openDb4o();
        List<RegisterContextRequest> results;
        //final String updateRegId = rcrNew.getRegistrationId();
        results = db.query(new Predicate<RegisterContextRequest>() {
            public boolean match(RegisterContextRequest req) {
                String storedRegID = req.getRegistrationId();
                if (storedRegID.equals(idGenerated)) {
                    return true;
                }
                return false;
            }
        });

        if (results.size() > 0) {
            closeDb4o();
            return true;
        }
        closeDb4o();
        return false;
    }

    public boolean deleteRegistration(final RegisterContextRequest rcrNew) {

        List<RegisterContextRequest> results;
        final String updateRegId = rcrNew.getRegistrationId();

        openDb4o();
        results = db.query(new Predicate<RegisterContextRequest>() {
            public boolean match(RegisterContextRequest req) {
                String storedRegID = req.getRegistrationId();
                if (storedRegID.equals(updateRegId)) {
                    return true;
                }
                return false;
            }
        });

        if (results.size() > 0) {
            RegisterContextRequest found = results.get(0);
            System.out.println("Deleting registration");
            db.delete(found);
            closeDb4o();
            return true;
        }
        closeDb4o();
        return false;
    }

    public ObjectSet<RegisterContextRequest> getAllRegistrations() {

        ObjectSet<RegisterContextRequest> result = db
                .queryByExample(RegisterContextRequest.class);
        // listResult(result);
        return result;
    }

    public List<RegisterContextRequest> getAssociations(final EntityId eID, final String scopeValueType, final ArrayList<String> discAttrList) {

        List<RegisterContextRequest> results;

        results = db.query(new Predicate<RegisterContextRequest>() {
            public boolean match(RegisterContextRequest req) {

                final int contRegListSize = req.getContextRegistrationList().getContextRegistration().size();
                //System.out.println("contRegListSize: " + contRegListSize);
                for (int i = 0; i < contRegListSize; i++) {

                    final int contMetadataListSize = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().size();
                    //System.out.println("entityidListSize: " + entityIdListSize);

                    for (int j = 0; j < contMetadataListSize; j++) {

                        String scopeValueEId = "";
                        ArrayList<String> scopeValueAttr = new ArrayList<String>();
                        int attrListSize = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().get(j).getValue().getAttributeAssociationList().getAttributeAssociation().size();

                        //check if operation scope is "Association"
                        String scopeField = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().get(j).getType();
                        if (scopeField.equalsIgnoreCase("Association")) {

                            if (scopeValueType.equalsIgnoreCase("SOURCES")) {

                                scopeValueEId = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().get(j).getValue().getTargetEntityId().getId();

                                for (int k = 0; k < attrListSize; k++) {
                                    String scopeValueAttrString = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().get(j).getValue().getAttributeAssociationList().getAttributeAssociation().get(0).getSourceAttribute();
                                    scopeValueAttr.add(scopeValueAttrString);
                                }

                            } else if (scopeValueType.equalsIgnoreCase("TARGETS")) {

                                scopeValueEId = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().get(j).getValue().getSourceEntityId().getId();

                                for (int k = 0; k < attrListSize; k++) {
                                    String scopeValueAttrString = req.getContextRegistrationList().getContextRegistration().get(i).getRegistrationMetadata().getContextMetadata().get(j).getValue().getAttributeAssociationList().getAttributeAssociation().get(k).getTargetAttribute();
                                    scopeValueAttr.add(scopeValueAttrString);
                                }
                            }

                            if (scopeValueEId.equalsIgnoreCase(eID.getId())) {

                                int discAttrListSize = discAttrList.size();
                                if (discAttrListSize > 0) {

                                    for (int m = 0; m < discAttrListSize; m++) {
                                        if (scopeValueAttr.contains(discAttrList.get(m))) {
                                            return true;
                                        }
                                    }
                                }
                                return true;
                            }

                        }
                    }
                }
                return true;
            }
        });

        return results;

    }

    public List<RegisterContextRequest> getRegByEntityID(
            final EntityId entityID) {

        List<RegisterContextRequest> results;

        results = db.query(new Predicate<RegisterContextRequest>() {
            public boolean match(RegisterContextRequest req) {

                final int contRegListSize = req.getContextRegistrationList()
                        .getContextRegistration().size();
                //System.out.println("contRegListSize: " + contRegListSize);
                for (int i = 0; i < contRegListSize; i++) {

                    final int entityIdListSize = req
                            .getContextRegistrationList()
                            .getContextRegistration().get(i).getEntityIdList()
                            .getEntityId().size();
                    //System.out.println("entityidListSize: " + entityIdListSize);

                    for (int j = 0; j < entityIdListSize; j++) {

                        if (req.getContextRegistrationList()
                                .getContextRegistration().get(i)
                                .getEntityIdList().getEntityId().get(j).getId()
                                .equals(entityID.getId())) {
                            return true;
                        }
                    }
                }
                return true;
            }
        });
        return results;
    }

    public List<RegisterContextRequest> getRegByEntityType(
            final String entityType) {

        List<RegisterContextRequest> results;

        results = db.query(new Predicate<RegisterContextRequest>() {
            public boolean match(RegisterContextRequest req) {

                final int contRegListSize = req.getContextRegistrationList()
                        .getContextRegistration().size();
                System.out.println("contRegListSize: " + contRegListSize);
                for (int i = 0; i < contRegListSize; i++) {

                    final int entityIdListSize = req
                            .getContextRegistrationList()
                            .getContextRegistration().get(i).getEntityIdList()
                            .getEntityId().size();
                    System.out.println("entityidListSize: " + entityIdListSize);

                    for (int j = 0; j < entityIdListSize; j++) {

                        if (req.getContextRegistrationList()
                                .getContextRegistration().get(i)
                                .getEntityIdList().getEntityId().get(j)
                                .getType().equals(entityType)) {
                            return true;
                        }
                    }
                }
                return true;
            }
        });
        return results;
    }

    public ContextRegistrationResponse getContRegHasEntityId(
            List<RegisterContextRequest> result, String eId) {
        //ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();
        ContextRegistrationResponse crr = new ContextRegistrationResponse();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eidFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getId();
                    if (eIdCheck.equals(eId)) {
                        eidFound = true;
                        break;
                    }
                }
                if (eidFound) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);
                    
                    crr.setContextRegistration(regCr);
                    //crrl.getContextRegistrationResponse().add(crr);
                    return crr;
                }
                // else return crrl;
            }
        }
        return crr;
    }

    public ContextRegistrationResponseList getContRegListContainsEIdAttribute(
            List<RegisterContextRequest> result, String eId, String attr) {

        ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eIdFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getId();
                    if (eIdCheck.equals(eId)) {
                        eIdFound = true;
                        break;
                    }
                }

                boolean attributeFound = false;
                int attributeListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute().size();
                for (int k = 0; k < attributeListSize; k++) {

                    String attributeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k).getName();
                    if (attributeCheck.equals(attr)) {
                        attributeFound = true;
                        break;
                    }
                }
                if (eIdFound && attributeFound) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);
                    ContextRegistrationResponse crr = new ContextRegistrationResponse();
                    crr.setContextRegistration(regCr);
                    crrl.getContextRegistrationResponse().add(crr);
                    // return crrl;
                }
            }
        }
        return crrl;
    }

    //returns ContextRegistrationResponse for Standard Operation
    public ContextRegistrationResponse getContRegRespContainsEIdAttribute(
            List<RegisterContextRequest> result, EntityId eId, ArrayList<String> attr) {

        //ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();
        ContextRegistrationResponse crr = new ContextRegistrationResponse();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eIdFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getId();
                    if (eIdCheck.equals(eId.getId())) {
                        eIdFound = true;
                        break;
                    }
                }

                boolean attributeFound = false;
                int attributeListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute().size();
                for (int k = 0; k < attributeListSize; k++) {

                    String attributeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k).getName();

                    //if (attributeCheck.equals(attr)) {
                    if (attr.contains(attributeCheck)) {
                        attributeFound = true;
                        break;
                    }

                }
                if ((eIdFound && attributeFound) || (eIdFound && attr.size() < 1)) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);

                    crr.setContextRegistration(regCr);
                    return crr;
                }

            }
        }
        return crr;

    }

    public ContextRegistrationResponseList getContRegHasEntityIdAttrDomain(
            List<RegisterContextRequest> result, String eId, String attr) {

        ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eIdFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getId();
                    if (eIdCheck.equals(eId)) {
                        eIdFound = true;
                        break;
                    }
                }

                boolean attrDomainFound = false;
                int attributeListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute().size();
                for (int k = 0; k < attributeListSize; k++) {

                    String attrDomainCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k).getName();
                    boolean isAttrDomain = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k)
                            .isIsDomain();
                    if (attrDomainCheck.equals(attr) && isAttrDomain) {
                        attrDomainFound = true;
                        break;
                    }
                }
                if (eIdFound && attrDomainFound) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);
                    ContextRegistrationResponse crr = new ContextRegistrationResponse();
                    crr.setContextRegistration(regCr);
                    crrl.getContextRegistrationResponse().add(crr);
                    // return crrl;
                }
            }
        }
        return crrl;
    }

    public ContextRegistrationResponseList getContRegHasEntityType(
            List<RegisterContextRequest> result, String eIdType) {
        ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eidTypeFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdTypeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getType();
                    if (eIdTypeCheck.equals(eIdType)) {
                        eidTypeFound = true;
                        break;
                    }
                }

                if (eidTypeFound) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);
                    ContextRegistrationResponse crr = new ContextRegistrationResponse();
                    crr.setContextRegistration(regCr);
                    crrl.getContextRegistrationResponse().add(crr);
                    // return crrl;
                }
            }
        }
        return crrl;
    }

    public ContextRegistrationResponseList getContRegHasEntityTypeAttr(
            List<RegisterContextRequest> result, String eIdType, String attr) {
        ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eidTypeFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdTypeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getType();
                    if (eIdTypeCheck.equals(eIdType)) {
                        eidTypeFound = true;
                        break;
                    }
                }

                boolean attributeFound = false;
                int attributeListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute().size();
                for (int k = 0; k < attributeListSize; k++) {

                    String attributeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k).getName();
                    if (attributeCheck.equals(attr)) {
                        attributeFound = true;
                        break;
                    }
                }
                if (eidTypeFound && attributeFound) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);
                    ContextRegistrationResponse crr = new ContextRegistrationResponse();
                    crr.setContextRegistration(regCr);
                    crrl.getContextRegistrationResponse().add(crr);
                    // return crrl;
                }
            }
        }
        return crrl;
    }

    public ContextRegistrationResponseList getContRegContainsETypeAttrDomain(
            List<RegisterContextRequest> result, String eIdType,
            String attrDomain) {
        ContextRegistrationResponseList crrl = new ContextRegistrationResponseList();

        int resultListSize = result.size();
        for (int i = 0; i < resultListSize; i++) {

            int contRegListSize = result.get(i).getContextRegistrationList()
                    .getContextRegistration().size();
            for (int j = 0; j < contRegListSize; j++) {

                boolean eidTypeFound = false;
                int entityIdListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getEntityIdList().getEntityId().size();
                for (int k = 0; k < entityIdListSize; k++) {

                    String eIdTypeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j).getEntityIdList()
                            .getEntityId().get(k).getType();
                    if (eIdTypeCheck.equals(eIdType)) {
                        eidTypeFound = true;
                        break;
                    }
                }

                boolean attributeFound = false;
                int attributeListSize = result.get(i)
                        .getContextRegistrationList().getContextRegistration()
                        .get(j).getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute().size();

                for (int k = 0; k < attributeListSize; k++) {

                    String attributeCheck = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k).getName();
                    boolean isAttrDomain = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j)
                            .getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().get(k)
                            .isIsDomain();
                    if (attributeCheck.equals(attrDomain) && isAttrDomain) {
                        attributeFound = true;
                        break;
                    }
                }
                if (eidTypeFound && attributeFound) {
                    ContextRegistration regCr = result.get(i)
                            .getContextRegistrationList()
                            .getContextRegistration().get(j);
                    ContextRegistrationResponse crr = new ContextRegistrationResponse();
                    crr.setContextRegistration(regCr);
                    crrl.getContextRegistrationResponse().add(crr);
                }
            }
        }
        return crrl;
    }

    public DiscoveryContextAvailabilityResponse removeSharedEntityID(
            DiscoveryContextAvailabilityResponse discContResp, ArrayList<EntityId> discEIdList) {

        int discEIdListSize = discEIdList.size();
        ArrayList<String> eIdStringList = new ArrayList<String>();

        for (int i = 0; i < discEIdListSize; i++) {
            eIdStringList.add(discEIdList.get(i).getId());
        }

        int contRegRespListSize = discContResp
                .getContextRegistrationResponseList()
                .getContextRegistrationResponse().size();

        //iterate through contextRegistrationResponseLists
        for (int j = 0; j < contRegRespListSize; j++) {

            int entityIdListSize = discContResp
                    .getContextRegistrationResponseList()
                    .getContextRegistrationResponse().get(j)
                    .getContextRegistration().getEntityIdList().getEntityId()
                    .size();

            //iterate through Entity IDs
            for (int k = 0; k < entityIdListSize; k++) {

                String eIdCheck = discContResp
                        .getContextRegistrationResponseList()
                        .getContextRegistrationResponse().get(j)
                        .getContextRegistration().getEntityIdList()
                        .getEntityId().get(k).getId();

                if (!eIdStringList.contains(eIdCheck)) {
                    //remove Entity ID
                    discContResp.getContextRegistrationResponseList()
                            .getContextRegistrationResponse().get(j)
                            .getContextRegistration().getEntityIdList()
                            .getEntityId().remove(k);
                    entityIdListSize--;
                    k--;
                }

            }
        }
        return discContResp;
    }

    public DiscoveryContextAvailabilityResponse removeSharedEntityType(
            DiscoveryContextAvailabilityResponse discContResp, String eType) {
        // TODO Auto-generated method stub

        int contRegRespListSize = discContResp
                .getContextRegistrationResponseList()
                .getContextRegistrationResponse().size();
        for (int j = 0; j < contRegRespListSize; j++) {

            int entityIdListSize = discContResp
                    .getContextRegistrationResponseList()
                    .getContextRegistrationResponse().get(j)
                    .getContextRegistration().getEntityIdList().getEntityId()
                    .size();
            for (int k = 0; k < entityIdListSize; k++) {

                String eTypeCheck = discContResp
                        .getContextRegistrationResponseList()
                        .getContextRegistrationResponse().get(j)
                        .getContextRegistration().getEntityIdList()
                        .getEntityId().get(k).getType();
                if (!eTypeCheck.equals(eType)) {
                    discContResp.getContextRegistrationResponseList()
                            .getContextRegistrationResponse().get(j)
                            .getContextRegistration().getEntityIdList()
                            .getEntityId().remove(k);
                    entityIdListSize--;
                    k--;
                }
            }
        }
        return discContResp;
    }

    public DiscoveryContextAvailabilityResponse removeSharedAttribute(
            DiscoveryContextAvailabilityResponse discContResp, ArrayList<String> discAttributeList) {

        int contRegRespListSize = discContResp
                .getContextRegistrationResponseList()
                .getContextRegistrationResponse().size();
        for (int j = 0; j < contRegRespListSize; j++) {

            int attributeListSize = discContResp
                    .getContextRegistrationResponseList()
                    .getContextRegistrationResponse().get(j)
                    .getContextRegistration()
                    .getContextRegistrationAttributeList()
                    .getContextRegistrationAttribute().size();

            for (int k = 0; k < attributeListSize; k++) {

                String attributeCheck = discContResp
                        .getContextRegistrationResponseList()
                        .getContextRegistrationResponse().get(j)
                        .getContextRegistration().getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute()
                        .get(k).getName();
//                if (!attributeCheck.equals(attribute)) {
                if (!discAttributeList.contains(attributeCheck)) {
                    //does attribute array contain this attribute from result
                    discContResp.getContextRegistrationResponseList()
                            .getContextRegistrationResponse().get(j)
                            .getContextRegistration().getContextRegistrationAttributeList()
                            .getContextRegistrationAttribute().remove(k);
                    attributeListSize--;
                    k--;
                }
            }
        }
        return discContResp;
    }

    public DiscoveryContextAvailabilityResponse removeSharedAttrDomain(
            DiscoveryContextAvailabilityResponse discContResp, String attribute) {

        int contRegRespListSize = discContResp
                .getContextRegistrationResponseList()
                .getContextRegistrationResponse().size();
        for (int j = 0; j < contRegRespListSize; j++) {

            int attrDomainListSize = discContResp
                    .getContextRegistrationResponseList()
                    .getContextRegistrationResponse().get(j)
                    .getContextRegistration()
                    .getContextRegistrationAttributeList()
                    .getContextRegistrationAttribute().size();

            for (int k = 0; k < attrDomainListSize; k++) {

                String attributeCheck = discContResp
                        .getContextRegistrationResponseList()
                        .getContextRegistrationResponse().get(j)
                        .getContextRegistration().getContextRegistrationAttributeList().getContextRegistrationAttribute()
                        .get(k).getName();
                boolean attrDomainCheck = discContResp
                        .getContextRegistrationResponseList()
                        .getContextRegistrationResponse().get(j)
                        .getContextRegistration().getContextRegistrationAttributeList().getContextRegistrationAttribute()
                        .get(k).isIsDomain();
                if (!(attributeCheck.equals(attribute) && attrDomainCheck)) {
                    discContResp.getContextRegistrationResponseList()
                            .getContextRegistrationResponse().get(j)
                            .getContextRegistration().getEntityIdList()
                            .getEntityId().remove(k);
                    attrDomainListSize--;
                    k--;
                }
            }
        }
        return discContResp;
    }

    public void listResult(List<RegisterContextRequest> result) {
        System.out.println(result.size());
        for (int i = 1; i <= result.size(); i++) {
            System.out.println(result.get(i).getContextRegistrationList()
                    .getContextRegistration().get(i - 1).getEntityIdList()
                    .getEntityId().get(i - 1).getId());
        }
    }

    // public List<RegisterContextRequest> retrieveRegReqByAttribute(
    // final String attribute) {
    //
    // List<RegisterContextRequest> results;
    //
    // results = db.query(new Predicate<RegisterContextRequest>() {
    // public boolean match(RegisterContextRequest req) {
    //
    // final int contRegListSize = req.getContextRegistrationList()
    // .getContextRegistration().size();
    // System.out.println("contRegListSize: " + contRegListSize);
    // for (int i = 0; i < contRegListSize; i++) {
    //
    // final int attributeIdListSize = req
    // .getContextRegistrationList()
    // .getContextRegistration().get(i)
    // .getContextRegistrationAttributeList()
    // .getContextRegistrationAttribute().size();
    // System.out.println("attributeIdListSize: "
    // + attributeIdListSize);
    //
    // for (int j = 0; j < attributeIdListSize; j++) {
    //
    // if (req.getContextRegistrationList()
    // .getContextRegistration().get(i)
    // .getContextRegistrationAttributeList()
    // .getContextRegistrationAttribute().get(j)
    // .getName().equals(attribute))
    // return true;
    // }
    // }
    // return true;
    // }
    // });
    //
    // return results;
    // }
    //
    // public List<RegisterContextRequest> retrieveRegReqByAttrDomain(
    // final String attrDomain) {
    //
    // List<RegisterContextRequest> results;
    //
    // results = db.query(new Predicate<RegisterContextRequest>() {
    // public boolean match(RegisterContextRequest req) {
    //
    // final int contRegListSize = req.getContextRegistrationList()
    // .getContextRegistration().size();
    // System.out.println("contRegListSize: " + contRegListSize);
    // for (int i = 0; i < contRegListSize; i++) {
    //
    // final int attributeIdListSize = req
    // .getContextRegistrationList()
    // .getContextRegistration().get(i)
    // .getContextRegistrationAttributeList()
    // .getContextRegistrationAttribute().size();
    // System.out.println("attributeIdListSize: "
    // + attributeIdListSize);
    //
    // for (int j = 0; j < attributeIdListSize; j++) {
    //
    // if (req.getContextRegistrationList()
    // .getContextRegistration().get(i)
    // .getContextRegistrationAttributeList()
    // .getContextRegistrationAttribute().get(j)
    // .getName().equals(attrDomain)
    // && req.getContextRegistrationList()
    // .getContextRegistration().get(i)
    // .getContextRegistrationAttributeList()
    // .getContextRegistrationAttribute()
    // .get(j).isIsDomain())
    // return true;
    // }
    // }
    // return true;
    // }
    // });
    //
    // return results;
    // }
    //
    //	public List<RegisterContextRequest> retrieveRegReqByEId(
    //			final String entityID, final RegisterContextRequest rcrNew) {
    //
    //		List<RegisterContextRequest> results;
    //		final String registrationId = rcrNew.getRegistrationId();
    //
    //		results = db.query(new Predicate<RegisterContextRequest>() {
    //			public boolean match(RegisterContextRequest req) {
    //
    //				final int contRegListSize = req.getContextRegistrationList()
    //						.getContextRegistration().size();
    //				System.out.println("contRegListSize: " + contRegListSize);
    //				for (int i = 0; i < contRegListSize; i++) {
    //
    //					final int entityIdListSize = req
    //							.getContextRegistrationList()
    //							.getContextRegistration().get(i).getEntityIdList()
    //							.getEntityId().size();
    //					System.out.println("entityidListSize: " + entityIdListSize);
    //
    //					for (int j = 0; j < entityIdListSize; j++) {
    //
    //						if (req.getContextRegistrationList()
    //								.getContextRegistration().get(i)
    //								.getEntityIdList().getEntityId().get(j).getId()
    //								.equals(entityID)&&req.getRegistrationId().equals(registrationId))
    //							return true;
    //					}
    //				}
    //				return true;
    //			}
    //		});
    //
    //		RegisterContextRequest rcrOld = results.get(0);
    //
    //		return results;
    //	}
}
