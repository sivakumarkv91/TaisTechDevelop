<%@ include file="/includes/taglibs.jspf"%>
<%@ page isELIgnored="false" pageEncoding="UTF-8"%>

<dsp:page>


	<dsp:importbean bean="/tais/userprofiling/RegistrationFormHandler" />
	<dsp:importbean bean="/tais/droplet/ProfileSecurityStatus" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
	<dsp:importbean bean="/atg/dynamo/droplet/PossibleValues" />
	<dsp:importbean bean="/atg/userprofiling/Profile" />
	<dsp:importbean bean="/atg/userprofiling/ProfileAdapterRepository" />
	<dsp:importbean bean="/atg/userprofiling/PropertyManager" />
	<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
	<dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />

	<dsp:getvalueof var="contextPath" vartype="java.lang.String"
		bean="/OriginatingRequest.contextPath" />
	<dsp:getvalueof var="serverName" vartype="java.lang.String"
		bean="/atg/dynamo/Configuration.siteHttpServerName" />
	<dsp:getvalueof var="serverPort" vartype="java.lang.String"
		bean="/atg/dynamo/Configuration.siteHttpServerPort" />
	<dsp:getvalueof var="httpServer" vartype="java.lang.String"
		value="http://${serverName}:${serverPort}" />

	<title>Sign Up</title>


	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">

		<jsp:body>
			
			<dsp:droplet name="ErrorMessageForEach">
			<dsp:param param="RegistrationFormHandler.formExceptions"
					name="exceptions" />

			<dsp:oparam name="output">
				<p>
					<dsp:valueof param="message" valueishtml="true" />
				</p>
			</dsp:oparam>
		</dsp:droplet>
		<dsp:form method="post" enctype="multipart/form-data"
				action="${originatingRequest.requestURI}" id="register">

			<section class="logincontainer">
			<div class="login">
      <h1>Sign Up Here</h1>
				<label>First Name<span style="color: red">*</span></label>

					<dsp:input type="text"
							bean="RegistrationFormHandler.value.firstName" required="true"
							name="firstName" size="24" tabindex="1" />
									
					<label>Last Name<span style="color: red">*</span></label>

					<dsp:input type="text"
							bean="RegistrationFormHandler.value.lastName" required="true"
							name="lastName" size="24" tabindex="2" />
				
					<label>Employee Code<span style="color: red">*</span></label>
					<dsp:input type="text"
							bean="RegistrationFormHandler.value.employeeCode" required="true"
							name="employeeCode" size="24" tabindex="3" />

					<label>Personal Phone no.<span style="color: red">*</span></label>
					<dsp:input type="text"
							bean="RegistrationFormHandler.value.personalPhone"
							required="true" name="personalPhone" size="24" tabindex="4" />
					<label>Home Phone no.<span style="color: red">*</span></label>
					<dsp:input type="text"
							bean="RegistrationFormHandler.value.homePhone" required="true"
							name="homephone" size="24" tabindex="5" />
				<table>
				<tr>
				<td><label>Gender<span style="color: red">*</span></label></td>
					<td><dsp:select bean="RegistrationFormHandler.value.gender"
										name="gender" required="true" tabindex="5">
							<dsp:droplet name="PossibleValues">
								<dsp:param name="itemDescriptorName" value="user" />
								<dsp:param name="repository" bean="ProfileAdapterRepository" />
								<dsp:param name="propertyName" value="gender" />
								<dsp:oparam name="output">
									<dsp:droplet name="ForEach">
										<dsp:param name="array" param="values" />
										<dsp:oparam name="output">
											<dsp:getvalueof var="gender" param="element" />
											<dsp:option value="${gender}">${gender}</dsp:option>
										</dsp:oparam>
									</dsp:droplet>
								</dsp:oparam>
							</dsp:droplet>
						</dsp:select>
					</td>
					</tr>
					<tr>
					<td><label>Marital Status<span style="color: red">*</span></label></td>
					<td><dsp:select
										bean="RegistrationFormHandler.value.maritalStatus"
										name="maritalStatus" required="true" tabindex="6">
							<dsp:droplet name="PossibleValues">
								<dsp:param name="itemDescriptorName" value="user" />
								<dsp:param name="repository" bean="ProfileAdapterRepository" />
								<dsp:param name="propertyName" value="maritalStatus" />
								<dsp:oparam name="output">
									<dsp:droplet name="ForEach">
										<dsp:param name="array" param="values" />
										<dsp:oparam name="output">
											<dsp:getvalueof var="status" param="element" />
											<dsp:option value="${status}">${status}</dsp:option>
										</dsp:oparam>
									</dsp:droplet>
								</dsp:oparam>
							</dsp:droplet>
						</dsp:select>
			</td>
			</tr>
			<tr>
								<td>
					<label>Job Location<span style="color: red">*</span></label>
								</td>
					<td><dsp:select
										bean="RegistrationFormHandler.value.jobLocation"
										name="jobLocation" required="true" tabindex="7">
							<dsp:droplet name="PossibleValues">
								<dsp:param name="itemDescriptorName" value="user" />
								<dsp:param name="repository" bean="ProfileAdapterRepository" />
								<dsp:param name="propertyName" value="jobLocation" />
								<dsp:oparam name="output">
									<dsp:droplet name="ForEach">
										<dsp:param name="array" param="values" />
										<dsp:oparam name="output">
											<dsp:getvalueof var="location" param="element" />
											<dsp:option value="${location}">${location}</dsp:option>
										</dsp:oparam>
									</dsp:droplet>
								</dsp:oparam>
							</dsp:droplet>
						</dsp:select>
					</td>
					
							<tr>
								<td>
					<label>Designation<span style="color: red">*</span></label>
								</td>
					<td><dsp:select
										bean="RegistrationFormHandler.value.designation"
										name="designation" required="true" tabindex="8">
							<dsp:droplet name="PossibleValues">
								<dsp:param name="itemDescriptorName" value="user" />
								<dsp:param name="repository" bean="ProfileAdapterRepository" />
								<dsp:param name="propertyName" value="designation" />
								<dsp:oparam name="output">
									<dsp:droplet name="ForEach">
										<dsp:param name="array" param="values" />
										<dsp:oparam name="output">
											<dsp:getvalueof var="desig" param="element" />
											<dsp:option value="${desig}">${desig}</dsp:option>
										</dsp:oparam>
									</dsp:droplet>
								</dsp:oparam>
							</dsp:droplet>
						</dsp:select>
						</td>
						</tr>
				</table>
					<label>Photo<span style="color: red">*</span></label>
					<dsp:input type="file"
							bean="RegistrationFormHandler.uploadProperty" name="profilePhoto"
							size="24" tabindex="9" />
					
			<br />
					<label>Email<span style="color: red">*</span></label>
					<dsp:input type="text" bean="RegistrationFormHandler.value.email"
							name="email_id" size="24" tabindex="10" />
					<label>Confirm Email<span style="color: red">*</span></label>
					<dsp:input type="text" bean="RegistrationFormHandler.confirmEmail"
							name="confirmEmail" size="24" tabindex="11" />
				<label>Password<span style="color: red">*</span></label>
					<dsp:input type="password"
							bean="RegistrationFormHandler.value.password" name="password"
							size="24" tabindex="12" />
					<label>Confirm Password<span style="color: red">*</span></label>
					<dsp:input type="password"
							bean="RegistrationFormHandler.value.confirmPassword"
							name="confirmPassword" size="24" tabindex="13" />
				<label>Date of Birth<span style="color: red">*</span></label>
					<dsp:input type="text"
							bean="RegistrationFormHandler.value.dateOfBirth" required="true"
							size="24" name="dob" class="date" tabindex="14" />
					<label>Enter Captcha<span style="color: red">*</span></label>
							<dsp:input bean="RegistrationFormHandler.captchaString"
							type="text" required="true" value="" name="captchaString"
							id="captchaString" tabindex="15" />
				<table id="captcha" align="center">
				<tr>
								<td><img id="captchaImageID"
									src="<c:url value='../simpleCaptcha.png'/>"></td>
				<td><img src="/taisdocroot/images/captcha/icon.jpg" width="19"
									height="19" alt="Refresh Image" border="1" align="right"
									onclick="javascript:refreshCaptchaImage();" /></td>
								</tr>
					</table>		


				<dsp:input bean="RegistrationFormHandler.createSuccessURL"
							type="hidden" value="../index.jsp" />
				<dsp:input bean="RegistrationFormHandler.createErrorURL"
							type="hidden" value="${originatingRequest.requestURI}" />

				<dsp:input bean="RegistrationFormHandler.confirmPassword"
							type="hidden" value="true" />

				<dsp:input bean="RegistrationFormHandler.value.autoLogin"
							type="hidden" value="true" />
						<p align="center">
							<dsp:input type="submit" bean="RegistrationFormHandler.create"
								value="Register" name="Register" tabindex="16" />
						
		
			</dsp:form>
</jsp:body>
	</tais:pageContainer>
</dsp:page>