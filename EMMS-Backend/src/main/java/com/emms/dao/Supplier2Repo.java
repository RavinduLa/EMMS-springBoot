//supplier2 repo
package com.emms.dao;

import java.util.Date;
//import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emms.model.Supplier2;

public interface Supplier2Repo extends JpaRepository<Supplier2, Integer> {
	
	@Query(value = "SELECT * FROM supplier2 WHERE original_id =:originalId and date =:date", nativeQuery=true)
	public List<Supplier2> getAllRecordsForId(
			@Param("originalId")Integer originalId,
			@Param("date") Date date);

}
