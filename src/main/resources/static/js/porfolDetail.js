$(document).ready(function() {
	var url = $('#fileInfoDiv').eq(0).find($('a')).text();
	$('#fileInfoDiv').eq(0).find($('a')).attr('href', url);
	
});