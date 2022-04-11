package com.reviewer.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.portfolio.mapper.ReplyMapper;
import com.reviewer.portfolio.vo.ReplyVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReplyController {
	@Autowired
	private final ReplyMapper replyMapper;
	
	/* 부모댓글 등록 */
	@PostMapping("/reply")
	public ReplyVO insertReply(Authentication authentication, ReplyVO replyVO) {
		replyVO.setUsername(authentication.getName());		//작성자
		replyMapper.insertReply(replyVO);
		replyVO.setGrp(replyVO.getId());					//부모댓글의 경우 grp=id
		replyMapper.insertReply(replyVO);
		return replyVO;
	}
	
	/* 댓글 수정 처리 */
	@PostMapping("/reply/{id}")
	public ReplyVO updateReply(@PathVariable Long id, String replyTextUpdate) {
		ReplyVO replyVO = replyMapper.getByReplyId(id);
		replyVO.setReplyText(replyTextUpdate);
		replyMapper.updateReply(replyVO);
		return replyVO;
	}
	
	/* 댓글 삭제 처리 */
	@DeleteMapping("/reply/{id}")
	public ResponseEntity<String> deleteReply(@PathVariable Long id){
		replyMapper.deleteReply(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
