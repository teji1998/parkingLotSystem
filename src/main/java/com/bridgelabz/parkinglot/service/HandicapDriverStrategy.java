package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ParkingStrategy;

import java.util.List;

public class HandicapDriverStrategy implements ParkingStrategy {
	@Override
	public ParkingLot getParkingLot(List<ParkingLot> parkingLots) throws ParkingLotException {
		ParkingLot parkingLot1 = parkingLots.stream()
				  .filter(parkingLot -> parkingLot.getSlotList().size() > 0)
				  .findFirst()
				  .orElseThrow(() -> new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "PARKING IS FULL"));
		return parkingLot1;
	}
}
