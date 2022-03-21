package com.reviewer.portfolio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.PorfolUploadVO;

@Mapper
@Repository
public interface PorfolBoardMapper {
	List<PorfolUploadVO> getAll();
	PorfolUploadVO getById(@Param("id") Long id);
	int insertPorfol(PorfolUploadVO userVO);
	int deletePorfol(@Param("id") Long id);
}