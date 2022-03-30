$(document).ready(function() {
	var url = $('#fileInfoDiv').eq(0).find($('a')).text();
	$('#fileInfoDiv').eq(0).find($('a')).attr('href', url);
	
	$('#deleteBtn').on('click', function(){
		if(!confirm('포트폴리오를 삭제하시겠습니까?')){
			return false;
		}
	});
});