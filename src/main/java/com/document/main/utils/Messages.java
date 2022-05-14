package com.document.main.utils;

public class Messages {

	public static final String INVALID_OTP = "OTP is invalid";

	public static final String VALID_OTP = "OTP is valid";

	public static final String SEND_OTP = "OTP sent successfully";

	public static final String SEND_OTP_FAILURE = "Error in sending OTP";

	public static final String USER_NOT_ACTIVE_FAILURE = "User is not active";

	public static final String APP_SUBMIT_SUCCESS = "Request submitted successfully";

	public static final String APP_LOST_SUBMIT_SUCCESS = "Your request has been sent to system Admin.";

	public static final String APP_SUBMIT_FAILURE = "Provide all required details, request not submitted";

	public static final String LOST_APP_SUBMIT_FAILURE = "Mobile no. you entered is not registered for this App.";

	public static final String UPDATE_REQUEST_SUCCESS = "Status updated successfully";

	public static final String UPDATE_REQUEST_FAILURE = "Error in updating status";

	public static final String UPDATE_REQ_APPROVE_FAILURE = "Card range limit reached, please generate new range";

	public static final String UPDATE_REQ_TIMESLOT_FAILURE = "Time slot does not exist or reached apllicant limit, please select new value";

	public static final String UPDATE_ISSUE_DUE_DATE_FAILURE = "Please provide Issue date and Due date to approved";

	public static final String DIVISION_HOSPITAL_FAILURE = "Selected Hospital and Division is not associated with this Mobile number";

	public static final String FILE_FAILURE = "Your approved ID card request is pending for eSign PDF. After eSIign PDF approval, ID Card will be available.";

	/**
	 * @INFO hospital customized messages
	 */

	public static final String HOSPITAL_SUBMIT_SUCCESS = "Hospital added successfully";

	public static final String HOSPITAL_UPDATE_SUCCESS = "Hospital Updated successfully";

	public static final String HOSPITAL_EXIST_FAILURE = "Hospital you provided is already exist,Please select unique value";

	/**
	 * @INFO zone customized messages
	 */
	public static final String ZONE_SUBMIT_SUCCESS = "Zone added successfully";

	public static final String ZONE_UPDATE_SUCCESS = "Zone Updated successfully";

	public static final String ZONE_EXIST_FAILURE = "Zone you provided is already exist,Please select unique value";

	public static final String ZONE_FAILURE = "Zone not exist,please create division first";

	/**
	 * @INFO division customized messages
	 */
	public static final String DIVISION_SUBMIT_SUCCESS = "Division added successfully";

	public static final String DIVISION_UPDATE_SUCCESS = "Division Updated successfully";

	public static final String DIVISION_EXIST_FAILURE = "Division you provided is already exist,Please select unique value";

	public static final String DIVISION_FAILURE = "Divison not exist,please create division first";

	/**
	 * @INFO card-range customized messages
	 */
	public static final String CARDRANGE_SUBMIT_SUCCESS = "Card Range added successfully";

	public static final String CARDRANGE_UPDATE_SUCCESS = "Card Range Updated successfully";

	/**
	 * @INFO time-slot customized messages
	 */
	public static final String TIMESLOT_SUBMIT_SUCCESS = "Time Slot added successfully";

	public static final String TIMESLOT_UPDATE_SUCCESS = "Time Slot Updated successfully";

	public static final String TIMESLOT_EXIST_FAILURE = "Time Slot you provided is already exist,Please select unique value";

	/**
	 * @INFO comment customized messages
	 */
	public static final String COMMENT_SUBMIT_SUCCESS = "Comment added successfully";

	public static final String COMMENT_VALIDATION_FAILURE = "Time Slot you provided is already exist,Please select unique value";

	/**
	 * @INFO guideline document customized messages
	 */
	public static final String DOCUMENT_SUBMIT_SUCCESS = "Document added successfully";

	public static final String DOCUMENT_UPDATE_SUCCESS = "Document Updated successfully";

	public static final String DOCUMENT_EXIST_FAILURE = "Document you provided is already exist,Please provide unique value";

	public static final String DOCUMENT_DELETE_SUCCESS = "Document Deleted successfully";

	public static final String DOCUMENT_TYPE_FAILURE = "Please upload only JPG,PNG,DOC, and PDF file";

	public static final String GUIDELINE_DOCUMENT_FILE_FAILURE = "Document is not available.";

	public static final String GUIDELINE_DOCUMENT_FILE_SUCCESS = "Document downloaded succssfully.";

	public static final String GUIDELINE_DOCUMENT_SUBMIT_FAILURE = "Provide all required details, request not submitted";

	public static final String GUIDELINE_DOCUMENT_LIST_FAIL = "There are no documents available.";

	/**
	 * @INFO user customized messages
	 */
	public static final String USER_SUBMIT_SUCCESS = "User added successfully";

	public static final String USER_PASSWORD_SUBMIT_SUCCESS = " Password changed successfully. Please login using the new login credentials";

	public static final String USER_MOBILE_NO_SUBMIT_SUCCESS = "Mobile Number changed successfully. Please login using the new login credentials";

	public static final String USER_UPDATE_SUCCESS = "User Updated successfully";

	public static final String USER_EXIST_FAILURE = "User you provided is already exist,Please select unique value";

	public static final String ROLE_SUBMIT_FAILURE = "Role not exist, please create role first";

	public static final String USER_NOT_EXIST_FAILURE = "User you provided is not exist,Please provide valid user details";

	public static final String USER_PAASWORD_NOT_MATCH_FAILURE = "please provide valid password";

	public static final String USER_PHONE_NO_NOT_MATCH_FAILURE = "please provide valid Phone Number";

	/**
	 * @info contact details
	 */
	public static final String CONTACT_DETAIL_SUBMIT_SUCCESS = "Settings submitted successfully";

	/**
	 * @param
	 */

	public static final String CARD_PRINT_IDENTITY_CARD_NO_KEY = "Identity Card No. : ";
	public static final String CARD_PRINT_NAME_KEY = "Name : ";
	public static final String CARD_PRINT_DATE_OF_BIRTH_KEY = "Date Of Birth : ";
	public static final String CARD_PRINT_GENDER_KEY = "Gender : ";
	public static final String CARD_PRINT_NAME_OF_DIVISION_KEY = "Name of Division : ";
	public static final String CARD_PRINT_NAME_OF_GOVT_HOSPTIAL_KEY = "Name of Govt. Hospital : ";
	public static final String CARD_PRINT_NAME_OF_GOVT_DOCTOR_KEY = "Name of Govt. Doctor : ";
	public static final String CARD_PRINT_DOCTOR_REGISTRATION_NO_KEY = "Registration Number of Govt. Doctor : ";
	public static final String CARD_PRINT_NATURE_OF_HANDICAP_KEY = "Nature Of Handicap : ";
	public static final String CARD_PRINT_VALIDITY_OF_CONCESSION_CERTIFICATE_KEY = "Validity of Concession Certificate : ";
	public static final String CARD_PRINT_ESCORT_KEY = "ESCORT : ";
	public static final String CARD_PRINT_CARD_VALID_UPTO_KEY = "Card Valid upto : ";
	public static final String CARD_PRINT_DATE_OF_ISSUE_KEY = "Date of issue : ";
	public static final String CARD_PRINT_MOBILE_NO_KEY = "Mobile Number : ";
	public static final String CARD_PRINT_AADHAR_NO_KEY = "Aadhar Number : ";
	public static final String CARD_PRINT_REQUEST_DATE_KEY = "Request Date : ";
	public static final String CARD_PRINT_PROCESSED_DATE_KEY = "Processed Date : ";
	public static final String CARD_PRINT_VERIFIED_DATE_KEY = "Verified Date : ";
	public static final String CARD_PRINT_APPROVED_DATE_KEY = "Approved Date : ";
}
