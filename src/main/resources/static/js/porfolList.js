$(document).ready(function() {
	//포폴 리스트 정렬
	$('#viewType').on('change', function(){
		var param = { viewType : $('#viewType').val()}
		$.ajax({
			type : "GET",
			url  : "/porfolList",
			data : param,
			success : function(resp){
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			}
			
		})
		
	});
});