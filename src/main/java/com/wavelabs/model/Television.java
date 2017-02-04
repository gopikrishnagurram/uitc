package com.wavelabs.model;

/**
 * Entity class to represent the television in relational database.
 * 
 * @author gopikrishnag
 *
 */
public class Television {

	private int id;
	private String name;
	private double cost;
	private Integer warranty;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(Integer warranty) {
		this.warranty = warranty;
	}

}
