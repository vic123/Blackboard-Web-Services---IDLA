/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

/**
 *
 * @author vic
 */
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.WebServiceException;

import java.util.List;

import bbcrsws.*;
import bbuws.*;

import blackboard.platform.log.LogService;

@WebService(name="BbWebservices", serviceName="BbWebservices", targetNamespace="http://projects.oscelot.org/gf/project/wservices_idla/BbWebservices")
public class BbWebservices {

    static class UserApiProcessor extends BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserInput> {
        static public void start(UserAccessPack.UserArgumentsWithUserInput args) throws BbWsFault{
            UserApiProcessor api_proc = new UserApiProcessor();
            api_proc.initialize(args);
            api_proc.run();
        }
    }

    @WebMethod
    public CourseMembershipDetails courseMembershipLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputList,
                    CourseMembershipAccessPack.class.getName(), "LoadListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public CourseMembershipDetails courseMembershipLoadRecordByCourseAndUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByCourseAndUserId()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadRecordByCourseAndUserId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByUserId()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadListByUserId");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByCourseId()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadListByCourseId");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByCourseIdWithUserInfo
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByCourseIdWithUserInfo()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadListByCourseIdWithUserInfo");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByCourseIdAndRole
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByCourseIdAndRole()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadListByCourseIdAndRole");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public CourseMembershipDetails courseMembershipInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertRecordById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "InsertRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseMembershipDetails courseMembershipUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateRecordById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "UpdateRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseMembershipDetails courseMembershipPersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistRecordById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "PersistRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseMembershipDetails courseMembershipDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteRecordById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "DeleteRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipInsertListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertListById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputList,
                    CourseMembershipAccessPack.class.getName(), "InsertListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipUpdateListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateListById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputList,
                    CourseMembershipAccessPack.class.getName(), "UpdateListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipPersistListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistListById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputList,
                    CourseMembershipAccessPack.class.getName(), "PersistListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipDeleteListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteListById()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputList,
                    CourseMembershipAccessPack.class.getName(), "DeleteListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public CourseMembershipDetails courseMembershipLoadRecordByCourseAndUserBatchId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByCourseAndUserBatchId()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadRecordByCourseAndUserBatchId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByTemplate()", this);
        CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA args
                = new CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA();
        args.initialize(CourseMembershipDetails.class, params, inputRecord,
                    CourseMembershipAccessPack.class.getName(), "LoadListByTemplate");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public UserDetails userLoadRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadRecordByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<UserDetails> userLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByTemplate()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListByTemplate");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public UserDetails userInsertRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertRecordByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "InsertRecordByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userUpdateRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateRecordByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "UpdateRecordByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userPersistRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistRecordByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "PersistRecordByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userDeleteRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteRecordByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "DeleteRecordByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<UserDetails> userPersistListByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistListByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "PersistListByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userDeleteListByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteListByBatchUid()", this);
        UserAccessPack.UserArguments_ADMIN_DATA args
                = new UserAccessPack.UserArguments_ADMIN_DATA();
        args.initialize(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "DeleteListByBatchUid");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public UserDetails userLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadRecordById");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userLoadRecordByName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByName()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadRecordByName");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertRecordById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "InsertRecordById");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateRecordById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "UpdateRecordById");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userPersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistRecordById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "PersistRecordById");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public UserDetails userDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteRecordById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "DeleteRecordById");
        UserApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<UserDetails> userLoadListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "LoadListById");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userInsertListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertListById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "InsertListById");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userUpdateListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateListById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "UpdateListById");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userPersistListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistListById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "PersistListById");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userDeleteListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteListById()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputList,
                    UserAccessPack.class.getName(), "DeleteListById");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userLoadListByEmailAddressFamilyNameGivenName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByEmailAddressFamilyNameGivenName()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListByEmailAddressFamilyNameGivenName");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userLoadListBySearchByUserName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListBySearchByUserName()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListBySearchByUserName");
        UserApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userLoadListObservedByObserverId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListObservedByObserverId()", this);
        UserAccessPack.UserArgumentsWithUserInput args
                = UserAccessPack.UserArgumentsWithUserInput.create(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListObservedByObserverId");
        UserApiProcessor.start(args);
        return args.getResultList();
    }


    @WebMethod
    public List<UserDetails> userLoadListByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputCourseRecord") bbcrsws.CourseDetails inputCourseRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByCourseId()", this);
        UserAccessPack.UserArgumentsWithUserAndCourseInput args
                = new UserAccessPack.UserArgumentsWithUserAndCourseInput();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListByCourseId");
        args.setInputCourseRecord(inputCourseRecord);
            BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndCourseInput> api_proc
                = new BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndCourseInput>();
            api_proc.initialize(args);
            api_proc.run();

        return args.getResultList();
    }

    @WebMethod
    public List<UserDetails> userLoadListAvailableObserversByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputCourseRecord") bbcrsws.CourseDetails inputCourseRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListAvailableObserversByCourseId()", this);
        UserAccessPack.UserArgumentsWithUserAndCourseInput args
                = new UserAccessPack.UserArgumentsWithUserAndCourseInput();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListAvailableObserversByCourseId");
        args.setInputCourseRecord(inputCourseRecord);
            BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndCourseInput> api_proc
                = new BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndCourseInput>();
            api_proc.initialize(args);
            api_proc.run();

        return args.getResultList();
    }

    @WebMethod
    public List<UserDetails> userLoadListByGroupId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputGroupRecord") bbgrpws.GroupDetails inputGroupRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByGroupId()", this);
        UserAccessPack.UserArgumentsWithUserAndGroupInput args
                = new UserAccessPack.UserArgumentsWithUserAndGroupInput();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListByGroupId");
        args.setInputGroupRecord(inputGroupRecord);
            BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndGroupInput> api_proc
                = new BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndGroupInput>();
            api_proc.initialize(args);
            api_proc.run();

        return args.getResultList();
    }
    @WebMethod
    public List<UserDetails> userLoadListByPrimaryPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputRoleRecord") bbuws.RoleDetails inputRoleRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByPrimaryRoleId()", this);
        UserAccessPack.UserArgumentsWithUserAndRoleInput args
                = new UserAccessPack.UserArgumentsWithUserAndRoleInput();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListByPrimaryPortalRoleId");
        args.setInputRoleRecord(inputRoleRecord);
            BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndRoleInput> api_proc
                = new BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndRoleInput>();
            api_proc.initialize(args);
            api_proc.run();

        return args.getResultList();
    }







    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordById()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordByRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByRoleId()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadRecordByRoleId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordByRoleName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByRoleName()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadRecordByRoleName");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordDefault
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordDefault()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadRecordDefault");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListRemovable
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListRemovable()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadListRemovable");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListAll
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListAll()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadListAll");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListAvailable
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListAvailable()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadListAvailable");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }


    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordPrimaryRoleByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord,
                    @WebParam(name = "inputUserRecord") bbuws.UserDetails inputUserRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordPrimaryRoleByUserId()", this);
        PortalRoleAccessPack.PortalRoleArgumentsWithUserInput args
                = new PortalRoleAccessPack.PortalRoleArgumentsWithUserInput();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadRecordPrimaryRoleByUserId");
        args.setInputUserRecord(inputUserRecord);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListAllByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord,
                    @WebParam(name = "inputUserRecord") bbuws.UserDetails inputUserRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListAllByUserId()", this);
        PortalRoleAccessPack.PortalRoleArgumentsWithUserInput args
                = new PortalRoleAccessPack.PortalRoleArgumentsWithUserInput();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadListAllByUserId");
        args.setInputUserRecord(inputUserRecord);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListSecondaryRolesByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord,
                    @WebParam(name = "inputUserRecord") bbuws.UserDetails inputUserRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListSecondaryRolesByUserId()", this);
        PortalRoleAccessPack.PortalRoleArgumentsWithUserInput args
                = new PortalRoleAccessPack.PortalRoleArgumentsWithUserInput();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "LoadListSecondaryRolesByUserId");
        args.setInputUserRecord(inputUserRecord);
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }




    @WebMethod
    public PortalRoleDetails portalRoleInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertRecordById()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "InsertRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleDetails portalRoleUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateRecordById()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "UpdateRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleDetails portalRolePersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistRecordById()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "PersistRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleDetails portalRoleDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteRecordById()", this);
        PortalRoleAccessPack.PortalRoleArguments args
                = new PortalRoleAccessPack.PortalRoleArguments();
        args.initialize(PortalRoleDetails.class, params, inputRecord,
                    PortalRoleAccessPack.class.getName(), "DeleteRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }




    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByTemplate()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "LoadListByTemplate");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationLoadRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "LoadRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationInsertRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "InsertRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationDeleteRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "DeleteRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationLoadListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "LoadListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationInsertListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "InsertListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationDeleteListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "DeleteListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }


    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByTemplate()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadListByTemplate");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadRecordByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadRecordByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertRecordByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "InsertRecordByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteRecordByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteRecordByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadListByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipInsertListByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered insertListByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "InsertListByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered deleteListByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteListByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }




}

/*
     @WebMethod
    public List<UserDetails> userLoadListAvailableObserversByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadListAvailableObserversByCourseId()", this);
        UserAccessPack.UserArgumentsWithUserAndCourseInput args
                = new UserAccessPack.UserArgumentsWithUserAndCourseInput();
        args.initialize(UserDetails.class, params, inputRecord,
                    UserAccessPack.class.getName(), "LoadListAvailableObserversByCourseId");
        args.setInputRecord(inputRecord);
            BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndCourseInput> api_proc
                = new BbWsApiProcessor<UserAccessPack.UserArgumentsWithUserAndCourseInput>();
            api_proc.initialize(args);
            api_proc.run();

        return args.getResultList();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationUpdateRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "UpdateRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationPersistRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "PersistRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationUpdateListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered updateListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "UpdateListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationPersistListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered persistListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "PersistListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }

 */