package com.emms.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emms.dao.JobRepository;
import com.emms.model.Job;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins ="*",allowedHeaders = "*",exposedHeaders = "*")
public class JobController {
	
	@Autowired
	private JobRepository jobRepository;
	
	@GetMapping(value = "job")
	public List<Job> getAllJobs (){
		List<Job> result = jobRepository.findAll();
		System.out.println("Returning all jobs: " + result.toString());
		return result;
	}
	
	@GetMapping(value= "pendingJobs")
	public List<Job> getPendingJobs() {
		List<Job> joblist = jobRepository.queryPendingJobs("pending");
		System.out.println("Returning pending job list: ");
		System.out.println(joblist.toString());
		
		return joblist;
	}
	
	@GetMapping(value= "pendingJobCount")
	public int getPendingJobCount() {
		List<Job> joblist = jobRepository.queryPendingJobs("pending");
		System.out.println("Returning pending job list: ");
		System.out.println(joblist.toString());
		
		return joblist.size();
	}
	
	@GetMapping(value=  "ongoingJobs")
	public List<Job> getOngoingJobs(){
		List<Job> joblist = jobRepository.queryOngoingJobs("ongoing");
		System.out.println("Returning ongoing job list: ");
		System.out.println(joblist.toString());
		
		return joblist;
	}
	
	@GetMapping(value=  "ongoingJobCount")
	public int getOngoingJobCount(){
		List<Job> joblist = jobRepository.queryOngoingJobs("ongoing");
		System.out.println("Returning ongoing job list: ");
		System.out.println(joblist.toString());
		
		return joblist.size();
	}
	
	@GetMapping(value=  "approvalWaitingJobs")
	public List<Job> getApprovalWaitingJobs(){
		List<Job> joblist = jobRepository.queryApprovalWaitingJobs("approvalWaiting");
		System.out.println("Returning approval waiting job list: ");
		System.out.println(joblist.toString());
		
		return joblist;
	}
	
	@GetMapping(value=  "approvalWaitingJobCount")
	public int getApprovalWaitingJobsCount(){
		List<Job> joblist = jobRepository.queryApprovalWaitingJobs("approvalWaiting");
		System.out.println("Returning approval waiting job list: ");
		System.out.println(joblist.toString());
		
		return joblist.size();
	}
	
	@GetMapping(value= "completedJobs")
	public List<Job> getCompletedJobs(){
		List<Job> joblist = jobRepository.queryCompletedJobs("completed");
		System.out.println("Returning completed job list: ");
		System.out.println(joblist.toString());
		
		return joblist;
	}
	
	@GetMapping(value= "completedJobCount")
	public int getCompletedJobCount(){
		List<Job> joblist = jobRepository.queryCompletedJobs("completed");
		System.out.println("Returning completed job list: ");
		System.out.println(joblist.toString());
		
		return joblist.size();
	}
	
	@GetMapping(value= "rejectedJobs")
	public List<Job> getRejectedJobs(){
		List<Job> joblist = jobRepository.queryRejectedJobs("rejected");
		System.out.println("Returning rejected job list: ");
		System.out.println(joblist.toString());
		
		return joblist;
	}
	
	@GetMapping(value= "rejectedJobCount")
	public int getRejectedJobCount(){
		List<Job> joblist = jobRepository.queryRejectedJobs("rejected");
		System.out.println("Returning rejected job list: ");
		System.out.println(joblist.toString());
		
		return joblist.size();
	}
	
	@PostMapping(value= "addJob")
	public synchronized Job addJob (@RequestBody Job job) {
		job.setJobId(this.generateId());
		job.setStatus("pending");
		System.out.println("Saving job : "+ job.toString());
		return jobRepository.save(job);
	}
	
	@GetMapping("/getJobById/{id}")
	public Optional<Job> getJobById(@PathVariable int id) {
		Optional<Job> job = jobRepository.findById(id);
		return job;
		
	}
	//@GetMapping(value = "getNewId")
	public int generateId() {
		List<Job> alljobs = jobRepository.findAll();
		int numberOfJobs = alljobs.size();
		int newId = 0;
		
		
		Job lastEnteredJob = alljobs.get(numberOfJobs-1);
		int lastJobId = lastEnteredJob.getJobId();
		
		newId = ++lastJobId;
		if(alljobs.isEmpty()) {
			return 1;
		}
		else {
			
			while(newId < lastJobId) {
				newId++;
			}
			return newId;
		}
	}
	
	
}
