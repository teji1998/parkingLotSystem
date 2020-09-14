package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {

	Object vehicle;
	ParkingLot parkingLot;
	ParkingLotOwner parkingLotOwner;
	AirportSecurity airportSecurity;

	@Before
	public void setUp() {
		vehicle = new Object();
		parkingLotOwner = new ParkingLotOwner();
		airportSecurity = new AirportSecurity();
		parkingLot = new ParkingLot(2);
		parkingLot.registerParkingLotObserver(parkingLotOwner);
		parkingLot.registerParkingLotObserver(airportSecurity);
	}

	@Test
	public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLot.initializeParkingLot();
		parkingLot.parkingVehicle(vehicle);
		boolean isParked = parkingLot.isVehicleParked(vehicle);
		Assert.assertTrue(isParked);
	}

	@Test
	public void givenAVehicle_WhenIsNotParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLot.initializeParkingLot();
		parkingLot.parkingVehicle(vehicle);
		boolean isNotParked = parkingLot.isVehicleNotParked(vehicle);
		Assert.assertTrue(isNotParked);
	}

	@Test
	public void givenAVehicle_WhenUnparkedAnotherVehicle_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle);
			parkingLot.isVehicleNotParked(vehicle);
		} catch (ParkingLotException e) {
			Assert.assertEquals("Vehicle is not unparked", e.getMessage());
		}
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformOwner() {
		ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
		parkingLot.registerParkingLotObserver(parkingLotOwner);
		try {
			parkingLot.parkingVehicle(vehicle);
			parkingLot.parkingVehicle(new Object());
		} catch (ParkingLotException e) {
			boolean parkingFull = parkingLotOwner.isParkingFull();
			Assert.assertTrue(parkingFull);
		}
	}

	@Test
	public void givenAnEmptyLot_WhenInformedToParkingLotOwner_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		boolean parkingFull = parkingLotOwner.isParkingFull();
		Assert.assertFalse(parkingFull);
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldInformAirportSecurity() {
		AirportSecurity airportSecurity = new AirportSecurity();
		parkingLot.registerParkingLotObserver(airportSecurity);
		try {
			parkingLot.parkingVehicle(vehicle);
			parkingLot.parkingVehicle(new Object());
		} catch (ParkingLotException e) {
			boolean parkingFull = airportSecurity.isParkingFull();
			Assert.assertTrue(parkingFull);
		}
	}

	@Test
	public void givenAnEmptyPlot_WhenAirportSecurityInformed_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		boolean parkingFull = airportSecurity.isParkingFull();
		Assert.assertFalse(parkingFull);
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsNotFull_ShouldInformParkingLotOwner() {
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle);
			parkingLot.isVehicleNotParked(vehicle);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingAvailable();
			Assert.assertTrue(notFull);
		}
	}

	@Test
	public void givenAVehicle_WhenParkingLotIsFull_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingAvailable();
			Assert.assertFalse(notFull);
		}
	}

	@Test
	public void givenCapacityIs2ShouldBeAbleToPark2Vehicle() {
		parkingLot.setCapacity(3);
		parkingLot.initializeParkingLot();
		Object vehicle2 = new Object();
		try {
			parkingLot.parkingVehicle(vehicle);
			boolean isParked1 = parkingLot.isVehicleParked(vehicle);
			parkingLot.parkingVehicle(vehicle2);
			boolean isParked2 = parkingLot.isVehicleParked(vehicle2);
			Assert.assertTrue(isParked1 && isParked2);
		} catch (ParkingLotException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenSameVehiclesTwoTimes_WhenParked_ShouldThrowException() {
		parkingLot.setCapacity(2);
		parkingLot.initializeParkingLot();
		parkingLot.registerParkingLotObserver(parkingLotOwner);
		try {
			parkingLot.parkingVehicle(vehicle);
			parkingLot.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			Assert.assertEquals("Vehicle is parked", e.getMessage());
		}
	}

	@Test
	public void givenParkingLot_WhenInitialized_ShouldGiveTheParkingCapacity() {
		parkingLot.setCapacity(15);
		int parkingCapacity = parkingLot.initializeParkingLot();
		Assert.assertEquals(15, parkingCapacity);
	}

	@Test
	public void givenParkingLot_WhenInitializedWithWrongCapacity_ShouldReturnNotEquals() {
		parkingLot.setCapacity(10);
		int parkingCapacity = parkingLot.initializeParkingLot();
		Assert.assertNotEquals(15, parkingCapacity);
	}

	@Test
	public void givenParkingLot_WhenEmpty_ShouldReturnEmptySlots() {
		List expectingList = new ArrayList();
		expectingList.add(0);
		expectingList.add(1);
		parkingLot.setCapacity(2);
		parkingLot.initializeParkingLot();
		ArrayList emptySlotList = parkingLot.gettingSlot();
		Assert.assertEquals(expectingList, emptySlotList);
	}

	@Test
	public void givenParkingLot_WhenParkWithProvidedSlot_ShouldReturnTrue() {
		parkingLot.setCapacity(10);
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean vehiclePark = parkingLot.isVehicleParked(vehicle);
			Assert.assertTrue(vehiclePark);
		}
	}

	@Test
	public void givenParkingLot_WhenVehicleFound_ShouldVehicleSlot() {
		parkingLot.setCapacity(10);
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(new Object());
			parkingLot.parkingVehicle(vehicle);
			int slotNumber = parkingLot.findingVehicle(vehicle);
			Assert.assertEquals(1, slotNumber);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLot_WhenVehicleParkedIfTimeIsSet_ShouldReturnTrue() {
		parkingLot.setCapacity(10);
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle);
		} catch (ParkingLotException e) {
			boolean isTimeSetted = parkingLot.isTimeSet(vehicle);
			Assert.assertTrue(isTimeSetted);
		}
	}

	@Test
	public void givenParkingSlot_WhenVehiclesAreEquals_ShouldReturnTrue() {
		Object vehicle = new Object();
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		ParkingSlot parkingSlot1 = new ParkingSlot(vehicle);
		boolean isVehicleSame = parkingSlot.equals(parkingSlot1);
		Assert.assertTrue(isVehicleSame);
	}
	
	@Test
	public void givenAParkingLotSystem_WhenAddedALot_ShouldReturnTrue() {
		ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1);
		ParkingLot parkingLot = new ParkingLot(10);
		parkingLotSystem.addLot(parkingLot);
		boolean isTheLotAdded = parkingLotSystem.isLotAdded(parkingLot);
		Assert.assertTrue(isTheLotAdded);
	}
}
