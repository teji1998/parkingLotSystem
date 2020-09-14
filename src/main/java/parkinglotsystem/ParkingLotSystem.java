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

	public void parkVehicle(Object vehicle) throws ParkingLotException {
		List<ParkingLot> parkingLotList = this.parkingLots;
		Collections.sort(parkingLotList, Comparator.comparing(list -> list.gettingSlot().size(), Comparator.reverseOrder()));
		ParkingLot lot = parkingLotList.get(0);
		lot.parkingVehicle(vehicle);
	}

	public boolean isVehicleParked(Object vehicle) {
		if (this.parkingLots.get(0).isVehicleParked(vehicle))
			return true;
		return false;
	}

	public boolean isVehicleNotParked(Object vehicle) throws ParkingLotException {
		for (int parkingLot = 0; parkingLot < this.parkingLots.size(); parkingLot++) {
			if (this.parkingLots.get(parkingLot).isVehicleNotParked(vehicle)) {
				return true;
			}
		}
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "VEHICLE IS NOT AVAILABLE");
	}
}

