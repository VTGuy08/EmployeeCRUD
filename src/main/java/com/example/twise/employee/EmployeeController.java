package com.example.twise.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class EmployeeController {
	private final static String URI_EMPLOYEES = "/employees";

	@Autowired
	private EmployeeRepository repo;

	@RequestMapping(value = URI_EMPLOYEES + "/create", method = RequestMethod.POST)
	public @ResponseBody HttpEntity<String> createEmployee(@RequestBody Employee employee) {
		repo.save(employee);
		return new ResponseEntity<String>(employee.getId(), HttpStatus.OK);
	}

	@RequestMapping(value = URI_EMPLOYEES + "/getEmployeeById", method = RequestMethod.GET)
	public @ResponseBody HttpEntity<List<Employee>> getEmployee(@RequestBody String id) {
		List<Employee> list = repo.findById(id);
		return new ResponseEntity<List<Employee>>(list, (list.size() == 0) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@RequestMapping(value = URI_EMPLOYEES + "/getEmployees", method = RequestMethod.GET)
	public @ResponseBody HttpEntity<List<Employee>> getEmployees() {
		return new ResponseEntity<List<Employee>>(repo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = URI_EMPLOYEES + "/updateEmployeeById", method = RequestMethod.PUT)
	public @ResponseBody HttpEntity<String> updateEmployeeById(@RequestBody EmployeeUpdate obj) {
		if (repo.findById(obj.getId()).size() > 0) {
			repo.save(obj.getEmployee());
			return new ResponseEntity<String>("Update success!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Update failed: Record not found for that id.", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = URI_EMPLOYEES + "/deleteEmployeeById", method = RequestMethod.DELETE)
	public @ResponseBody HttpEntity<String> deleteEmployee(@RequestBody String id) {
		List<Employee> employeeList = repo.findById(id);
		if (repo.findById(id).size() > 0) {
			repo.delete(employeeList.get(0));
			return new ResponseEntity<String>("Delete success!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Delete success!", HttpStatus.NOT_FOUND);
	}
}
