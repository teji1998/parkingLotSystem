package parkinglotsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

	public int initializeParkingLot() {
		IntStream.range(0, this.capacity).forEach(slots -> vehicles.add(null));
		return vehicles.size();
	}

	public ArrayList gettingSlot() {
		ArrayList<Integer> emptySlot = new ArrayList();
		for (int slot = 0; slot < this.capacity; slot++ ) {
			if (this.vehicles.get(slot) == null)
				emptySlot.add(slot);
		}
		return emptySlot;
	}

	public void parkingVehicle(int slot, Object vehicle) throws ParkingLotException {
		if (isVehicleParked(vehicle)) {
			throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_EXISTS,"Vehicle is parked");
		}
		this.vehicles.set(slot, vehicle);
	}

	public int findingVehicle(Object vehicle) throws ParkingLotException {
		if (this.vehicles.contains(vehicle))
			return this.vehicles.indexOf(vehicle);
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle is not present");
	}
}