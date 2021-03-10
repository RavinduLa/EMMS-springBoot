package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.Model;

public interface ModelRepo extends JpaRepository<Model, Integer> {

}
