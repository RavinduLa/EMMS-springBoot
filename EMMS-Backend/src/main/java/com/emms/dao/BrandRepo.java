package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.Brand;

public interface BrandRepo extends JpaRepository<Brand, Integer> {

}
