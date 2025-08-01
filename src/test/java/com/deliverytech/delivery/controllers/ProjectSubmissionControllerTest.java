package com.deliverytech.delivery.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.deliverytech.delivery.dtos.request.ProjectSubmission;
import com.deliverytech.delivery.security.JwtUtil;
import com.deliverytech.delivery.services.ProjectService;
import com.deliverytech.delivery.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProjectSubmissionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectSubmissionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private ProjectService projectService;

	@MockBean
	private JwtUtil jwtUtil;

	@Test
	@WithMockUser(username = "ana", roles = "USER")
	void testSubmitProject() throws Exception {
		ProjectSubmission submission = new ProjectSubmission();
		submission.setStudentName("Ana");
		submission.setProjectTitle("Delivery API");
		submission.setRepositoryUrl("https://github.com/ana/delivery-api");
		submission.setObservations("Versão final");

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(post("/api/projects/final-submission").contentType("application/json")
				.content(objectMapper.writeValueAsString(submission))).andExpect(status().isOk());
	}
}
