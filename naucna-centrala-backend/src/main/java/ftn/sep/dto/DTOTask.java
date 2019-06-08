package ftn.sep.dto;

public class DTOTask {

	private String taskId;
	private String name;
	private String assignee;
	
	public DTOTask() {
	}

	public DTOTask(String taskId, String name, String assignee) {
		super();
		this.taskId = taskId;
		this.name = name;
		this.assignee = assignee;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
}
