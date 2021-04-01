package com.emms.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.emms.archiveDao.DepartmentArchiveRepository;
import com.emms.archiveModels.DepartmentArchive;
import com.emms.dao.DepartmentRepo;
import com.emms.model.Department;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class DepartmentController {
	
	@Autowired
	DepartmentRepo departmentRepo;
	@Autowired
	DepartmentArchiveRepository departmentArchiveRepository;
	
	@GetMapping(value = "allDepartments")
	public List<Department> getAllDepartments(){
		List<Department> deptList = departmentRepo.findAll();
		
		List<Department> activeDeptList = new ArrayList<>();
		
		for(Department d : deptList) {
			String status = d.getStatus();
			if(status.equals("active")) {
				activeDeptList.add(d);
			}
		}
		System.out.println("Returning all active departments" );
		System.out.println(activeDeptList);
		return activeDeptList;
	}
	
	@PostMapping("addDepartment")
	public Department addDepartment(@RequestBody Department department) {
		department.setStatus("active");
		Department dep = departmentRepo.save(department);
		
		DepartmentArchive da = new DepartmentArchive();
		da.setOriginalId(dep.getDid());
		da.setDepartmentName(dep.getDepartmentName());
		
		Date date = new Date();
		da.setCreatedDate(date);
		da.setStatus("active");
		
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
		
		Optional<Department> departmentOptional = departmentRepo.findById(did);
		Department department = departmentOptional.get();
		DepartmentArchive da = new  DepartmentArchive();
		List<DepartmentArchive> daList = new ArrayList<>();
		daList = departmentArchiveRepository.findByOriginalId(department.getDid());
		
		for(DepartmentArchive d : daList) {
			d.setStatus("history");
			departmentArchiveRepository.save(d);
		}
		
		da.setOriginalId(department.getDid());
		da.setDepartmentName(department.getDepartmentName());
		
		Date currentDate  = new Date();
		da.setCreatedDate(currentDate);
		da.setStatus("deleted");
		departmentArchiveRepository.save(da);
		department.setStatus("deleted");
		departmentRepo.save(department);
		//departmentRepo.deleteById(did);
		return did;
	}

}
