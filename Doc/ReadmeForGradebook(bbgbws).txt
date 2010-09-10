!!Note on Mar 14, 2010 - during review I've faced with logic flaw in multi-API results comparison logic that got propagated through overall processing design and revealed additional conflicts around missFieldTag, null value indication of that field was not assigned by previously passed API and check for error-free fields modification through re-load of saved record. Please see today's remaining comments marked with double-exclamation for more details.
!!Note on Mar 14, 2010 - I cannot come right away to decision on how it would be best of all to resolve those logical issues and just wrote down thoughts that I had along with review of particular statements of existing version of the document. Probably sticking of missFieldTag to constant null will resolve most of revealed complications, but not everything.



Project Summary
================
Clone of BBwebservices project, to expose more web service operations and demonstrate an alternative architectural design for process flow.
Homepage: http://projects.oscelot.org/gf/project/wservices_idla/



Project Purpose
===============

The general purpose of the WServices_idla project is to either merge with the original webservices this project was branched from, or to remain a branch of the original webservices project with improvements focusing on application integration and containing the following improvements:

1) Extension of various operations with newer API capabilities available in later (v.8 and v.9) versions of Blackboard. 

2) Complementing of several data items (Users, Courses, Course Enrollments, Institutional Roles, Users assignments to institutional roles, Observer role assignments) to support full list of CRUD (create, read, update, delete) operations.

3) Design and implementation capability for dynamic loading of API-specific classes (assures backward compatibility with older Blackboard versions). 

4) Safe accessing of field values with writing log into data structures returned to client (client-side debugging and testing) 

5) Global exception handling for a limited number of control flow points. 

6) Separation of API-specific data accessing operations from remaining processing logic, i.e. simplification of development for new API/items handling classes. 

7) Capability for automated testing of data variations returned by older/newer or alternative APIs. Conformity checks for a list of records (missing/excessive) and specific field values (equality) returned by different APIs are handled.



Currently implemented interfaces following this specification
=============================================================
Please refer to InterfaceSheet of Excel\InterfaceSheet_and_CodeGeneration.xls in SVN repository.
Unmentioned there are Gradebook and Score interfaces that are frozen in the middle-way - they were reworked first and remains in unchanged state of prototype.



Client request may be customized in following ways:
===================================================

 * more than one from available similar Blackboard APIs to be used for sequential processing of request with checking of equality of each API effects
 * verbosity level of "data structure log" (referred below as DataLog) - helper information in log format about processing of each field of data structures returned 
 * severity levels causing exception to be thrown to client both on field and record access levels, lover severities causing just setting of field value to predefined error tag and/or returning false as row level operation result to client
 * limit request to access only particular data structure fields (or pre-defined group of fields)



Multiple API Data Load Processing Scenario
==========================================

Web service will try sequentially to retrieve data through requested APIs. WebService exception may be thrown out to client if some API is unavailable due to BB library version or method implementation. Processing of each data field supported by particular API is performed according to scenarios below, where checks on possible inconsistencies are performed and details on ways of accruing of each particular field may be provided too in DataLog.

Initialization:
---------------
All result structure fields are initialized to null values initially. 

Use Cases:
----------
!!Note on Mar 14, 2010 - almost all of cases below need re-thinking and corrections, see today's notes on first case below for more details. Also it looks like current even unmodified processing design have potential conflicts around missFieldTag(if it has non-null value) and INSERT/UPDATE processing when input record is assigned to result and then re-loaded and compared with saved data - missFieldTag will be assumed by load processing as value assigned to the field by previously passed API.

Case: 
Field is unavailable in particular API or field is not marked for retrieving 
Action: Any field assignment or check is omitted, if field was not previously initialized, it remains null, if it was set through some previously passed API then remains unchanged. Nothing is appended to "data structure log".
Rationale: It will require both more coding and resources if each API-related processing will have to consider all possible fields available through other APIs. Each API will pay attention only for fields that are known to be available within its interface.
!!Note on Mar 14, 2010 - this needs to be revised. Initially it was thought that client may need manually to combine several APIs in order to get filled requested data structure, but it seems both complex for client side and non-rational for server one. Probably field value has to be set to warn tag and warn log message to be supplied if field is unavailable in particular API. This is the way how CourseMembership DATA sets User and Course BatchUids by this moment.
Such behavior will require complementing of all APIs upon adding of new fields to data structures, but should be more stable in terms of interface with client - if client occasionally relying on field that is not practically available then it has to receive some signal on this fact.

Case: 
Field is known to be unavailable or having wrong value in particular API under some conditions (i.e. titles and grades of calculated columns, missing BatchUid in some of DATA API interfaces).
Action: 
Field assignment and check should be omitted, however warning message has to be written to "data structure log". dataFieldErrorThrowSeverity can be configured for raising an exception upon detection of Warning in DataLog.
!!Note on Mar 14, 2010 - Probably field value has to be set to warn tag too.
Rationale: 
In situation when client requests data from particular API where some small pieces of data are unavailable it has to be notified on such inconsistency.

Case: 
Field is available, but was not assigned inside Bb, or some regular and predicted error occurs upon its access (i.e. - Changed date of never changed item, unset grade, etc.)
!!Note on Mar 14, 2010 - I think this was about possible situation when Bb API would throw an exception that in some circumstances needs to be ignored, but practically such situation was not met yet and this case probably has to be removed at all. 
Action: 
Field should be set to nullValueTag value if field assumes to be unassigned in particular API, informational message has to be written to "data structure log" on action taken. 
Rationale: 
If some error coming from difference in field values obtained through different APIs occurs later then it has to be possible to be "traced" back. So, any field modification action has to be registered. Informational severity level will be turned off by default.
!!Note on Mar 14, 2010 - also this case looks like continuance of thoughts about "multiple APIs" loading single data structure, see today's note on first case.

Case: 
Field is available and has value, current field is null or has the same value as newly obtained.
!!Note on Mar 14, 2010 - if field is null then it needs raising of field level error condition - it had to be set to warn tag if was not available in previous API... probably warn tag has to be overridden by current API value
Action: 
Field is set to value converted to string. Debug level message is written to "data structure log" on action taken and actual value assigned to the field.
Rationale: 
Same as above.


Case: 
Field is available and has value, but its current value differs from newly obtained one.
Action: 
Field is set to errorValueTag. Error message is written to "data structure log" on action taken, newly obtained value that differs from old one is supplied too.
Rationale: 
This is actually an aim of the whole story – automated capturing of any inconsistencies in data returned by different Blackboard library and API versions. 



Data modification general processing scenario
==============================================

Input data structure(s) contains data for persisting. Fields containing values different from missFieldTag (which is null by default) are stored in Bb. 
Decision on fields that have to be persisted is independent of dataVerbosity value, i.e. behavior is always as if dataVerbosity was set to CUSTOM.

In case of INSERT/UPDATE, data is reloaded and compared with saved one. List of Fields that are actually reloaded is controlled by dataVerbosity value. HOWEVER (important) – returned record will contain values in at least ALL posted fields if dataVerbosity is higher than ONLY_ID. While ONLY fields defined by dataVerbosity are actually reloaded and compared. This comes from internal simplicity needs – just assigning input record to result and passing it for data reload, and apparently it may contain some data in EXTENDED field kind while dataVerbosity is set to STANDARD for example. In case of ONLY_ID no data compare is performed, but record is reloaded and only primary key fields are returned.  
NONE dataVerbosity mode assumes no data reload. The result will look like ONLY_ID if input record contained correct values in Id field(s). 
CUSTOM data verbosity mode is most appropriate during INSERT/UPDATE operations - in this case only fields that had values (those that were modified) will be reloaded, compared and returned.

!!Note on Mar 14, 2010 - in order to become adequate to new (today's) corrections on multiple API data load processing, more strict control needs to be defined for data verbosity modes, i.e. for example if it was set to STANDARD, but not all "STANDARD" fields had values (some were set to missFieldTag(null)), then data reload will raise field level error condition. Otherwise there will be a need for one more flagging condition in base code, indicating how to qualify current null field value if API pass is above 1.

In case of DELETE, dataVerbosity higher than ONLY_ID will just cause reassigning of full input record as it was passed from client. ONLY_ID will behave as a NONE.

During data reload, loaded data is compared with the one from input (Id is handled as a special case during INSERT operation - it is nulled and ignored by data comparing action and its value does not checked for difference), and in case of difference processing is the same as if this data was previously loaded with another API. 
!!Note on Mar 14, 2010 - here is one more conflict of today's notes on modification of multi-API load processing - it is necessary to ignore somehow BbId value during compare of input data with inserted one.



Common parameters of all web methods
====================================

Client may specify following request processing options (provided in BbWsParams structure), default values are specified in parentheses after BbWsParams field name 

 * apiToUseList(specific to particular webservice method)  - list of structures containing 2 strings. First one is apiTag code list with values like DATA_GB, ADMIN_DATA, PLATFORM_GB2, WS_GB. Another one is innerClassOverride, which meaning can be explained probably only with sample. DATA contains User methods like userLoadById(), ADMIN_DATA contains methods like userLoadByBatchId(). They are not of the absolutely same kind and cannot have same names, but have identical format of parameters and return same data. And someone may like to run them in test mode for comparative accessing of 2 different APIs. Methods mentioned above are accessed through inner classes of DataAccessPack, and this parameter allows to call loadById() web method, that by default transfers to LoadById class of DATA, and redirect it to LoadByBatchId (by setting apiTag="ADMIN_DATA" and  innerClassOverride="LoadByBatchId") class of ADMIN_DATA. Defaults of this parameter depends on web method and Blackboard version.
dataLogSeverity - "Data structure log" max verbosity – DEBUG, INFO, WARN, ERROR. Default – WARN. 

 * password("") - 
webservices access password, it can be configured in Webservices building block properties.

 * nullValueTag("BbWsNull") - 
string representing null value of data field. Input data fields containing value of this tag will be converted to null value upon assignment to Bb data structures, result null Bb fields are converted to this value.

 * errorValueTag("BbWsError") - 
string representing error condition happened upon accessing of particular field - result field contains specified string in place of real value. Don't applicable for input field value.

 * warnValueTag("BbWsWarn") - 
string representing warning condition happened upon accessing of particular field. Don't applicable for input field value.

 * missFieldTag(null) - 
string flagging that accessing of field containing this tag value should be omitted. Applicable only for input.

 * dataFieldErrorThrowSeverity("ERROR") - 
severity level of field access problem causing an exception to be thrown (thrown here exception is re-handled on record level). 
Possible values are DEBUG, INFO, WARN, ERROR, FATAL. 
FATAL can be assumed as a synonim of NONE.

 * dataRecordErrorThrowSeverity	("ERROR") - 
severity level of record-wide access problem causing an exception to be thrown (in other words – web interface may return boolean success/failure of record level operation or throw an exception to client at this point of exception handling).
Possible values are DEBUG, INFO, WARN, ERROR, FATAL.
Practically used may be just ERROR and FATAL, i.e. whether to break list processing upon error. Transactional mechanisms are not be supported – data modifications performed before error condition are not be abandoned.
FATAL can be assumed as a synonym of NONE. Normally, no exceptions should be expected on return if dataRecordErrorThrowSeverity is FATAL.

 * dataLogSeverity(DataLogSeverity.WARN) - 
severity level of "data structure log" (referred below as bbWsDataLog, which is actual data log's field name in all returned data structures) supplemented with each of data structures returned.
Possible values are DEBUG, INFO, WARN, ERROR, FATAL.

 * dataVerbosity(DataVerbosity.STANDARD) - 
defines set of fields containing data returned. 
Possible values are NONE, ONLY_ID, MINIMAL, STANDARD, EXTENDED, CUSTOM. NONE can be used only during data modifications meaning that no data should be re-read.
if dataVerbosity is set to CUSTOM, returned data will contain only those fields set that had values different from specified in missFieldTag.

 * datePattern("yyyy-MM-dd") - 
java.text.SimpleDateFormat ruled date pattern of date/time input and result fields.

 * timePattern("HH:mm:ss") - 
java.text.SimpleDateFormat ruled time pattern of date/time input and result date fields.



Input and result data structures 
================================

Same data structures are used for input and result data. Several fields however are applicable only to results. 
In general, some more than usual in regular coding excessiveness was defined for interface methods. This was targeted for unification of interface and simplifying of coding on client side. The responsibility of making calls effective in terms of realized traffic is over client, because it can provide much unnecessary data and still get required results. In case of future necessity of performance restrictions this may be handled by some checks that will raise exceptions upon detection of excessive input data.

Common fields of all data structures
------------------------------------
 * bbWsBoolResult - result only - indication of success or failure processing of particular record (data structure object).
 * bbWsTextResult - result only - textual description (usually obtained as most root exception cause) of failure 
 * bbWsDataLog - result only - list of BbWsDataLogRecord objects providing details on field-level access operations.
 * BbId - primary key of Bb record corresponding to data structure returned.

Remaining fields are individual per API and carry both data load keys/field filtration or keys/new data for persisting operations.

BbWsDataLogRecord record 
------------------------
List of log records is included as one more element in all returned data structures 
 * Field Name (as it is named in data structure) 
 * Record BbId
 * Field value (!!Note on Mar 14, 2010 - no consistent logic here, various kinds of field values are assigned in different invocation of data logger)
 * API used 
 * API pass
 * Severity level 
 * Message (!!Note on Mar 14, 2010 - message text tries to substitute limitations of single value field, providing something like triple of Ws original value, Ws converted value and Bb actual value, but does not have consistency too)
 * Current server side date and time


Meanings of regular input field values upon different conditions
--------------------------------------------------------------
 * I. In case of data load – 
 ** 1. If dataVerbosity was set to CUSTOM, then  null value (missFieldTag) of particular field means that it should be omitted, returned value of this field will be null too
 ** 2. Non-null value defines 
 *** a. If dataVerbosity was set to CUSTOM, indicates that field has to be read and returned 
 *** b. Parameter of the request, i.e. userId or courseId (as a consequence of (1) above, fields representing parameters will be always returned)
 * II.	In case of Insert or Update
 ** 1. null value indicates that field should not be saved/modified
 ** 2. Non null value indicates new field value and/or identifier of record that has to be modified. Assigning of explicit null value will be indicated with commonNullValue assigned to the field
 * III. In case of delete 
 ** 1. non-null fields contains parameterization of delete operation, i.e. userId or courseId, etc.



Argumentation of null value used as indicator of non-required fields
====================================================================
According to tests made in direction of JAXWS to .NET, nulled strings are not encoded into SOAP messages at all, and thus using of null is reducing necessary traffic.



Deliberation on use of strict datatypes in exchanged data structures
====================================================================

Rough mapping of BBGradebookWebService, data.gradebook, platform.gradebook2 and ws.gradebook was made for Lineitem and Score items. It demonstrated light inconsistency of datatypes used internally by Blackboard in various APIs – fields of float/double datatype (pointsPossible, weight, averageScore, etc.) are declared as float in data.gradebook, as double in platform.gradebook2 and again as float in ws.gradebook.

This makes me think that using of String container datatype for any field exposed by BBGradebookWebService would be quite eligible. Where strings by themselves are stored in same (and complemented) data structures that were introduced in v.1.2 of BBGradebookWebService.

Null value will be used for indication of missing/unnecessary field. Actual null values will be transmitted as configurable string tags, i.e. like “!!NULL!!” or any other value (including empty string) assigned to commonNullValue parameter.



Step-by-step implementation of custom API accessing classes
===========================================================
Detailed and explanatory step-by-step is not ready yet, BbWebservices, DataDetails and DataAccessPack[_API] (UserDetails, UserAccessPack, UserAccessPack_DATA and UserAccessPack_ADMIN_DATA for example) classes have to be studied for samples on what is necessry for implementation of new interfaces.


Processing flow description
===========================

!!Note on Mar 14, 2010 - description below is principally outdated, gradebook code did not change and follows it, but latest code is built around BbWebservices(BbWsParams->BbWsArguments)->BbWsApiProcessor->BbWsDataAccessPack processing sequence. Description of gradebook processing below is left unmodified.

Current design is yet rough enough and made around of Gradebook Lineitem data reading. So, some names and points of control flow switching are not quite clean yet. 

Processing logic is concentrated at 3 points: 
a)	dynamic loading of classes responsible for actual blackboard data retrieval and pushing it into returned to client data structures (BBGradebookWebService.DataGetter.run())
b)	web interfaces returning lists of values are passing through DataDetails.RecordListLoader.loadImp() and loadRecord() that take care for checking of possible inconsistencies on missing/surplus records returned by various APIs.
c)	DataDetails.RecordLoader.ValueLoader.load() implements logic for comparing of record fields values obtained through different APIs

BBGradebookWebService entry point -> runGetter () client level exception handling -> DataLoader.load()bb-services-log.txt exception handling -> RecordListLoader.loadImp()->abstract LoadList() and LoadRecord() -> RecordLoader.loadImp() -> ValueLoader.load() –> data record log exception handling -> abstract loadImp().

DataLoader hierarchy of classes (RecordListLoader and RecordLoader are inherited from DataLoader and probably ValueLoader is candidate for this too) is made as internal in DataDetails class intentionally for an ability of direct fields access of DataDetail (designed to be ancestor for all data structures returned for client like LineitemDetails) from performance reasons.

Web service entrance points (BBGradebookWebService. getAllLineItemsForCourseIdWithNamedElements(), its overloaded version getAllLineItemsForCourseIdWithNamedElementsAndParams() and getLineitemDetailsForGivenLineitemBbIdWithNamedElements) create appropriate DataGetter successor which parameterizes DataGetter.run() behavior and can perform additional pre/post checks and/or returned result conversion. Invocation of inherited DataGetter is performed through BBGradebookWebService.runGetter() which centralizes and represents end-point of internal exception handling and re-throwing as WebServiceException to client. Parameters of Client call are stored in successor classes of CommonParams and are passed in this form to particular data access implementor. 
DataGetter is made internal to BBGradebookWebService just for easier code editing.

DataGetter.run() 
a)	checks permission for accessing of web service interface from Client and
b)	cycles through the list of APIs (particular data accessing implementations – successors of DataLoader class) that must be used for obtaining of data and sequentially passes control to loaded classes with providing of objects that should contain resulting data (i.e. kind of in/out parameter)

Loaded by DataGetter classes are successors of DataLoader. They are currently designed to instantiate themselves through custom static method that holds now customizations that did not fit well into overall design yet. Basically its task is to create appropriate DataLoader object and to pass control to DataLoader.load() method.

DataLoader.load() implements second level of exception handling- re-throwing with writing of stack trace into log file because dynamic loading/invocation through reflection seems to cut out much info from initial exception occurred (at least it was logged so by default). DataLoader.loadImp() is next level of processing implemented in successor classes – RecordLoader and RecordListLoader. 

RecordListLoader.loadImp() relies on LoadList() that should be overridden by successor and implement actual load of Blackboard items into the source list. loadImp() then iterates through it invoking loadRecord(). Checks on missing/exhaustive records in the list are separated between loadImp() and loadRecord() - loadRecord() first looks in already obtained records from other APIs if any and marks record as "Record was missing in previously passed APIs" if it is missing there. loadImp() as last processing step searches for any records that were not updated at this API pass and marks found records as "Record is missing in currently passed API". RecordListLoader.loadRecord() relays on createRecordLoader().

RecordLoader functionality is implemented most in final successors. On first place it is loadImp() that implements actual assignments of source record values to target data structures. Source value obtaining and converting to String is implemented inside try/catch block of RecordLoader.ValueLoader.load(). This function performs value comparing and log into “data log” errors if particular value is different in various APIs. Actual value loading has to be implemented in successors of ValueLoader that are defined as dynamic (inline) classes in overridden RecordLoader.loadImp(). See internal LineitemDetails classes RecordListLoader_DATA_GB/PLATFORM_GB2 and RecordLoader_DATA_GB/PLATFORM_GB2 for current sample implementation of API-specific data accessing classes.



Untested functional areas
======================================

 * course update was not tested at all

 * tests on almost all access methods were passed, but only in the mode of dataRecordErrorThrow==FATAL and dataFieldErrorThrow==FATAL and EXTENDED dataVerbosity (see test client for more details). Principally, this combination of options covers most part of code and represents most complex processing, but other processing options combinations were not definite targets of testing at all yet and therefore should have some bugs. This includes differtent from standard field tags, dataField|RecordThrowSeverity combinations and wiring of apiToUseList in all CRUD operations matrix.



Unresearched interface areas
=============================

 * Unclear necessity for specific Parent Role (System or Portal) for observers.

 * Check that setting CourseMembership's "available=false" disables student's enrollment.



Known bugs
==========

 * Hash password option seems not to work

 * Course copy has 2 problems:
 ** a) some course_contents_fk1 violation, may be some consequence of (b), did not look at it attentively at all yet
 ** b) timeout delay when copying proxy links defined for a course if proxy server is unavailable - Ws->Bb->proxy server -> exception in Bb internals on delay, exception in Ws on delay, Bb continues execution.

 * custom portal role assignment to UserDetails is not expected to work - probably key not found exception will be thrown, there is a difference in internal Bb forming of key for standard and "removable" portal roles. 

 * nulls of User dataSourceBbId, department, check other APIs data on null fields in EXTENDED


ToDo: 
=====

 * Move logSeverityOverride to bblock properties from web.xml

 * Adopt Gradebook interface to latest design and class hierarchy

 * Adopt Score interface to latest design and class hierarchy

 * Port Course interface to new design

 * Implement SystemRole interface

 * Implement UserRole interface

 * Permissions for particular methods of Ws have to be tried to be correlated somehow with general building block permissions defined at the moment of registration, i.e. to be propagated to internal blackboard security logic. This would be ideally, but unsure if possible without custom registration client or something of sort...  Otherwise some matrix of API to select/insert/update/delete operations should be handled in custom way. Current per method approach does not seem to be comfortable.

 * Decision on naming convention should be taken – whether it comes strictly from Bb internal names or reality. For example, how to name course accessing API methods if they are named internally ById, but actually work ByBatchUid? Requires more detailed testing of Bb v.9 - whether behavior of some incorrectly named API did not change in right way?

 * Rework logical design - i.e., today's notes in document marked with !! have to be joined in specification.

 * In particular, think on possible conflicts between "all fields are initialized to null by default before first API pass processing" and that missFieldTag is null by default. Feeling like something may go wrong in logic if missFieldTag is customized away from its default value. May be when it get mixed with some data of verbosity modes and data modification scenarious. Yes, assignment of input record to result one in order to be checked by data reload will probably not complete successfully.

 * Formalize "value" field of BbWsDataLogRecord to more strict meaning, may be introduce more "value" fields that should cover all of its conversion/compare/assignment permutations.

