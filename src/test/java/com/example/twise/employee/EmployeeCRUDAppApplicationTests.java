package com.example.twise.employee;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.twise.employee.Employee;
import com.example.twise.employee.EmployeeRepository;
import com.example.twise.employee.EmployeeUpdate;
import com.example.twise.employee.PositionCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeCRUDAppApplicationTests {
	/*
	 * For these tests, install MongoDB and run: mongod -dbpath C:\Path\To\Data
	 * 
	 * This will start the database.
	 */

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	EmployeeRepository repo;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	// Dummy Data
	private Employee emp1 = createEmployeeStub("John", 'P', "Doe", PositionCategory.ProgramManager);
	private Employee emp2 = createEmployeeStub("Jane", 'M', "Doe", PositionCategory.Executive);
	private List<Employee> employees = Arrays.asList(emp1, emp2);

	/**
	 * Initialize the MongoDB by deleting its contents and then adding our dummy
	 * data. All tests operate on the assumption that this database contains two
	 * employees: John and Jane Doe.
	 */
	@Before
	public void init() {
		repo.deleteAll();
		repo.insert(employees);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	/*
	 * Commented out so that we can examine the database after running tests. We can
	 * use URLs to test some methods:
	 * http://localhost:8080/employee/search/findById?id=5a7a789a8baddc3088dd2c03
	 * http://localhost:8080/employee/search
	 */
	// @After
	// public void tearDown() {
	// repoMock.deleteAll();
	// }

	/**
	 * Generate some dummy data
	 * 
	 * @param fName
	 * @param mI
	 * @param lName
	 * @param pos
	 * @return
	 */
	private Employee createEmployeeStub(String fName, char mI, String lName, PositionCategory pos) {
		Employee e = new Employee(fName, mI, lName);
		e.setAddress1("123 Employee Way");
		e.setAddress2("Suite 2001");
		e.setCity("Smallville");
		e.setState("Kansas");
		e.setZipCode(20148);
		e.setDateHired(new Date());
		e.setEmailAddress(fName + "." + lName + "@gmail.com");
		e.setPhoneNumber("123-456-7890");
		e.setPositionCat(pos);
		return e;
	}

	/**
	 * Convenience method to retrieve all employees for debugging.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private ResultActions getAllEmployees() throws Exception {
		return mockMvc.perform(get("/employees")).andExpect(status().isOk());
	}

	/**
	 * Convenience method to retrieve all employees for debugging.
	 * 
	 * @param result
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	private String getResponseContent(ResultActions result) throws UnsupportedEncodingException {
		return result.andReturn().getResponse().getContentAsString();
	}

	@Test
	public void testAddEmployee() throws Exception {
		Employee jack = createEmployeeStub("Jack", 'T', "Sparrow", PositionCategory.Indirect);
		ObjectMapper mapper = new ObjectMapper();

		String jackJson = mapper.writeValueAsString(jack);
		ResultActions result = mockMvc
				.perform(post("/employees/create").contentType(MediaType.APPLICATION_JSON).content(jackJson));

		result.andExpect(content().string(is("\"" + jack.getId() + "\""))).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void testGetEmployeeById() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		String jsonId = mapper.writeValueAsString(emp1.getId());

		ResultActions result = mockMvc.perform(
				get("/employees/getEmployeeById").contentType(MediaType.APPLICATION_JSON).content(jsonId.toString()))
				.andExpect(status().is2xxSuccessful());

		result.andExpect(jsonPath("$.[0].id", is(emp1.getId())))
				.andExpect(jsonPath("$.[0].firstName", is(emp1.getFirstName())));
	}

	@Test
	public void testGetAllEmployees() throws Exception {
		// Uses the CRUD Rest Service Interface
		ResultActions result = mockMvc.perform(get("/employees")).andExpect(status().is2xxSuccessful());
		result.andExpect(content().contentType("application/hal+json;charset=UTF-8"))
				.andExpect(jsonPath("$._embedded.employees", hasSize(2)))
				.andExpect(jsonPath("$._embedded.employees[0].firstName", is("John")))
				.andExpect(jsonPath("$._embedded.employees[0].middleInitial", is("P")))
				.andExpect(jsonPath("$._embedded.employees[1].firstName", is("Jane")))
				.andExpect(jsonPath("$._embedded.employees[1].middleInitial", is("M")));

		// Uses the REST implementation of EmployeeController
		ResultActions result2 = mockMvc.perform(get("/employees/getEmployees")).andExpect(status().is2xxSuccessful());
		result2.andExpect(content().contentType("application/hal+json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$.[0].firstName", is("John")))
				.andExpect(jsonPath("$.[0].middleInitial", is("P")))
				.andExpect(jsonPath("$.[1].firstName", is("Jane")))
				.andExpect(jsonPath("$.[1].middleInitial", is("M")));
	}

	@Test
	public void testUpdateEmployeeSuccess() throws Exception {
		//
		// Construct a new employee to add to the repository
		Employee jack = createEmployeeStub("Jack", 'T', "Sparrow", PositionCategory.Indirect);
		ObjectMapper mapper = new ObjectMapper();
		String jackJson = mapper.writeValueAsString(jack);

		// Make a call to the REST service to add 'Jack' to the database
		ResultActions resultCreate = mockMvc
				.perform(post("/employees/create").contentType(MediaType.APPLICATION_JSON).content(jackJson));

		resultCreate.andExpect(status().is2xxSuccessful());

		String id = resultCreate.andReturn().getResponse().getContentAsString().replace("\"", "");
		String jsonId = mapper.writeValueAsString(id);

		// Make an update to 'Jack'
		jack.setPositionCat(PositionCategory.Director);

		// Sanity check that we did in fact change 'Jack'
		assertEquals(PositionCategory.Director, jack.getPositionCat());

		EmployeeUpdate jackUpdated = new EmployeeUpdate(id, jack);
		String jackJsonUpdated = mapper.writeValueAsString(jackUpdated);

		// Perform the Update, expect success
		ResultActions resultUpdate = mockMvc.perform(
				put("/employees/updateEmployeeById").contentType(MediaType.APPLICATION_JSON).content(jackJsonUpdated));

		resultUpdate.andExpect(status().is2xxSuccessful());

		// Retrieve the record and verify the update took
		ResultActions resultRetrieve = mockMvc.perform(
				get("/employees/getEmployeeById").contentType(MediaType.APPLICATION_JSON).content(jsonId.toString()))
				.andExpect(status().isOk());

		resultRetrieve.andExpect(jsonPath("$.[0].id", is(id)))
				.andExpect(jsonPath("$.[0].positionCat", is(jack.getPositionCat().toString())));
	}

	@Test
	public void testUpdateEmployeeFail() throws Exception {
		//
		// Construct a new employee to add to the repository
		Employee jack = createEmployeeStub("Jack", 'T', "Sparrow", PositionCategory.Indirect);
		ObjectMapper mapper = new ObjectMapper();
		String jackJson = mapper.writeValueAsString(jack);

		// Make a call to the REST service to add 'Jack' to the database
		ResultActions resultCreate = mockMvc
				.perform(post("/employees/create").contentType(MediaType.APPLICATION_JSON).content(jackJson));

		resultCreate.andExpect(status().is2xxSuccessful());

		String id = resultCreate.andReturn().getResponse().getContentAsString().replace("\"", "");

		// Prematurely remove 'Jack' so that the update fails.
		repo.delete(jack);

		// Make an update to 'Jack'
		jack.setPositionCat(PositionCategory.Director);

		// Sanity check that we did in fact change 'Jack'
		assertEquals(PositionCategory.Director, jack.getPositionCat());

		EmployeeUpdate jackUpdated = new EmployeeUpdate(id, jack);
		String jackJsonUpdated = mapper.writeValueAsString(jackUpdated);

		// Perform the Update, expect success
		ResultActions resultUpdate = mockMvc.perform(
				put("/employees/updateEmployeeById").contentType(MediaType.APPLICATION_JSON).content(jackJsonUpdated));

		resultUpdate.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteEmployeeSuccess() throws Exception {
		Employee jack = createEmployeeStub("Jack", 'T', "Sparrow", PositionCategory.Indirect);
		ObjectMapper mapper = new ObjectMapper();

		String jackJson = mapper.writeValueAsString(jack);
		ResultActions resultCreate = mockMvc
				.perform(post("/employees/create").contentType(MediaType.APPLICATION_JSON).content(jackJson));
		resultCreate.andExpect(content().string(is("\"" + jack.getId() + "\""))).andExpect(status().is2xxSuccessful());

		String idJson = mapper.writeValueAsString(jack.getId());
		ResultActions resultDelete = mockMvc.perform(
				delete("/employees/deleteEmployeeById").contentType(MediaType.APPLICATION_JSON).content(idJson));
		resultDelete.andExpect(status().is2xxSuccessful());

		mockMvc.perform(get("/employees/getEmployeeById").contentType(MediaType.APPLICATION_JSON).content(idJson))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteEmployeeFail() throws Exception {
		Employee jack = createEmployeeStub("Jack", 'T', "Sparrow", PositionCategory.Indirect);
		ObjectMapper mapper = new ObjectMapper();

		String jackJson = mapper.writeValueAsString(jack);
		ResultActions resultCreate = mockMvc
				.perform(post("/employees/create").contentType(MediaType.APPLICATION_JSON).content(jackJson));
		resultCreate.andExpect(content().string(is("\"" + jack.getId() + "\""))).andExpect(status().is2xxSuccessful());

		// Prematurely delete jack from the repo
		repo.delete(jack);

		String idJson = mapper.writeValueAsString(jack.getId());
		ResultActions resultDelete = mockMvc.perform(
				delete("/employees/deleteEmployeeById").contentType(MediaType.APPLICATION_JSON).content(idJson));
		resultDelete.andExpect(status().isNotFound());
	}

	@Test
	public void testfindByLastNameSuccess() throws Exception {
		ResultActions resultCreate = mockMvc.perform(get("/employees/search/findByLastName?lastName=Doe"));
		resultCreate.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$._embedded.employees", hasSize(2)))
				.andExpect(jsonPath("$._embedded.employees[0].firstName", is("John")))
				.andExpect(jsonPath("$._embedded.employees[0].middleInitial", is("P")))
				.andExpect(jsonPath("$._embedded.employees[1].firstName", is("Jane")))
				.andExpect(jsonPath("$._embedded.employees[1].middleInitial", is("M")));
	}

	@Test
	public void testfindByLastNameFail() throws Exception {
		ResultActions resultCreate = mockMvc.perform(get("/employees/search/findByLastName?lastName=Smithers"));
		resultCreate.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$._embedded.employees", hasSize(0)));
	}

}
