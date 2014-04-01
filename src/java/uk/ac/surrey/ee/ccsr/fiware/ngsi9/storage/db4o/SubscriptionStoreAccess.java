package uk.ac.surrey.ee.ccsr.fiware.ngsi9.storage.db4o;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import eu.fiware.iot.ngsi.*;
import java.io.File;
import java.util.ArrayList;
import javax.servlet.ServletContext;

public class SubscriptionStoreAccess {

    public ObjectContainer db;
    public static String DB4OFILENAME = "";

    public SubscriptionStoreAccess() {
    }

    public SubscriptionStoreAccess(ServletContext context) {

        DB4OFILENAME = context.getRealPath("/repository/ngsiSubRepo.db4o");
        //user.home

    }

    public void openDb4o() {

        System.out.println(DB4OFILENAME);
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(15); // set activation depth
        configuration.common().objectClass(RegisterContextRequest.class).cascadeOnDelete(true);
        db = Db4oEmbedded.openFile(configuration, DB4OFILENAME);
    }

    public void closeDb4o() {
        db.close();

    }

    public void deleteDb() {

        new File(DB4OFILENAME).delete();
        System.out.println("db deleted: " + DB4OFILENAME);
    }

    public void storeSubscription(SubscribeContextAvailabilityRequest subsReq) {

        openDb4o();

        try {
            db.store(subsReq);
        } finally {
            db.close();
            System.out.println("Stored SubscribeContextAvailabilityRequest: " + subsReq.getSubscriptionId());
        }
    }

    public boolean deleteSubscription(final String subsID) {
        List<SubscribeContextAvailabilityRequest> results;
        //final String updateSubId = rcrNew.getSubscriptionId();
        results = db.query(new Predicate<SubscribeContextAvailabilityRequest>() {
            public boolean match(SubscribeContextAvailabilityRequest req) {
                String storedRegID = req.getSubscriptionId();
                if (storedRegID.equals(subsID)) {
                    return true;
                }
                return false;
            }
        });
        if (results.size() > 0) {
            SubscribeContextAvailabilityRequest found = results.get(0);
            System.out.println("Deleting SubscribeContextAvailabilityRequest");
            //found.getReference();
            db.delete(found);
            return true;
        }
        return false;
    }

    public boolean updateSubscription(final UpdateContextAvailabilitySubscriptionRequest subReq) {
        List<SubscribeContextAvailabilityRequest> results;
        //final String updateSubId = rcrNew.getSubscriptionId();
        results = db.query(new Predicate<SubscribeContextAvailabilityRequest>() {
            public boolean match(SubscribeContextAvailabilityRequest req) {
                String storedRegID = req.getSubscriptionId();
                if (storedRegID.equals(subReq.getSubscriptionId())) {
                    return true;
                }
                return false;
            }
        });
        if (results.size() > 0) {
            SubscribeContextAvailabilityRequest found = results.get(0);
            System.out.println("Updating SubscribeContextAvailabilityRequest");

            found.setEntityIdList(subReq.getEntityIdList());
            found.setAttributeList(subReq.getAttributeList());
            found.setRestriction(subReq.getRestriction());
            found.setDuration(subReq.getDuration());

            try {
                db.store(found);
            } finally {
                System.out.println("Stored SubscribeContextAvailabilityRequest: " + found.getSubscriptionId());
            }
            return true;
        }
        return false;
    }

    public List<SubscribeContextAvailabilityRequest> matchSubscriptions(final RegisterContextRequest regReq) {

        List<SubscribeContextAvailabilityRequest> results;

        ArrayList<EntityId> regEIdList = new ArrayList<EntityId>();
        ArrayList<ContextRegistrationAttribute> regAttrList = new ArrayList<ContextRegistrationAttribute>();

        final int contRegListSize = regReq.
                getContextRegistrationList()
                .getContextRegistration()
                .size();
        //System.out.println("contRegListSize: " + contRegListSize);
        for (int i = 0; i < contRegListSize; i++) {

            //get Entities
             int entityIdListSize =  regReq
                    .getContextRegistrationList()
                    .getContextRegistration()
                    .get(i)
                    .getEntityIdList()
                    .getEntityId()
                    .size();            
            
            //System.out.println("entityidListSize: " + entityIdListSize);
            for (int j = 0; j < entityIdListSize; j++) {

                regEIdList.add(regReq.getContextRegistrationList()
                        .getContextRegistration()
                        .get(i)
                        .getEntityIdList()
                        .getEntityId()
                        .get(j));
            }

            //get attributes
            
            
            int attributeListSize = regReq.getContextRegistrationList()
                    .getContextRegistration()
                    .get(i)
                    .getContextRegistrationAttributeList()
                    .getContextRegistrationAttribute()
                    .size();
            for (int k = 0; k < attributeListSize; k++) {
                regAttrList.add(regReq.getContextRegistrationList()
                        .getContextRegistration()
                        .get(i)
                        .getContextRegistrationAttributeList()
                        .getContextRegistrationAttribute()
                        .get(k));
            }
        }

        final ArrayList<EntityId> regEIdListQuery = regEIdList;
        final ArrayList<ContextRegistrationAttribute> regAttrListQuery = regAttrList;
        
        openDb4o();

        //perform query
        results = db.query(new Predicate<SubscribeContextAvailabilityRequest>() {
            public boolean match(SubscribeContextAvailabilityRequest subReq) {

                int subEIdListSize = subReq.getEntityIdList().getEntityId().size();
                int subAttrListSize = subReq.getAttributeList().getAttribute().size();

                boolean eIdMatch = false;
                boolean attrMatch = false;

                for (int i = 0; i < subEIdListSize; i++) {

                    String subEIdType = subReq.getEntityIdList().getEntityId().get(i).getType();
                    boolean subEIdIsPattern = subReq.getEntityIdList().getEntityId().get(i).isIsPattern();
                    String subEId = subReq.getEntityIdList().getEntityId().get(i).getId();

                    for (int j = 0; j < regEIdListQuery.size(); j++) {

                        if (regEIdListQuery.get(j).getType().equals(subEIdType)) {
                            if (regEIdListQuery.get(j).isIsPattern().equals(subEIdIsPattern)) {
                                if (subEIdIsPattern) {
                                    if (regEIdListQuery.get(j).getId().contains(subEId)) {
                                        eIdMatch = true;
                                        break;
                                    }
                                } else {
                                    if (regEIdListQuery.get(j).getId().equals(subEId)) {
                                        eIdMatch = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } //end j loop

                }
                //get attribute!!
                for (int k = 0; k < subAttrListSize; k++) {
                    String attr = subReq.getAttributeList().getAttribute().get(k);
                    for (int m = 0; m < regAttrListQuery.size(); m++) {
                        if (regAttrListQuery.get(k).getName().equals(attr)) {
                            attrMatch = true;
                        }

                    }

                }//end k loop

                if (eIdMatch && attrMatch) {
                    return true;
                } else {
                    System.out.println("No subscription match found");
                    return false;
                }
            }
        });

        return results;

    }//matchSubscriptions()
    
    

}//class
