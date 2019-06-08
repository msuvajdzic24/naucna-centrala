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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.sep.camunda.services.RestProcessService;
import ftn.sep.dto.DTOChooseJournal;
import ftn.sep.dto.DTOFinalCorrection;
import ftn.sep.dto.DTOPayment;
import ftn.sep.dto.DTOResubmitArticle;
import ftn.sep.dto.DTOSubmitArticle;
import ftn.sep.dtoreturn.DTOReviewAuthorReturn;
import ftn.sep.model.Article;
import ftn.sep.model.Review;
import ftn.sep.services.ArticleService;
import ftn.sep.services.ReviewService;

@RestController
public class PublishAuthorController {
	
    @Autowired
    private RestProcessService restService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping(path = "/publish/chooseJournal/{taskId}", produces = "application/json")
	public ResponseEntity<?> chooseJournal(@PathVariable("taskId") String taskId, 
			@RequestBody DTOChooseJournal dtoCJ,
			Authentication authentication) {
			
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
		
		Map<String, VariableValueDto> variables = new HashMap<>();
		VariableValueDto vv = new VariableValueDto();
		vv.setValue(dtoCJ.getJournalId());
		variables.put("journalId", vv);
		this.restService.submitTaskForm(taskId, variables);
		
		task = this.restService.getNextTask(processInstanceId);
		
		return new ResponseEntity<TaskDto>(task, HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/payMembershipFee/{taskId}", produces = "application/json")
	public ResponseEntity<?> payMembershipFee(@PathVariable("taskId") String taskId,
			@RequestBody DTOPayment dtoPayment,
			BindingResult bindingResult,
			Authentication authentication) {
		
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
        }
        
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
		
		Map<String, VariableValueDto> variables = new HashMap<>();
		VariableValueDto vv = new VariableValueDto();
		vv.setValue(dtoPayment.isPayment());
		variables.put("payment", vv);
        this.restService.submitTaskForm(taskId, variables);
        
        try {
        	task = this.restService.getNextTask(processInstanceId);
        } catch (Exception e) {
        	task = null;
        }
        
		return new ResponseEntity<TaskDto>(task, HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/submitArticle/{taskId}", produces = "application/json")
	public ResponseEntity<?> submitArticle(@PathVariable("taskId") String taskId,
			@ModelAttribute DTOSubmitArticle dtoSA,
			Authentication authentication) {
        
        Article article = this.articleService.submit(dtoSA, authentication.getName());
        
		Map<String, VariableValueDto> variables = new HashMap<>();
		VariableValueDto vv = new VariableValueDto();
		vv.setValue(article.getId());
		variables.put("articleId", vv);
        this.restService.submitTaskForm(taskId, variables);
        
		return new ResponseEntity<>("Article succesfully submitted!", HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/resubmitArticle/{taskId}", produces = "application/json")
	public ResponseEntity<?> resubmitArticle(@PathVariable("taskId") String taskId,
			@ModelAttribute DTOResubmitArticle dtoRA,
			Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
        
		Integer id = (Integer) this.restService.getVariable(processInstanceId, "articleId").getValue();
		Long articleId = id.longValue();
        @SuppressWarnings("unused")
		Article article = this.articleService.resubmit(articleId, dtoRA, authentication.getName());
       
        this.restService.completeTask(taskId);
        
		return new ResponseEntity<>("Article succesfully resubmitted!", HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getReviewsAuthor/{taskId}", produces = "application/json")
	public ResponseEntity<?> getReviewsAuthor(@PathVariable("taskId") String taskId, 
			Authentication authentication) {
			
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
		
		Integer id = (Integer) this.restService.getVariable(processInstanceId, "articleId").getValue();
		Long articleId = id.longValue();
        
        List<Review> reviews = this.reviewService.getReviews(articleId);
        Set<DTOReviewAuthorReturn> reviewsAuthor = new HashSet<>();
        for (Review review : reviews) {
			DTOReviewAuthorReturn dtoRA = DTOReviewAuthorReturn.convert(review);
			reviewsAuthor.add(dtoRA);
		}
		
		return new ResponseEntity<Set<DTOReviewAuthorReturn>>(reviewsAuthor, HttpStatus.OK);
	}
	
	@PostMapping(path = "/publish/submitCorrection/{taskId}", produces = "application/json")
	public ResponseEntity<?> submitCorrection(@PathVariable("taskId") String taskId,
			@ModelAttribute DTOFinalCorrection dtoFC,
			Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processInstanceId = task.getProcessInstanceId();
        
		Integer id = (Integer) this.restService.getVariable(processInstanceId, "articleId").getValue();
		Long articleId = id.longValue();
		DTOResubmitArticle dtoRA = new DTOResubmitArticle(dtoFC.getFile(), dtoFC.getTitle(), dtoFC.getKeywords(), dtoFC.getArticleAbstract());
        @SuppressWarnings("unused")
		Article article = this.articleService.resubmit(articleId, dtoRA, authentication.getName());
       
    	Map<String, VariableValueDto> variables = new HashMap<>();
		VariableValueDto vv = new VariableValueDto();
		vv.setValue(dtoFC.getAuthorMessage());
		variables.put("authorMessage", vv);
        this.restService.submitTaskForm(taskId, variables);
        
		return new ResponseEntity<>("Article succesfully resubmitted!", HttpStatus.OK);
	}

}
