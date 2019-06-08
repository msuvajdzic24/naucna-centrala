package ftn.sep.camunda.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.CompleteTaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ftn.sep.dto.DTOFormField;

@Service
public class RestProcessService {

	private RestTemplate restTemplate = new RestTemplate();
	
	public ProcessInstanceDto startProcess(String key, Map<String, VariableValueDto> variables) {
		StartProcessInstanceDto spi = new StartProcessInstanceDto();
		spi.setVariables(variables);
		
		ProcessInstanceDto pi = this.restTemplate.postForObject("http://localhost:8080/rest/process-definition/key/" + key + "/start", 
				spi, ProcessInstanceDto.class);
		return pi;
	}
	
	public TaskDto getNextTask(String processId) {
		ResponseEntity<List<TaskDto>> response = this.restTemplate.exchange("http://localhost:8080/rest/task?processInstanceId=" + processId, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<TaskDto>>(){});
		List<TaskDto> tasks = response.getBody();
		return tasks.get(0);
	}
	
	public TaskDto getTask(String taskId) {
		TaskDto task = restTemplate.getForObject("http://localhost:8080/rest/task/" + taskId, 
				TaskDto.class);
		return task;
	}
	
	public List<TaskDto> getTaskAssignee(String assignee) {
		ResponseEntity<List<TaskDto>> response = this.restTemplate.exchange("http://localhost:8080/rest/task?assignee=" + assignee, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<TaskDto>>(){});
		List<TaskDto> tasks = response.getBody();
		return tasks;
	}
	
	public VariableValueDto getVariable(String processId, String name) {
		VariableValueDto vv = this.restTemplate.getForObject("http://localhost:8080/rest/process-instance/" + processId + "/variables/" + name, 
				VariableValueDto.class);
		return vv;
	}
	
	public List<DTOFormField> getTaskForm(String taskId) {
		ResponseEntity<LinkedHashMap<String, VariableValueDto>> response = this.restTemplate.exchange("http://localhost:8080/rest/task/" + taskId + "/form-variables",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<LinkedHashMap<String, VariableValueDto>>() {});
		LinkedHashMap<String, VariableValueDto> map = response.getBody();
		List<DTOFormField> formFields = new ArrayList<>();
		for (String key : map.keySet()) {
			DTOFormField ff = new DTOFormField();
			ff.setId(key);
			ff.setType(map.get(key).getType());
			ff.setLabel(capitalize(key));
			formFields.add(ff);
		}
		return formFields;
	}
	
	public void submitTaskForm(String taskId, Map<String, VariableValueDto> variables) {
		CompleteTaskDto completeDto = new CompleteTaskDto();
		completeDto.setVariables(variables);
		@SuppressWarnings("unused")
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/rest/task/" + taskId + "/submit-form", completeDto, Void.class);
	}
	
	public void completeTask(String taskId) {
		CompleteTaskDto completeDto = new CompleteTaskDto();
		@SuppressWarnings("unused")
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/rest/task/" + taskId + "/complete", completeDto, Void.class);
	}
	
	private String capitalize(final String line) {
		   return Character.toUpperCase(line.charAt(0)) + line.substring(1);
		}
}
