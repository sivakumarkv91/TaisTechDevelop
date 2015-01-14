	$(document).ready(function(){
			$(".date").attr("readonly", "readonly").datepicker();
			
			
		});
	
	function refreshCaptchaImage() {
		document.getElementById('captchaImageID').src = "../simpleCaptcha.png?timestamp="+ new Date().getTime();
	}
	
	function showFileAttach(){
		var selValue = $("#expenseType").value;
		if(selValue == 'Special Allowances'){
			$("#fileAttach").show();
		} else {
			$("#fileAttach").hide();
		}
	}