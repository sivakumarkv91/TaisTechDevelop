package tais.admin.users;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;

import atg.core.util.StringUtils;
import atg.droplet.DropletException;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.scenario.userprofiling.ScenarioProfileFormHandler;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.userprofiling.Profile;
import atg.userprofiling.ProfilePropertyUpdate;
import atg.userprofiling.ProfileTools;
import atg.userprofiling.ProfileUpdateEvent;
import atg.userprofiling.ProfileUpdateTrigger;

/**
 * 
 * @author siva.ku This is a FormHandler class which is to update the status of
 *         user account by administrator.
 * 
 */
public class UserStatusUpdateFormHandler extends ScenarioProfileFormHandler {

	private Repository profileRepository;

	private MutableRepositoryItem profileRepositoryItem;
	private ProfileUpdateEvent profileUpdateEvent;
	private ProfileUpdateTrigger profileUpdateTrigger;
	boolean generateProfileUpdateEvents;
	private ProfileTools profileTools;
	private Profile profile;

	private UserAccountStates userAccountStates;

	private String successURL;
	private String errorURL;

	private boolean eligibleForFoodExpense;

	private String userEmail;
	private String changedState;
	private String accountStatus;

	private String expenseFromDate;
	private String expenseTillDate;

	private double splitAmount;

	protected static final String LOCK_ACCOUNT = "LOCK";
	protected static final String UNLOCK_ACCOUNT = "UNLOCK";
	protected static final String ACTIVATE_ACCOUNT = "ACTIVATE";
	protected static final String APPROVE_ACCOUNT = "APPROVE";
	protected static final String DEACTIVATE_ACCOUNT = "DEACTIVATE";

	private Map<String, String> allUserAccountStates;
	private String[] possibleChangeableUserStates;

	/**
	 * 
	 * @param pRequest
	 * @param pResponse
	 * @return boolean
	 * @throws ServletException
	 * @throws IOException
	 *             This handler method will throw form exceptions if any and
	 *             guides user to success and failure pages respectively.
	 * @throws RepositoryException
	 * @throws CommerceException
	 */
	@SuppressWarnings("unchecked")
	public boolean handleUpdateUser(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException, RepositoryException {

		profileRepositoryItem = (MutableRepositoryItem) getProfileTools()
				.getItemFromEmail(userEmail);
		try {
			if (StringUtils.isEmpty(changedState)) {
				addFormException(new DropletException(
						"Please select user account status to be changed."));
			} else {
				@SuppressWarnings("rawtypes")
				Dictionary valueDict = getValue();
				if (APPROVE_ACCOUNT.equals(changedState)
						|| ACTIVATE_ACCOUNT.equals(changedState)
						|| UNLOCK_ACCOUNT.equals(changedState)) {

					valueDict.put("accountStatus", "active");
					preUpdateUser(pRequest, pResponse, valueDict);

					getProfileTools().updateProperty("accountStatus", "active",
							profileRepositoryItem);

					if (APPROVE_ACCOUNT.equals(changedState)) {
						getProfileTools().updateProperty(
								"isEligibleForFoodExpense",
								isEligibleForFoodExpense(),
								profileRepositoryItem);
						vlogInfo("Account approved for user::" + userEmail);
					}

					vlogDebug("Account Status Updated::", changedState);
					vlogInfo("Account Status Updated:: " + userEmail,
							changedState);
				} else if (DEACTIVATE_ACCOUNT.equals(changedState)) {

					valueDict.put("accountStatus", "deactivated");
					preUpdateUser(pRequest, pResponse, valueDict);

					getProfileTools().updateProperty("accountStatus",
							"deactivated", profileRepositoryItem);
					vlogDebug("Account Status Updated::", changedState);
					vlogInfo("Account Status Updated:: " + userEmail,
							changedState);
				} else if (LOCK_ACCOUNT.equals(changedState)) {

					valueDict.put("accountStatus", "locked");
					preUpdateUser(pRequest, pResponse, valueDict);

					getProfileTools().updateProperty("accountStatus", "locked",
							profileRepositoryItem);
					vlogDebug("Account Status Updated::", changedState);
					vlogInfo("Account Status Updated:: " + userEmail,
							changedState);
				}

			}
		} catch (Exception e) {

			vlogError("Error in User Status Update::", e.toString());

		}
		postUpdateUser(pRequest, pResponse);
		return checkFormRedirect(successURL, errorURL, pRequest, pResponse);
	}

	/*
	 * preUpdateUser method sets the profileUpdate Event with updated property
	 * info from specified dictionary object.
	 */

	@SuppressWarnings("rawtypes")
	protected void preUpdateUser(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse, Dictionary valueDict)
			throws ServletException, IOException {
		if (isLoggingDebug())
			logDebug("preUpdateUser");
		if (isGenerateProfileUpdateEvents()) {
			profileRepositoryItem = (MutableRepositoryItem) getProfileTools()
					.getItemFromEmail(userEmail);
			RepositoryItem item = profileRepositoryItem;
			Vector propertyUpdatesVector = new Vector();

			try {
				getProfileTools().buildUpdateMessage(item, valueDict,
						propertyUpdatesVector, pRequest, pResponse);
			} catch (RepositoryException e) {
				if (isLoggingError())
					vlogError(
							"Error in building updadate message for Account Status::",
							e.toString());
			}
			if (propertyUpdatesVector.size() > 0) {
				ProfilePropertyUpdate propertyUpdates[] = new ProfilePropertyUpdate[propertyUpdatesVector
						.size()];
				propertyUpdatesVector.copyInto(propertyUpdates);
				profileUpdateEvent = new ProfileUpdateEvent();
				profileUpdateEvent.setPropertyUpdates(propertyUpdates);
			}
		}
	}

	/*
	 * @see
	 * atg.userprofiling.ProfileFormHandler#sendProfileUpdateEvent(atg.userprofiling
	 * .ProfileUpdateEvent) calls profile update trigger with profileUpdateEvent
	 */
	public void sendProfileUpdateEvent(ProfileUpdateEvent pEvent) {
		getProfileUpdateTrigger().profileUpdate(pEvent);
	}

	/*
	 * @see atg.userprofiling.ProfileFormHandler#postUpdateUser(atg.servlet.
	 * DynamoHttpServletRequest, atg.servlet.DynamoHttpServletResponse) calls
	 * sendProfileUpdateEvent with specified updateEvent for profile.
	 */
	protected void postUpdateUser(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {
		if (isGenerateProfileUpdateEvents() && profileUpdateEvent != null) {

			profileRepositoryItem = (MutableRepositoryItem) getProfileTools()
					.getItemFromEmail(userEmail);
			profileUpdateEvent.setProfile(profileRepositoryItem);
			sendProfileUpdateEvent(profileUpdateEvent);
		}
	}

	/**
	 * 
	 * @param pRequest
	 * @param pResponse
	 * @return boolean
	 * @throws ServletException
	 * @throws IOException
	 * @throws RepositoryException
	 * 
	 *             This is handle method which takes care of handling form
	 *             errors and guiding to success and failure pages.
	 */
	public boolean handleFindUsers(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {

		return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
				pResponse);
	}

	public boolean handleFindExpenses(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {

		if (StringUtils.isEmpty(getExpenseFromDate())
				|| StringUtils.isEmpty(getExpenseTillDate())) {
			addFormException(new DropletException(
					"Please provide both from & to date."));
			return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
					pResponse);
		}
		DateFormat formatter;
		Date fromDate = null, toDate = null;
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			fromDate = formatter.parse(getExpenseFromDate());
			toDate = formatter.parse(getExpenseTillDate());
		} catch (ParseException e) {
			System.out.println(e.toString());
		}

		if (fromDate.after(toDate)) {
			addFormException(new DropletException(
					"From date is greater than to date.Please correct the date "));
			return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
					pResponse);
		}
		if (!getFormError()) {
			try {
				userExpList = getUserStatusManager()
						.getUsersFoodExpenseItemsByDate(fromDate, toDate);

				if (userExpList == null || userExpList.size() == 0) {
					addFormException(new DropletException(
							"No expense records exists with range of dates provided."));
					return checkFormRedirect(getSuccessURL(), getErrorURL(),
							pRequest, pResponse);
				}
			} catch (RepositoryException e) {
				System.out.println(e.toString());
			}
		}

		return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
				pResponse);
	}

	@SuppressWarnings("unchecked")
	public boolean handleSplitExpenses(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {

		if (getSplitAmount() <= 0.0) {
			addFormException(new DropletException(
					"Please provide split/share amount. -ven values are not allowed."));
			return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
					pResponse);
		}
		DateFormat formatter;
		Date fromDate = null, toDate = null;
		List<UserList> resultExpItems = null;
		List<RepositoryItem> userFoodExpItemList = null;
		RepositoryItem[] users = null;
		int eligibleUsers = 0;
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			fromDate = formatter.parse(getExpenseFromDate());
			toDate = formatter.parse(getExpenseTillDate());
		} catch (ParseException e) {
			System.out.println(e.toString());
		}

		if (!getFormError()) {

			try {
				resultExpItems = getUserStatusManager()
						.getUsersFoodExpenseItemsByDate(fromDate, toDate);
				eligibleUsers = getUserStatusManager()
						.getCountOfFoodExpenseEligibleUsers();
				users = getUserStatusManager().getAllFoodExpenseEligibleUsers();
			} catch (RepositoryException e1) {
				e1.printStackTrace();
			}

			double splitAmount = getSplitAmount();
			double actualUserExpensesTotal = 0.0;
			
			if (resultExpItems != null) {
				for (UserList user : resultExpItems) {
					actualUserExpensesTotal += user.getTotalExpenses();
				}
				
				if(splitAmount < actualUserExpensesTotal){
					addFormException(new DropletException(
							"Split/share amount should be more than total user expenses."));
					return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
							pResponse);
				}
			}
			
			if (resultExpItems != null) {

				for (UserList user : resultExpItems) {
					splitAmount -= user.getTotalExpenses();
					List<RepositoryItem> expItems = user.getExpenseItemList();
					for (RepositoryItem exp : expItems) {
						try {
							MutableRepositoryItem mExpItem = getProfileTools()
									.getMutableItem(exp);

							getProfileTools().updateProperty("status",
									"settled", mExpItem);
						} catch (RepositoryException e) {

						}

					}
				}
			}
			double finalSplitAmount = splitAmount / eligibleUsers;
			
			String empCode = null;
			for (RepositoryItem user : users) {
				try {
					MutableRepositoryItem userItem = getProfileTools()
							.getMutableItem(user);

					empCode = (String) user.getPropertyValue("employeeCode");
					userFoodExpItemList = (List<RepositoryItem>) user
							.getPropertyValue("foodExpense");

					if (userFoodExpItemList != null
							&& userFoodExpItemList.size() != 0) {
						for (UserList use : resultExpItems) {
							if (empCode.equals(use.getEmployeeId())) {
								getProfileTools().updateProperty(
										"foodExpenseAmount",
										finalSplitAmount
												+ use.getTotalExpenses(),
										userItem);
							}
						}
					} else {
						getProfileTools().updateProperty("foodExpenseAmount",
								finalSplitAmount, userItem);
					}
					getProfileTools().updateProperty("foodExpStatus",
							"settled", userItem);
					getProfileTools().updateProperty("foodExpStartDate",
							fromDate, userItem);
					getProfileTools().updateProperty("foodExpEndDate", toDate,
							userItem);
					
				} catch (RepositoryException e) {

				}
			}

			try {
				createSettlementItem(users);
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
			
		}

		return checkFormRedirect(getSuccessURL(), getErrorURL(), pRequest,
				pResponse);
	}

	private void createSettlementItem(RepositoryItem[] users) throws RepositoryException{
		MutableRepository profileRepo = getProfileTools().getProfileRepository();
		MutableRepositoryItem settlementItem = profileRepo
				.createItem("foodAllowanceSettlement");
		settlementItem.setPropertyValue("user_id",	getProfile().getRepositoryId());
		settlementItem.setPropertyValue("foodAllowanceFunds", getSplitAmount() );
		settlementItem.setPropertyValue("lastSettlementDate", new Date());
		settlementItem.setPropertyValue("remarks",getRemarks());
		settlementItem.setPropertyValue("availedUsers", Arrays.asList(users));
		profileRepo.addItem(settlementItem);
		
	}
	private UserStatusManager userStatusManager;

	private List<UserList> userExpList;

	private List<UserList> settleExpList;
	
	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<UserList> getSettleExpList() {
		return settleExpList;
	}

	public void setSettleExpList(List<UserList> settleExpList) {
		this.settleExpList = settleExpList;
	}

	public List<UserList> getUserExpList() {
		return userExpList;
	}

	public void setUserExpList(List<UserList> userExpList) {
		this.userExpList = userExpList;
	}

	public UserStatusManager getUserStatusManager() {
		return userStatusManager;
	}

	public void setUserStatusManager(UserStatusManager userStatusManager) {
		this.userStatusManager = userStatusManager;
	}

	public String getSuccessURL() {
		return successURL;
	}

	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}

	public String getErrorURL() {
		return errorURL;
	}

	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}

	public Repository getProfileRepository() {
		return profileRepository;
	}

	public void setProfileRepository(Repository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public ProfileTools getProfileTools() {
		return profileTools;
	}

	public void setProfileTools(ProfileTools profileTools) {
		this.profileTools = profileTools;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setChangedState(String changedState) {
		this.changedState = changedState;
	}

	public String getChangedState() {
		return changedState;
	}

	public ProfileUpdateEvent getProfileUpdateEvent() {
		return profileUpdateEvent;
	}

	public void setProfileUpdateEvent(ProfileUpdateEvent profileUpdateEvent) {
		this.profileUpdateEvent = profileUpdateEvent;
	}

	public ProfileUpdateTrigger getProfileUpdateTrigger() {
		return profileUpdateTrigger;
	}

	public void setProfileUpdateTrigger(
			ProfileUpdateTrigger profileUpdateTrigger) {
		this.profileUpdateTrigger = profileUpdateTrigger;
	}

	public void setGenerateProfileUpdateEvents(
			boolean pGenerateProfileUpdateEvents) {
		generateProfileUpdateEvents = pGenerateProfileUpdateEvents;
	}

	public boolean isGenerateProfileUpdateEvents() {
		return generateProfileUpdateEvents;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public MutableRepositoryItem getProfileRepositoryItem() {
		return profileRepositoryItem;
	}

	public void setProfileRepositoryItem(
			MutableRepositoryItem profileRepositoryItem) {
		this.profileRepositoryItem = profileRepositoryItem;
	}

	public UserAccountStates getUserAccountStates() {
		return userAccountStates;
	}

	public void setUserAccountStates(UserAccountStates userAccountStates) {
		this.userAccountStates = userAccountStates;
	}

	public Map<String, String> getAllUserAccountStates() {
		if (userAccountStates.getUserAccountStatus() != null) {
			allUserAccountStates = userAccountStates.getUserAccountStatus();
		}
		return allUserAccountStates;
	}

	public void setAllUserAccountStates(Map<String, String> allUserAccountStates) {
		this.allUserAccountStates = allUserAccountStates;
	}

	/**
	 * this is the getter method which returns possible changeable states for
	 * user account.
	 * 
	 * @return possibleChangeableUserStates
	 */
	public String[] getPossibleChangeableUserStates() {
		String getterName = "CHANGE_" + accountStatus;
		String nextStates = userAccountStates.getModifiableUserAccountStatus()
				.get(getterName);
		if (nextStates != null) {
			possibleChangeableUserStates = nextStates.split(";");
		}
		return possibleChangeableUserStates;
	}

	public void setPossibleChangeableUserStates(
			String[] possibleChangeableUserStates) {
		this.possibleChangeableUserStates = possibleChangeableUserStates;
	}

	public boolean isEligibleForFoodExpense() {
		return eligibleForFoodExpense;
	}

	public void setEligibleForFoodExpense(boolean eligibleForFoodExpense) {
		this.eligibleForFoodExpense = eligibleForFoodExpense;
	}

	public String getExpenseFromDate() {
		return expenseFromDate;
	}

	public void setExpenseFromDate(String expenseFromDate) {
		this.expenseFromDate = expenseFromDate;
	}

	public String getExpenseTillDate() {
		return expenseTillDate;
	}

	public void setExpenseTillDate(String expenseTillDate) {
		this.expenseTillDate = expenseTillDate;
	}

	public double getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(double splitAmount) {
		this.splitAmount = splitAmount;
	}

}
