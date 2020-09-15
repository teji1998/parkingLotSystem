package parkinglotsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {

	private int capacity;
	private ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
	private Object vehicle;
	private List<ParkingSlot> vehicles;
	private ParkingLotOwner owner;
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

	public void parkingVehicle(Object vehicle, Enum driverType) throws ParkingLotException {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle, driverType);
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

	public boolean isVehicleParked(Object vehicle) {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		if (this.vehicles.contains(parkingSlot))
				return true;
			return false;
	}

	public boolean isVehicleNotParked(Object vehicle) throws ParkingLotException {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		for (int slotnumber = 0; slotnumber < this.vehicles.size(); slotnumber++) {
			if (this.vehicles.contains(parkingSlot)) {
				this.vehicles.set(slotnumber, null);

				vehicleCount--;
				for (ParkingLotObserver observer : parkingLotObservers) {
					observer.parkingAvailable();
				}
				return true;
			}
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

	public int getParkingSlot() throws ParkingLotException {
		ArrayList<Integer> emptySlotList = gettingSlot();
		for (int slot = 0; slot < emptySlotList.size(); slot++) {
			if (emptySlotList.get(0) == (slot)) {
				return slot;
			}
		}
		throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking is full");
	}

	public int findingVehicle(Object vehicle) throws ParkingLotException {
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		if (this.vehicles.contains(parkingSlot))
			return this.vehicles.indexOf(parkingSlot);
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle is not present");
	}

	public boolean isTimeSet(Object vehicle) {
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
}
