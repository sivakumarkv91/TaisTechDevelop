<dsp:page>

	<dsp:importbean bean="/tais/admin/users/UserStatusUpdateFormHandler" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
	<dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />
	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">
		<jsp:body>
		<%-- Admin page title --%>
		<div id="atg_store_contentHeader">
		<h2 class="title">Food Expense Management
		</h2>
		</div>
		
<dsp:include page="/admins/gadgets/adminMenu.jsp" />
<br />
		
		<dsp:droplet name="ErrorMessageForEach">
			<dsp:param param="UserStatusUpdateFormHandler.formExceptions"
					name="exceptions" />

			<dsp:oparam name="output">
				<p>
					<dsp:valueof param="message" valueishtml="true" />
				</p>
			</dsp:oparam>
		</dsp:droplet>
		
		<div class="atg_store_main atg_store_myAccount">
		
		<br />
		<dsp:form action="${originatingRequest.requestURI}" method="post"
					name="admin_userStatus" formid="admin_userStatus_form">
			<table align="center">
				<tr>

					<td><label>Expenses From :</label></td>
					
			<td><dsp:input bean="UserStatusUpdateFormHandler.expenseFromDate"
									id="expenseFromDate" type="text" required="true"
									placeholder="Choose From Date" class="date" tabindex="1" /></td>
									
					<td><label>Expenses Till :</label></td>
			<td><dsp:input bean="UserStatusUpdateFormHandler.expenseTillDate"
									id="expenseToDate" type="text" required="true" maxlength="255"
									placeholder="Choose Till Date" class="date" tabindex="2" /></td>
					<td><dsp:input bean="UserStatusUpdateFormHandler.successURL"
									type="hidden" value="${originatingRequest.requestURI}" /> <dsp:input
									bean="UserStatusUpdateFormHandler.errorURL" type="hidden"
									value="${originatingRequest.requestURI}" /> <span
								class="atg_store_basicButton tertiary">
					<dsp:input bean="UserStatusUpdateFormHandler.findExpenses"
										type="submit" value="Get Expenses" /> </span></td>

				</tr>
			</table>
</dsp:form>
			<dsp:getvalueof var="userExpList"
						bean="UserStatusUpdateFormHandler.userExpList" />
			
			<br />
				<br />
				<c:if test="${not empty userExpList}">
				<dsp:include page="foodExpenseResults.jsp">
					<dsp:param name="expenseResults" value="${userExpList}" />
				</dsp:include>
				</c:if>
		
			</div>
		</jsp:body>
	</tais:pageContainer>
</dsp:page>
