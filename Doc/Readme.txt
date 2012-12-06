
Project Summary:
================
Blackboard Web Services - IDLA (later on named as WsIdla)
is Building Block (BB) for providing access to Blackboard API and data via XML Web Services based on JAX-WS.

Currently implemented APIs are: User, Course, CourseMembership, PortalRole, ObserverAssociation, PortalRoleMembership.

Integrated with rev576 (v.1.2) of "3) Beta/" of AMNL BBwebservices (http://projects.oscelot.org/gf/project/webservices/) project - it allows access to Announcement, Calendar, Content, Course, Discussion, Gradebook, Group and User APIs/data.




Features:
=========
 * Full CRUD support (create/read/update/delete) for implemented APIs.
 * Support both for single record and packet(list) processing. 
 * Fully customizable set of accessed fields.
 * Rechecking of data saving operation through record reloading and compare with input values.
 * Client defined error response either with by flag in returned data structure or throwing of exception on field and record levels.
 * Providing of "data access log" as part of returned data structures for easier debugging.
 * Customizable format of date and time.
 * Full thread of messages from nested exceptions and root causes included in error responses.
 * Capability to increase logging verbosity of building block independently from global Bb settings.
 * Dynamic loading of API-specific classes (allowing backward compatibility with older Blackboard versions).
 * Capability for automated testing of data variations returned by older/newer or alternative APIs - conformity checks for a list of records (missing/excessive) and specific field values (equality) returned by different APIs are handled.




Implemented by WsIdla APIs Summary:
================================
CourseMembership - single record and list CRUD processing
User - single record and list CRUD processing, list loading by linked parameters (ByGroupId, CourseId, etc.)

PortalRole - single record CRUD processing, list loading by linked parameters (ByUserId) and specific flags (Available, Removable, etc.)
ObserverAssociation - single record and list CRUD processing
PortalRoleMembership - single record and list CRUD processing
Course - single record CRUD processing, course copy operation.

For full list of provided web methods please see Doc/WebMethods.txt or column AT of BbWebserviceMethods sheet of Excel/InterfaceSheet_and_CodeGeneration.xls




Current version:
================
Please see BbWebservices.war/WEB-INF/bb-manifest.xml for information on release version and Bb version compatibility.




System Requirements:
====================
Blackboard Academic Suite of the version specified in bbversion value of bb-manifest.xml.




Download:
=========
http://projects.oscelot.org/gf/project/wservices_idla/ - homepage.

http://projects.oscelot.org/gf/project/wservices_idla/frs/ - latest installable release (BbWebservices.war, TestClients.zip)

http://projects.oscelot.org/gf/project/wservices_idla/scmsvn/?action=browse&path=%2Ftrunk%2F - web access to project source repository

http://projects.oscelot.org/gf/project/wservices_idla/scmsvn/?action=AccessInfo - instructions on source checkout with SVN client




Installation:
=============
WsIdla is installed as any other Building Block through "Administrator Panel / Building Blocks / Installed Tools / Upload Building Blocks" (in v.9). 

Settings button allows to:
 * Specify password (password hashing option did not work upon testing - todo!!)
 * Enable particular web methods available from integrated BBwebservices project (IMPORTANT - upon initial installation access to all methods will be set to disabled, an error will sound as "access denied", deleting and re-installing of building block does not reset access to earlier enabled methods)
 * Specify Log Severity level (?? rename to verbosity) - this setting overrides standard Blackboard log verbosity if for example it is set to WARN, while WsIdla's one to DEBUG, however opposite will not be true, i.e. setting of Bb one to DEBUG and WsIdla's to WARN will not suppress WsIdla's DEBUG level log entries (not tested!!). 
Possible values corresponding to LogService.Verbosity are integers that may differ for BB versions, in v.7.3 they are from 0 to 4, in v.9 are from 0 to 5, this is why there is DEBUG2 value in the list, corresponding to Bb verbosity of level 5.

Physically building block files will be placed to /blackboard/content/vi/bb_bb60/plugins/IDLA-BbWebservices/.

Conflicts were noticed of between JAX-WS 2.1 and JAX-WS 2.2 when another building block exists using another JAX-WS version (practically it happened with original AMNL BBwebservices compiled with JAX-WS 2.1 and WsIdla compiled with JAX-WS 2.2 installed on same Bb server).




Quick start:
============
The fastest way to start probably would be 
a) map your Bb server IP to idlatestbb.com URL (for example in  WinRoot\system32\drivers\etc\hosts file on Windows systems)

b) open TestClients/AutoTestClient/AutoTestClient_VS2008.sln, update of Web References of bbIDLA(DotNetProxy) and App_WebReferences of AutoTestClient, compile TestClients/DotNetProxy/bbIDLA and TestClients/AutoTestClient.

c) Input initial test data that AutoTestClient expects to find in Bb database. Theoretically, necessary data is identified in AutoTestClient's BbWsTest.aspx.cs TestArgs initialization and the one currently set there is just user with name "student_001" and course with courseId "TestClass_001_ID", and student_001 has to be enrolled to TestClass_001_ID. (!!) But some more constants may be encoded somewhere in AutoTestClient sources, it was not checked. data_release_9.0.440.0.zip contains Bb databases used for test runs.

d) Try to run/debug AutoTestClient.

See "AutoTestClient" topic for more details.
Reading through the rest of this file is highly recommended too.



Web Service End Points:
=======================
WsIdla Web Services:
--------------------
http://<Bb URL>/webapps/IDLA-BbWebservices-bb_bb60/BbWebservices
, access to web methods of all APIs is implemented through single end point.

AMNL BBwebservices:
-------------------
http://<Bb URL>/webapps/IDLA-BbWebservices-bb_bb60/BB<type-of-specific-webservice>WebService 
, i.e.
http://<Bb URL>/webapps/IDLA-BbWebservices-bb_bb60/BBAnnouncementWebService
http://<Bb URL>/webapps/IDLA-BbWebservices-bb_bb60/BBCalendarWebService
and so on for Content, Course, Discussion, Gradebook, Group and User.




Compatibility with AMNL BBwebservices v.1.2:
============================================
WsIdla use same data structures as AMNL BBwebservices whit minimal modifications, which include (a) all non-String data types are converted to String (see "Deliberation on use of strict datatypes in exchanged data structures" topic for more details) (b) several fields are renamed either in order to match better Bb naming to sound more type-specific (like Available to IsAvailable).

Code of AMNL BBwebservices v.1.2 using data structures of implemented by WsIdla APIs is modified as well to handle String and renamed fields.
Please see "Deliberation on Use of Strict Datatypes" for argumentation on use of String datatype only in exchanged data structures.




Deliberation on Use of Strict Datatypes:
========================================
Rough mapping of BBGradebookWebService, data.gradebook, platform.gradebook2 and ws.gradebook was made for Lineitem and Score items. It demonstrated light inconsistency of datatypes used internally by Blackboard in various APIs – fields of float/double datatype (pointsPossible, weight, averageScore, etc.) are declared as float in data.gradebook, as double in platform.gradebook2 and again as float in ws.gradebook.

Null value is used by default for indication of missing/unnecessary field. "Real" null values are transmitted as configurable string tags, i.e. like "BbWsNull" or any other value (including empty string) assigned to nullValueTag parameter. Primitive types do not support null value and such kind of indication would not be possible.

According to tests made in direction of JAXWS to .NET, nulled strings are not encoded into SOAP messages at all, and thus using of null is reducing necessary traffic.




Notes on Specifics of Various APIs/Web Methods:
===============================================
All noticed specifics in APIs behavior are provided as comments in tops of appropriate data structure source files, like BbWebservices/src/java/bbuws/UserDetails.java for User API, BbWebservices/src/java/bbuws/PortalRoleDetails.java for PortalRole API, BbWebservices/src/java/bbcrsws/CourseDetails.java for Course API - search for such files in BbWebservices/src/java/ directory.

Possible (accepted) values for specific fields can be found in  
SetFieldsExtended() methods AutoTestClient, please see AutoTestClient topic for more details.



Web Methods Naming, Parameters and Results:
===========================================
Names of all published web methods have following structure:

<APIDetails/APIDetailsList>  <API><Action><Record/List><ByClause>(BbWsParams, <APIDetails/APIDetailsList>, [<APIDetails])

possible Action values are Load, Insert, Update, Persist, Delete, Copy
BbWsParams is common parameterization structure passed to all web methods, explained below as “Common Parameters – BbWsParams” topic

for example:
UserDetails userLoadRecordById(BbWsParams, UserDetails) - loads single record of UserDetails by Id from passed UserDetails structure.

List<CourseMembershipDetails> courseMembershipPersistListById(BbWsParams, List<CourseMembershipDetails>) - persists (either inserts or updates depending on existence of record) list of passed CourseMembershipDetails structures and returns same list, updated with data reloaded after persisting, data log record and success/fail result for each processed record.

List<UserDetails> userLoadListByCourseId(BbWsParams, UserDetails, CourseDetails) - loads list of UserDetails, as long as UserDetails does not have CourseId field, third parameter of type CourseDetails is included in passed data, only CourseId is used, values of UserDetails are used only for possible custom filtering of fields to be returned.



Common Parameters - BbWsParams:
===============================
Client may specify following request processing options (provided in BbWsParams structure), default values are applied for null values and are specified in parentheses after field name.

/BbWebservices/src/java/bbwscommon/BbWsParams.java and BbWsArguments.java are source files closely related to BbWsParams. 

 * apiToUseList (default is specific to particular webservice method) - 
please see "Testing of Bb APIs with WsIdla" topic for more information on this parameter and leave it null for regular processing.

 * password("") - 
webservices access password, it can be configured in Webservices building block properties.

 * nullValueTag("BbWsNull") - 
string representing null value of data field. Input data fields containing value of this tag will be converted to null value upon assignment to Bb data structures, null Bb fields are converted to this value in result data.

 * errorValueTag("BbWsError") - 
string representing error condition happened upon accessing of particular field - result field contains specified string in place of real value. Don't applicable for input field value.

 * warnValueTag("BbWsWarn") - 
(?? probably will be removed - only error condition will substitute real field value) string representing warning condition happened upon accessing of particular field. Don't applicable for input field value.

 * missFieldTag(null) - 
string flagging that accessing of field containing this tag value should be omitted. All result structure fields are initialized to missFieldTag values initially. Input data on client side should probably be initialized to this value too in order to avoid situations when occasional null may reset important Bb data.

 * dataVerbosity(DataVerbosity.STANDARD) - 
defines set of fields containing data returned. 
Possible values are NONE, ONLY_ID, MINIMAL, STANDARD, EXTENDED, CUSTOM. NONE is applicable only during data modifications. NONE meaning that no data re-load will be performed (only keys from input record are copied into result), ONLY_ID means that record will be also reloaded, but only BbId will be updated in result record and no data compare will be performed.
if dataVerbosity is set to CUSTOM, returned data will contain only those fields set that had values different from value specified in missFieldTag.
MINIMAL, STANDARD and EXTENDED are mostly convenient for data load operations, please see setWsFields() implementations in successors of BbWsDataAccessPack for sets of fields predefined for these dataVerbosity values. NONE values are interpreted as ONLY_ID during data loading (only BbId).
See "Data Modification Processing" subtopic for more details.

 * dataFieldErrorThrowSeverity("ERROR") - 
severity level of field access problem causing an exception to be thrown (thrown here exception is re-handled on record level). 
Possible values are DEBUG, INFO, WARN, ERROR, FATAL, practically useful are only WARN, ERROR, FATAL. 

 * dataRecordErrorThrowSeverity	("ERROR") - 
severity level of record-wide access problem causing an exception to be thrown (in other words – web interface may return success/failure of record level operation or throw an exception to client at this point of exception handling).
Possible values are same as for dataFieldErrorThrowSeverity.
Transactional mechanisms are not be supported – data modifications performed before error condition are not rollbacked.
Normally, no application exceptions should be expected on return if dataRecordErrorThrowSeverity is FATAL.

 * dataLogSeverity("WARN") - 
(?? rename to dataLogVerbosity) verbosity of "data structure log" (DataLog) supplemented with each of data structures returned.
Accepted values are DEBUG, INFO, WARN, ERROR, FATAL. 

 * datePattern("yyyy-MM-dd") - 
java.text.SimpleDateFormat ruled date pattern of date/time input and result fields.

 * timePattern("HH:mm:ss") - 
java.text.SimpleDateFormat ruled time pattern of date/time input and result date fields.




Exchanged Data Structures:
==========================
Same data structures are used for input and result data. Several fields however are applicable only to results. 

In general, some more than usual in regular coding excessiveness was defined for interface methods. This was targeted for unification of interface and simplifying of coding on both client and server sides. The responsibility of making calls effective in terms of realized traffic is over client, because it can provide much unnecessary data and still get requested response and no errors.

Common fields of all data structures
------------------------------------
 * BbId - primary key of Bb record.
 * bbWsBoolResult - result only - indication of success or failure processing of particular record, can be "true" or "false".
 * bbWsTextResult - result only - textual description (in case of error - messages from exception chain) of failure, or just SUCCESS in case of true bbWsBoolResult.
 * bbWsDataLog - result only - list of BbWsDataLogRecord objects providing details on field-level access operations (see DataLog topic below).

Remaining fields are individual per API and specify either keys/field filtration for data load or keys/new data for modification operations.



DataLog:
========
List of log records is included as bbWsDataLog field in all returned data structures.

BbWsDataLogRecord record 
------------------------
 * fieldName - Field Name (as it is named in data structure) 
 * recordId - Record BbId, primary key of Bb record.
 * value - Field value that was finally (tried to be) assigned to Ws or Bb data structure depending on data load or modification operation
 * bbValue - value that was initially read from Bb
 * wsValue - value that was initially set in Ws data structure
 * apiUsed - code of API that was used for data access
 * apiPass - sequence (order) of data access operation. When it follows very first one, WsIdla compares already obtained values with newly loaded and signals errors in case of their difference.
 * severityLevel (?? rename to verbosity) - can be DEBUG, INFO, WARN, ERROR, FATAL.
 * message - free textual message, exception message in case of Bb exception upon field access, may include info on value, bbValue and wsValue because in case of settings permitting exception throwing only this text will be provided back to client as exception message
 * dateTime - server side date and time of log record generation




Project directories and important files:
========================================
/BbWebservices - WsIdla project and sources (Netbeans 6.0.1, JAX-WS 2.1)
/BbWebservices/src - java sources
/BbWebservices/src/java/bbwscommon/BbWebservices.java - "front end" of WsIdla.
/BbWebservices/src/java/bbwscommon/BbWsParams.java - common parameters structure for all web methods
/BbWebservices/src/java/bbwscommon/BbWsArguments.java - class to which BbWsParams are converted for internal WsIdla use.
/BbWebservices/web - configuration and GUI

/BbWebservices/web/WEB-INF/bb-manifest.xml - Bb building block configuration file

/Doc - documentation
/Doc/Readme.txt - this file
/Doc/ReadmeForGradebook(bbgbws).txt - an original readme for pre-alpha experimental implementation of Gradebook API which is not moved to current set of base classes yet. 

/Excel/InterfaceSheet_and_CodeGeneration.xls - Excel file with sheets described next
/Excel/InterfaceSheet.csv - table with classification of available web methods by API and CRUD action
/Excel/BbWebserviceMethods.csv - primary used for code generation, but as side effect contains list of all implemented by WsIdla web methods
/Excel/FieldSetters.csv - used for code generation, contains list of all fields from all implemented by WsIdla APIs

/lib - Bb libraries required for project compilation

/sql - piece of T-SQL script library allowing fast backup/restore of Bb MS SQL Server databases 

/TestClients - sample access code
/TestClients/AutoTestClient - demonstrates access to each WsIdla web method and most of fields of data structures
/TestClients/AutoTestClient/App_Code/BbWsTest.aspx.cs - practical AutoTestClient entry point - BbWsTestPage.aspx.cs just inherits from it, and by itself is mapped as Default in Web.Config
/TestClients/AutoTestClient/TestResults - sample test results, see readme.txt inside this directory for naming convention of files with test results and other possible notes.
/TestClients/AutoTestClient/AutoTestClient_VS2008.sln - VS2008 solution file, including in itself AutoTestClient and DotNetProxy projects (AutoTestClient by default is working through DotNetProxy interface), see "AutoTestClient" section of this file for how to exclude DotNetProxy from WsIdla call chain).
/TestClients/DotNetProxy/bbIDLA - project implementing retransmitting of calls to WsIdla and integrated AMNL BBwebservices, its main purpose is for centralized client-side error handling and logging.
/TestClients/webservices_amnl - tests copied from and based on AMNL BBwebservices, basic samples of some AMNL BBwebservices APIs access.

/webservices_amnl - mirror of AMNL BBwebservices (vendor) sources, integrated AMNL BBwebservices are copied (branched) from here which allows merging of AMNL BBwebservices updates.

/data_release_9.0.440.0.zip - zip file with Bb databases for Bb release 9.0.440.0_2.1.94 with initial test data expected as available by AutoTestClient.



BbWebservices:
==============

Compilation:
------------
Development environment - Netbeans 6.0.1
Open BbWebservices project.
Resolve /lib/BbVersion location (Project Properties / Libraries).
Build/Build main project (F11) should generate BbWebservices/dist/BbWebservices.war 

Processing Flow:
----------------
"Front end" of WsIdla is BbWebservices/src/java/bbwscommon/BbWebservices.java, it creates BbWsArguments from received BbWsParams and input data (<APIDetails/APIDetailsList>). In its major part BbWebservices.java is generated by BbWebserviceMethods sheet of Excel/InterfaceSheet_and_CodeGeneration.xls.

BbWsApiProcessor takes class name from BbWsArguments and loads-executes actual data access class (BbWsParams.apiToUseList can override identification of class to be loaded, see "Testing of Bb APIs with WsIdla" topic for more details). Password checking is performed here as well.

Dynamically loaded and executed are inner classes of successors of BbWsDataAccessPack. BbWsDataAccessPack successors have to follow specific naming convention with "_APISuffix" code at the end of their names.

BbWsDataAccessPack implements main processing logic. Its inner classes inherited from BbWsDataAccessPack.DataAccessor call semi-abstract (throwing exception in base implementation) methods of outer BbWsDataAccessPack class (loadList(), loadRecord(), ..., setBbFields(), deleteRecord()).

Successors of BbWsDataAccessPack must implement those methods, which have to perform minimal necessary processing specific to particular API.

Intermediate successors of inner DataAccessor implemented in BbWsDataAccessPack are InputListProcessor (processes list of records acessing each of them for example by Id), RecordLoader, RecordInserter, RecordUpdater, RecordPersister, RecordDeleter, RecordListLoader (used when single Bb call returns list of records). Design allows wiring of several DataAccessors successively, InputListProcessor uses it to call different RecordAccessor classes depending on initialization (in successors of BbWsDataAccessPack).

BbWsDataAccessPack.WsFieldSetter and BbWsDataAccessPack.BbFieldSetter implement core processing of single Bb field retrieving and assigning. Inherited from BbWsDataAccessPack classes create/call anonymous inner classes inherited from WsFieldSetter and BbFieldSetter in overriden setWsFields() and setBbFields() methods, these anonymous classes are generated by FieldSetters sheet of Excel/InterfaceSheet_and_CodeGeneration.xls.

Exception handling is performed in WsFieldSetter/BbFieldSetter (field level), BbWsDataAccessPack.execute() (web method call level), RecordAccessor.access(),  InputListProcessor.execute() (?? may be excessive) and RecordListLoader.access() (?? may be excessive) (last three - record level).

Data Modification Processing:
-----------------------------
Input data structure(s) contains data for persisting. Fields containing values different from BbWsParams.missFieldTag (which is null by default) are stored in Bb. In opposite words, fields set to missFieldTag are omitted from any processing. When data is updated, target record is preloaded and only then modified, i.e. data is not lost.
 
Decision on fields that have to be persisted is independent from BbWsParams.dataVerbosity value, behavior is always as if dataVerbosity was set to CUSTOM.

After insert or update action record is re-loaded and compared with input data. dataVerbosity value defines fields that are actually compared at this moment.

dataVerbosity modes:
 * NONE - forces no data reload to occur. Returned record will have only BbWsBoolResult, BbWsTextResult and bbWsDataLog set/modified, and will contain copied from input record ids (BbId and possibly additional alternative key fields depending on API). During list operations if input record did not have BbId set, result's record BbId will be set to random UUID.
 * ONLY_ID - keys are copied from input record, Bb record is reloaded, then BbId is retrieved from Bb data and only it is updated in result record and no data compare is performed.
 * MINIMAL - input record is saved, then copied as result one, which is then compared with data reloaded from Bb. In case of MINIMAL only minimal set of most important fields like name, alternative ids, etc. is compared/updated, however result record will contain all remaining data passed on input as well. And, if some "MINIMAL field" was not set on input, it will contain reloaded from Bb value (compare is omited in this situation, field is just updated).
 * STANDARD - same as minimal, but set of compared fields is wider.
 * EXTENDED - same as minimal, but set of compared fields is full.
 * CUSTOM - only those fields are reloaded/compared that were not set to missFieldTag (except of BbId). This mode looks like most applicable for regular insert/update operations that do not aim additional data load as side effect.

During delete ONLY_ID will behave as NONE and any dataVerbosity higher than ONLY_ID will cause just reassigning of full input record to result one.

Testing of Bb APIs with WsIdla: 
-------------------------------
(!!)Note - this functionality was not tested much and recently.

It is possible to request WsIdla to access same Bb data sequentially with different methods (all methods in such group should have same parameters and return same results). 

For example results returned by userLoadRecordById(), userLoadRecordByName() and userLoadRecordByBatchUid() can be compared this way, or userLoadListByTemplate() and userLoadListBySearchByUserName() for the sample of list compare.

During this operation WsIdla will check retrieved data for equality with previously loaded one.

Compared are field values and missing/excessive records in case of record list loading operations.

Checks on missing and new fields are not implemented. It is expected to become possible after making use of Bb _Attributes and checking that every field in _Attributes is mapped to WsIdla data structure field and no excessive fields exist on WsIdla side (??).

BbWsParams.apiToUseList designation is to allow such "wiring" of several web methods or just substitution of the class that should be loaded by default by BbWsApiProcessor (see "Processing Flow" subtopic for preamble). Default class is defined from successor of BbWsArguments (currently only _DATA or _ADMIN_DATA suffix) and hard coded class name string passed from BbWebservices. 

apiToUseList is a list of structures containing 2 strings. 
    public static class ApiToUse {
        public String apiTag;
        public String innerClassOverride;
    }

First one is apiTag code list with values like _DATA or _ADMIN_DATA (initially planned ones were for Gradebook with 3 access ways - DATA_GB, PLATFORM_GB2, WS_GB). It may be assumed as identifier of BbWsDataAccessPack successor name with restriction from internal WsIdla naming convention on that all methods are from similar APIs (i.e. all user... web methods load inner classes from either UserAccessPack_ADMIN_DATA or UserAccessPack_DATA).

Another one is innerClassOverride, which specifies successor of BbWsDataAccessPack.DataAccessor that will be actually loaded by BbWsApiProcessor.

If apiToUseList has only one record - then this record just overrides default class that had to be loaded if apiToUseList was null.

apiPassedCount is a flag indicating whether record is reloaded and has to be compared, it is both field of overall BbWsArguments, as well as record-level flag. On record level it is used for indication of missing records in currently passed API.

Global apiPassedCount is incremented in BbWsApiProcessor, record level one in various successors of DataAccessor class.
Mechanisms of field values compare is implemented in BbWsDataAccessPack.WsFieldSetter.setWsField() and activated if record level apiPassedCount > 0.

In BbWsDataAccessPack.RecordListLoader.RecordListLoader_BbListIterator/RecordListLoader_WsLHMIterator.access() are implemented checks on missing/excessive records.

RecordListLoader_BbListIterator looks in already obtained records from other APIs if global apiPassedCount > 0 and marks record as "Record was missing in previously passed APIs" if it is missing there. 

RecordListLoader_WsLHMIterator searches for any records without incremented apiPassedCount (compared to global apiPassedCount) and marks found records as "Record is missing in currently passed API".

See more about apiPassedCount in "Data Modification Processing" subtopic.




AutoTestClient:
===============
Development environment - MS Visual Studio 2008.

AutoTestClient does not include tests for integrated AMNL BBwebservices, it covers only WsIdla web methods.

See "Quick start" and "Web Service End Points" topics for quick AutoTestClient configuration and compilation.

Internally AutoTestClient is implemented as one big partial class BbWsTest (BbWsTest.aspx.cs + all BbWsTest.<API>.aspx.cs files in TestClients/AutoTestClient/App_Code). It was not carefully designed, main intention was to provide as much points for overriding or events injection/manipulation as possible and to split code in some reusable pieces. Currently it performs only "success case" autotesting, automated analysis of expected fail conditions is not implemented yet.

Base processing logic is concentrated in BbWsTest.aspx.cs, where set of base inner classes is introduced.

TestArgs class contains shared data storage for all tests. Its instances are parameterized by type of API data structure - i.e. UserDetails, CourseDetails, etc. But each its instance has access to other instances as well through TestArgsStruct which by itself contains TestArgs instances of all types. Successors of TestArgs (instantiated in TestArgsStruct constructor) complement TestArgs with concrete test running classes, successors of BbWsTest.TestCase.

TestCase (and its base TestAction) implement running of tests, analysis of results and their visualization.

Global control and settings of test runs is performed in: 
 * BbWsTest.RunTest() - top level calls of per-API sets of tests,
 * TestArgs.TestArgs() - specific BbWsParams settings,
 * Run<API>Test() of BbWsTest.<API>.aspx.cs files - commenting/uncommenting of specific test to run.

First place of practical interest should be BbWsTest.<API>.aspx.cs, API specific files of BbWsTest class, and inside them final successors of BbWsTest.TestCase class implementing actual calls of web service methods. Almost all of them are done in same pattern, by overriding of 3 methods - preAction(), executeImp() and postAction() and actively relying on their siblings (i.e. like calling from preAction() executeImp() of another test case). 

SetFieldsExtended() method of TestArgs overriden in its typed successors implements field level testing and contains lists of possible values accepted by specific fields, it is usually run from test class named like <api>UpdateRecordBy<something>_extended.

Initial skeleton of BbWsTest.<API>.aspx.cs files is generated by BbWebserviceMethods sheet of InterfaceSheet_and_CodeGeneration.xls.
Sample test results are available in /TestClients/AutoTestClient/TestResults directory.

Excluding of DotNetProxy:
-------------------------
AutoTestClient can call WsIdla either directly or through DotNetProxy (default).

Web References of DotNetProxy maps WsIdla to bbIDLA.BBAddedService namespace.
App_WebReferences of AutoTestClient maps WsIdla to bbws namespace.

In order to exclude DotNetProxy, "using bbIDLA.BBAddedService;" directive has to be replaced with "using bbws;" in all files implementing partial BbWsTest class (BbWsTest.aspx.cs, BbWsTest.Course.aspx.cs, BbWsTest.CourseMembership.aspx.cs and so on).

And one more correction has to be made in BbWsTest.aspx.cs - 
public BbWsWebReference bbWs = new BbWsWebReference(true);
has to be replaced with 
public bbws.BbWebservices bbWs = new bbws.BbWebservices();

BbWsWebReference class performs "translation" of method names from lower cased first letter to upper cased one (DotNetProxy methods are named/parameterized in a same way as web service methods, but capitalize first letter according to .Net naming convention).




DotNetProxy:
============
DotNetProxy performs forwarding of calls to web service methods and does optional exception handling and logging.

It provides entry points both to WsIdla and integrated AMNL BBwebservices. WsIdla web reference namespace is named as BBAddedService, AMNL BBwebservices web references names follow naming of their end points (specified in "Web Service End Points" topic).

Client-specific exception logging has to be implemented in BlackBoardWebServices.LogError() method.




Known problems and things to do:
================================
Ongoing notes in source comments and documentation are tagged with either double-exclamation or double-question sign (!! and ??). Exclamation means that it should be done, question that it is on the stage of analysis/research.

Long running, burn and full code coverage tests were not performed.

Performance profiling and optimization was not performed.

 * Review and rework base classes to support next level of functionality in Bb data accessibility (working name is intelligent Ws interface, design documentation is in development)

 * Simplify coding of field accessors through some semi-generic approach using internal Bb _Attributes for knowledge of right ways of field get/convert/set operations.

 * Extend AutoTestClient in automated analysis of failure test cases.

 * Adopt Gradebook interface to latest design and class hierarchy.

 * Adopt Score interface to latest design and class hierarchy.

 * List possible field values in enum fields modification error messages.

 * Research if configurable permissions for WsIdla methods can be implemented through general building block permissions currently defined at the moment of registration, i.e. to be propagated to internal blackboard security logic. If it is not possible then some matrix of permissions to select/insert/update/delete per API should be handled in custom way.

 * In case of customers' interest add possibility of raw XML output as it is implemented in AMNL BBwebservices in XML suffixed web methods.

 * Move access to bbId and rowStatus to base BbWsDataAccessPack or intermediate base class.

 * Add thread id to log entries.




Version history:
================
Project releases versioning was started from 2.0 as continuance of branched from and integrated into WsIdla initial AMNL BBwebservices v.1.2 sources.

For full development log please see commit comments to SVN trunk at http://projects.oscelot.org/gf/project/wservices_idla/scmsvn/?action=browse&path=%2Ftrunk%2F&view=log 

2.1.100 - Doc/Readme.txt (this file) is updated and included in release, source comments/tags added.

2.1.94 - Single-record (except courseLoadListByTemplate() that returns list) access to Course API is included in new functionality. Small fixes in BbWebservices and AutoTestClient.

2.0.82 - first public release, provides CRUD access to User, CourseMembership, PortalRole, ObserverAssociation and PortalRoleMembership APIs




Contact information: 
====================
Please use for communication
http://projects.oscelot.org/gf/project/wservices_idla/forum/ or
http://projects.oscelot.org/gf/project/wservices_idla/tracker/ project pages.




Development is financed by http://idahodigitallearning.org/
===========================================================




License: GNU General Public License (GPL)
=========================================

