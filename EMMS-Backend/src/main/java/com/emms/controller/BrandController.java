package com.emms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emms.dao.BrandRepo;
import com.emms.model.Brand;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class BrandController {
	
	@Autowired
	BrandRepo brandRepo;
	
	@GetMapping(value= "allBrands")
	public List<Brand> getAllBrands() {
		List<Brand>brandList = brandRepo.findAll();
		System.out.println("Returning brands: "+ brandList.toString());
		return brandList;
	}
	
	@PostMapping(value="addBrand")
	public Brand addBrand(@RequestBody Brand brand) {
		
		Brand brandr  = brandRepo.save(brand);
		System.out.println("Saving brand: " + brand.toString());
		return brandr;
		
	}

}
