$(document).ready(function() {
	//포폴 리스트 정렬
	$('#viewType').on('change', function(){
		$("#viewType option:eq("+this+")").prop("selected", true);
		var param = { viewType : $('#viewType').val()}
		var viewType = $('#viewType').val();
		$.ajax({
			type : "GET",
			url  : "/porfolList/" + viewType,
			data : param,
			async: false,
			success : function(resp){
				var arr = [];
				var categoryHtml = '';
				var cardHtml = '';
				$('#viewTypeVal').val(resp[0].viewType);

				$.each(resp, function(idx, item){
					arr.push(resp[idx]);
				});
				
				/* arr 배열 반복문 돌리기(순서O) */
				$.each(arr, function(idx, item){
					var thumbnailName = '';
					$.ajax({
						type : "GET",
						url  : "/thumbnail/" + arr[idx].thumbnailId,
						async: false,
						success : function(data){
							$.each(data, function(i, t){
								thumbnailName = data[i];
							});
						}
					});
					
					if (arr[idx].category === 'frontend'){
						categoryHtml = '<li class="list-group-item"><img src="/images/front.png"><span>프론트엔드</span></li>';
					} else if (arr[idx].category === 'backend'){
						categoryHtml = '<li class="list-group-item"><img src="/images/back.png"><span>백엔드</span></li>';
					} else if (arr[idx].category === 'design'){
						categoryHtml = '<li class="list-group-item"><img src="/images/design.png"><span>디자인</span></li>';
					}

					cardHtml += '<div class="card"><div class="thumbnailDiv"><a href="/porfolDetail/' + arr[idx].id + '">'
								 + '<img id="thumbnail" src="/upload/thumbnail/' + thumbnailName + '" class="card-img-top" alt="thumbnail.."></a></div>'
								 + '<div class="card-body"><ul class="list-group list-group-flush">' + categoryHtml + '</ul><h5 class="card-title"><span>' + arr[idx].title + '</sapn></h5>'
								 + '<p class="card-text"><span>'+ arr[idx].description +'</sapn></p><p id="hashtags"><sapn>' + arr[idx].hashtags + '</span></p></div></div>';
							
					$('.row').empty().append(cardHtml);
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			}
			
		})
		
	});
	
	$('.pageIndex').on('click', function(){
		var idx = $(this).text();
		var cntPerPage = $('.cntPerPage').val();
		var category = $('.category').val();
		var viewType = $('#viewTypeVal').val();				
		
		$('.pageIndex').attr('href', '/porfolList?page=' + encodeURI(idx) + "&cntPerPage=" + encodeURI(cntPerPage) + "&category=" + encodeURI(category) + "&viewType=" + encodeURI(viewType));
	});
});
