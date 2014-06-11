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
import blackboard.data.course.Course;
import blackboard.admin.persist.course.CloneConfig;
import blackboard.data.BbFile;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.user.impl.PersonDbLoader;

import java.io.File;


import bbwscommon.*;


public abstract class CourseAccessPack <BbCourseType extends Course,
            ArgumentsType extends BbWsArguments<CourseDetails, CourseDetails>>
        extends BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType
            <ArgumentsType, BbCourseType, CourseDetails> {

    static public class CourseArguments_DATA extends BbWsArguments<CourseDetails, CourseDetails> {
    }
    static public class CourseArguments_ADMIN_DATA extends BbWsArguments<CourseDetails, CourseDetails> {
        protected void initializeDefaults() {
            super.initializeDefaults();
            getApiToUseList().clear();
            getApiToUseList().add(new ApiToUse(ApiToUseEnum.ADMIN_DATA, null));
        }
    }

    public static class CourseArgumentsWithTargetCourseInput extends CourseArguments_ADMIN_DATA {
        private CourseDetails inputSourceCourse;
        private CourseDetails inputTargetCourse;        

        public void initialize(Class<CourseDetails> wsResultClass, BbWsParams params, CourseDetails recordInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName, CourseDetails inputTargetCourseRecord) {
                super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, innerDAPDefaultClassName);
                this.inputTargetCourse = inputTargetCourseRecord;
                this.inputSourceCourse = recordInput;
        }
        //base method must become hidden, not good as design!!
        //trick with initialize seem not to work well here
        /*
        protected void initialize(Class<CourseDetails> wsResultClass, BbWsParams params, CourseDetails recordInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName) {
            super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, innerDAPDefaultClassName); 
        }
        */

        public CourseDetails getInputTargetCourse() {
            return inputTargetCourse;
        }

        public void setInputTargetCourse(CourseDetails inputTargetCourse) {
            this.inputTargetCourse = inputTargetCourse;
        }

        public CourseDetails getInputSourceCourse() {
            return inputSourceCourse;
        }

        public void setInputSourceCourse(CourseDetails inputSourceCourse) {
            this.inputSourceCourse = inputSourceCourse;
        }
    }

    public static class CourseArgumentsWithTargetCourseInputAndCopyParams
            extends CourseArgumentsWithTargetCourseInput {
        private CourseCopyParams courseCopyParams;

        public void initialize(Class<CourseDetails> wsResultClass, BbWsParams params, CourseDetails recordInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName, CourseDetails inputTargetCourseRecord,
            CourseCopyParams courseCopyParams) {
                super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, innerDAPDefaultClassName,
                        inputTargetCourseRecord);
                this.courseCopyParams  = courseCopyParams;
        }

        public CourseCopyParams getCourseCopyParams() {
            return this.courseCopyParams;
        }

        public void setCourseCopyParams(CourseCopyParams courseCopyParams) {
            this.courseCopyParams = courseCopyParams;
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
                        bbObject.setId(Id.generateId(Course.DATA_TYPE, newValue));
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
                    return bbObject.getCourseId();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCourseId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCourseId(newValue);
                }
            }.setBbField("courseId");
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
                    return bbObject.getDescription();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getDescription();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setDescription(newValue);
                }
            }.setBbField("description");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getCreatedDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCreationDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCreatedDate(parseDate(newValue));
                }
            }.setBbField("creationDate");
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
                    return Long.toString(bbObject.getAbsoluteLimit());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getAbsoluteLimit();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setAbsoluteLimit(Long.parseLong(newValue));
                }
            }.setBbField("absoluteLimit");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getAllowGuests());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getAllowGuests();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setAllowGuests(Boolean.parseBoolean(newValue));
                }
            }.setBbField("allowGuests");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getAllowObservers());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getAllowObservers();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setAllowObservers(Boolean.parseBoolean(newValue));
                }
            }.setBbField("allowObservers");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    if (bbObject.getBannerImageFile() != null)                 return bbObject.getBannerImageFile().getPath();
                else return null;
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getBannerImageFile();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    if (BbWsUtil.nullSafeStringComparator(newValue, "") == 0) bbObject.setBannerImageFile(BbFile.UNSET_FILE);
                    else {
                        String upath = newValue.replace('\\', '/');
                        File file = new File(upath);
                        BbFile bbf = new BbFile(file, file.getName());
                        bbObject.setBannerImageFile(bbf);
                    }
                }
            }.setBbField("bannerImageFile");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getButtonStyleId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getButtonStyleId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setButtonStyleId(Id.generateId(Course.DATA_TYPE, newValue));
                }
            }.setBbField("buttonStyleId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getCartridgeId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCartridgeId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setCartridgeId(Id.generateId(Course.DATA_TYPE, newValue));
                }
            }.setBbField("cartridgeId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getClassificationId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getClassificationId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setClassificationId(Id.generateId(Course.DATA_TYPE, newValue));
                }
            }.setBbField("classificationId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    if (bbObject.getDurationType() != null) {
                        return bbObject.getDurationType().toFieldName();
                    } else return null;
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getDurationType();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setDurationType((Course.Duration)Course.Duration.fromFieldName(newValue, Course.Duration.class));
                }
            }.setBbField("durationType");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getEndDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEndDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setEndDate(parseDate(newValue));
                }
            }.setBbField("endDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    if (bbObject.getEnrollmentType() != null) {
                        return bbObject.getEnrollmentType().toFieldName();
                    } else return null;
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEnrollmentType();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setEnrollmentType((Course.Enrollment)Course.Enrollment.fromFieldName(newValue, Course.Enrollment.class));
                }
            }.setBbField("enrollmentType");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getEnrollmentStartDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEnrollmentStartDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setEnrollmentStartDate(parseDate(newValue));
                }
            }.setBbField("enrollmentStartDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getEnrollmentEndDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEnrollmentEndDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setEnrollmentEndDate(parseDate(newValue));
                }
            }.setBbField("enrollmentEndDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getEnrollmentAccessCode();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getEnrollmentAccessCode();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setEnrollmentAccessCode(newValue);
                }
            }.setBbField("enrollmentAccessCode");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getInstitutionName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getInstitution();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setInstitutionName(newValue);
                }
            }.setBbField("institution");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getIsLocaleEnforced());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getIsLocaleEnforced();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIsLocaleEnforced(Boolean.parseBoolean(newValue));
                }
            }.setBbField("isLocaleEnforced");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getIsLockedOut());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getIsLockedOut();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIsLockedOut(Boolean.parseBoolean(newValue));
                }
            }.setBbField("isLockedOut");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Boolean.toString(bbObject.getIsNavCollapsible());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getIsNavigationCollapsible();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setIsNavCollapsible(Boolean.parseBoolean(newValue));
                }
            }.setBbField("isNavigationCollapsible");
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
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getNavColorBg();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getNavigationBackgroundColor();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setNavColorBg(newValue);
                }
            }.setBbField("navigationBackgroundColor");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getNavColorFg();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getNavigationForegroundColor();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setNavColorFg(newValue);
                }
            }.setBbField("navigationForegroundColor");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    if (bbObject.getNavStyle() != null) {
                        return bbObject.getNavStyle().toFieldName();
                    } else return Course.NavStyle.DEFAULT.toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getNavigationStyle();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setNavStyle((Course.NavStyle)Course.NavStyle.fromFieldName(newValue, Course.NavStyle.class));
                }
            }.setBbField("navigationStyle");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Long.toString(bbObject.getNumDaysOfUse());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getNumberOfDaysOfUse();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setNumDaysOfUse(Integer.parseInt(newValue));
                }
            }.setBbField("numberOfDaysOfUse");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getPaceType().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getPaceType();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setPaceType((Course.Pace) Course.Pace.fromFieldName(newValue, Course.Pace.class));
                }
            }.setBbField("paceType");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getServiceLevelType().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getServiceLevelType();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setServiceLevelType((Course.ServiceLevel)Course.ServiceLevel.fromFieldName(newValue, Course.ServiceLevel.class));
                }
            }.setBbField("serviceLevelType");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Long.toString(bbObject.getSoftLimit());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getSoftLimit();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setSoftLimit(Long.parseLong(newValue));
                }
            }.setBbField("softLimit");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return extractDate(bbObject.getStartDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getStartDate();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setStartDate(parseDate(newValue));
                }
            }.setBbField("startDate");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return Long.toString(bbObject.getUploadLimit());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUploadLimit();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setUploadLimit(Long.parseLong(newValue));
                }
            }.setBbField("uploadLimit");
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
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCourseId();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getCourseId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCourseId(newValue);
                }
            }.setWsField("courseId");		        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.STANDARD) >= 0) {
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
                return bbObject.getDescription();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDescription();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDescription(newValue);
                }
            }.setWsField("description");		 
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getCreatedDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getCreationDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCreationDate(newValue);
                }
            }.setWsField("creationDate");		
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
                return Long.toString(bbObject.getAbsoluteLimit());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getAbsoluteLimit();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setAbsoluteLimit(newValue);
                }
            }.setWsField("absoluteLimit");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getAllowGuests());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getAllowGuests();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setAllowGuests(newValue);
                }
            }.setWsField("allowGuests");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getAllowObservers());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getAllowObservers();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setAllowObservers(newValue);
                }
            }.setWsField("allowObservers");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                if (bbObject.getBannerImageFile() != null)                 return bbObject.getBannerImageFile().getPath();
                else return null;
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getBannerImageFile();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBannerImageFile(newValue);
                }
            }.setWsField("bannerImageFile");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getButtonStyleId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getButtonStyleId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setButtonStyleId(newValue);
                }
            }.setWsField("buttonStyleId");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCartridgeId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getCartridgeId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCartridgeId(newValue);
                }
            }.setWsField("cartridgeId");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getClassificationId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getClassificationId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setClassificationId(newValue);
                }
            }.setWsField("classificationId");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    if (bbObject.getDurationType() != null) {
                        return bbObject.getDurationType().toFieldName();
                    } else return null;
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDurationType();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDurationType(newValue);
                }
            }.setWsField("durationType");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getEndDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEndDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEndDate(newValue);
                }
            }.setWsField("endDate");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    if (bbObject.getEnrollmentType() != null) {
                        return bbObject.getEnrollmentType().toFieldName();
                    } else return null;
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEnrollmentType();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEnrollmentType(newValue);
                }
            }.setWsField("enrollmentType");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getEnrollmentStartDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEnrollmentStartDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEnrollmentStartDate(newValue);
                }
            }.setWsField("enrollmentStartDate");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getEnrollmentEndDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEnrollmentEndDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEnrollmentEndDate(newValue);
                }
            }.setWsField("enrollmentEndDate");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getEnrollmentAccessCode();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getEnrollmentAccessCode();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setEnrollmentAccessCode(newValue);
                }
            }.setWsField("enrollmentAccessCode");		 
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getInstitutionName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getInstitution();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setInstitution(newValue);
                }
            }.setWsField("institution");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getIsLocaleEnforced());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsLocaleEnforced();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsLocaleEnforced(newValue);
                }
            }.setWsField("isLocaleEnforced");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getIsLockedOut());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsLockedOut();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsLockedOut(newValue);
                }
            }.setWsField("isLockedOut");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.getIsNavCollapsible());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsNavigationCollapsible();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsNavigationCollapsible(newValue);
                }
            }.setWsField("isNavigationCollapsible");		
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
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getNavColorBg();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getNavigationBackgroundColor();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setNavigationBackgroundColor(newValue);
                }
            }.setWsField("navigationBackgroundColor");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getNavColorFg();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getNavigationForegroundColor();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setNavigationForegroundColor(newValue);
                }
            }.setWsField("navigationForegroundColor");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getNavStyle().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getNavigationStyle();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setNavigationStyle(newValue);
                }
            }.setWsField("navigationStyle");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Long.toString(bbObject.getNumDaysOfUse());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getNumberOfDaysOfUse();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setNumberOfDaysOfUse(newValue);
                }
            }.setWsField("numberOfDaysOfUse");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getPaceType().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getPaceType();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setPaceType(newValue);
                }
            }.setWsField("paceType");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getServiceLevelType().toFieldName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getServiceLevelType();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setServiceLevelType(newValue);
                }
            }.setWsField("serviceLevelType");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Long.toString(bbObject.getSoftLimit());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getSoftLimit();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setSoftLimit(newValue);
                }
            }.setWsField("softLimit");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return extractDate(bbObject.getStartDate());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getStartDate();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setStartDate(newValue);
                }
            }.setWsField("startDate");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Long.toString(bbObject.getUploadLimit());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUploadLimit();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUploadLimit(newValue);
                }
            }.setWsField("uploadLimit");		
        }

        
    }
}

