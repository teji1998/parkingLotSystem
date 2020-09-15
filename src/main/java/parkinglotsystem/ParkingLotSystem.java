package parkinglotsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

	public void parkVehicle(Vehicle vehicle, Enum type) throws ParkingLotException {
		ParkingStrategy parkingLotStrategy = AssignLot.car(type);
		ParkingLot lot = parkingLotStrategy.getParkingLot(this.parkingLots);
		lot.parkingVehicle(vehicle, type);
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
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "VEHICLE IS NOT AVAILABLE");
	}

	public List findVehicleByField(String fieldName) {
		List<ArrayList> parkingLotsList = new ArrayList<>();
		for (ParkingLot list : this.parkingLots) {
			ArrayList<Integer> onField = list.findOnField(fieldName);
			parkingLotsList.add(onField);
		}
		return parkingLotsList;
	}
}

