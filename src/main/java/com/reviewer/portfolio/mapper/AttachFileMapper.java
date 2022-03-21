package com.reviewer.portfolio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.AttachFileVO;

@Mapper
@Repository
public interface AttachFileMapper {
	AttachFileVO getById(@Param("id") Long id);
	Long insertFile(AttachFileVO attachFileVO);
}
