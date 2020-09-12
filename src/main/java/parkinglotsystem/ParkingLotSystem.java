package parkinglotsystem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {

	private int capacity;
	private ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
	private Object vehicle;
	private List vehicles;
	private ParkingLotOwner owner;
	private List<ParkingLotObserver> parkingLotObservers;


	public ParkingLotSystem(int capacity) {
		this.capacity = capacity;
		this.parkingLotObservers = new ArrayList();
		this.vehicles = new ArrayList();
	}

	public void registerParkingLotObserver(ParkingLotObserver observer) {
		this.parkingLotObservers.add(observer);
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public void parkingVehicle(Object vehicle) throws ParkingLotException {
		if (this.vehicles.size() == this.capacity) {
			for (ParkingLotObserver observer : parkingLotObservers) {
				observer.parkingFull();
			}
			throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot is Full");
		}
		if (isVehicleParked(vehicle)) {
			throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_EXISTS, "Vehicle is parked");
		}
		this.vehicles.add(vehicle);
	}

	public boolean isVehicleParked(Object vehicle) {
			if (this.vehicles.contains(vehicle))
				return true;
			return false;
	}

	public boolean isVehicleNotParked(Object vehicle) throws ParkingLotException {
		if (this.vehicles.contains(vehicle)) {
			this.vehicles.remove(vehicle);
			for (ParkingLotObserver observer : parkingLotObservers) {
				observer.parkingAvailable();
			}
			return true;
		}
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_UNPARKING_EXCEPTION,"Vehicle is not present");
	}

}