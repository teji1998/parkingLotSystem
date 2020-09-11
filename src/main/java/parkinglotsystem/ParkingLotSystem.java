package parkinglotsystem;

public class ParkingLotSystem {

	private Object vehicle;

	public void parkingVehicle(Object vehicle) {
		this.vehicle = vehicle;
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
