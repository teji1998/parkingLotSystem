package parkinglotsystem;

public class ParkingLotSystem {

	private final int capacity;
	int currentCapacity =0;
	private Object vehicle;
	private ParkingLotOwner parkingLotOwner;

	public ParkingLotSystem(int capacity) {
		this.capacity = capacity;
	}
		
	public void parkingVehicle(Object vehicle) throws ParkingLotException {
		if (this.capacity == currentCapacity)
			throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking lot is full");
		this.vehicle = vehicle;
		currentCapacity ++;
	}

	public void unParkingVehicle(Object vehicle) {
		if(this.vehicle.equals(vehicle)) {
			this.vehicle = null;
		}
	}

	public boolean isVehicleParked(Object vehicle) throws ParkingLotException {
		if(this.vehicle.equals(vehicle))
			return true;
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_PARKING_EXCEPTION, "Vehicle is not parked");
	}

	public boolean isVehicleNotParked() throws ParkingLotException {
		if (this.vehicle == null)
			return true;
		throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_UNPARKING_EXCEPTION, "Vehicle is not unparked");
	}
}
