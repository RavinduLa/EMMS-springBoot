package com.emms.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.emms.archiveDao.SupplierArchiveRepository;
import com.emms.archiveModels.DepartmentArchive;
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
	@Autowired
	private DepartmentArchiveRepository departmentArchiveRepository; 
	
	@GetMapping("equipment")
	public List<Equipment> getAllEquipment(){
		
		String result = equipmentrepo.findAll().toString();
		System.out.println("Returning all equipment");
		System.out.println(result);
		List<Equipment> equipmentList = new ArrayList<>();
		equipmentList =  equipmentrepo.findAll();
		
		for(Equipment e : equipmentList) {
			int supplierId  = e.getSupplier();
			int departmentId = e.getDepartment();
			Date purchaseDate = e.getPurchaseDate();
			String supplierName = getSupplierNameForDate(supplierId, purchaseDate);
			String departmentName = getDepartmentNameForDate(departmentId, purchaseDate);
			e.setSupplierName(supplierName);
			e.setDepartmentName(departmentName);
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
	
	public String getDepartmentNameForDate(int id, Date purchasedate) {
		List<DepartmentArchive> departments = departmentArchiveRepository.findByOriginalId(id);
		String departmentName = "Yet to put";
		for (DepartmentArchive d: departments) {
			Date createdDate = d.getCreatedDate();	
			if(purchasedate.after(createdDate) || purchasedate.equals(createdDate)) {
				departmentName = d.getDepartmentName();
			}
			else {
				System.out.println("Error: there is no created date for department before or on purchase date");
			}
		}
		
		return departmentName;
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
	public synchronized boolean getIdAvailability (@PathVariable long id) {
		
		
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
	public synchronized Equipment addEquipment(@RequestBody Equipment newEquipment) {
		System.out.println("saving equipment");
		System.out.println(newEquipment.toString());
		return equipmentrepo.save(newEquipment);
		//equipmentrepo.save(newEquipment);
	}
	
	@PostMapping("updateEquipment")
	public synchronized Equipment updateEquipment(@RequestBody Equipment newEquipment) {
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
	public synchronized long deleteById(@PathVariable long id) {
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
	
	//returns the equipment list based on the given supplier id
	@GetMapping(value = "getEquipmentForSupplier/{id}")
	public List<Equipment> getEquipmentBySupplier (@PathVariable int  id){
		List<Equipment> equipmentList = equipmentrepo.findBySupplier(id);
		System.out.println("Returning equipments for supplier,  "+ id+ " : ");
		System.out.println(equipmentList.toString());
		return equipmentList;
	}

	//returns equipment based on the given location
	@GetMapping(value = "getEquipmentForLocation/{location}")
	public List<Equipment> getEquipmentForLocation(@PathVariable String location){
		
		List<Equipment> equipmentList = equipmentrepo.findByLocation(location.toLowerCase());
		System.out.println("Returning equipments for location,  "+ location+ " : ");
		System.out.println(equipmentList.toString());
		return equipmentList;
	}
	
	//returns equipment based on the given department
	@GetMapping(value = "getEquipmentForDepartment/{dept}")
	public List<Equipment> getEquipmentForDepartment(@PathVariable int dept){
		
		List<Equipment> equipmentList = equipmentrepo.findByDepartment(dept);
		System.out.println("Returning equipments for department,  "+ dept+ " : ");
		System.out.println(equipmentList.toString());
		return equipmentList;
	}
	
	//returns equipment list based on the passed location and the department.
	//both department and equipment must match for an equipment to be returned.
	@GetMapping(value = "getEquipmentForLocationAndDepartment/{location}/{department}")
	public List<Equipment> getEquipmentForLocationAndDepartment(@PathVariable String location, 
			@PathVariable int department	){
		List<Equipment> lEquipmentList = equipmentrepo.findByLocation(location);
		List<Equipment> dEquipmentList = equipmentrepo.findByDepartment(department);
		List<Equipment> matchingEquipmentList = new ArrayList<Equipment>();
		for(Equipment e : lEquipmentList) {
			
			for(Equipment r : dEquipmentList) {
				if(e.getAssetId() == r.getAssetId()) {
					matchingEquipmentList.add(e);
				}
			}
		}
		
		if(matchingEquipmentList.isEmpty()) {
			System.out.println("The matching list is empty");
		}
		else {
			System.out.println("Returing the matching list : " + matchingEquipmentList.toString());
		}
		
		return matchingEquipmentList;
	}
	
	//returns equipment for the given asset id 
	@GetMapping(value = "getEquipmentForAssetId/{assetId}")
	public Equipment getEquipmentForAssetId(@PathVariable long assetId){
		Equipment equipment = equipmentrepo.findByAssetId(assetId);
		
		
		try {
			System.out.println("Returning equipment : " + equipment.toString() );
			return equipment;
		}catch(NullPointerException n){
			System.out.println("Equipment is  null. The asset id is not found.");
			return new Equipment();
		}
		
	}
	
	//returns equipment for the given serial number.
	@GetMapping(value = "getEquipmentForSerialNumber/{serialNumber}")
	public Equipment getEquipmentForSerialNumber(@PathVariable String serialNumber) {
		Equipment equipment = equipmentrepo.findBySerialNumber(serialNumber);
		
		try {
			System.out.println("Returning equipment : " + equipment.toString() );
			return equipment;
		}catch(NullPointerException n) {
			System.out.println("Equipment is  null. The asset id is not found.");
			return new Equipment();
		}
	}
	
	//returns equipment purchased between passed dates
	//date formats must be in the format 'yyy-MM-dd'
	@GetMapping(value = "getEquipmentForTimePeriod/{s}/{en}")
	public List<Equipment> getEquipmentForTimePeriod (@PathVariable String s, @PathVariable String en){
		
		Date start = new Date();
		try {
			start = new SimpleDateFormat("yyyy-MM-dd").parse(s);
		} catch (ParseException e1) {
			System.err.println("Error whe parsing start string to date");
			e1.printStackTrace();
		}
		Date end = new Date();
		try {
			end = new SimpleDateFormat("yyyy-MM-dd").parse(en);
		}catch (ParseException e1) {
			System.err.println("Error whe parsing end string to date");
			e1.printStackTrace();
		}
		
		
		
		List<Equipment> equipmentList = equipmentrepo.findAll();
		List<Equipment> sortedEquipment = new ArrayList<Equipment>();
		
		for(Equipment e: equipmentList) {
			Date purchaseDate = e.getPurchaseDate();
			//adds equipment purchased between start and end
			if( (purchaseDate.after(start) && purchaseDate.before(end)) ) {
				sortedEquipment.add(e);
			}
		}
		
		return sortedEquipment;
	}
	
	@GetMapping(value = "getUnderWarrantyEquipment")
	public List<Equipment> getUnderWarrantyEquipment(){
		Date currentDate = new Date();
		List<Equipment> allEquipment = equipmentrepo.findAll();
		List<Equipment> underWarrantyList = new ArrayList<Equipment>();
		
		for(Equipment e : allEquipment) {
			int warrantyMonths = e.getWarrantyMonths();
			Date purchaseDate = e.getPurchaseDate();
			
			Date warrantyEndDate = calculateWarrantyEndDate(purchaseDate, warrantyMonths);
			if(currentDate.before(warrantyEndDate)) {
				underWarrantyList.add(e);
			}
			
		}
		
		return underWarrantyList;
	}
	
	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}
	
	public static Date toDate(Calendar calender) {
		Date date = new Date();
		date = calender.getTime();
		return date;
	}
	
	public static Date calculateWarrantyEndDate (Date purchaseDate, int warrantyMonths) {
		Calendar calenderPurchaseDate, calenderWarrantyEndDate ;
		calenderPurchaseDate = toCalendar(purchaseDate);
		System.out.println("Calender type date: " + calenderPurchaseDate.toString());
		calenderWarrantyEndDate  = calenderPurchaseDate;
		calenderWarrantyEndDate.add(Calendar.MONTH, warrantyMonths);
		Date warrantyEndDate = toDate(calenderWarrantyEndDate);
		
		System.out.println("Warranty end date: " + warrantyEndDate);
		return warrantyEndDate;
				
	}

}
