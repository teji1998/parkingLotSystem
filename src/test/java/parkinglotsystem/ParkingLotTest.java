package parkinglotsystem;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

	@Test
	public void givenAVehicle_WhenParked_ShouldReturnTrue() {
		Object vehicle = new Object();
		ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
		boolean isParked = parkingLotSystem.parkingVehicle(vehicle);
		Assert.assertTrue(isParked);
	}

	@Test
	public void givenAVehicle_WhenIsNotParked_ShouldReturnTrue() {
		Object vehicle = new Object();
		ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
		parkingLotSystem.parkingVehicle(vehicle);
		boolean isNotParked = parkingLotSystem.unParkingVehicle(vehicle);
		Assert.assertTrue(isNotParked);
	}
}
