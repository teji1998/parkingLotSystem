package parkinglotsystem;

public class ParkingLotOwner {

	private static boolean isFull = false;

	public void parkingFull(boolean isFull) {
		if(isFull)
			ParkingLotOwner.isFull = true;
	}

	public boolean isParkingFull() {
		if (isFull)
			return true;
		return false;
	}
}