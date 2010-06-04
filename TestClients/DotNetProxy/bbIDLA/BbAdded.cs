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
        private bool doRethrow = false; 
        BBAddedService.BbWebservices bbAdded = null;

        public BbAdded()
        {
            bbAdded = new bbIDLA.BBAddedService.BbWebservices();
        }

        public BbAdded(bool doRethrow):this()
        {
            this.doRethrow = doRethrow;
        }

        //CourseMembership Load Record ById
        public courseMembershipDetails CourseMembershipLoadRecordById(bbWsParams @params, courseMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.courseMembershipLoadRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.CourseMembershipLoadRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //CourseMembership Load List ById
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

        //CourseMembership Load Record ByCourseAndUserId
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

        //CourseMembership Load List ByUserId
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

        //CourseMembership Load List ByCourseId
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

        //CourseMembership Load List ByCourseIdWithUserInfo
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

        //CourseMembership Load List ByCourseIdAndRole
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

        //CourseMembership Insert Record ById
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

        //CourseMembership Update Record ById
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

        //CourseMembership Persist Record ById
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

        //CourseMembership Delete Record ById
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

        //CourseMembership Insert List ById
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

        //CourseMembership Update List ById
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

        //CourseMembership Persist List ById
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

        //CourseMembership Delete List ById
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

        //CourseMembership Load Record ByCourseAndUserBatchId
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

        //CourseMembership Load List ByTemplate
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

        //User Load Record ByBatchUid
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

        //User Load List ByTemplate
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

        //User Insert Record ByBatchUid
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

        //User Update Record ByBatchUid
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

        //User Persist Record ByBatchUid
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

        //User Delete Record ByBatchUid
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

        //User Persist List ByBatchUid
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

        //User Delete List ByBatchUid
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

        //User Load Record ById
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

        //User Load Record ByName
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

        //User Insert Record ById
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

        //User Update Record ById
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

        //User Persist Record ById
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

        //User Delete Record ById
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

        //User Load List ById
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

        //User Insert List ById
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

        //User Update List ById
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

        //User Persist List ById
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

        //User Delete List ById
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

        //User Load List ByEmailAddressFamilyNameGivenName
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

        //User Load List BySearchByUserName
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

        //User Load List ObservedByObserverId
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

        //User Load List ByCourseId
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

        //User Load List AvailableObserversByCourseId
        public userDetails[] UserLoadListAvailableObserversByCourseId(bbWsParams @params, userDetails inputRecord, courseDetails inputCourseRecord)
        {
            try
            {
                return bbAdded.userLoadListAvailableObserversByCourseId(@params, inputRecord, inputCourseRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.UserLoadListAvailableObserversByCourseId", ex);
                return null;
            }
        }

        //User Load List ByGroupId
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

        //User Load List ByPrimaryPortalRoleId
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

        //PortalRole Load Record ById
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

        //PortalRole Load Record ByRoleId
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

        //PortalRole Load Record ByRoleName
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

        //PortalRole Load Record Default
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

        //PortalRole Load List Removable
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

        //PortalRole Load List All
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

        //PortalRole Load List Available
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

        //PortalRole Load Record PrimaryRoleByUserId
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

        //PortalRole Load List AllByUserId
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

        //PortalRole Load List SecondaryRolesByUserId
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

        //PortalRole Insert Record ById
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

        //PortalRole Update Record ById
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

        //PortalRole Persist Record ById
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

        //PortalRole Delete Record ById
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

        //ObserverAssociation Load List ByTemplate
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

        //ObserverAssociation Load Record ByObserverAndUsersBatchUid
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

        //ObserverAssociation Insert Record ByObserverAndUsersBatchUid
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

        //ObserverAssociation Delete Record ByObserverAndUsersBatchUid
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

        //ObserverAssociation Load List ByObserverAndUsersBatchUid
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

        //ObserverAssociation Insert List ByObserverAndUsersBatchUid
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

        //ObserverAssociation Delete List ByObserverAndUsersBatchUid
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

        //PortalRoleMembership Load Record ById
        public portalRoleMembershipDetails PortalRoleMembershipLoadRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadRecordById", ex);
                return null;
            }
        }

        //PortalRoleMembership Load Record ByUserIdAndPortalRoleId
        public portalRoleMembershipDetails PortalRoleMembershipLoadRecordByUserIdAndPortalRoleId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadRecordByUserIdAndPortalRoleId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadRecordByUserIdAndPortalRoleId", ex);
                return null;
            }
        }

        //PortalRoleMembership Insert Record ById
        public portalRoleMembershipDetails PortalRoleMembershipInsertRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipInsertRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipInsertRecordById", ex);
                return null;
            }
        }

        //PortalRoleMembership Persist Record ById
        public portalRoleMembershipDetails PortalRoleMembershipPersistRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipPersistRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipPersistRecordById", ex);
                return null;
            }
        }

        //PortalRoleMembership Delete Record ById
        public portalRoleMembershipDetails PortalRoleMembershipDeleteRecordById(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipDeleteRecordById(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipDeleteRecordById", ex);
                return null;
            }
        }

        //PortalRoleMembership Delete Record ByUserIdAndPortalRoleId
        public portalRoleMembershipDetails PortalRoleMembershipDeleteRecordByUserIdAndPortalRoleId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipDeleteRecordByUserIdAndPortalRoleId", ex);
                return null;
            }
        }

        //PortalRoleMembership Load List ById
        public portalRoleMembershipDetails[] PortalRoleMembershipLoadListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadListById", ex);
                return null;
            }
        }

        //PortalRoleMembership Insert List ById
        public portalRoleMembershipDetails[] PortalRoleMembershipInsertListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipInsertListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipInsertListById", ex);
                return null;
            }
        }

        //PortalRoleMembership Persist List ById
        public portalRoleMembershipDetails[] PortalRoleMembershipPersistListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipPersistListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipPersistListById", ex);
                return null;
            }
        }

        //PortalRoleMembership Delete List ById
        public portalRoleMembershipDetails[] PortalRoleMembershipDeleteListById(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipDeleteListById(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipDeleteListById", ex);
                return null;
            }
        }

        //PortalRoleMembership Delete Record ByUserId
        public portalRoleMembershipDetails PortalRoleMembershipDeleteRecordByUserId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipDeleteListByUserId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipDeleteRecordByUserId", ex);
                return null;
            }
        }

        //PortalRoleMembership Load List ByUserId
        public portalRoleMembershipDetails[] PortalRoleMembershipLoadListByUserId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadListByUserId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadListByUserId", ex);
                return null;
            }
        }

        //PortalRoleMembership Load List ByPortalRoleId
        public portalRoleMembershipDetails[] PortalRoleMembershipLoadListByPortalRoleId(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadListByPortalRoleId(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadListByPortalRoleId", ex);
                return null;
            }
        }

        //PortalRoleMembership Load List ByTemplate
        public portalRoleMembershipDetails[] PortalRoleMembershipLoadListByTemplate(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadListByTemplate(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadListByTemplate", ex);
                return null;
            }
        }

        //PortalRoleMembership Load Record ByUserAndPortalRoleBatchUid
        public portalRoleMembershipDetails PortalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid", ex);
                return null;
            }
        }

        //PortalRoleMembership Insert Record ByUserAndPortalRoleBatchUid
        public portalRoleMembershipDetails PortalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid", ex);
                return null;
            }
        }

        //PortalRoleMembership Delete Record ByUserAndPortalRoleBatchUid
        public portalRoleMembershipDetails PortalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails inputRecord)
        {
            try
            {
                return bbAdded.portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid", ex);
                return null;
            }
        }

        //PortalRoleMembership Load List ByUserAndPortalRoleBatchUid
        public portalRoleMembershipDetails[] PortalRoleMembershipLoadListByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipLoadListByUserAndPortalRoleBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipLoadListByUserAndPortalRoleBatchUid", ex);
                return null;
            }
        }

        //PortalRoleMembership Insert List ByUserAndPortalRoleBatchUid
        public portalRoleMembershipDetails[] PortalRoleMembershipInsertListByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipInsertListByUserAndPortalRoleBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipInsertListByUserAndPortalRoleBatchUid", ex);
                return null;
            }
        }

        //PortalRoleMembership Delete List ByUserAndPortalRoleBatchUid
        public portalRoleMembershipDetails[] PortalRoleMembershipDeleteListByUserAndPortalRoleBatchUid(bbWsParams @params, portalRoleMembershipDetails[] inputList)
        {
            try
            {
                return bbAdded.portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.PortalRoleMembershipDeleteListByUserAndPortalRoleBatchUid", ex);
                return null;
            }
        }

    }
}