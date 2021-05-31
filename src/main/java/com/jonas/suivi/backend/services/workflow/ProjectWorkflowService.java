package com.jonas.suivi.backend.services.workflow;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.impl.Project;

@Service
public class ProjectWorkflowService {

//	@Autowired
//	private RuntimeService runtimeService;
//
//	@Autowired
//	private TaskService taskService;
//
//	@Transactional
//	public void startProcess(Project project) {
//		Map<String, Object> variables = new HashMap<>();
//		variables.put("owner", project.getProjectManager().getLogin());
//		variables.put("label", project.getLabel());
//		runtimeService.startProcessInstanceByKey("projectClose", variables);
//	}

//	@Transactional
//	public List<Project> getTasks(String assignee) {
//		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(assignee).list();
//		return tasks.stream().map(task -> {
//			Map<String, Object> variables = taskService.getVariables(task.getId());
//			return new Project(task.getId(), (String) variables.get("owner"), (String) variables.get("label"));
//		}).collect(Collectors.toList());
//	}
//
//	@Transactional
//	public void submitReview(Approval approval) {
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("approved", approval.isStatus());
//		taskService.complete(approval.getId(), variables);
//	}

}
