$(document).ready(function() {

	$("#registerForm").validate();
	
	$('a.btn-primary').bind('click', function() {
		alert("FDF");
        $('form').submit();
    	}).attr("href", "javascript:void(0)");
	
});
