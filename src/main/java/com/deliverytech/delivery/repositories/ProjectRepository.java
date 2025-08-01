package com.deliverytech.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
