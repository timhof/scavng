$(document).ready(function() {

	$.each(["target", "user"], function(index, value){
		
		var modal_id = "#"+value+"_modal";
		var form_id = "#"+value+"_form";
		
	    // wire up the buttons to dismiss the modal when shown
	    $(modal_id).bind("show", function() {
	        $(modal_id + " a.btn-success").click(function(e) {
	            
	            $(form_id).submit();
	
	            // hide the dialog box
	            $(modal_id).modal('hide');
	        });
	        $(".edit_add_modal a.btn-cancel").click(function(e) {
	            // hide the dialog box
	            $(modal_id).modal('hide');
	        });
	    });

	    // remove the event listeners when the dialog is hidden
	    $(modal_id).bind("hide", function() {
	        // remove event listeners on the buttons
	        $(modal_id + " a.btn").unbind();
	    });
	});
	
    // finally, wire up the actual modal functionality and show the dialog

    $('#add_target_btn').bind('click', function() {
       showTargetForm("Create a new target", "Create");
    });
    
     $('#add_user_btn').bind('click', function() {
       showPlayerForm("Create a new user", "Create");
    });
});


//Called when user clicks on an "edit" link
var edit_target = function(id){
	var url = '/target/detail_json/' + id;
        $.ajax({
           	type: "GET",
           	url: url,
	   		dataType: "json",
           	success: function(data)
           	{
				showTargetForm("Update Target", "OK", id, data.description);
			}

	});
};

//Called when user clicks on an "edit" link
var edit_user = function(id){
	var url = '/user/detail_json/' + id;
        $.ajax({
           	type: "GET",
           	url: url,
	   		dataType: "json",
           	success: function(data)
           	{
				showPlayerForm("Update Player", "OK", id, data.email);
			}

	});
};

var showTargetForm = function(legend, buttonText, id, description){

	//Update form fields to contain current value for target being modified
	$('#target_id').val(id);
	$('#input-description').val(description);

	//Update legend message
	$('#target_modal_legend').html(legend);
	//Update text displayed on OK button
	$('#target_ok_button').html(buttonText);

    $("#target_modal").modal({
      	"backdrop"  : "static",
       	"keyboard"  : true,
       	"show"      : true    // this parameter ensures the modal is shown immediately
    });
}

var showPlayerForm = function(legend, buttonText, id, email){

	//Update form fields to contain current value for target being modified
	$('#user_id').val(id);
	$('#input-email').val(email);

	//Update legend message
	$('#user_modal_legend').html(legend);
	//Update text displayed on OK button
	$('#user_ok_button').html(buttonText);

    $("#user_modal").modal({
      	"backdrop"  : "static",
       	"keyboard"  : true,
       	"show"      : true    // this parameter ensures the modal is shown immediately
    });
}
