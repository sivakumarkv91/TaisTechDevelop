package tais.admin.users;

import java.util.Date;
import java.util.List;

import atg.repository.RepositoryItem;

/**
 * The Class UserList.
 */
public class UserList {

    /** The id. */
    private String id;

    /** The first name. */
    private String firstName;

    /** The login. */
    private String login;

    /** The registration date. */
    private Date registrationDate;

    /** The account status. */
    private String accountStatus;

    /** The total count. */
    private int totalCount;
    
    private double totalExpenses;
    
    private String employeeId;
    
    private List<RepositoryItem> expenseItemList; 
    
    public List<RepositoryItem> getExpenseItemList() {
		return expenseItemList;
	}

	public void setExpenseItemList(List<RepositoryItem> expenseItemList) {
		this.expenseItemList = expenseItemList;
	}

	public double getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
     * Gets the id.
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param userId
     *        the new id
     */
    public final void setId(final String userId) {
        this.id = userId;
    }

    /**
     * Gets the first name.
     * @return the first name
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * @param name
     *        the new first name
     */
    public final void setFirstName(final String name) {
        this.firstName = name;
    }

    /**
     * Gets the login.
     * @return the login
     */
    public final String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     * @param loginId
     *        the new login
     */
    public final void setLogin(final String loginId) {
        this.login = loginId;
    }

    /**
     * Gets the registration date.
     * @return the registration date
     */
    public final Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registration date.
     * @param regiDate
     *        the new registration date
     */
    public final void setRegistrationDate(final Date regiDate) {
        this.registrationDate = regiDate;
    }

    /**
     * Gets the account status.
     * @return the account status
     */
    public final String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the account status.
     * @param status
     *        the new account status
     */
    public final void setAccountStatus(final String status) {
        this.accountStatus = status;
    }

    /**
     * Gets the total count.
     * @return the total count
     */
    public final int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the total count.
     * @param count
     *        the new total count
     */
    public final void setTotalCount(final int count) {
        this.totalCount = count;
    }
}
