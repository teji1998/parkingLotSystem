package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	public void givenParkingLot_WhenInitialized_ShouldGiveTheParkingCapacity() {
		parkingLotSystem.setCapacity(15);
		int parkingCapacity = parkingLotSystem.initializeParkingLot();
		Assert.assertEquals(15,parkingCapacity);
	}

	@Test
	public void givenParkingLot_WhenInitializedWithWrongCapacity_ShouldReturnNotEquals() {
		parkingLotSystem.setCapacity(10);
		int parkingCapacity = parkingLotSystem.initializeParkingLot();
		Assert.assertNotEquals(15,parkingCapacity);
	}

	@Test
	public void givenParkingLot_WhenEmpty_ShouldReturnEmptySlots() {
		List expectingList = new ArrayList();
		expectingList.add(0);
		expectingList.add(1);
		parkingLotSystem.setCapacity(2);
		parkingLotSystem.initializeParkingLot();
		ArrayList emptySlotList = parkingLotSystem.gettingSlot();
		Assert.assertEquals(expectingList, emptySlotList);
	}

	@Test
	public void givenParkingLot_WhenParkWithProvidedSlot_ShouldReturnTrue() {
		parkingLotSystem.setCapacity(10);
		parkingLotSystem.initializeParkingLot();
		ArrayList<Integer> emptySlotList = parkingLotSystem.gettingSlot();
		try {
			parkingLotSystem.parkingVehicle(emptySlotList.get(0),vehicle);
		} catch (ParkingLotException e) {
			boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle);
			Assert.assertTrue(vehiclePark);
		}
	}

	@Test
	public void givenParkingLot_WhenVehicleFound_ShouldVehicleSlot() {
		parkingLotSystem.setCapacity(10);
		parkingLotSystem.initializeParkingLot();
		ArrayList<Integer> emptySlotList = parkingLotSystem.gettingSlot();
		try {
			parkingLotSystem.parkingVehicle(emptySlotList.get(0),new Object());
			parkingLotSystem.parkingVehicle(emptySlotList.get(1),vehicle);
			int slotNumber = parkingLotSystem.findingVehicle(this.vehicle);
			Assert.assertEquals(1,slotNumber);
		} catch (ParkingLotException e) {
		}
	}
}
