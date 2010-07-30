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

package bbws.calendar;

import blackboard.data.calendar.CalendarEntry;
import java.util.Calendar;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBCalendarEntry
{
    private String calendarEntryBbId;
    private String description;
    private String endDate;
    private String externalType;
    private String startDate;

    public BBCalendarEntry(){}
    public BBCalendarEntry(CalendarEntry ce)
    {
	this.calendarEntryBbId = ce.getId().toExternalString();
	this.description = ce.getDescription().getText();
	this.endDate = extractDate(ce.getEndDate());
	this.externalType = ce.getType().toFieldName();
	this.startDate = extractDate(ce.getStartDate());
    }
    
    public String getCalendarEntryBbId()
    {
	return this.calendarEntryBbId;
    }
    
    public void setCalendarEntryBbId(String calendarEntryBbId)
    {
	this.calendarEntryBbId = calendarEntryBbId;
    }
    
    public String getDescription()
    {
	return this.description;
    }
    
    public void setDescription(String description)
    {
	this.description = description;
    }
    
    public String getEndDate()
    {
	return this.endDate;
    }
    
    public void setEndDate(String endDate)
    {
	this.endDate = endDate;
    }
    
    public String getExternalType()
    {
	return this.externalType;
    }
    
    public void setExternalType(String externalType)
    {
	this.externalType = externalType;
    }
    
    public String getStartDate()
    {
	return this.startDate;
    }
    
    public void setStartDate(String startDate)
    {
	this.startDate = startDate;
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
    
    private String[] getCalendarEntryDetails()
    {
	return new String[]{
	    this.calendarEntryBbId,
	    this.startDate,
	    this.endDate,
	    this.description,
	    this.externalType
	};
    }
}
