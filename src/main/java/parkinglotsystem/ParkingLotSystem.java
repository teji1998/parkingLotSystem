package parkinglotsystem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
	private int lotCapacity;
	private List<ParkingLot> parkingLots = new ArrayList();

	public ParkingLotSystem(int lotCapacity) {
		this.lotCapacity = lotCapacity;
	}

	public void addLot(ParkingLot parkingLot) {
		this.parkingLots.add(parkingLot);
	}

	public boolean isLotAdded(ParkingLot parkingLot) {
		if (this.parkingLots.contains(parkingLot))
			return true;
		return false;
	}
}
