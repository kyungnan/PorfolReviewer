package com.reviewer.portfolio.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class ReplyVO {
	private Long id;
	private Long boardId;
	private Long grp;		//대댓글이 속한 원본 댓글id
	private int grps;		//같은 grp의 순서
	private int grpl;		//원본댓글:0, 대댓글:1
	private String replyText;
	private String username;
	private String createDt;
	private boolean updateFlag;
	
	public void setCreateDt(LocalDateTime createDt) {
		this.createDt = createDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
