package com.emms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emms.dao.DepartmentRepo;
import com.emms.model.Department;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class DepartmentController {
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@GetMapping(value = "allDepartments")
	public List<Department> getAllDepartments(){
		List<Department> deptList = departmentRepo.findAll();
		System.out.println("Returnung all departments" );
		System.out.println(deptList);
		return deptList;
	}
	
	@PostMapping("addDepartment")
	public Department addDepartment(@RequestBody Department department) {
		Department dep = departmentRepo.save(department);
		System.out.println("Returnung a department: "+dep );
		return dep;
	}
	
	@GetMapping(value="getIdAvailability/{did}")
	public boolean getAvailability(@PathVariable int did) {
		Optional<Department> dept = departmentRepo.findById(did);
		
		if(dept.isEmpty()) {
			System.out.println("Id is available");
			return true;
		}
		else {
			System.out.println("Id is not available");
			return false;
		}
		
	}
	
	@DeleteMapping(value= "deleteDepartment/{did}")
	public int deleteDepartment(@PathVariable int did) {
		System.out.println("Deleteing department: " + did);
		departmentRepo.deleteById(did);
		return did;
	}

}
