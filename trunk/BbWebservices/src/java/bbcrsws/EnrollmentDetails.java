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

import blackboard.admin.data.course.Enrollment;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class EnrollmentDetails extends AbstractCourseDetails implements ReturnTypeInterface
{
    private String userRole;
    
    EnrollmentDetails(){}
    EnrollmentDetails(Enrollment enr)
    {
        this.available = enr.getIsAvailable();
        this.creationDate = extractDate(enr.getEnrollmentDate());
        this.courseId = enr.getCourseSiteBatchUid();
        this.userRole = enr.getRole().toFieldName();
        this.bbId = enr.getId().toExternalString(); //vic
    }

    public String getUserRole()
    {
	return this.userRole;
    }

    public void setUserRole(String userRole)
    {
	this.userRole = userRole;
    }
    
    public String[] toStringArray()
    {
	return new String[]{this.courseId,
			    Boolean.toString(this.available),
			    this.creationDate,
			    this.userRole
	};
    }

    public String[] toStringArrayHeader()
    {
	return new String[]{"Course Id",
			    "Available",
			    "Date Created",
			    "User Role"
	};
    }
/**
 *
 * @author vic
 */
    private String bbId;
    @Override public String getBbId() {
        return this.bbId;
    }
    @Override public void setBbId(String id) {
        this.bbId = id;
    }
    /*
    @Override public String getBatchUid() {
        return this.bbId;
    }
    @Override public void setBatchUid(String batchUid) {
        this.bbId = batchUid;
    }*/
}
