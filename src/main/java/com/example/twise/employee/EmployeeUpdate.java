package com.example.twise.employee;

/***
 * The purpose of this class is to house the updated employee record as well as
 * the id to use for the update.
 * 
 * @author Taylor Wise
 *
 */
public class EmployeeUpdate {

	private Employee employee; // The updates
	private String id; // Id of the entry to assign the updates

	public EmployeeUpdate() {
		/* Required for json construction */
	}

	public EmployeeUpdate(String id, Employee employee) {
		// The Employee class must have this id value
		// for the database to successfully update.
		employee.setId(id);

		this.id = id;
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
