package com.bridgelabz.parkinglot.observer;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public interface ParkingStrategy {
	public ParkingLot getParkingLot(List<ParkingLot> parkingLots) throws ParkingLotException;
}
