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

import blackboard.data.content.ContentFile;
import java.util.Calendar;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBContentFile
{
    private String action;
    private String contentFileBbId;
    private String dataType;
    private String linkName;
    private String modifiedDate;
    private String name;
    private Long size;
    private String storageType;

    public BBContentFile(){}
    public BBContentFile(ContentFile cf)
    {
	this.action = cf.getAction().toFieldName();
	this.contentFileBbId = cf.getId().toExternalString();
	this.dataType = cf.getDataType().getName();
	this.linkName = cf.getLinkName();
	this.modifiedDate = extractDate(cf.getModifiedDate());
	this.name = cf.getName();
	this.size = cf.getSize();
	this.storageType = cf.getStorageType().toFieldName();
    }
    
    public String getAction()
    {
	return this.action;
    }
    
    public void setAction(String action)
    {
	this.action = action;
    }
    
    public String getContentFileBbId()
    {
	return this.contentFileBbId;
    }
    
    public void setContentFileBbId(String contentFileBbId)
    {
	this.contentFileBbId = contentFileBbId;
    }

    public String getDataType()
    {
	return this.dataType;
    }

    public void setDataType(String dataType)
    {
	this.dataType = dataType;
    }

    public String getLinkName()
    {
	return this.linkName;
    }

    public void setLinkName(String linkName)
    {
	this.linkName = linkName;
    }

    public String getModifiedDate()
    {
	return this.modifiedDate;
    }

    public void setModifiedDate(String modifiedDate)
    {
	this.modifiedDate = modifiedDate;
    }
    
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public Long getSize()
    {
	return this.size;
    }

    public void setSize(Long size)
    {
	this.size = size;
    }

    public String getStorageType()
    {
	return this.storageType;
    }

    public void setStorageType(String storageType)
    {
	this.storageType = storageType;
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

    private String[] getContentFileDetails()
    {
	return new String[]{"Id",
			    "Action",
			    "Data Type",
			    "Link Name",
			    "Modified Date",
			    "Name",
			    "Size",
			    "Storage Type"
	};
    }

}
