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
import blackboard.data.user.UserRole;
import blackboard.persist.user.UserRoleDbLoader;
import blackboard.persist.user.UserRoleDbPersister;
import blackboard.persist.user.impl.UserRoleDbLoaderImpl;
import blackboard.persist.user.impl.UserRoleDbPersisterImpl;

import blackboard.data.user.User;
import blackboard.persist.user.UserDbLoader;
import blackboard.data.role.PortalRole;

//import blackboard.admin.data.role.PortalRoleMembership;
//import blackboard.admin.persist.role.impl.PortalRoleMembershipDbLoader;
//import blackboard.admin.persist.role.PortalRoleMembershipLoader;
//import blackboard.admin.persist.role.impl.PortalRoleMembershipDbPersister;
//import blackboard.admin.persist.role.PortalRoleMembershipPersister;

//import blackboard.admin.data.user.Person;

import blackboard.persist.Id;
import blackboard.admin.data.IAdminObject;
import blackboard.admin.data.IAdminObject.RowStatus;
import blackboard.base.BbEnum;
import blackboard.persist.BbPersistenceManager;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.db.BbDatabase;
import blackboard.persist.DatabaseContainer;
import blackboard.persist.impl.SimpleSelectQuery;
import blackboard.persist.impl.NewBaseDbLoader;
import blackboard.persist.user.impl.UserRoleDbMap;
import blackboard.persist.KeyNotFoundException;


import java.util.*;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;


//public class UserAccessPack <BbUserType extends User, WsObjectType extends BbWsDataDetails>
public class PortalRoleMembershipAccessPack_DATA<BbPortalRoleMembershipType extends UserRole,
            ArgumentsType extends PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA>
        extends PortalRoleMembershipAccessPack<BbPortalRoleMembershipType, ArgumentsType> {

    protected void checkNotNullUserAndPortalRoleIds() throws Exception {
        if (getArgs().getInputRecord().getUserId() == null) {
            throw new blackboard.persist.KeyNotFoundException ("UserId cannot be null for " + getClass().getName() + ".{accessRecordByUserIdAndPortalRoleId()}.");
        }
        if (getArgs().getInputRecord().getPortalRoleId() == null) {
            throw new blackboard.persist.KeyNotFoundException ("PortalRoleId cannot be null for " + getClass().getName() + ".{accessRecordByUserIdAndPortalRoleId()}.");
        }
    }
    
    public static class PortalRoleMembershipLoadByIdPack 
                extends PortalRoleMembershipAccessPack_DATA<UserRole,
                PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA> {

        private class DbConnectivityPrivilege implements PrivilegedExceptionAction {
            private SimpleSelectQuery query;
            DbConnectivityPrivilege(SimpleSelectQuery query) {
                super();
                this.query = query;
            }
            public Object run() throws Exception {
                query.executeQuery(null);
                return null;
            }
        }
        protected void loadRecordById() throws Exception {
            
            Id id = checkAndgenerateId(User.DATA_TYPE,getArgs().getInputRecord().getBbId());
            //UserRoleDbLoader includes query.addWhere("RowStatus", Integer.valueOf(0)); in every method
            //bbObject = (UserRoleDbLoader.Default.getInstance().loadById(id));            
            //  NewBaseDbLoader.loadObject() is final protected... 
            //blackboard.persist.impl.SimpleSelectQuery query = new blackboard.persist.impl.SimpleSelectQuery(blackboard.persist.user.impl.UserRoleDbMap.MAP);
            //query.addWhere("id", id);
            //bbObject = (UserRole)(UserRoleDbLoader.Default.getInstance().loadObject(query, null));
            //**********************
            //Below is shrinked rewrite of NewBaseDbLoader.loadObject(), exception incapsulation/rethrowing of exceptions was not ported to code below
            BbPersistenceManager pm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
            BbDatabase db = ((DatabaseContainer)pm.getContainer()).getBbDatabase();
            SimpleSelectQuery query = new SimpleSelectQuery(UserRoleDbMap.MAP);
            query.addWhere("id", id);
            try {
                query.init(db, pm.getContainer());
                AccessController.doPrivileged(new DbConnectivityPrivilege(query));
                bbObject = (UserRole)query.getResult();
                if(bbObject == null) {
                    String msg = blackboard.platform.intl.BundleManagerFactory.getInstance().getBundle("platform").getString("db.persist.err.not.found", new Object[0]);
                    throw new KeyNotFoundException(msg);
                }
            } finally {
                query.close();
            }
        }
        protected void loadRecordByUserIdAndPortalRoleId() throws Exception {
            checkNotNullUserAndPortalRoleIds();
            Id user_id = checkAndgenerateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            Id prole_id = checkAndgenerateId(PortalRole.DATA_TYPE,getArgs().getInputRecord().getPortalRoleId());
            bbObject = UserRoleDbLoader.Default.getInstance().loadByUserIdAndPortalRoleId(user_id, prole_id);
        }
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
        @Override protected void loadRecordByAltId() throws Exception {
            loadRecordByUserIdAndPortalRoleId();
        }
        @Override protected void insertRecord() throws Exception {
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }

        @Override protected void updateRecord() throws Exception {
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }
        @Override protected void deleteRecord() throws Exception {
            Id id = checkAndgenerateId(User.DATA_TYPE,getArgs().getInputRecord().getBbId());
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.deleteById(id);
        }
    }

    public static class LoadRecordById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class LoadRecordByUserIdAndPortalRoleId extends PortalRoleMembershipLoadByIdPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByUserIdAndPortalRoleId();
        }
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class InsertRecordById extends PortalRoleMembershipLoadByIdPack {
        /*@Override public void access() throws Exception {
            bbObject = new UserWithPwd();
            super.access();
        }*/
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class PersistRecordById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class DeleteRecordById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class DeleteRecordByUserIdAndPortalRoleId extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
        @Override protected void deleteRecord() throws Exception {
            checkNotNullUserAndPortalRoleIds();
            Id user_id = checkAndgenerateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            Id prole_id = checkAndgenerateId(PortalRole.DATA_TYPE,getArgs().getInputRecord().getPortalRoleId());
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.deleteByUserIdAndInstitutionRoleId(user_id, prole_id);
        }
        
    }


    public static class LoadListById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class InsertListById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class PersistListById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class DeleteListById extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class DeleteListByUserId extends PortalRoleMembershipLoadByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
        @Override protected void deleteRecord() throws Exception {
            Id user_id = checkAndgenerateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.deleteAllByUserId(user_id);
        }
    }




    public static class PortalRoleMembershipListLoadPack
        extends PortalRoleMembershipAccessPack_DATA<UserRole,PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA> {

        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }
    public static class LoadListByUserId extends PortalRoleMembershipListLoadPack {
        @Override protected void loadList () throws Exception {
            if (getArgs().getInputRecord().getUserId() == null) {
                throw new blackboard.persist.KeyNotFoundException ("UserId cannot be null for " + getClass().getName() + ".loadList().");
            }
            Id user_id = checkAndgenerateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            bbObjectList
                    = UserRoleDbLoader.Default.getInstance().loadByUserId(user_id);
        }
    }

    public static class LoadListByPortalRoleId extends PortalRoleMembershipListLoadPack {
        @Override protected void loadList () throws Exception {
            if (getArgs().getInputRecord().getPortalRoleId() == null) {
                throw new blackboard.persist.KeyNotFoundException ("PortalRoleId cannot be null for " + getClass().getName() + ".loadList().");
            }
            Id prole_id = checkAndgenerateId(PortalRole.DATA_TYPE,getArgs().getInputRecord().getPortalRoleId());
            bbObjectList
                    = UserRoleDbLoader.Default.getInstance().loadByPortalRoleId(prole_id);
        }
    }



    @Override protected void setBbFields() throws Exception {
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                            bbObject.setId(checkAndgenerateId(UserRole.DATA_TYPE, newValue));
                }
            }.setBbField("bbId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getUserId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUserId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setUserId(checkAndgenerateId(User.DATA_TYPE, newValue));
                }
            }.setBbField("userId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getPortalRoleId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPortalRoleId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setPortalRoleId(checkAndgenerateId(PortalRole.DATA_TYPE, newValue));
                }
            }.setBbField("portalRoleId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return RowStatus.getValues()[bbObject.getBbAttributes().getInteger("RowStatus")].toFieldName();
                    //return bbObject.getBbAttributes().getBbEnum("RowStatus").toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getRowStatus();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    int irs = BbWsUtil.convertBbEnum2Int((RowStatus)RowStatus.fromFieldName(newValue, RowStatus.class));
                    bbObject.getBbAttributes().setInteger("RowStatus", irs);
                }
            }.setBbField("rowStatus");
    }
/*        
 *          public void setRowStatus(RowStatus rowStatus) {
            _bbAttributes.setBbEnum("RowStatus", rowStatus);
        }
        public BbEnum getRowStatus(RowStatus rowStatus) {
            return _bbAttributes.getBbEnum("RowStatus");
        }

"            new BbFieldSetter() {
* 
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getRowStatus().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getRowStatus();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setRowStatus((IAdminObject.RowStatus)IAdminObject.RowStatus.fromFieldName(newValue, IAdminObject.RowStatus.class));
                }
            }.setBbField(""rowStatus"");

"
*/        

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
                    return bbObject.getUserId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserId(newValue);
                }
            }.setWsField("userId");		 
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getPortalRoleId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getPortalRoleId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPortalRoleId(newValue);
                }
            }.setWsField("portalRoleId");		        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.STANDARD) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return RowStatus.getValues()[bbObject.getBbAttributes().getInteger("RowStatus")].toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getRowStatus();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setRowStatus(newValue);
                }
            }.setWsField("rowStatus");		
        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.EXTENDED) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    User u = (UserDbLoader.Default.getInstance().loadById(bbObject.getUserId()));
                    return u.getBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserBatchUid(newValue);
                }
            }.setWsField("userBatchUid");		
        }
    }
}
