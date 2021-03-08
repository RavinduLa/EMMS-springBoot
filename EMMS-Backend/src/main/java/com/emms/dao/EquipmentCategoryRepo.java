package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.EquipmentCategories;

public interface EquipmentCategoryRepo extends JpaRepository<EquipmentCategories, Integer> {

}
