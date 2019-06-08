package ftn.sep.camunda.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CheckReviewService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
			
		//Integer loopCounter = (Integer) execution.getVariable("loopCounter");
		String reviewerUsername = (String) execution.getVariable("reviewer");
		@SuppressWarnings("unchecked")
		List<String> reviewersFinish = (List<String>) execution.getVariable("reviewersFinish");
		for (String reviewer : reviewersFinish) {
			if (reviewer.equals(reviewerUsername)) {
				execution.setVariableLocal("alreadyWritten", true);
				return;
			}
		}
		
		execution.setVariableLocal("alreadyWritten", false);
		
	}

}
