/*
 *   bbCourse Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbAdded.cs $
 *   Description: Provides C# wrapper methods on Blackboard "Added" Web Service methods. These are the
 *   additional methods added by Viktor and are supplemental in part to the Course and User methods as 
 *   well as adding portal and  observer specific methods.Some of the resturn types are defined in 
 *   the proxy classes while others were specifically developed for use in the application. 
 *   These details classes are found in the BlackBoardWebServices.cs class file
 *  
 *   Created On          : February 24, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-02-24 14:51:05 -0700 (Wed, 24 Feb 2010) $
 *   $LastChangedBy: alex.loepp $
 *   $LastChangedRevision: 2 $       
 *
 *   Change History:   
 *   0.0.0    ATL   Initial Development.
 *
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using bbIDLA.BBAddedService;

namespace bbIDLA
{
    public class BbAdded
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBAddedService.BbWebservices bbAdded = null;

        public BbAdded()
        {
            bbAdded = new bbIDLA.BBAddedService.BbWebservices();
        }

        //Course Membership Load Record By Id
        public courseMembershipDetails CourseMembershipLoadRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadRecordById", ex);
                return null;
            }
        }

        //Course Membership Load List By Id
        public courseMembershipDetails[] CourseMembershipLoadListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.courseMembershipLoadListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadListById", ex);
                return null;
            }
        }

        //Course Membership Load Record By Course And UserId
        public courseMembershipDetails CourseMembershipLoadRecordByCourseAndUserId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadRecordByCourseAndUserId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadRecordByCourseAndUserId", ex);
                return null;
            }
        }

        //Course Membership Load List By UserId
        public courseMembershipDetails[] CourseMembershipLoadListByUserId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadListByUserId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadListByUserId", ex);
                return null;
            }
        }

        //Course Membership Load List By CourseId
        public courseMembershipDetails[] CourseMembershipLoadListByCourseId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadListByCourseId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadListByCourseId", ex);
                return null;
            }
        }

        //Course Membership Load List By CourseId With User Info
        public courseMembershipDetails[] CourseMembershipLoadListByCourseIdWithUserInfo(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadListByCourseIdWithUserInfo(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadListByCourseIdWithUserInfo", ex);
                return null;
            }
        }

        //Course Membership Load List By CourseId And Role
        public courseMembershipDetails[] CourseMembershipLoadListByCourseIdAndRole(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadListByCourseIdAndRole(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadListByCourseIdAndRole", ex);
                return null;
            }
        }

        //Course Membership Insert Record By Id
        public courseMembershipDetails CourseMembershipInsertRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipInsertRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipInsertRecordById", ex);
                return null;
            }
        }

        //Course Membership Update Record By Id
        public courseMembershipDetails CourseMembershipUpdateRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipUpdateRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipUpdateRecordById", ex);
                return null;
            }
        }

        //Course Membership Persist Record By Id
        public courseMembershipDetails CourseMembershipPersistRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipPersistRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipPersistRecordById", ex);
                return null;
            }
        }

        //Course Membership Delete Record By Id
        public courseMembershipDetails CourseMembershipDeleteRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipDeleteRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipDeleteRecordById", ex);
                return null;
            }
        }

        //Course Membership Insert List By Id
        public courseMembershipDetails[] CourseMembershipInsertListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.courseMembershipInsertListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipInsertListById", ex);
                return null;
            }
        }

        //Course Membership Update List By Id
        public courseMembershipDetails[] CourseMembershipUpdateListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.courseMembershipUpdateListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipUpdateListById", ex);
                return null;
            }
        }

        //Course Membership Persist List By Id
        public courseMembershipDetails[] CourseMembershipPersistListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.courseMembershipPersistListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipPersistListById", ex);
                return null;
            }
        }

        //Course Membership Delete List By Id
        public courseMembershipDetails[] CourseMembershipDeleteListById(bbWsParams @params, courseMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.courseMembershipDeleteListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipDeleteListById", ex);
                return null;
            }
        }

        //Course Membership Load Record By Course And User BatchId
        public courseMembershipDetails CourseMembershipLoadRecordByCourseAndUserBatchId(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadRecordByCourseAndUserBatchId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadRecordByCourseAndUserBatchId", ex);
                return null;
            }
        }

        //Course Membership Load List By Template
        public courseMembershipDetails[] CourseMembershipLoadListByTemplate(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadListByTemplate(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadListByTemplate", ex);
                return null;
            }
        }

        //User Load Record By BatchUid
        public userDetails UserLoadRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadRecordByBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadRecordByBatchUid", ex);
                return null;
            }
        }

        //User Load List By Template
        public userDetails[] UserLoadListByTemplate(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadListByTemplate(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListByTemplate", ex);
                return null;
            }
        }

        //User Insert Record By Batch Uid
        public userDetails UserInsertRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userInsertRecordByBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserInsertRecordByBatchUid", ex);
                return null;
            }
        }

        //User Update Record By BatchUid
        public userDetails UserUpdateRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userUpdateRecordByBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserUpdateRecordByBatchUid", ex);
                return null;
            }
        }

        //User Persist Record By BatchUid
        public userDetails UserPersistRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userPersistRecordByBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserPersistRecordByBatchUid", ex);
                return null;
            }
        }

        //User Delete Record By BatchUid
        public userDetails UserDeleteRecordByBatchUid(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userDeleteRecordByBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserDeleteRecordByBatchUid", ex);
                return null;
            }
        }

        //User Persist List By BatchUid
        public userDetails[] UserPersistListByBatchUid(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userPersistListByBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserPersistListByBatchUid", ex);
                return null;
            }
        }

        //User Delete List By Batch Uid
        public userDetails[] UserDeleteListByBatchUid(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userDeleteListByBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserDeleteListByBatchUid", ex);
                return null;
            }
        }

        //User Load Record By Id
        public userDetails UserLoadRecordById(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadRecordById", ex);
                return null;
            }
        }

        //User Load Record By Name
        public userDetails UserLoadRecordByName(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadRecordByName(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadRecordByName", ex);
                return null;
            }
        }

        //User Insert Record By Id
        public userDetails UserInsertRecordById(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userInsertRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserInsertRecordById", ex);
                return null;
            }
        }

        //User Update Record By Id
        public userDetails UserUpdateRecordById(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userUpdateRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserUpdateRecordById", ex);
                return null;
            }
        }

        //User Persist Record By Id
        public userDetails UserPersistRecordById(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userPersistRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserPersistRecordById", ex);
                return null;
            }
        }

        //User Delete Record By Id
        public userDetails UserDeleteRecordById(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userDeleteRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserDeleteRecordById", ex);
                return null;
            }
        }

        //User Load List By Id
        public userDetails[] UserLoadListById(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userLoadListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListById", ex);
                return null;
            }
        }

        //User Insert List By Id
        public userDetails[] UserInsertListById(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userInsertListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserInsertListById", ex);
                return null;
            }
        }

        //User Update List By Id
        public userDetails[] UserUpdateListById(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userUpdateListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserUpdateListById", ex);
                return null;
            }
        }

        //User Persist List By Id
        public userDetails[] UserPersistListById(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userPersistListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserPersistListById", ex);
                return null;
            }
        }

        //User Delete List By Id
        public userDetails[] UserDeleteListById(bbWsParams @params, userDetails[] inputList)
        {
            try
            {
                return bbAdded.userDeleteListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserDeleteListById", ex);
                return null;
            }
        }
        //User Load List By Email Address Family Name Given Name
        public userDetails[] UserLoadListByEmailAddressFamilyNameGivenName(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadListByEmailAddressFamilyNameGivenName(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListByEmailAddressFamilyNameGivenName", ex);
                return null;
            }
        }

        //User Load List By Search By User Name
        public userDetails[] UserLoadListBySearchByUserName(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadListBySearchByUserName(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListBySearchByUserName", ex);
                return null;
            }
        }

        //User Load List Observed By ObserverId
        public userDetails[] UserLoadListObservedByObserverId(bbWsParams @params, userDetails inputRecord)
        {
            try
            {
                return bbAdded.userLoadListObservedByObserverId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListObservedByObserverId", ex);
                return null;
            }
        }

        //User Load List By CourseId
        public userDetails[] UserLoadListByCourseId(bbWsParams @params, userDetails inputRecord, courseDetails inputCourseRecord)
        {
            try
            {
                return bbAdded.userLoadListByCourseId(@params, inputRecord, inputCourseRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListByCourseId", ex);
                return null;
            }
        }

        //User Load List By GroupId
        public userDetails[] UserLoadListByGroupId(bbWsParams @params, userDetails inputRecord, groupDetails inputGroupRecord)
        {
            try
            {
                return bbAdded.userLoadListByGroupId(@params, inputRecord, inputGroupRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListByGroupId", ex);
                return null;
            }
        }

        //User Load List By Primary Portal RoleId
        public userDetails[] UserLoadListByPrimaryPortalRoleId(bbWsParams @params, userDetails inputRecord, roleDetails inputRoleRecord)
        {
            try
            {
                return bbAdded.userLoadListByPrimaryPortalRoleId(@params, inputRecord, inputRoleRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListByPrimaryPortalRoleId", ex);
                return null;
            }
        }

        //Portal Role Load Record By Id
        public portalRoleDetails PortalRoleLoadRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadRecordById", ex);
                return null;
            }
        }

        //Portal Role Load Record By RoleId
        public portalRoleDetails PortalRoleLoadRecordByRoleId(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadRecordByRoleId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadRecordByRoleId", ex);
                return null;
            }
        }

        //Portal Role Load Record By Role Name
        public portalRoleDetails PortalRoleLoadRecordByRoleName(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadRecordByRoleName(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadRecordByRoleName", ex);
                return null;
            }
        }

        //Portal Role Load Record Default
        public portalRoleDetails PortalRoleLoadRecordDefault(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadRecordDefault(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadRecordDefault", ex);
                return null;
            }
        }

        //Portal Role Load List Removable
        public portalRoleDetails[] PortalRoleLoadListRemovable(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadListRemovable(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadListRemovable", ex);
                return null;
            }
        }

        //Portal Role Load List All
        public portalRoleDetails[] PortalRoleLoadListAll(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadListAll(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadListAll", ex);
                return null;
            }
        }

        //Portal Role Load List Available
        public portalRoleDetails[] PortalRoleLoadListAvailable(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadListAvailable(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadListAvailable", ex);
                return null;
            }
        }

        public portalRoleDetails PortalRoleLoadRecordPrimaryRoleByUserId(bbWsParams @params, portalRoleDetails inputRecord, userDetails inputUserRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadRecordPrimaryRoleByUserId(@params, inputRecord, inputUserRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadRecordPrimaryRoleByUserId", ex);
                return null;
            }
        }

        //Portal Role Load List All By UserId
        public portalRoleDetails[] PortalRoleLoadListAllByUserId(bbWsParams @params, portalRoleDetails inputRecord, userDetails inputUserRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadListAllByUserId(@params, inputRecord, inputUserRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadListAllByUserId", ex);
                return null;
            }
        }

        //Portal Role Load List Secondary Roles By UserId
        public portalRoleDetails[] PortalRoleLoadListSecondaryRolesByUserId(bbWsParams @params, portalRoleDetails inputRecord, userDetails inputUserRecord)
        {
            try
            {
                return bbAdded.portalRoleLoadListSecondaryRolesByUserId(@params, inputRecord, inputUserRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleLoadListSecondaryRolesByUserId", ex);
                return null;
            }
        }

        //Portal Role Insert Record By Id
        public portalRoleDetails PortalRoleInsertRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleInsertRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleInsertRecordById", ex);
                return null;
            }
        }

        //Portal Role Update Record By Id
        public portalRoleDetails PortalRoleUpdateRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleUpdateRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleUpdateRecordById", ex);
                return null;
            }
        }
        //Portal Role Persist Record By Id
        public portalRoleDetails PortalRolePersistRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRolePersistRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRolePersistRecordById", ex);
                return null;
            }
        }

        //Portal Role Delete Record By Id
        public portalRoleDetails PortalRoleDeleteRecordById(bbWsParams @params, portalRoleDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleDeleteRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleDeleteRecordById", ex);
                return null;
            }
        }

        //Observer Association Load List By Template
        public observerAssociationDetails[] ObserverAssociationLoadListByTemplate(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            try
            {
                return bbAdded.observerAssociationLoadListByTemplate(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationLoadListByTemplate", ex);
                return null;
            }
        }

        //Observer Association Load Record By Observer And Users BatchUid
        public observerAssociationDetails ObserverAssociationLoadRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            try
            {
                return bbAdded.observerAssociationLoadRecordByObserverAndUsersBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationLoadRecordByObserverAndUsersBatchUid", ex);
                return null;
            }
        }
        //Observer Association Insert Record By Observer And Users BatchUid
        public observerAssociationDetails ObserverAssociationInsertRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            try
            {
                return bbAdded.observerAssociationInsertRecordByObserverAndUsersBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationInsertRecordByObserverAndUsersBatchUid", ex);
                return null;
            }
        }

        //Observer Association Delete Record By Observer And Users BatchUid
        public observerAssociationDetails ObserverAssociationDeleteRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            try
            {
                return bbAdded.observerAssociationDeleteRecordByObserverAndUsersBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationDeleteRecordByObserverAndUsersBatchUid", ex);
                return null;
            }
        }

        //Observer Association Load List By Observer And Users BatchUid
        public observerAssociationDetails[] ObserverAssociationLoadListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            try
            {
                return bbAdded.observerAssociationLoadListByObserverAndUsersBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationLoadListByObserverAndUsersBatchUid", ex);
                return null;
            }
        }
        //Observer Association Insert List By Observer And Users BatchUid
        public observerAssociationDetails[] ObserverAssociationInsertListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            try
            {
                return bbAdded.observerAssociationInsertListByObserverAndUsersBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationInsertListByObserverAndUsersBatchUid", ex);
                return null;
            }
        }
        //Observer Association Delete List By Observer And Users BatchUid
        public observerAssociationDetails[] ObserverAssociationDeleteListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            try
            {
                return bbAdded.observerAssociationDeleteListByObserverAndUsersBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationDeleteListByObserverAndUsersBatchUid", ex);
                return null;
            }
        }
   }
}