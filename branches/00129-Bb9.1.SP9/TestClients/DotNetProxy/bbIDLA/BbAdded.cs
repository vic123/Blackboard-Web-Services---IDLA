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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
            }
        }
        //ObserverAssociation Persist Record ByObserverAndUsersBatchUid
        public observerAssociationDetails ObserverAssociationPersistRecordByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails inputRecord)
        {
            try
            {
                return bbAdded.observerAssociationPersistRecordByObserverAndUsersBatchUid(@params, inputRecord);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationPersistRecordByObserverAndUsersBatchUid", ex);
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
            }
        }
        //ObserverAssociation Persist List ByObserverAndUsersBatchUid
        public observerAssociationDetails[] ObserverAssociationPersistListByObserverAndUsersBatchUid(bbWsParams @params, observerAssociationDetails[] inputList)
        {
            try
            {
                return bbAdded.observerAssociationPersistListByObserverAndUsersBatchUid(@params, inputList);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAdded.ObserverAssociationPersistListByObserverAndUsersBatchUid", ex);
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
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
                if (doRethrow) throw;
                else return null;
            }
        }


        //Course Load Record ById
        public courseDetails CourseLoadRecordById(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseLoadRecordById(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseLoadRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Load Record ByCourseId
        public courseDetails CourseLoadRecordByCourseId(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseLoadRecordByCourseId(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseLoadRecordByCourseId", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Load Record ByBatchUid
        public courseDetails CourseLoadRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseLoadRecordByBatchUid(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseLoadRecordByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Load List ByTemplate
        public courseDetails[] CourseLoadListByTemplate(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseLoadListByTemplate(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseLoadListByTemplate", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Insert Record ById
        public courseDetails CourseInsertRecordById(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseInsertRecordById(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseInsertRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Update Record ById
        public courseDetails CourseUpdateRecordById(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseUpdateRecordById(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseUpdateRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Persist Record ById
        public courseDetails CoursePersistRecordById(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.coursePersistRecordById(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CoursePersistRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Delete Record ById
        public courseDetails CourseDeleteRecordById(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseDeleteRecordById(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseDeleteRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Copy Record ById
        public courseDetails CourseCopyRecordById(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord) {
            try {
                return bbAdded.courseCopyRecordById(@params, inputRecord, inputTargetCourseRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseCopyRecordById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Copy Record ByBatchUid
        public courseDetails CourseCopyRecordByBatchUid(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord) {
            try {
                return bbAdded.courseCopyRecordByBatchUid(@params, inputRecord, inputTargetCourseRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseCopyRecordByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Insert Record ByBatchUid
        public courseDetails CourseInsertRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseInsertRecordByBatchUid(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseInsertRecordByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Update Record ByBatchUid
        public courseDetails CourseUpdateRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseUpdateRecordByBatchUid(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseUpdateRecordByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Persist Record ByBatchUid
        public courseDetails CoursePersistRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.coursePersistRecordByBatchUid(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CoursePersistRecordByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Delete Record ByBatchUid
        public courseDetails CourseDeleteRecordByBatchUid(bbWsParams @params, courseDetails inputRecord) {
            try {
                return bbAdded.courseDeleteRecordByBatchUid(@params, inputRecord);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseDeleteRecordByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }


        //Course Copy Record WithParamsById
        public courseDetails CourseCopyRecordWithParamsById(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord, courseCopyParams inputCopyParams) {
            try {
                return bbAdded.courseCopyRecordWithParamsById(@params, inputRecord, inputTargetCourseRecord, inputCopyParams);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseCopyRecordWithParamsById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Copy Record WithParamsByBatchUid
        public courseDetails CourseCopyRecordWithParamsByBatchUid(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord, courseCopyParams inputCopyParams) {
            try {
                return bbAdded.courseCopyRecordWithParamsByBatchUid(@params, inputRecord, inputTargetCourseRecord, inputCopyParams);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseCopyRecordWithParamsByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Merge Record WithParamsById
        public courseDetails CourseMergeRecordWithParamsById(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord, courseCopyParams inputCopyParams) {
            try {
                return bbAdded.courseMergeRecordWithParamsById(@params, inputRecord, inputTargetCourseRecord, inputCopyParams);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseMergeRecordWithParamsById", ex);
                if (doRethrow) throw;
                else return null;
            }
        }

        //Course Merge Record WithParamsByBatchUid
        public courseDetails CourseMergeRecordWithParamsByBatchUid(bbWsParams @params, courseDetails inputRecord, courseDetails inputTargetCourseRecord, courseCopyParams inputCopyParams) {
            try {
                return bbAdded.courseMergeRecordWithParamsByBatchUid(@params, inputRecord, inputTargetCourseRecord, inputCopyParams);
            } catch (Exception ex) {
                BlackBoardWebServices.LogError("bbAdded.CourseMergeRecordWithParamsByBatchUid", ex);
                if (doRethrow) throw;
                else return null;
            }
        }
    }
}