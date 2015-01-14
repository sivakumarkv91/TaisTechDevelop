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
		<dsp:importbean bean="/tais/droplet/TaisFoodExpenseReportDroplet" />
	<dsp:importbean bean="/tais/admin/users/UserStatusUpdateFormHandler" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
	
	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">
		<jsp:body>
		<dsp:include page="/admins/gadgets/adminMenu.jsp" />
<br />
		<%-- Admin page title --%>
		<div id="atg_store_contentHeader">
		<h2 class="title">Food Expense Settlement
		</h2>
		</div>

	<dsp:droplet name="TaisFoodExpenseReportDroplet">
	<dsp:oparam name="output">
	<dsp:getvalueof var="settledResults" param="result"/>
	<dsp:droplet name="/atg/dynamo/droplet/ForEach">
		<dsp:param name="array" value="${settledResults}" />
			<dsp:oparam name="outputStart">
			<table style="width: 75%; position: center">
			<thead style="background-color: #eaeaea">
					<tr align="center"style="background-color: #929292 !important;color: #c41401;">
						<td>Settlement Date</td><td><dsp:valueof param="element.settledDateOn" /></td>
						<td>Funds Granted </td><td align="right">&#x20B9;.<dsp:valueof param="element.foodExpenseFunds" /></td>
					</tr>
				
					<th>Employee code</th>
					<th>Name</th>
					<th>Settlement Period</th>
					
					<th>Amount Settled</th>
				</thead>
				</dsp:oparam>
				<dsp:oparam name="output">
						<tr>
						<td><dsp:valueof param="element.employeeCode" /></td>
						<td><dsp:valueof param="element.firstName" /></td>
						<td><dsp:valueof param="element.settlementPeriod" /></td>
					
						<td align="right">&#x20B9;.<dsp:valueof param="element.settledAmount" /></td>
					</tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
				</dsp:oparam>
				<dsp:oparam name="empty">
					<tr>
						<td><center>No records present.</center></td>
					</tr>
				</dsp:oparam>
				</dsp:droplet>
			</table>
		</dsp:oparam>
	</dsp:droplet>
	</jsp:body>
	</tais:pageContainer>
</dsp:page>
