package com.reviewer.portfolio.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AttachFileVO {
	private Long id;
	private String originFileName;
	private String serverFileName;
	private String filePath;
}
