package com.reviewer.portfolio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.LikeVO;

@Mapper
@Repository
public interface LikeMapper {
	int getTotLikeCnt(Long boardId);
	Long getLikedUserIdByBoardId(@Param("boardId") Long boardId, @Param("userId") Long userId);
	int getLikeByLikeId(Long id);
	int insertLikeOn(LikeVO likeVO);
	int updateLikeOnOff(@Param("id") Long id, @Param("deleteYn") int deleteYn);
}
