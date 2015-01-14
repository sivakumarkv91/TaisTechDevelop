package tais.admin.users;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExpenseInfoBean {

	private String employeeCode;
	
	private String firstName;
	
	private double settledAmount;
	
	private double expendedAmount;
	
	private Date settledDateOn;
	
	private String settlementPeriod;
	
	private Double foodExpenseFunds;
	
	private Map<Date, ExpenseInfoBean> expenseInfoMap = 
			new HashMap<Date, ExpenseInfoBean>();

	public Map<Date, ExpenseInfoBean> getExpenseInfoMap() {
		return expenseInfoMap;
	}

	public void setExpenseInfoMap(Map<Date, ExpenseInfoBean> expenseInfoMap) {
		this.expenseInfoMap = expenseInfoMap;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public double getSettledAmount() {
		return settledAmount;
	}

	public void setSettledAmount(double settledAmount) {
		this.settledAmount = settledAmount;
	}

	public double getExpendedAmount() {
		return expendedAmount;
	}

	public void setExpendedAmount(double expendedAmount) {
		this.expendedAmount = expendedAmount;
	}

	public Date getSettledDateOn() {
		return settledDateOn;
	}

	public void setSettledDateOn(Date settledDateOn) {
		this.settledDateOn = settledDateOn;
	}

	public String getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public Double getFoodExpenseFunds() {
		return foodExpenseFunds;
	}

	public void setFoodExpenseFunds(Double foodExpenseFunds) {
		this.foodExpenseFunds = foodExpenseFunds;
	}

}
