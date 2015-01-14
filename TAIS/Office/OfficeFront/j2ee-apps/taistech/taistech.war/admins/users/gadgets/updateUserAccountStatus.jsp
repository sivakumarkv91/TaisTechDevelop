<%--
  This page displays alternate or possible account states to be changed for a user by admin.

  Required parameters:
    userId
    userName
    userEmail
    userStatus
    
  Optional parameters:
    None  
--%>

<dsp:page>
  <dsp:importbean bean="/tais/admin/users/UserStatusUpdateFormHandler"/>
  <dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />

<tais:pageContainer titleString="TaisTech" bodyClass="innertube">
<div id="atg_store_contentHeader">
        <h2 class="title">
         Admin
        </h2>
      </div>
<dsp:getvalueof var="id" param="employeeId"/>
<dsp:getvalueof id="name" param="userName"/>
<dsp:getvalueof id="email" param="userEmail"/>
<dsp:getvalueof id="status" param="userStatus"/>

<c:choose>
				<c:when test="${empty status}">
				<dsp:setvalue bean="UserStatusUpdateFormHandler.accountStatus"	value="default"/>
				</c:when>
				<c:otherwise>
				<dsp:setvalue bean="UserStatusUpdateFormHandler.accountStatus"	value="${status}"/>
				</c:otherwise>
				</c:choose>
			
					<dsp:droplet name="ErrorMessageForEach">
			<dsp:param param="UserStatusUpdateFormHandler.formExceptions"
				name="exceptions" />

			<dsp:oparam name="output">
				<p>
					<dsp:valueof param="message" valueishtml="true" />
				</p>
			</dsp:oparam>
		</dsp:droplet>

<dsp:form name="updateUserAccountStatus" action="updateUserAccountStatus.jsp">
			<table align="center">
			<tr>
				<td> Employee Id</td><td>${id}</td></tr>
				<tr><td>&nbsp</td></tr>
				<tr><td> Name</td><td>${name}</td></tr>
				<tr><td>&nbsp</td></tr>
				<tr><td>E-mail</td><td>${email}</td></tr>
				<tr><td>&nbsp</td></tr>
				<tr><td> Account Status</td>
				<c:choose>
				<c:when test="${empty status}">
				<td>--</td>&nbsp
				</c:when>
				<c:otherwise>
				<td>${status}</td>
				</c:otherwise>
				</c:choose>
				</tr>
				<tr><td>&nbsp</td></tr>
				<tr>
				<td>Update Account Status</td>
				<td> <dsp:input type="checkbox" bean="UserStatusUpdateFormHandler.eligibleForFoodExpense"
                 checked="false" value="true" id="foodExp"> Is Eligible For Food Expenses</dsp:input></td> 
       
       </tr>
       <tr><td>&nbsp</td></tr>
       <tr>
				<td>Update Account Status</td>
				<td>
				<dsp:getvalueof var="possibleChangeableUserStates" bean="UserStatusUpdateFormHandler.possibleChangeableUserStates"/>
				
   					<dsp:select bean="UserStatusUpdateFormHandler.changedState"
						id="userAccountStatus">
						<dsp:droplet name="/atg/dynamo/droplet/ForEach">
							<dsp:param name="array" value="${possibleChangeableUserStates}" />
						<dsp:oparam name="output">
							<dsp:getvalueof var="state" param="element"/>
							<dsp:option value="${state}">${state}</dsp:option>
						</dsp:oparam>
					</dsp:droplet>
					</dsp:select>
				</td>
				</tr>
				</tr>
				<tr><td>&nbsp</td></tr>
				<tr><td>
				 <dsp:input bean="UserStatusUpdateFormHandler.successURL" type="hidden" value="updateUserAccountStatusSuccess.jsp?userName=${name}"/>
				 		 <dsp:input bean="UserStatusUpdateFormHandler.userEmail" type="hidden" value="${email}"/>
							 
            <dsp:input bean="UserStatusUpdateFormHandler.errorURL" type="hidden" value="updateUserAccountStatus.jsp?userId=${id}&userName=${name}&userEmail=${email}&userStatus=${status}"/> 
			
				<span class="atg_store_basicButton tertiary">	
				
             <dsp:input bean="UserStatusUpdateFormHandler.updateUser" type="submit" value="Submit"/>   
		</span>	
		
		<%-- Close Button --%>
<fmt:message var="closeButtonText" key="common.closeWindowText"/>
<span class="atg_store_basicButton tertiary">
<input type="submit" onclick="window.close();" value="close"/>
</span> 

		</dsp:form>
				</td></tr>
				</table>
</tais:pageContainer>
</dsp:page>
