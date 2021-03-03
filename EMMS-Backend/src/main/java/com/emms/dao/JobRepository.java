package com.emms.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.emms.model.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
	
	@Query( value = "SELECT * FROM jobs  WHERE status = 'pending' ", nativeQuery=true)
	public List<Job> queryPendingJobs(String status);
	
	@Query( value = "SELECT * FROM jobs  WHERE status = 'ongoing' ", nativeQuery=true)
	public List<Job> queryOngoingJobs(String status);
	
	@Query( value = "SELECT * FROM jobs  WHERE status = 'approvalwaiting' ", nativeQuery=true)
	public List<Job> queryApprovalWaitingJobs(String status);
	
	@Query( value = "SELECT * FROM jobs  WHERE status = 'completed' ", nativeQuery=true)
	public List<Job> queryCompletedJobs(String status);
	
	@Query( value = "SELECT * FROM jobs  WHERE status = 'rejected' ", nativeQuery=true)
	public List<Job> queryRejectedJobs(String status);

}
