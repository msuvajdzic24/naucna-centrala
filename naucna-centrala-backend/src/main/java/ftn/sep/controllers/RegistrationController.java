package ftn.sep.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;
//import org.camunda.bpm.engine.impl.form.FormFieldImpl;
//import org.camunda.bpm.engine.impl.form.FormPropertyImpl;
//import org.camunda.bpm.engine.impl.form.handler.FormFieldHandler;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
//import org.camunda.bpm.engine.rest.dto.task.FormDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
//import org.camunda.bpm.engine.rest.sub.task.impl.TaskVariablesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ftn.sep.camunda.services.RegistrationService;
import ftn.sep.camunda.services.RestProcessService;
import ftn.sep.dto.DTOFormField;
import ftn.sep.dto.DTOFormFields;
import ftn.sep.dto.DTOFormSubmission;
import ftn.sep.exceptions.BadRequestException;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private RestProcessService restService;
	
	@Autowired
	private RegistrationService registrationService;
	
	
	@GetMapping(path = "/start", produces = "application/json")
	public ResponseEntity<?> start() throws JsonParseException, JsonMappingException, IOException {

		Map<String, VariableValueDto> variables = new HashMap<>();
		ProcessInstanceDto pi = this.restService.startProcess("Registracija", variables);
		System.out.println(pi.getId());
		System.out.println(pi.getDefinitionId());
		
		TaskDto task = this.restService.getNextTask(pi.getId());
		System.out.println(task.getId());
		System.out.println(task.getName());
		System.out.println(task.getTaskDefinitionKey());
			
		List<DTOFormField> formFields = this.restService.getTaskForm(task.getId());
		
		DTOFormFields dtoFF = new DTOFormFields(task.getId(), formFields, pi.getId());
		return new ResponseEntity<DTOFormFields>(dtoFF, HttpStatus.OK);
	}
	
	@PostMapping(path = "/post/{taskId}", produces = "application/text")
	public ResponseEntity<?> postForm(@PathVariable("taskId") String taskId, 
			@RequestBody List<DTOFormSubmission> dtoFS) {
			
		boolean userExist = this.registrationService.userExist(dtoFS);
		if (userExist) {
			throw new BadRequestException("User with that username already exist!");
		}
				
		Map<String, VariableValueDto> map = this.mapListToDto(dtoFS);
		this.restService.submitTaskForm(taskId, map);
		
		return new ResponseEntity<>("Successfully registration!", HttpStatus.OK);
	}
		
	@GetMapping(path = "/confirmEmail/{processId}", produces = "application/text")
	public ResponseEntity<?> confirmEmail(@PathVariable("processId") String processId) {
				
		TaskDto task;
		try{
			task = this.restService.getNextTask(processId);
		} catch(Exception e) {
			throw new BadRequestException("No email for confirmation!");
		}


		Integer id = (Integer) this.restService.getVariable(processId, "userId").getValue();
		Long authorId = id.longValue();
		this.registrationService.confirmEmail(authorId);
		
		this.restService.completeTask(task.getId());
		
		return new ResponseEntity<>("Email successfully confirmed!", HttpStatus.OK);
	}
	
	private Map<String, VariableValueDto> mapListToDto(List<DTOFormSubmission> list) {
		
		Map<String, VariableValueDto> map = new HashMap<>();
		for(DTOFormSubmission temp : list){
			VariableValueDto vv = new VariableValueDto();
			vv.setValue(temp.getFieldValue());
			map.put(temp.getFieldId(), vv);
		}
		
		return map;
	}

}
