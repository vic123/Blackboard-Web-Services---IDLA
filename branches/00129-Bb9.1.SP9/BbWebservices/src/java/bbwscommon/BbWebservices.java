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
import javax.annotation.Resource;

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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadRecordByCourseAndUserId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadListByUserId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadListByCourseId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadListByCourseIdWithUserInfo()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadListByCourseIdAndRole()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipInsertRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipUpdateRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipPersistRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipDeleteRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipInsertListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipUpdateListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipPersistListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipDeleteListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadRecordByCourseAndUserBatchId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseMembershipLoadListByTemplate()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadRecordByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListByTemplate()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userInsertRecordByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userUpdateRecordByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userPersistRecordByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userDeleteRecordByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userPersistListByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userDeleteListByBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadRecordByName()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userInsertRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userUpdateRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userPersistRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userDeleteRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userInsertListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userUpdateListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userPersistListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userDeleteListById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListByEmailAddressFamilyNameGivenName()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListBySearchByUserName()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListObservedByObserverId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListByCourseId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListAvailableObserversByCourseId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListByGroupId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered userLoadListByPrimaryPortalRoleId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadRecordByRoleId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadRecordByRoleName()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadRecordDefault()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadListRemovable()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadListAll()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadListAvailable()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadRecordPrimaryRoleByUserId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadListAllByUserId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleLoadListSecondaryRolesByUserId()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleInsertRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleUpdateRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRolePersistRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleDeleteRecordById()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationLoadListByTemplate()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationLoadRecordByObserverAndUsersBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationInsertRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "InsertRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationPersistRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationPersistRecordByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputRecord,
                    ObserverAssociationAccessPack.class.getName(), "PersistRecordByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public ObserverAssociationDetails observerAssociationDeleteRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationDeleteRecordByObserverAndUsersBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationLoadListByObserverAndUsersBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationInsertListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "InsertListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationPersistListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationPersistListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "PersistListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationDeleteListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered observerAssociationDeleteListByObserverAndUsersBatchUid()", this);
        ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA args
                = new ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA();
        args.initialize(ObserverAssociationDetails.class, params, inputList,
                    ObserverAssociationAccessPack.class.getName(), "DeleteListByObserverAndUsersBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadRecordById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipLoadRecordByUserIdAndPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadRecordByUserIdAndPortalRoleId()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadRecordByUserIdAndPortalRoleId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipInsertRecordById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "InsertRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipPersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipPersistRecordById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "PersistRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipDeleteRecordById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteRecordByUserIdAndPortalRoleId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadListById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipInsertListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipInsertListById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "InsertListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipPersistListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipPersistListById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "PersistListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipDeleteListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipDeleteListById()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteListById");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteListByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipDeleteListByUserId()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteListByUserId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadListByUserId()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadListByUserId");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadListByPortalRoleId()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputRecord,
                    PortalRoleMembershipAccessPack.class.getName(), "LoadListByPortalRoleId");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    /*
     * load by template method of Bb 9.0 PortalRoleMembership API does not generate valid queries to database
     * [TEST\SQLEXPRESS]Incorrect syntax near the keyword 'AND'. - when searched by userId
     * com.inet.tds.be: Column 'p_person_batch_uid' not found. - when searched by userBatchUid
     */
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadListByTemplate()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipLoadListByUserAndPortalRoleBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipInsertListByUserAndPortalRoleBatchUid()", this);
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
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid()", this);
        PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA args
                = new PortalRoleMembershipAccessPack.PortalRoleMembershipArguments_ADMIN_DATA();
        args.initialize(PortalRoleMembershipDetails.class, params, inputList,
                    PortalRoleMembershipAccessPack.class.getName(), "DeleteListByUserAndPortalRoleBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
//**************************************************************************************************************** 
//******************************************************** Course
//****************************************************************************************************************    
    @WebMethod
    public CourseDetails courseLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseLoadRecordById()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "LoadRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails courseLoadRecordByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseLoadRecordByCourseId()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "LoadRecordByCourseId");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails courseLoadRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseLoadRecordByBatchUid()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "LoadRecordByBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public List<CourseDetails> courseLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseLoadListByTemplate()", this);
        CourseAccessPack.CourseArguments_ADMIN_DATA args
                = new CourseAccessPack.CourseArguments_ADMIN_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "LoadListByTemplate");
        BbWsApiProcessor.start(args);
        return args.getResultList();
    }
    @WebMethod
    public CourseDetails courseInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseInsertRecordById()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "InsertRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails courseUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseUpdateRecordById()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "UpdateRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails coursePersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered coursePersistRecordById()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "PersistRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails courseDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseDeleteRecordById()", this);
        CourseAccessPack.CourseArguments_DATA args
                = new CourseAccessPack.CourseArguments_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "DeleteRecordById");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    /**
     * @deprecated  Replaced by courseCopyRecordExactById
     */
    @Deprecated
    @WebMethod
    public CourseDetails courseCopyRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseCopyRecordById()", this);
        CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams args
                = new CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "CopyRecordById", inputTargetCourseRecord, null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    /**
     * @deprecated  Replaced by courseCopyRecordExactByBatchUid with inputCopyParams = null
     */
    @Deprecated
    @WebMethod
    public CourseDetails courseCopyRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseCopyRecordByBatchUid()", this);
        CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams args
                = new CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "CopyRecordByBatchUid", inputTargetCourseRecord, null);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    @WebMethod
    public CourseDetails courseCopyRecordWithParamsById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams 
                    ) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseCopyRecordWithParamsById()", this);
        CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams args
                = new CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "CopyRecordWithParamsById",
                                inputTargetCourseRecord, inputParams);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    @WebMethod
    public CourseDetails courseCopyRecordWithParamsByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseCopyRecordWithParamsByBatchUid()", this);
        CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams args
                = new CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "CopyRecordWithParamsByBatchUid",
                                inputTargetCourseRecord, inputParams);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    @WebMethod
    public CourseDetails courseMergeRecordWithParamsById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseCopyRecordWithCopyParamsById()", this);
        CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams args
                = new CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "MergeRecordById",
                                inputTargetCourseRecord, inputParams);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    @WebMethod
    public CourseDetails courseMergeRecordWithParamsByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseCopyRecordWithCopyParamsById()", this);
        CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams args
                = new CourseAccessPack.CourseArgumentsWithTargetCourseInputAndCopyParams();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "MergeRecordByBatchUid",
                                inputTargetCourseRecord, inputParams);
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }

    @WebMethod
    public CourseDetails courseInsertRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseInsertRecordByBatchUid()", this);
        CourseAccessPack.CourseArguments_ADMIN_DATA args
                = new CourseAccessPack.CourseArguments_ADMIN_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "InsertRecordByBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails courseUpdateRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseUpdateRecordByBatchUid()", this);
        CourseAccessPack.CourseArguments_ADMIN_DATA args
                = new CourseAccessPack.CourseArguments_ADMIN_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "UpdateRecordByBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails coursePersistRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered coursePersistRecordByBatchUid()", this);
        CourseAccessPack.CourseArguments_ADMIN_DATA args
                = new CourseAccessPack.CourseArguments_ADMIN_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "PersistRecordByBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
    }
    @WebMethod
    public CourseDetails courseDeleteRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault
    {
        BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered courseDeleteRecordByBatchUid()", this);
        CourseAccessPack.CourseArguments_ADMIN_DATA args
                = new CourseAccessPack.CourseArguments_ADMIN_DATA();
        args.initialize(CourseDetails.class, params, inputRecord,
                    CourseAccessPack.class.getName(), "DeleteRecordByBatchUid");
        BbWsApiProcessor.start(args);
        return args.getResultRecord();
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