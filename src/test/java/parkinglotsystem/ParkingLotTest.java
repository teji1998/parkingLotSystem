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
		parkingLotOwner = new ParkingLotOwner();
		airportSecurity = new AirportSecurity();
		parkingLotSystem = new ParkingLotSystem(2);
		parkingLotSystem.registerParkingLotObserver(parkingLotOwner);
		parkingLotSystem.registerParkingLotObserver(airportSecurity);
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
		boolean isNotParked = parkingLotSystem.isVehicleNotParked(vehicle);
		Assert.assertTrue(isNotParked);
	}

	@Test
	public void givenAVehicle_WhenUnparkedAnotherVehicle_ShouldReturnFalse() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.isVehicleNotParked(vehicle);
		} catch (ParkingLotException e) {
			Assert.assertEquals("Vehicle is not unparked", e.getMessage());
		}
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformOwner() {
		ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
		parkingLotSystem.registerParkingLotObserver(parkingLotOwner);
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.parkingVehicle(new Object());
		} catch (ParkingLotException e) {
			boolean parkingFull = parkingLotOwner.isParkingFull();
			Assert.assertTrue(parkingFull);
	}
	}

	@Test
	public void givenAnEmptyLot_WhenInformedToParkingLotOwner_ShouldReturnFalse() {
		boolean parkingFull = parkingLotOwner.isParkingFull();
		Assert.assertFalse(parkingFull);
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformAirportSecurity() {
		AirportSecurity airportSecurity = new AirportSecurity();
		parkingLotSystem.registerParkingLotObserver(airportSecurity);
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.parkingVehicle(new Object());
		} catch (ParkingLotException e) {
			boolean parkingFull = airportSecurity.isParkingFull();
			Assert.assertTrue(parkingFull);
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
			parkingLotSystem.isVehicleNotParked(vehicle);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingAvailable();
			Assert.assertTrue(notFull);
		}
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldReturnFalse() {
		try {
			parkingLotSystem.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingAvailable();
			Assert.assertFalse(notFull);
		}
	}

	@Test
	public void givenCapacityIs2ShouldBeAbleToPark2Vehicle() {
		parkingLotSystem.setCapacity(2);
		Object vehicle2 = new Object();
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
			parkingLotSystem.parkingVehicle(vehicle2);
			boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle2);
			Assert.assertTrue(isParked1 && isParked2);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenSameVehiclesTwoTimes_WhenParked_ShouldThrowException() {
		parkingLotSystem.setCapacity(2);
		parkingLotSystem.registerParkingLotObserver(parkingLotOwner);
		try {
			parkingLotSystem.parkingVehicle(vehicle);
			parkingLotSystem.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			Assert.assertEquals("Vehicle is parked", e.getMessage());
		}
	}
}