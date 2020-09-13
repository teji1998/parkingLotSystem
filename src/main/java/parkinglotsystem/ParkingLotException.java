package parkinglotsystem;

public class ParkingLotException extends Exception {

	enum ExceptionType {
		VEHICLE_UNPARKING_EXCEPTION,
		PARKING_LOT_FULL,
		VEHICLE_EXISTS,
		VEHICLE_NOT_FOUND;
	}

	ExceptionType type;

	public ParkingLotException(ExceptionType type, String message){
		super(message);
		this.type = type;
	}
}
