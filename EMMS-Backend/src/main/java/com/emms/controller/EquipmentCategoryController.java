package com.emms.controller;

import java.util.ArrayList;
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
import com.emms.dao.CategoryBrandRepo;
import com.emms.dao.EquipmentCategoryRepo;
import com.emms.model.Brand;
import com.emms.model.CategoryBrand;
import com.emms.model.EquipmentCategories;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class EquipmentCategoryController {
	
	@Autowired
	EquipmentCategoryRepo categoryRepo;
	@Autowired
	CategoryBrandRepo cbRepo;
	@Autowired
	BrandRepo brandRepo;
	
	@GetMapping(value= "allCategories")
	public List<EquipmentCategories> getAllCategories(){
		List<EquipmentCategories> catList =  categoryRepo.findAll();
		System.out.println("returning all equipment category: "+ catList.toString());
		return catList;
	}
	
	@GetMapping(value = "getCatById/{id}")
	public Optional<EquipmentCategories> getCategoryById(@PathVariable int id) {
		Optional<EquipmentCategories> category = categoryRepo.findById(id);
		System.out.println("Returning equipment : "+ category.toString());
		return category;
	}
	
	//error mitigated after changing ' value = ' to 'value= ' (removed space)
	@PostMapping(value= "addCategory")
	public EquipmentCategories addCategory(@RequestBody EquipmentCategories category) {
		
		category.setCategoryId(generateId());
		System.out.println("saving category: " + category.toString());
		return categoryRepo.save(category);
		
	}
	
	public int generateId() {
		List<EquipmentCategories> allCategories  = categoryRepo.findAll();
		int numberOfCategoriess = allCategories.size();
		int newId = 0;
		
		EquipmentCategories lastEnteredCategory = allCategories.get(numberOfCategoriess-1);
		int lastId = lastEnteredCategory.getCategoryId();
		
		newId = ++lastId;
		if(allCategories.isEmpty()) {
			return 1;
		}
		else {
			while(newId < lastId) {
				newId++;
			}
			return newId;
		}
	}

	@DeleteMapping(value="deleteCategoryById/{id}")
	public int deleteCategory(@PathVariable int id) {
		
		System.out.println("Deleting category with id: " + id);
		categoryRepo.deleteById(id);
		return id;
		
	}
	
	@GetMapping(value="allCategoryBrandCombinations")
	public List<CategoryBrand> getAllEntriesForBrandCategories() {
		List<CategoryBrand> cbList = cbRepo.findAll();
		System.out.println("Returning all combinations for brands and categories: ");
		System.out.println(cbList.toString());
		return cbList;
	}
	
	@PostMapping("addBrandToCategory")
	public boolean addBrandToCategory(@RequestBody CategoryBrand cb) {
		
		System.out.println("Saving brand cat :" + cb.toString());
		String brand = cb.getBrand();
		String category = cb.getCategory();
		
		boolean doesEntryExist = doesEntryExist(brand, category);
		
		if(doesEntryExist == false) {
			cb.setId(generateBrandCategoryId());
			cbRepo.save(cb);
			System.out.println("combo is not in the db. Saving combo");
			return true;
		}
		else {
			System.out.println("combo is  in the db. Returning false");
			return false;
		}
		
		
	}
	
	public boolean doesEntryExist (String brand, String category) {
		List<CategoryBrand> cbList = cbRepo.findAll();
		
		for(CategoryBrand cb: cbList) {
			if( category.equals(cb.getCategory()  )) {
				if(brand.equals(cb.getBrand())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int generateBrandCategoryId() {
		List<CategoryBrand> cbList = cbRepo.findAll();
		int numberOfEntries = cbList.size();
		
		if(cbList.isEmpty()) {
			return 1;
		}
		else {
			int newId = 0;
			CategoryBrand lastEntry = cbList.get(numberOfEntries -1);
			int lastId = lastEntry.getId();
			newId = ++lastId;
			
			while(newId < lastId) {
				newId++;
			}
			return newId;
		}
		
		
	}
	
	@DeleteMapping(value="deleteBrandCategoryById/{id}")
	public int deleteBrandCategoryById(@PathVariable int id) {
		System.out.println("Deleting combo : " + id);
		cbRepo.deleteById(id);
		return id;
	}
	
	@GetMapping(value="getBrandsForCategory/{category}")
	public List<Brand> getBrandsForCategory(@PathVariable String category) {
		
		List<Brand> brandList ;
		List<Brand> returningBrandList = new ArrayList<Brand>();
		List<CategoryBrand> cbList = cbRepo.findAll();
		
		Brand tempBrand = new Brand();
		String brandName;
		int brandId;
		
		brandList = brandRepo.findAll();
		
		for(CategoryBrand cb : cbList) {
			if(cb.getCategory().equals(category)) {
				brandName = cb.getBrand();
				
				for(Brand brand:brandList) {
					if(brand.getBrandName().equals(brandName)) {
						returningBrandList.add(brand);
					}
				}
			}
		}
		
		System.out.println("Returning brand list for category :  " + category );
		System.out.println(returningBrandList.toString());
		return returningBrandList;
		
	}
	
	@GetMapping("isCategoryAvailable/{name}")
	public boolean isNameAvailable(@PathVariable String  name) {
		
		name = name.toLowerCase();
		List<EquipmentCategories> categoryList = categoryRepo.findAll();
		
		
		for(EquipmentCategories category : categoryList) {
			String categoryName = category.getCategoryName();
			categoryName = categoryName.toLowerCase();
			if(categoryName.equals(name)  ) {
				System.out.println("Category is already in the database. Returning false");
				return false;
			}
		}
		
		System.out.println("Category is not in the database. Returning true.");
		return true;
		
	}
	


}
