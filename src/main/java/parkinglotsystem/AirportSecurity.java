package parkinglotsystem;

public class AirportSecurity {

	private static boolean isFull;

	public void parkingFull(boolean isFull) {
		if(isFull)
			AirportSecurity.isFull = isFull;
	}

	public boolean isParkingFull() {
		if (isFull)
			return true;
		return false;
	}

	public boolean isParkingNotFull() {
		if (!isFull)
			return true;
		return false;
	}
}
