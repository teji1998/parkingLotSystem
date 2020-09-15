package com.bridgelabz.parkinglot.utility;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleType;
import com.bridgelabz.parkinglot.observer.ParkingStrategy;
import com.bridgelabz.parkinglot.service.HandicapDriverStrategy;
import com.bridgelabz.parkinglot.service.LargeVehicleStrategy;
import com.bridgelabz.parkinglot.service.NormalDriverStrategy;

public class AssignLot {

	public static ParkingStrategy car(Enum type) {
		if (type.equals(DriverType.HANDICAP)) {
			return new HandicapDriverStrategy();
		} else if (type.equals(VehicleType.LARGE_VEHICLE)) {
			return new LargeVehicleStrategy();
		}
		return new NormalDriverStrategy();
	}
}
