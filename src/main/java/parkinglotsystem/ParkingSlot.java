package parkinglotsystem;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {
	private Enum driverType;
	LocalDateTime time;
	Vehicle vehicle;

	public ParkingSlot(Vehicle vehicle, Enum driverType) {
		this.vehicle = vehicle;
		this.time = LocalDateTime.now();
		this.driverType = driverType;
	}

	public ParkingSlot(Vehicle vehicle) {
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

