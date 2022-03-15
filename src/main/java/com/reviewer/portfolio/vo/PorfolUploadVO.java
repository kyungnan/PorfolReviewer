package com.reviewer.portfolio.vo;

public class PorfolUploadVO {
	private String category;
	private String title;
	private String url;
	private String FileName;
	private String fileServerName;
	private String thumbnailName;
	private String thumbnailServerName;
	private String description;
	private CharSequence hashtags;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFileServerName() {
		return fileServerName;
	}
	public void setFileServerName(String fileServerName) {
		this.fileServerName = fileServerName;
	}
	public String getThumbnailName() {
		return thumbnailName;
	}
	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}
	public String getThumbnailServerName() {
		return thumbnailServerName;
	}
	public void setThumbnailServerName(String thumbnailServerName) {
		this.thumbnailServerName = thumbnailServerName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CharSequence getHashtags() {
		return hashtags;
	}
	public void setHashtags(CharSequence hashtags) {
		this.hashtags = hashtags;
	}

}
