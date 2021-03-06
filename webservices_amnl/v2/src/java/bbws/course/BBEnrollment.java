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

import blackboard.admin.data.course.Enrollment;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBEnrollment extends AbstractBBCourse
{
    private String userRole;
    
    BBEnrollment(){}
    public BBEnrollment(Enrollment enr)
    {
        this.available = enr.getIsAvailable();
        this.creationDate = extractDate(enr.getEnrollmentDate());
        this.courseId = enr.getCourseSiteBatchUid();
        this.userRole = enr.getRole().toFieldName();
    }

    public String getUserRole()
    {
	return this.userRole;
    }

    public void setUserRole(String userRole)
    {
	this.userRole = userRole;
    }
}
