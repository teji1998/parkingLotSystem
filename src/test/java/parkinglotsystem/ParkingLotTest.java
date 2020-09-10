package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

	Object vehicle;
	ParkingLotSystem parkingLotSystem;

	@Before
	public void setUp() {
		vehicle = new Object();
		parkingLotSystem = new ParkingLotSystem();
	}

	@Test
	public void givenAVehicle_WhenParked_ShouldReturnTrue() {
		boolean isParked = parkingLotSystem.parkingVehicle(vehicle);
		Assert.assertTrue(isParked);
	}

	@Test
	public void givenAVehicle_WhenIsNotParked_ShouldReturnTrue() {
		parkingLotSystem.parkingVehicle(vehicle);
		boolean isNotParked = parkingLotSystem.unParkingVehicle(vehicle);
		Assert.assertTrue(isNotParked);
	}

	@Test
	public void givenAVehicle_WhenUnparkedAnotherVehicle_ShouldReturnFalse() {
		parkingLotSystem.parkingVehicle(vehicle);
		boolean isNotParked = parkingLotSystem.unParkingVehicle(new Object());
		Assert.assertFalse(isNotParked);
	}
}
