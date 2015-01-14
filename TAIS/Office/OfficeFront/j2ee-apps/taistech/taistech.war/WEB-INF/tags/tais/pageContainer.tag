<%@ include file="/includes/taglibs.jspf"%>
<%@ include file="/includes/context.jspf"%>

<%@ tag language="java"%>

<%@ attribute name="divId"%>
<%@ attribute name="copyrightDivId"%>
<%@ attribute name="bodyClass"%>
<%@ attribute name="contentClass"%>
<%@ attribute name="titleKey"%>
<%@ attribute name="textKey"%>
<%@ attribute name="titleString"%>
<%@ attribute name="textString"%>
<%@ attribute name="index"%>
<%@ attribute name="formErrorsRenderer" fragment="true"%>




<%-- Setup the page e.g CSS, JS, Page Icon etc --%>
<dsp:include page="/includes/header.jsp">
	<dsp:param name="title" value="${titleString}" />
</dsp:include>


<%-- Main Content --%>
<div id="contentwrapper">
	<div id="contentcolumn" class="${contentClass}">
<jsp:doBody/>
		
	</div>
</div>
<%-- End Content --%>



<dsp:include page="/includes/footer.jsp" />
