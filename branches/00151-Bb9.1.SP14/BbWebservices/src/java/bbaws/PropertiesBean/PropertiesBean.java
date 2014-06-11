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
package bbaws.PropertiesBean;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.data.registry.*;
import blackboard.persist.*;
import blackboard.persist.registry.*;
import blackboard.platform.*;


public class PropertiesBean implements java.io.Serializable
{	
	private static PropertiesBean propertiesBean;
	private static String vendorId = "amnl";
	private static String applicationHandle = "BBAnnouncementsWebService";
	private Properties p;
	
	public PropertiesBean()
	{
		super();
	} 

	public void setProperties( HttpServletRequest request ) throws Exception
	{
	    Enumeration params = request.getParameterNames();
	    BbPersistenceManager bbPm = null;
	    SystemRegistryEntryDbPersister srdbp = null;
	    SystemRegistryEntry sre = null;
	    String key = "";
	    String value = "";
	    //key not found
	    Boolean knf = false;

	    try
	    {
		    bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
		    srdbp = (SystemRegistryEntryDbPersister)bbPm.getPersister(SystemRegistryEntryDbPersister.TYPE);
	    }catch(Exception e){e.printStackTrace();}

	    boolean me = params.hasMoreElements();
	    while(me)
	    {
		sre = new SystemRegistryEntry();
		//System.err.println("Start of while loop");
		knf = false;
	        key = params.nextElement().toString();
		//System.err.println("key "+key);
		value = request.getParameter(key);
		//System.err.println("value "+value);

		try
		{
		    SystemRegistryEntryDbLoader srdbl = (SystemRegistryEntryDbLoader)bbPm.getLoader(SystemRegistryEntryDbLoader.TYPE);
		    sre = srdbl.loadByKey(vendorId+"-"+applicationHandle+"-"+key);
		}
		catch(KeyNotFoundException knfe){knf=true;}
		catch(Exception e){e.printStackTrace();}

		//System.err.println("knf="+knf+":sreValue="+sre.getValue()+":value="+value+"!sre==value="+!sre.getValue().equals(value));
		
		if(knf || !sre.getValue().equals(value))
		{
		    try
		    {
			sre.setKey(vendorId+"-"+applicationHandle+"-"+key);
			sre.setValue(value);
			sre.setDescription(vendorId+applicationHandle);
			srdbp.persist(sre);

			//System.err.println("persisting="+sre.getKey());
		    }catch(Exception e){e.printStackTrace();}
		}
		me = params.hasMoreElements();
		//System.err.println("boolean:"+me);
	    }
	}

	public static PropertiesBean getInstance()
	{
		   if (propertiesBean == null) {
			   propertiesBean = new PropertiesBean();
		   }
		   //System.out.println( "PropertiesManager getInstance() Initialized" );
		   return propertiesBean;
	}

	// Reading property files.  Split into 2 methods.
	public Properties getProperties() throws Exception
	{
	    Properties properties = new Properties();
	    BbPersistenceManager bbPm = null;
	    SystemRegistryEntry sre = null;
	    
	    try
	    {
		    bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    }catch(Exception e){}//e.printStackTrace();}

	    try
	    {
		SystemRegistryEntryDbLoader srdbl = (SystemRegistryEntryDbLoader)bbPm.getLoader(SystemRegistryEntryDbLoader.TYPE);
		Registry r = srdbl.loadRegistry();
		Collection c = r.entries();
		Iterator i = c.iterator();
		int inc=1;
		
		while(i.hasNext())
		{
		    Object o = i.next();
		    if(o!=null)
		    {
			sre = (SystemRegistryEntry)o;
			if(sre!=null && sre.getKey().startsWith(vendorId+"-"+applicationHandle+"-"))
			{
			    //System.err.println("key found..."+sre.getKey());
			    properties.setProperty(sre.getKey().substring((vendorId+"-"+applicationHandle+"-").length()),sre.getValue());
			}
		    }
		    inc++;
		}
	    }
	    catch(KeyNotFoundException knfe){}
	    catch(NullPointerException npe){npe.printStackTrace();}
	    catch(Exception e){}//e.printStackTrace();}

	    return properties;
	} 

	public String getProperty(String propertyName) 
	{
		String _prop = "";
		//System.err.println("Attempting to get property "+propertyName);
		try {
			Properties p = this.getProperties();
			if(p!=null)
			{
			    _prop = p.getProperty(propertyName);
			    //System.err.println("getting property: "+propertyName+" returned="+_prop);
			}else{_prop = "";}
		} catch (Exception e) {
			//System.err.println(propertyName+" getProperty catch statement");
			e.printStackTrace();
			_prop = "";
		}
	    return _prop;
	}  
	
	public void setProperty(String key, String value) throws Exception
	{
		p.setProperty(key, value);
	} 
}


