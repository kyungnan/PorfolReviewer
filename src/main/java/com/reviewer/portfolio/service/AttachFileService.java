package com.reviewer.portfolio.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reviewer.portfolio.mapper.AttachFileMapper;
import com.reviewer.portfolio.mapper.ThumbnailMapper;
import com.reviewer.portfolio.vo.AttachFileVO;
import com.reviewer.portfolio.vo.PorfolUploadVO;
import com.reviewer.portfolio.vo.ThumbnailVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachFileService {
	@Value("${file.upload.location}")
	private String filePath;
	@Autowired
	private final AttachFileMapper attachFileMapper;
	@Autowired
	private final ThumbnailMapper thumbnailMapper;
	
	public Long uploadAttachFile(MultipartFile multipartFile) {
		String uuid = UUID.randomUUID().toString();
		String originalfileName = multipartFile.getOriginalFilename();
		String serverFileName =  uuid + "_" + originalfileName;
		String storeFilePath = filePath + "/attachFile/" + serverFileName;
		
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
		String storeFilePath = filePath + "/thumbnail/" + serverFileName;
		
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
