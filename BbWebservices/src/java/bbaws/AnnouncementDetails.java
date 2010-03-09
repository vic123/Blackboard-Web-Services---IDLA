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

package bbaws;

import blackboard.data.announcement.Announcement;
import java.util.Calendar;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class AnnouncementDetails implements ReturnTypeInterface
{
    private String announcementBbId;
    private String body;
    private String endDate;
    private Boolean permanent;
    private String startDate;
    private String title;
    private String type;
    
    public AnnouncementDetails(){}
    public AnnouncementDetails(Announcement a)
    {
	this.announcementBbId = a.getId().toExternalString();
	this.body = a.getBody().getText();
	this.endDate = extractDate(a.getRestrictionEndDate());
	this.permanent = a.getIsPermanent();
	this.startDate = extractDate(a.getRestrictionStartDate());
	this.title = a.getTitle();
	this.type = a.getType().toFieldName();
    }
    
    public String getAnnouncementBbId()
    {
	return this.announcementBbId;
    }
    
    public void setAnnouncementBbId(String announcementBbId)
    {
	this.announcementBbId = announcementBbId;
    }
    
    public String getBody()
    {
	return this.body;
    }
    
    public void setBody(String body)
    {
	this.body = body;
    }

    public String getEndDate()
    {
	return this.endDate;
    }
    
    public void setEndDate(String endDate)
    {
	this.endDate = endDate;
    }
    
    public Boolean getPermanent()
    {
	return this.permanent;
    }
    
    public void setPermanent(Boolean permanent)
    {
	this.permanent = permanent;
    }

    public String getStartDate()
    {
	return this.startDate;
    }
    
    public void setStartDate(String startDate)
    {
	this.startDate = startDate;
    }
    
    public String getTitle()
    {
	return title;
    }
    
    public void setTitle(String title)
    {
	this.title = title;
    }

    public String getType()
    {
	return this.type;
    }
    
    public void setType(String type)
    {
	this.type = type;
    }

    private String extractDate(Calendar cal)
    {
	try
	{
	    return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
	}
	catch (Exception e)
	{
	    return "Never";
	}
    }
    
    private String[] getAnnouncementDetails()
    {
	return new String[]{
	  this.announcementBbId,
	  this.startDate,
	  this.endDate,
	  this.title,
	  this.body,
	  this.type,
	  Boolean.toString(this.permanent)
	};
    }
    
    public String[] toStringArray()
    {
	return getAnnouncementDetails();
    }

    public String[] toStringArrayHeader()
    {
	return new String[]{
	    "AnnouncementBbId",
	    "Start Date",
	    "End Date",
	    "Title",
	    "Body",
	    "Type",
	    "Permanent"
	};
    }
}
