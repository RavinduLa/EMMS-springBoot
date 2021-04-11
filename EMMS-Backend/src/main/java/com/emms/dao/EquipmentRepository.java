package com.emms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

	List<Equipment> findBySupplier(int id);
	List<Equipment> findByLocation(String location);
	List<Equipment> findByDepartment(int department);
	Equipment findByAssetId(long assetId);
	Equipment findBySerialNumber(String serialNumber);
}
