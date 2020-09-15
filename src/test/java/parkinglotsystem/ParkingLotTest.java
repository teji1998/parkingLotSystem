package parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {

	Vehicle vehicle;
	ParkingLot parkingLot;
	ParkingLotOwner parkingLotOwner;
	AirportSecurity airportSecurity;
	ParkingLot parkingLot1;
	ParkingLotSystem parkingLotSystem;

	@Before
	public void setUp() {
		vehicle = new Vehicle("black");
		parkingLotOwner = new ParkingLotOwner();
		airportSecurity = new AirportSecurity();
		parkingLot = new ParkingLot(2);
		parkingLot.registerParkingLotObserver(parkingLotOwner);
		parkingLot.registerParkingLotObserver(airportSecurity);
		parkingLot1 = new ParkingLot(10);
		parkingLotSystem = new ParkingLotSystem(1);
	}

	@Test
	public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLot.initializeParkingLot();
		parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
		boolean isParked = parkingLot.isVehicleParked(vehicle);
		Assert.assertTrue(isParked);
	}

	@Test
	public void givenAVehicle_WhenIsNotParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLot.initializeParkingLot();
		parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
		boolean isNotParked = parkingLot.isVehicleNotParked(vehicle);
		Assert.assertTrue(isNotParked);
	}

	@Test
	public void givenAVehicle_WhenUnparkedAnotherVehicle_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
			parkingLot.parkingVehicle(new Vehicle("black"), DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
			parkingLot.parkingVehicle(new Vehicle("blue"), DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingAvailable();
			Assert.assertFalse(notFull);
		}
	}

	@Test
	public void givenCapacityIs2ShouldBeAbleToPark2Vehicle() {
		parkingLot.setCapacity(3);
		parkingLot.initializeParkingLot();
		Vehicle vehicle2 = new Vehicle("black");
		try {
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
			boolean isParked1 = parkingLot.isVehicleParked(vehicle);
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
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
			parkingLot.parkingVehicle(new Vehicle(), DriverType.NORMAL);
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL);
		} catch (ParkingLotException e) {
			boolean isTimeSetted = parkingLot.isTimeSet(vehicle);
			Assert.assertTrue(isTimeSetted);
		}
	}

	@Test
	public void givenParkingSlot_WhenVehiclesAreEquals_ShouldReturnTrue() {
		Vehicle vehicle = new Vehicle();
		ParkingSlot parkingSlot = new ParkingSlot(vehicle);
		ParkingSlot parkingSlot1 = new ParkingSlot(vehicle);
		boolean isVehicleSame = parkingSlot.equals(parkingSlot1);
		Assert.assertTrue(isVehicleSame);
	}
	
	@Test
	public void givenAParkingLotSystem_WhenAddedALot_ShouldReturnTrue() {
		ParkingLot parkingLot2 = new ParkingLot(10);
		parkingLotSystem.addLot(parkingLot1);
		parkingLotSystem.addLot(parkingLot2);
		boolean isTheLotAdded = parkingLotSystem.isLotAdded(parkingLot2);
		Assert.assertTrue(isTheLotAdded);
	}

	@Test
	public void givenParkingLotSystem_WhenParkedVehicleInGivenLot_ShouldReturnTrue() {
		parkingLotSystem.addLot(parkingLot1);
		parkingLot1.setCapacity(1);
		parkingLot1.initializeParkingLot();
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL);
		} catch (ParkingLotException e) {
			boolean isParkedAtALot = parkingLotSystem.isVehicleParked(vehicle);
			Assert.assertTrue(isParkedAtALot);
		}
	}

	@Test
	public void givenAParkingLotSystem_WhenCarIsEvenlyDistributed_ShouldReturnTrue() {
		parkingLot1.setCapacity(4);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot1);
		ParkingLot parkingLot2 = new ParkingLot(4);
		parkingLot2.setCapacity(4);
		parkingLot2.initializeParkingLot();
	   Vehicle vehicle2 = new Vehicle("black");
		Vehicle vehicle3 = new Vehicle("red");
		Vehicle vehicle4 = new Vehicle("white");
		parkingLotSystem.addLot(parkingLot2);
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL);
			boolean isVehiclePark1 = parkingLotSystem.isVehicleParked(vehicle);
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL);
			boolean isVehiclePark2 = parkingLotSystem.isVehicleParked(vehicle2);
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL);
			boolean isVehiclePark3 = parkingLotSystem.isVehicleParked(vehicle3);
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL);
			boolean isVehiclePark4 = parkingLotSystem.isVehicleParked(vehicle4);
			Assert.assertTrue(isVehiclePark1 && isVehiclePark2 && isVehiclePark3 && isVehiclePark4);
		} catch (ParkingLotException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenParkingLotSystem_WhenVehicleUnPark_ShouldReturnTrue() throws ParkingLotException {
		parkingLot1.setCapacity(10);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot1);
		ParkingLot parkingLot2 = new ParkingLot(10);
		parkingLot2.setCapacity(10);
		parkingLot2.initializeParkingLot();
		Vehicle vehicle2 = new Vehicle("white");
		Vehicle vehicle3 = new Vehicle("red");
		Vehicle vehicle4 = new Vehicle("orange");
		parkingLotSystem.addLot(parkingLot2);
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL);

		} catch (ParkingLotException e) {
			boolean isUnParkVehicle = parkingLotSystem.isVehicleNotParked(vehicle);
			Assert.assertTrue(isUnParkVehicle);
		}
	}

	@Test
	public void givenParkingLotSystem_WhenDriverTypeIsHandiCap_ShouldReturnNearestLotSpace() {
		parkingLot1.setCapacity(10);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot1);
		ParkingLot parkingLot2 = new ParkingLot();
		parkingLot2.setCapacity(10);
		parkingLot2.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot2);
		ParkingLot parkingLot3 = new ParkingLot();
		parkingLot3.setCapacity(10);
		parkingLot3.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot3);
		Vehicle vehicle2 = new Vehicle("red");
		Vehicle vehicle3 = new Vehicle("black");
		Vehicle vehicle4 = new Vehicle("blue");
		Vehicle vehicle5 = new Vehicle("black");
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle5, DriverType.HANDICAP);
		} catch (ParkingLotException e) {
			boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle5);
			Assert.assertTrue(vehiclePark);
		}
	}

	@Test
	public void givenParkingLotSystem_WhenDriverTypeIsNormal_ShouldReturnNearestLotSpace() {
		ParkingStrategy parkingLotStrategy = AssignLot.car(DriverType.NORMAL);
		List<ParkingLot> parkingLot1 = new ArrayList<>();
		ParkingLot parkingLot12 = new ParkingLot(1);
		parkingLot12.setCapacity(10);
		parkingLot12.initializeParkingLot();
		parkingLot1.add(parkingLot12);
		ParkingLot parkingLot;
		try {
			parkingLot = parkingLotStrategy.getParkingLot(parkingLot1);
			Assert.assertEquals(parkingLot12, parkingLot);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLotSystem_WhenLargeVehicleParked_ShouldReturnTrue() {
		parkingLot1.setCapacity(10);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot1);
		ParkingLot parkingLot2 = new ParkingLot();
		parkingLot2.setCapacity(10);
		parkingLot2.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot2);
		ParkingLot parkingLot3 = new ParkingLot();
		parkingLot3.setCapacity(10);
		parkingLot3.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot3);
		Vehicle vehicle2 = new Vehicle("red");
		Vehicle vehicle3 = new Vehicle("black");
		Vehicle vehicle4 = new Vehicle("white");
		Vehicle vehicle5 = new Vehicle("white");
		Vehicle vehicle6 = new Vehicle("black");
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(new Vehicle(), DriverType.HANDICAP);
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle5, DriverType.HANDICAP);
			parkingLotSystem.parkVehicle(vehicle6, VehicleType.LARGE_VEHICLE);
		} catch (ParkingLotException e) {
			boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle6);
			Assert.assertTrue(vehiclePark);
		}
	}

	@Test
	public void givenParkingLotSystem_WhenParkedVehicleColorIsWhite_ShouldListParkedSlots() {
		parkingLot1.setCapacity(10);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot1);

		ParkingLot parkingLot2 = new ParkingLot();
		parkingLot2.setCapacity(10);
		parkingLot2.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot2);

		ParkingLot parkingLot3 = new ParkingLot();
		parkingLot3.setCapacity(10);
		parkingLot3.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot3);

		Vehicle vehicle1 = new Vehicle("white");
		Vehicle vehicle2 = new Vehicle("black");
		Vehicle vehicle3 = new Vehicle("white");
		Vehicle vehicle4 = new Vehicle("blue");
		Vehicle vehicle5 = new Vehicle("white");
		Vehicle vehicle6 = new Vehicle("green");
		Vehicle vehicle7 = new Vehicle("white");
		Vehicle vehicle8 = new Vehicle("white");
		try {
			parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle5, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle6, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle7, DriverType.NORMAL);
			parkingLotSystem.parkVehicle(vehicle8, DriverType.NORMAL);
			List whiteCarList = parkingLotSystem.findVehicleByField("white");
			List  expectedResult= new ArrayList();
			expectedResult.add(1);
			expectedResult.add(2);
			Assert.assertEquals(expectedResult,whiteCarList.get(0));
		} catch (ParkingLotException e) {
		}
	}
}
