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

import com.emms.dao.ModelRepo;

import com.emms.model.Model;


@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class ModelController {
	
	@Autowired
	ModelRepo modelRepo;
	
	@GetMapping(value="allModels")
	public List<Model> getAllModels(){
		List<Model> modelList =  modelRepo.findAll();
		System.out.println("returning models : "+ modelList.toString());
		return modelList;
	}
	
	@GetMapping(value = "getModelById/{id}")
	public Optional<Model> getModelById(@PathVariable int id) {
		Optional<Model> model = modelRepo.findById(id);
		System.out.println("Returning model : "+ model.toString());
		return model;
	}
	
	@PostMapping(value="addModel")
	public Model addModel(@RequestBody Model  model) {
			
		model.setModelId(generateId());
		System.out.println("saving model: " + model.toString());
		return modelRepo.save(model);
			
	}
	@DeleteMapping(value="deleteModelById/{id}")
	public int deleteModel(@PathVariable int id) {
		System.out.println("Deleting model: "+ id);
		modelRepo.deleteById(id);
		return id;
	}
	
	public int generateId() {
		List<Model> allModels  = modelRepo.findAll();
		
		if(allModels.isEmpty()) {
			return 1;
		}
		else {
			
			int numberOfModels = allModels.size();
			int newId = 0;
			
			Model lastEnteredModel = allModels.get(numberOfModels-1);
			int lastId = lastEnteredModel.getModelId();
			
			newId = ++lastId;
			while(newId < lastId) {
				newId++;
			}
			return newId;
		}
	}
	
	@GetMapping(value="isModelAvailable/{model}")
	public boolean isModelAvailable(@PathVariable String model) {
		model = model.toLowerCase();
		List<Model> modelList = modelRepo.findAll();
		
		for(Model m: modelList) {
			String modelName = m.getModel();
			modelName.toLowerCase();
			if(modelName.equals(model)) {
				System.out.println("Model is already in the database. Returning false");
				return false;
			}
			
		}
		
		System.out.println("Model is not in the database. Returning true.");
		return true;
	}
	
	public void getModelsForBrands() {
		
	}

}
