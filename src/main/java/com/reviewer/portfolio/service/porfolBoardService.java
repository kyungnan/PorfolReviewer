package com.reviewer.portfolio.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reviewer.portfolio.mapper.AccountMapper;
import com.reviewer.portfolio.mapper.PorfolBoardMapper;
import com.reviewer.portfolio.vo.PorfolUploadVO;

import com.reviewer.portfolio.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class porfolBoardService {
	
	@Autowired
	private final AttachFileService attachFileService;
	@Autowired
	private final AccountMapper accountMapper;
	@Autowired
	private final PorfolBoardMapper porfolBoardMapper;
	
	public void uploadPorfol(Authentication authentication, PorfolUploadVO porfolUploadVO, MultipartFile portfolio, MultipartFile thumbnail) {
		Long fileId = attachFileService.uploadAttachFile(portfolio);
		Long thumbnailId = attachFileService.uploadThumbnail(thumbnail);
		UserVO user = accountMapper.getByUsername(authentication.getName());

		porfolUploadVO.setFileId(fileId);
		porfolUploadVO.setThumbnailId(thumbnailId);
		porfolUploadVO.setUserId(user.getId());
		
		porfolBoardMapper.insertPorfol(porfolUploadVO);
	}
	
	public List<PorfolUploadVO> getAll(HashMap<String, Object> map) {
		List<PorfolUploadVO> boards = Collections.emptyList();
		Integer getAllCnt = porfolBoardMapper.getAllCnt(map);
		
		if (getAllCnt > 0) {
			boards = porfolBoardMapper.getAll(map);
		}
		return boards;
	}
}
