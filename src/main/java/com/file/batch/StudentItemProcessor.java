package com.file.batch;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.file.batch.model.Student;

@Component
public class StudentItemProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student item) throws Exception {
		LocalDate date = LocalDate.now();
		item.setDateOfBirth(date.minusYears(item.getAge()).with(TemporalAdjusters.firstDayOfYear()));
		return item;
	}

}
