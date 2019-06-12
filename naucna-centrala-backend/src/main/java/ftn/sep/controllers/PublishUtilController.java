package ftn.sep.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.exception.NotFoundException;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ftn.sep.camunda.services.RestProcessService;
import ftn.sep.dtoreturn.DTOArticleCamReturn;
import ftn.sep.dtoreturn.DTOAuthorReturn;
import ftn.sep.dtoreturn.DTOJournalCamReturn;
import ftn.sep.model.Article;
import ftn.sep.model.Author;
import ftn.sep.model.Journal;
import ftn.sep.repositories.AuthorRepository;
import ftn.sep.services.ArticleService;
import ftn.sep.services.JournalService;

@RestController
public class PublishUtilController {
	
	@Autowired
	private RestProcessService restService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private JournalService journalService;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@GetMapping(path = "/publish/start", produces = "application/text")
	public ResponseEntity<?> start(Authentication authentication) throws JsonProcessingException {
		
		Map<String, VariableValueDto> variables = new HashMap<>();
		VariableValueDto vv = new VariableValueDto();
		vv.setValue(authentication.getName());
		variables.put("author", vv);
		ProcessInstanceDto pi = this.restService.startProcess("Glavni_proces", variables);
	
		TaskDto task = this.restService.getNextTask(pi.getId());
		
		return new ResponseEntity<String>(task.getId(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getTasks", produces = "application/json")
	public ResponseEntity<?> getTasks(Authentication authentication) {
		
		List<TaskDto> tasks = this.restService.getTaskAssignee(authentication.getName());
		for (TaskDto task : tasks) {
			System.out.println(task.getId() + " " + task.getName());
		}
		return new ResponseEntity<List<TaskDto>>(tasks, HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getArticle/{taskId}", produces = "application/json")
	public ResponseEntity<?> getArticle(@PathVariable("taskId") String taskId, Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processId = task.getProcessInstanceId();
		
		Integer id = (Integer) this.restService.getVariable(processId, "articleId").getValue();
		Long articleId = id.longValue();
		Article article = this.articleService.getArticle(articleId);
		if (article == null) {
			throw new NotFoundException("Article not found!");
		}
		DTOArticleCamReturn dtoAR = DTOArticleCamReturn.convert(article);
		
		return new ResponseEntity<DTOArticleCamReturn>(dtoAR, HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getJournal/{taskId}", produces = "application/json")
	public ResponseEntity<?> getJournal(@PathVariable("taskId") String taskId, Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processId = task.getProcessInstanceId();
		
		Integer id = (Integer) this.restService.getVariable(processId, "journalId").getValue();
		Long journalId = id.longValue();
		Journal journal = this.journalService.getJournal(journalId);
		if (journal == null) {
			throw new NotFoundException("Journal not found!");
		}
		DTOJournalCamReturn dtoJR = DTOJournalCamReturn.convert(journal);
		
		return new ResponseEntity<DTOJournalCamReturn>(dtoJR, HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/download/{articleId}")
	public ResponseEntity<?> downloadArticle(@PathVariable("articleId") Long articleId, Authentication authentication) {
		
		Resource resource = this.articleService.loadFileAsResource(articleId); 
		
        return ResponseEntity.ok()
        		.contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@GetMapping(path = "/publish/getAuthor/{taskId}", produces = "application/json")
	public ResponseEntity<?> getAuthor(@PathVariable("taskId") String taskId, Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processId = task.getProcessInstanceId();
		
		String authorUsername = (String) this.restService.getVariable(processId, "author").getValue();
		Author author = this.authorRepository.findByUsername(authorUsername);
		if (author == null) {
			throw new NotFoundException("Author not found!");
		}
		DTOAuthorReturn dtoAR = DTOAuthorReturn.convert(author);
		
		return new ResponseEntity<DTOAuthorReturn>(dtoAR, HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/getComment/{taskId}", produces = "application/text")
	public ResponseEntity<?> getComment(@PathVariable("taskId") String taskId, Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processId = task.getProcessInstanceId();
		
		String comment = (String) this.restService.getVariable(processId, "comment").getValue();
		
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	@GetMapping(path = "/publish/isFirstCycle/{taskId}", produces = "application/json") 
	public ResponseEntity<?> isFirstCycle(@PathVariable("taskId") String taskId, Authentication authentication) {
		
		TaskDto task = this.restService.getTask(taskId);
		String processId = task.getProcessInstanceId();
		
		boolean firstCycle = (boolean) this.restService.getVariable(processId, "firstCycle").getValue();
		
		return new ResponseEntity<>(firstCycle, HttpStatus.OK);
	}
}
