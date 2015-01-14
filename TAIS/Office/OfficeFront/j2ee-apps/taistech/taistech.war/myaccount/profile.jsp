<dsp:page>
	<dsp:importbean bean="/atg/store/profile/RegistrationFormHandler" />
	<dsp:importbean bean="/atg/userprofiling/Profile" />
	<dsp:importbean bean="/atg/core/i18n/LocaleTools" />
	<dsp:importbean bean="/tais/droplet/ProfileSecurityStatus" />
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
		<h2 class="title">My Profile</h2>
	</div>

	<div id="profileInfo">
	<dl>
		<dd>
			<dsp:getvalueof var="photoUrl" bean="Profile.profilePhotoUrl" />
			<img src="${httpServer}${photoUrl}" alt="Profile Photo" height="50%"
									width="50%" />
		</dd>

			<dt>Email</dt>
			<dd>
				<dsp:valueof bean="Profile.email" />
			</dd>

			<dt>First Name</dt>
			<dd>
				<dsp:valueof bean="Profile.firstName" />
			</dd>

			<dt>Last Name</dt>
			<dd>
				<dsp:valueof bean="Profile.lastName" />
			</dd>

			<dt>Employee code</dt>
			<dd>
				<dsp:valueof bean="Profile.employeeCode" />
			</dd>

			<dt>Gender</dt>
			<dd>
				<dsp:valueof bean="Profile.gender" />
			</dd>

			<dt>Birthday</dt>
			<dd>
				<dsp:getvalueof var="dateofBirth" vartype="java.util.Date"
									bean="Profile.dateofBirth" />
				<c:choose>
					<c:when test="${not empty dateofBirth}">
						<dsp:getvalueof var="dateFormat"
											bean="LocaleTools.userFormattingLocaleHelper.datePatterns.shortWith4DigitYear" />

						<fmt:formatDate value="${dateofBirth}" pattern="${dateFormat}" />
					</c:when>
					<c:otherwise>

					</c:otherwise>
				</c:choose>
			</dd>

			<dt>Marital Status</dt>
			<dd>
				<dsp:valueof bean="Profile.gender" />
			</dd>

			<dt>Personal Phone No.</dt>
			<dd>
				<dsp:valueof bean="Profile.personalPhone" />
			</dd>

			<dt>Home Phone No.</dt>
			<dd>
				<dsp:valueof bean="Profile.homePhone" />
			</dd>

			<dt>Job Location</dt>
			<dd>
				<dsp:valueof bean="Profile.jobLocation" />
			</dd>
			<dt>Designation</dt>
			<dd>
				<dsp:valueof bean="Profile.designation" />
			</dd>

		</dl>
	</div>

	<div id="change">
		<%-- 'Change password' gadget --%>
		
				<a title="$" href="#"> <span>Change
						Password</span>
				</a>

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
</style>
</dsp:page>

