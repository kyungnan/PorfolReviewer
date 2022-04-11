$(document).ready(function() {
	var url = $('#fileInfoDiv').eq(0).find($('a')).text();
	$('#fileInfoDiv').eq(0).find($('a')).attr('href', url);
	
	$('#deleteBtn').on('click', function(){
		if(!confirm('포트폴리오를 삭제하시겠습니까?')){
			return false;
		}
	});

	/* 부모댓글 등록 */	
	$('#replyAddBtn').on('click', function(){
		var boardId = $('#boardId').val();
		var replyText = $('#replyText').val();
		var param = { "boardId" : boardId, "replyText" : replyText};

		$.ajax({
			url 	: "/reply",
			type 	: "post",
			data 	: param,
			success : function(resp){
				alert('댓글이 등록되었습니다.');
				$('#replyText').val('');
				var body = '<div class="card-body replyList"><div class="d-inline-block profileImgDiv"><img class="profileImg" alt="" src="/images/people1.png"></div>' +
				'<div class="d-inline-block replyContentDiv"><ul class="list-group list-group-flush"><li class="list-group-item"><h6>'+resp.username+'</h6>' +
				'<p>'+ resp.replyText +'</p>' +
				'<p>' + new Date(+new Date() + 3240 * 10000).toISOString().replace("T", " ").replace(/\..*/, '') +'</p>' +
				'<div><input id="replyId" type="hidden" th:value="${replyList.id}"><button class="btn btn-outline-dark replyModifyFormBtn" type="button">수정</button></div></li></ul></div></div>';
				
				$('#replyDiv').append(body);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert('댓글 등록이 실패하였습니다.');
			}
		});
	});
});