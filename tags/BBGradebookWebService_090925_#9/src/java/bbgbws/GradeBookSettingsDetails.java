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

package bbgbws;

import blackboard.data.gradebook.impl.GradeBookSettings;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class GradeBookSettingsDetails
{
    private Boolean averageDisplayed;
    private Boolean commentsDisplayed;
    private Boolean firstLastDisplayed;
    private String gradeBookSettingBbId;
    private Boolean lastFirstDisplayed;
    private Boolean studentIdDisplayed;
    private Boolean userIdDisplayed;
    private String weightType;
    //??    gs.getPublicItemId();

    public GradeBookSettingsDetails(){}
    public GradeBookSettingsDetails(GradeBookSettings gbs)
    {
	this.averageDisplayed = gbs.isAverageDisplayed();
	this.commentsDisplayed = gbs.areCommentsDisplayed();
	this.firstLastDisplayed = gbs.isFirstLastDisplayed();
	this.gradeBookSettingBbId = gbs.getId().toExternalString();
	this.lastFirstDisplayed = gbs.isLastFirstDisplayed();
	this.studentIdDisplayed = gbs.isStudentIdDisplayed();
	this.userIdDisplayed = gbs.isUserIdDisplayed();
	this.weightType = gbs.getWeightType().toFieldName();
    }
    
    public Boolean getAverageDisplayed()
    {
	return this.averageDisplayed;
    }
    
    public void setAverageDisplayed(Boolean averageDisplayed)
    {
	this.averageDisplayed = averageDisplayed;
    }
    
    public Boolean getCommentsDisplayed()
    {
	return this.commentsDisplayed;
    }
    
    public void setCommentsDisplayed(Boolean commentsDisplayed)
    {
	this.commentsDisplayed = commentsDisplayed;
    }
    
    public Boolean getFirstLastDisplayed()
    {
	return this.firstLastDisplayed;
    }
    
    public void setFirstLastDisplayed(Boolean firstLastDisplayed)
    {
	this.firstLastDisplayed = firstLastDisplayed;
    }
    
    public String getGradeBookSettingBbId()
    {
	return this.gradeBookSettingBbId;
    }
    
    public void setGradeBookSettingBbId(String gradeBookSettingBbId)
    {
	this.gradeBookSettingBbId = gradeBookSettingBbId;
    }
    
    public Boolean getLastFirstDisplayed()
    {
	return this.lastFirstDisplayed;
    }
    
    public void setLastFirstDisplayed(Boolean lastFirstDisplayed)
    {
	this.lastFirstDisplayed = lastFirstDisplayed;
    }
    
    public Boolean getStudentIdDisplayed()
    {
	return this.studentIdDisplayed;
    }
    
    public void setStudentIdDisplayed(Boolean studentIdDisplayed)
    {
	this.studentIdDisplayed = studentIdDisplayed;
    }

    public Boolean getUserIdDisplayed()
    {
	return this.userIdDisplayed;
    }
    
    public void setUserIdDisplayed(Boolean userIdDisplayed)
    {
	this.userIdDisplayed = userIdDisplayed;
    }
    
    public String getWeightType()
    {
	return this.weightType;
    }
    
    public void setWeightType(String weightType)
    {
	this.weightType = weightType;
    }
    
    public String[] getGradeBookSettingsDetails()
    {
	return new String[]{
	    this.gradeBookSettingBbId,
	    Boolean.toString(this.commentsDisplayed),
	    this.weightType,
	    Boolean.toString(this.averageDisplayed),
	    Boolean.toString(this.firstLastDisplayed),
	    Boolean.toString(this.lastFirstDisplayed),
	    Boolean.toString(this.studentIdDisplayed),
	    Boolean.toString(this.userIdDisplayed)
	};
    }
    
    public String[] toStringArray()
    {
	return getGradeBookSettingsDetails();
    }
    
    public String[] toStringArrayHeader()
    {
	return new String[]{"GradeBookSettingBbId","Comments Displayed",
				"Weight Type","Average Displayed",
				"First/Last Displayed","Last/First Displayed",
				"Student Id Displayed","User Id Displayed"};
    }
}
