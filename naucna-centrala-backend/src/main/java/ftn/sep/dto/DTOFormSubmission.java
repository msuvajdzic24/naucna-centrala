package ftn.sep.dto;

import java.io.Serializable;

public class DTOFormSubmission implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fieldId;
	private String fieldValue;
	
	public DTOFormSubmission() {
	}

	public DTOFormSubmission(String fieldId, String fieldValue) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
}
