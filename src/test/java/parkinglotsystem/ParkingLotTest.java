package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

	Object vehicle;
	ParkingLotSystem parkingLotSystem;
	ParkingLotOwner parkingLotOwner;
	AirportSecurity airportSecurity;

	@Before
	public void setUp() {
		vehicle = new Object();
		parkingLotSystem = new ParkingLotSystem();
		parkingLotOwner = new ParkingLotOwner();
		airportSecurity = new AirportSecurity();
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
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformOwner() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean isFull = parkingLotOwner.isParkingFull();
			Assert.assertTrue(isFull);
		}
	}

	@Test
	public void givenAnEmptyLot_WhenInformedToParkingLotOwner_ShouldReturnFalse() {
		boolean parkingFull = parkingLotOwner.isParkingFull();
		Assert.assertFalse(parkingFull);
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformAirportSecurity() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean isFull = airportSecurity.isParkingFull();
			Assert.assertTrue(isFull);
		}
	}

	@Test
	public void givenAnEmptyPlot_WhenAirportSecurityInformed_ShouldReturnFalse() {
		boolean parkingFull = airportSecurity.isParkingFull();
		Assert.assertFalse(parkingFull);
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsNotFull_ShouldInformParkingLotOwner() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.unParkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingNotFull();
			Assert.assertTrue(notFull);
		}
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldReturnFalse() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingNotFull();
			Assert.assertFalse(notFull);
		}
	}
}