package parkinglotsystem;

public class AirportSecurity implements ParkingLotObserver {

	private boolean parkingCapacity;

	@Override
	public void parkingFull() {
		this.parkingCapacity = true;
	}

	@Override
	public void parkingAvailable() {
		this.parkingCapacity = false;
	}

	public boolean isParkingFull() {
		return this.parkingCapacity;
	}

	public boolean isParkingAvailable() {
		return this.parkingCapacity;
	}
}
