package com.deliverytech.delivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dtos.request.ProjectSubmission;
import com.deliverytech.delivery.entities.Project;
import com.deliverytech.delivery.repositories.ProjectRepository;

@Service
public class ProjectService {

	private final ProjectRepository repository;

	public ProjectService(ProjectRepository repository) {
		this.repository = repository;
	}

	@CacheEvict(value = "projects", allEntries = true)
	public void saveSubmission(ProjectSubmission submission) {
		Project project = new Project();
		project.setStudentName(submission.getStudentName());
		project.setProjectTitle(submission.getProjectTitle());
		project.setRepositoryUrl(submission.getRepositoryUrl());
		project.setObservations(submission.getObservations());

		repository.save(project);
	}

	@Cacheable("projects")
	public List<ProjectSubmission> listarSubmissoes() {
		simulateHeavyOperation(); // simula latência
		return repository.findAll().stream().map(project -> {
			ProjectSubmission submission = new ProjectSubmission();
			submission.setStudentName(project.getStudentName());
			submission.setProjectTitle(project.getProjectTitle());
			submission.setRepositoryUrl(project.getRepositoryUrl());
			submission.setObservations(project.getObservations());
			return submission;
		}).collect(Collectors.toList());
	}

	private void simulateHeavyOperation() {
		try {
			Thread.sleep(3000); // Simula operação pesada
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
