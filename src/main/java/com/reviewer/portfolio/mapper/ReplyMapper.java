package com.reviewer.portfolio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.ReplyVO;

@Mapper
@Repository
public interface ReplyMapper {
	List<ReplyVO> getAll(@Param("boardId") Long boardId);
	ReplyVO getByReplyId(@Param("id") Long id);
	int insertReply(ReplyVO replyVO);
	int updateReply(ReplyVO replyVO);
	int deleteReply(@Param("id") Long id);
}
