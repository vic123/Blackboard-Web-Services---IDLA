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

package bbws.content;

import bbws.course.AbstractBBContent;
import blackboard.data.content.Content;
import java.util.Calendar;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBContent extends AbstractBBContent
{
    private String body;
    private String contentHandler;
    private Boolean described;
    private String endDate;
    private Boolean folder;
    private Boolean lesson;
    private String modifiedDate;
    private Integer numOfFiles;
    private String offlineName;
    private String offlinePath;
    private String parentContentBbId;
    private String persistentTitle;
    private String renderType;
    private String startDate;
    private String title;
    private String titleColour;

    public BBContent(){}
    public BBContent(Content c)
    {
	this.available = c.getIsAvailable();
	this.body = c.getBody().getText();
	this.contentBbId = c.getId().toExternalString();
	this.contentHandler = c.getContentHandler();
	this.dataType = c.getDataType().getName();
	this.described = c.getIsDescribed();
	this.endDate = extractDate(c.getEndDate());
	this.folder = c.getIsFolder();
	this.lesson = c.getIsLesson();
	this.modifiedDate = extractDate(c.getModifiedDate());
	this.numOfFiles = c.getContentFiles().size();
	this.offlineName = c.getOfflineName();
	this.offlinePath = c.getOfflinePath();
	this.parentContentBbId = c.getParentId().toExternalString();
	this.persistentTitle = c.getPersistentTitle();
	this.position = c.getPosition();
	this.renderType = c.getRenderType().toFieldName();
	this.startDate = extractDate(c.getStartDate());
	this.title = c.getTitle();
	this.titleColour = c.getTitleColor();
	this.url = c.getUrl();
    }

    public String getBody()
    {
	return this.body;
    }
    
    public void setBody(String body)
    {
	this.body = body;
    }
    
    public String getContentHandler()
    {
	return this.contentHandler;
    }
    
    public void setContentHandler(String contentHandler)
    {
	this.contentHandler = contentHandler;
    }

    public Boolean getDescribed()
    {
	return this.described;
    }
    
    public void setDescribed(Boolean described)
    {
	this.described = described;
    }
    
    public String getEndDate()
    {
	return this.endDate;
    }
    
    public void setEndDate(String endDate)
    {
	this.endDate = endDate;
    }
    
    public Boolean getFolder()
    {
	return this.folder;
    }
    
    public void setFolder(Boolean folder)
    {
	this.folder = folder;
    }
    
    public Boolean getLesson()
    {
	return this.lesson;
    }

    public void setLesson(Boolean lesson)
    {
	this.lesson = lesson;
    }
    
    public String getModifiedDate()
    {
	return this.modifiedDate;
    }
    
    public void setModifiedDate(String modifiedDate)
    {
	this.modifiedDate = modifiedDate;
    }
    
    public Integer getNumOfFiles()
    {
	return this.numOfFiles;
    }
    
    public void setNumOfFiles(Integer numOfFiles)
    {
	this.numOfFiles = numOfFiles;
    }
    
    public String getOfflineName()
    {
	return this.offlineName;
    }
    
    public void setOfflineName(String offlineName)
    {
	this.offlineName = offlineName;
    }
    
    public String getOfflinePath()
    {
	return this.offlinePath;
    }
    
    public void setOfflinePath(String offlinePath)
    {
	this.offlinePath = offlinePath;
    }
    
    public String getParentContentBbId()
    {
	return this.parentContentBbId;
    }
    
    public void setParentContentBbId(String parentContentBbId)
    {
	this.parentContentBbId = parentContentBbId;
    }

    public String getPersistentTitle()
    {
	return this.persistentTitle;
    }
    
    public void setPersistentTitle(String persistentTitle)
    {
	this.persistentTitle = persistentTitle;
    }
    
    public String getRenderType()
    {
	return this.renderType;
    }

    public void setRenderType(String renderType)
    {
	this.renderType = renderType;
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
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public String getTitleColour()
    {
	return this.titleColour;
    }
    
    public void setTitleColour(String titleColour)
    {
	this.titleColour = titleColour;
    }

    //@SuppressWarnings("static-access")
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

    private String[] getContentDetails()
    {
	return new String[]{"ContentBbId",
			    "ParentContentBbId",
			    "Position",
			    "Folder",
			    "Described",
			    "Lesson",
			    "Available",
			    "Start Date",
			    "Modified Date",
			    "End Date",
			    "Content Handler",
			    "Data Type",
			    "Render Type",
			    "Offline Path",
			    "Offline Name",
			    "Url",
			    "Title",
			    "Title Colour",
			    "Persistent Title",
			    "Body",
			    "Num. Of Files"
	};
    }

}
