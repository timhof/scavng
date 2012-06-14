$(document).ready(function() {

	$("#input-email").focus();
	
	$("#registerForm").validate();
	
	$('a.btn-primary').bind('click', function() {
        $('form').submit();
    	}).attr("href", "javascript:void(0)");
	
	$("#input-passphrase-again").keyup(function(event){
	    if(event.keyCode == 13){
	        $("#register_button").click();
	    }
	});
});
