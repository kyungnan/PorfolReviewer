package com.reviewer.portfolio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.reviewer.portfolio.vo.CertificationVO;
import com.reviewer.portfolio.vo.UserVO;

@Mapper
@Repository
public interface AccountMapper {
	List<UserVO> getAll();
	UserVO getById(@Param("id") Long id);
	UserVO getByUsername(@Param("username") String username);
	UserVO getByEmail(@Param("email") String email);
	int insertUser(UserVO userVO);
	int deleteUser(@Param("id") Long id);
	int insertCertificationNum(CertificationVO certificationVO);
	String getByJsessionId(String jsessionId);
}
