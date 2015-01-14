<dsp:page>
	<dsp:importbean bean="/atg/userprofiling/Profile" />
 <dsp:importbean bean="/tais/droplet/ProfileSecurityStatus"/>
   <dsp:importbean bean="/atg/userprofiling/ProfileFormHandler"/>
	<dsp:getvalueof id="title" param="title" />

	<!DOCTYPE HTML>
	<html>
<head>
<title>${title}</title>
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/taisdocroot/css/main.css">
<link rel="icon" type="image/png" href="/taisdocroot/images/favicon.png"/>


<script src="/tais/js/1.11.2/jquery-1.11.2.js"></script>
<script src="/tais/js/1.11.2/jquery-ui.js"></script>
<script src="/tais/js/1.11.2/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="/taisdocroot/css/jquery-ui/jquery-ui.css">
<script src="/tais/js/tais.js"></script>
</head>

<body>
<body>
<div id="maincontainer">
	

<div id="topsection"><div class="innertube">
<a href="/tais/index.jsp"> <img src="/taisdocroot/images/logo.png"></a>
</div>

<div id="navcontainer" class="topcorner">
<ul>
	<dsp:droplet name="ProfileSecurityStatus">
		<dsp:getvalueof var="isTransient" bean="Profile.transient" />
		<%-- Logged in user --%>
		<dsp:oparam name="loggedIn">

			<c:choose>
				<%-- The user is known --%>
				<c:when test="${not isTransient}">
				<li><span style="color: blue">Welcome</span> &nbsp;<dsp:valueof bean="Profile.firstName"/> </li> &nbsp;|&nbsp;
				<li>	<dsp:a page="../myaccount/profile.jsp">My Account</dsp:a></li> &nbsp;|&nbsp;
				<dsp:a page="/" title="Sign-off">
              <dsp:property bean="ProfileFormHandler.logoutSuccessURL" 
                            value="index.jsp"/>
              <dsp:property bean="ProfileFormHandler.logout" value="true"/>
              <li>Sign-off</li>
            </dsp:a>
				</c:when>
			</c:choose>
		</dsp:oparam>

		<%-- Anonymous user or logged in from cookie --%>
		<dsp:oparam name="default">
			<li>	<dsp:a page="../myaccount/login.jsp">Sign In</dsp:a></li>&nbsp;|&nbsp;
				<li><dsp:a href="../myaccount/register.jsp" class="register-window">Sign - up</dsp:a></li>
				
		</dsp:oparam>
	</dsp:droplet>
</ul>
			</div>
			</div>
			
</dsp:page>