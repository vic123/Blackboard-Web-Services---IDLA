/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
 */
import bbwscommon.*;
import blackboard.admin.data.user.ObserverAssociation;
import blackboard.admin.persist.user.impl.ObserverAssociationDbLoader;
import blackboard.admin.persist.user.impl.ObserverAssociationDbPersister;
import blackboard.admin.persist.user.ObserverAssociationPersister;
import blackboard.platform.security.DomainManagerFactory;
import blackboard.platform.security.DomainManager;
import blackboard.platform.security.Domain;
import blackboard.data.user.User;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.user.PersonPersister;
import blackboard.admin.persist.user.impl.PersonDbLoader;
import blackboard.admin.persist.user.impl.PersonDbPersister;


public class ObserverAssociationAccessPack_ADMIN_DATA extends ObserverAssociationAccessPack<ObserverAssociation> {


    protected void setResultRecordIds() {
        super.setResultRecordIds();
        if (getArgs().getInputRecord() != null) {
            getArgs().getResultRecord().setObserverBbId(getArgs().getInputRecord().getObserverBbId());
            getArgs().getResultRecord().setUsersBbId(getArgs().getInputRecord().getUsersBbId());
        }
    }

    public static class LoadListByTemplate extends
            ObserverAssociationAccessPack_ADMIN_DATA {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, ObserverAssociation.class, da);
        }
        @Override protected void loadList() throws Exception {
            bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
            setBbFields();
            bbObjectList = ObserverAssociationDbLoader.Default.getInstance().load(bbObject);
        }
    }

    public static class ObserverAssociationRecordAccessPack extends ObserverAssociationAccessPack_ADMIN_DATA {
        @Override protected void loadRecord() throws Exception {
            String c_bid = getArgs().getInputRecord().getObserverBatchUid();
            String u_bid = getArgs().getInputRecord().getUsersBatchUid();
            bbObject = ObserverAssociationDbLoader.Default.getInstance().load(c_bid, u_bid);
        }

        @Override protected void loadRecordByAltId() throws Exception {
            loadRecord();
        }

        @Override protected void insertRecord() throws Exception {
            Person prsn = PersonDbLoader.Default.getInstance().load(bbObject.getObserverBatchUid());
            if (prsn.getSystemRole().compareTo(User.SystemRole.OBSERVER) != 0) {
                blackboard.platform.security.DomainManager dom_mgr = DomainManagerFactory.getInstance();
                Domain default_domain = dom_mgr.getDefaultDomain();
                String sec_roles[] = new String[0];
                String usernames[] = {prsn.getUserName()};
                dom_mgr.saveDomainAdminView(default_domain.getId().toExternalString(),
                        usernames,
                        sec_roles);
                prsn.setSystemRole(User.SystemRole.OBSERVER);
                PersonPersister uper = PersonDbPersister.Default.getInstance();
                uper.update(prsn);
            }
            ObserverAssociationPersister dbper = ObserverAssociationDbPersister.Default.getInstance();
            dbper.insert(bbObject);
        }

        @Override protected void deleteRecord() throws Exception {
            if (bbObject == null) bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
            setBbFields();
            ObserverAssociationPersister dbper = ObserverAssociationPersister.Default.getInstance();
            dbper.remove(bbObject);
        }
    }
    public static class LoadRecordByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, ObserverAssociation.class, da);
        }
    }
    public static class InsertRecordByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, ObserverAssociation.class, da);
        }
    }

    public static class DeleteRecordByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, ObserverAssociation.class, da);
        }
    }
    
    public static class LoadListByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, ObserverAssociation.class, ilp);
        }
    }
    public static class InsertListByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, ObserverAssociation.class, ilp);
        }
    }

    public static class DeleteListByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, ObserverAssociation.class, ilp);

        }
    }

    @Override protected void setBbFields() throws Exception {
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getObserverBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getObserverBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setObserverBatchUid(newValue);
                }
            }.setBbField("observerBatchUid");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getUsersBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUsersBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setUsersBatchUid(newValue);
                }
            }.setBbField("usersBatchUid");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getDataSourceBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getDataSourceBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setDataSourceBatchUid(newValue);
                }
            }.setBbField("dataSourceBatchUid");
    }

    @Override protected void setWsFields() throws Exception {
        super.setWsFields();
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.STANDARD) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getObserverBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getObserverBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setObserverBatchUid(newValue);
                }
            }.setWsField("observerBatchUid");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getUsersBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUsersBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUsersBatchUid(newValue);
                }
            }.setWsField("userBatchUid");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDataSourceBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDataSourceBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDataSourceBatchUid(newValue);
                }
            }.setWsField("dataSourceBatchUid");		        
        }

    }
    
}
