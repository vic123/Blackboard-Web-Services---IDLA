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
import blackboard.data.role.PortalRole;

//import blackboard.admin.data.role.PortalRoleMembership;
//import blackboard.admin.persist.role.impl.PortalRoleMembershipDbLoader;
//import blackboard.admin.persist.role.PortalRoleMembershipLoader;
//import blackboard.admin.persist.role.impl.PortalRoleMembershipDbPersister;
//import blackboard.admin.persist.role.PortalRoleMembershipPersister;

//import blackboard.admin.data.user.Person;

import blackboard.persist.Id;
import blackboard.admin.data.IAdminObject;

import java.util.*;

//public class UserAccessPack <BbUserType extends User, WsObjectType extends BbWsDataDetails>
public class PortalRoleMembershipAccessPack_DATA<BbPortalRoleMembershipType extends UserRole,
            ArgumentsType extends PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA>
        extends PortalRoleMembershipAccessPack<BbPortalRoleMembershipType, ArgumentsType> {

    public static class PortalRoleMembershipAccessByIdPack 
                extends PortalRoleMembershipAccessPack_DATA<UserRole,PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA> {
        protected void loadRecordById() throws Exception {
            checkNotNullId();
            //Id id = PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(User.DATA_TYPE,getArgs().getInputRecord().getBbId());
            Id id = Id.generateId(UserRole.DATA_TYPE,getArgs().getInputRecord().getBbId());
            bbObject = (UserRoleDbLoader.Default.getInstance().loadById(id));
        }
        protected void checkNotNullUserAndPortalRoleIds() throws Exception {
            if (getArgs().getInputRecord().getUserId() == null) {
                throw new blackboard.persist.KeyNotFoundException ("UserId cannot be null for " + getClass().getName() + ".{accessRecordByUserIdAndPortalRoleId()}.");
            }
            if (getArgs().getInputRecord().getPortalRoleId() == null) {
                throw new blackboard.persist.KeyNotFoundException ("PortalRoleId cannot be null for " + getClass().getName() + ".{accessRecordByUserIdAndPortalRoleId()}.");
            }
        }
        protected void loadRecordByUserIdAndPortalRoleId() throws Exception {
            checkNotNullUserAndPortalRoleIds();
            Id user_id = Id.generateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            Id prole_id = Id.generateId(PortalRole.DATA_TYPE,getArgs().getInputRecord().getPortalRoleId());
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
            checkNotNullId();
            Id id = Id.generateId(UserRole.DATA_TYPE,getArgs().getInputRecord().getBbId());
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.deleteById(id);
        }

    }

    public static class LoadRecordById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class LoadRecordByUserIdAndPortalRoleId extends PortalRoleMembershipAccessByIdPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByUserIdAndPortalRoleId();
        }
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class InsertRecordById extends PortalRoleMembershipAccessByIdPack {
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

    public static class PersistRecordById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class DeleteRecordById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
    }

    public static class DeleteRecordByUserIdAndPortalRoleId extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
        @Override protected void deleteRecord() throws Exception {
            checkNotNullUserAndPortalRoleIds();
            Id user_id = Id.generateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            Id prole_id = Id.generateId(PortalRole.DATA_TYPE,getArgs().getInputRecord().getPortalRoleId());
            UserRoleDbPersister uper = UserRoleDbPersister.Default.getInstance();
            uper.deleteByUserIdAndInstitutionRoleId(user_id, prole_id);
        }
        
    }


    public static class LoadListById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class InsertListById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class PersistListById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class DeleteListById extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, UserRole.class, ilp);
        }
    }

    public static class DeleteListByUserId extends PortalRoleMembershipAccessByIdPack {
        public void initialize(PortalRoleMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, UserRole.class, da);
        }
        @Override protected void deleteRecord() throws Exception {
            Id user_id = Id.generateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
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
            Id user_id = Id.generateId(User.DATA_TYPE,getArgs().getInputRecord().getUserId());
            bbObjectList
                    = UserRoleDbLoader.Default.getInstance().loadByUserId(user_id);
        }
    }

    public static class LoadListByPortalRoleId extends PortalRoleMembershipListLoadPack {
        @Override protected void loadList () throws Exception {
            if (getArgs().getInputRecord().getPortalRoleId() == null) {
                throw new blackboard.persist.KeyNotFoundException ("PortalRoleId cannot be null for " + getClass().getName() + ".loadList().");
            }
            Id prole_id = Id.generateId(PortalRole.DATA_TYPE,getArgs().getInputRecord().getPortalRoleId());
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
                            bbObject.setId(Id.generateId(UserRole.DATA_TYPE, newValue));
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
                        bbObject.setUserId(Id.generateId(User.DATA_TYPE, newValue));
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
                        bbObject.setPortalRoleId(Id.generateId(PortalRole.DATA_TYPE, newValue));
                }
            }.setBbField("portalRoleId");
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
    }
}
