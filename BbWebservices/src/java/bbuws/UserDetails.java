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

import bbwscommon.BbWsArguments.DataVerbosity;
import blackboard.data.role.PortalRole;
import blackboard.data.user.User;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.platform.BbServiceManager;
import java.util.Calendar;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 * @author G.G.Bowie@ljmu.ac.uk
 */
//public class UserDetails extends bbwscommon.BbWsDataDetails<bbwscommon.BbWsParams<UserDetails>>  implements ReturnTypeInterface
public class UserDetails extends bbwscommon.BbWsDataDetails<bbwscommon.BbWsArguments>  implements ReturnTypeInterface

{
    public enum Verbosity{minimal,standard,extended}
    

    private Verbosity verbosity;
    //standard details
    private String bbId;
    private String userName;
    private String givenName;
    private String middleName;
    private String familyName;
    private String emailAddress;
    private String lastLogin;
    private String portalRoleName;
    private String systemRole;
    //extended details
    private String batchUid;
    private String dataSourceBbId;
    private String studentId;
    private String title;
    private String systemRoleId;
    private String portalRoleId;
    private String gender;
    private String birthDate;
    private String educationLevel;
    private String jobTitle;
    private String company;
    private String department;
    private String street1;
    private String street2;
    private String city;
    private String county;
    private String country;
    private String postCode;
    private String businessPhone1;
    private String businessPhone2;
    private String mobilePhone;
    private String homePhone1;
    private String homePhone2;
    private String businessFax;
    private String homeFax;
    private String webPage;
    private String cardNumber;
    private String cdRomDriveMac;
    private String cdRomDrivePC;
    private String showAddContactInfo;
    private String showAddressInfo;
    private String showEmailInfo;
    private String showWorkInfo;
    private String isAvailable;
    private String isInfoPublic;
    private String modifiedDate;
    private String locale;

    //standard details

    @Override
    public String getBbId() {
        return bbId;
    }

    @Override
    public void setBbId(String newId) {
        bbId = newId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGivenName() {
        return givenName;
    }
    
    public void setGivenName(String newGivenName) {
        givenName = newGivenName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String newMiddleName) {
        middleName = newMiddleName;
    }
    
    public String getFamilyName() {
        return familyName;
    }
    
    public void setFamilyName(String newFamilyName) {
        familyName = newFamilyName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String newEmailAddress) {
        emailAddress = newEmailAddress;
    }
    
    public String getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(String newLastLogin) {
        lastLogin = newLastLogin;
    }
    
    public String getPortalRoleName() {
        return portalRoleName;
    }
    
    public void setPortalRoleName(String newRoleName) {
        portalRoleName = newRoleName;
    }
    
    public String getSystemRole() {
        return systemRole;
    }
    
    public void setSystemRole(String newSystemRole) {
        systemRole = newSystemRole;
    }

    public String getBatchUid() {
        return batchUid;
    }

    //extended details

    public void setBatchUid(String batchUserBbId) {
        this.batchUid = batchUserBbId;
    }

    public String getDataSourceBbId() {
        return dataSourceBbId;
    }

    public void setDataSourceBbId(String dataSourceBbId) {
        this.dataSourceBbId = dataSourceBbId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    public String getPortalRoleId() {
        return portalRoleId;
    }

    public void setPortalRoleId(String portalRoleId) {
        this.portalRoleId = portalRoleId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getBusinessPhone1() {
        return businessPhone1;
    }

    public void setBusinessPhone1(String businessPhone1) {
        this.businessPhone1 = businessPhone1;
    }

    public String getBusinessPhone2() {
        return businessPhone2;
    }

    public void setBusinessPhone2(String businessPhone2) {
        this.businessPhone2 = businessPhone2;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone1() {
        return homePhone1;
    }

    public void setHomePhone1(String homePhone1) {
        this.homePhone1 = homePhone1;
    }

    public String getHomePhone2() {
        return homePhone2;
    }

    public void setHomePhone2(String homePhone2) {
        this.homePhone2 = homePhone2;
    }

    public String getBusinessFax() {
        return businessFax;
    }

    public void setBusinessFax(String businessFax) {
        this.businessFax = businessFax;
    }

    public String getHomeFax() {
        return homeFax;
    }

    public void setHomeFax(String homeFax) {
        this.homeFax = homeFax;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCdRomDriveMac() {
        return cdRomDriveMac;
    }

    public void setCdRomDriveMac(String cdROMDriveMac) {
        this.cdRomDriveMac = cdROMDriveMac;
    }

    public String getCdRomDrivePC() {
        return cdRomDrivePC;
    }

    public void setCdRomDrivePC(String cdROMDrivePC) {
        this.cdRomDrivePC = cdROMDrivePC;
    }

    public String getShowAddContactInfo() {
        return showAddContactInfo;
    }

    public void setShowAddContactInfo(String showAddContactInfo) {
        this.showAddContactInfo = showAddContactInfo;
    }

    public String getShowAddressInfo() {
        return showAddressInfo;
    }

    public void setShowAddressInfo(String showAddressInfo) {
        this.showAddressInfo = showAddressInfo;
    }

    public String getShowEmailInfo() {
        return showEmailInfo;
    }

    public void setShowEmailInfo(String showEmailInfo) {
        this.showEmailInfo = showEmailInfo;
    }

    public String getShowWorkInfo() {
        return showWorkInfo;
    }

    public void setShowWorkInfo(String showWorkInfo) {
        this.showWorkInfo = showWorkInfo;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIsInfoPublic() {
        return isInfoPublic;
    }

    public void setIsInfoPublic(String isInfoPublic) {
        this.isInfoPublic = isInfoPublic;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    public UserDetails(){}

    public UserDetails(Verbosity verbosity)
    {
	this.verbosity = verbosity;	
    }
    public UserDetails(User bbUser, Verbosity verbosity) throws Exception
    {
	this.verbosity = verbosity;
	switch(this.verbosity)
	{
	    case minimal:
		this.userName = bbUser.getUserName();
		return;
	    case extended:
		batchUid = bbUser.getBatchUid();
		dataSourceBbId = bbUser.getDataSourceId().toExternalString();
		userName = bbUser.getUserName();
		studentId = bbUser.getStudentId();
		title = bbUser.getTitle();
		systemRoleId = bbUser.getSystemRoleIdentifier();
		portalRoleId = bbUser.getPortalRoleId().toExternalString();
		gender = bbUser.getGender().toFieldName();
		birthDate = extractBirthDate(bbUser);
		educationLevel =  bbUser.getEducationLevel().toFieldName();
		jobTitle = bbUser.getJobTitle();
		company = bbUser.getCompany();
		department = bbUser.getDepartment();
		street1 = bbUser.getStreet1();
		street2 = bbUser.getStreet2();
		city = bbUser.getCity();
		county = bbUser.getState();
		country = bbUser.getCountry();
		postCode = bbUser.getZipCode();
		businessPhone1 = bbUser.getBusinessPhone1();
		businessPhone2 = bbUser.getBusinessPhone2();
		mobilePhone = bbUser.getMobilePhone();
		homePhone1 = bbUser.getHomePhone1();
		homePhone2 = bbUser.getHomePhone2();
		businessFax = bbUser.getBusinessFax();
		homeFax = bbUser.getHomeFax();
		webPage = bbUser.getWebPage();
		cardNumber = bbUser.getCardNumber();
		cdRomDriveMac = bbUser.getCDRomDriveMac();
		cdRomDrivePC = bbUser.getCDRomDrivePC();
		//user.getCreatedDate()

		showAddContactInfo = Boolean.toString(bbUser.getShowAddContactInfo());
		showAddressInfo = Boolean.toString(bbUser.getShowAddressInfo());
		showEmailInfo = Boolean.toString(bbUser.getShowEmailInfo());
		showWorkInfo = Boolean.toString(bbUser.getShowWorkInfo());
		isAvailable = Boolean.toString(bbUser.getIsAvailable());
		isInfoPublic = Boolean.toString(bbUser.getIsInfoPublic());

		modifiedDate = extractModifiedDate(bbUser);
		locale = bbUser.getLocale();
	    case standard:
		bbId = bbUser.getId().toExternalString();
		givenName = bbUser.getGivenName();
		middleName = bbUser.getMiddleName();
		familyName = bbUser.getFamilyName();
		emailAddress = bbUser.getEmailAddress();
		lastLogin = retrieveLastLogin((Calendar) bbUser.getLastLoginDate());
		portalRoleName = retrieveRoleName(bbUser.getId());
		systemRole = retrieveSystemRole(bbUser);
		return;
	}
	throw new Exception("Undefined verbosity of user");
    }

    @SuppressWarnings("static-access")
    private String retrieveLastLogin(Calendar calendar) {
        try
        {
            return (calendar.get(Calendar.YEAR)+"-"+(calendar.get(calendar.MONTH)+1)+"-"+calendar.get(calendar.DAY_OF_MONTH)+" "+calendar.get(calendar.HOUR_OF_DAY)+":"+calendar.get(calendar.MINUTE)+":"+calendar.get(calendar.SECOND));
        }
        catch(Exception e)
        {
            return "Never";
        }
    }

    private String retrieveRoleName(Id id) {
        try
        {
            @SuppressWarnings("static-access")
            BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();
            PortalRoleDbLoader prl = (PortalRoleDbLoader)bbPm.getLoader(PortalRoleDbLoader.TYPE);
            PortalRole pr = prl.loadPrimaryRoleByUserId(id);
            return pr.getRoleName();
        }
        catch(Exception e)
        {
            return "";
        }
    }

    private String retrieveSystemRole(User bbUser) {
        try
        {
            return bbUser.getSystemRole().toExternalString();
        }
        catch(Exception e)
        {
            return "";
        }
    }

    @SuppressWarnings("static-access")
    private String extractBirthDate(User bbUser) {
        try
        {
            Calendar gc = (Calendar)bbUser.getBirthDate();
            return gc.get(Calendar.YEAR)+"-"+(gc.get(gc.MONTH)+1)+"-"+gc.get(gc.DAY_OF_MONTH);
        }
        catch(Exception e)
        {
            return "Not Given";
        }
    }

    @SuppressWarnings("static-access")
    private String extractModifiedDate(User bbUser) {
        try
        {
            Calendar gc = (Calendar)bbUser.getModifiedDate();
            return gc.get(Calendar.YEAR)+"-"+(gc.get(gc.MONTH)+1)+"-"+gc.get(gc.DAY_OF_MONTH)+" "+gc.get(gc.HOUR_OF_DAY)+":"+gc.get(gc.MINUTE)+":"+gc.get(gc.SECOND);
        }
        catch(Exception e)
        {
            return "Never";
        }
    }

    public String[] toStringArrayHeader()
    {
	switch(this.verbosity)
	{
        case minimal:
        return new String[]{"Username"};
	    case standard:
        return new String[]{"User BbId","First Name","Middle Name",
				    "Second Name","Email Address",
				    "Last Logged In","Primary Portal Role","System Role"};
	    case extended:
		return new String[]{"User BbId","First Name","Middle Name",
				    "Second Name","Email Address",
				    "Last Logged In","Primary Portal Role","System Role",
				    "Batch User BbId","Data Source BbId",
				    "User Name","Student Id","Title",
				    "System Role Id","Portal Role Id",
				    "Gender","Birth Date","Education Level","Job Title",
				    "Company","Department","Street 1","Street 2",
				    "City","State/County","Country","Zip/Post Code",
				    "Business Phone 1","Business Phone 2",
				    "Mobile Phone","Home Phone 1","Home Phone 2",
				    "Business Fax","Home Fax","Web Page","Card Number",
				    "CDROM Drive Mac","CDROM Drive PC","Show Add Contact Info.",
				    "Show Address Info.","Show Email Info.","Show Work Info.",
				    "Avaialble","Info. Public",
				    "Modified Date","Locale"};
	    default: return new String[]{};
	}
    }

    public String[] toStringArray()
    {
	switch(this.verbosity)
	{
        case minimal:
        return new String[]{
            this.userName
        };
	    case standard:
		return new String[]{
		    this.bbId,
		    this.givenName,
		    this.middleName,
		    this.familyName,
		    this.emailAddress,
		    this.lastLogin,
		    this.portalRoleName,
		    this.systemRole
		};
	    case extended:
		return new String[]{
		    this.bbId,
		    this.givenName,
		    this.middleName,
		    this.familyName,
		    this.emailAddress,
		    this.lastLogin,
		    this.portalRoleName,
		    this.systemRole,
		    this.batchUid,
		    this.dataSourceBbId,
		    this.userName,
		    this.studentId,
		    this.title,
		    this.systemRoleId,
		    this.portalRoleId,
		    this.gender,
		    this.birthDate,
		    this.educationLevel,
		    this.jobTitle,
		    this.company,
		    this.department,
		    this.street1,
		    this.street2,
		    this.city,
		    this.county,
		    this.country,
		    this.postCode,
		    this.businessPhone1,
		    this.businessPhone2,
		    this.mobilePhone,
		    this.homePhone1,
		    this.homePhone2,
		    this.businessFax,
		    this.homeFax,
		    this.webPage,
		    this.cardNumber,
		    this.cdRomDriveMac,
		    this.cdRomDrivePC,
		    this.showAddContactInfo,
		    this.showAddressInfo,
		    this.showEmailInfo,
		    this.showWorkInfo,
		    this.isAvailable,
		    this.isInfoPublic,
		    this.modifiedDate,
		    this.locale,
		};
	    default: return new String[]{};
	}
    }

/**
    @Override public String getId() {
        return this.bbId;
    }
    @Override public void setId(String id) {
        this.bbId = id;
    }


 *
 * @author vic123, IDLA
 */


    //public void initialize(bbwscommon.BbWsCommonParams params) {
//works(1)    public void initialize(bbwscommon.BbWsCommonParams<UserDetails> params) {
//    public void initialize(bbwscommon.BbWsCommonParams<? super UserDetails> params) {
//    public void initialize(bbwscommon.BbWsDataDetails<? super bbwscommon.BbWsCommonParams<UserDetails>> params) {

    //@Override public void initialize(bbwscommon.BbWsDataDetails params) {}

//    public void initialize(UserDetails params) {
//    @Override public void initialize(bbwscommon.BbWsDataDetails<? super bbwscommon.BbWsCommonParams<UserDetails>> params) {
//    @Override public void initialize(bbwscommon.BbWsArguments args) {
    public void initializeFields(bbwscommon.BbWsArguments args) {
        //super.initialize(args);
        //!! does not checks for verbosity of paarams
        //not used by the moment
        bbId = "";
        userName = "";

        //if ("STANDARD, EXTENDED".indexOf(args.getDataVerbosity()) != -1) {
        if (args.getDataVerbosity().compareTo(DataVerbosity.STANDARD) == 0
                && args.getDataVerbosity().compareTo(DataVerbosity.EXTENDED) == 0) {
            verbosity = Verbosity.standard;
            //standard details
            givenName = "";
            middleName = "";
            familyName = "";
            emailAddress = "";
            lastLogin = "";
            portalRoleName = "";
            systemRole = "";
        } else {


        }
            //extended details
            String miss_field_tag = args.getMissFieldTag();
            batchUid = miss_field_tag;
            dataSourceBbId = miss_field_tag;
            studentId = miss_field_tag;
            title = miss_field_tag;
            systemRoleId = miss_field_tag;
            portalRoleId = miss_field_tag;
            gender = miss_field_tag;
            birthDate = miss_field_tag;
            educationLevel = miss_field_tag;
            jobTitle = miss_field_tag;
            company = miss_field_tag;
            department = miss_field_tag;
            street1 = miss_field_tag;
            street2 = miss_field_tag;
            city = miss_field_tag;
            county = miss_field_tag;
            country = miss_field_tag;
            postCode = miss_field_tag;
            businessPhone1 = miss_field_tag;
            businessPhone2 = miss_field_tag;
            mobilePhone = miss_field_tag;
            homePhone1 = miss_field_tag;
            homePhone2 = miss_field_tag;
            businessFax = miss_field_tag;
            homeFax = miss_field_tag;
            webPage = miss_field_tag;
            cardNumber = miss_field_tag;
            cdRomDriveMac = miss_field_tag;
            cdRomDrivePC = miss_field_tag;
            showAddContactInfo = miss_field_tag;
            showAddressInfo = miss_field_tag;
            showEmailInfo = miss_field_tag;
            showWorkInfo = miss_field_tag;
            isAvailable = miss_field_tag;
            isInfoPublic = miss_field_tag;
            modifiedDate = miss_field_tag;
            locale = miss_field_tag;
    }

}
