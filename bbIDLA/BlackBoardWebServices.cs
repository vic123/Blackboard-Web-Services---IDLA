/*
 *   BlackboardWebServices Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BlackBoardWebServices.cs $
 *   Description: Provides Utility methods for data manipulation as well as custom classes used
 *   to define specific return types for the various methods in the couse, gradebook, and user
 *   classes. More definitions will be added here as needed. The rationale was to avoid returning generic
 *   types like 'stringArray' from the methods as these types are not transparent.
 *  
 *   Created On          : February 2, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-02-26 17:34:48 -0700 (Fri, 26 Feb 2010) $
 *   $LastChangedBy: alex.loepp $
 *   $LastChangedRevision: 11 $       
 *
 *   Change History:   
 *   0.0.0    ATL   Initial Development.
 *
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text.RegularExpressions;
using System.Collections.Specialized;
using System.Reflection;
using System.Data.SqlClient;

namespace bbIDLA
{
    public class BlackBoardWebServices
    {

        #region Utility Methods
       
        //use reflection to get the properties of the object returned from
        //the BB web service and return the property name and property value
        public static NameValueCollection ConvertResultsToNameValueCollection(object results)
        {
            PropertyInfo[] pinfos = results.GetType().GetProperties();
            NameValueCollection nvc = new NameValueCollection();

            foreach (PropertyInfo pi in pinfos)
            {
                nvc.Add(pi.Name, pi.GetValue(results, null) as string);
            }

            return nvc;
        }
     
        //take the elements of a name - value arrays and convert the name - value pair
        //to the corresponding properties of a specific object definition
        public static T ConvertStringArrayToObjectDefinition<T>(string[] names, string[] values) where T : new()
        {
            T obj = new T();
            PropertyInfo[] pinfos = obj.GetType().GetProperties();

            for (int i = 0; i < names.Length; i++ )
            {
                foreach (PropertyInfo pi in pinfos)
                {
                    //remove spaces and non-alphanumeric characters. This can be expanded
                    string name = Regex.Replace(names[i], "\\s+|/|\\.", "");

                    if (name.ToLower() == pi.Name.ToLower())
                    {
                        pi.SetValue(obj, values[i], null);
                    }
                }
            }

            return obj;
        }

        //log error messages from any method
        public static void LogError(string source, Exception ex)
        {
            //!!comment out the code below and provide custom logging code 
            throw new Exception("Implement custom logging mechanism in bbIDLA.BlackBoardWebServices.LogError() method.", ex);

            /* IDLA logging code block - commented out in order to eliminate necessity from DLSIS_ClassLibrary.dll
            DLSIS_ClassLibrary.ErrorHandler err = new DLSIS_ClassLibrary.ErrorHandler();
            err.LogError(DLSIS_ClassLibrary.ErrorHandler.ErrorType.WebServices,
                                   DLSIS_ClassLibrary.ErrorHandler.ErrorSource.Class,
                                   DLSIS_ClassLibrary.ErrorHandler.ErrorSeverity.Normal,
                                   String.Format("Error in {0} method: ", source) + ex.Message, ex.ToString(),
                                   String.Empty, String.Empty, String.Empty);
             */ 

        }

        #endregion

    }

    //custom return types as required by methods. Prinmarily designed to avoid returning a generic array type
    #region Return Object Definitions

    public class BbCourseDetails
    {
        // Public Properties
        public string CourseBbID
        {
            get;
            set;
        }

        public string CourseID
        {
            get;
            set;
        }

        public string Title
        {
            get;
            set;
        }

        public string Description
        {
            get;
            set;
        }

        public string CreationDate
        {
            get;
            set;
        }

        public string ModifiedDate
        {
            get;
            set;
        }

        public string Available
        {
            get;
            set;
        }

        public string AbsoluteLimit
        {
            get;
            set;
        }

        public string AllowGuests
        {
            get;
            set;
        }

        public string AllowObservers
        {
            get;
            set;
        }

        public string BannerImageUrl
        {
            get;
            set;
        }

        public string BatchUid
        {
            get;
            set;
        }

        public string ButtonStyle
        {
            get;
            set;
        }

        public string Cartridge
        {
            get;
            set;
        }

        public string Classification
        {
            get;
            set;
        }

        public string DurationType
        {
            get;
            set;
        }

        public string Enrollment
        {
            get;
            set;
        }

        public string Institution
        {
            get;
            set;
        }

        public string LocaleEnforced
        {
            get;
            set;
        }

        public string LockedOut
        {
            get;
            set;
        }

        public string NavigationCollapsible
        {
            get;
            set;
        }

        public string Locale
        {
            get;
            set;
        }

        public string NavigationBackgroundColour
        {
            get;
            set;
        }

        public string NavigationForegroundColour
        {
            get;
            set;
        }

        public string NavigationStyle
        {
            get;
            set;
        }

        public string NumberOfDaysOfUse
        {
            get;
            set;
        }

        public string PaceType
        {
            get;
            set;
        }

        public string ServiceLevelType
        {
            get;
            set;
        }

        public string SoftLimit
        {
            get;
            set;
        }

        public string StartDate
        {
            get;
            set;
        }

        public string UploadLimit
        {
            get;
            set;
        }
    }

    public class BbCourseQuotaDetails
    {
        public string CourseAbsoluteLimit
        { get; set; }

        public string CourseAbsoluteLimitRemaining
        { get; set; }

        public string CourseSize
        { get; set; }

        public string CourseSoftLimit
        { get; set; }

        public string CourseUploadLimit
        { get; set; }

        public string CourseEnforcedQuota
        { get; set; }

        public string CourseEnforcedUploadLimit
        { get; set; }

        public string SystemAbsoluteLimit
        { get; set; }

        public string SystemSoftLimit
        { get; set; }

        public string SystemUploadLimit
        { get; set; }
    }

    public class EnrolledCourseDetails
    {
        public string CourseID
        {
            get;set;
        }

        public string Available
        {
            get;set;
        }

        public string DateCreated
        {
            get;set;
        }

        public string UserRole
        {
            get;set;
        }
    }

    public class EnrolledUserDetails
    {
        public string UserBbID
        { get; set; }

        public string FirstName
        { get; set; }

        public string MiddleName
        { get; set; }

        public string SecondName
        { get; set; }

        public string EmailAddress
        { get; set; }

        public string LastLoggedIn
        { get; set; }

        public string PrimaryPortalRole
        { get; set; }

        public string SystemRole
        { get; set; }

        public string BatchUserBbId
        { get; set; }

        public string DataSourceBbId
        { get; set; }

        public string UserName
        { get; set; }

        public string StudentID
        { get; set; }

        public string Title
        { get; set; }

        public string SystemRoleID
        { get; set; }

        public string PortalRoleID
        { get; set; }

        public string Gender
        { get; set; }

        public string BirthDate
        { get; set; }

        public string EducationLevel
        { get; set; }

        public string JobTitle
        { get; set; }

        public string Company
        { get; set; }

        public string Department
        { get; set; }

        public string Street1
        { get; set; }

        public string Street2
        { get; set; }

        public string City
        { get; set; }

        public string StateCounty
        { get; set; }

        public string Country
        { get; set; }

        public string ZipPostCode
        { get; set; }

        public string BusinessPhone1
        { get; set; }

        public string BusinessPhone2
        { get; set; }

        public string MobilePhone
        { get; set; }

        public string HomePhone1
        { get; set; }

        public string HomePhone2
        { get; set; }

        public string BusinessFax
        { get; set; }

        public string HomeFax
        { get; set; }

        public string WebPage
        { get; set; }

        public string CardNumber
        { get; set; }

        public string CDROMDriveMac
        { get; set; }

        public string CDROMDrivePC
        { get; set; }

        public string ShowAddContactInfo
        { get; set; }

        public string ShowAddressInfo
        { get; set; }

        public string ShowEmailInfo
        { get; set; }

        public string ShowWorkInfo
        { get; set; }

        public string Avaialble
        { get; set; }

        public string InfoPublic
        { get; set; }

        public string ModifiedDate
        { get; set; }

        public string Locale
        { get; set; }
    }

    public class RoleDefinition
    {
        public string RoleID
        { get; set; }

        public string RoleName
        { get; set; }
    }

    public class GradebookDetails
    {
        public string LineItemBbID { get; set; }

        public string Name { get; set; }

        public string DateAdded { get; set; }

        public string DateChanged { get; set; }

        public string ColumnPosition { get; set; }

        public string Available { get; set; }

        public string PointsPossible { get; set; }

        public string Type { get; set; }

        public string Weight { get; set; }

        public string AssessmentBbID { get; set; }

        public string AssessmentLocation { get; set; }
    }

    #endregion
}