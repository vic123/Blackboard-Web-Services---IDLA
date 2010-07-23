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

import blackboard.platform.log.LogService;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.user.impl.UserDbLoaderImpl;

import blackboard.persist.user.UserDbPersister;
import blackboard.persist.user.UserRoleDbPersister;
import blackboard.data.user.User.Gender;
import blackboard.data.user.User.EducationLevel;
import blackboard.persist.Id;
import blackboard.data.user.User;
import blackboard.base.BbList;

import blackboard.admin.data.user.Person;
import blackboard.admin.persist.user.impl.PersonDbPersister;
import blackboard.admin.persist.user.PersonPersister;
import blackboard.admin.persist.user.impl.PersonDbLoader;
import blackboard.persist.DataType;
import blackboard.data.role.PortalRole;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.persist.user.impl.UserDbMap;

//import blackboard.platform.integration.service.impl.UserPasswordHelper;
import blackboard.platform.security.SecurityUtil;



import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;


//public class UserAccessPack extends BbWsDataAccessPack<BbWsArguments<bbuws.UserDetails>,
//public class UserAccessPack_DATA_GB extends BbWsDataAccessPack<UserAccessPack_DATA_GB.UserArguments,User, bbuws.UserDetails> {
//public class UserAccessPack_DATA_GB<BbUserType extends User, WsInputType extends BbWsDataDetails>
//public class UserAccessPack_DATA_GB<BbUserType extends User, ArgumentsType extends UserAccessPack.UserArguments>
public class UserAccessPack_DATA<BbUserType extends User, ArgumentsType extends UserAccessPack.UserArgumentsWithUserInput>
            extends UserAccessPack<BbUserType, ArgumentsType> {
//    static class UserArguments extends BbWsArguments<UserDetails> {}
    //void loadRecord(blackboard.data.user.User bbo) throws Exception {
    public static class UserAccessPackWithUserInput<BbUserType extends User>
            extends UserAccessPack_DATA<BbUserType, UserArgumentsWithUserInput> {
        @Override protected void setBbFields() throws Exception {

            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setId(checkAndgenerateId(User.DATA_TYPE, newValue));
                }
            }.setBbField("bbId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setBatchUid(newValue);
                }
            }.setBbField("batchUid");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getUserName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUserName();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setUserName(newValue);
                }
            }.setBbField("userName");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getPassword();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPassword();
                }

                private boolean stringContainsHexCharsOnly(String s) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        boolean is_hex_char = (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
                        if (!is_hex_char) return false;
                    }
                    return true;
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    boolean do_hash = true;
                    if (newValue.length() == 32) {
                        if (stringContainsHexCharsOnly(newValue)) do_hash = false;
                    }
                    if (do_hash) {
                        newValue = SecurityUtil.getHashValue(newValue);
                        getArgs().getInputRecord().setPassword(newValue); //necessary for later successfull compare of loaded record
                    } 
                    bbObject.setPassword(newValue);
                }
            }.setBbField("password");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getGivenName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getGivenName();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setGivenName(newValue);
                }
            }.setBbField("givenName");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getMiddleName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getMiddleName();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setMiddleName(newValue);
                }
            }.setBbField("middleName");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getFamilyName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getFamilyName();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setFamilyName(newValue);
                }
            }.setBbField("familyName");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return extractDate(bbObject.getLastLoginDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getLastLogin();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setLastLoginDate(parseDate(newValue));
                }
            }.setBbField("lastLogin");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getEmailAddress();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEmailAddress();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setEmailAddress(newValue);
                }
            }.setBbField("emailAddress");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getStudentId();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getStudentId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setStudentId(newValue);
                }
            }.setBbField("studentId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getTitle();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getTitle();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setTitle(newValue);
                }
            }.setBbField("title");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getSystemRoleIdentifier();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getSystemRoleId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setSystemRoleIdentifier(newValue);
                }
            }.setBbField("systemRoleId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getSystemRole().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getSystemRole();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setSystemRole(User.SystemRole.fromFieldName(newValue));
                }
            }.setBbField("systemRole");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getPortalRoleId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPortalRoleId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setPortalRoleId(checkAndgenerateId(User.DATA_TYPE, newValue));
                        
                }
            }.setBbField("portalRoleId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    PortalRoleDbLoader prl = PortalRoleDbLoader.Default.getInstance();
                    PortalRole pr = prl.loadPrimaryRoleByUserId(bbObject.getPortalRoleId());
                    return pr.getRoleName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPortalRoleName();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    PortalRole pr = PortalRoleAccessPack_DATA.loadRecordByRoleNameLogic(newValue);
                    bbObject.setPortalRole(pr);
                    getArgs().getInputRecord().setPortalRoleName(pr.getRoleName()); //necessary for later successfull compare of loaded record
					//!!add warning to DataLog if names are not equal
                }
            }.setBbField("portalRoleName");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getGender().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getGender();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setGender((User.Gender)User.Gender.fromFieldName(newValue, User.Gender.class));
                }
            }.setBbField("gender");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return extractDate(bbObject.getBirthDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBirthDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setBirthDate(parseDate(newValue));
                }
            }.setBbField("birthDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getEducationLevel().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEducationLevel();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setEducationLevel((User.EducationLevel)EducationLevel.fromFieldName(newValue, User.EducationLevel.class));
                }
            }.setBbField("educationLevel");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getJobTitle();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getJobTitle();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setJobTitle(newValue);
                }
            }.setBbField("jobTitle");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCompany();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCompany();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCompany(newValue);
                }
            }.setBbField("company");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDepartment();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCompany();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setDepartment(newValue);
                }
            }.setBbField("company");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getStreet1();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getStreet1();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setStreet1(newValue);
                }
            }.setBbField("street1");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getStreet2();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getStreet2();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setStreet2(newValue);
                }
            }.setBbField("street2");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCity();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCity();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCity(newValue);
                }
            }.setBbField("city");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getState();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCounty();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setState(newValue);
                }
            }.setBbField("county");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCountry();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCountry();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCountry(newValue);
                }
            }.setBbField("country");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getZipCode();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPostCode();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setZipCode(newValue);
                }
            }.setBbField("postCode");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getBusinessPhone1();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBusinessPhone1();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setBusinessPhone1(newValue);
                }
            }.setBbField("businessPhone1");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getBusinessPhone2();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBusinessPhone2();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setBusinessPhone2(newValue);
                }
            }.setBbField("businessPhone2");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getMobilePhone();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getMobilePhone();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setMobilePhone(newValue);
                }
            }.setBbField("mobilePhone");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getHomePhone1();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getHomePhone1();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setHomePhone1(newValue);
                }
            }.setBbField("homePhone1");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getHomePhone2();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getHomePhone2();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setHomePhone2(newValue);
                }
            }.setBbField("homePhone2");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getBusinessFax();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBusinessFax();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setBusinessFax(newValue);
                }
            }.setBbField("businessFax");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getHomeFax();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getHomeFax();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setHomeFax(newValue);
                }
            }.setBbField("homeFax");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getWebPage();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getWebPage();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setWebPage(newValue);
                }
            }.setBbField("webPage");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCardNumber();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCardNumber();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCardNumber(newValue);
                }
            }.setBbField("cardNumber");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCDRomDriveMac();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCdRomDriveMac();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCDRomDriveMac(newValue);
                }
            }.setBbField("cdRomDriveMac");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCDRomDrivePC();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCdRomDrivePC();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCDRomDrivePC(newValue);
                }
            }.setBbField("cdRomDrivePC");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getShowAddContactInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getShowAddContactInfo();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setShowAddContactInfo(Boolean.parseBoolean(newValue));
                }
            }.setBbField("showAddContactInfo");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getShowAddressInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getShowAddressInfo();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setShowAddressInfo(Boolean.parseBoolean(newValue));
                }
            }.setBbField("showAddressInfo");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getShowEmailInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getShowEmailInfo();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setShowEmailInfo(Boolean.parseBoolean(newValue));
                }
            }.setBbField("showEmailInfo");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getShowWorkInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getShowWorkInfo();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setShowWorkInfo(Boolean.parseBoolean(newValue));
                }
            }.setBbField("showWorkInfo");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getIsAvailable());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getIsAvailable();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIsAvailable(Boolean.parseBoolean(newValue));
                }
            }.setBbField("isAvailable");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getIsInfoPublic());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getIsInfoPublic();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIsInfoPublic(Boolean.parseBoolean(newValue));
                }
            }.setBbField("isInfoPublic");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return extractDate(bbObject.getModifiedDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getModifiedDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setModifiedDate(parseDate(newValue));
                }
            }.setBbField("modifiedDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getLocale();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getLocale();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setLocale(newValue);
                }
            }.setBbField("locale");
            
        }
    }
/* Difficult to get back results
    public static class DeleteListByBatchUid extends PersonAccessByBatchUidPack {
        class ListDeleter extends DataAccessor {
            public void access() throws Exception {
                BbList<Person> p_l = new BbList<Person>();
                for (UserDetails ud: getArgs().getInputList()) {
                    bbObject = new Person();
                    getArgs().setInputRecord(ud);
                    setBbFields();
                    p_l.add(bbObject);
                    appendBoolResultDataLogFromInputRecord();
                }
                PersonPersister uper = PersonDbPersister.Default.getInstance();
                blackboard.admin.snapshot.persist.Results r = uper.remove(p_l); //??
            }
        }
        public void initialize(UserArguments args) {
            ListDeleter da = new ListDeleter();
            da.initialize(null); 
            super.initialize(args, Person.class, da);
        }
    }*/
    public static class UserWithPwd extends User {
        @Override public void setUserName(String strUserName) {
            super.setUserName(strUserName);
            if(strUserName != null && _bbAttributes.getBbAttribute("Password").getValue() == null) {
                _bbAttributes.setString("Password",
                    blackboard.admin.snapshot.util.ConversionUtility.getHashValue(getUserName()));
                _bbAttributes.getBbAttribute("Password").setIsDirty(false);
            }
        }
    }

    public static class UserAccessByIdPack extends UserAccessPackWithUserInput<User> {
        protected void loadRecordById() throws Exception {
            Id id = checkAndgenerateId(User.DATA_TYPE, getArgs().getInputRecord().getBbId());
            bbObject = (UserDbLoader.Default.getInstance().loadById(id));
        }
        protected void loadRecordByName() throws Exception {
            bbObject = UserDbLoader.Default.getInstance().loadByUserName(getArgs().getInputRecord().getUserName());
        }
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
        @Override protected void loadRecordByAltId() throws Exception {
            loadRecordByName();
        }
        @Override protected void insertRecord() throws Exception {
            UserDbPersister uper = UserDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }

        @Override protected void updateRecord() throws Exception {
            UserDbPersister uper = UserDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }

        @Override protected void deleteRecord() throws Exception {
            Id id = checkAndgenerateId(User.DATA_TYPE, getArgs().getInputRecord().getBbId());
            UserDbPersister uper = UserDbPersister.Default.getInstance();
            uper.deleteById(id);
        }

        /*
        @Override protected void persistRecord() throws Exception {
            try {
                if (getArgs().getInputRecord().getId() != null) loadRecord();
            } catch (blackboard.persist.KeyNotFoundException kfe) {
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "persistRecord() caught blackboard.persist.KeyNotFoundException thrown by loadRecord() => record does not exist, choosing = new User(). ", this);
            }
            if (bbObject == null) {
                    bbObject = new UserWithPwd();
            }
            setBbFields();
            UserDbPersister uper = UserDbPersister.Default.getInstance();
            uper.persist(bbObject);
        }
         *
         */
    }

    public static class LoadRecordById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
    }

    public static class LoadRecordByName extends UserAccessByIdPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByName();
        }
        public void initialize(UserArgumentsWithUserInput args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
    }

    public static class InsertRecordById extends UserAccessByIdPack {
        /*@Override public void access() throws Exception {
            bbObject = new UserWithPwd();
            super.access();
        }*/
        public void initialize(UserArgumentsWithUserInput args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            Class c = UserWithPwd.class;
            super.initialize(args, c, da);
        }
    }

    public static class UpdateRecordById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
    }

    public static class PersistRecordById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            Class c = UserWithPwd.class;
            super.initialize(args, c, da);
        }
    }

    /*public static class PersistRecordById extends UserAccessByIdPack {
        class RecordPersister_this extends DataAccessor {
            public void access() throws Exception {
                persistRecord();
            }
        }
        public void initialize(UserArgumentsWithUserInput args) {
            RecordPersister_this da = new RecordPersister_this();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
    }*/

    public static class DeleteRecordById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
    }
    public static class LoadListById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, User.class, ilp);
        }
    }

    public static class InsertListById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            Class c = UserWithPwd.class;
            super.initialize(args, c, ilp);
        }
    }

    public static class UpdateListById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, User.class, ilp);
        }
    }

    public static class PersistListById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            Class c = UserWithPwd.class;
            super.initialize(args, c, ilp);
        }
    }

    public static class DeleteListById extends UserAccessByIdPack {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, User.class, ilp);
        }
    }
    //?? loadGuestUser


    public static class UserListLoadPackWithUserInput extends UserAccessPack_DATA<User, UserArgumentsWithUserInput> {
        public void initialize(UserArgumentsWithUserInput args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
        protected void loadListByEmailAddressFamilyNameGivenName()  throws Exception {
            bbObjectList
                    = UserDbLoader.Default.getInstance().loadByEmailAddressFamilyNameGivenName(
                        getArgs().getInputRecord().getEmailAddress(),
                        getArgs().getInputRecord().getFamilyName(),
                        getArgs().getInputRecord().getGivenName());
        }
        protected void loadListBySearchByUserName()  throws Exception {
            bbObjectList = UserDbLoader.Default.getInstance().searchByUserName(
                    getArgs().getInputRecord().getUserName());
        }
        protected void loadListObservedByObserverId()  throws Exception {
            Id id = checkAndgenerateId(User.DATA_TYPE, getArgs().getInputRecord().getBbId());
            bbObjectList = UserDbLoader.Default.getInstance().loadObservedByObserverId(id);
        }
    }
    public static class LoadListByEmailAddressFamilyNameGivenName extends UserListLoadPackWithUserInput {
        @Override protected void loadList () throws Exception {
            loadListByEmailAddressFamilyNameGivenName();
        }
    }
    //!!?? loadByStudentIdFamilyNameGivenName
    //!!?? loadByCardNumberFamilyNameGivenName

    public static class LoadListBySearchByUserName extends UserListLoadPackWithUserInput {
        @Override protected void loadList () throws Exception {
            loadListBySearchByUserName();
        }
    }
    public static class LoadListObservedByObserverId extends UserListLoadPackWithUserInput {
        @Override protected void loadList () throws Exception {
            loadListObservedByObserverId();
        }
    }
    
    //public BbList load(ObserverAssociation template)


    public static class LoadListByCourseId extends UserAccessPack_DATA<User, UserAccessPack.UserArgumentsWithUserAndCourseInput> {
        public void initialize(UserAccessPack.UserArgumentsWithUserAndCourseInput args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
        @Override protected void loadList () throws Exception {
            String str_id = getArgs().getInputCourseRecord().getBbId();
            Id id = checkAndgenerateId(blackboard.data.course.Course.DATA_TYPE,str_id);
            bbObjectList = UserDbLoader.Default.getInstance().loadByCourseId(id);
        }
    }

    public static class LoadListAvailableObserversByCourseId extends UserAccessPack_DATA<User, UserAccessPack.UserArgumentsWithUserAndCourseInput> {
        public void initialize(UserAccessPack.UserArgumentsWithUserAndCourseInput args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
        @Override protected void loadList () throws Exception {
            String str_id = getArgs().getInputCourseRecord().getBbId();
            Id id = checkAndgenerateId(blackboard.data.course.Course.DATA_TYPE,str_id);
            UserDbLoaderImpl udbli = (UserDbLoaderImpl) UserDbLoader.Default.getInstance();
            bbObjectList = udbli.loadAvailableObserversByCourseId(id, BbWsUtil.getFullFilteredMap(UserDbMap.MAP));
        }
    }


    public static class LoadListByGroupId extends UserAccessPack_DATA<User, UserAccessPack.UserArgumentsWithUserAndGroupInput> {
        public void initialize(UserArgumentsWithUserAndGroupInput args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
        @Override protected void loadList () throws Exception {
            String str_id = getArgs().getInputGroupRecord().getBbId();
            Id id = checkAndgenerateId(blackboard.data.course.Group.DATA_TYPE, str_id);
            bbObjectList = UserDbLoader.Default.getInstance().loadByGroupId(id);
        }
    }

    public static class LoadListByPrimaryPortalRoleId extends UserAccessPack_DATA<User, UserArgumentsWithUserAndRoleInput> {
        public void initialize(UserArgumentsWithUserAndRoleInput args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, User.class, da);
        }
        @Override protected void loadList () throws Exception {
            String str_id = getArgs().getInputRoleRecord().getBbId();
            Id id = checkAndgenerateId(blackboard.data.role.PortalRole.DATA_TYPE, str_id);
            bbObjectList = UserDbLoader.Default.getInstance().loadByPrimaryPortalRoleId(id);
        }
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
                return bbObject.getBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBatchUid(newValue);
                }
            }.setWsField("batchUid");
            new WsFieldSetter() {
                @Override
                protected boolean checkByNewValueCompareWithOld(String oldValue, String newValue) throws Exception {
                return oldValue.compareToIgnoreCase(newValue) == 0;
                }
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getUserName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserName();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserName(newValue);
                }
            }.setWsField("userName");
        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.STANDARD) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getGivenName();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getGivenName();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setGivenName(newValue);
                }
            }.setWsField("givenName");
            new WsFieldSetter() {
                @Override
                protected boolean checkByNewValueCompareWithOld(String oldValue, String newValue) throws Exception {
            return oldValue.compareToIgnoreCase(newValue) == 0;
                }
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getPassword();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getPassword();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPassword(newValue);
                }
            }.setWsField("password");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getMiddleName();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getMiddleName();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setMiddleName(newValue);
                }
            }.setWsField("middleName");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getFamilyName();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getFamilyName();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setFamilyName(newValue);
                }
            }.setWsField("familyName");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getLastLoginDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getLastLogin();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setLastLogin(newValue);
                }
            }.setWsField("lastLogin");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getEmailAddress();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getEmailAddress();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEmailAddress(newValue);
                }
            }.setWsField("emailAddress");
        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.EXTENDED) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getStudentId();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getStudentId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setStudentId(newValue);
                }
            }.setWsField("studentId");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getTitle();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getTitle();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setTitle(newValue);
                }
            }.setWsField("title");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getSystemRoleIdentifier();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getSystemRoleId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setSystemRoleId(newValue);
                }
            }.setWsField("systemRoleId");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getSystemRole().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getSystemRole();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setSystemRole(newValue);
                }
            }.setWsField("systemRole");
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
            }.setWsField("portalRoleId");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                PortalRoleDbLoader prl = PortalRoleDbLoader.Default.getInstance();
                PortalRole pr = prl.loadPrimaryRoleByUserId(bbObject.getId());
                    return pr.getRoleName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getPortalRoleName();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPortalRoleName(newValue);
                }
            }.setWsField("portalRoleName");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getGender().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getGender();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setGender(newValue);
                }
            }.setWsField("gender");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getBirthDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getBirthDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBirthDate(newValue);
                }
            }.setWsField("birthDate");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getEducationLevel().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEducationLevel();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEducationLevel(newValue);
                }
            }.setWsField("educationLevel");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getJobTitle();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getJobTitle();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setJobTitle(newValue);
                }
            }.setWsField("jobTitle");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCompany();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCompany();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCompany(newValue);
                }
            }.setWsField("company");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getDepartment();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCompany();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCompany(newValue);
                }
            }.setWsField("company");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getStreet1();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getStreet1();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setStreet1(newValue);
                }
            }.setWsField("street1");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getStreet2();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getStreet2();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setStreet2(newValue);
                }
            }.setWsField("street2");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCity();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCity();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCity(newValue);
                }
            }.setWsField("city");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getState();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCounty();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCounty(newValue);
                }
            }.setWsField("county");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCountry();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCountry();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCountry(newValue);
                }
            }.setWsField("country");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getZipCode();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getPostCode();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPostCode(newValue);
                }
            }.setWsField("postCode");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getBusinessPhone1();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getBusinessPhone1();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBusinessPhone1(newValue);
                }
            }.setWsField("businessPhone1");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getBusinessPhone2();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getBusinessPhone2();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBusinessPhone2(newValue);
                }
            }.setWsField("businessPhone2");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getMobilePhone();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getMobilePhone();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setMobilePhone(newValue);
                }
            }.setWsField("mobilePhone");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getHomePhone1();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getHomePhone1();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setHomePhone1(newValue);
                }
            }.setWsField("homePhone1");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getHomePhone2();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getHomePhone2();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setHomePhone2(newValue);
                }
            }.setWsField("homePhone2");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getBusinessFax();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getBusinessFax();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBusinessFax(newValue);
                }
            }.setWsField("businessFax");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getHomeFax();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getHomeFax();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setHomeFax(newValue);
                }
            }.setWsField("homeFax");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getWebPage();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getWebPage();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setWebPage(newValue);
                }
            }.setWsField("webPage");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCardNumber();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCardNumber();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCardNumber(newValue);
                }
            }.setWsField("cardNumber");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCDRomDriveMac();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCdRomDriveMac();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCdRomDriveMac(newValue);
                }
            }.setWsField("cdRomDriveMac");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCDRomDrivePC();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getCdRomDrivePC();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCdRomDrivePC(newValue);
                }
            }.setWsField("cdRomDrivePC");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getShowAddContactInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getShowAddContactInfo();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setShowAddContactInfo(newValue);
                }
            }.setWsField("showAddContactInfo");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getShowAddressInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getShowAddressInfo();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setShowAddressInfo(newValue);
                }
            }.setWsField("showAddressInfo");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getShowEmailInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getShowEmailInfo();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setShowEmailInfo(newValue);
                }
            }.setWsField("showEmailInfo");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getShowWorkInfo());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getShowWorkInfo();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setShowWorkInfo(newValue);
                }
            }.setWsField("showWorkInfo");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getIsAvailable());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsAvailable();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsAvailable(newValue);
                }
            }.setWsField("isAvailable");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getIsInfoPublic());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsInfoPublic();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsInfoPublic(newValue);
                }
            }.setWsField("isInfoPublic");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getModifiedDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getModifiedDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setModifiedDate(newValue);
                }
            }.setWsField("modifiedDate");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getLocale();
                }
                @Override public String getWsFieldValue() throws Exception {
                return getArgs().getResultRecord().getLocale();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setLocale(newValue);
                }
            }.setWsField("locale");
        }

    }


    //private String retrieveLastLogin(Calendar calendar) {
    //causes: W:\BB-webservice\src\wservices_idla\BbGradebookWs\src\java\bbuws\UserAccessPack_DATA_GB.java:120: non-static method retrieveLastLogin(java.util.Calendar) cannot be referenced from a static context
                //if (c != null) bb_value = retrieveLastLogin(c);
    protected String retrieveLastLogin(Calendar calendar) {
        return (calendar.get(Calendar.YEAR)+"-"+(calendar.get(calendar.MONTH)+1)+"-"+calendar.get(calendar.DAY_OF_MONTH)+" "+calendar.get(calendar.HOUR_OF_DAY)+":"+calendar.get(calendar.MINUTE)+":"+calendar.get(calendar.SECOND));
    }
}



