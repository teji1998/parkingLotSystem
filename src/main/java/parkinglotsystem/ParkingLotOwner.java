package parkinglotsystem;

public class ParkingLotOwner {
	private boolean capacity;

	public void ParkingFull() {
		this.capacity = true;
	}

	public boolean isParkingFull() {
		return this.capacity;
	}

}
