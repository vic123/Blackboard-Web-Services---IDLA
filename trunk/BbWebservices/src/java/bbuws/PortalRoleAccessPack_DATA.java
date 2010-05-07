/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
 */
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.persist.Id;
import blackboard.data.role.PortalRole;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.persist.role.PortalRoleDbPersister;


public class PortalRoleAccessPack_DATA <BbPortalRoleType extends PortalRole,
        ArgumentsType extends PortalRoleAccessPack.PortalRoleArguments>
        extends PortalRoleAccessPack<BbPortalRoleType, ArgumentsType> {


    protected Id generateInputPortalRoleId() throws Exception {
        return PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(
                PortalRole.DATA_TYPE,getArgs().getInputRecord().getBbId());
    }

//************************* RoleLoadRecordPack ***********************
    public static class RoleLoadRecordPack
            extends PortalRoleAccessPack_DATA<PortalRole, PortalRoleArguments> {
        public void initialize(PortalRoleArguments args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null); 
            super.initialize(args, PortalRole.class, da);
        }
        protected void loadRecordByRoleName() throws Exception {
            bbObject = PortalRoleDbLoader.Default.getInstance().loadByRoleName(getArgs().getInputRecord().getRoleName());
        }
        protected void loadRecordDefault() throws Exception {
            bbObject = PortalRoleDbLoader.Default.getInstance().loadDefault();
        }
    }
    public static class LoadRecordByRoleName extends RoleLoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByRoleName();
        }
    }
    public static class LoadRecordDefault extends RoleLoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordDefault();
        }
    }

//************************* RoleLoadListPack ***********************
    public static class RoleLoadListPack
            extends PortalRoleAccessPack_DATA<PortalRole, PortalRoleArguments> {
        public void initialize(PortalRoleArguments args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
        protected void loadListRemovable () throws Exception {
            bbObjectList = PortalRoleDbLoader.Default.getInstance().loadRemovable();
        }
        protected void loadListAll () throws Exception {
            bbObjectList = PortalRoleDbLoader.Default.getInstance().loadAll();
        }
        protected void loadListAvailable () throws Exception {
            bbObjectList = PortalRoleDbLoader.Default.getInstance().loadAvailableRolesAll();
        }
    }
    public static class LoadListRemovable extends RoleLoadListPack {
        @Override protected void loadList() throws Exception {
            loadListRemovable();
        }
    }
    public static class LoadListAll extends RoleLoadListPack {
        @Override protected void loadList() throws Exception {
            loadListAll();
        }
    }
    public static class LoadListAvailable extends RoleLoadListPack {
        @Override protected void loadList() throws Exception {
            loadListAvailable();
        }
    }


//************************************************
    public static class LoadRecordPrimaryRoleByUserId
            extends PortalRoleAccessPack_DATA<PortalRole, PortalRoleArgumentsWithUserInput> {
        public void initialize(PortalRoleArgumentsWithUserInput args) {
            RecordLoader  da = new RecordLoader();
            da.initialize(null); 
            super.initialize(args, PortalRole.class, da);
        }
        @Override protected void loadRecord () throws Exception {
            String str_id = getArgs().getInputUserRecord().getBbId();
            Id id = PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(
                        blackboard.data.user.User.DATA_TYPE, str_id);
            bbObject = PortalRoleDbLoader.Default.getInstance().loadPrimaryRoleByUserId(id);
        }
    }

//************************* RoleLoadListByUserIdPack ***********************
    public static class RoleLoadListByUserIdPack
            extends PortalRoleAccessPack_DATA<PortalRole, PortalRoleArgumentsWithUserInput> {
        public void initialize(PortalRoleArgumentsWithUserInput args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
        protected void loadListAllByUserId () throws Exception {
            String str_id = getArgs().getInputUserRecord().getBbId();
            Id id = PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(
                        blackboard.data.user.User.DATA_TYPE, str_id);
            bbObjectList = PortalRoleDbLoader.Default.getInstance().loadAllByUserId(id);
        }
        protected void loadListSecondaryRolesByUserId () throws Exception {
            String str_id = getArgs().getInputUserRecord().getBbId();
            Id id = PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(
                        blackboard.data.user.User.DATA_TYPE, str_id);
            bbObjectList = PortalRoleDbLoader.Default.getInstance().loadSecondaryRolesByUserId(id);
        }
    }
    public static class LoadListAllByUserId extends RoleLoadListByUserIdPack {
        @Override protected void loadList() throws Exception {
            loadListAllByUserId();
        }
    }
    public static class LoadListSecondaryRolesByUserId extends RoleLoadListByUserIdPack {
        @Override protected void loadList() throws Exception {
            loadListSecondaryRolesByUserId();
        }
    }


//************************* RoleAccessByIdPack ***********************
    public static class RoleAccessByIdPack
            extends PortalRoleAccessPack_DATA<PortalRole, PortalRoleArguments> {
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
        @Override protected void loadRecordByAltId() throws Exception {
            loadRecordByRoleId();
        }

        protected void loadRecordById()  throws Exception {
            checkNotNullId();
            Id id = generateInputPortalRoleId();
            bbObject = PortalRoleDbLoader.Default.getInstance().loadById(id);
        }
        protected void loadRecordByRoleId()  throws Exception {
            bbObject = PortalRoleDbLoader.Default.getInstance().loadByRoleId(getArgs().getInputRecord().getRoleId());
        }

        @Override protected void insertRecord() throws Exception {
            PortalRoleDbPersister uper = PortalRoleDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }
        @Override protected void updateRecord() throws Exception {
            PortalRoleDbPersister uper = PortalRoleDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }
        @Override protected void deleteRecord() throws Exception {
            checkNotNullId();
            Id id = generateInputPortalRoleId();
            PortalRoleDbPersister uper = PortalRoleDbPersister.Default.getInstance();
            uper.deleteById(id);
        }
    }
    public static class LoadRecordById extends RoleAccessByIdPack {
        public void initialize(PortalRoleArguments args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
    }
    public static class LoadRecordByRoleId extends RoleAccessByIdPack {
        public void initialize(PortalRoleArguments args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
        @Override protected void loadRecord() throws Exception {
            loadRecordByRoleId();
        }
    }

    public static class InsertRecordById extends RoleAccessByIdPack {
        public void initialize(PortalRoleArguments args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
    }
    public static class UpdateRecordById extends RoleAccessByIdPack {
        public void initialize(PortalRoleArguments args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
    }
    public static class PersistRecordById extends RoleAccessByIdPack {
        public void initialize(PortalRoleArguments args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
    }
    public static class DeleteRecordById extends RoleAccessByIdPack {
        public void initialize(PortalRoleArguments args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, PortalRole.class, da);
        }
    }
}
