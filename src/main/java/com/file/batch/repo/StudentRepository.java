package com.file.batch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.file.batch.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


}
