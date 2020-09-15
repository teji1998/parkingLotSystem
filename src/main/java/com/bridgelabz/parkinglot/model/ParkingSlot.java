package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {
	private int slot;
	private Enum driverType;
	public LocalDateTime time;
	public Vehicle vehicle;
	String attendantName;

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public ParkingSlot(Vehicle vehicle, Enum driverType, String parkingAttendantName) {
		this.vehicle = vehicle;
		this.time = LocalDateTime.now();
		this.driverType = driverType;
		this.attendantName = parkingAttendantName;
	}
	public ParkingSlot(int slot) {
		this.slot=slot;
	}
	public int getSlot() {
		return slot;
	}

	public LocalDateTime getTime() {
		return time;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public String getAttendantName() {

		return attendantName;
	}
	public ParkingSlot(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParkingSlot that = (ParkingSlot) o;
		return Objects.equals(vehicle, that.vehicle);
	}

}

