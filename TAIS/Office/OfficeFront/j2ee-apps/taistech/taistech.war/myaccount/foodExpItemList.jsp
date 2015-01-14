<dsp:page>
	<dsp:importbean bean="/atg/userprofiling/Profile" />
	<dsp:importbean bean="/atg/core/i18n/LocaleTools" />
	<dsp:importbean bean="/tais/droplet/ProfileSecurityStatus" />
	<dsp:importbean bean="/tais/droplet/TaisFoodExpenseItemDroplet" />
	<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
	<dsp:droplet name="ProfileSecurityStatus">
		<dsp:getvalueof var="isTransient" bean="Profile.transient" />
		<%-- Logged in user --%>
		<dsp:oparam name="loggedIn">
			<tais:pageContainer titleString="My Profile" bodyClass="innertube">

				<jsp:body>

	<%-- 
        Display left-side menu with selected 
      --%>
	
	<dsp:include page="gadgets/myAccountMenu.jsp" />
	<br />
					<br />
	<%-- Profile page title --%>
	<div id="title">
		<h2 class="title">My Food Expenses</h2>
	</div>

	<div id="profileInfo">
	<dsp:droplet name="TaisFoodExpenseItemDroplet">
	<dsp:oparam name="output">
	<dsp:getvalueof var="itemList" param="foodExpenseItemList" />
				<dsp:droplet name="ForEach">
				<dsp:param name="array" param="foodExpenseItemList" />
				
					<dsp:oparam name="outputStart">
					<table style="width: 100%; position: center">
					<thead style="background-color: Red;">
					<th>Expenditure Date</th>
					<th>Description</th>
					<th>Amount in &#x20B9;.</th>
					<th>Status</th>
					</thead>
										
					</dsp:oparam>
				<dsp:oparam name="output">
				<tr>
				<td><dsp:valueof param="element.expenseDate" /></td>
				<td><dsp:valueof param="element.descriptioin" /></td>
				<td align="right">&#x20b9;<dsp:valueof param="element.amount" /></span></td>
				<td><dsp:valueof param="element.status" /></td>
				</tr>				
				</dsp:oparam>
				<dsp:oparam name="outputEnd">
				
										</table>
				<br />
				</dsp:oparam>
				</dsp:droplet>
	
	</dsp:oparam>
	</dsp:droplet>
	</div>

</jsp:body>
			</tais:pageContainer>
		</dsp:oparam>

		<%-- Anonymous user or logged in from cookie --%>
		<dsp:oparam name="default">
			<dsp:include page="login.jsp" />
		</dsp:oparam>
	</dsp:droplet>


	<style type="text/css">
#change {
	position: relative;
}

table,th,td {
	border: 1px solid black;
	border-collapse: collapse;
	background-color: #EEE;
}

th,td {
	padding: 10px;
}
</style>
</dsp:page>

