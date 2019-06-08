package ftn.sep.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.sep.camunda.services.RestProcessService;
import ftn.sep.dto.DTOChooseNewReviewer;
import ftn.sep.dto.DTOChooseReviewers;
import ftn.sep.dto.DTODecision;
import ftn.sep.dto.DTOFinalDecision;
import ftn.sep.dtoreturn.DTOReviewEditorReturn;
import ftn.sep.dtoreturn.DTOReviewersReturn;
import ftn.sep.model.PotentialReviewers;
import ftn.sep.model.Review;
import ftn.sep.services.ReviewService;

@RestController
public class PublishEditorController {
	
	@Autowired
	private RestProcessService restService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping(path = "/publish/decision/{taskId}", produces = "application/text")
	public ResponseEntity<?> decision(@PathVariable("taskId") String taskId, 
			@RequestBody DTODecision dtoDecision,
			BindingResult bindingResult,
			Authentication authentication) {
		
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
        }
        
        Map<String, VariableValueDto> variables = new HashMap<>();
        VariableValueDto vv = new VariableValueDto();
        vv.setValue(dtoDecision.isRelevant());
        variables.put("relevant", vv);
        VariableValueDto vv2 = new VariableValueDto();
        vv2.setValue(dtoDecision.isProperlyFormatted());
        variables.put("properlyFormatted", vv2);
        VariableValueDto vv3 = new VariableValueDto();
        vv3.setValue(dtoDecision.getComment());
        variables.put("comment", vv3);
        this.restService.submitTaskForm(taskId, variables);

        return new ResponseEntity<>("Successfully submitted decision!", HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getReviewers/{taskId}", produces = "application/json")
	public ResponseEntity<?> getReviewers(@PathVariable("taskId") String taskId,
			Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
        
		Integer id = (Integer) this.restService.getVariable(processInstanceId, "articleId").getValue();
		Long articleId = id.longValue();
		
		PotentialReviewers pr = this.reviewService.getPotentianlReviewers(articleId);
		@SuppressWarnings("unchecked")
		List<String> choosenReviewers = (List<String>) this.restService.getVariable(processInstanceId, "reviewers").getValue();
		Integer numberOfReviewers = (Integer) this.restService.getVariable(processInstanceId, "numberOfReviewers").getValue();
		
		DTOReviewersReturn dtoRR = DTOReviewersReturn.convert(pr, numberOfReviewers, choosenReviewers);
		
		return new ResponseEntity<DTOReviewersReturn>(dtoRR, HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/chooseReviewers/{taskId}", produces = "application/text")
	public ResponseEntity<?> analyzeReviews(@PathVariable("taskId") String taskId, 
			@RequestBody DTOChooseReviewers dtoCR,
			BindingResult bindingResult,
			Authentication authentication) {
		
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
        }
        
        Map<String, VariableValueDto> variables = new HashMap<>();
        VariableValueDto vv = new VariableValueDto();
        vv.setValue(dtoCR.getReviewers());
        variables.put("reviewers", vv);
        this.restService.submitTaskForm(taskId, variables);

        return new ResponseEntity<>("Successfully chose reviewers!", HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/chooseNewReviewer/{taskId}", produces = "application/text")
	public ResponseEntity<?> analyzeReviews(@PathVariable("taskId") String taskId, 
			@RequestBody DTOChooseNewReviewer dtoCNR,
			BindingResult bindingResult,
			Authentication authentication) {
		
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
        }
        
        Map<String, VariableValueDto> variables = new HashMap<>();
        VariableValueDto vv = new VariableValueDto();
        vv.setValue(dtoCNR.getReviewer());
        variables.put("reviewer", vv);
        this.restService.submitTaskForm(taskId, variables);

        return new ResponseEntity<>("Successfully chose new reviewer!", HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/analyzeReviews/{taskId}", produces = "application/text")
	public ResponseEntity<?> analyzeReviews(@PathVariable("taskId") String taskId, 
			@RequestBody DTOFinalDecision dtoFD,
			BindingResult bindingResult,
			Authentication authentication) {
		
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
        }
        
        Map<String, VariableValueDto> variables = new HashMap<>();
        VariableValueDto vv = new VariableValueDto();
        vv.setValue(dtoFD.getDecision());
        variables.put("decision", vv);
        this.restService.submitTaskForm(taskId, variables);

        return new ResponseEntity<>("Successfully submitted decision!", HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getReviewsEditor/{taskId}", produces = "application/json")
	public ResponseEntity<?> getReviewsEditor(@PathVariable("taskId") String taskId, 
			Authentication authentication) {
			
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
		
		Integer id = (Integer) this.restService.getVariable(processInstanceId, "articleId").getValue();
		Long articleId = id.longValue();
        
        List<Review> reviews = this.reviewService.getReviews(articleId);
        Set<DTOReviewEditorReturn> reviewsEditor = new HashSet<>();
        for (Review review : reviews) {
			DTOReviewEditorReturn dtoRe = DTOReviewEditorReturn.convert(review);
			reviewsEditor.add(dtoRe);
		}
		
		return new ResponseEntity<Set<DTOReviewEditorReturn>>(reviewsEditor, HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/publish/getAuthorMessage/{taskId}", produces = "applicaiton/text")
	public ResponseEntity<?> getAuthorMessage(@PathVariable("taskId") String taskId, 
			Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
		
		String message;
		try{
			message = (String) this.restService.getVariable(processInstanceId, "authorMessage").getValue();
		} catch (Exception e) {
			message = null;
		}
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
		
}
