package com.bridgelabz.parkinglot.model;

public class Vehicle {

	private String numberPlate;
	private String modelName;
	private String color;

	public Vehicle(String color) {
		this.color = color;
	}

    public Vehicle(String color, String modelName, String numberPlate) {
			this.color=color;
			this.numberPlate=numberPlate;
			this.modelName = modelName;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public String getModelName() {
		return modelName;
	}

	public String getColor() {
		return color;
	}
}
