<dsp:page>
	<dsp:importbean bean="/tais/droplet/ProfileSecurityStatus" />
	<dsp:importbean bean="/tais/userprofiling/TaisFoodExpenseFormHandler" />
	<dsp:importbean var="originatingRequest" bean="/OriginatingRequest" />
	<dsp:importbean bean="/atg/dynamo/droplet/PossibleValues" />
	<dsp:importbean bean="/atg/userprofiling/ProfileAdapterRepository" />
	<dsp:importbean bean="/atg/userprofiling/PropertyManager" />
	<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
	<dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach" />
	<tais:pageContainer titleString="TaisTech" bodyClass="innertube">

		<jsp:body>
			<%-- 
        Display left-side menu with selected 
      --%>
	
	<dsp:include page="gadgets/myAccountMenu.jsp" />
	<br />
					<br />
			<br />
	<section class="logincontainer">
	
	
		<dsp:droplet name="ErrorMessageForEach">
			<dsp:param bean="TaisFoodExpenseFormHandler.formExceptions"
						name="exceptions" />

			<dsp:oparam name="output">
				<p>
					<dsp:valueof param="message" valueishtml="true" />
				</p>
			</dsp:oparam>
		</dsp:droplet>
<div class="login">
      <h1>Add Food Expenses</h1>
	<dsp:form action="${originatingRequest.requestURI}"enctype="multipart/form-data"  method="post"
						id="addExpense">
		

			<label>Expense Date<span style="color: red">*</span></label>
			<dsp:input bean="TaisFoodExpenseFormHandler.value.expenseDate"
							id="expenseDate" type="text" required="true" maxlength="255"
							placeholder="Choose Date" class="date" tabindex="1" />
			<label>Choose Expense Type <span style="color: red">*</span></label>
			<dsp:select	bean="TaisFoodExpenseFormHandler.value.expenseType" name="expenseType" required="true" tabindex="2" id="expenseType" onchange="showFileAttach();">
						<dsp:droplet name="PossibleValues">
							<dsp:param name="itemDescriptorName" value="foodExpenditure" />
							<dsp:param name="repository" bean="ProfileAdapterRepository" />
							<dsp:param name="propertyName" value="expenseType" />
							<dsp:oparam name="output">
								<dsp:droplet name="ForEach">
									<dsp:param name="array" param="values" />
									<dsp:oparam name="output">
										<dsp:getvalueof var="type" param="element" />
										<dsp:option value="${type}">${type}</dsp:option>
									</dsp:oparam>
								</dsp:droplet>
							</dsp:oparam>
						</dsp:droplet>
			</dsp:select>
			<div id="fileAttach">
			<label>Attach Proofs<span style="color: red">*</span></label>
					<dsp:input type="file"
							bean="TaisFoodExpenseFormHandler.uploadProperty" name="expenseProofs"
							size="24" tabindex="3" />
			</div>
			<br /> <label>Amount<span style="color: red">*</span></label>
		<dsp:input bean="TaisFoodExpenseFormHandler.value.amount" type="text"
							required="true" tabindex="4" value="" placeholder="&#x20B9;."
							id="amount" />
				 <label>Notes (Optional)</label>
		<dsp:input bean="TaisFoodExpenseFormHandler.value.descriptioin"
							type="text" tabindex="5" value="" placeholder="Add notes"
							id="description" />
			<br />
		<dsp:input bean="TaisFoodExpenseFormHandler.addExpSuccessUrl"
							type="hidden" value="foodExpItemList.jsp" />
		<dsp:input bean="TaisFoodExpenseFormHandler.addExpSuccessUrl"
							type="hidden" value="${originatingRequest.requestURI}" />

		<dsp:input type="submit" bean="TaisFoodExpenseFormHandler.addExpense"
							class="btnLogin" value="Add Exp." tabindex="6" />
							
					</dsp:form>
    </div>
  </section>
</jsp:body>
	</tais:pageContainer>
</dsp:page>
