using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

using bbIDLA;
using bbIDLA.BBAddedService;
//!!using bbws;


/// <summary>
/// Summary description for BbWsWebReference
/// </summary>
public class BbWsWebReference : bbIDLA.BbAdded
{
        public BbWsWebReference(bool doRethrow) : base(doRethrow) { }

        public courseMembershipDetails courseMembershipLoadRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadRecordById(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipLoadListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            return base.CourseMembershipLoadListById(@params, inputList);
        }
        public courseMembershipDetails courseMembershipLoadRecordByCourseAndUserId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadRecordByCourseAndUserId(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipLoadListByUserId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadListByUserId(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipLoadListByCourseId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadListByCourseId(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipLoadListByCourseIdWithUserInfo(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadListByCourseIdWithUserInfo(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipLoadListByCourseIdAndRole(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadListByCourseIdAndRole(@params, inputRecord);
        }
        public courseMembershipDetails courseMembershipInsertRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipInsertRecordById(@params, inputRecord);
        }
        public courseMembershipDetails courseMembershipUpdateRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipUpdateRecordById(@params, inputRecord);
        }
        public courseMembershipDetails courseMembershipPersistRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipPersistRecordById(@params, inputRecord);
        }
        public courseMembershipDetails courseMembershipDeleteRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipDeleteRecordById(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipInsertListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            return base.CourseMembershipInsertListById(@params, inputList);
        }
        public courseMembershipDetails[] courseMembershipUpdateListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            return base.CourseMembershipUpdateListById(@params, inputList);
        }
        public courseMembershipDetails[] courseMembershipPersistListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            return base.CourseMembershipPersistListById(@params, inputList);
        }
        public courseMembershipDetails[] courseMembershipDeleteListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            return base.CourseMembershipDeleteListById(@params, inputList);
        }
        public courseMembershipDetails courseMembershipLoadRecordByCourseAndUserBatchId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadRecordByCourseAndUserBatchId(@params, inputRecord);
        }
        public courseMembershipDetails[] courseMembershipLoadListByTemplate(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            return base.CourseMembershipLoadListByTemplate(@params, inputRecord);
        }
        public userDetails userLoadRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadRecordByBatchUid(@params, inputRecord);
        }
        public userDetails[] userLoadListByTemplate(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadListByTemplate(@params, inputRecord);
        }
        public userDetails userInsertRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserInsertRecordByBatchUid(@params, inputRecord);
        }
        public userDetails userUpdateRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserUpdateRecordByBatchUid(@params, inputRecord);
        }
        public userDetails userPersistRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserPersistRecordByBatchUid(@params, inputRecord);
        }
        public userDetails userDeleteRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserDeleteRecordByBatchUid(@params, inputRecord);
        }
        public userDetails[] userPersistListByBatchUid(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserPersistListByBatchUid(@params, inputList);
        }
        public userDetails[] userDeleteListByBatchUid(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserDeleteListByBatchUid(@params, inputList);
        }
        public userDetails userLoadRecordById(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadRecordById(@params, inputRecord);
        }
        public userDetails userLoadRecordByName(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadRecordByName(@params, inputRecord);
        }
        public userDetails userInsertRecordById(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserInsertRecordById(@params, inputRecord);
        }
        public userDetails userUpdateRecordById(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserUpdateRecordById(@params, inputRecord);
        }
        public userDetails userPersistRecordById(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserPersistRecordById(@params, inputRecord);
        }
        public userDetails userDeleteRecordById(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserDeleteRecordById(@params, inputRecord);
        }
        public userDetails[] userLoadListById(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserLoadListById(@params, inputList);
        }
        public userDetails[] userInsertListById(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserInsertListById(@params, inputList);
        }
        public userDetails[] userUpdateListById(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserUpdateListById(@params, inputList);
        }
        public userDetails[] userPersistListById(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserPersistListById(@params, inputList);
        }
        public userDetails[] userDeleteListById(bbWsParams @params, userDetails[] inputList)
        {
            return base.UserDeleteListById(@params, inputList);
        }
        public userDetails[] userLoadListByEmailAddressFamilyNameGivenName(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadListByEmailAddressFamilyNameGivenName(@params, inputRecord);
        }
        public userDetails[] userLoadListBySearchByUserName(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadListBySearchByUserName(@params, inputRecord);
        }
        public userDetails[] userLoadListObservedByObserverId(bbWsParams @params, userDetails inputRecord)
        {
            return base.UserLoadListObservedByObserverId(@params, inputRecord);
        }
        public userDetails[] userLoadListByCourseId(bbWsParams @params, userDetails inputRecord, courseDetails inputCourseRecord)
        {
            return base.UserLoadListByCourseId(@params, inputRecord, inputCourseRecord);
        }
        public userDetails[] userLoadListAvailableObserversByCourseId(bbWsParams @params, userDetails inputRecord, courseDetails inputCourseRecord)
        {
            return base.UserLoadListAvailableObserversByCourseId(@params, inputRecord, inputCourseRecord);
        }
        public userDetails[] userLoadListByGroupId(bbWsParams @params, userDetails inputRecord, groupDetails inputGroupRecord)
        {
            return base.UserLoadListByGroupId(@params, inputRecord, inputGroupRecord);
        }
        public userDetails[] userLoadListByPrimaryPortalRoleId(bbWsParams @params, userDetails inputRecord, roleDetails inputRoleRecord)
        {
            return base.UserLoadListByPrimaryPortalRoleId(@params, inputRecord, inputRoleRecord);
        }
        public portalRoleDetails portalRoleLoadRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadRecordById(@params, inputRecord);
        }
        public portalRoleDetails portalRoleLoadRecordByRoleId(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadRecordByRoleId(@params, inputRecord);
        }
        public portalRoleDetails portalRoleLoadRecordByRoleName(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadRecordByRoleName(@params, inputRecord);
        }
        public portalRoleDetails portalRoleLoadRecordDefault(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadRecordDefault(@params, inputRecord);
        }
        public portalRoleDetails[] portalRoleLoadListRemovable(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadListRemovable(@params, inputRecord);
        }
        public portalRoleDetails[] portalRoleLoadListAll(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadListAll(@params, inputRecord);
        }
        public portalRoleDetails[] portalRoleLoadListAvailable(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleLoadListAvailable(@params, inputRecord);
        }
        public portalRoleDetails portalRoleLoadRecordPrimaryRoleByUserId(bbWsParams @params, portalRoleDetails inputRecord, userDetails inputUserRecord)
        {
            return base.PortalRoleLoadRecordPrimaryRoleByUserId(@params, inputRecord, inputUserRecord);
        }
        public portalRoleDetails[] portalRoleLoadListAllByUserId(bbWsParams @params, portalRoleDetails inputRecord, userDetails inputUserRecord)
        {
            return base.PortalRoleLoadListAllByUserId(@params, inputRecord, inputUserRecord);
        }
        public portalRoleDetails[] portalRoleLoadListSecondaryRolesByUserId(bbWsParams @params, portalRoleDetails inputRecord, userDetails inputUserRecord)
        {
            return base.PortalRoleLoadListSecondaryRolesByUserId(@params, inputRecord, inputUserRecord);
        }
        public portalRoleDetails portalRoleInsertRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleInsertRecordById(@params, inputRecord);
        }
        public portalRoleDetails portalRoleUpdateRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleUpdateRecordById(@params, inputRecord);
        }
        public portalRoleDetails portalRolePersistRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRolePersistRecordById(@params, inputRecord);
        }
        public portalRoleDetails portalRoleDeleteRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            return base.PortalRoleDeleteRecordById(@params, inputRecord);
        }
        public observerAssociationDetails[] observerAssociationLoadListByTemplate(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            return base.ObserverAssociationLoadListByTemplate(@params, inputRecord);
        }
        public observerAssociationDetails observerAssociationLoadRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            return base.ObserverAssociationLoadRecordByObserverAndUsersBatchUid(@params, inputRecord);
        }
        public observerAssociationDetails observerAssociationInsertRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            return base.ObserverAssociationInsertRecordByObserverAndUsersBatchUid(@params, inputRecord);
        }
        public observerAssociationDetails observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            return base.ObserverAssociationPersistRecordByObserverAndUsersBatchUid(@params, inputRecord);
        }
        public observerAssociationDetails observerAssociationDeleteRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            return base.ObserverAssociationDeleteRecordByObserverAndUsersBatchUid(@params, inputRecord);
        }
        public observerAssociationDetails[] observerAssociationLoadListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            return base.ObserverAssociationLoadListByObserverAndUsersBatchUid(@params, inputList);
        }
        public observerAssociationDetails[] observerAssociationInsertListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            return base.ObserverAssociationInsertListByObserverAndUsersBatchUid(@params, inputList);
        }
        public observerAssociationDetails[] observerAssociationPersistListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            return base.ObserverAssociationPersistListByObserverAndUsersBatchUid(@params, inputList);
        }
        public observerAssociationDetails[] observerAssociationDeleteListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            return base.ObserverAssociationDeleteListByObserverAndUsersBatchUid(@params, inputList);
        }
        public portalRoleMembershipDetails portalRoleMembershipLoadRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipLoadRecordById(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipLoadRecordByUserIdAndPortalRoleId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipLoadRecordByUserIdAndPortalRoleId(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipInsertRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipInsertRecordById(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipPersistRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipPersistRecordById(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipDeleteRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipDeleteRecordById(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipDeleteRecordByUserIdAndPortalRoleId(@params, inputRecord);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipLoadListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipLoadListById(@params, inputList);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipInsertListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipInsertListById(@params, inputList);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipPersistListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipPersistListById(@params, inputList);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipDeleteListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipDeleteListById(@params, inputList);
        }
        public portalRoleMembershipDetails portalRoleMembershipDeleteListByUserId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipDeleteRecordByUserId(@params, inputRecord);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipLoadListByUserId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipLoadListByUserId(@params, inputRecord);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipLoadListByPortalRoleId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipLoadListByPortalRoleId(@params, inputRecord);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipLoadListByTemplate(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipLoadListByTemplate(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid(@params, inputRecord);
        }
        public portalRoleMembershipDetails portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            return base.PortalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid(@params, inputRecord);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipLoadListByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipLoadListByUserAndPortalRoleBatchUid(@params, inputList);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipInsertListByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipInsertListByUserAndPortalRoleBatchUid(@params, inputList);
        }
        public portalRoleMembershipDetails[] portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            return base.PortalRoleMembershipDeleteListByUserAndPortalRoleBatchUid(@params, inputList);
        }

        public courseDetails courseLoadRecordById(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseLoadRecordById(@params, inputRecord);
        }
        public courseDetails courseLoadRecordByCourseId(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseLoadRecordByCourseId(@params, inputRecord);
        }
        public courseDetails courseLoadRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseLoadRecordByBatchUid(@params, inputRecord);
        }
        public courseDetails[] courseLoadListByTemplate(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseLoadListByTemplate(@params, inputRecord);
        }
        public courseDetails courseInsertRecordById(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseInsertRecordById(@params, inputRecord);
        }
        public courseDetails courseUpdateRecordById(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseUpdateRecordById(@params, inputRecord);
        }
        public courseDetails coursePersistRecordById(bbWsParams @params, courseDetails inputRecord) {
            return base.CoursePersistRecordById(@params, inputRecord);
        }
        public courseDetails courseDeleteRecordById(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseDeleteRecordById(@params, inputRecord);
        }
        public courseDetails courseCopyRecordById(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord) {
            return base.CourseCopyRecordById(@params, inputRecord, inputTargetCourseRecord);
        }
        public courseDetails courseCopyRecordByBatchUid(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord) {
            return base.CourseCopyRecordByBatchUid(@params, inputRecord, inputTargetCourseRecord);
        }
        public courseDetails courseInsertRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseInsertRecordByBatchUid(@params, inputRecord);
        }
        public courseDetails courseUpdateRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseUpdateRecordByBatchUid(@params, inputRecord);
        }
        public courseDetails coursePersistRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            return base.CoursePersistRecordByBatchUid(@params, inputRecord);
        }
        public courseDetails courseDeleteRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            return base.CourseDeleteRecordByBatchUid(@params, inputRecord);
        }

}
