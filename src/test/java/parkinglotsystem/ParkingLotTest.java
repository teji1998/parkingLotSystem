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

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformOwner() {
		ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.parkingVehicle(new Object());
			boolean parkingFull = parkingLotOwner.isParkingFull();
			Assert.assertTrue(parkingFull);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsNotFull_ShouldReturnFalse() {
		ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
		boolean parkingFull = parkingLotOwner.isParkingFull();
		Assert.assertFalse(parkingFull);
	}
}