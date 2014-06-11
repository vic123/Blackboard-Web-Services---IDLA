package bbwscommon;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceException;
import bbgbws.GradableItemAccessPack_GB2_Gen;
import bbgbws.GradableItemDetails;
import bbgbws.GradableItemParams;
import bbcrsws.*;
import bbuws.*;

@WebService(name="BbWebservices"
//            , serviceName="BbWebservices"
            , targetNamespace="http://projects.oscelot.org/gf/project/wservices_idla/BbWebservices"
//            , endpointInterface = "bbwscommon.IBbWebservices"
            )
public interface IBbWebservices extends IBbWebservices_Gen {
    @WebMethod
    public CourseMembershipDetails courseMembershipLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseMembershipDetails courseMembershipLoadRecordByCourseAndUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByCourseIdWithUserInfo
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByCourseIdAndRole
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseMembershipDetails courseMembershipInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseMembershipDetails courseMembershipUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseMembershipDetails courseMembershipPersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseMembershipDetails courseMembershipDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipInsertListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipUpdateListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipPersistListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipDeleteListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<CourseMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseMembershipDetails courseMembershipLoadRecordByCourseAndUserBatchId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseMembershipDetails> courseMembershipLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userLoadRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userInsertRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userUpdateRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userPersistRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userDeleteRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userPersistListByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userDeleteListByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userLoadRecordByName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userPersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public UserDetails userDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userInsertListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userUpdateListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userPersistListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userDeleteListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<UserDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListByEmailAddressFamilyNameGivenName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListBySearchByUserName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListObservedByObserverId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputCourseRecord") bbcrsws.CourseDetails inputCourseRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListAvailableObserversByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputCourseRecord") bbcrsws.CourseDetails inputCourseRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListByGroupId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputGroupRecord") bbgrpws.GroupDetails inputGroupRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<UserDetails> userLoadListByPrimaryPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") UserDetails inputRecord,
                    @WebParam(name = "inputRoleRecord") bbuws.RoleDetails inputRoleRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordByRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordByRoleName
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordDefault
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListRemovable
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListAll
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListAvailable
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleLoadRecordPrimaryRoleByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord,
                    @WebParam(name = "inputUserRecord") bbuws.UserDetails inputUserRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListAllByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord,
                    @WebParam(name = "inputUserRecord") bbuws.UserDetails inputUserRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleDetails> portalRoleLoadListSecondaryRolesByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord,
                    @WebParam(name = "inputUserRecord") bbuws.UserDetails inputUserRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRolePersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleDetails portalRoleDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public ObserverAssociationDetails observerAssociationLoadRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public ObserverAssociationDetails observerAssociationInsertRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public ObserverAssociationDetails observerAssociationPersistRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public ObserverAssociationDetails observerAssociationDeleteRecordByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") ObserverAssociationDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationLoadListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationInsertListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationPersistListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<ObserverAssociationDetails> observerAssociationDeleteListByObserverAndUsersBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<ObserverAssociationDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipLoadRecordByUserIdAndPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipPersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipInsertListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipPersistListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipDeleteListById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteListByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByUserId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByPortalRoleId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    /*
     * load by template method of Bb 9.0 PortalRoleMembership API does not generate valid queries to database
     * [TEST\SQLEXPRESS]Incorrect syntax near the keyword 'AND'. - when searched by userId
     * com.inet.tds.be: Column 'p_person_batch_uid' not found. - when searched by userBatchUid
     */
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public PortalRoleMembershipDetails portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") PortalRoleMembershipDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipLoadListByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipInsertListByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<PortalRoleMembershipDetails> portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputList") List<PortalRoleMembershipDetails> inputList) throws WebServiceException, BbWsFault;
//****************************************************************************************************************
//******************************************************** Course
//****************************************************************************************************************
    @WebMethod
    public CourseDetails courseLoadRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseLoadRecordByCourseId
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseLoadRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public List<CourseDetails> courseLoadListByTemplate
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseInsertRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseUpdateRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails coursePersistRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseDeleteRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;

    /**
     * @deprecated  Replaced by courseCopyRecordExactById
     */
    @Deprecated
    @WebMethod
    public CourseDetails courseCopyRecordById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord) throws WebServiceException, BbWsFault;

    /**
     * @deprecated  Replaced by courseCopyRecordExactByBatchUid with inputCopyParams = null
     */
    @Deprecated
    @WebMethod
    public CourseDetails courseCopyRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord) throws WebServiceException, BbWsFault;

    @WebMethod
    public CourseDetails courseCopyRecordWithParamsById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseCopyRecordWithParamsByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") bbcrsws.CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseMergeRecordWithParamsById
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseMergeRecordWithParamsByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord,
                    @WebParam(name = "inputTargetCourseRecord") CourseDetails inputTargetCourseRecord,
                    @WebParam(name = "inputParams") CourseCopyParams inputParams
                    ) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseInsertRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseUpdateRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails coursePersistRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
    @WebMethod
    public CourseDetails courseDeleteRecordByBatchUid
                (@WebParam(name = "params") BbWsParams params,
                    @WebParam(name = "inputRecord") CourseDetails inputRecord) throws WebServiceException, BbWsFault;
}
