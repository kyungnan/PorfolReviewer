$(document).ready(function() {
	var url = $('#porfolUrl').text();
	$('#porfolUrl').attr('href', url);
	
	$('#deleteBtn').on('click', function(){
		if(!confirm('포트폴리오를 삭제하시겠습니까?')){
			return false;
		}
	});
	
	/* 부모댓글 등록 */	
	$('.replyAddBtn').on('click', function(){
		var boardId = $('#boardId').val();
		var boardTitle = $('#porfolTitle').text();
		var boardWriter = $('#boardWriter').val();
		var replyWriter = $('#replyWriter').val();
		var replyText = $('#replyText').val();
		var param = { "boardId" : boardId, "replyText" : replyText};

		$.ajax({
			url 	: "/reply",
			type 	: "post",
			data 	: param,
			success : function(resp){
				alert('댓글이 등록되었습니다.');
				location.reload();
				if (boardWriter != replyWriter){
					if (socket){
						let socketMsg = "reply," + replyWriter + "," + boardWriter + "," + boardId + "," + boardTitle;
						socket.send(socketMsg);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert('댓글 등록이 실패하였습니다.');
			}
		});
	});
});

/* 부모댓글 수정 폼 (get)*/
function replyUpdateForm (replyId){
	var username = $('#' + replyId + ' .username').text();
	var replyTextOrigin = $('#' + replyId + ' .replyTextOrigin').text();
	var updateForm =  '<div th:id="' + replyId + '" class="card-body replyList"><div class="d-inline-block profileImgDiv"><img class="profileImg" alt="" src="/images/people1.png"></div>' +
			'<div class="d-inline-block replyContentDiv"><ul class="list-group list-group-flush"><li class="list-group-item"><h6 id="username">'+username+'</h6>' +
			'<textarea id="replyTextUpdate" class="form-control" rows="3">'+ replyTextOrigin +'</textarea>' +
			'<p>' + new Date(+new Date() + 3240 * 10000).toISOString().replace("T", " ").replace(/\..*/, '') +'</p>' +
			'<div><button id="replyUpdateBtn" class="btn btn-outline-dark" type="button">수정완료</button></div></li></ul></div></div>';

	$('#' + replyId).replaceWith(updateForm);
			
	/* 부모댓글 수정 처리 (post)*/
	$('#replyUpdateBtn').on('click', function(){
		var replyTextUpdate = $('#replyTextUpdate').val();	
		$.ajax({
			url		 : "/reply/" + replyId,
			type	 : "post",
			data	 : {
				replyTextUpdate : replyTextUpdate
			},
			success  : function(resp){
				location.reload();
			},
			error 	 : function(XMLHttpRequest, textStatus, errorThrown){
				alert('댓글 수정이 실패했습니다. 다시 시도 해주세요.');
			}
		});
	})
}
		
/* 부모댓글 삭제 처리 */
function replyDelete (replyId){
	if (confirm('댓글을 삭제하시겠습니까?')){
		$.ajax({
			url		 : "/reply/" + replyId,
			type	 : "delete",
			success  : function(resp){
				location.reload();
			},
			error 	 : function(XMLHttpRequest, textStatus, errorThrown){
				alert('댓글 삭제가 실패했습니다. 다시 시도 해주세요.');
			}
		});
	} else {
		return;
	}
}
		
/* 자식댓글 등록 폼 (get) */
function reReplyForm (replyId){
	var reReplyForm = '<div class="reReplyForm"><form><div class="card-body"><textarea id="reReplyText" class="form-control" rows="3"></textarea></div>' +
			'<div class="card-footer bg-light"><button type="button" class="btn btn-outline-dark" id="reReplyAddBtn">등록</button></div></form></div>';

	$('#' + replyId + " .reReplyForm").replaceWith(reReplyForm);
			
	/* 자식댓글 등록 */
	$('#reReplyAddBtn').on('click', function(){
		var originReplyUsername = $('#originReplyUsername').text();
		var boardId = $('#boardId').val();
		var boardTitle = $('#porfolTitle').text();
		var replyWriter = $('#replyWriter').val();
		var replyText = $('#reReplyText').val();
		var param = { "boardId" : boardId, "replyText" : replyText, "grp" : replyId};
				
		$.ajax({
			url 	: "/reply",
			type 	: "post",
			data 	: param,
			success : function(resp){
				alert('댓글이 등록되었습니다.');
				location.reload();
				
				if (originReplyUsername != replyWriter){
					if (socket){
						let socketMsg = "reReply," + replyWriter + "," + originReplyUsername + "," + boardId + "," + boardTitle;
						socket.send(socketMsg);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert('댓글 등록이 실패하였습니다.');
			}
		});
	});
}

/* 좋아요 클릭 */
function like_func(porfolUploadVO_id){
	var boardId = $('#boardId').val();
	var boardTitle = $('#porfolTitle').text();
	var boardWriter = $('#boardWriter').val();
	var replyWriter = $('#replyWriter').val();
    $.ajax({
        type:"GET",
        url:`/porfolDetail/${porfolUploadVO_id}/like`
    }).done(function(resp){
		$('#likeDeleteYn').val(resp);
		location.reload();
		
		if (boardWriter != replyWriter && resp == 1){
			if (socket){
				let socketMsg = "like," + replyWriter + "," + boardWriter + "," + boardId + "," + boardTitle;
				socket.send(socketMsg);
			}
		}
    }).fail(function(error){
        alert(JSON.stringify(error));
    });
}

