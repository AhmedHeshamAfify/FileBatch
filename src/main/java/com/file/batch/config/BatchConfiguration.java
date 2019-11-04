package com.file.batch.config;

import java.io.Reader;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.file.batch.JobCompletionNotificationListener;
import com.file.batch.StudentItemProcessor;
import com.file.batch.model.Student;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	EntityManagerFactory emf;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemWriter<Student> writer() {
		JpaItemWriter<Student> writer = new JpaItemWriter<Student>();
		writer.setEntityManagerFactory(emf);
		return writer;
	}

	@Bean
	public FlatFileItemReader<Student> reader() {
		return new FlatFileItemReaderBuilder<Student>().name("studentItemReader")
				.resource(new ClassPathResource("students.csv")).delimited()
				.names(new String[] { "firstName", "lastName", "GPA", "age" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {
					{
						setTargetType(Student.class);
					}
				}).build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Student, Student>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean
	public StudentItemProcessor processor() {
		return new StudentItemProcessor();
	}

	@Bean
	public Job importStudentJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importStudentJob").incrementer(new RunIdIncrementer()).listener(listener)
				.start(step1).build();
	}

}
