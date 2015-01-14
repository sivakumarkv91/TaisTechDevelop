<%--
  This page displays success message after updating account status successfully.

  Required parameters:
	userName
    
  Optional parameters:
    None  
--%>

<dsp:page>
	<dsp:importbean bean="/atg/admin/users/UserStatusUpdateFormHandler" />
	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">
				<jsp:body>
		<div id="atg_store_contentHeader">
		<h2 class="title">Admin</h2>
		</div>
		<br/><br/><br/>
		<table align="center">
			<tr>
				<td><h3>Success
					
						<dsp:getvalueof param="userName" var="name" />
						
          ${name}</h3>
				</td>
			</tr>
			<tr><td>&nbsp</td></tr>
			<tr><td>&nbsp</td></tr>
			<tr>
				<td><center><%-- Close Button --%> <fmt:message var="closeButtonText"
					key="common.closeWindowText" /> <span
					class="atg_store_basicButton tertiary"> <input type="submit"
					onclick="window.close();" value="close" /> </span></center></td>
			</tr>
		</table>
</jsp:body></tais:pageContainer>
</dsp:page>