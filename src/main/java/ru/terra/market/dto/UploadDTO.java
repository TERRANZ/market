package ru.terra.market.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadDTO {
	private String name;
	private CommonsMultipartFile fileData;
	private String target;
	private Integer targetId;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
}