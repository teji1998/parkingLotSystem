package parkinglotsystem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NormalDriverStrategy implements ParkingStrategy {
	NORMAL;

	@Override
	public ParkingLot getParkingLot(List<ParkingLot> parkingLots) throws ParkingLotException {
		List<ParkingLot> parkingLotsList = parkingLots;
		Collections.sort(parkingLotsList, Comparator.comparing(list -> list.gettingSlot().size(), Comparator.reverseOrder()));
		return parkingLotsList.get(0);
	}
}
