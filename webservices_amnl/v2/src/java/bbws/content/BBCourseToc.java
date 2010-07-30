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
import blackboard.data.navigation.CourseToc;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBCourseToc extends AbstractBBContent
{
    private Boolean entryPoint;
    private String internalHandle;
    private String label;
    private String targetType;

    public BBCourseToc(){}
    public BBCourseToc(CourseToc ct)
    {
	this.available = ct.getIsEnabled();
	this.contentBbId = ct.getContentId().toExternalString();
	this.dataType = ct.getDataType().toString();
	this.entryPoint = ct.getIsEntryPoint();
	this.internalHandle = ct.getInternalHandle();
	this.label = ct.getLabel();
	this.position = ct.getPosition();
	this.targetType = ct.getTargetType().toFieldName();
	this.url = ct.getUrl();
    }
    
    public Boolean getEntryPoint()
    {
	return this.entryPoint;
    }
    
    public void setEntryPoint(Boolean entryPoint)
    {
	this.entryPoint = entryPoint;
    }
    
    public String getInternalHandle()
    {
	return this.internalHandle;
    }
    
    public void setInternalHandle(String internalHandle)
    {
	this.internalHandle = internalHandle;
    }
    
    public String getLabel()
    {
	return this.label;
    }
    
    public void setLabel(String label)
    {
	this.label = label;
    }
    
    public String getTargetType()
    {
	return this.targetType;
    }
    
    public void setTargetType(String targetType)
    {
	this.targetType = targetType;
    }
    
    private String[] getCourseTocDetails()
    {
	return new String[]{this.contentBbId,
			    this.dataType,
			    this.internalHandle,
			    Boolean.toString(this.available),
			    Boolean.toString(this.entryPoint),
			    this.label,
			    Integer.toString(this.position),
			    this.targetType,
			    this.url
	};
    }

}
