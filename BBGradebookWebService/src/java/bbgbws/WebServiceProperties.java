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

package bbgbws;

import blackboard.data.registry.SystemRegistryEntry;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.registry.SystemRegistryEntryDbLoader;
import blackboard.platform.BbServiceManager;

/**
 *
 * @author Andrew
 */
public class WebServiceProperties
{
    private String vendorId;
    private String applicationHandle;

    private WebServiceProperties(){}
    public WebServiceProperties(String vendorId, String applicationHandle)
    {
	this.vendorId = vendorId;
	this.applicationHandle = applicationHandle;
    }

    public String getConfigProperty(String param)
    {
	SystemRegistryEntry sre = new SystemRegistryEntry();

	try
	{
	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    SystemRegistryEntryDbLoader srdbl = (SystemRegistryEntryDbLoader)bbPm.getLoader(SystemRegistryEntryDbLoader.TYPE);
	    sre = srdbl.loadByKey(vendorId+"-"+applicationHandle+"-"+param);
	}
	catch(KeyNotFoundException knfe){return "Error: Given parameter not found";}
	catch(Exception e){return e.toString();}

	if(sre.getDescription().contains("IDLA"))
	{
	    return sre.getValue();
	}
	else
	{
	    return "Error: Access denied on property";
	}
    }

    public boolean isMethodAccessible(String method)
    {
	String isMethodAccessible = getConfigProperty(method+".access");

	if(isMethodAccessible!=null && isMethodAccessible.equalsIgnoreCase("Yes"))
	{
	    return true;
	}

	return false;
    }
    
    public boolean usingPassword()
    {
	String usingPwd = getConfigProperty("pwd.use");

	if(usingPwd!=null && usingPwd.equalsIgnoreCase("Yes"))
	{
	    return true;
	}

	return false;
    }

    public boolean passwordMatches(String pwd)
    {
	String configPwd = getConfigProperty("pwd.pwd");

	if(configPwd!=null && pwd!=null && configPwd.equals(pwd))
	{
	    return true;
	}

	return false;
    }
}
