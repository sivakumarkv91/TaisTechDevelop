package tais.admin.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;

import atg.core.util.StringUtils;
import atg.nucleus.GenericService;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.repository.RepositoryItemDescriptor;
import atg.repository.RepositoryView;
import atg.repository.rql.RqlStatement;

/**
 * The Class UserStatusManager.
 * 
 * @author siva.ku This is the manager class which fetches users list based on
 *         email and accountStatus.
 */
public class UserStatusManager extends GenericService {

	/** The profile repository. */
	private Repository profileRepository;

	/**
	 * Gets the profile repository.
	 * 
	 * @return the profile repository
	 */
	public final Repository getProfileRepository() {
		return profileRepository;
	}

	/**
	 * Sets the profile repository.
	 * 
	 * @param repository
	 *            the new profile repository
	 */
	public final void setProfileRepository(final Repository repository) {
		this.profileRepository = repository;
	}

	/**
	 * Display users. This method finds out list of users with specified
	 * parameters
	 * 
	 * @param accountStatus
	 *            the account status
	 * @param email
	 *            the email
	 * @param startIndex
	 *            the start index
	 * @param numOfRecords
	 *            the num of records
	 * @return the list
	 * @throws ServletException
	 *             the servlet exception
	 * @throws RepositoryException
	 *             the repository exception
	 */
	public final List<UserList> displayUsers(final String accountStatus,
			final String email, final String startIndex,
			final String numOfRecords) throws ServletException,
			RepositoryException {
		RepositoryItem[] users = null;
		UserList listItem = null;
		List<UserList> userList = null;
		Object[] params = null;

		vlogDebug(" UserStatusManager : displayUsers :: AccountStatus : "
				+ accountStatus);
		vlogDebug(" UserStatusManager : displayUsers :: Eemail : " + email);
		final RepositoryItemDescriptor desciptor = profileRepository
				.getItemDescriptor("user");

		final RepositoryView repositoryView = desciptor.getRepositoryView();
		RqlStatement statement = null;
		// fetches all user accounts
		if (StringUtils.isEmpty(email) && StringUtils.isEmpty(accountStatus)) {
			statement = RqlStatement.parseRqlStatement("ALL");
			users = statement.executeQuery(repositoryView, params);
			vlogDebug(" Displaying all users");
		} else if (StringUtils.isEmpty(email)) {
			// fetches all users based on selected accountStatus
			params = new Object[1];
			params[0] = new Object[] { accountStatus };
			statement = RqlStatement.parseRqlStatement("accountStatus = ?0");
			users = statement.executeQuery(repositoryView, params);
		} else if (!StringUtils.isEmpty(email)
				&& !StringUtils.isEmpty(accountStatus)) {
			// fetches user based on his/her email and accountStatus
			params = new Object[2];
			params[0] = new Object[] { accountStatus };
			params[1] = new Object[] { email };
			statement = RqlStatement
					.parseRqlStatement("accountStatus = ?0 AND email= ?1");
			users = statement.executeQuery(repositoryView, params);
			vlogDebug(" Fetch user based on status and user email");
		} else if (!StringUtils.isEmpty(email)
				&& StringUtils.isEmpty(accountStatus)) {
			// fetches user based on his/her email
			params = new Object[1];
			params[0] = new Object[] { email };
			statement = RqlStatement.parseRqlStatement("email= ?0");
			users = statement.executeQuery(repositoryView, params);
			vlogDebug(" Displaying users based on user email");
		}
		// For finding total number of records for pagination.
		RepositoryItem[] items = null;
		int totalCount = 0;
		if (users != null && users.length > 0) {
			totalCount = users.length;
			int startIndexInt;
			if (startIndex == null || startIndex.equals("")) {
				startIndexInt = 0;
			} else {
				startIndexInt = Integer.parseInt(startIndex);
			}
			int numUsersInt = 0;
			if (numOfRecords == null || numOfRecords.equals("")) {
				numUsersInt = totalCount;
			} else {
				numUsersInt = Integer.parseInt(numOfRecords);
			}
			int toIndex = startIndexInt + numUsersInt;
			if (toIndex > totalCount) {
				toIndex = totalCount;
			}
			items = Arrays.copyOfRange(users, startIndexInt, toIndex);
		} else {
			items = users;
		}
		int i = 0;
		if (items != null && items.length > 0) {
			userList = new ArrayList<UserList>();

			for (RepositoryItem item : items) {
				i = 0;
				listItem = new UserList();
				listItem.setId((String) (item.getPropertyValue("id")));
				listItem.setFirstName((String) item
						.getPropertyValue("firstName"));
				listItem.setLogin((String) item.getPropertyValue("login"));
				listItem.setAccountStatus((String) item
						.getPropertyValue("accountStatus"));
				userList.add(listItem);
				listItem.setTotalCount(totalCount);
				i++;
			}

		}

		return userList;
	}

	protected int getCountOfFoodExpenseEligibleUsers()
			throws RepositoryException {
		int count = 0;
		RepositoryItem[] users = null;
		users = getAllFoodExpenseEligibleUsers();
		if (users != null) {
			count = users.length;
		}
		return count;
	}

	protected RepositoryItem[] getAllFoodExpenseEligibleUsers()
			throws RepositoryException {
		RepositoryItem[] users = null;
		Object[] params = null;

		final RepositoryItemDescriptor desciptor = profileRepository
				.getItemDescriptor("user");

		final RepositoryView repositoryView = desciptor.getRepositoryView();
		RqlStatement statement = null;
		params = new Object[1];
		params[0] = new Object[] { Boolean.TRUE };
		statement = RqlStatement
				.parseRqlStatement("isEligibleForFoodExpense = ?0");
		users = statement.executeQuery(repositoryView, params);

		return users;
	}

	@SuppressWarnings("unchecked")
	protected List<UserList> getUsersFoodExpenseItemsByDate(Date expFrom,
			Date expTo) throws RepositoryException {

		RepositoryItem[] users = null;
		UserList userExpBean = null;
		List<UserList> finalUserList = null;

		users = getAllFoodExpenseEligibleUsers();
		List<RepositoryItem> foodExpItemList = null;
		List<RepositoryItem> finalFoodExpItemList = null;

		if (users != null) {
			finalUserList = new ArrayList<UserList>();
			for (RepositoryItem user : users) {

				userExpBean = new UserList();
				foodExpItemList = (List<RepositoryItem>) user
						.getPropertyValue("foodExpense");

				userExpBean.setEmployeeId((String) user
						.getPropertyValue("employeeCode"));

				userExpBean.setFirstName((String) user
						.getPropertyValue("firstName"));
				
				if (foodExpItemList != null) {
					finalFoodExpItemList = getExpenseItemListForUser(
							foodExpItemList, expFrom, expTo);

					if (finalFoodExpItemList != null
							&& finalFoodExpItemList.size() != 0) {

						userExpBean
								.setTotalExpenses(calculateTotalExpenseAmount(finalFoodExpItemList));
						userExpBean.setExpenseItemList(finalFoodExpItemList);
					}
				}
				
				finalUserList.add(userExpBean);
			}
		}
		return finalUserList;

	}

	private List<RepositoryItem> getExpenseItemListForUser(
			List<RepositoryItem> foodExpItemList, Date expFrom, Date expTo) {
		List<RepositoryItem> finalFoodExpItemList = new ArrayList<RepositoryItem>();

		for (RepositoryItem expItem : foodExpItemList) {

			Date expDate = (Date) expItem.getPropertyValue("expenseDate");
			String status = (String) expItem.getPropertyValue("status");

			if ((expDate.after(expFrom) || expDate.equals(expFrom))
					&& (expDate.before(expTo) || expDate.equals(expTo))
					&& "unsettled".equals(status)) {
				finalFoodExpItemList.add(expItem);
			}
		}

		return finalFoodExpItemList;
	}

	private double calculateTotalExpenseAmount(List<RepositoryItem> expItemList) {
		double totalAmount = 0.0;
		for (RepositoryItem item : expItemList) {
			totalAmount += (Double) item.getPropertyValue("amount");
		}
		return totalAmount;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, List<ExpenseInfoBean>> getSettledExpenseDetails()
			throws RepositoryException {
		RepositoryItem[] settledItems = null;
		Object[] params = null;
		ExpenseInfoBean expInfoBean = null;
		List<ExpenseInfoBean> infoList = null;
		Map<Integer, List<ExpenseInfoBean>> resultedItemMap = new TreeMap<Integer, List<ExpenseInfoBean>>();

		final RepositoryItemDescriptor desciptor = profileRepository
				.getItemDescriptor("foodAllowanceSettlement");

		final RepositoryView repositoryView = desciptor.getRepositoryView();
		RqlStatement statement = null;
		params = new Object[1];
		params[0] = new Object[] { Boolean.TRUE };
		statement = RqlStatement.parseRqlStatement("ALL");
		settledItems = statement.executeQuery(repositoryView, params);

		if (settledItems != null) {
			int i=0;
			for (RepositoryItem settledItem : settledItems) {
				
				infoList =  new ArrayList<ExpenseInfoBean>();
				Date settlementDate = (Date)settledItem.getPropertyValue("lastSettlementDate");
				
				List<RepositoryItem> availedUsers = (List<RepositoryItem>)settledItem.getPropertyValue("availedUsers");
				for(RepositoryItem user:availedUsers){
					expInfoBean = new ExpenseInfoBean();
					
					Date startDate =(Date) user.getPropertyValue("foodExpStartDate");
					Date endDate =(Date) user.getPropertyValue("foodExpEndDate");
					Double totalFunds = (Double)settledItem.getPropertyValue("foodAllowanceFunds");
					Double actualShare = totalFunds / availedUsers.size();
					Double settledAmount = (Double)user.getPropertyValue("foodExpenseAmount");
					
					expInfoBean.setEmployeeCode((String)user.getPropertyValue("employeeCode"));
					expInfoBean.setFirstName((String)user.getPropertyValue("firstName"));
					expInfoBean.setSettledAmount((Double)user.getPropertyValue("foodExpenseAmount"));
					expInfoBean.setSettlementPeriod("From "+startDate.toString() +"To "+endDate.toString());
					expInfoBean.setFoodExpenseFunds((Double)settledItem.getPropertyValue("foodAllowanceFunds"));
					expInfoBean.setSettledDateOn(settlementDate);
					infoList.add(expInfoBean);
				}
				resultedItemMap.put(i, infoList);
				i++;
			}

		}

		return resultedItemMap;
	}
}
