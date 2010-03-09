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

package bbuws;

//blackboard
import blackboard.admin.data.IAdminObject.RecStatus;
import blackboard.admin.data.IAdminObject.RowStatus;
import blackboard.admin.data.course.Enrollment;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.course.EnrollmentLoader.Default;
import blackboard.admin.persist.user.impl.PersonDbPersister;
import blackboard.admin.persist.user.impl.PersonDbLoader;
import blackboard.data.course.CourseMembership;
import blackboard.data.role.PortalRole;
import blackboard.data.user.User;
import blackboard.data.user.User.EducationLevel;
import blackboard.data.user.User.Gender;
import blackboard.data.user.UserRole;
import blackboard.db.ConstraintViolationException;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.user.UserDbPersister;
import blackboard.persist.user.UserRoleDbPersister;
import blackboard.platform.BbServiceManager;
import blackboard.platform.security.DomainManagerFactory;
import blackboard.platform.security.SystemRole;
import blackboard.platform.security.SecurityUtil;

//java
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//javax
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 * @author Mark.A.ONeil@Dartmouth.EDU
 * @author G.G.Bowie@ljmu.ac.uk
 */
@WebService(name="BBUserWebService", serviceName="BBUserWebService", targetNamespace="http://www.ncl.ac.uk/BBUserWebService")
public class BBUserWebService
{
	private WebServiceProperties wsp = new WebServiceProperties("amnl","BBUserWebService");
	//private enum Verbosity{minimal,standard,extended}

	private Boolean addAUser(String pwd, String userId, String batchUId, String firstName, String middleName, String lastName,
				String emailAddress, String studentId, String userPwd, String gender, String birthYear,
				String birthMonth, String birthDay, String eduLevel, String company,
				String jobTitle, String department, String street1, String street2,
				String city, String stateOrProvince, String zipOrPostCode, String country,
				String website, String homePhone, String workPhone, String workFax,
				String mobilePhone, String portalRole, String[] secPortalRoles, String systemRole,
				/*String[] secSystemRoles,*/ Boolean available) throws WebServiceException
	{
	    try
	    {
            PersonDbLoader.Default.getInstance().load(userId);
            //return "Error: User may already exist";
            throw new WebServiceException("User may already exist");
	    }
	    catch(KeyNotFoundException knfe){} //We need the user to not exist
	    catch(Exception e)
	    {
            //return "Error while trying to check if user already exists: "+e;
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }

	    Person p = new Person();
        ArrayList<PortalRole> secPRoles = null;
        String debug = "setting user name";
        try
	    {
            p.setUserName(checkUserDetail(userId));//userid
            debug = "setting batch uid";
            p.setBatchUid(checkUserDetail(batchUId));//batchuid
            debug = "setting given name";
            p.setGivenName(checkUserDetail(firstName));//firstname
            debug = "setting middle name";
            try{p.setMiddleName(checkUserDetail(middleName));}catch(Exception e){}//middlename - Catch Exception as it's not a mandatory field
            debug = "setting family name";
            p.setFamilyName(checkUserDetail(lastName));//lastname
            debug = "setting email address";
            p.setEmailAddress(emailAddress);//emailaddress
            debug = "setting student id";
            try{p.setStudentId(checkUserDetail(studentId));}catch(Exception e){}//studentid - Catch Exception as it's not a mandatory field
            debug = "setting password";
            p.setPassword(SecurityUtil.getHashValue(userPwd));//password - The password in blackboard is irrelevant if you're using ldap
            debug = "setting gender";
            try//gender - Is this working?
            {
                p.setGender(Gender.fromExternalString(gender.trim().toUpperCase()));
            }
            catch(Exception e)
            {
                p.setGender(Gender.UNKNOWN);
            }
            debug = "setting birthdate";
            try//birthdate
            {
                p.setBirthDate(new GregorianCalendar(Integer.parseInt(birthYear),Integer.parseInt(birthMonth)-1,Integer.parseInt(birthDay)));
            }catch(Exception e){}
            debug = "setting education level";
            try//Education Level
            {
                p.setEducationLevel(EducationLevel.fromExternalString(eduLevel.trim().toUpperCase()));
            }
            catch(Exception e)
            {
                p.setEducationLevel(EducationLevel.UNKNOWN);
            }
            debug = "setting company";
            try{p.setCompany(checkUserDetail(company));}catch(Exception e){}//Company - Catch Exception as it's not a mandatory field
            debug = "setting job title";
            try{p.setJobTitle(checkUserDetail(jobTitle));}catch(Exception e){}//Job Title - Catch Exception as it's not a mandatory field
            debug = "setting department";
            try{p.setDepartment(checkUserDetail(department));}catch(Exception e){}//Department - Catch Exception as it's not a mandatory field
            debug = "setting street1";
            try{p.setStreet1(checkUserDetail(street1));}catch(Exception e){}//Street 1 - Catch Exception as it's not a mandatory field
            debug = "setting street2";
            try{p.setStreet2(checkUserDetail(street2));}catch(Exception e){}//Street 2 - Catch Exception as it's not a mandatory field
            debug = "setting city";
            try{p.setCity(checkUserDetail(city));}catch(Exception e){}//City - Catch Exception as it's not a mandatory field
            debug = "setting state or province";
            try{p.setState(checkUserDetail(stateOrProvince));}catch(Exception e){}//State / Province - Catch Exception as it's not a mandatory field
            debug = "setting zip or post code";
            try{p.setZipCode(checkUserDetail(zipOrPostCode));}catch(Exception e){}//Zip / Postal Code - Catch Exception as it's not a mandatory field
            debug = "setting country";
            try{p.setCountry(checkUserDetail(country));}catch(Exception e){}//Country - Catch Exception as it's not a mandatory field
            debug = "setting website";
            try{p.setWebPage(checkUserDetail(website));}catch(Exception e){}//Website - Catch Exception as it's not a mandatory field
            debug = "setting home phone";
            try{p.setHomePhone1(checkUserDetail(homePhone));}catch(Exception e){}//Home Phone - Catch Exception as it's not a mandatory field
            debug = "setting work phone";
            try{p.setHomePhone2(checkUserDetail(workPhone));}catch(Exception e){}//Work Phone - Catch Exception as it's not a mandatory field
            debug = "setting work fax";
            try{p.setHomeFax(checkUserDetail(workFax));}catch(Exception e){}//Work Fax - Catch Exception as it's not a mandatory field
            debug = "setting mobile phone";
            try{p.setMobilePhone(checkUserDetail(mobilePhone));}catch(Exception e){}//Mobile Phone - Catch Exception as it's not a mandatory field
            debug = "setting portal role";
            //Portal Role
            PortalRole pr = null;
            if(portalRole!=null && !portalRole.equalsIgnoreCase(""))
            {
                portalRole = portalRole.trim();
                pr = PortalRoleDbLoader.Default.getInstance().loadByRoleId(portalRole);

            }
            else
            {
                pr = PortalRoleDbLoader.Default.getInstance().loadByRoleId("STUDENT");
            }
            p.setPortalRole(pr);
            debug = "setting system role";
            //System Role
            if(systemRole!=null && !systemRole.equalsIgnoreCase(""))
            {
                p.setSystemRole(blackboard.data.user.User.SystemRole.fromFieldName(systemRole.trim().toUpperCase()));
            }
            else
            {
                p.setSystemRole(blackboard.data.user.User.SystemRole.NONE);
            }
            debug = "setting available";
            if(available!=null){p.setIsAvailable(available);}else{throw new Exception("Invalid availability");}//Available
            debug = "setting row status";
            p.setRowStatus(RowStatus.ENABLED);
            debug = "setting rec status";
            p.setRecStatus(RecStatus.ADD);
            //p.setReplacementBatchUid(userId);
            debug = "checking secondary portal roles, number specified="+secPortalRoles.length;
            for(int i=0;i<secPortalRoles.length;i++)
            {
                debug += ", "+secPortalRoles[i];
            }
            //parse valid secondary roles, assuming any roles are specified then they
            //must be valid or the user is not added. No specified roles, null or blank roles are ignored.
            secPRoles = parseSecondaryPortalRoles(secPortalRoles);
            debug = "exiting try to persist user, you really shouldn't see this!";
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+" (hint: code was in the process of... "+debug+")");
	    }

	    try
	    {
            PersonDbPersister.Default.getInstance().insert(p);
	    }
	    catch(ConstraintViolationException cve)
	    {
	        throw new WebServiceException("The user you're trying to add may already exist");
	    }
	    catch(Exception e)
	    {
	        throw new WebServiceException("Error while trying to add user: "+e.toString());
	    }

	    /******
	     * Following must be set AFTER user is created as you need User.Id
	     * in order to set their UserRoles
	     *****/

	    //Secondary Portal Roles
        if(secPRoles!=null && secPRoles.size()>0)
        {
            try
            {
                setOrModifySecondaryPortalRolesForGivenUserId(userId,secPRoles);
            }
            catch(Exception e)
            {
                throw new WebServiceException(e.toString());
            }
        }

        return true;
	    //return userId+" : "+batchUId+" : "+firstName+" : "+middleName+" : "+lastName+" : "+emailAddress+" : "+studentId+" : "+pwd+" : "+gender+" : "+birthYear+" : "+birthMonth+" : "+birthDay+" : "+eduLevel+" : "+company+" : "+jobTitle+" : "+department+" : "+street1+" : "+street2+" : "+city+" : "+stateOrProvince+" : "+zipOrPostCode+" : "+country+" : "+website+" : "+homePhone+" : "+workPhone+" : "+workFax+" : "+mobilePhone+" : "+portalRole+" : "+systemRole+" : "+available;
	}

	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public Boolean addDefaultStudent(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "firstName") String firstName,
					@WebParam(name = "middleName") String middleName, @WebParam(name = "lastName") String lastName,
					@WebParam(name = "emailAddress") String emailAddress, @WebParam(name = "studentId") String studentId,  @WebParam(name = "studentPassword") String studentPassword) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addDefaultStudent");
	    //return addUser(userId,userId,firstName,middleName,lastName,emailAddress,studentId,"11111111",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"STUDENT","NONE",true);
	    return addAUser(pwd,userId,userId,firstName,middleName,lastName,emailAddress,studentId,studentPassword,
			    "","","","","","","","","","","","","","","","","","","","",
			    new String[]{},"",/*new String[]{""},*/true);
	}

	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String addDefaultStudentXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "firstName") String firstName,
					@WebParam(name = "middleName") String middleName, @WebParam(name = "lastName") String lastName,
					@WebParam(name = "emailAddress") String emailAddress, @WebParam(name = "studentId") String studentId,  @WebParam(name = "studentPassword") String studentPassword) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addDefaultStudentXML");
	    //return addUser(userId,userId,firstName,middleName,lastName,emailAddress,studentId,"11111111",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"STUDENT","NONE",true);
	    return toXML(null,addAUser(pwd,userId,userId,firstName,middleName,lastName,emailAddress,studentId,studentPassword,
                        "","","","","","","","","","","","","","","","","","","","",
                        new String[]{},"",/*new String[]{""},*/true));
	}

	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public Boolean addUser(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "batchUId") String batchUId,
				@WebParam(name = "firstName") String firstName, @WebParam(name = "middleName") String middleName,
				@WebParam(name = "lastName") String lastName, @WebParam(name = "emailAddress") String emailAddress,
				@WebParam(name = "studentId") String studentId, @WebParam(name = "userPwd") String userPwd,
				@WebParam(name = "gender") String gender, @WebParam(name = "birthYear") String birthYear,
				@WebParam(name = "birthMonth") String birthMonth, @WebParam(name = "birthDay") String birthDay,
				@WebParam(name = "eduLevel") String eduLevel, @WebParam(name = "company") String company,
				@WebParam(name = "jobTitle") String jobTitle, @WebParam(name = "department") String department,
				@WebParam(name = "street1") String street1, @WebParam(name = "street2") String street2,
				@WebParam(name = "city") String city, @WebParam(name = "stateOrProvince") String stateOrProvince,
				@WebParam(name = "zipOrPostCode") String zipOrPostCode, @WebParam(name = "country") String country,
				@WebParam(name = "website") String website, @WebParam(name = "homePhone") String homePhone,
				@WebParam(name = "workPhone") String workPhone, @WebParam(name = "workFax") String workFax,
				@WebParam(name = "mobilePhone") String mobilePhone, @WebParam(name = "portalRole") String portalRole,
				@WebParam(name = "secPortalRole") String[] secPortalRoles, @WebParam(name = "systemRole") String systemRole,
				/*@WebParam(name = "secSystemRole") String[] secSystemRoles,*/ @WebParam(name = "available") Boolean available) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addUser");
	    return addAUser(pwd,userId,batchUId,firstName,middleName,lastName,emailAddress,studentId,
			    userPwd,gender,birthYear,birthMonth,birthDay,eduLevel,company,jobTitle,
			    department,street1,street2,city,stateOrProvince,zipOrPostCode,
			    country,website,homePhone,workPhone,workFax,mobilePhone,
			    portalRole,secPortalRoles,systemRole,/*new String[]{""},*/available);
	}

	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String addUserXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "batchUId") String batchUId,
				@WebParam(name = "firstName") String firstName, @WebParam(name = "middleName") String middleName,
				@WebParam(name = "lastName") String lastName, @WebParam(name = "emailAddress") String emailAddress,
				@WebParam(name = "studentId") String studentId, @WebParam(name = "userPwd") String userPwd,
				@WebParam(name = "gender") String gender, @WebParam(name = "birthYear") String birthYear,
				@WebParam(name = "birthMonth") String birthMonth, @WebParam(name = "birthDay") String birthDay,
				@WebParam(name = "eduLevel") String eduLevel, @WebParam(name = "company") String company,
				@WebParam(name = "jobTitle") String jobTitle, @WebParam(name = "department") String department,
				@WebParam(name = "street1") String street1, @WebParam(name = "street2") String street2,
				@WebParam(name = "city") String city, @WebParam(name = "stateOrProvince") String stateOrProvince,
				@WebParam(name = "zipOrPostCode") String zipOrPostCode, @WebParam(name = "country") String country,
				@WebParam(name = "website") String website, @WebParam(name = "homePhone") String homePhone,
				@WebParam(name = "workPhone") String workPhone, @WebParam(name = "workFax") String workFax,
				@WebParam(name = "mobilePhone") String mobilePhone, @WebParam(name = "portalRole") String portalRole,
				@WebParam(name = "secPortalRole") String[] secPortalRoles, @WebParam(name = "systemRole") String systemRole,
				/*@WebParam(name = "secSystemRole") String[] secSystemRoles,*/ @WebParam(name = "available") Boolean available) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addUserXML");
	    return toXML(null,addAUser(pwd,userId,batchUId,firstName,middleName,lastName,emailAddress,studentId,
			    userPwd,gender,birthYear,birthMonth,birthDay,eduLevel,company,jobTitle,
			    department,street1,street2,city,stateOrProvince,zipOrPostCode,
			    country,website,homePhone,workPhone,workFax,mobilePhone,
			    portalRole,secPortalRoles,systemRole,/*new String[]{""},*/available));
	}

	private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
	{
	    if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
	    {
		throw new WebServiceException("Access Denied");
	    }
	}

	private String checkUserDetail(String userDetail) throws Exception
	{
	    if(userDetail!=null && !userDetail.equalsIgnoreCase("") && !containsSpecialCharacters(userDetail))
	    {
		return userDetail.trim();
	    }
	    throw new Exception("Invalid user detail: '"+userDetail+"'");
	}

	private Boolean containsSpecialCharacters(String toCheck)
	{
	    if(!toCheck.contains(" ") && /*!toCheck.contains("@") &&*/ !toCheck.contains("%") && !toCheck.contains("&") && !toCheck.contains("#") && !toCheck.contains("<")	&& !toCheck.contains(">") && !toCheck.contains("=") && !toCheck.contains("+"))
	    {
		return false;
	    }
	    return true;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public Boolean deleteUserByUserBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userBbId") String userBbId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserBbId");
	    try
	    {
		//You could just deleteById(bbPm.generateId()) but this way you also check if user exists
		UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(User.DATA_TYPE,userBbId)).getId());
	    }
	    catch(Exception e)
	    {
		//return "Error: Please provide a valid Id for a user";
		throw new WebServiceException("Invalid userId");
	    }
	    return true;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String deleteUserByUserBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userBbId") String userBbId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserBbIdXML");
	    try
	    {
            //You could just deleteById(bbPm.generateId()) but this way you also check if user exists
            UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(User.DATA_TYPE,userBbId)).getId());
	    }
	    catch(Exception e)
	    {
            //return "Error: Please provide a valid Id for a user";
            throw new WebServiceException("Invalid userId");
	    }
	    return toXML(null,true);
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public Boolean deleteUserByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserId");

	    try
	    {
		UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
	    }
	    catch(Exception e)
	    {
		//return "Error: Please provide a valid userId";
		throw new WebServiceException("Invalid userId");
	    }
	    return true;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String deleteUserByUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserIdXML");

	    try
	    {
            UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
	    }
	    catch(Exception e)
	    {
            //return "Error: Please provide a valid userId";
            throw new WebServiceException("Invalid userId");
	    }
	    return toXML(null,true);
	}

	private Boolean doesUserExist(String userId)
	{
	    try
	    {
		UserDbLoader.Default.getInstance().loadByUserName(userId);
	    }
	    catch(KeyNotFoundException knfe)
	    {
		return false;
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException("Error whilst searching to see if user exists: "+e.toString()+" "+e.getMessage());
	    }
	    return true;
	}
	
	/**
	 * Web service operation
	 */
	@WebMethod
	public Boolean doesUserExist(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"doesUserExist");
	    return doesUserExist(userId);
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String doesUserExistXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"doesUserExistXML");
	    return toXML(null,doesUserExist(userId));
	}

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String[] getAllUserIDsNoDetails(@WebParam(name = "pwd") String pwd) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getAllUserIDsNoDetails");
	    try
	    {
            List<String> sl = new ArrayList<String>();
            List<UserDetails> udsl = getAllUserObjsNoDetails();
            Iterator i = udsl.iterator();
            while(i.hasNext())
            {
                sl.add(((UserDetails)i.next()).getUserName());
            }
            return (String[])sl.toArray(new String[sl.size()]);
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public List<UserDetails> getAllUserIDsNoDetailsWithNamedElements(@WebParam(name = "pwd") String pwd) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getAllUserIDsNoDetailsWithNamedElements");
	    try
	    {
            return getAllUserObjsNoDetails();
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getAllUserIDsNoDetailsXML(@WebParam(name = "pwd") String pwd) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getAllUserIDsNoDetailsXML");
	    try
	    {
            return toXML("UserDetails",getAllUserObjsNoDetails());
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private List<UserDetails> getAllUserObjsNoDetails() throws WebServiceException
	{
	    List<UserDetails> al = new ArrayList<UserDetails>();
	    Person p = new Person();
	    p.setBatchUid("%%");

	    try
	    {
            List userList = PersonDbLoader.Default.getInstance().load(p);
            Iterator i = userList.iterator();
            while(i.hasNext())
            {
                al.add(new UserDetails((User)i.next(),UserDetails.Verbosity.minimal));
            }
            return al;
        }
        catch (KeyNotFoundException knfe)
        {
            //return new UserDetailsStandard(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No users found: "+knfe.getMessage());
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private List<CourseMembership> getCourseMembershipFromCourseId(String courseId) throws Exception
	{
	    return CourseMembershipDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),null,true);
	}
	
	private List<UserDetails> getEnrolledStudentsForGivenCourseIDWithNamedElements(String courseId) throws Exception
	{
	    List<UserDetails> rl = new ArrayList<UserDetails>();
	    //This may require heavy loading instead of lightweight
	    List<CourseMembership> cml = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),CourseMembership.Role.STUDENT);

	    if(cml.size()>0)
	    {
		Iterator i = cml.iterator();
		while(i.hasNext())
		{
		    //getuserid and load userdb for each user? less memory as only one user.
		    //loaded at a time, but do you need heavyweight load to get userid??
		    //rl.add(new UserDetailsStandard(((CourseMembership)i.next()).getUser(),true));
		    rl.add(new UserDetails(UserDbLoader.Default.getInstance().loadById(((CourseMembership)i.next()).getUserId()),UserDetails.Verbosity.minimal));
		}
		return rl;
	    }
	    throw new Exception("No students found");
	}
	
	@WebMethod
	public List<UserDetails> getEnrolledStudentsForGivenCourseIDWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledStudentsForGivenCourseIDWithNamedElements");
	    try
	    {
            return getEnrolledStudentsForGivenCourseIDWithNamedElements(courseId);
	    }
	    catch(Exception e)
	    {
    		throw new WebServiceException("Error while retrieving students: "+e.toString()+" "+e.getMessage());
	    }
	}

	@WebMethod
	public String getEnrolledStudentsForGivenCourseIDXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledStudentsForGivenCourseIDXML");
	    try
	    {
            return toXML("UserDetails",getEnrolledStudentsForGivenCourseIDWithNamedElements(courseId));
	    }
	    catch(Exception e)
	    {
    		throw new WebServiceException("Error while retrieving students: "+e.toString()+" "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public List<UserDetails> getEnrolledUserDetailsForGivenCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledUserDetailsForGivenCourseWithNamedElements");
	    extendedDetails = handleNullValue(extendedDetails);
	    try
	    {
    		return getEnrolledUserObjsForGivenCourse(courseId,extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard);
        }
        catch (KeyNotFoundException knfe)
        {
    		throw new WebServiceException("CourseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
        catch (Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getEnrolledUserDetailsForGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledUserDetailsForGivenCourseXML");
	    extendedDetails = handleNullValue(extendedDetails);
	    try
	    {
    		return toXML("UserDetails",getEnrolledUserObjsForGivenCourse(courseId,extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard));
        }
        catch (KeyNotFoundException knfe)
        {
    		throw new WebServiceException("CourseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
        catch (Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private List<UserDetails> getEnrolledUserObjsForGivenCourse(String courseId, UserDetails.Verbosity verbosity) throws Exception
	{
	    List<UserDetails> al = new ArrayList();
	    List<CourseMembership> membershipList = getCourseMembershipFromCourseId(courseId);
	    Iterator i = membershipList.iterator();
	    while(i.hasNext())
	    {
            al.add(new UserDetails(((CourseMembership)i.next()).getUser(),verbosity));
	    }
	    return al;
	}

	 /**
	 * Web service operation
	 */
	@WebMethod
	public String[][] getEnrolledUsersForGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledUsersForGivenCourse");
	    headerDesc = handleNullValue(headerDesc);
	    extendedDetails = handleNullValue(extendedDetails);
	    try
	    {
            List<String[]> sl = new ArrayList<String[]>();
            List<UserDetails> udl = getEnrolledUserObjsForGivenCourse(courseId, extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard);
            UserDetails.Verbosity verbosity = extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard;
            if(headerDesc)
            {
                sl.add(new UserDetails(verbosity).toStringArrayHeader());
            }

            Iterator i = udl.iterator();
            while(i.hasNext())
            {
                sl.add(((UserDetails)i.next()).toStringArray());
            }

            return (String[][])sl.toArray(new String[sl.size()][]);
        }
	    catch(KeyNotFoundException knfe)
	    {
            throw new WebServiceException("CourseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String[][] getEnrollmentsByBatchUIDandCourseIDPattern(@WebParam(name = "pwd") String pwd, @WebParam(name = "userBatchUid") String userBatchUid, @WebParam(name = "regex") String regex, @WebParam(name ="isMemberOnly") Boolean isMemberOnly, @WebParam(name ="extendedDetails") Boolean extendedDetails, @WebParam(name ="headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrollmentsByBatchUIDandCourseIDPattern");
	    headerDesc = handleNullValue(headerDesc);
	    extendedDetails = handleNullValue(extendedDetails);
	    //When isMemberOnly is TRUE the service simply returns true or false,
	    //  otherwise it returns a list of course batch_uids and roles....
	    isMemberOnly = handleNullValue(isMemberOnly);
		    
	    List<String[]> enRec = new ArrayList<String[]>();
	    enRec.add(new String[]{"Error","No matching memberships found"});
	    Pattern pattern = Pattern.compile("");
	    try
	    {
		pattern = Pattern.compile(regex);
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }

	    List matchesCourseUID = new ArrayList();
	    List matchesRoles = new ArrayList();
	    //take the user batch_uid and find their memberships
	    List membershipList = null;
	    Enrollment enrollment = new Enrollment();
	    enrollment.setPersonBatchUid(userBatchUid);
	    try{
		 membershipList = Default.getInstance().load(enrollment);
	    }catch(PersistenceException ex){}

	    String role = "";
	    if(membershipList.size()>0)
	    {
             for (int cnt = 0; cnt < membershipList.size(); cnt++)
             {
                 if(isAMatch(pattern,((Enrollment)membershipList.get(cnt)).getCourseSiteBatchUid()))
                 {
                 if(isMemberOnly) //found a match - good to go
                 {
                     enRec.clear();
                     if (headerDesc)
                     {
                    enRec.add(new String[]{"Is Member"});
                     }
                     enRec.add(new String[]{"true"});
                     return (String[][])enRec.toArray(new String[enRec.size()][]);
                 }
                 else
                 {
                    // build a list them all
                    role = ((Enrollment)membershipList.get(cnt)).getRole().toExternalString();
                    matchesCourseUID.add(((Enrollment)membershipList.get(cnt)).getCourseSiteBatchUid());
                    matchesRoles.add( role.substring(role.lastIndexOf(':')+1));
                 }
                 }
             }

             if (matchesCourseUID.size() > 0 && matchesRoles.size() >0)
             {
                 enRec.clear();
                 if (headerDesc)
                 {
                 enRec.add(new String[] {"Course_UID", "Role"});
                 }

                 Iterator imcuid = matchesCourseUID.iterator();
                 Iterator imr = matchesRoles.iterator();
                 while(imcuid.hasNext())
                 {
                 enRec.add(new String[]{(String)imcuid.next(),(String)imr.next()});
                 }
             }
	     }

	    return (String[][])enRec.toArray(new String[enRec.size()][]);
	}

	private List<bbuws.Role> getSecondaryPortalRolesForGivenUserId(String userId) throws Exception
	{
	    List<PortalRole> rl = PortalRoleDbLoader.Default.getInstance().loadSecondaryRolesByUserId(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
	    List<bbuws.Role> prl = new ArrayList<bbuws.Role>();
	    Iterator i = rl.iterator();

	    while(i.hasNext())
	    {
            prl.add(new bbuws.Role((PortalRole)i.next()));
	    }
	    return prl;
	}

	/**
	* Web service operation
	*/
	@Deprecated
	@WebMethod
	public String[][] getSecondaryPortalRolesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondaryPortalRolesForGivenUserId");
        headerDesc = handleNullValue(headerDesc);
	    try
	    {
            List<String[]> sl = new ArrayList<String[]>();
            List<bbuws.Role> prl = getSecondaryPortalRolesForGivenUserId(userId);
            Iterator i = prl.iterator();

            if(headerDesc)
            {
                sl.add(new bbuws.Role().toStringArrayHeader());
            }

            while(i.hasNext())
            {
                sl.add(((bbuws.Role)i.next()).toStringArray());
            }

            return (String[][])sl.toArray(new String[sl.size()][]);
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public List<bbuws.Role> getSecondaryPortalRolesForGivenUserIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondaryPortalRolesForGivenUserIdWithNamedElements");
	    //validateUserId(pwd,userId);
	    try
	    {
            return getSecondaryPortalRolesForGivenUserId(userId);
	    }
	    catch(Exception e)
	    {
            //return new String[][]{{"Error"},{"Couldn't load roles: "+e.toString()}};
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getSecondaryPortalRolesForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondaryPortalRolesForGivenUserIdXML");
	    try
	    {
            return toXML("Roles",getSecondaryPortalRolesForGivenUserId(userId));
	    }
	    catch(Exception e)
	    {
            //return new String[][]{{"Error"},{"Couldn't load roles: "+e.toString()}};
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private List<bbuws.Role> getSecondarySystemRolesForGivenUserId(String userId) throws Exception
	{
	    List<SystemRole> rl = DomainManagerFactory.getInstance().getDefaultDomainRolesForUser(userId);
	    List<bbuws.Role> prl = new ArrayList<bbuws.Role>();
	    Iterator i = rl.iterator();

	    while(i.hasNext())
	    {
            prl.add(new bbuws.Role((SystemRole)i.next()));
	    }
	    return prl;
	}

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String[][] getSecondarySystemRolesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondarySystemRolesForGivenUserId");
	    //validateUserId(pwd,userId);
            headerDesc = handleNullValue(headerDesc);
	    try
	    {
		List<String[]> sl = new ArrayList<String[]>();
		List<bbuws.Role> prl = getSecondarySystemRolesForGivenUserId(userId);
		Iterator i = prl.iterator();

		if(headerDesc)
		{
		    sl.add(new bbuws.Role().toStringArrayHeader());
		}
		
		while(i.hasNext())
		{
		    sl.add(((bbuws.Role)i.next()).toStringArray());
		}

		return (String[][])sl.toArray(new String[sl.size()][]);
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public List<bbuws.Role> getSecondarySystemRolesForGivenUserIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondarySystemRolesForGivenUserIdWithNamedElements");
	    //validateUserId(pwd,userId);
	    try
	    {
		return getSecondarySystemRolesForGivenUserId(userId);
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getSecondarySystemRolesForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondarySystemRolesForGivenUserIdXML");
	    try
	    {
    		return toXML("Roles",getSecondarySystemRolesForGivenUserId(userId));
	    }
	    catch(Exception e)
	    {
        	throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	* Web service getUserDetails
	*/
	@Deprecated
	@WebMethod
	public String[][] getUserDetails(@WebParam(name = "pwd") String pwd, @WebParam(name="userId") String userId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserDetails");
	    headerDesc = handleNullValue(headerDesc);
	    extendedDetails = handleNullValue(extendedDetails);
	    UserDetails.Verbosity verbosity = extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard;
	    try
	    {
            if(headerDesc)
    		{
                return new String[][]{getUserDetailsFromUserId(userId,verbosity).toStringArrayHeader(),getUserDetailsFromUserId(userId,verbosity).toStringArray()};
            }
            else
            {
                return new String[][]{getUserDetailsFromUserId(userId,verbosity).toStringArray()};
            }
        }
	    catch(KeyNotFoundException knfe)
	    {
            //return new String[][]{{"Error"},{"userId '"+userId+"' not found or does not exist ("+knfe.getMessage()+")"}};
            throw new WebServiceException("userId '"+userId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
	    catch(Exception e)
	    {
            //return new String[][]{{"Error"},{e.getMessage()}};
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
  	}

	private UserDetails getUserDetailsFromUserId(String userId, UserDetails.Verbosity verbosity) throws Exception
	{
	    return new UserDetails(UserDbLoader.Default.getInstance().loadByUserName(userId),verbosity);
	}

    /**
	* Web service getUserDetailsExtendedWithNamedElements
	*/
	@WebMethod
    public UserDetails getUserDetailsWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name="userId") String userId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getUserDetailsWithNamedElements");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            if(extendedDetails)
            {
                return getUserDetailsFromUserId(userId,UserDetails.Verbosity.extended);
            }
            else
            {
                return getUserDetailsFromUserId(userId,UserDetails.Verbosity.standard);
            }
        }
        catch (KeyNotFoundException knfe)
        {
            //return new UserDetails(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No user found: "+knfe.getMessage());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }

	/**
	* Web service getUserDetailsExtendedWithNamedElements
	*/
	@WebMethod
    public String getUserDetailsXML(@WebParam(name = "pwd") String pwd, @WebParam(name="userId") String userId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getUserDetailsXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<UserDetails> ud = new ArrayList<UserDetails>();
            if(extendedDetails)
            {
                ud.add(getUserDetailsFromUserId(userId,UserDetails.Verbosity.extended));
                return toXML("UserDetails",ud);
            }
            else
            {
                ud.add(getUserDetailsFromUserId(userId,UserDetails.Verbosity.standard));
                return toXML("UserDetails",ud);
            }
        }
        catch (KeyNotFoundException knfe)
        {
            //return new UserDetails(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No user found: "+knfe.getMessage());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String getUserRoleInCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserRoleInCourse");
	    try
	    {
            return CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()).getRole().toFieldName();
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public bbuws.Role getUserRoleInCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserRoleInCourseWithNamedElements");
	    try
	    {
            return new bbuws.Role(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()));
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getUserRoleInCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserRoleInCourseXML");
	    try
	    {
            List<bbuws.Role> role = new ArrayList<bbuws.Role>();
            role.add(new bbuws.Role(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId())));
            return toXML("Roles",role);
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
	    {
           return false;
	    }
        return value;
	}

	private static boolean isAMatch(Pattern pattern, String searchStr)
	{
	    boolean match = false;
	    Matcher matcher = pattern.matcher(searchStr);
	    while (matcher.find())
	    {
            match = true;
	    }
	    return match;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public boolean isUserInCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"isUserInCourse");
	    try
	    {
            if(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId())!=null)
            {
                return true;
            }
	    }catch(Exception e){}
	    return false;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String isUserInCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"isUserInCourseXML");
	    try
	    {
            if(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId())!=null)
            {
                return toXML(null,true);
            }
	    }catch(Exception e){}
	    return toXML(null,false);
	}

	private ArrayList<PortalRole> parseSecondaryPortalRoles(String[] roles) throws WebServiceException
	{
	    ArrayList pRoles = new ArrayList();
        try
        {
            PortalRoleDbLoader prl = PortalRoleDbLoader.Default.getInstance();
            for(int i=0; i<roles.length; i++)
            {
                if(roles[i]!=null && !roles[i].trim().equalsIgnoreCase(""))
                {
                    pRoles.add(prl.loadByRoleId(roles[i].trim()));
                }
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException("Invalid role specified "+e.toString());
        }
	    return pRoles;
	}

    private Boolean setOrModifySecondaryPortalRolesForGivenUserId(String userId, ArrayList<PortalRole> roles) throws WebServiceException
    {
   	    //if roles.Length == 0 it will simply delete any existing roles
	    try
	    {
            User u = UserDbLoader.Default.getInstance().loadByUserName(userId,null,true);
            PortalRole priPR = u.getPortalRole();
            UserRoleDbPersister prstr = UserRoleDbPersister.Default.getInstance();
            prstr.deleteAllByUserId(u.getId());
            PortalRole pr = null;
            UserRole ur = null;

            for(int i=0; i<roles.size();i++)
            {
                ur = new UserRole();
                ur.setUser(u);
                pr = roles.get(i);

                if(!pr.getRoleID().equalsIgnoreCase(priPR.getRoleID()))
                {
                    ur.setPortalRoleId(pr.getId());
                    prstr.persist(ur);
                }
            }
	    }
	    catch(Exception e)
	    {
            //return "Error: could not set secondary roles for user "+e.toString();
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	    return true;
    }

	/**
	 * roles can be NULL (I think) or 0 length
	 *
	 * Web service operation
	 */
	@WebMethod
	public Boolean setOrModifySecondaryPortalRolesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "roles") String[] roles) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"setOrModifySecondaryPortalRolesForGivenUserId");
        return setOrModifySecondaryPortalRolesForGivenUserId(userId,parseSecondaryPortalRoles(roles));
	}

	/**
	 * roles can be NULL (I think) or 0 length
	 *
	 * Web service operation
	 */
	@WebMethod
	public String setOrModifySecondaryPortalRolesForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "roles") String[] roles) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"setOrModifySecondaryPortalRolesForGivenUserIdXML");
        return toXML(null,setOrModifySecondaryPortalRolesForGivenUserId(userId,parseSecondaryPortalRoles(roles)));
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
