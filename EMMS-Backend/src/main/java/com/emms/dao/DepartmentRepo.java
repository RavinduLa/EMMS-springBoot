package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
