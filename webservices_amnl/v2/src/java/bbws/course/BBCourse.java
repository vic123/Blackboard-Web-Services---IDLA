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
package bbws.course;

import blackboard.data.course.Course;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBCourse extends AbstractBBCourse
{
    public enum BBCourseVerbosity{minimal,standard,extended}

    private BBCourseVerbosity verbosity;
    //standard details
    private String courseBbId;
    private String title;
    private String description;
    private String modifiedDate;
    //extended details
    private String absoluteLimit;
    private Boolean allowGuests;
    private Boolean allowObservers;
    private String bannerImageFile;
    private String batchUId;
    private String buttonStyle;
    private String cartridgeDescription;
    private String classification;
    private String durationType;
    private String endDate;
    private String enrollment;
    private String institution;
    private Boolean localeEnforced;
    private Boolean lockedOut;
    private Boolean navigationCollapsible;
    private String locale;
    private String navigationBackgroundColour;
    private String navigationForegroundColour;
    private String navigationStyle;
    private Integer numberOfDaysOfUse;
    private String paceType;
    private String serviceLevelType;
    private String softLimit;
    private String startDate;
    private String uploadLimit;

    public BBCourse(){}
    public BBCourse(BBCourseVerbosity verbosity)
    {
        this.verbosity = verbosity;
    }
    public BBCourse(Course course, BBCourseVerbosity verbosity) throws Exception
    {
        this.verbosity = verbosity;

        switch(verbosity)
        {
            case extended:
                this.absoluteLimit = Long.toString(course.getAbsoluteLimit());
                this.allowGuests = course.getAllowGuests();
                this.allowObservers = course.getAllowObservers();
                try{this.bannerImageFile = course.getBannerImageFile().getPath();}catch(Exception e){this.bannerImageFile = "";}
                this.batchUId = course.getBatchUid();
                try{this.buttonStyle = course.getButtonStyle().getDescription();}catch(Exception e){this.buttonStyle = "";}
                try{this.cartridgeDescription = course.getCartridge().getDescription();}catch(Exception e){this.cartridgeDescription = "";}
                try{this.classification = course.getClassification().getTitle();}catch(Exception e){this.classification = "";}
                this.durationType = course.getDurationType().toFieldName();
                this.endDate = extractDate(course.getEndDate());
                this.enrollment = course.getEnrollmentType().toFieldName();
                this.institution = course.getInstitutionName();
                this.localeEnforced = course.getIsLocaleEnforced();
                this.lockedOut = course.getIsLockedOut();
                this.navigationCollapsible = course.getIsNavCollapsible();
                this.locale = course.getLocale();
                this.navigationBackgroundColour = course.getNavColorBg();
                this.navigationForegroundColour = course.getNavColorFg();
                this.navigationStyle = course.getNavStyle().toFieldName();
                this.numberOfDaysOfUse = course.getNumDaysOfUse();
                this.paceType = course.getPaceType().toFieldName();
                this.serviceLevelType = course.getServiceLevelType().toFieldName();
                this.softLimit = Long.toString(course.getSoftLimit());
                this.startDate = extractDate(course.getStartDate());
                this.uploadLimit = Long.toString(course.getUploadLimit());
            case standard:
                this.courseBbId = course.getId().toExternalString();
                this.title = course.getTitle();
                this.description = course.getDescription();
                this.creationDate = extractDate(course.getCreatedDate());
                this.modifiedDate = extractDate(course.getModifiedDate());
                this.available = course.getIsAvailable();
            case minimal:
                this.courseId = course.getCourseId();
            return;
            default: throw new Exception("Undefined verbosity of course");
        }
    }

    public String getCourseBbId()
    {
	return this.courseBbId;
    }

    public void setCourseBbId(String courseBbId)
    {
	this.courseBbId = courseBbId;
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

    public Boolean getAllowGuests()
    {
	return this.allowGuests;
    }

    public void setAllowGuests(Boolean allowGuests)
    {
	this.allowGuests = allowGuests;
    }

    public Boolean getAllowObservers()
    {
	return this.allowObservers;
    }

    public void setAllowObservers(Boolean allowObservers)
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
    public String getBatchUId()
    {
	return this.batchUId;
    }

    public void setBatchUId(String batchUId)
    {
	this.batchUId = batchUId;
    }
    public String getButtonStyle()
    {
	return this.buttonStyle;
    }

    public void setButtonStyle(String buttonStyle)
    {
	this.buttonStyle = buttonStyle;
    }
    public String getCartridgeDescription()
    {
	return this.cartridgeDescription;
    }

    public void setCartridgeDescription(String cartridgeDescription)
    {
	this.cartridgeDescription = cartridgeDescription;
    }
    public String getClassification()
    {
	return this.classification;
    }

    public void setClassification(String classification)
    {
	this.classification = classification;
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
    public String getEnrollment()
    {
	return this.enrollment;
    }

    public void setEnrollment(String enrollment)
    {
	this.enrollment = enrollment;
    }
    public String getInstitution()
    {
	return this.institution;
    }

    public void setInstitution(String institution)
    {
	this.institution = institution;
    }

    public Boolean getLocaleEnforced()
    {
	return this.localeEnforced;
    }

    public void setLocaleEnforced(Boolean localeEnforced)
    {
	this.localeEnforced = localeEnforced;
    }

    public String getLocale()
    {
	return this.locale;
    }

    public void setLocale(String locale)
    {
	this.locale = locale;
    }

    public Boolean getNavigationCollapsible()
    {
	return this.navigationCollapsible;
    }

    public void setNavigationCollapsible(Boolean navigationCollapsible)
    {
	this.navigationCollapsible = navigationCollapsible;
    }

    public Boolean getLockedOut()
    {
	return this.lockedOut;
    }

    public void setLockedOut(Boolean lockedOut)
    {
	this.lockedOut = lockedOut;
    }

    public String getNavigationBackgroundColour()
    {
	return this.navigationBackgroundColour;
    }

    public void setNavigationBackgroundColour(String navigationBackgroundColour)
    {
	this.navigationBackgroundColour = navigationBackgroundColour;
    }
    public String getNavigationForegroundColour()
    {
	return this.navigationForegroundColour;
    }

    public void setNavigationForegroundColour(String navigationForegroundColour)
    {
	this.navigationForegroundColour = navigationForegroundColour;
    }
    public String getNavigationStyle()
    {
	return this.navigationStyle;
    }

    public void setNavigationStyle(String navigationStyle)
    {
	this.navigationStyle = navigationStyle;
    }
    public Integer getNumberOfDaysOfUse()
    {
	return this.numberOfDaysOfUse;
    }

    public void setNumberOfDaysOfUse(Integer numberOfDaysOfUse)
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
}
