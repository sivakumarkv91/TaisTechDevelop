<%--
 This page displays  all users based on selected  user account status.
 also displays a particular user with selected state. 

  Required parameters:
    None
    
  Optional parameters:
    None  
--%>

<dsp:page>
	 
	<dsp:importbean bean="/tais/admin/users/UserStatusUpdateFormHandler" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
  <dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />
	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">
				<jsp:body>
		<%-- Admin page title --%>
		<div id="atg_store_contentHeader">
		<h2 class="title">User Management
		</h2>
		</div>
		
<dsp:include page="/admins/gadgets/adminMenu.jsp"/>
<br/>
		
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
		<dsp:form action="${originatingRequest.requestURI}" method="post" name="admin_userStatus"
			formid="admin_userStatus_form">
			<table align="center">
				<tr>

					<td>Email<dsp:input
						type="text" bean="UserStatusUpdateFormHandler.userEmail" /></td>
					<td>Choose Account Status
					
					
					<dsp:getvalueof var="allUserAccountStates" bean="UserStatusUpdateFormHandler.allUserAccountStates"/>
   					<dsp:select bean="UserStatusUpdateFormHandler.accountStatus"
						id="userAccountStatus">
						<dsp:option value="">ALL</dsp:option>
						 <c:forEach items="${allUserAccountStates}" var="item">
						<dsp:option value="${item.key}">${item.value}</dsp:option>
						</c:forEach>
					</dsp:select></td>
					<td><dsp:input bean="UserStatusUpdateFormHandler.successURL"
						type="hidden" value="${originatingRequest.requestURI}" /> <dsp:input
						bean="UserStatusUpdateFormHandler.errorURL" type="hidden"
						value="${originatingRequest.requestURI}" /> <span class="atg_store_basicButton tertiary">
					<dsp:input bean="UserStatusUpdateFormHandler.findUsers" type="submit"
						value="Get Users" /> </span></td>

				</tr>
			</table>

			<dsp:getvalueof var="user_email" bean="UserStatusUpdateFormHandler.userEmail" />
			<dsp:getvalueof var="accountStatus" bean="UserStatusUpdateFormHandler.accountStatus" />
			<br/>
			
				<dsp:include page="gadgets/userStatusResult.jsp">
					<dsp:param name="status" value="${accountStatus}" />
					<dsp:param name="email" value="${user_email}" />
				</dsp:include>
			
		</dsp:form></div>
		</jsp:body>
</tais:pageContainer>
</dsp:page>
