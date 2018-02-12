package com.example.twise.employee;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	List<Employee> findById(@Param("id") String id);
	List<Employee> findByLastName(@Param("lastName") String lastName);
}
