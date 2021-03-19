package com.emms.controller;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emms.dao.SupplierHistoryRepository;
import com.emms.dao.SupplierRepository;
import com.emms.model.Supplier;
import com.emms.model.SupplierHistory;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class SupplierController {
	
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	SupplierHistoryRepository supplierHistoryRepository;
	
	@RequestMapping(value = "allSuppliers")
	public List<Supplier> getAllSuppliers(){
		List<Supplier> supplierList = supplierRepository.findAll();
		return supplierList;
	}
	
	@PostMapping(value = "addSupplier")
	public Supplier addSupplier (@RequestBody Supplier supplier) {
		Supplier sup = new Supplier();
		int id = generateId2();
		System.out.println("Setting supplier id: "+ id);
		supplier.setSupplierId(id);
		sup = supplierRepository.save(supplier);
		System.out.println("adding supplier: "+ sup);
		return sup;
	}
	
	@RequestMapping(value = "getSupplierById/{id}")
	public Optional<Supplier> getSupplierById(@PathVariable int id) {
		return supplierRepository.findById(id);
	}
	
	public int generateId() {
		Long l = new Long(10);
		l = supplierRepository.count();
		int supplierCount = l.intValue();
		int newId;
		
		if(supplierCount == 0) {
			newId =  1;
		}
		else {
			newId = ++supplierCount;
			Optional<Supplier> lastRecord = supplierRepository.findById(supplierCount-1);
			Supplier sup = lastRecord.get();
			int lastId = sup.getSupplierId();
			
			newId = ++lastId;
			while(newId < lastId) {
				newId++;
			}
			
			
		}
		System.out.println("Returning new id: " + newId);
		return newId;
	}
	
	public int generateId2()
	{
		List<Supplier> supplierList  = supplierRepository.findAll();
		
		if(supplierList.isEmpty()) {
			return 1;
		}
		else {
			int supplierCount = supplierList.size();
			Supplier lastEnteredSupplier = supplierList.get(supplierCount-1);
			int lastSupplierId = lastEnteredSupplier.getSupplierId();
			int newId = ++lastSupplierId;
			
			while(newId < lastSupplierId) {
				newId++;
			}
			
			return newId;
		}
		
		
	}
	
	@GetMapping(value = "isSupplierAvailable/{name}")
	public boolean isSupplierAvailable(@PathVariable String name) {
		name = name.toLowerCase();
		List<Supplier> supplierList = supplierRepository.findAll();
		
		for(Supplier supplier: supplierList) {
			String supName = supplier.getSupplierName().toLowerCase();
			if(supName.equals(name)) {
				System.out.println("Supplier name is unavailable. Returning false.");
				return false;
			}
		}
		System.out.println("Supplier name is available. Returning true.");
		return true;
	}
	
	@PutMapping(value="editSupplier/{id}")
	public Supplier updateSupplier(@RequestBody Supplier supplier, @PathVariable int id) {
		Optional<Supplier> s = supplierRepository.findById(id);
		//get method gives the type supplier here
		Supplier sup = s.get();
		
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(id);
		supplierHistory.setOldValue(sup.getSupplierName());
		supplierHistory.setNewValue(supplier.getSupplierName());
		supplierHistory.setUser("admin");
		System.out.println("Updating supplier id : " + id);
		return supplierRepository.save(supplier);
		
	}
	
	

}
