package ftn.sep.camunda.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class NewReviewerHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		DelegateExecution execution =  delegateTask.getExecution();
		
		@SuppressWarnings("unchecked")
		List<String> reviewers = (List<String>) execution.getVariable("reviewers");
		String newReviewer = (String) execution.getVariable("reviewer");
		reviewers.add(newReviewer);
		execution.setVariable("reviewers", reviewers);
		
	}

}
