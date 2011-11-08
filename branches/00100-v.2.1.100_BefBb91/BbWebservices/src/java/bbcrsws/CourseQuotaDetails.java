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

import blackboard.data.course.CourseQuota;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class CourseQuotaDetails implements ReturnTypeInterface
{
    private Long courseAbsoluteLimit;
    private Long courseAbsoluteLimitRemainingSize;
    private Long courseSize;
    private Long courseSoftLimit;
    private Long courseUploadLimit;
    private Boolean enforceQuota;
    private Boolean enforceUploadLimit;
    private Long systemAbsoluteLimit;
    private Long systemSoftLimit;
    private Long systemUploadLimit;

    public CourseQuotaDetails(){}
    public CourseQuotaDetails(CourseQuota courseQuota)
    {
	this.courseAbsoluteLimit = courseQuota.getCourseAbsoluteLimit();
	this.courseAbsoluteLimitRemainingSize = courseQuota.getCourseAbsoluteLimitRemainingSize();
	this.courseSize = courseQuota.getCourseSize();
	this.courseSoftLimit = courseQuota.getCourseSoftLimit();
	this.courseUploadLimit = courseQuota.getCourseUploadLimit();
	this.enforceQuota = courseQuota.getEnforceQuota();
	this.enforceUploadLimit = courseQuota.getEnforceUploadLimit();
	this.systemAbsoluteLimit = courseQuota.getSystemAbsoluteLimit();
	this.systemSoftLimit = courseQuota.getSystemSoftLimit();
	this.systemUploadLimit = courseQuota.getSystemUploadLimit();
    }
    
    public Long getCourseAbsoluteLimit()
    {
	return this.courseAbsoluteLimit;
    }

    public void setCourseAbsoluteLimit(Long courseAbsoluteLimit)
    {
	this.courseAbsoluteLimit = courseAbsoluteLimit;
    }

    public Long getCourseAbsoluteLimitRemainingSize()
    {
	return this.courseAbsoluteLimitRemainingSize;
    }

    public void setCourseAbsoluteLimitRemainingSize(Long courseAbsoluteLimitRemainingSize)
    {
	this.courseAbsoluteLimitRemainingSize = courseAbsoluteLimitRemainingSize;
    }

    public Long getCourseSize()
    {
	return this.courseSize;
    }

    public void setCourseSize(Long courseSize)
    {
	this.courseSize = courseSize;
    }

    public Long getCourseSoftLimit()
    {
	return this.courseSoftLimit;
    }

    public void setCourseSoftLimit(Long courseSoftLimit)
    {
	this.courseSoftLimit = courseSoftLimit;
    }

    public Long getCourseUploadLimit()
    {
	return this.courseUploadLimit;
    }

    public void setCourseUploadLimit(Long courseUploadLimit)
    {
	this.courseUploadLimit = courseUploadLimit;
    }

    public Boolean getEnforceQuota()
    {
	return this.enforceQuota;
    }

    public void setEnforceQuota(Boolean enforceQuota)
    {
	this.enforceQuota = enforceQuota;
    }

    public Boolean getEnforceUploadLimit()
    {
	return this.enforceUploadLimit;
    }

    public void setEnforceUploadLimit(Boolean enforceUploadLimit)
    {
	this.enforceUploadLimit = enforceUploadLimit;
    }

    public Long getSystemAbsoluteLimit()
    {
	return this.systemAbsoluteLimit;
    }

    public void setSystemAbsoluteLimit(Long systemAbsoluteLimit)
    {
	this.systemAbsoluteLimit = systemAbsoluteLimit;
    }

    public Long getSystemSoftLimit()
    {
	return this.systemSoftLimit;
    }

    public void setSystemSoftLimit(Long systemSoftLimit)
    {
	this.systemSoftLimit = systemSoftLimit;
    }

    public Long getSystemUploadLimit()
    {
	return this.systemUploadLimit;
    }

    public void setSystemUploadLimit(Long systemUploadLimit)
    {
	this.systemUploadLimit = systemUploadLimit;
    }

    public String[] toStringArray()
    {
	return new String[]{Long.toString(this.courseAbsoluteLimit),Long.toString(this.courseAbsoluteLimitRemainingSize),
			    Long.toString(this.courseSize),Long.toString(this.courseSoftLimit),
			    Long.toString(this.courseUploadLimit),Boolean.toString(this.enforceQuota),
			    Boolean.toString(this.enforceUploadLimit),Long.toString(this.systemAbsoluteLimit),
			    Long.toString(this.systemSoftLimit),Long.toString(this.systemUploadLimit)};
    }

    public String[] toStringArrayHeader()
    {    
	return new String[]{"course absolute limit","course absolute limit remaining",
			    "course size","course soft limit",
			    "course upload limit","course enforced quota",
			    "course enforced upload limit","system absolute limit",
			    "system soft limit","system upload limit"};
    }
}
