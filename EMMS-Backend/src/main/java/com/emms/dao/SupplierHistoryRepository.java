//supplier history repo
package com.emms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emms.model.SupplierHistory;

public interface SupplierHistoryRepository extends JpaRepository<SupplierHistory, Integer> {

}
