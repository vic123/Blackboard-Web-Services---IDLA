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

import blackboard.data.course.CourseMembership;
import java.util.Calendar;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class CourseMembershipDetails extends bbwscommon.BbWsDataDetails implements ReturnTypeInterface
{

    public String getChildCourseId() {
        return childCourseId;
    }

    /**
     * @param childCourseId the childCourseId to set
     */
    public void setChildCourseId(String childCourseId) {
        this.childCourseId = childCourseId;
    }

    /**
     * @return the courseBatchUid
     */
    public String getCourseBatchUid() {
        return courseBatchUid;
    }

    /**
     * @param courseBatchUid the courseBatchUid to set
     */
    public void setCourseBatchUid(String courseBatchUid) {
        this.courseBatchUid = courseBatchUid;
    }

    /**
     * @return the userBatchUid
     */
    public String getUserBatchUid() {
        return userBatchUid;
    }

    /**
     * @param userBatchUid the userBatchUid to set
     */
    public void setUserBatchUid(String userBatchUid) {
        this.userBatchUid = userBatchUid;
    }

    public enum Verbosity{minimal,standard}

    private Verbosity verbosity;
    private String available;
    private String cartridgeAccess;
    private String courseBbId;
    private String courseBatchUid;
    private String bbId;
    private String dataSourceBbId;
    private String enrollmentDate;
    private String introduction;
    private String lastAccessDate;
    private String modifiedDate;
    private String notes;
    private String personalInfo;
    private String role;
    private String userBbId;
    //slow to obtain, CourseMembership.User object is not always available
    //may be it is availavle only with loadByCourseIdWithUserInfo
    private String userBatchUid;
    //private String receiveEmail;
    private String childCourseId;
    
    public CourseMembershipDetails(){}
    public CourseMembershipDetails(CourseMembership cm, Verbosity verbosity) throws Exception
    {
        this.verbosity = verbosity;
        switch(verbosity)
        {
            case standard:
                this.available = Boolean.toString(cm.getIsAvailable());
                this.cartridgeAccess = Boolean.toString(cm.getHasCartridgeAccess());
                this.courseBbId = cm.getCourseId().toExternalString();
                this.dataSourceBbId = cm.getDataSourceId().toExternalString();
                this.enrollmentDate = extractDate(cm.getEnrollmentDate());
                this.introduction = cm.getIntroduction();
                this.lastAccessDate = extractDate(cm.getLastAccessDate());
                this.modifiedDate = extractDate(cm.getModifiedDate());
                this.notes = cm.getNotes();
                this.personalInfo = cm.getPersonalInfo();
                this.role = cm.getRole().toFieldName();
                this.userBbId = cm.getUserId().toExternalString();
                //cm.getUser() is usually null
                //this.userBatchUid = cm.getUser().getBatchUid();
            case minimal:
                this.bbId = cm.getId().toExternalString();
            return;
            default: throw new Exception("Undefined verbosity of course membership");
        }
    }

    public String getAvailable()
    {
	return this.available;
    }

    public void setAvailable(String available)
    {
	this.available = available;
    }

    public String getCartridgeAccess()
    {
	return this.cartridgeAccess;
    }

    public void setCartridgeAccess(String cartridgeAccess)
    {
	this.cartridgeAccess = cartridgeAccess;
    }

    public String getCourseBbId()
    {
	return this.courseBbId;
    }
    
    public void setCourseBbId(String courseBbId)
    {
	this.courseBbId = courseBbId;
    }

    @Override
    public String getBbId()
    {
        return this.bbId;
    }

    @Override
    public void setBbId(String courseMembershipBbId)
    {
        this.bbId = courseMembershipBbId;
    }

    public String getDataSourceBbId()
    {
	return this.dataSourceBbId;
    }

    public void setDataSourceBbId(String dataSourceBbId)
    {
	this.dataSourceBbId = dataSourceBbId;
    }

    public String getEnrollmentDate()
    {
	return this.enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate)
    {
	this.enrollmentDate = enrollmentDate;
    }

    public String getIntroduction()
    {
	return this.introduction;
    }

    public void setIntroduction(String introduction)
    {
	this.introduction = introduction;
    }

    public String getLastAccessDate()
    {
	return this.lastAccessDate;
    }

    public void setLastAccessDate(String lastAccessDate)
    {
	this.lastAccessDate = lastAccessDate;
    }

    public String getModifiedDate()
    {
	return this.modifiedDate;
    }

    public void setModifiedDate(String modifiedDate)
    {
	this.modifiedDate = modifiedDate;
    }

    public String getNotes()
    {
	return this.notes;
    }

    public void setNotes(String notes)
    {
	this.notes = notes;
    }

    public String getPersonalInfo()
    {
	return this.personalInfo;
    }

    public void setPersonalInfo(String personalInfo)
    {
	this.personalInfo = personalInfo;
    }

    public String getRole()
    {
	return this.role;
    }

    public void setRole(String role)
    {
	this.role = role;
    }

    public String getUserBbId()
    {
	return this.userBbId;
    }

    public void setUserBbId(String userBbId)
    {
	this.userBbId = userBbId;
    }

    private String extractDate(Calendar cal)
    {
	try
	{
	    return cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	}
	catch(Exception e)
	{
	    return "Never";
	}
    }
    
    public String[] toStringArray()
    {
        String avlbl = null, cAccss = null;
        try{avlbl = available;}catch(Exception e){}
        try{cAccss = cartridgeAccess;}catch(Exception e){}
        switch(verbosity)
        {
            case standard:
                return new String[]{avlbl,
                                    cAccss,
                                    this.courseBbId,
                                    this.bbId,
                                    this.dataSourceBbId,
                                    this.enrollmentDate,
                                    this.introduction,
                                    this.lastAccessDate,
                                    this.modifiedDate,
                                    this.notes,
                                    this.personalInfo,
                                    this.role,
                                    this.userBbId};
            case minimal:
                return new String[]{this.bbId};
            default:
                return new String[]{};
        }
    }

    public String[] toStringArrayHeader()
    {
        switch(verbosity)
        {
            case standard:
                return new String[]{"Available",
                            "CartridgeAccess",
                            "Course BbId",
                            "CourseMembership BbId",
                            "Data Source BbId",
                            "Enrollment Date",
                            "Introduction",
                            "Last Access Date",
                            "Modified Date",
                            "Notes",
                            "Personal Info",
                            "Role",
                            "User BbId"};
            case minimal:
                return new String[]{"CourseMembership BbId"};
            default:
                return new String[]{};
        }
    }

/**
 *
 * @author vic
 */

    /*
    }*/

}
