package parkinglotsystem;

public class ParkingLotSystem {

	private Object vehicle;
	ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
	AirportSecurity airportSecurity = new AirportSecurity();

	public void parkingVehicle(Object vehicle) throws ParkingLotException {
		if (this.vehicle != null)
			throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot is Full");
		this.vehicle = vehicle;
		parkingLotOwner.parkingFull(this.vehicle != null);
		airportSecurity.parkingFull(this.vehicle != null);
	}

	public void unParkingVehicle(Object vehicle) {
		if(this.vehicle.equals(vehicle)) {
			this.vehicle = null;
			parkingLotOwner.parkingFull(this.vehicle != null);
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