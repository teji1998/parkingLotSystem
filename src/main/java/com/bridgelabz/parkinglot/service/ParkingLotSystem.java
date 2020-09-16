package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.ParkingStrategy;
import com.bridgelabz.parkinglot.utility.AssignLot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {
	private int lotCapacity;
	private List<ParkingLot> parkingLots;

	public ParkingLotSystem(int lotCapacity) {
		this.lotCapacity = lotCapacity;
		parkingLots = new ArrayList<>();
	}
	public void addLot(ParkingLot parkingLot) {
		this.parkingLots.add(parkingLot);
	}
	public boolean isLotAdded(ParkingLot parkingLot) {
		if (this.parkingLots.contains(parkingLot))
			return true;
		return false;
	}

	public void parkVehicle(Vehicle vehicle, Enum type, String XYZ) throws ParkingLotException {
		ParkingStrategy parkingLotStrategy = AssignLot.car(type);
		ParkingLot lot = parkingLotStrategy.getParkingLot(this.parkingLots);
		lot.parkingVehicle(vehicle, type, "XYZ");
	}

	public boolean isVehicleParked(Vehicle vehicle) {
		if (this.parkingLots.get(0).isVehicleParked(vehicle))
			return true;
		return false;
	}

	public boolean isVehicleNotParked(Vehicle vehicle) throws ParkingLotException {
		for (int parkingLot = 0; parkingLot < this.parkingLots.size(); parkingLot++) {
			if (this.parkingLots.get(parkingLot).isVehicleNotParked(vehicle)) {
				return true;
			}
		}
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,
				  "VEHICLE IS NOT AVAILABLE");
	}

	public List<List<Integer>> findVehicleByField(String fieldName) {
		List<List<Integer>> listOfLotsWithWhiteVehicles = this.parkingLots.stream()
				  .map(lot -> lot.findOnField(fieldName))
				  .collect(Collectors.toList());
		return  listOfLotsWithWhiteVehicles;
	}

	public List<List<String>> findVehicleByNumberPlate(String color, String modelName) {
		List<List<String>> parkingLotsList = new ArrayList<>();
		for (ParkingLot list : this.parkingLots) {
			List<String> onField = list.findParkedToyatoVehicleDetails(color,modelName);
			parkingLotsList.add(onField);
		}
		return parkingLotsList;
	}

}