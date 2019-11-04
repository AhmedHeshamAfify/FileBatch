package com.file.batch.controller;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.file.batch.model.Student;
import com.file.batch.repo.StudentRepository;
import com.file.batch.service.StudentService;

@RestController
public class BatchController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job fileBatchJob;

	@Autowired
	StudentService studentService;


	@RequestMapping("/")
	public String runBatch() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date())
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		JobExecution jobExecution = jobLauncher.run(fileBatchJob, jobParameters);
		
		return "Batch Job Status is : " + jobExecution.getStatus().toString();
	}

	@RequestMapping("/showInsertedStudents")
	public List<Student> showStudents() throws Exception {

		return studentService.getAllStudents();
	}
}
