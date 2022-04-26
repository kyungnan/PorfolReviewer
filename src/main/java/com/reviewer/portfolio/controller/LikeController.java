package com.reviewer.portfolio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.portfolio.mapper.AccountMapper;
import com.reviewer.portfolio.mapper.LikeMapper;
import com.reviewer.portfolio.vo.LikeVO;
import com.reviewer.portfolio.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeController {
	
	private final AccountMapper accountMapper;
	private final LikeMapper likeMapper;

	@SuppressWarnings("unused")
	@GetMapping("/porfolDetail/{id}/like")
	public int like(@PathVariable("id") Long id, Authentication authentication) {

		UserVO userVO = accountMapper.getByUsername(authentication.getName());	//좋아요한 UserId 가져오기
		Long likeId = likeMapper.getLikedUserIdByBoardId(id, userVO.getId());	//해당 글에 좋아요 한적 있는지 확인하기
		Integer delete_yn = likeMapper.getLikeByLikeId(likeId);
		
		if (delete_yn == null) {
			LikeVO likeVO = new LikeVO();
			likeVO.setBoardId(id);
			likeVO.setUserId(userVO.getId());
			likeVO.setDeleteYn(1);
			likeMapper.insertLikeOn(likeVO);
		} else if (delete_yn == 0) {
			delete_yn = 1;
			likeMapper.updateLikeOnOff(likeId, delete_yn);
		} else if (delete_yn == 1) {
			delete_yn = 0;
			likeMapper.updateLikeOnOff(likeId, delete_yn);
		}
		return delete_yn;
	}
}
