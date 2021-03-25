package com.emms.controller;

import java.util.Date;
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

import com.emms.archiveDao.SupplierArchiveRepository;
import com.emms.archiveModels.SupplierArchive;
import com.emms.dao.EquipmentRepository;
import com.emms.model.Equipment;

@RestController
@RequestMapping("api/")
//@CrossOrigin(origins ="http://localhost:3000")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
//@CrossOrigin(origins ="^(https?://(?:.+\.)?mywebsite\.com(?::\d{1,5})?)$")
public class EquipmentController {
	
	
	
	@Autowired
	private EquipmentRepository equipmentrepo;
	@Autowired
	private SupplierArchiveRepository supplierArchiveRepository;
	
	@GetMapping("equipment")
	public List<Equipment> getAllEquipment(){
		
		String result = equipmentrepo.findAll().toString();
		System.out.println("Returning all equipment");
		System.out.println(result);
		List<Equipment> equipmentList = new ArrayList<>();
		equipmentList =  equipmentrepo.findAll();
		
		for(Equipment e : equipmentList) {
			int supplierId  = e.getSupplier();
			Date purchaseDate = e.getPurchaseDate();
			String supplierName = getSupplierNameForDate(supplierId, purchaseDate);
			e.setSupplierName(supplierName);
		}
		
		return equipmentList;
		
	}
	
	public String getSupplierNameForDate(int id, Date purchasedate) {
		List<SupplierArchive> suppliers = supplierArchiveRepository.findByOriginalId(id);
		String supplierName = "Yet to put";
		for(SupplierArchive s: suppliers) {
			Date createdDate = s.getCreatedDate();
			if(purchasedate.after(createdDate) || purchasedate.equals(createdDate)) {
				supplierName = s.getSupplieName();
			}
			else {
				System.out.println("Error: there is no created date before or on purchase date");
			}
		}
		
		return supplierName;
	}
	
	//pathvariable was int
	@GetMapping(value="/getEquipmentById/{id}")
	public Optional<Equipment>  getEquipmentById(@PathVariable long id) {
		Optional<Equipment> equipment = equipmentrepo.findById( id);

		System.out.println("Returning equipment for id");
		System.out.println(equipment.toString());
		
		return equipment;
		
	}
	
	
	//checking asset id availability
	//returns true if id is available
	//else returns null
	@GetMapping(value = "/checkIdAvailability/{id}")
	public boolean getIdAvailability (@PathVariable long id) {
		
		
		Optional<Equipment> e = equipmentrepo.findById(id);
		System.out.println("Equipment: " + e);
		
		if(e.isEmpty() ) {
			System.out.println("Return true");
			return true;
		}
		else {
			System.out.println("Return false");
			return false;
		}
		
	}
	
	@PostMapping("addEquipment")
	public Equipment addEquipment(@RequestBody Equipment newEquipment) {
		System.out.println("saving equipment");
		System.out.println(newEquipment.toString());
		return equipmentrepo.save(newEquipment);
		//equipmentrepo.save(newEquipment);
	}
	
	@PostMapping("updateEquipment")
	public Equipment updateEquipment(@RequestBody Equipment newEquipment) {
		System.out.println("updating equipment");
		return equipmentrepo.save(newEquipment);
		//equipmentrepo.save(newEquipment);
	}
	
	/*
	 * @DeleteMapping("deleteEquipment") //@PostMapping("deleteEquipment") public
	 * int deleteById(@RequestBody Equipment equipment) {
	 * //System.out.println("deleting equipment"+ equipment.getAssetId());
	 * //equipmentrepo.delete(equipment); int id = equipment.getAssetId();
	 * System.out.println("deleting equipment"+ id); equipmentrepo.deleteById(id);
	 * return id; }
	 */
	
	@DeleteMapping(value="/deleteEquipment/{id}")
	public long deleteById(@PathVariable long id) {
		System.out.println("deleting equipment " + id);
		equipmentrepo.deleteById(id);
		return id;
	}
	
	@GetMapping(value = "assetCount")
	public long getAssetCount() {
		long count = equipmentrepo.count();
		System.out.println("Equipment count: " + count);
		return count;
		
		
	}


}
