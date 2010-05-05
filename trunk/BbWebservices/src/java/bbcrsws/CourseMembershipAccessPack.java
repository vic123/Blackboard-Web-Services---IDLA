/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbcrsws;

/**
 *
 * @author vic
 */
import blackboard.persist.Id;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.user.impl.PersonDbLoader;


import bbwscommon.*;


public abstract class CourseMembershipAccessPack <BbCourseMembershipType extends CourseMembership,
            //ArgumentsType extends CourseMembershipAccessPack.CourseMembershipArguments,
            ArgumentsType extends BbWsArguments<CourseMembershipDetails, CourseMembershipDetails>>
//            WsObjectType extends  CourseMembershipDetails>
        extends BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType
            <ArgumentsType, BbCourseMembershipType, CourseMembershipDetails> {

    //static class CourseMembershipArguments extends BbWsArguments<WsObjectType, WsObjectType> {
    static public class CourseMembershipArguments_DATA extends BbWsArguments<CourseMembershipDetails, CourseMembershipDetails> {
    }
    static public class CourseMembershipArguments_ADMIN_DATA extends BbWsArguments<CourseMembershipDetails, CourseMembershipDetails> {
        protected void initializeDefaults() {
            super.initializeDefaults();
            getApiToUseList().clear();
            getApiToUseList().add(new ApiToUse(ApiToUseEnum.ADMIN_DATA, null));
        }
    }

    protected abstract void setBbCourseBatchUidField() throws Exception ;
    protected abstract void setWsCourseBatchUidField() throws Exception ;
    protected abstract void setBbUserBatchUidField() throws Exception ;
    protected abstract void setWsUserBatchUidField() throws Exception ;

    @Override protected void setSafeResultRecordIds() {
        super.setSafeResultRecordIds();
        getArgs().getResultRecord().setUserBbId(getArgs().getInputRecord().getUserBbId());
        getArgs().getResultRecord().setCourseBbId(getArgs().getInputRecord().getCourseBbId());
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
                        bbObject.setId(Id.generateId(CourseMembership.DATA_TYPE, newValue));
                }
            }.setBbField("bbId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getCourseId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCourseBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setCourseId(Id.generateId(CourseMembership.DATA_TYPE, newValue));
                }
            }.setBbField("courseBbId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getUserId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUserBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setUserId(Id.generateId(CourseMembership.DATA_TYPE, newValue));
                }
            }.setBbField("userBbId");

            setBbCourseBatchUidField();


            setBbUserBatchUidField();

            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getIsAvailable());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getAvailable();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIsAvailable(Boolean.parseBoolean(newValue));
                }
            }.setBbField("available");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getHasCartridgeAccess());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCartridgeAccess();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setHasCartridgeAccess(Boolean.parseBoolean(newValue));
                }
            }.setBbField("cartridgeAccess");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getDataSourceId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getDataSourceBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setDataSourceId(Id.generateId(CourseMembership.DATA_TYPE, newValue));
                }
            }.setBbField("dataSourceBbId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getEnrollmentDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEnrollmentDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setEnrollmentDate(parseDate(newValue));
                }
            }.setBbField("enrollmentDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getLastAccessDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getLastAccessDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setLastAccessDate(parseDate(newValue));
                }
            }.setBbField("lastAccessDate");
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
                    return bbObject.getRole().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getRole();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setRole(Role.fromFieldName(newValue));
                }
            }.setBbField("role");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getIntroduction();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getIntroduction();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIntroduction(newValue);
                }
            }.setBbField("introduction");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getNotes();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getNotes();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setNotes(newValue);
                }
            }.setBbField("notes");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getPersonalInfo();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPersonalInfo();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setPersonalInfo(newValue);
                }
            }.setBbField("personalInfo");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getChildCourseId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getChildCourseId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setChildCourseId(Id.generateId(CourseMembership.DATA_TYPE, newValue));
                }
            }.setBbField("childCourseId");


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
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCourseId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getCourseBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCourseBbId(newValue);
                }
            }.setWsField("courseBbId");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getUserId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserBbId(newValue);
                }
            }.setWsField("userBbId");

            setWsCourseBatchUidField();


            setWsUserBatchUidField();

            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getIsAvailable());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getAvailable();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setAvailable(newValue);
                }
            }.setWsField("available");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getHasCartridgeAccess());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getCartridgeAccess();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCartridgeAccess(newValue);
                }
            }.setWsField("cartridgeAccess");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDataSourceId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDataSourceBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDataSourceBbId(newValue);
                }
            }.setWsField("dataSourceBbId");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getEnrollmentDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEnrollmentDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEnrollmentDate(newValue);
                }
            }.setWsField("enrollmentDate");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getLastAccessDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getLastAccessDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setLastAccessDate(newValue);
                }
            }.setWsField("lastAccessDate");
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
                return bbObject.getRole().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getRole();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setRole(newValue);
                }
            }.setWsField("role");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getIntroduction();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIntroduction();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIntroduction(newValue);
                }
            }.setWsField("introduction");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getNotes();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getNotes();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setNotes(newValue);
                }
            }.setWsField("notes");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getPersonalInfo();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getPersonalInfo();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPersonalInfo(newValue);
                }
            }.setWsField("personalInfo");
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getChildCourseId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getChildCourseId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setChildCourseId(newValue);
                }
            }.setWsField("childCourseId");

    }
}

/*
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    //causes java.lang.NullPointerException
                    return bbObject.getUser().getBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserBatchUid(newValue);
                }
            }.setWsField("userBatchUid");


         new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                return bbObject.getUser().getBatchUid();
            }
            @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserBatchUid();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                Person person = PersonDbLoader.Default.getInstance().load(newValue);
                if (getArgs().getResultRecord().getUserBbId() != null
                    && person.getId().toExternalString() != getArgs().getResultRecord().getUserBbId()) {
                        throw new BbWsException ("userBatchId - Attemtp to set userBatchId along with inconsistent userId - set userId to null in order to change user by BatchUid.");
                }
                bbObject.setUser(person);
            }
        }.setBbField("userBatchUid");


 */