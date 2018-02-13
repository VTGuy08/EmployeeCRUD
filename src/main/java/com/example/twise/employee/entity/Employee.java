package com.example.twise.employee.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * The purpose of this class is to represent the Employee record in
 * the MongoDB.
 * 
 * @author Taylor Wise
 *
 */
public class Employee {
	@Id private String id;
	private String firstName;
	private char middleInitial;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private PositionCategory positionCat;
	private Date dateHired;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private int zipCode;
	private boolean activeFlag;
	
	public Employee() {
		/* required for json construction */
	}
	
	public Employee(String firstName, char middleInitial, String lastName) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.id = ObjectId.get().toString(); // Generate a unique id for us
	}
	
	/**
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the middleInitial
	 */
	public char getMiddleInitial() {
		return middleInitial;
	}
	/**
	 * @param middleInitial the middleInitial to set
	 */
	public void setMiddleInitial(char middleInitial) {
		this.middleInitial = middleInitial;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the positionCat
	 */
	public PositionCategory getPositionCat() {
		return positionCat;
	}
	/**
	 * @param positionCat the positionCat to set
	 */
	public void setPositionCat(PositionCategory positionCat) {
		this.positionCat = positionCat;
	}
	/**
	 * @return the dateHired
	 */
	public Date getDateHired() {
		return dateHired;
	}
	/**
	 * @param dateHired the dateHired to set
	 */
	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zipCode
	 */
	public int getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the activeFlag
	 */
	public boolean isActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
}
