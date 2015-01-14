<dsp:page>
	<dsp:importbean bean="/atg/multisite/Site" />
	<dsp:importbean bean="/atg/dynamo/servlet/RequestLocale" />
	<dsp:importbean bean="/atg/userprofiling/Profile" />

	<br/><br/>
	<div id="horizontalMenu">
	
	<dsp:getvalueof var="isAdmin" bean="Profile.isAdmin"/>
	<dsp:getvalueof var="designation" bean="Profile.designation"/>
	
		<ul>			
			<li><dsp:a page="#" title="Manage Users">Manage Users</dsp:a></li>
			<li><dsp:a page="/admins/foodexpense/usersFoodExpenses.jsp" title="Settle Food Exp.">Settle Food Exp.</dsp:a></li>
			<li><dsp:a page="#" title="Track Status">Track Status</dsp:a></li>
			<li><dsp:a page="#" title="Leave Plans">Leave Plans</dsp:a></li>
			<li><dsp:a page="#" title="Reports">Reports</dsp:a></li>
		</ul>
	</div>
	<br/>
</dsp:page>

