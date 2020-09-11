package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.acl.Owner;

public class ParkingLotTest {

	Object vehicle;
	ParkingLotSystem parkingLotSystem;

	@Before
	public void setUp() {
		vehicle = new Object();
		parkingLotSystem = new ParkingLotSystem(1);
	}

	@Test
	public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLotSystem.parkingVehicle(vehicle);
		boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
		Assert.assertTrue(isParked);
	}

	@Test
	public void givenAVehicle_WhenIsNotParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLotSystem.parkingVehicle(vehicle);
		parkingLotSystem.unParkingVehicle(vehicle);
		boolean isNotParked = parkingLotSystem.isVehicleNotParked();
		Assert.assertTrue(isNotParked);
	}

	@Test
	public void givenAVehicle_WhenUnparkedAnotherVehicle_ShouldReturnFalse() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.isVehicleNotParked();
		} catch (ParkingLotException e) {
			Assert.assertEquals("Vehicle is not unparked", e.getMessage());
		}
	}

	@Test
	public void givenFullParkingLot_WhenOwnerKnows_ShouldThrowException() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.parkingVehicle(new Object());
		} catch (ParkingLotException e) {
			Assert.assertEquals("Parking lot is full", e.getMessage());
		}
	}
}