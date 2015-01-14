<%--
  This page displays users with their respective account status.

  Required parameters:
    status - user account status
    email- user email
    
  Optional parameters:
    None  
--%>

<dsp:page>
<dsp:importbean bean="/tais/droplet/UserStatusDroplet" />

<dsp:getvalueof var="account_Status" param="status"/>  
<dsp:getvalueof var="user_email" param="email"/>   
	<%--UserStatusDroplet fetches users list with specified parameters--%>				
		<dsp:droplet name="UserStatusDroplet">
			<dsp:param name="accountStatus" value="${account_Status}" />
			<dsp:param name="email" value="${user_email}" />
			<%-- Get default page size for the current site --%>
			<dsp:oparam name="output">
			
			 <table class="atg_store_myOrdersTable atg_store_dataTable" border="0" summary="${tableSummary}" cellspacing="0" cellpadding="0">
				<dsp:droplet name="/atg/dynamo/droplet/ForEach">
					<dsp:param name="array" param="result" />
				<dsp:oparam name="outputStart">
				
				
				<%-- Table header --%>
              <thead>
                <tr>
                <th class="site" scope="col">
                 Empoyee Id
                </th>
                <th scope="col">
              Name
                </th>
              
                <th scope="col">
              Email
                </th>
              
                <th scope="col">
               Account Status
                </th>
                 
  				
                <th scope="col">&nbsp;</th>
                 <th scope="col">&nbsp;</th>
                  <th scope="col">&nbsp;</th>
                
                </tr>
              </thead>
				<tbody>
				</dsp:oparam>
					<dsp:oparam name="output">
				<dsp:getvalueof var="userData" param="result"/>
				<tr>
				<td><dsp:valueof param="element.id"/></td>
				<td><dsp:valueof param="element.firstName"/></td>
				<td><dsp:valueof param="element.login"/></td>
				<dsp:getvalueof var="status" param="element.accountStatus"/>
				<c:choose>
				<c:when test="${empty status}">
				<td>--</td>&nbsp
				</c:when>
				<c:otherwise>
				<td><dsp:valueof param="element.accountStatus"/></td>&nbsp
				</c:otherwise>
				</c:choose>
				<td>
				<dsp:a page="updateUserAccountStatus.jsp">
					Update Account Status
					<dsp:param name="userId" param="element.id"/>
					<dsp:param name="userName" param="element.firstName"/>
					<dsp:param name="userEmail" param="element.login"/>
					<dsp:param name="userStatus" value="${status}"/>
				</dsp:a>
				</td>
				</tr>			
					</dsp:oparam>
					<dsp:oparam name="empty">
					<tr><td><center>No records present.</center></td></tr>
					</dsp:oparam>
				
				</dsp:droplet>
				</table>
				
			</dsp:oparam>
		</dsp:droplet>
</dsp:page>
