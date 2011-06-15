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
import blackboard.persist.user.UserDbLoader;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.user.PersonPersister;
import blackboard.admin.persist.user.impl.PersonDbLoader;
import blackboard.admin.persist.user.impl.PersonDbPersister;
import blackboard.admin.data.IAdminObject;


public class ObserverAssociationAccessPack_ADMIN_DATA extends ObserverAssociationAccessPack<ObserverAssociation> {


    protected void setSafeResultRecordIds() {
        super.setSafeResultRecordIds();
        getArgs().getResultRecord().setObserverBbId(getArgs().getInputRecord().getObserverBbId());
        getArgs().getResultRecord().setUsersBbId(getArgs().getInputRecord().getUsersBbId());
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
        @Override protected void updateRecord() throws Exception {
            ObserverAssociationPersister dbper = ObserverAssociationDbPersister.Default.getInstance();
            dbper.save(bbObject);
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

    public static class PersistRecordByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordPersister da = new RecordPersister();
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

    public static class PersistListByObserverAndUsersBatchUid extends
            ObserverAssociationRecordAccessPack {
        public void initialize(ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args) {
            RecordPersister da = new RecordPersister();
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
            super.setBbFields();
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
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getRowStatus().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getRowStatus();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setRowStatus((IAdminObject.RowStatus)IAdminObject.RowStatus.fromFieldName(newValue, IAdminObject.RowStatus.class));
                }
            }.setBbField("rowStatus");
/*
  
 new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                     bbObject._bbAttributes.getInteger("RowStatus", 0);                    
                    return bbObject.getRowStatus().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getRowStatus();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    try {
                        bbObject.setRowStatus((IAdminObject.RowStatus)IAdminObject.RowStatus.fromFieldName(newValue, IAdminObject.RowStatus.class));
                    } catch (Exception e) {
                        blackboard.base.BbEnum rs = IAdminObject.RowStatus.fromFieldName(newValue, IAdminObject.RowStatus.class);
                        rs.hashCode()
                        IAdminObject.RowStatus rs_all[] = IAdminObject.RowStatus.getValues();
                        StringBuffer msg = new StringBuffer("");
                        for (int i = 0; i < rs_all.length; i++) {
                            IAdminObject.RowStatus rs = rs_all[i];
                            msg.append(rs.toFieldName() + ": " + String.valueOf(rs.hashCode()) + "; ");
                            throw 
                        }
                    }
                }
            }.setBbField("rowStatus");            
   
 *          
 */ 
    }

    @Override protected void setWsFields() throws Exception {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBbId(newValue);
                }
            }.setWsField("bbId");	 	
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.MINIMAL) >= 0) {
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
            }.setWsField("usersBatchUid");		        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.STANDARD) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getRowStatus().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getRowStatus();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setRowStatus(newValue);
                }
            }.setWsField("rowStatus");		        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.EXTENDED) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    User u = (UserDbLoader.Default.getInstance().loadByBatchUid(bbObject.getObserverBatchUid()));
                    return u.getId().getExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getObserverBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setObserverBbId(newValue);
                }
            }.setWsField("observerBbId");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    User u = (UserDbLoader.Default.getInstance().loadByBatchUid(bbObject.getUsersBatchUid()));
                    return u.getId().getExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUsersBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUsersBbId(newValue);
                }
            }.setWsField("usersBbId");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDataSourceId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDataSourceId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDataSourceId(newValue);
                }
            }.setWsField("dataSourceId");		
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
