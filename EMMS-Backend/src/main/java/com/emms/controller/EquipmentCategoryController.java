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

import com.emms.dao.CategoryBrandRepo;
import com.emms.dao.EquipmentCategoryRepo;
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
	
	@GetMapping(value= "allCategories")
	public List<EquipmentCategories> getAllCategories(){
		List<EquipmentCategories> catList =  categoryRepo.findAll();
		System.out.println("returning equipment category: "+ catList.toString());
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
	
	@PostMapping("addBrandToCategory")
	public CategoryBrand addBrandToCategory(@RequestBody CategoryBrand cb) {
		
		System.out.println("Saving brand cat :" + cbRepo.toString());
		return cbRepo.save(cb);
	}
	
	@GetMapping("isCategoryAvailable/{name}")
	public boolean isNameAvailable(@PathVariable String  name) {
		
		List<EquipmentCategories> categoryList = categoryRepo.findAll();
		
		
		for(EquipmentCategories category : categoryList) {
			String categoryName = category.getCategoryName();
			if(categoryName.equals(name)) {
				System.out.println("Category is already in the database. Returning false");
				return false;
			}
		}
		
		System.out.println("Category is not in the database. Returning true.");
		return true;
		
	}
	


}
