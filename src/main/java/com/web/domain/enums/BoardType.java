package com.web.domain.enums;

public enum BoardType {
	notice("��������"),
	free("�����Խ���");
	
	private String value;
	
	BoardType(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return this.value;
	}
}
