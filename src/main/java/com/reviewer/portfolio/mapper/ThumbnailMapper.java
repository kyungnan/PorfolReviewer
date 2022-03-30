package com.reviewer.portfolio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.ThumbnailVO;

@Mapper
@Repository
public interface ThumbnailMapper {
	ThumbnailVO getById(@Param("id") Long id);
	Long insertThumbnail(ThumbnailVO thumbnailVO);
	int deleteThumbnail(@Param("id") Long id);
}
