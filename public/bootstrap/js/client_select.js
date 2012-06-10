//Called when user clicks on an "edit" link
var change_client = function(id){
	alert("change_client");
	var url = '/clients/index_json';
    $.ajax({
        type: "GET",
        url: url,
	  	dataType: "json",
        success: function(data)
        {
        	alert("change_client");
			if(data){
				$.each(data, function() {
					alert(this.name);
					var client_li = $("#clients_ul").append('<li>' + this.name + '</li>').find('li');
	      			client_li.addClass('dropdown');
	   			});
			}
		}
	});
};