<%--
  This page displays users with their respective account status.

  Required parameters:
    status - user account status
    email- user email
    
  Optional parameters:
    None  
--%>

<dsp:page>
	<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
	<dsp:importbean bean="/tais/admin/users/UserStatusUpdateFormHandler" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
	<dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />
	<dsp:getvalueof var="expenseResults" param="expenseResults" />
	<dsp:getvalueof var="user_email" param="email" />

	<dsp:droplet name="/atg/dynamo/droplet/ForEach">
		<dsp:param name="array" value="${expenseResults}" />
		<dsp:oparam name="outputStart">


			<table style="width: 50%; position: center">
				<thead style="background-color: #eaeaea">
					<th>Employee Code</th>
					<th>Name</th>
					<th>Total Amount in &#x20B9;.</th>
				</thead>
				</dsp:oparam>
				<dsp:oparam name="output">
					<dsp:getvalueof var="userData" vartype="tais.admin.users.UserList"
						param="result" />
					<tr>
						<td><dsp:valueof param="element.employeeId" /></td>
						<td><dsp:valueof param="element.firstName" /></td>
						<td align="right">&#x20b9;<dsp:valueof
								param="element.totalExpenses" /></td>
					</tr>
				</dsp:oparam>
				<dsp:oparam name="empty">
					<tr>
						<td><center>No records present.</center></td>
					</tr>
				</dsp:oparam>
				</dsp:droplet>
			</table>

			<div style="position: absolute; right: 260px; top: 306px;"
				align="right">
				<dsp:form action="${originatingRequest.requestURI}" method="post"
					name="admin_splitExpenses" formid="admin_splitExpenses">
				Amount to split: <dsp:input
						bean="UserStatusUpdateFormHandler.splitAmount"
						id="expenseFromDate" type="text" required="true"
						placeholder="Split Amount &#x20b9;" tabindex="1"
						style="width:100px" />
					<dsp:input bean="UserStatusUpdateFormHandler.successURL"
						type="hidden" value="foodAllowanceSettlement.jsp" />
					<dsp:input bean="UserStatusUpdateFormHandler.errorURL"
						type="hidden" value="${originatingRequest.requestURI}" />

					<dsp:input bean="UserStatusUpdateFormHandler.expenseFromDate"
						type="hidden" beanvalue="UserStatusUpdateFormHandler.expenseFromDate" />
						
					<dsp:input bean="UserStatusUpdateFormHandler.expenseTillDate"
						type="hidden" beanvalue="UserStatusUpdateFormHandler.expenseTillDate" />
						
					<dsp:input bean="UserStatusUpdateFormHandler.splitExpenses"
						type="submit" value="Split Expenses" />
					</span>
					</td>
				</dsp:form>
			</div>
</dsp:page>
