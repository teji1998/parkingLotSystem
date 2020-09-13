package parkinglotsystem;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {
	LocalDateTime time;
	Object vehicle;

	public ParkingSlot(Object vehicle) {
		this.vehicle = vehicle;
		this.time = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParkingSlot that = (ParkingSlot) o;
		return Objects.equals(vehicle, that.vehicle);
	}

}

