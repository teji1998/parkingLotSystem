package parkinglotsystem;

public class AirportSecurity {

	private static boolean isFull;

	public void parkingFull(boolean isFull) {
		if(isFull)
			AirportSecurity.isFull = true;
	}

	public boolean isParkingFull() {
		if (isFull)
			return true;
		return false;
	}
}
