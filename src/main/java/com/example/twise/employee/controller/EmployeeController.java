package com.example.twise.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.twise.employee.data.EmployeeRepository;
import com.example.twise.employee.entity.Employee;

@RequestMapping("/employees")
@RepositoryRestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository repo;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody HttpEntity<List<Employee>> getEmployees() {
		return new ResponseEntity<List<Employee>>(repo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity<String> createEmployee(@RequestBody Employee employee) {
		repo.save(employee);
		return new ResponseEntity<String>(employee.getId(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody HttpEntity<Employee> getEmployee(@PathVariable("id") String id) {
		Employee employee = repo.findOne(id); //repo.findById(id);
		
		return new ResponseEntity<Employee>(employee, (employee == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity<String> updateEmployeeById(@PathVariable("id") String id, @RequestBody Employee obj) {
		if (repo.findById(id).size() > 0) {
			repo.save(obj);
			return new ResponseEntity<String>("Update success!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Update failed: Record not found for that id.", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value =  "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody HttpEntity<String> deleteEmployee(@PathVariable("id") String id) {
		List<Employee> employeeList = repo.findById(id);
		if (repo.findById(id).size() > 0) {
			repo.delete(employeeList.get(0));
			return new ResponseEntity<String>("Delete success!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Delete success!", HttpStatus.NOT_FOUND);
	}
}
