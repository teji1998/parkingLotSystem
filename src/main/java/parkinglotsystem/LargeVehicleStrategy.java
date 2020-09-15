package parkinglotsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LargeVehicleStrategy implements ParkingStrategy {
		@Override
		public ParkingLot getParkingLot(List<ParkingLot> parkingLots) throws ParkingLotException {
			List<ParkingLot> parkingLotsList = new ArrayList<>(parkingLots);
			Collections.sort(parkingLotsList, Comparator.comparing(list -> list.getVehicleCount()));
			return parkingLotsList.get(0);
		}
}
