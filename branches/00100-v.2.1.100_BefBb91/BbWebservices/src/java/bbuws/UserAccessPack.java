/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbuws;
/**
 *
 * @author vic
 */
import bbwscommon.*;

import blackboard.data.user.User;


import java.util.*;

//public class UserAccessPack <BbUserType extends User, WsObjectType extends BbWsDataDetails>
public class UserAccessPack <BbUserType extends User, ArgumentsType extends UserAccessPack.UserArguments<bbuws.UserDetails>>
//        extends BbWsDataAccessPack<UserAccessPack.UserArguments<WsInputType>,BbUserType, bbuws.UserDetails, WsInputType> {
        extends BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType
            <ArgumentsType, BbUserType, bbuws.UserDetails> {
        
//            Person, bbuws.UserDetails> {
    static class UserArguments<WsInputType extends BbWsDataDetails> extends BbWsArguments<UserDetails, WsInputType> {
    //static class UserArguments<WsInputType extends bbuws.UserDetails> extends BbWsArguments<UserDetails, UserDetails> {
    }

    public static class UserArguments_ADMIN_DATA extends UserAccessPack.UserArgumentsWithUserInput {
        protected void initializeDefaults() {
            super.initializeDefaults();
            getApiToUseList().clear();
            getApiToUseList().add(new ApiToUse(ApiToUseEnum.ADMIN_DATA, null));
        }
    }

    public static class UserArgumentsWithUserInput extends UserArguments<UserDetails> {
        static public UserArgumentsWithUserInput create(Class<bbuws.UserDetails> wsObjectClass, BbWsParams params, List<bbuws.UserDetails> inputList,
                    String dataAccessPackClassName, String dataAccessorStartMethodName) {
            UserArgumentsWithUserInput args = new UserArgumentsWithUserInput();
            args.initialize(wsObjectClass, params, inputList, dataAccessPackClassName, dataAccessorStartMethodName);
            return args;
        }
        static public UserArgumentsWithUserInput create(Class<bbuws.UserDetails> wsObjectClass, BbWsParams params, bbuws.UserDetails inputRecord,
                    String dataAccessPackClassName, String dataAccessorStartMethodName) {
            UserArgumentsWithUserInput args = new UserArgumentsWithUserInput();
            args.initialize(wsObjectClass, params, inputRecord, dataAccessPackClassName, dataAccessorStartMethodName);
            return args;
        }
    }

    public static class UserArgumentsWithUserAndCourseInput extends UserArgumentsWithUserInput {
        private bbcrsws.CourseDetails inputCourseRecord;
        //private List<bbcrsws.CourseDetails> inputCourseList;

/*        public void initialize(Class<UserDetails> wsResultClass, BbWsParams params, UserDetails recordInput,
                String dataAccessPackClassName, String dataAccessorStartMethodName, bbcrsws.CourseDetails inputCourseRecord) {
            super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, dataAccessorStartMethodName);
            this.setInputCourseRecord(inputCourseRecord);
        }

        public void initialize(Class<UserDetails> wsResultClass, BbWsParams params, List<UserDetails> listInput,
                String dataAccessPackClassName, String dataAccesserStartMethodName, bbcrsws.CourseDetails inputCourseRecord) {
            super.initialize(wsResultClass, params, listInput, dataAccessPackClassName, dataAccesserStartMethodName);
            this.setInputCourseRecord(inputCourseRecord);
        }*/
        /**
         * @return the inputCourseRecord
         */
        public bbcrsws.CourseDetails getInputCourseRecord() {
            return inputCourseRecord;
        }

        /**
         * @param inputCourseRecord the inputCourseRecord to set
         */
        public void setInputCourseRecord(bbcrsws.CourseDetails inputCourseRecord) {
            this.inputCourseRecord = inputCourseRecord;
        }
    }

    public static class UserArgumentsWithUserAndGroupInput extends UserArgumentsWithUserInput {
        /*public void initialize(Class<UserDetails> wsResultClass, BbWsParams params, UserDetails recordInput,
                String dataAccessPackClassName, String dataAccessorStartMethodName, bbgrpws.GroupDetails inputGroupRecord) {
            super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, dataAccessorStartMethodName);
            this.setInputGroupRecord(inputGroupRecord);
        }
        */
        private bbgrpws.GroupDetails inputGroupRecord;

        /**
         * @return the inputGroupRecord
         */
        public bbgrpws.GroupDetails getInputGroupRecord() {
            return inputGroupRecord;
        }

        /**
         * @param inputGroupRecord the inputGroupRecord to set
         */
        public void setInputGroupRecord(bbgrpws.GroupDetails inputGroupRecord) {
            this.inputGroupRecord = inputGroupRecord;
        }
    }

    public static class UserArgumentsWithUserAndRoleInput extends UserArgumentsWithUserInput {
        /*public void initialize(Class<UserDetails> wsResultClass, BbWsParams params, UserDetails recordInput,
                String dataAccessPackClassName, String dataAccessorStartMethodName, bbuws.Role inputGroupRecord) {
            super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, dataAccessorStartMethodName);
            this.setInputRoleRecord(inputRoleRecord);
        }*/

        private bbuws.RoleDetails inputRoleRecord;

        /**
         * @return the inputRoleRecord
         */
        public bbuws.RoleDetails getInputRoleRecord() {
            return inputRoleRecord;
        }

        /**
         * @param inputRoleRecord the inputRoleRecord to set
         */
        public void setInputRoleRecord(bbuws.RoleDetails inputRoleRecord) {
            this.inputRoleRecord = inputRoleRecord;
        }

    }


    @Override protected void setSafeResultRecordIds() {
        super.setSafeResultRecordIds();
        getArgs().getResultRecord().setBatchUid(getArgs().getInputRecord().getBatchUid());
    }

        public abstract class BbCheckedFieldSetter extends BbFieldSetter {

        protected void checkInputValue(String value) {

            //!! Unimplemented
            //looks like unnecessary
/*

	private String checkUserDetail(String userDetail) throws Exception
	{
	    if(userDetail!=null && !userDetail.equalsIgnoreCase("") && !containsSpecialCharacters(userDetail))
	    {
		return userDetail.trim();
	    }
	    throw new Exception("Invalid user detail: '"+userDetail+"'");
	}
	private Boolean containsSpecialCharacters(String toCheck)
	{
	    if(!toCheck.contains(" ") && 
                //!toCheck.contains("@") &&
            !toCheck.contains("%") && !toCheck.contains("&") && !toCheck.contains("#") && !toCheck.contains("<")	&& !toCheck.contains(">") && !toCheck.contains("=") && !toCheck.contains("+"))
	    {
		return false;
	    }
	    return true;
	}
*/

        }
    }

}
