package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.observer.ParkingStrategy;

import java.util.Objects;

public class Vehicle {

	private String modelName;
	private String numberPlate;
	private String color;

	public Vehicle(String color) {
		this.color = color;
	}

	public Vehicle(String color, String modelName, String numberPlate) {
		this.color = color;
		this.numberPlate = numberPlate;
		this.modelName = modelName;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public String getModelName() {
		return modelName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vehicle vehicle = (Vehicle) o;
		return Objects.equals(modelName, vehicle.modelName) &&
				  Objects.equals(numberPlate, vehicle.numberPlate) &&
				  Objects.equals(color, vehicle.color) ;
	}

	public String getColor() {
		return color;
	}
}