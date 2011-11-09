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

//blackboard
import blackboard.persist.gradebook.impl.OutcomeDefinitionDbLoader;
import blackboard.persist.gradebook.impl.OutcomeDefinitionDbPersister;
import blackboard.data.gradebook.Lineitem;
import blackboard.data.course.CourseMembership;
import blackboard.data.gradebook.Score;
import blackboard.data.gradebook.impl.Attempt;
import blackboard.data.gradebook.impl.GradeBookSettings;
import blackboard.data.gradebook.impl.Outcome;
import blackboard.data.gradebook.impl.OutcomeDefinition;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.gradebook.LineitemDbLoader;
import blackboard.persist.gradebook.LineitemDbPersister;
import blackboard.persist.gradebook.ScoreDbLoader;
import blackboard.persist.gradebook.impl.AttemptDbLoader;
import blackboard.persist.gradebook.impl.AttemptDbPersister;
import blackboard.persist.gradebook.impl.GradeBookSettingsDbLoader;
import blackboard.persist.gradebook.impl.GradeBookSettingsDbPersister;
import blackboard.persist.gradebook.impl.OutcomeDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.BbServiceManager;

//java
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

//javax
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.annotation.Resource;
import blackboard.platform.log.LogService;


//import blackboard.platform.gradebook2.*;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService
public class BBGradebookWebService_BefBb91
{
@Resource
public static javax.xml.ws.WebServiceContext wsContextStatic;

@Resource
private javax.xml.ws.WebServiceContext wsContext;

    private WebServiceProperties wsp = new WebServiceProperties("IDLA","BbWebservices");

    private Boolean addLineitemToGivenCourseId(String courseId, String name,
                                                String type, Float pointsPossible, Float weight,
                                                Boolean available) throws WebServiceException
    {
	try
	{
	    Lineitem li = new Lineitem();
	    li.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
	    //li.setAssessmentLocation(Lineitem.AssessmentLocation.INTERNAL);
	    li.setName(name);
	    li.setIsAvailable(available);
	    li.setPointsPossible(pointsPossible);
	    li.setType(type);
	    li.setWeight(weight);
	    ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not add lineitem "+e.toString());
	}
	return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean addLineitemToGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addLineitemToGivenCourseId");
        return addLineitemToGivenCourseId(courseId, name, type, pointsPossible, weight, available);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String addLineitemToGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addLineitemToGivenCourseIdXML");
        return toXML(null,addLineitemToGivenCourseId(courseId, name, type, pointsPossible, weight, available));
    }

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
	if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
	{
	    throw new WebServiceException("Access Denied");
	}
    }

    private Boolean deleteGivenAttemptByAttemptBbId(String attemptBbId) throws WebServiceException
    {
	try
	{
	    AttemptDbPersister.Default.getInstance().deleteById(AttemptDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Attempt.DATA_TYPE,attemptBbId)).getId());
	}
	catch(Exception e)
	{
	    throw new WebServiceException( "Error: Could not delete attempt - "+e.getMessage());
	}
	return true;
    }
    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteGivenAttemptByAttemptBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbId");
        return deleteGivenAttemptByAttemptBbId(attemptBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGivenAttemptByAttemptBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbIdXML");
        return toXML(null,deleteGivenAttemptByAttemptBbId(attemptBbId));
    }

    private Boolean deleteOutcomeDefinitionByOutcomeDefBbId(String outcomeDefBbId) throws WebServiceException
    {
    	try
	{
	    OutcomeDefinitionDbPersister.Default.getInstance().deleteById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId));
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Does that item/outcome exist? "+e.toString());
	}
	return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteLineItemByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineItemBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"deleteLineItemByLineItemBbId");
	return deleteOutcomeDefinitionByOutcomeDefBbId(lineItemBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteLineItemByLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineItemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteLineItemByLineItemBbIdXML");
        return toXML(null,deleteOutcomeDefinitionByOutcomeDefBbId(lineItemBbId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteOutcomeDefinitionByOutcomeDefBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"deleteOutcomeDefinitionByOutcomeDefBbId");
	return deleteOutcomeDefinitionByOutcomeDefBbId(outcomeDefBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteOutcomeDefinitionByOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteOutcomeDefinitionByOutcomeDefBbIdXML");
        return toXML(null,deleteOutcomeDefinitionByOutcomeDefBbId(outcomeDefBbId));
    }

    private List<LineitemDetails> getAllLineitemObjsForCourseId(String courseId) throws Exception
    {
        List<LineitemDetails> rl = new ArrayList<LineitemDetails>();
        Iterator i = null;
        //try{System.err.println(new blackboard.platform.plugin.PlugInManager().getPlatformVersion().toString());}catch(Exception e){System.err.println("Could not retrieve version info");}
        //if(true) - this should be a version check instead of (just) a try/catch block
        //{
            try
            {
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Class gradebookManagerFactoryClass = Class.forName(\"blackboard.platform.gradebook2.GradebookManagerFactory\");");
                Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod(\"getInstanceWithoutSecurityCheck\").invoke(gradebookManagerFactoryClass.newInstance());");
                Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
                //blackboard.platform.gradebook2.GradebookManager gm = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();

                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Class gbmClass = gradebookManager.getClass();");
                Class gbmClass = gradebookManager.getClass();
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): List l = (List)gbmClass.getDeclaredMethod(...");
                List l = (List)gbmClass.getDeclaredMethod("getGradebookItems",new Class[]{blackboard.persist.Id.class}).invoke(gradebookManager,new Object[]{((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId()});
                //List<blackboard.platform.gradebook2.GradableItem> l = gm.getGradebookItems(((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId());
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): i = l.iterator();"); 
                i = l.iterator();
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): while (i.hasNext())"); 
                while (i.hasNext())
                {
                    rl.add(new LineitemDetails(i.next()));
                }
                //or you will get an out of memory error...
                l.clear();
                l = null;
            }
            catch(ClassNotFoundException cnfe)
            {
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Catched, List<Lineitem> lil = ((LineitemDbLoader)BbServiceManager..."); 
                List<Lineitem> lil = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
                if(lil.size()<1)
                {
                    throw new Exception("No lineitems found");
                }
                i = lil.iterator();
                while(i.hasNext())
                {
                    rl.add(new LineitemDetails((Lineitem)i.next()));
                }
            }
        //}
        return rl;
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllLineItemsForCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =: List<LineitemDetails> lil = getAllLineitemObjsForCourseId(courseId);");
            List<LineitemDetails> lil = getAllLineitemObjsForCourseId(courseId);
            if(headerDesc)
            {
                BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    rl.add(new LineitemDetails().toStringArrayHeader());");
            rl.add(new LineitemDetails().toStringArrayHeader());
            }
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    Iterator i = lil.iterator();");
            Iterator i = lil.iterator();
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    while(i.hasNext())");
            while(i.hasNext())
            {
                BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    rl.add(((LineitemDetails)i.next()).toStringArray());");
            rl.add(((LineitemDetails)i.next()).toStringArray());
            }
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    return (String[][])rl.toArray(new String[rl.size()][]);");
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            BbWsLog.logForward(e, "getAllLineItemsForCourseId(@WebParam(name =:    throw new WebServiceException(...");
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllLineItemsForCourseIdWithNamedElements")
    public List<LineitemDetails> getAllLineItemsForCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "commonParams") CommonParams params)
    {
        javax.servlet.ServletContext sc = (javax.servlet.ServletContext)BBGradebookWebService.wsContextStatic.getMessageContext().get(javax.xml.ws.handler.MessageContext.SERVLET_CONTEXT);
        String value = sc.getInitParameter("logSeverityOverride");
        BbWsLog.logForward("getAllLineItemsForCourseIdWithNamedElements - String value = sc.getInitParameter value: " + value);        
        
        sc = (javax.servlet.ServletContext)wsContext.getMessageContext().get(javax.xml.ws.handler.MessageContext.SERVLET_CONTEXT);
        value = sc.getInitParameter("logSeverityOverride");
        BbWsLog.logForward("getAllLineItemsForCourseIdWithNamedElements - String value = sc.getInitParameter value: " + value);        

        LineitemParams lip = new LineitemParams(pwd, courseId, null, params);
        return getAllLineItemsForCourseIdWithNamedElementsAndParams(lip);
    }

    @WebMethod
    public List<LineitemDetails> getAllLineItemsForCourseIdWithNamedElementsAndParams(@WebParam(name = "lineitemParams") LineitemParams params)
    {
        AllLineItemsForCourseIdGetter getter 
                = new AllLineItemsForCourseIdGetter(params);
        runGetter(getter);
        //getter.run();
        return getter.result;
    }
    
   
    @WebMethod
    public LineitemDetails getLineitemDetailsForGivenLineitemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId, CommonParams params) throws WebServiceException
    {
        LineitemParams lip = new LineitemParams(pwd, null, lineitemBbId, params);
        return getLineitemDetailsForGivenLineitemBbIdWithNamedElementsAndParams(lip);
    }
    @WebMethod
    public LineitemDetails getLineitemDetailsForGivenLineitemBbIdWithNamedElementsAndParams(@WebParam(name = "lineitemParams") LineitemParams params) throws WebServiceException {
        //LineitemDetailsForGivenLineitemBbIdGetter getter 
        LineitemDetailsForGivenLineitemBbIdGetter getter         
                = new LineitemDetailsForGivenLineitemBbIdGetter(params);
        runGetter(getter);
        //getter.run();
        return getter.result;
    }
    
    public abstract class DataGetter<InternalResultType, ResultType, ParamsType extends CommonParams> {
        protected String errorPrefix;
        protected String methodName;
        protected ParamsType params;
        protected Class paramTypes[]; 
        public InternalResultType internalResult;
        public ResultType result;
        String dataLoaderClassNamePrefix;
        String dataLoaderStaticMethodName;
        DataGetter () {
            this.params = null;
            this.paramTypes = null;
        }
        DataGetter (ParamsType params) {
          this.params = params;   
          this.paramTypes = null;          
        }

        public void run() throws Exception {
                authoriseMethodUse(params.getPassword(), methodName);
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered DataGetter.run(); ", this);
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "for (int i = 0; i < CommonParams.apiToUseTags.length; i++) { params.getLogVerbosity(): " + params.getLogVerbosity() , this);
                for (int i = 0; i < CommonParams.apiToUseTags.length; i++) {
                    if (params.apiToUse.indexOf(CommonParams.apiToUseTags[i]) != -1) {
                        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Loading class for API " 
                                + CommonParams.apiToUseTags[i] 
                                + "; dataLoaderClassNamePrefix: " + dataLoaderClassNamePrefix 
                                + " dataLoaderStaticMethodName: " + dataLoaderStaticMethodName, this);
                        String c_name = dataLoaderClassNamePrefix + "_" + CommonParams.apiToUseTags[i];
                        params.wsPrivate_setCurrentApiUsed (CommonParams.apiToUseTags[i]);
                        BbWsLog.logForward(LogService.Verbosity.DEBUG, "Class data_loader_class = Class.forName(c_name); c_name: " + c_name, this);
                        Class data_loader_class = Class.forName(c_name);
                        BbWsLog.logForward(LogService.Verbosity.DEBUG, "java.lang.reflect.Method data_loader_method = data_loader_class.getDeclaredMethod(dataLoaderStaticMethodName); dataLoaderStaticMethodName: " + dataLoaderStaticMethodName, this);
                        java.lang.reflect.Method data_loader_method = data_loader_class.getDeclaredMethod(dataLoaderStaticMethodName, paramTypes);
                        //result = (ResultType) data_loader_method.invoke(null, params); 
    //                    Class<ResultType> c_result = data_loader_method.getReturnType();
    //                    return c_result.cast(data_loader_method.invoke(null, params));
                        Object paramObjs[] = new Object[2];
                        paramObjs[0] = params;
                        paramObjs[1] = internalResult;
                        BbWsLog.logForward(LogService.Verbosity.DEBUG, "data_loader_method.invoke(null, paramObjs);", this);
                        data_loader_method.invoke(null, paramObjs);  
                        params.incApiPassedCount();
                        //BbWsLog.logForward(LogService.Verbosity.DEBUG, "data_loader_method.invoke(null, params, internalResult);", this);
                        //data_loader_method.invoke(null, params, internalResult);  //- works too
                    }
                }
                result = (ResultType)internalResult;
        }
    }
    
    private class AllLineItemsForCourseIdGetter
            extends DataGetter<java.util.LinkedHashMap<String, LineitemDetails>, 
                                java.util.List<LineitemDetails>, LineitemParams> {
        AllLineItemsForCourseIdGetter (LineitemParams params) {
            super(params);
            internalResult = new java.util.LinkedHashMap<String, LineitemDetails>();
            //result = new java.util.LinkedHashMap<String, LineitemDetails>();
            errorPrefix = "Error: Could not retrieve line items, caused by: ";
            methodName = "getAllLineItemsForCourseIdWithNamedElements";
            dataLoaderClassNamePrefix = "bbgbws.LineitemDetails$RecordListLoader";
            dataLoaderStaticMethodName = "loadByCourseIdStarter";
            paramTypes = new Class[2];
            paramTypes[0] = LineitemParams.class;
            paramTypes[1] = java.util.LinkedHashMap.class;
        }
        
        @Override
        public void run() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "getOverridenSeverity -  = LogService.Verbosity.DEBUG.getLevelAsInt(): " + LogService.Verbosity.DEBUG.getLevelAsInt());
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "getOverridenSeverity -  = LogService.Verbosity.INFORMATION.getLevelAsInt(): " + LogService.Verbosity.INFORMATION.getLevelAsInt());
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "getOverridenSeverity -  = LogService.Verbosity.WARNING.getLevelAsInt(): " + LogService.Verbosity.WARNING.getLevelAsInt());
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "getOverridenSeverity -  = LogService.Verbosity.ERROR.getLevelAsInt(): " + LogService.Verbosity.ERROR.getLevelAsInt());
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "getOverridenSeverity -  = LogService.Verbosity.FATAL.getLevelAsInt(): " + LogService.Verbosity.FATAL.getLevelAsInt());
                //2009-09-21 06:56:07 - BBGradebookWebService	error	bbgbws.BBGradebookWebService@431f85 runGetter catch (Throwable t): ; obj.toString():  - java.lang.NoSuchFieldError: AUDIT
                //BbWsLog.logForward(LogService.Verbosity.DEBUG, "getOverridenSeverity -  = LogService.Verbosity.AUDIT.getLevelAsInt(): " + LogService.Verbosity.AUDIT.getLevelAsInt());
/*            
            if (params.getCourseId() != null && params.getLineitemBbId() != null) {
                throw new BbWsException("Invalid parameter values - only either courseId or lineitemBbId should has value set (non-null), but not both.");
            }
            if (params.getCourseId() == null && params.getLineitemBbId() == null) {
                throw new BbWsException("Invalid parameter values - both courseId and lineitemBbId cannot be null.");
            }
 */ 
            super.run();
            result = ConvertLinkedHashMap2List(internalResult);
        }
    }
    
    private class LineitemDetailsForGivenLineitemBbIdGetter
            extends DataGetter<LineitemDetails, 
                               LineitemDetails, LineitemParams> {
        LineitemDetailsForGivenLineitemBbIdGetter (LineitemParams params) {
            super(params);
            internalResult = new LineitemDetails();
            //result = new java.util.LinkedHashMap<String, LineitemDetails>();
            errorPrefix = "Error: Could not retrieve line item, caused by: ";
            methodName = "getLineItemForGivenLineitemBbIdWithNamedElements";
            dataLoaderClassNamePrefix = "bbgbws.LineitemDetails$RecordLoader";
            dataLoaderStaticMethodName = "loadByGivenLineitemBbIdStarter";
            paramTypes = new Class[2];
            paramTypes[0] = LineitemParams.class;
            paramTypes[1] = LineitemDetails.class;
        }
    }
    
    public static java.util.List ConvertLinkedHashMap2List(java.util.LinkedHashMap lhm) {
        return Arrays.asList(lhm.values().toArray());
    }
        //List keyList = Arrays.asList(LinkedHashMap.keySet().toArray());        
//        key to first element:
//keyList.get(0);
//key to last element:
//keyList.get(keyList.size());


    
    private void runGetter (DataGetter getter) {
        try {            
            getter.run();
        } catch (Throwable t) {
            BbWsLog.logForward(LogService.Verbosity.ERROR, t, "runGetter catch (Throwable t): ", this);
            throw new WebServiceException(getter.errorPrefix + "Throwable.getMessage():" + t.getMessage() + "Throwable.toString():" + t.toString() );
        }
    }
            

//=============================================================================    
//------------------------------------------------------------ OUTCOME -----    
//=============================================================================    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllLineItemsForCourseIdXML")
    public String getAllLineItemsForCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseIdXML");
        try
        {
            return toXML("LineItems",getAllLineitemObjsForCourseId(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    private List<OutcomeDefinitionDetails> getAllOutcomeDefinitionsForGivenCourse(String courseId) throws Exception
    {
        List<OutcomeDefinition> ods = ((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
        if(ods.size()<1)
        {
            throw new Exception("No outcome defs found");
        }
        List<OutcomeDefinitionDetails> rl = new ArrayList<OutcomeDefinitionDetails>();
        Iterator i = ods.iterator();
        while(i.hasNext())
        {
            rl.add(new OutcomeDefinitionDetails((OutcomeDefinition)i.next()));
        }
        return rl;
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllOutcomeDefinitionsForGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourse");
	headerDesc = handleNullValue(headerDesc);
	try
	{
	    List<OutcomeDefinitionDetails> odl = getAllOutcomeDefinitionsForGivenCourse(courseId);
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new OutcomeDefinitionDetails().toStringArrayHeader());
	    }
	    Iterator i = odl.iterator();
	    while(i.hasNext())
	    {
		rl.add(((OutcomeDefinitionDetails)i.next()).toStringArray());
	    }
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public List<OutcomeDefinitionDetails> getAllOutcomeDefinitionsForGivenCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourseWithNamedElements");
	try
	{
	    return getAllOutcomeDefinitionsForGivenCourse(courseId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllOutcomeDefinitionsForGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourseXML");
        try
        {
            return toXML("OutcomeDefinitions",getAllOutcomeDefinitionsForGivenCourse(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
        }
    }

    private List<OutcomeDetails> getAllOutcomesForGivenOutcomeDefBbId(String outcomeDefBbId) throws Exception
    {
	List<OutcomeDetails> rl = new ArrayList<OutcomeDetails>();
	List<Outcome> ol = Arrays.asList(((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)).getOutcomes());
	if(ol.isEmpty())
	{
	    throw new Exception("No outcomes found for outcomeDefBbId");
	}
	Iterator i = ol.iterator();
	while(i.hasNext())
	{
	    rl.add(new OutcomeDetails((Outcome)i.next()));
	}
	return rl;
    }

    @WebMethod
    public List<OutcomeDetails> getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements");
	try
	{
	    return getAllOutcomesForGivenOutcomeDefBbId(outcomeDefBbId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error while getting outcomes: "+e.toString());
	}
    }

    @WebMethod
    public String getAllOutcomesForGivenOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomesForGivenOutcomeDefBbIdXML");
        try
        {
            return toXML("Outcomes",getAllOutcomesForGivenOutcomeDefBbId(outcomeDefBbId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcomes: "+e.toString());
        }
    }

    static private class TestInternalClassForLoad {
    //private class TestInternalClassForLoad {
        static blackboard.platform.gradebook2.GradebookManager TestMethod () {
        //blackboard.platform.gradebook2.GradebookManager TestMethod () {
            blackboard.platform.gradebook2.GradebookManager gradebookManager = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            return gradebookManager;
        }
    }    
    
//=============================================================================    
//------------------------------------------------------------ SCORE -----    
//=============================================================================    
    
    private List<ScoreDetails> getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, Boolean extendedDetails) throws Exception
    {
    try{
        List<ScoreDetails> rl = new ArrayList<ScoreDetails>();
        try 
        {
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  BbConfig.LIBRARY_VERSION:" + blackboard.platform.config.BbConfig.LIBRARY_VERSION);
            BbWsLog.logForward(BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.LIBRARY_VERSION));
            //??BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  BbConfig.VERSION_NUMBER:" + blackboard.platform.config.BbConfig.VERSION_NUMBER);
            //??BbWsLog.logForward(BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.VERSION_NUMBER));
            
            
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod(");
            //??Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
            //Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck");
            //Object gradebookManager = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            //Object gradebookManager = (Class.forName("bbgbws.TestClassForLoad")).getDeclaredMethod("TestMethod").invoke(null);

            //BbWsLog.logForward("Class gradebookManagerClass = Class.forName(\"bbgbws.BBGradebookWebService.TestInternalClassForLoad\");");
            //Class gradebookManagerClass = Class.forName("bbgbws.BBGradebookWebService.TestInternalClassForLoad");

            //BbWsLog.logForward("Class gradebookManagerClass = Class.forName(\"TestInternalClassForLoad\");");
            //Class gradebookManagerClass = Class.forName("TestInternalClassForLoad");
            
            //BbWsLog.logForward("Class gradebookManagerClass = Class.forName(\"bbgbws.BBGradebookWebService$TestInternalClassForLoad\");");
            //Class gradebookManagerClass = Class.forName("bbgbws.BBGradebookWebService$TestInternalClassForLoad");

            //TestInternalClassForLoad tstcls = new bbgbws.BBGradebookWebService.TestInternalClassForLoad();
            //Object gradebookManager1 = tstcls.TestMethod();

            /*
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gradebookManagerClass = Class.forName(");
            Class gradebookManagerClass = Class.forName("bbgbws.TestClassForLoad");
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  gradebookManagerClass = " + String.valueOf(gradebookManagerClass));
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object gradebookManager = gradebookManagerClass.getDeclaredMethod(");
            java.lang.reflect.Method gradebookManagerMethod = gradebookManagerClass.getDeclaredMethod("TestMethod");
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Method = " + gradebookManagerMethod);
            Object gradebookManager = gradebookManagerMethod.invoke(null);
            */

            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gradebookManagerFactoryClass = Class.forName");
            Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
            Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
            
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gbmClass = gradebookManager.getClass();");
            Class gbmClass = gradebookManager.getClass();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  blackboard.persist.Id courseId = ((LineitemDbLoader)");
            blackboard.persist.Id courseId = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gradableItemClass = Class.forName");
            Class gradableItemClass = Class.forName("blackboard.platform.gradebook2.GradableItem");
            //gradableItemClass.getDeclaredField("DATA_TYPE").
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  blackboard.persist.Id gradableItemId = BbServiceManager.getPersistenceService()");
            blackboard.persist.Id gradableItemId = BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId((blackboard.persist.DataType)gradableItemClass.getField("DATA_TYPE").get(null),lineItemBbId);
            
            //Object gradableItem = gbmClass.getDeclaredMethod("getGradebookItem", parameterTypes)    public abstract GradableItem getGradebookItem(Id id)
            //blackboard.persist.Id courseId = ((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class bdrClass = Class.forName");    
            Class bdrClass = Class.forName("blackboard.platform.gradebook2.BookDataRequest");
            //Object bdr = bdrClass.getConstructor(new Class[]{blackboard.persist.Id.class}).
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object bdr = bdrClass.getConstructor(blackboard.persist.Id.class).newInstance(courseId);");
            Object bdr = bdrClass.getConstructor(blackboard.persist.Id.class).newInstance(courseId);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object bookData = gbmClass.getDeclaredMethod(");
            Object bookData = gbmClass.getDeclaredMethod("getBookData", bdrClass).invoke(gradebookManager, bdr);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  bookData.getClass().getDeclaredMethod(");
            bookData.getClass().getDeclaredMethod("addParentReferences").invoke(bookData);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  bookData.getClass().getDeclaredMethod(");
            bookData.getClass().getDeclaredMethod("runCumulativeGrading").invoke(bookData);

            //load the list of all student enrollments in the course
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  List<CourseMembership> cmlist = ");
            List<CourseMembership> cmlist = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(courseId, CourseMembership.Role.STUDENT);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Iterator<CourseMembership> cm_iter = cmlist.iterator();");
            Iterator<CourseMembership> cm_iter = cmlist.iterator();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  while (cm_iter.hasNext())");
            while (cm_iter.hasNext())
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  CourseMembership cm = cm_iter.next();");
                CourseMembership cm = cm_iter.next();
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object gradeWithAttemptScore = bookData.getClass().getDeclaredMethod(");
                Object gradeWithAttemptScore = bookData.getClass().getDeclaredMethod("get", blackboard.persist.Id.class, blackboard.persist.Id.class).invoke(bookData, cm.getId(), gradableItemId);
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  rl.add(new ScoreDetails(gradeWithAttemptScore, ");
                rl.add(new ScoreDetails(gradeWithAttemptScore, extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard, lineItemBbId));
                //gradeWithAttemptScore gwas = bookData.get(cm.getId(), gradableItemId);
            }
            //or you will get an out of memory error...
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  cmlist.clear();");
            cmlist.clear();
            cmlist = null;
        }
        //catch (ClassNotFoundException cnfe) 
        catch (NoClassDefFoundError cnfe) 
        {
            BbWsLog.logForward(cnfe, "getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Caught catch (ClassNotFoundException cnfe)");
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  rl.clear();");
            rl.clear();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  List<Score> scores = ((");
            List<Score> scores = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getScores();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    if(scores.size()<1)");
            if(scores.size()<1)
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    throw new WebServiceException(");
                throw new WebServiceException("No scores found");
            }
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    Iterator i = scores.iterator();");
            Iterator i = scores.iterator();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    while(i.hasNext())");
            while(i.hasNext())
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));");
                rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));
            }
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    scores.clear();");
            scores.clear();
            scores = null;
        }
        catch (ClassNotFoundException cnfe) 
        {
            BbWsLog.logForward(cnfe, "getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Caught catch (ClassNotFoundException cnfe)");
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  rl.clear();");
            rl.clear();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  List<Score> scores = ((");
            List<Score> scores = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getScores();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    if(scores.size()<1)");
            if(scores.size()<1)
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    throw new WebServiceException(");
                throw new WebServiceException("No scores found");
            }
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    Iterator i = scores.iterator();");
            Iterator i = scores.iterator();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    while(i.hasNext())");
            while(i.hasNext())
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));");
                rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));
            }
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    scores.clear();");
            scores.clear();
            scores = null;
        }
        BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    return rl;");
        return rl;
    }
    catch (Throwable t) {
        BbWsLog.logForward(t, "getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Caught throwable");
        throw new WebServiceException("Error: Could not retrieve scores for this line item "+t.toString(), t);
    }
    }

        /**
         * Web service operation
         */
        @WebMethod
        public List<ScoreDetails> getAllScoreDetailsForGivenLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails) throws WebServiceException
        {
        authoriseMethodUse(pwd,"getAllScoreDetailsForGivenLineItemBbIdWithNamedElements");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllScoreDetailsForGivenLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllScoreDetailsForGivenLineItemBbIdXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Scores",getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllScoresForGivenLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllScoresForGivenLineItemBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
            BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    List<ScoreDetails> sdsl = getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);");
	    List<ScoreDetails> sdsl = getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
	    List<String[]> rl = new ArrayList<String[]>();
            BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    if(headerDesc)");
	    if(headerDesc)
	    {
		rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    Iterator i = sdsl.iterator();
            BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    while(i.hasNext())");
	    while(i.hasNext())
	    {
                BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    rl.add(((ScoreDetails)i.next()).toStringArray());");
		rl.add(((ScoreDetails)i.next()).toStringArray());
	    }
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
            BbWsLog.logForward(e, "getAllScoresForGivenLineItemBbId(@WebParam(name = :    throw new WebServiceException(...");
	    throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
	}
    }

//=============================================================================    
//------------------------------------------------------------ ATTEMPT -----    
//=============================================================================    
    
    private List<AttemptDetails> getAttemptDetailsObjsForGivenOutcomeDefBbId(String outcomeDefBbId, Boolean extendedDetails) throws Exception
    {
	List<Attempt> al = AttemptDbLoader.Default.getInstance().loadByOutcomeDefinitionId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId));
	List<AttemptDetails> rl = new ArrayList<AttemptDetails>();
	Iterator i = al.iterator();
	while(i.hasNext())
	{
	    rl.add(new AttemptDetails((Attempt)i.next(),extendedDetails?AttemptDetails.Verbosity.extended:AttemptDetails.Verbosity.standard));
	}
	return rl;
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAttemptsByGivenOutcomeDefBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAttemptsByGivenOutcomeDefBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    List<AttemptDetails> adsl = getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails);
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new AttemptDetails(extendedDetails?AttemptDetails.Verbosity.extended:AttemptDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    Iterator i = adsl.iterator();
	    while(i.hasNext())
	    {
		rl.add(((AttemptDetails)i.next()).toStringArray());
	    }
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public List<AttemptDetails> getAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements");
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAttemptDetailsByGivenOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAttemptDetailsByGivenOutcomeDefBbIdXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Attempts",getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }

    private GradeBookSettingsDetails getGradeBookSettingDetailsObjForGivenCourseId(String courseId) throws Exception
    {
	return new GradeBookSettingsDetails(GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId()));
    }

    
//=============================================================================    
//------------------------------------------------------------ SETTINGS -----    
//=============================================================================    
    
    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod 
    public String[][] getGradebookSettingsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseID");
	headerDesc = handleNullValue(headerDesc);
	try
	{
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new GradeBookSettingsDetails().toStringArrayHeader());
	    }
	    rl.add(getGradeBookSettingDetailsObjForGivenCourseId(courseId).toStringArray());
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod 
    public GradeBookSettingsDetails getGradebookSettingsForGivenCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseIDWithNamedElements");
	try
	{
	    return getGradeBookSettingDetailsObjForGivenCourseId(courseId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGradebookSettingsForGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseIDXML");
        try
        {
            return toXML("GradeBookSetting",getGradeBookSettingDetailsObjForGivenCourseId(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
        }
    }

    private LineitemDetails getLineitemDetailsForGivenLineitemBbId(String lineitemBbId) throws WebServiceException
    {
        try
        {
            return new LineitemDetails(((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE, lineitemBbId)));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve lineitem details "+e.toString());
        }
    }
    
//=============================================================================    
//------------------------------------------------------------ Single value return methods -----    
//=============================================================================    
    
    @WebMethod
    public String getLineitemDetailsForGivenLineitemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getLineitemDetailsForGivenLineitemBbIdXML");
        return toXML("LineItems",getLineitemDetailsForGivenLineitemBbId(lineitemBbId));
    }

    private OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(String outcomeDefBbId) throws WebServiceException
    {
	try
	{
            return new OutcomeDefinitionDetails(OutcomeDefinitionDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)));
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve outcomeDefinition details "+e.toString());
	}
    }

    @WebMethod
    public OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
        return getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId);
    }

    @WebMethod
    public String getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
        return toXML("OutcomeDefinitions",getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId));
    }

    private ScoreDetails getScoreDetailsForGivenScoreBbId(String scoreBbId, Boolean extendedDetails) throws WebServiceException
    {
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
            return new ScoreDetails(((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Score.SCORE_DATA_TYPE,scoreBbId)),extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public ScoreDetails getScoreDetailsForGivenScoreBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getScoreDetailsForGivenScoreBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdXML");
        return toXML("ScoreDetails",getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails));
    }

    private ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbId(String userId, String lineItemBbId, Boolean extendedDetails) throws WebServiceException
    {
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return  getScoreDetailsObjForGivenScoreObj
		    (
			getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
			(
			    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
			    (
				    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId(),
				    UserDbLoader.Default.getInstance().loadByUserName(userId).getId()
			    ).getId().toExternalString(),
			    lineItemBbId
			),
			extendedDetails
		    );
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    @WebMethod
    public ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenUserIdAndLineItemBbId(userId, lineItemBbId, extendedDetails);
    }

    @WebMethod
    public String getScoreDetailsForGivenUserIdAndLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdXML");
        return toXML("Scores",getScoreDetailsForGivenUserIdAndLineItemBbId(userId, lineItemBbId, extendedDetails));
    }

    private ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(String courseMembershipBbId, String lineItemBbId, Boolean extendedDetails) throws WebServiceException
    {
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return getScoreDetailsObjForGivenScoreObj(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId),extendedDetails);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId, extendedDetails);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML");
        return toXML("Scores",getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId, extendedDetails));
    }

    private ScoreDetails getScoreDetailsObjForGivenScoreObj(Score s, Boolean extendedDetails) throws Exception
    {
        return new ScoreDetails(s,extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
    }

    private Score getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(String courseMembershipBbId, String lineItemBbId) throws Exception
    {
	    return ((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadByCourseMembershipIdAndLineitemId
		(
		    CourseMembershipDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE,courseMembershipBbId)).getId(),
		    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getId()
		);
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getScoreForGivenCourseMembershipBbIdAndLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name="headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    rl.add(getScoreDetailsObjForGivenScoreObj(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId),extendedDetails).toStringArray());
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    @Deprecated
    @WebMethod
    public String[][] getScoreForGivenUserIdAndLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name="headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreForGivenUserIdAndLineItemBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    rl.add(
		    getScoreDetailsObjForGivenScoreObj
		    (
			getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
			(
			    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
			    (
				    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId(),
				    UserDbLoader.Default.getInstance().loadByUserName(userId).getId()
			    ).getId().toExternalString(),
			    lineItemBbId
			),
			extendedDetails
		    ).toStringArray()
	    );
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    private OutcomeDetails getOutcomeDetailsFromOutcomeBbId(String outcomeBbId) throws WebServiceException
    {
	try
	{
            return new OutcomeDetails(OutcomeDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Outcome.DATA_TYPE, outcomeBbId)));
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No outcome found. Does outcome exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving outcome... "+e.toString());
	}
    }

    @WebMethod
    public OutcomeDetails getOutcomeDetailsFromOutcomeBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdWithNamedElements");
        return getOutcomeDetailsFromOutcomeBbId(outcomeBbId);
    }

    @WebMethod
    public String getOutcomeDetailsFromOutcomeBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdXML");
        return toXML("Outcomes",getOutcomeDetailsFromOutcomeBbId(outcomeBbId));
    }

    private Boolean handleNullValue(Boolean value)
    {
	if (value == null)
	{
	    return false;
	}
	return value;
    }

    private Boolean setLineItemWeightByLineItemBbId(String lineItemBbId, String weight) throws WebServiceException
    {
	try
	{
	    Lineitem li = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId));
	    li.setWeight(Float.parseFloat(weight));
	    ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While setting or persisting weight for lineitem "+e.toString());
	}
	return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean setLineItemWeightByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
        return setLineItemWeightByLineItemBbId(lineItemBbId, weight);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String setLineItemWeightByLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
        return toXML(null,setLineItemWeightByLineItemBbId(lineItemBbId, weight));
    }

    private Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(String courseId, String itemOrCategory) throws WebServiceException
    {
	try
	{
	    if(itemOrCategory!=null && itemOrCategory.length()>0)
	    {
		itemOrCategory = itemOrCategory.toLowerCase().trim();
		String firstChar = itemOrCategory.substring(0,1).toUpperCase();
		itemOrCategory = firstChar+itemOrCategory.substring(1);
	    }
	    else
	    {
		throw new Exception("Invalid item or category");
	    }
	    GradeBookSettings gbs = GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
	    gbs.setWeightType(GradeBookSettings.WeightType.fromFieldName(itemOrCategory));
	    GradeBookSettingsDbPersister.Default.getInstance().persist(gbs);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While setting or persisting weightings type for gradebook "+e.toString());
	}
	return true;
    }

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
        return setWeightByItemOrCategoryForGradebookInGivenCourseId(courseId, itemOrCategory);
    }

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public String setWeightByItemOrCategoryForGradebookInGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
        return toXML(null,setWeightByItemOrCategoryForGradebookInGivenCourseId(courseId, itemOrCategory));
    }

    private String toXML(String xmlTopLevelType, Object data) throws WebServiceException
    {
        StringBuffer for_return = new StringBuffer("");
        if(data==null)
        {
            throw new WebServiceException("Error parsing to xml: Passed data is null/blank");
        }

        if(data instanceof List)
        {
            if(xmlTopLevelType==null || xmlTopLevelType.equalsIgnoreCase(""))
            {
                throw new WebServiceException("Error parsing to xml: xmlTopLevelType is null/blank");
            }

            List list = (List)data;
            Iterator itrtr = list.iterator();
            for_return.append("<" + xmlTopLevelType + ">");
            while(itrtr.hasNext())
            {
                for_return.append(toXMLAnObject((ReturnTypeInterface)itrtr.next(),xmlTopLevelType.substring(0,xmlTopLevelType.length()-1)));
            }
            for_return.append("</"+xmlTopLevelType+">");
        }
        else if(data instanceof ReturnTypeInterface)
        {
            if(xmlTopLevelType==null || xmlTopLevelType.equalsIgnoreCase(""))
            {
                throw new WebServiceException("Error parsing to xml: xmlTopLevelType is null/blank");
            }

            for_return.append("<"+xmlTopLevelType+">");
            for_return.append(toXMLAnObject((ReturnTypeInterface)data,xmlTopLevelType.substring(0,xmlTopLevelType.length()-1)));
            for_return.append("</"+xmlTopLevelType+">");
        }
        else
        {
            for_return = new StringBuffer("<![CData[" + data + "]]>");
        }
        return for_return.toString();
    }

    private StringBuffer toXMLAnObject(ReturnTypeInterface rti, String subElementName)
    {
        StringBuffer for_return = new StringBuffer("<"+subElementName+">");
        for(int i=0;i<rti.toStringArrayHeader().length;i++)
        {
            for_return.append("<"+rti.toStringArrayHeader()[i]+">"+rti.toStringArray()[i]+"</"+rti.toStringArrayHeader()[i]+">");
        }
        return for_return.append("<"+subElementName+">");
    }
}
