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
       showForm("Create a new player", "Create");
    });
});


//Called when user clicks on an "edit" link
var edit_player = function(id){
	var url = '/player/detail_json/' + id;
        $.ajax({
           	type: "GET",
           	url: url,
	   		dataType: "json",
           	success: function(data)
           	{
				showForm("Update Player", "OK", id, data.email);
			}

	});
};


var showForm = function(legend, buttonText, id, email){
	
	//Update form fields to contain current value for player being modified
	$('#player_id').val(id);
	$('#input-email').val(email);

	//Update legend message
	$('#modal_legend').html(legend);
	//Update text displayed on OK button
	$('#ok_button').html(buttonText);

    $("#myModal").modal({
      	"backdrop"  : "static",
       	"keyboard"  : true,
       	"show"      : true    // this parameter ensures the modal is shown immediately
    });
}