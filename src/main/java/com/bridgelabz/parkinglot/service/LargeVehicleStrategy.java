package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.observer.ParkingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LargeVehicleStrategy implements ParkingStrategy {
	@Override
	public ParkingLot getParkingLot(List<ParkingLot> parkingLots) {
		List<ParkingLot> parkingLotsList = new ArrayList<>(parkingLots);
		Collections.sort(parkingLotsList, Comparator.comparing(list -> list.getParkVehicleCount()));
		return parkingLotsList.get(0);
	}
}
