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

	public boolean isVehicleParked(Object vehicle) {
		if(this.vehicle.equals(vehicle))
			return true;
		return false;
	}

	public boolean isVehicleNotParked(Object vehicle) {
		if (this.vehicle == null)
			return true;
		return false;
	}
}
