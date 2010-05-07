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

//import blackboard.data.user.User;
//import blackboard.data.Identifiable;
//import blackboard.data.user.UserRole;

import blackboard.admin.data.role.PortalRoleMembership;
import blackboard.admin.persist.role.impl.PortalRoleMembershipDbLoader;
import blackboard.admin.persist.role.PortalRoleMembershipLoader;
import blackboard.admin.persist.role.impl.PortalRoleMembershipDbPersister;
import blackboard.admin.persist.role.PortalRoleMembershipPersister;

import blackboard.admin.data.user.Person;
import blackboard.data.role.PortalRole;
import blackboard.persist.Id;
import blackboard.admin.data.IAdminObject;

import java.util.*;

//public class UserAccessPack <BbUserType extends User, WsObjectType extends BbWsDataDetails>
public class PortalRoleMembershipAccessPack_ADMIN_DATA
        extends PortalRoleMembershipAccessPack_DATA
            <PortalRoleMembership, PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA> {


    /*
     * load by template method of Bb 9.0 PortalRoleMembership API does not generate valid queries to database
     * [TEST\SQLEXPRESS]Incorrect syntax near the keyword 'AND'. - when searched by userId
     * com.inet.tds.be: Column 'p_person_batch_uid' not found. - when searched by userBatchUid
     */
    public static class LoadListByTemplate extends
            PortalRoleMembershipAccessPack_ADMIN_DATA {
        public void initialize(PortalRoleMembershipAccessPack_ADMIN_DATA.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, PortalRoleMembership.class, da);
        }
        @Override protected void loadList() throws Exception {
            bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
            setBbFields();
            bbObjectList = PortalRoleMembershipDbLoader.Default.getInstance().load(bbObject);
        }
    }

    public static class PortalRoleMembershipRecordAccessPack extends PortalRoleMembershipAccessPack_ADMIN_DATA {
        @Override protected void loadRecord() throws Exception {
            String u_bid = getArgs().getInputRecord().getUserBatchUid();
            String pr_bid = getArgs().getInputRecord().getPortalRoleBatchUid();
            bbObject = PortalRoleMembershipDbLoader.Default.getInstance().load(u_bid, pr_bid);
        }
        @Override protected void loadRecordByAltId() throws Exception {
            loadRecord();
        }
        @Override protected void insertRecord() throws Exception {
            PortalRoleMembershipPersister dbper = PortalRoleMembershipDbPersister.Default.getInstance();
            dbper.insert(bbObject);
        }
        @Override protected void deleteRecord() throws Exception {
            if (bbObject == null) bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
            setBbFields();
            PortalRoleMembershipPersister dbper = PortalRoleMembershipPersister.Default.getInstance();
            dbper.remove(bbObject);
        }
    }


    public static class LoadRecordByUserAndPortalRoleBatchUid extends
            PortalRoleMembershipRecordAccessPack {
        public void initialize(PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, PortalRoleMembership.class, da);
        }
    }
    public static class InsertRecordByUserAndPortalRoleBatchUid extends
            PortalRoleMembershipRecordAccessPack {
        public void initialize(PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, PortalRoleMembership.class, da);
        }
    }

    public static class DeleteRecordByUserAndPortalRoleBatchUid extends
            PortalRoleMembershipRecordAccessPack {
        public void initialize(PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, PortalRoleMembership.class, da);
        }
    }

    public static class LoadListByUserAndPortalRoleBatchUid extends
            PortalRoleMembershipRecordAccessPack {
        public void initialize(PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, PortalRoleMembership.class, ilp);
        }
    }
    public static class InsertListByUserAndPortalRoleBatchUid extends
            PortalRoleMembershipRecordAccessPack {
        public void initialize(PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, PortalRoleMembership.class, ilp);
        }
    }

    public static class DeleteListByUserAndPortalRoleBatchUid extends
            PortalRoleMembershipRecordAccessPack {
        public void initialize(PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, PortalRoleMembership.class, ilp);

        }
    }


    @Override protected void setBbFields() throws Exception {
            super.setBbFields();
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getPersonBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUserBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setPersonBatchUid(newValue);
                }
            }.setBbField("userBatchUid");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getPortalRoleBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPortalRoleBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setPortalRoleBatchUid(newValue);
                }
            }.setBbField("portalRoleBatchUid");
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

            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getRecStatus().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getRecStatus();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setRecStatus((IAdminObject.RecStatus)IAdminObject.RecStatus.fromFieldName(newValue, IAdminObject.RecStatus.class));
                }
            }.setBbField("recStatus");
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
                    return bbObject.getPersonBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserBatchUid(newValue);
                }
            }.setWsField("userBatchUid");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getPortalRoleBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getPortalRoleBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPortalRoleBatchUid(newValue);
                }
            }.setWsField("portalRoleBatchUid");
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
            }.setWsField("rowStatus");

            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getRecStatus().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getRecStatus();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setRecStatus(newValue);
                }
            }.setWsField("recStatus");
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
