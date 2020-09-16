package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {
	
	private Enum type;
	public LocalDateTime time;
	public Vehicle vehicle;
	public String attendantName;
	private int slot;

	public void setSlot(int slot) {
		this.slot = slot;
	}
	public ParkingSlot(Vehicle vehicle, Enum driverType, String parkingAttendantName) {
		this.vehicle = vehicle;
		this.time = LocalDateTime.now();
		this.type = driverType;
		this.attendantName = parkingAttendantName;
	}

	public ParkingSlot(int slot) {
		this.slot=slot;
	}

	public Enum getType() {
		return type;
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
		return vehicle.equals(that.vehicle);
	}

	@Override
	public String toString() {
		return "ParkingSlot{" +
				  "slot=" + slot +
				  ", time=" + time +
				  ", vehicle=" + vehicle +
					", type=" + type +
				  ", attendantName='" + attendantName + '\'' +
				  '}';
	}
}
