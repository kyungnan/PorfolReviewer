package com.reviewer.portfolio.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reviewer.portfolio.mapper.AttachFileMapper;
import com.reviewer.portfolio.mapper.ThumbnailMapper;
import com.reviewer.portfolio.vo.AttachFileVO;
import com.reviewer.portfolio.vo.ThumbnailVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachFileService {
	@Autowired
	private final AttachFileMapper attachFileMapper;
	@Autowired
	private final ThumbnailMapper thumbnailMapper;
	
	public String getFilePath(String servarFileName) {
    	String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\files\\";
		return projectPath + servarFileName;
	}
	
	public String getThumbnailPath(String servarFileName) {
    	String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\thumbnail\\";
		return projectPath + servarFileName;
	}
	
	public Long uploadAttachFile(MultipartFile multipartFile) {
		String uuid = UUID.randomUUID().toString();
		String originalfileName = multipartFile.getOriginalFilename();
		String serverFileName =  uuid + "_" + originalfileName;
		String storeFilePath = getFilePath(serverFileName);
		
		try {
			multipartFile.transferTo(new File(storeFilePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AttachFileVO attachFile = new AttachFileVO();
		attachFile.setOriginFileName(originalfileName);
		attachFile.setServerFileName(serverFileName);
		attachFile.setFilePath(storeFilePath);
		attachFileMapper.insertFile(attachFile);
		return attachFile.getId();
	}
	
	public Long uploadThumbnail(MultipartFile multipartFile) {
		String uuid = UUID.randomUUID().toString();
		String originalfileName = multipartFile.getOriginalFilename();
		String serverFileName =  uuid + "_" + originalfileName;
		String storeFilePath = getThumbnailPath(serverFileName);
		
		try {
			multipartFile.transferTo(new File(storeFilePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ThumbnailVO thumbnailVO = new ThumbnailVO();
		thumbnailVO.setOriginThumbnailName(originalfileName);
		thumbnailVO.setServerThumbnailName(serverFileName);
		thumbnailVO.setThumbnailPath(storeFilePath);
		
		thumbnailMapper.insertThumbnail(thumbnailVO);
		return thumbnailVO.getId();
	}
}
