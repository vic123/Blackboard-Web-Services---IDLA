
OSCELOT registration fields:

Project full name
Blackboard Web Services - IDLA

Project Purpose
General project purpose is either to get merged with original webservices project or to remain a clone with at least following improvements:
1) Extension of some operations with newer API capabilities available in later (v.8 and v.9) versions of Blackboard. Currently implemented is reading of Scores with blackboard.platform.gradebook2 API.
2) Complementing of several data items (Users, Courses, Course Enrollments, Institutional Roles, Users assignments to institutional roles, Observer role assignments) to support full list of CRUD (create, read, update, delete) operations.
3) Design and implement capability for dynamic loading of API-specific classes (assures backward compatibility with older Blackboard versions), safe accessing of field values with logging into returned to client data structures (client-side debugging and testing) and global exception handling at limited number of control flow points. Separation of API-specific data accessing operations from remaining processing logic, i.e. all possible simplification of development for new API/items handling classes. Currently all Lineitem reading operations are re-worked to prototype this future design.
4) Capability for automated testing of variations in data returned by older/newer or alternative APIs. Currently conformity checks on list of records and particular field values returned by different APIs are handled.

Project Public Description
Experimental clone of webservices project, it exposes more web service operations and demonstrates alternative design of internal processing.

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

New parameter is nullable and when unspecified then default processing is applied that may differ depending on web method called and actual Blackboard version.
Web service will try sequentially to retrieve data through requested APIs. WebService exception may be thrown out to client if some API is unavailable due to BB library version or method implementation. Processing of each data field supported by particular API is performed according to scenario below, where checks on possible inconsistencies are performed and details on ways of accruing of each particular field may be provided too.

Initialization:
All output structure fields are initialized to null values initially.

Case: Field is unavailable in particular API
Action: Any field assignment or check is omitted, if field was not previously initialized, it remains null, if it was set through some previously passed API then remains unchanged. Nothing is appended to "data structure log".
Rationale: It will require both more coding and resources if each API-related processing will have to consider all possible fields available through other APIs. Each API will pay attention only for fields that are known to be available within its interface.

Case: Field is known to be unavailable or having wrong value in particular API under some conditions ((i.e. titles and grades of calculated columns).
Action: Field assignment and check should be omitted, however warning message has to be written to "data structure log". Currently it is responsibility of end-point ValueLoader implementation to take care of this.
Rationale: In situation when client requests data from particular API where some small pieces of data are unavailable it has to be notified on such inconsistency 

Case: Field is available, but is not set, or some regular and predicted error occurs upon its access (i.e. - Changed date of never changed item, unset grade, etc.)
Action: Field should be set to empty string (commonEmptyValue from parameters) or null if field assumes to be unassigned in particular API, informational message has to be written to "data structure log" on action taken. Default ValueLoader.load() behavior is to match any exception with error condition, so such customization has to be handled in ValueLoader successors.
Rationale: If some error coming from difference in field values obtained through different APIs occurs later then it has to be possible to be "traced" back. So, any field modification action has to be registered. Informational severity level will be turned off by default.

Case: Field is available and has value, current field is null or has the same value as newly obtained.
Action: Field is set to value converted to string. Debug level message is written to "data structure log" on action taken and actual value assigned to the field.
Rationale: Same as above.

Case: Field is available and has value, but its current value differs from newly obtained one.
Action: Field is set to commonErrorValue. Error message is written to "data structure log" on action taken, newly obtained value that differs from old one is supplied too.
Rationale: This is actually an aim of the whole story – automated capturing of any inconsistencies in data returned by different Blackboard library and API versions. 


Parameterization data structure (optional methods' parameter):
•	API to use – string list with values like DATA_GB, PLATFORM_GB2, WS_GB. Default depends on web method and Blackboard version.
•	"Data structure log" max verbosity – DEBUG, INFO, WARN, ERROR. Default – WARN. 
•	Password
•	commonEmptyValue. Default empty string.
•	commonErrorValue. Default – “!!ERROR!!”

Log record (list of log records is included as one more element in all returned data structures) 
•	Field Name (as it is named in data structure) 
•	Unmodified field value returned by currently used API
•	API used 
•	Severity level 
•	Message 
•	Current server side date and time

Deliberation on use of strict datatypes in returned to client data structures:
When using String datatype it is possible to return null, as well as an empty string can be such "value missing" indicator. Probably empty string is not as clear as null value, but it would create fewer problems for clients that for example need just to visualize Blackboard data.

Rough mapping of BBGradebookWebService, data.gradebook, platform.gradebook2 and ws.gradebook was made for Lineitem and Score items. It demonstrated light inconsistency of datatypes used internally by Blackboard in various APIs – fields of float/double datatype (pointsPossible, weight, averageScore, etc.) are declared as float in data.gradebook, as double in platform.gradebook2 and again as float in ws.gradebook.

This makes me think that using of String container datatype for any field exposed by BBGradebookWebService would be quite eligible. Where strings by themselves are stored in same (and complemented) data structures that were introduced in v.1.2 of BBGradebookWebService.

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
Think about ObjectFactory per each web method (overriden group of methods). +callback for List<BbObject>
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



 