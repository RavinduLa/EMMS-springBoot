package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}
