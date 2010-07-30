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

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public abstract class AbstractBBContent
{
    protected Boolean available;
    protected String contentBbId;
    protected String dataType;
    protected Integer position;
    protected String url;

    public Boolean getAvailable()
    {
	return this.available;
    }

    public void setAvailable(Boolean available)
    {
	this.available = available;
    }

    public String getDataType()
    {
	return this.dataType;
    }

    public void setDataType(String dataType)
    {
	this.dataType = dataType;
    }

    public String getContentBbId()
    {
	return this.contentBbId;
    }

    public void setContentBbId(String contentBbId)
    {
	this.contentBbId = contentBbId;
    }

    public Integer getPosition()
    {
	return this.position;
    }

    public void setPosition(Integer position)
    {
	this.position = position;
    }

    public String getUrl()
    {
	return this.url;
    }

    public void setUrl(String url)
    {
	this.url = url;
    }
}
