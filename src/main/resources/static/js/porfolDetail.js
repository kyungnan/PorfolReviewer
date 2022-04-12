$(document).ready(function() {
	var url = $('#fileInfoDiv').eq(0).find($('a')).text();
	$('#fileInfoDiv').eq(0).find($('a')).attr('href', url);
	
	$('#deleteBtn').on('click', function(){
		if(!confirm('포트폴리오를 삭제하시겠습니까?')){
			return false;
		}
	});

	/* 부모댓글 등록 */	
	$('.replyAddBtn').on('click', function(){
		var boardId = $('#boardId').val();
		var replyText = $('#replyText').val();
		var param = { "boardId" : boardId, "replyText" : replyText};

		$.ajax({
			url 	: "/reply",
			type 	: "post",
			data 	: param,
			success : function(resp){
				alert('댓글이 등록되었습니다.');
				location.reload();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert('댓글 등록이 실패하였습니다.');
			}
		});
	});
});