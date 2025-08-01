package com.deliverytech.delivery.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.request.ProjectSubmission;
import com.deliverytech.delivery.services.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/projects")
public class ProjectSubmissionController {

	private final ProjectService projectService;

	public ProjectSubmissionController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Operation(summary = "Submeter projeto final")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Submissão realizada com sucesso") })
	@PostMapping("/final-submission")
	public ResponseEntity<String> submitProject(@RequestBody ProjectSubmission submission) {
		projectService.saveSubmission(submission);
		return ResponseEntity.ok("Projeto submetido com sucesso");
	}

	@GetMapping
	public ResponseEntity<List<ProjectSubmission>> listarProjetos() {
		return ResponseEntity.ok(projectService.listarSubmissoes());
	}

}
