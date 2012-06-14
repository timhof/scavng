$(document).ready(function() {

    $("#input-email").focus();
    
    $('a.btn-primary').bind('click', function() {
        $('form').submit();
    }).attr("href", "javascript:void(0)");
    
    $("#input-passphrase").keyup(function(event){
	    if(event.keyCode == 13){
	        $("#login_button").click();
	    }
	});
});