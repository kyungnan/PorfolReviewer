package com.reviewer.portfolio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.HashtagVO;

@Mapper
@Repository
public interface HashtagMapper {
	List<HashtagVO> getAll();
	HashtagVO getById(Long id);
	int insertHashtag(HashtagVO hashtagvo);
}
