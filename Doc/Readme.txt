OSCELOT registration fields:
(Wording was corrected after actual registration request)

Project full name
Blackboard Web Services - IDLA

Project Purpose
The general purpose of the WServices_idla project is to either merge with the original webservices this project was branched from, or to remain a branch of the original webservices project with improvements focusing on application integration and containing the following improvements:

1) Extension of various operations with newer API capabilities available in later (v.8 and v.9) versions of Blackboard. Currently implemented is reading of Scores with blackboard.platform.gradebook2 API.

2) Complementing of several data items (Users, Courses, Course Enrollments, Institutional Roles, Users assignments to institutional roles, Observer role assignments) to support full list of CRUD (create, read, update, delete) operations.

3) Design and implementation capability for dynamic loading of API-specific classes (assures backward compatibility with older Blackboard versions). 

4) Safe accessing of field values with writing log into data structures returned to client (client-side debugging and testing) 

5) Global exception handling for a limited number of control flow points. 

6) Separation of API-specific data accessing operations from remaining processing logic, i.e. simplification of development for new API/items handling classes. Currently all Lineitem reading operations are re-worked to prototype this future design.

7) Capability for automated testing of data variations returned by older/newer or alternative APIs. Conformity checks for a  list of records and specific field values returned by different APIs are handled.

Project Public Description
Clone of BBwebservices project, to expose more web service operations and demonstrate an alternative architectural design for process flow.

Project UNIX Name
wservices_idla

Homepage URL
http://projects.oscelot.org/gf/project/wservices_idla/

Development Status
Pre-Alpha

License
GPL

Programming Language
Java, C#

Topic
Projects/Blackboard/Building Blocks

Intended Audience
Developers


Main Processing Scenario

Client may specify 
•	one or more from possible Blackboard APIs to be used 
•	severity level of "log" (later referred as "data structure log") supplemented with each of data structures returned 
•	severity level of problem during access of particular field causing an exception to be thrown (thrown here exception is re-handled on record level)
•	severity level of problem during access of the record causing an exception to be thrown (in other words – web interface may return boolean success/failure of record level operation or throw an exception to client at this point of exception handling)
•	Input data structure(s) contains required by web method parameters for data retrieval or data to be persisted.
•	If dataVerbosity is set to CUSTOM, returned data fields are only those that have non-null (missFieldTag) values.

New parameter is nullable and when unspecified then default processing is applied that may differ depending on web method called and actual Blackboard version.
Web service will try sequentially to retrieve data through requested APIs. WebService exception may be thrown out to client if some API is unavailable due to BB library version or method implementation. Processing of each data field supported by particular API is performed according to scenarios below, where checks on possible inconsistencies are performed and details on ways of accruing of each particular field may be provided too.

Data Load processing scenario.
Initialization:
All output structure fields are initialized to null values initially. 

Case: Field is unavailable in particular API or field is not marked for retrieving 
Action: Any field assignment or check is omitted, if field was not previously initialized, it remains null, if it was set through some previously passed API then remains unchanged. Nothing is appended to "data structure log".
Rationale: It will require both more coding and resources if each API-related processing will have to consider all possible fields available through other APIs. Each API will pay attention only for fields that are known to be available within its interface.

Case: Field is known to be unavailable or having wrong value in particular API under some conditions ((i.e. titles and grades of calculated columns).
Action: Field assignment and check should be omitted, however warning message has to be written to "data structure log". Currently it is responsibility of end-point ValueLoader implementation to take care of this. ?? throwDataErrorLevel can be configured for raising an exception upon detection of Warning in DataLog ??
Rationale: In situation when client requests data from particular API where some small pieces of data are unavailable it has to be notified on such inconsistency 

Case: Field is available, but is not set, or some regular and predicted error occurs upon its access (i.e. - Changed date of never changed item, unset grade, etc.)
Action: Field should be set to commonNullValue value if field assumes to be unassigned in particular API, informational message has to be written to "data structure log" on action taken. Default ValueLoader.load() behavior is to match any exception with error condition, so such customization has to be handled in ValueLoader successors.
Rationale: If some error coming from difference in field values obtained through different APIs occurs later then it has to be possible to be "traced" back. So, any field modification action has to be registered. Informational severity level will be turned off by default.

Case: Field is available and has value, current field is null or has the same value as newly obtained.
Action: Field is set to value converted to string. Debug level message is written to "data structure log" on action taken and actual value assigned to the field.
Rationale: Same as above.

Case: Field is available and has value, but its current value differs from newly obtained one.
Action: Field is set to errorValueTag. Error message is written to "data structure log" on action taken, newly obtained value that differs from old one is supplied too.
Rationale: This is actually an aim of the whole story – automated capturing of any inconsistencies in data returned by different Blackboard library and API versions. 

Data modification general processing scenario.

Input data structure(s) contains data for persisting. Fields containing values different from missFieldTag (which is null by default) are stored in Bb. 
Decision on fields that have to be persisted is independent of dataVerbosity value, i.e. behavior is as if dataVerbosity is set to CUSTOM.

In case of INSERT/UPDATE, data is reloaded and compared with saved one. List of Fields that are actually processed is controlled by dataVerbosity value. HOWEVER (important) – returned record will contain values in at least ALL posted fields if dataVerbosity is higher than ID_ONLY. While ONLY fields defined by dataVerbosity are actually reloaded and compared. This comes from internal simplicity needs – just assigning input record to result and passing it for data reload, and apparently it may contain some data in EXTENDED field kind while dataVerbosity is set to STANDARD for example. In case of ID_ONLY it is easy to assign only keys to new record and only those fields will be set on return. 
NONE dataVerbosity mode assumes no data reload. The result will look like ID_ONLY if input record contained correct values in Id field(s). 

In case of DELETE, dataVerbosity higher than ID_ONLY will just cause reassigning of full input record as it was passed from client. ID_ONLY will behave as a NONE.

During data reload, loaded data is compared with the one from input (Id is handled as a special case in order to be possible to reload it after INSERT and its value does not checked for difference), and in case of difference processing is the same as if this data was previously loaded with another API. 

checkPersistedDataWithLoad



Parameterization data structure (optional methods' parameter):
•	apiToUseList  - API to use – list of structures containing 2 strings. First one is apiTag code list with values like DATA_GB, ADMIN_DATA, PLATFORM_GB2, WS_GB. Another one is innerClassOverride, which meaning can be explained probably only with sample. DATA contains User methods like LoadById(), ADMIN_DATA contains methods like LoadByBatchId(). They are not of the same kind and cannot have same names. However someone may like to run them in test mode for comparative accessing of 2 different APIs. Methods mentioned above are accessed through inner classes of DataAccessPack, and this parameter allows to call loadById() web method, that by default transfers to LoadById class of DATA, and redirect it to LoadByBatchId class of ADMIN_DATA. Defaults of this parameter depends on web method and Blackboard version.
•	dataLogSeverity - "Data structure log" max verbosity – DEBUG, INFO, WARN, ERROR. Default – WARN. 
•	Password
•	nullValueTag. Default – “BbWsNull”.
•	errorValueTag. Default – “BbWsError”
•	missFieldTag, Default - null
•	dataVerbosity, NONE, ONLY_ID, MINIMAL, STANDARD, EXTENDED, CUSTOM (NONE can be used only during data modifications meaning that no data should be re-read).
•	dataFieldErrorThrow SeverityLevel – WARNING, ERROR(default), FATAL (primary for testing of detected errors) - for more efficient testing this flag may cause raising of exceptions in case of errors and/or warnings that otherwise are just recorded in DataLog
•	dataRecordErrorThrow SeverityLevel – these practically used probably will be just ERROR and FATAL, i.e. whether to break list processing upon error. Transactional mechanisms will not be supported – data modifications performed before error condition will not be abandoned.
•	insertOrUpdate – BOTH, INSERT, UPDATE, default – BOTH (this is implemented with separate methods – look clearer and more safe, and practically Bb API does not always provide persist method working both for insert and update operations)
•	throwInsertUpdateOnly – true/false, default true. Throws an error if record had to be updated but does not exists or if record had to be inserted, but exists – for UPDATE/INSERT values of insertOrUpdate flag respectively (this gets covered by separate insert/update methods and persist one that behaves as if this flag was off)
•	checkPersistedDataWithLoad – after updating or inserting of data, reloads record and compares to the values that were saved, desired processing should repeat procedure of loading of values with different APIs. Any mismatches will be written to DataLog and exception will be raised according to dataLogErrorLevel. (this becomes auto-covered with the logic of dataVerbosity handling, when it is specified higher than NONE during insert/update operations) 
•	datePattern
•	timePattern

inputRecord or InputList–all ancesrtor of BbWsDataDetails, same fields structure that is returned by data load calls – depending on operation defines :
1.	In case of data load – 
1.	If dataVerbosity was set to CUSTOM, then  null value (missFieldTag) of particular field means that it should be omitted, returned value of this field will be null too
2.	Non-null value defines 
1.	If dataVerbosity was set to CUSTOM, iIndicates that field has to be read and returned 
2.	Parameter of the request, i.e. userId or courseId 1.(i.e., fields representing parameters will be always returned)
2.	In case of Insert or Update
1.	null value indicates that field should not be saved/modified
2.	Non null value indicates new field value and/or identifier of record that has to be modified. Assigning of explicit null value will be indicated with commonNullValue assigned to the field
3.	In case of delete 
1.	non-null fields contains parameterization of delete operation, , i.e. userId or courseId, etc.


Argumentation of null value used as indicator of non-required fields – according to tests made in direction of JAXWS to .NET, nulled strings are not encoded into SOAP messages at all, and thus using of null is reducing necessary traffic.



DataLog record (list of log records is included as one more element in all returned data structures) 
•	Field Name (as it is named in data structure) 
•	Unmodified field value returned by currently used API
•	API used 
•	API pass
•	Severity level 
•	Message 
•	Current server side date and time

Deliberation on use of strict datatypes in returned to client data structures:

Rough mapping of BBGradebookWebService, data.gradebook, platform.gradebook2 and ws.gradebook was made for Lineitem and Score items. It demonstrated light inconsistency of datatypes used internally by Blackboard in various APIs – fields of float/double datatype (pointsPossible, weight, averageScore, etc.) are declared as float in data.gradebook, as double in platform.gradebook2 and again as float in ws.gradebook.

This makes me think that using of String container datatype for any field exposed by BBGradebookWebService would be quite eligible. Where strings by themselves are stored in same (and complemented) data structures that were introduced in v.1.2 of BBGradebookWebService.

Null value will be used for indication of missing/unnecessary field. Actual null values will be transmitted as configurable string tags, i.e. like “!!NULL!!” or any other value (including empty string) assigned to commonNullValue parameter.

Test cases:
nulled parameters
bad logSeverityOverride values 
To be continued...

Step-by-step implementation of custom API accessing classes:
Will be defined after one more cycle of re-design.


Processing flow description
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

ToDo: 
Move logSeverityOverride to bblock properties from web.xml
Think about ObjectFactory per each web method (overridden group of methods). +callback for List<BbObject>
BbWsLog.logForward with Class parameter
Original vendor processing to remain as kind of API
Class names, etc. (parameters set in DataGetter successors) into params. Setting method name should not begin with “set” to be hidden to client.
Rename DataGetter (??DataAccessClassLoader) 
Rename DataLoader (??DataAccesser) and load() methods to ??access())
loadByGivenLineitemBbIdStarter – actual object loading has to be inside catch of DataLoader.load()
Check/test how commonEmptyValue is handled in ValueLoader.load()
Move context-param to properties (admin settings)
RecordListLoader should not call RecordLoader.load() – double exception handling
More informative WebServiceExceptions – take reason from internal exception
Test getLineitemDetailsForGivenLineitemBbIdWithNamedElements()

Last moment appendix on March 09, 2010:
ToDo:
Permissions for particular methods of Ws have to be correlated somehow with general building block permissions defined at the moment of registration, i.e. to be propagated to internal blackboard structures. This would be ideally, but unsure if possible. Otherwise some matrix of API to select/insert/update/delete operations.

Decision on naming convention – from Bb internal names or reality. For example, how to name course accessing API methods if they are named internally ById, but actually work ByBatchUid? 


Known bugs/untested areas - 
1) hash password option seems not to work
2) course copy has 2 problems 
a) some course_contents_fk1 violation, may be some consequence of (b), did not look at it attentively at all
b) timeout delay when copying proxy links defined for a course if proxy server is unavailable - Ws->Bb->proxy server -> exception in Bb internals on delay, exception in Ws on delay, Bb continues execution.
custom portal role
3) course update was not tested at all
4) tests on all other access methods were passed, but only in the mode of dataRecordErrorThrow==FATAL and dataFieldErrorThrow==FATAL and EXTENDED dataVerbosity (see test client for more details). Principally, this combination of options covers most part of code and represents most complex processing, but other processing options combinations were not definite targets of testing at all and therefore should have some bugs.







