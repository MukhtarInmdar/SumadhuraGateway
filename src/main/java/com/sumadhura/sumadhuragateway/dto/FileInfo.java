package com.sumadhura.sumadhuragateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)

@Getter
@Setter
@ToString
public class FileInfo {

	private Integer id;
	private String name;
	private String base64;
	private String url;
	private String extension;
	private Long visibilty;
	private String createdBy;
	private String createdType;
	private String filePath;
	private Long doucumentId;

}
