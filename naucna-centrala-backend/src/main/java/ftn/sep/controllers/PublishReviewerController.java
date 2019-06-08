package ftn.sep.controllers;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.sep.camunda.services.RestProcessService;
import ftn.sep.dto.DTOReview;

@RestController
public class PublishReviewerController {
	
	@Autowired
	private RestProcessService restService;
	
	@PostMapping(path = "/publish/review/{taskId}", produces = "application/text")
	public ResponseEntity<?> analyzeReviews(@PathVariable("taskId") String taskId, 
			@RequestBody DTOReview dtoReview,
			BindingResult bindingResult,
			Authentication authentication) {
		
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
        }
      
        Map<String, VariableValueDto> variables = new HashMap<>();
        VariableValueDto vv = new VariableValueDto();
        vv.setValue(dtoReview.getCommentEditor());
        variables.put("commentEditor", vv);
        VariableValueDto vv2 = new VariableValueDto();
        vv2.setValue(dtoReview.getCommentAuthor());
        variables.put("commentAuthor", vv2);
        VariableValueDto vv3 = new VariableValueDto();
        vv3.setValue(dtoReview.getSuggestion());
        variables.put("suggestion", vv3);
        this.restService.submitTaskForm(taskId, variables);

        return new ResponseEntity<>("Successfully submitted review!", HttpStatus.OK);
	}

}
