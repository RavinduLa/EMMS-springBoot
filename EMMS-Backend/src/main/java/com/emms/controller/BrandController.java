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

import com.emms.dao.BrandRepo;
import com.emms.model.Brand;
//import com.emms.model.EquipmentCategories;

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
	public synchronized Brand addBrand(@RequestBody Brand brand) {
		
		brand.setBrandId(generateId());
		Brand brandr  = brandRepo.save(brand);
		System.out.println("Saving brand: " + brand.toString());
		return brandr;
		
	}
	
	public int generateId() {
		List<Brand> allBrands  = brandRepo.findAll();
		
		if(allBrands.isEmpty()) {
			return 1;
		}
		else {
			
			int numberOfBrands = allBrands.size();
			int newId = 0;
			
			Brand lastEnteredBrand = allBrands.get(numberOfBrands-1);
			int lastId = lastEnteredBrand.getBrandId();
			
			newId = ++lastId;
			while(newId < lastId) {
				newId++;
			}
			return newId;
		}
	}
	
	@GetMapping("isBrandAvailable/{name}")
	public synchronized boolean isNameAvailable(@PathVariable String  name) {
		name = name.toLowerCase();
		List<Brand> brandList = brandRepo.findAll();
		
		
		for(Brand brand : brandList) {
			String brandName = brand.getBrandName();
			brandName = brandName.toLowerCase();
			if(brandName.equals(name)) {
				System.out.println("Brand is already in the database. Returning false");
				return false;
			}
		}
		
		System.out.println("Brand is not in the database. Returning true.");
		return true;
		
	}
	
	
	@GetMapping(value="/getBrandById/{id}")
	public Optional<Brand>  getEquipmentById(@PathVariable int id) {
		Optional<Brand> equipment = brandRepo.findById( id);

		System.out.println("Returning brand for id");
		System.out.println(equipment.toString());
		
		return equipment;
		
	}
	
	@DeleteMapping(value="/deleteBrand/{id}")
	public synchronized int deleteById(@PathVariable int id) {
		System.out.println("deleting equipment " + id);
		brandRepo.deleteById(id);
		return id;
	}

}
