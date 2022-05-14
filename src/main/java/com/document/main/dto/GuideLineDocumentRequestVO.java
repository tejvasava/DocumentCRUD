package com.document.main.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuideLineDocumentRequestVO {

	private Long id;

	private String title;

	private String remark;

	private Boolean status;

	private MultipartFile document;
	
}