package ftn.sep.dto;

import java.util.List;

public class DTOFormFields {
	
	String taskId;
	List<DTOFormField> formFields;
	String processInstanceId;
	
	public DTOFormFields() {
	}

	public DTOFormFields(String taskId, List<DTOFormField> formFields, String processInstanceId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<DTOFormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<DTOFormField> formFields) {
		this.formFields = formFields;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

}
