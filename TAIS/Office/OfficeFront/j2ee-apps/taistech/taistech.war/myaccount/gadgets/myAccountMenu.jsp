<dsp:page>
	<dsp:importbean bean="/atg/multisite/Site" />
	<dsp:importbean bean="/atg/dynamo/servlet/RequestLocale" />
	<dsp:importbean bean="/atg/userprofiling/Profile" />

	<br/><br/>
	<div id="horizontalMenu">
	
	<dsp:getvalueof var="isAdmin" bean="Profile.isAdmin"/>
	<dsp:getvalueof var="designation" bean="Profile.designation"/>
	
		<ul>
		<c:if test="${isAdmin &&  designation eq 'Project Manager'}">
			<li><dsp:a page="/admins/users/manageUsers.jsp" title="Admin">Admin</dsp:a></li>
		</c:if>
			
			<li><dsp:a page="../profile.jsp" title="Profile">My Profile</dsp:a></li>
			<li><dsp:a page="../addFoodExp.jsp" title="Add Food Exp.">Add Food Exp.</dsp:a></li>
			<li><dsp:a page="../foodExpItemList.jsp" title="Food Exp. INFO">Food Exp. Info</dsp:a></li>
			<li><dsp:a page="#" title="Address Book">Address Book</dsp:a></li>
		</ul>
	</div>
	<br/>
</dsp:page>

