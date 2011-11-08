/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package bbcrsws;

import blackboard.data.course.Course;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */


/* (vic) - 
 * Following behavior was detected during update of Course via 
 * CourseDbPersister.persist() in Bb rel. 9.0.440.0 
 * (corresponds to courseUpdateRecordById() web method) :
 * It looks like internal cache indexed by BatchUid does not update BatchUid key, 
 * and if batchUid was updated, then loading by new batchUid immediately after update
 * does not find anything, loading by previous batchUid returns updated record with  
 * new batchUid value.
 * Upon attempt of updating of CourseId it is updated only in cache, changes are not actually saved 
 * to database, i.e. upon immediate reload CourseId will contain new value, but after some time it 
 * changes back to initial value.
 * 
 * 
 * courseDeleteRecordByBatchUid method performs archiving of deleted course, while
 * courseDeleteRecordById does not.
 * Archive of the course is placed in \blackboard\content\vi\bb_bb60\recyclebin.
 * 
 * classificationId is not settable via courseInsertRecordByBatchUid() and 
 * courseUpdateRecordByBatchUid() methods
 * 
 * Updating of courseId with courseUpdateRecordByBatchUid() causes
 * blackboard.data.ImmutableException: An immutable object can not be modified [Course id cannot be modified.]
 * 
 * Course Copy - timeout delay when copying proxy links defined for a course if proxy server is unavailable 
 * - Ws->Bb->proxy server -> exception in Bb internals on delay, exception in Ws on delay, Bb continues execution.
 * Probably exception should be analysed on client side - it was timeout exception. 
 * (!! complement with exception type/message)
 * Longer delay may be set on client side like args.bbCrsWs.Timeout = 900000; sampled in AutoTestClient.
 * 
 * Setting isAvailable = “false” makes Course invisible for students, 
 * but teacher and, for example, grader (did not test with all course roles) 
 * sees this class with the note “(unavailable)” to the right.
 * 
 * Setting of serviceLevelType field to the value different from “FULL” 
 * (possible ones should be COMMUNITY, REGISTERED, TEST_DRIVE, SYSTEM) 
 * hides class from everywhere – it is not visible at all even from System Admin panel. 
 */ 
public class CourseDetails extends AbstractCourseDetails implements ReturnTypeInterface
{

    public String getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(String enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public String getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public void setEnrollmentEndDate(String enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }

    public String getEnrollmentAccessCode() {
        return EnrollmentAccessCode;
    }

    public void setEnrollmentAccessCode(String EnrollmentAccessCode) {
        this.EnrollmentAccessCode = EnrollmentAccessCode;
    }
    public enum Verbosity{minimal,standard,extended}

    private Verbosity verbosity;
    //standard details
    private String bbId;
    private String title;
    private String description;
    private String modifiedDate;
    //extended details
    private String absoluteLimit;
    private String allowGuests;
    private String allowObservers;
    private String bannerImageFile;
    private String batchUid;
    private String buttonStyleId;
    private String cartridgeId;
    private String classificationId;
    private String durationType;
    private String endDate;
    private String enrollmentType;
    private String enrollmentStartDate;
    private String enrollmentEndDate;
    private String EnrollmentAccessCode;
    private String institution;
    private String isLocaleEnforced;
    private String isLockedOut;
    private String isNavigationCollapsible;
    private String locale;
    private String navigationBackgroundColor;
    private String navigationForegroundColor;
    private String navigationStyle;
    private String numberOfDaysOfUse;
    private String paceType;
    private String serviceLevelType;
    private String softLimit;
    private String startDate;
    private String uploadLimit;

    public CourseDetails(){}
    public CourseDetails(Verbosity verbosity)
    {
        this.verbosity = verbosity;
    }
    public CourseDetails(Course course, Verbosity verbosity) throws Exception
    {
        this.verbosity = verbosity;

        switch(verbosity)
        {
            case extended:
                this.absoluteLimit = Long.toString(course.getAbsoluteLimit());
                this.allowGuests = Boolean.toString(course.getAllowGuests());
                this.allowObservers = Boolean.toString(course.getAllowObservers());
                try{this.bannerImageFile = course.getBannerImageFile().getPath();}catch(Exception e){this.bannerImageFile = "";}
                this.batchUid = course.getBatchUid();
                this.buttonStyleId = course.getButtonStyleId().toExternalString();
                this.cartridgeId = course.getCartridgeId().toExternalString();
                this.classificationId = course.getClassificationId().toExternalString();
                this.durationType = course.getDurationType().toFieldName();
                this.endDate = extractDate(course.getEndDate());
                this.enrollmentType = course.getEnrollmentType().toFieldName();
                this.institution = course.getInstitutionName();
                this.isLocaleEnforced = Boolean.toString(course.getIsLocaleEnforced());
                this.isLockedOut = Boolean.toString(course.getIsLockedOut());
                this.isNavigationCollapsible = Boolean.toString(course.getIsNavCollapsible());
                this.locale = course.getLocale();
                this.navigationBackgroundColor = course.getNavColorBg();
                this.navigationForegroundColor = course.getNavColorFg();
                this.navigationStyle = course.getNavStyle().toFieldName();
                this.numberOfDaysOfUse = Integer.toString(course.getNumDaysOfUse());
                this.paceType = course.getPaceType().toFieldName();
                this.serviceLevelType = course.getServiceLevelType().toFieldName();
                this.softLimit = Long.toString(course.getSoftLimit());
                this.startDate = extractDate(course.getStartDate());
                this.uploadLimit = Long.toString(course.getUploadLimit());
            case standard:
                this.bbId = course.getId().toExternalString();
                this.title = course.getTitle();
                this.description = course.getDescription();
                this.creationDate = extractDate(course.getCreatedDate());
                this.modifiedDate = extractDate(course.getModifiedDate());
                this.isAvailable = Boolean.toString(course.getIsAvailable());
            case minimal:
                this.courseId = course.getCourseId();
            return;
            default: throw new Exception("Undefined verbosity of course");
        }
    }

    @Override
    public String getBbId()
    {
	return this.bbId;
    }

    @Override
    public void setBbId(String courseBbId)
    {
	this.bbId = courseBbId;
    }

    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public String getDescription()
    {
	return this.description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public String getModifiedDate()
    {
	return this.modifiedDate;
    }

    public void setModifiedDate(String modifiedDate)
    {
	this.modifiedDate = modifiedDate;
    }

    public String getAbsoluteLimit()
    {
	return this.absoluteLimit;
    }

    public void setAbsoluteLimit(String absoluteLimit)
    {
	this.absoluteLimit = absoluteLimit;
    }

    public String getAllowGuests()
    {
	return this.allowGuests;
    }

    public void setAllowGuests(String allowGuests)
    {
	this.allowGuests = allowGuests;
    }

    public String getAllowObservers()
    {
	return this.allowObservers;
    }

    public void setAllowObservers(String allowObservers)
    {
	this.allowObservers = allowObservers;
    }

    public String getBannerImageFile()
    {
	return this.bannerImageFile;
    }

    public void setBannerImageFile(String bannerImageFile)
    {
	this.bannerImageFile = bannerImageFile;
    }
    public String getBatchUid()
    {
	return this.batchUid;
    }

    public void setBatchUid(String batchUId)
    {
	this.batchUid = batchUId;
    }
    public String getButtonStyleId()
    {
	return this.buttonStyleId;
    }

    public void setButtonStyleId(String buttonStyleId)
    {
	this.buttonStyleId = buttonStyleId;
    }
    public String getCartridgeId()
    {
	return this.cartridgeId;
    }

    public void setCartridgeId(String cartridgeId)
    {
	this.cartridgeId = cartridgeId;
    }
    public String getClassificationId()
    {
	return this.classificationId;
    }

    public void setClassificationId(String classificationId)
    {
	this.classificationId = classificationId;
    }
    public String getDurationType()
    {
	return this.durationType;
    }

    public void setDurationType(String durationType)
    {
	this.durationType = durationType;
    }
    public String getEndDate()
    {
	return this.endDate;
    }

    public void setEndDate(String endDate)
    {
	this.endDate = endDate;
    }
    public String getEnrollmentType()
    {
	return this.enrollmentType;
    }

    public void setEnrollmentType(String enrollmentType)
    {
	this.enrollmentType = enrollmentType;
    }
    public String getInstitution()
    {
	return this.institution;
    }

    public void setInstitution(String institution)
    {
	this.institution = institution;
    }

    public String getIsLocaleEnforced()
    {
	return this.isLocaleEnforced;
    }

    public void setIsLocaleEnforced(String isLocaleEnforced)
    {
	this.isLocaleEnforced = isLocaleEnforced;
    }

    public String getLocale()
    {
	return this.locale;
    }

    public void setLocale(String locale)
    {
	this.locale = locale;
    }

    public String getIsNavigationCollapsible()
    {
	return this.isNavigationCollapsible;
    }

    public void setIsNavigationCollapsible(String isNavigationCollapsible)
    {
	this.isNavigationCollapsible = isNavigationCollapsible;
    }

    public String getIsLockedOut()
    {
	return this.isLockedOut;
    }

    public void setIsLockedOut(String isLockedOut)
    {
	this.isLockedOut = isLockedOut;
    }

    public String getNavigationBackgroundColor()
    {
	return this.navigationBackgroundColor;
    }

    public void setNavigationBackgroundColor(String navigationBackgroundColor)
    {
	this.navigationBackgroundColor = navigationBackgroundColor;
    }
    public String getNavigationForegroundColor()
    {
	return this.navigationForegroundColor;
    }

    public void setNavigationForegroundColor(String navigationForegroundColor)
    {
	this.navigationForegroundColor = navigationForegroundColor;
    }
    public String getNavigationStyle()
    {
	return this.navigationStyle;
    }

    public void setNavigationStyle(String navigationStyle)
    {
	this.navigationStyle = navigationStyle;
    }
    public String getNumberOfDaysOfUse()
    {
	return this.numberOfDaysOfUse;
    }

    public void setNumberOfDaysOfUse(String numberOfDaysOfUse)
    {
	this.numberOfDaysOfUse = numberOfDaysOfUse;
    }
    public String getPaceType()
    {
	return this.paceType;
    }

    public void setPaceType(String paceType)
    {
	this.paceType = paceType;
    }
    public String getServiceLevelType()
    {
	return this.serviceLevelType;
    }

    public void setServiceLevelType(String serviceLevelType)
    {
	this.serviceLevelType = serviceLevelType;
    }
    public String getSoftLimit()
    {
	return this.softLimit;
    }

    public void setSoftLimit(String softLimit)
    {
	this.softLimit = softLimit;
    }
    public String getStartDate()
    {
	return this.startDate;
    }

    public void setStartDate(String startDate)
    {
	this.startDate = startDate;
    }
    public String getUploadLimit()
    {
	return this.uploadLimit;
    }

    public void setUploadLimit(String uploadLimit)
    {
	this.uploadLimit = uploadLimit;
    }

    public String[] toStringArray()
    {
        switch(verbosity)
        {
            case minimal:
                return new String[]{this.courseId};
            case standard:
                return new String[]{this.bbId,
                        this.courseId,
                        this.title,
                        this.description,
                        this.creationDate,
                        this.modifiedDate,
                        this.isAvailable};
            case extended:
                return new String[]{this.bbId,
                        this.courseId,
                        this.title,
                        this.description,
                        this.creationDate,
                        this.modifiedDate,
                        this.isAvailable,
                        this.absoluteLimit,
                        this.allowGuests,
                        this.allowObservers,
                        this.bannerImageFile,
                        this.batchUid,
                        this.buttonStyleId,
                        this.cartridgeId,
                        this.classificationId,
                        this.durationType,
                        this.endDate,
                        this.enrollmentType,
                        this.institution,
                        this.isLocaleEnforced,
                        this.isLockedOut,
                        this.isNavigationCollapsible,
                        this.locale,
                        this.navigationBackgroundColor,
                        this.navigationForegroundColor,
                        this.navigationStyle,
                        this.numberOfDaysOfUse,
                        this.paceType,
                        this.serviceLevelType,
                        this.softLimit,
                        this.startDate,
                        this.uploadLimit};
            default:
                return new String[]{};
        }
    }

    public String[] toStringArrayHeader()
    {
        switch(verbosity)
        {
            case minimal:
                return new String[]{"course Id"};
            case standard:
                return new String[]{"courseBbId","course Id","title",
                        "description","creation date",
                        "modified date","available"};
            case extended:
                return new String[]{"courseBbId","course Id","title",
                        "description","creation date",
                        "modified date","available",
                        "absolute limit","allow guests",
                        "allow observers","banner image url","batch uid",
                        "button style","cartridge","classification",
                        "duration type","end date","enrollment","institution",
                        "locale enforced","locked out","navigation collapsible",
                        "locale","navigation background colour","navigation foreground colour",
                        "navigation style","number of days of use","pace type",
                        "service level type","soft limit","start date","upload limit"};
            default:
                return new String[]{};
        }
    }
/**
 *
 * @author vic

    public String getId() {
        return this.bbId;
    }
    public void setId(String id) {
        this.bbId = id;
    }
 */
}
