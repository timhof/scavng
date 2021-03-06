$(document).ready(function() {

    // wire up the buttons to dismiss the modal when shown
    $("#myModal").bind("show", function() {
        $("#myModal a.btn-success").click(function(e) {
            // do something based on which button was clicked
            // we just log the contents of the link element for demo purposes
            $('form').submit();

            // hide the dialog box
            $("#myModal").modal('hide');
        });
        $("#myModal a.btn-cancel").click(function(e) {
            // hide the dialog box
            $("#myModal").modal('hide');
        });
    });

    // remove the event listeners when the dialog is hidden
    $("#myModal").bind("hide", function() {
        // remove event listeners on the buttons
        $("#myModal a.btn").unbind();
    });

    // finally, wire up the actual modal functionality and show the dialog

    $('a.btn-create').bind('click', function() {
       showForm("Create a new target", "Create");
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
				showForm("Update Target", "OK", id, data.description);
			}

	});
};


var showForm = function(legend, buttonText, id, description){
	
	//Update form fields to contain current value for target being modified
	$('#target_id').val(id);
	$('#input-description').val(description);

	//Update legend message
	$('#modal_legend').html(legend);
	//Update text displayed on OK button
	$('#ok_button').html(buttonText);

    $("#myModal").modal({
      	"backdrop"  : "static",
       	"keyboard"  : true,
       	"show"      : true    // this parameter ensures the modal is shown immediately
    });
    
    $('#myModal').on('shown', function () {
	  	$("#input-description").focus();
	});
	
	$("#input-message").keyup(function(event){
	    if(event.keyCode == 13){
	        $("#ok_button").click();
	    }
	});
}