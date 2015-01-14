<dsp:page>
	<dsp:importbean bean="/atg/userprofiling/ProfileFormHandler" />
	<dsp:importbean bean="/tais/droplet/ProfileSecurityStatus" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
		<dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />
	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">

		<jsp:body>
	<section class="logincontainer">
	
	
		<dsp:droplet name="ErrorMessageForEach">
			<dsp:param param="RegistrationFormHandler.formExceptions"
				name="exceptions" />

			<dsp:oparam name="output">
				<p>
					<dsp:valueof param="message" valueishtml="true" />
				</p>
			</dsp:oparam>
		</dsp:droplet>
<div class="login">
      <h1>Sign In Here</h1>
	<dsp:form action="${originatingRequest.requestURI}" method="post"
						id="login_form">
		
			<label>Email<span style="color: red">*</span></label>
			<dsp:input bean="ProfileFormHandler.value.login" id="username"
							type="text" required="true" maxlength="255"
							placeholder="username/email" tabindex="1" />
			<br /> <label>Password<span style="color: red">*</span></label>
			<dsp:input bean="ProfileFormHandler.value.password" type="password"
							required="true" value="" id="password" />
			<br />
		
		<dsp:input bean="ProfileFormHandler.loginSuccessURL" type="hidden"
							value="../index.jsp" />

		 <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            Remember me 
          </label>
          <br/>
		<dsp:input type="submit" bean="ProfileFormHandler.login"
							class="btnLogin" value="Login" tabindex="4" />
			

	</dsp:form>
	    <div class="login-help">
      <p>Forgot your password? <a href="#">Click here to reset it</a>.</p>
    </div>
    </div>
  </section>
</jsp:body>
	</tais:pageContainer>
</dsp:page>