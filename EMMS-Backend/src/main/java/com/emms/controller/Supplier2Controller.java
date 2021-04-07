//supplier 2 controller

package com.emms.controller;

import java.util.Date;
//import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emms.dao.Supplier2Repo;import com.emms.model.Supplier2;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class Supplier2Controller {
	
	@Autowired
	Supplier2Repo supplier2Repo;
	
	//@GetMapping(value="allSuppliers")
	public List<Supplier2> getAllSuppliers (){
		return supplier2Repo.findAll();
	}
	
	//@PostMapping(value = "addSupplier")
	public synchronized Supplier2 addSupplier(@RequestBody Supplier2 sup) {
		Supplier2 sup2 = supplier2Repo.save(sup);
		return sup2;
	}
	
	//@RequestMapping(value = "getSupplierNameForDate/{date}/{100}")
	public List<Supplier2> getSupplierNameForDate(@PathVariable Date date, Integer originalId) {
			List<Supplier2> suppList = supplier2Repo.getAllRecordsForId(originalId, date);
			System.out.println("Returning supplist: " + suppList.toString());
			return suppList;
	}

}
