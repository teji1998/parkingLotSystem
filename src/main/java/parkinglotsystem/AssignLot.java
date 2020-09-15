package parkinglotsystem;

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
