package parkinglotsystem;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {
	private ParkingStrategy driverType;
	LocalDateTime time;
	Object vehicle;

	public ParkingSlot(Object vehicle, ParkingStrategy driverType) {
		this.vehicle = vehicle;
		this.time = LocalDateTime.now();
		this.driverType = driverType;
	}

	public ParkingSlot(Object vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParkingSlot that = (ParkingSlot) o;
		return Objects.equals(vehicle, that.vehicle);
	}

}

