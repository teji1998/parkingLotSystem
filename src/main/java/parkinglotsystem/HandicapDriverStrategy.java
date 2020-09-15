package parkinglotsystem;

import java.util.List;

public enum HandicapDriverStrategy implements ParkingStrategy {
		HANDICAP;

		@Override
		public ParkingLot getParkingLot(List<ParkingLot> parkingLots) throws ParkingLotException {
			ParkingLot parkingLot1 = parkingLots.stream()
					  .filter(parkingLot -> parkingLot.gettingSlot().size() > 0)
					  .findFirst()
					  .orElseThrow(() -> new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "PARKING IS FULL"));
			return parkingLot1;
		}
}
