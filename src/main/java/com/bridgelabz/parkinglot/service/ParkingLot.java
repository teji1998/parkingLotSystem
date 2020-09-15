package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkingSlot;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {

	private int capacity;
	private List<ParkingSlot> vehicles;
	private List<ParkingLotObserver> parkingLotObservers;
	int vehicleCount;

	public ParkingLot() {
		this.parkingLotObservers = new ArrayList<>();
		this.vehicles = new ArrayList();
	}

	public ParkingLot(int capacity) {
		this.capacity = capacity;
		this.parkingLotObservers = new ArrayList();
		this.vehicles = new ArrayList<>();
	}

	public void registerParkingLotObserver(ParkingLotObserver observer) {
		this.parkingLotObservers.add(observer);
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void parkingVehicle(Vehicle vehicle, Enum driverType, String attendantName) throws ParkingLotException {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle, driverType, attendantName);
		if (!this.vehicles.contains(null)) {
			for (ParkingLotObserver observer : parkingLotObservers) {
				observer.parkingFull();
			}
			throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot is Full");
		}
		if (isVehicleParked(vehicle)) {
			throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_EXISTS, "Vehicle is parked");
		}
		int emptySlot = getParkingSlot();
		this.vehicles.set(emptySlot, parkingSlot);
		vehicleCount ++;
	}

	public boolean isVehicleParked(Vehicle vehicle) {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		if (this.vehicles.contains(parkingSlot))
				return true;
			return false;
	}

	public boolean isVehicleNotParked(Vehicle vehicle) throws ParkingLotException {
		boolean present = this.vehicles.stream()
				  .filter(slot -> (vehicle) == slot.getVehicle())
				  .findFirst()
				  .isPresent();
		for (ParkingLotObserver observer : parkingLotObservers) {
			observer.parkingAvailable();
		}
		if (present)
			return true;
		return false;
	}

	public int initializeParkingLot() {
		IntStream.range(0, this.capacity).forEach(slots -> vehicles.add(null));
		return vehicles.size();
	}

	public ArrayList getSlotList() {
		ArrayList<Integer> emptySlots = new ArrayList();
		IntStream.range(0, capacity)
				  .filter(slot -> this.vehicles.get(slot).getVehicle() == null)
				  .forEach(slot -> emptySlots.add(slot));
		if (emptySlots.size() == 0) {
			for (ParkingLotObserver observer : parkingLotObservers) {
				observer.parkingFull();
			}
		}
		return emptySlots;
	}

	public int getParkingSlot() throws ParkingLotException {
		ArrayList<Integer> emptySlotList = getSlotList();
		for (int slot = 0; slot < emptySlotList.size(); slot++) {
			if (emptySlotList.get(0) == (slot)) {
				return slot;
			}
		}
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "PARKING IS FULL");
	}
	public int findingVehicle(Vehicle vehicle) throws ParkingLotException {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		if (this.vehicles.contains(parkingSlot))
			return this.vehicles.indexOf(parkingSlot);
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle is not present");
	}

	public boolean isTimeSet(Vehicle vehicle) {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		for (int i = 0; i < this.vehicles.size(); i++) {
			if (this.vehicles.get(i).time != null && this.vehicles.contains(parkingSlot))
				return true;
		}
		return false;
	}

	public int  getVehicleCount(){
		return vehicleCount;
	}

	public List<Integer> findOnField(String fieldName) {
		List<Integer> whiteVehicleList = new ArrayList<>();
		whiteVehicleList = this.vehicles.stream()
				  .filter(parkingSlot -> parkingSlot.getVehicle() != null)
				  .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equals(fieldName))
				  .map(parkingSlot -> parkingSlot.getSlot())
				  .collect(Collectors.toList());
		return whiteVehicleList;
	}

	public List<String> findParkedToyatoVehicleDetails(String color, String modelName) {
		List<String> fieldList = new ArrayList<>();
		List<String> fieldList1 = new ArrayList<>();
		fieldList = this.vehicles.stream()
				  .filter(parkingSlot -> parkingSlot.getVehicle() != null)
				  .filter(parkingSlot -> parkingSlot.getVehicle().getModelName().equals(modelName))
				  .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equals(color))
				  .map(parkingSlot -> (parkingSlot.getAttendantName())+"  "+(parkingSlot.getSlot())+"  "+(parkingSlot.vehicle.getNumberPlate()))
				  .collect(Collectors.toList());
		return fieldList;
	}
}