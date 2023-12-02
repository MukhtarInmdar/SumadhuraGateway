package com.sumadhura.sumadhuragateway.employeeservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfo {

	private Integer id;
	private String name;
	private String base64;
	private String url;
	private String extension;
	private Long visibilty;
	private String createdBy;
	private String createdType;
	private String password;
	private String filePath;
	private String fileType;
	
}
