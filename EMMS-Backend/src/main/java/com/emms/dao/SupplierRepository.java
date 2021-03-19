package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
