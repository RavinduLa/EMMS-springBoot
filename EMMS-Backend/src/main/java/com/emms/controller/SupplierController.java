//supplier controller

package com.emms.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emms.archiveDao.SupplierArchiveRepository;
import com.emms.archiveModels.SupplierArchive;
import com.emms.dao.SupplierHistoryRepository;
import com.emms.dao.SupplierRepository;
import com.emms.model.Supplier;
import com.emms.model.SupplierHistory;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class SupplierController {

	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	SupplierHistoryRepository supplierHistoryRepository;
	@Autowired
	SupplierArchiveRepository supplierArchiveRepository;

	@RequestMapping(value = "allSuppliers")
	public List<Supplier> getAllSuppliers() {
		List<Supplier> supplierList = supplierRepository.findAll();
		List<Supplier> activeSupplierList = new ArrayList<>() ;
		for(Supplier s: supplierList) {
			String status = s.getStatus();
			if(status.equals("active")) {
				activeSupplierList.add(s);
			}
		}
		return activeSupplierList;
	}

	@PostMapping(value = "addSupplier")
	public synchronized Supplier addSupplier(@RequestBody Supplier supplier) {
		Supplier sup = new Supplier();
		int id = generateId2();
		System.out.println("Setting supplier id: " + id);
		supplier.setSupplierId(id);
		supplier.setStatus("active");
		sup = supplierRepository.save(supplier);
		System.out.println("adding supplier: " + sup);
		
		
		SupplierArchive sa = new SupplierArchive();
		sa.setOriginalId(supplier.getSupplierId());
		sa.setSupplieName(supplier.getSupplierName());
		sa.setPhone(supplier.getPhone());
		sa.setEmail(supplier.getEmail());
		
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 LocalDateTime now = LocalDateTime.now();
		 
		 int year = now.getYear();
		 int month = now.getMonthValue();
		 int day = now.getDayOfMonth();

		Date currentDate = new Date();
		currentDate.setYear(year);
		currentDate.setMonth(month);
		currentDate.setDate(day);
		
		System.out.println("Local dateTime: "+ now.toString());
		System.out.println("Current date: "+ currentDate.toString());*/
		
		java.util.Date date = new java.util.Date();
		
		System.out.println("Date: " + date);
		sa.setCreatedDate(date);
		sa.setStatus("active");
		supplierArchiveRepository.save(sa);
		
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

		if (supplierCount == 0) {
			newId = 1;
		} else {
			newId = ++supplierCount;
			Optional<Supplier> lastRecord = supplierRepository.findById(supplierCount - 1);
			Supplier sup = lastRecord.get();
			int lastId = sup.getSupplierId();

			newId = ++lastId;
			while (newId < lastId) {
				newId++;
			}

		}
		System.out.println("Returning new id: " + newId);
		return newId;
	}

	public int generateId2() {
		List<Supplier> supplierList = supplierRepository.findAll();

		if (supplierList.isEmpty()) {
			return 1;
		} else {
			int supplierCount = supplierList.size();
			Supplier lastEnteredSupplier = supplierList.get(supplierCount - 1);
			int lastSupplierId = lastEnteredSupplier.getSupplierId();
			int newId = ++lastSupplierId;

			while (newId < lastSupplierId) {
				newId++;
			}

			return newId;
		}

	}

	@GetMapping(value = "isSupplierAvailable/{name}")
	public boolean isSupplierAvailable(@PathVariable String name) {
		name = name.toLowerCase();
		List<Supplier> supplierList = supplierRepository.findAll();

		for (Supplier supplier : supplierList) {
			String supName = supplier.getSupplierName().toLowerCase();
			if (supName.equals(name)) {
				System.out.println("Supplier name is unavailable. Returning false.");
				return false;
			}
		}
		System.out.println("Supplier name is available. Returning true.");
		return true;
	}

	@PutMapping(value = "editSupplier/{id}")
	public Supplier updateSupplier(@RequestBody Supplier supplier, @PathVariable int id) {
		Optional<Supplier> s = supplierRepository.findById(id);
		// get method gives the type supplier here
		Supplier sup = s.get();

		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(id);
		supplierHistory.setOldValue(sup.getSupplierName());
		supplierHistory.setNewValue(supplier.getSupplierName());
		supplierHistory.setUser("admin");
		System.out.println("Updating supplier id : " + id);
		return supplierRepository.save(supplier);

	}

	@DeleteMapping(value = "deleteSupplierById/{id}")
	public Supplier deleteSupplierById(@PathVariable int id) {
		Optional<Supplier> supplierOp = supplierRepository.findById(id);
		Supplier supplier = supplierOp.get();
		SupplierArchive sa = new SupplierArchive();
		
		//SupplierArchive saPrev = new SupplierArchive();
		List<SupplierArchive> saList = new ArrayList<>();
		saList = supplierArchiveRepository.findByOriginalId(supplier.getSupplierId());
		
		for(SupplierArchive sup: saList) {
			sup.setStatus("history");
			supplierArchiveRepository.save(sup);
		}
		
		sa.setOriginalId(supplier.getSupplierId());
		sa.setSupplieName(supplier.getSupplierName());
		sa.setPhone(supplier.getPhone());
		sa.setEmail(supplier.getEmail());

	
		java.util.Date currentDate = new Date();
		
		sa.setCreatedDate(currentDate);
		sa.setStatus("deleted");
		
		supplierArchiveRepository.save(sa);
		supplier.setStatus("deleted");
		supplierRepository.save(supplier);
		
		return supplier;
		
	}

}
