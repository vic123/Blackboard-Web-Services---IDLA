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

import java.util.Calendar;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public abstract class AbstractBBCourse
{
    protected String courseId;
    protected String creationDate;
    protected Boolean available;
    
    protected String extractDate(Calendar cal)
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

    public String getCourseId()
    {
	return this.courseId;
    }

    public void setCourseId(String courseId)
    {
	this.courseId = courseId;
    }

    public String getCreationDate()
    {
	return this.creationDate;
    }

    public void setCreationDate(String creationDate)
    {
	this.creationDate = creationDate;
    }

    public Boolean getAvailable()
    {
	return this.available;
    }

    public void setAvailable(Boolean available)
    {
	this.available = available;
    }
}
