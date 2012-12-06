package bbuws;

import blackboard.admin.data.user.Person;
import blackboard.admin.persist.user.PersonPersister;
import blackboard.admin.persist.user.impl.PersonDbLoader;
import blackboard.admin.persist.user.impl.PersonDbPersister;

import bbwscommon.*; 

public class UserAccessPack_ADMIN_DATA extends UserAccessPack_DATA.UserAccessPackWithUserInput<Person> {

    /*        public void initialize(UserArguments args) {
    RecordLoader rl = new RecordLoader();
    rl.initialize(null);
    super.initialize(args, bbObjectClass, rl);
    }*/

    
    @Override
    protected void loadRecord() throws Exception {
        bbObject = PersonDbLoader.Default.getInstance().load(getArgs().getInputRecord().getBatchUid());
    }
    @Override
    protected void loadRecordByAltId() throws Exception {
        loadRecord();
    }



    @Override
    protected void insertRecord() throws Exception {
        PersonPersister uper = PersonDbPersister.Default.getInstance();
        uper.insert(bbObject);
    }

    @Override
    protected void updateRecord() throws Exception {
        //if (bbObject == null) bbObject = PersonDbLoader.Default.getInstance().load(getArgs().getInputRecord().getUserName());
        setBbFields();
        PersonPersister uper = PersonDbPersister.Default.getInstance();
        uper.update(bbObject);
    }

    /*
    @Override protected void persistRecord() throws Exception {
    loadRecord();
    if (bbObject == null) insertRecord();
    else updateRecord();
    }*/
    @Override
    protected void deleteRecord() throws Exception {
        if (bbObject == null) {
            bbObject = new Person();
        }
        //!! deleteRecord - this way, or through setBbFields()??
        bbObject.setBatchUid(getArgs().getInputRecord().getBatchUid());
        PersonPersister uper = PersonDbPersister.Default.getInstance();
        uper.remove(bbObject);
    }

    public static class LoadRecordByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, Person.class, da);
        }
    }

    public static class LoadListByTemplate extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, Person.class, da);
        }
        @Override protected void loadList () throws Exception {
            if (bbObject == null) bbObject = new Person();
            setBbFields();
            bbObjectList = PersonDbLoader.Default.getInstance().load(bbObject);
        }
    }

    public static class InsertRecordByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            //super.initialize(args, User.class, da);
            super.initialize(args, Person.class, da);
        }
    }

    public static class UpdateRecordByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            //super.initialize(args, User.class, da);
            super.initialize(args, Person.class, da);
        }
    }


    public static class PersistRecordByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            //super.initialize(args, User.class, da);
            super.initialize(args, Person.class, da);
        }
    }
/*??
    public static class SaveRecordByBatchUid extends UserAccessPack_ADMIN_DATA {
        class RecordSaver extends DataAccessor {
 * //this access is wrong, has to be done as persist in DATA, i.e. with use of base classes
            public void access() throws Exception {
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) != 0) {
                    getArgs().setResultRecord(getArgs().getInputRecord());
                } else createWsResultObjectIfNull();
                bbObject = PersonDbLoader.Default.getInstance().load(getArgs().getInputRecord().getUserName());
                if (bbObject == null) bbObject = new Person();
                setBbFields();
                PersonPersister uper = PersonDbPersister.Default.getInstance();
                uper.save(bbObject);
                //appendBoolResultDataLogFromInputRecord();
            }
        }
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordSaver da = new RecordSaver();
            da.initialize(null);
            super.initialize(args, Person.class, da);
        }
    }
*/
    public static class PersistListByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            //super.initialize(args, User.class, ilp);
            super.initialize(args, Person.class, ilp);
        }
    }

    public static class DeleteRecordByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            //super.initialize(args, User.class, da);
            super.initialize(args, Person.class, da);
        }
    }

    public static class DeleteListByBatchUid extends UserAccessPack_ADMIN_DATA {
        public void initialize(UserArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, Person.class, ilp);
        }
    }


}
