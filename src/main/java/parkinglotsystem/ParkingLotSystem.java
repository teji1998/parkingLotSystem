package parkinglotsystem;

public class ParkingLotSystem {

	private Object vehicle;

	public boolean parkingVehicle(Object vehicle) {
		this.vehicle = vehicle;
		return true;
	}

	public boolean unParkingVehicle(Object vehicle) {
		if(this.vehicle.equals(vehicle)) {
			this.vehicle = null;
			return true;
		}
		return false;
	}
}
