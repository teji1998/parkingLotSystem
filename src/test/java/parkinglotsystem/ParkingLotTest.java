package parkinglotsystem;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleType;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkingSlot;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.AirportSecurity;
import com.bridgelabz.parkinglot.observer.ParkingLotOwner;
import com.bridgelabz.parkinglot.observer.ParkingStrategy;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import com.bridgelabz.parkinglot.utility.AssignLot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ParkingLotTest {

	@Mock
	Vehicle vehicle;
	ParkingLot parkingLot;
	ParkingLotOwner parkingLotOwner;
	AirportSecurity airportSecurity;
	ParkingLot parkingLot1;
	ParkingLotSystem parkingLotSystem;
	private ParkingLot mockedLot;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

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
		mockedLot = mock(ParkingLot.class);
	}

	@Test
	public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLot.initializeParkingLot();
		parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
		boolean isParked = parkingLot.isVehicleParked(vehicle);
		Assert.assertTrue(isParked);
	}

	@Test
	public void givenAVehicle_WhenIsNotParked_ShouldReturnTrue() throws ParkingLotException {
		parkingLot.initializeParkingLot();
		parkingLot.parkingVehicle(vehicle, DriverType.NORMAL,"xyz");
		boolean isNotParked = parkingLot.isVehicleNotParked(vehicle);
		Assert.assertTrue(isNotParked);
	}

	@Test
	public void givenAVehicle_WhenUnparkedAnotherVehicle_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		try {
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
			parkingLot.isVehicleNotParked(vehicle);
		} catch (ParkingLotException e) {
			Assert.assertEquals("Vehicle is not unparked", e.getMessage());
		}
	}

	@Test
	public void givenVehicle_WhenParkingLotFull_ShouldInformToOwner() {
		ParkingLot parkingLot = new ParkingLot(1);
		parkingLot.initializeParkingLot();
		Vehicle vehicle = new Vehicle("black");
		ParkingLotOwner parkingOwner = new ParkingLotOwner();
		parkingLot.registerParkingLotObserver(parkingOwner);
		try {
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "XYZ");
			parkingLot.parkingVehicle(new Vehicle("black"), DriverType.NORMAL, "XYZ");
			boolean parkingFull = parkingOwner.isParkingFull();
			Assert.assertTrue(parkingFull);
		} catch (ParkingLotException e) {
		}

	}

	@Test
	public void givenAnEmptyLot_WhenInformedToParkingLotOwner_ShouldReturnFalse() {
		parkingLot.initializeParkingLot();
		boolean parkingFull = parkingLotOwner.isParkingFull();
		Assert.assertFalse(parkingFull);
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
		} catch (ParkingLotException e) {
			boolean notFull = parkingLotOwner.isParkingAvailable();
			Assert.assertFalse(notFull);
		}
	}

	@Test
	public void givenVehicle_WhenParkingLotFull_ShouldInformToAirportSecurity() {
		ParkingLot parkingLot = new ParkingLot(1);
		parkingLot.initializeParkingLot();
		AirportSecurity airportSecurity = new AirportSecurity();
		Vehicle vehicle = new Vehicle("black");
		parkingLot.registerParkingLotObserver(airportSecurity);
		try {
			parkingLot.parkingVehicle(vehicle,DriverType.NORMAL, "XYZ");
			parkingLot.parkingVehicle(new Vehicle("black"),DriverType.NORMAL, "XYZ");
			boolean parkingFull = airportSecurity.isParkingFull();
			Assert.assertTrue(parkingFull);
		} catch (ParkingLotException e) {
		}

	}

	@Test
	public void givenVehicle_WhenSpaceIsAvailable_ShouldInformToAirportSecurity() {
		ParkingLot parkingLot = new ParkingLot();
		parkingLot.setCapacity(2);
		parkingLot.initializeParkingLot();
		AirportSecurity airportSecurity = new AirportSecurity();
		Vehicle vehicle = new Vehicle("black");
		parkingLot.registerParkingLotObserver(airportSecurity);
		try {
			parkingLot.parkingVehicle(vehicle,DriverType.NORMAL, "XYZ");
			parkingLot.parkingVehicle(new Vehicle("black"),DriverType.NORMAL, "XYZ");
		} catch (ParkingLotException e) {
		}
		try {
			parkingLot.isVehicleNotParked(vehicle);
		} catch (ParkingLotException e) {
		}
		boolean parkingAvailable = airportSecurity.isParkingAvailable();
		Assert.assertFalse(parkingAvailable);
}


	@Test
	public void givenCapacityIs2ShouldBeAbleToPark2Vehicle() {
		parkingLot.setCapacity(3);
		parkingLot.initializeParkingLot();
		Vehicle vehicle2 = new Vehicle("black");
		try {
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
			boolean isParked1 = parkingLot.isVehicleParked(vehicle);
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL, "xyz");
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
		} catch (ParkingLotException e) {
			Assert.assertEquals("VEHICLE ALREADY PARK", e.getMessage());
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
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
			parkingLot.parkingVehicle(new Vehicle("black"), DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
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
			parkingLot.parkingVehicle(vehicle, DriverType.NORMAL, "xyz");
		} catch (ParkingLotException e) {
			boolean isTimeSetted = parkingLot.isTimeSet(vehicle);
			Assert.assertTrue(isTimeSetted);
		}
	}

	@Test
	public void givenParkingSlot_WhenVehiclesAreEquals_ShouldReturnTrue() {
		Vehicle vehicle = new Vehicle("black");
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
		parkingLotSystem.addLot(mockedLot);
		parkingLot1.setCapacity(1);
		parkingLot1.initializeParkingLot();
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, "xyz");
			verify(mockedLot).parkingVehicle(vehicle, DriverType.NORMAL, "XYZ");
			when(mockedLot.isVehicleParked(vehicle)).thenReturn(true);
		} catch (ParkingLotException e) {
			boolean isParkedAtALot = parkingLotSystem.isVehicleParked(vehicle);
			Assert.assertTrue(isParkedAtALot);
		}
	}

	@Test
	public void givenParkingLotSystem_WhenVehicleNotParkedOnLot_ShouldReturnFalse() {
		parkingLotSystem.addLot(mockedLot);
		Vehicle vehicle = new Vehicle("black");
		Vehicle vehicle2 = new Vehicle("black");
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, "XYZ");
			verify(mockedLot).parkingVehicle(vehicle, DriverType.NORMAL, "XYZ");
			when(mockedLot.isVehicleParked(vehicle2)).thenReturn(false);
			boolean isVehiclePark = parkingLotSystem.isVehicleParked(vehicle2);
			Assert.assertFalse(isVehiclePark);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLotSystem_WhenVehicleShouldParkInEvenlyDistributedLots_ShouldReturnTrue() {
		parkingLot1.setCapacity(10);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(mockedLot);

		ParkingLot parkingLot2 = new ParkingLot(10);
		parkingLot2.setCapacity(10);
		parkingLot2.initializeParkingLot();

		Vehicle vehicle = new Vehicle("black");
		Vehicle vehicle2 = new Vehicle("black");
		Vehicle vehicle3 = new Vehicle("white");
		Vehicle vehicle4 = new Vehicle("blue");

		parkingLotSystem.addLot(parkingLot2);
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, "XYZ");
			boolean isVehiclePark1 = parkingLotSystem.isVehicleParked(vehicle);
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, "XYZ");
			boolean isVehiclePark2 = parkingLotSystem.isVehicleParked(vehicle2);
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, "XYZ");
			boolean isVehiclePark3 = parkingLotSystem.isVehicleParked(vehicle3);
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, "XYZ");
			boolean isVehiclePark4 = parkingLotSystem.isVehicleParked(vehicle4);
			Assert.assertTrue(isVehiclePark1 && isVehiclePark2 && isVehiclePark3 && isVehiclePark4);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLotSystem_WhenUnParkNotAvailableVehicle_ShouldThrowException() {
		parkingLot1.setCapacity(10);
		parkingLot1.initializeParkingLot();
		parkingLotSystem.addLot(parkingLot1);
		Vehicle vehicle = new Vehicle("black");
		Vehicle vehicle2 = new Vehicle("black");
		Vehicle vehicle3 = new Vehicle("white");
		try {
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, "xyz");
			boolean isUnParkVehicle = parkingLotSystem.isVehicleNotParked(vehicle3);
			Assert.assertTrue(isUnParkVehicle);
		} catch (ParkingLotException e) {
			Assert.assertEquals("VEHICLE ALREADY PARK", e.getMessage());
		}
	}

	@Test
	public void givenParkingLotSystem_WhenDriverTypeIsHandicap_ShouldReturnNearestLotSpace() {
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
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle5, DriverType.HANDICAP, "xyz");
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
	public void givenParkingLot_WhenLargeVehicleParked_ShouldReturnTrue() {
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
			parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(new Vehicle("black"), DriverType.HANDICAP, "xyz");
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle5, DriverType.HANDICAP, "xyz");
			parkingLotSystem.parkVehicle(vehicle6, VehicleType.LARGE_VEHICLE, "xyz");
		} catch (ParkingLotException e) {
			boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle6);
			Assert.assertTrue(vehiclePark);
		}
	}

	@Test
	public void givenParkingLot_WhenParkedVehicleColorIsWhite_ShouldReturn1() {
		parkingLot.setCapacity(3);
		parkingLot.initializeParkingLot();
		Vehicle vehicle1 = new Vehicle("white");
		Vehicle vehicle2 = new Vehicle("black");
		try {
			parkingLot.parkingVehicle(vehicle1, DriverType.NORMAL, "XYZ");
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL, "XYZ");
			List<Integer> onField = parkingLot.findOnField("white");
			List whiteCarList = new ArrayList();
			whiteCarList.add(0);
			Assert.assertEquals(whiteCarList, onField);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLot_WhenParkedVehicleColorIsBlue_ShouldListParkedSlots() {
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
		Vehicle vehicle1 = new Vehicle("white", "toyota", "MH-12-A-1234");
		Vehicle vehicle2 = new Vehicle("blue", "BMW", "MH-12-A-1234");
		Vehicle vehicle3 = new Vehicle("blue", "toyota", "MH-12-A-1234");
		Vehicle vehicle4 = new Vehicle("white", "toyota", "MH-12-A-1234");
		Vehicle vehicle5 = new Vehicle("white", "BMW", "MH-12-A-1234");
		Vehicle vehicle6 = new Vehicle("blue", "toyota", "MH-12-B-1234");
		Vehicle vehicle7 = new Vehicle("blue", "toyota", "MH-12-C-1234");
		Vehicle vehicle8 = new Vehicle("blue", "toyota", "MH-12-D-1234");
		try {
			parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle5, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle6, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle7, DriverType.NORMAL, "xyz");
			parkingLotSystem.parkVehicle(vehicle8, DriverType.NORMAL, "xyz");
			List<List<String>> vehicleByNumberPlate = parkingLotSystem.findVehicleByNumberPlate("blue", "toyota");
			List expectedResult = new ArrayList();
			expectedResult.add("xyz  2  MH-12-C-1234");
			Assert.assertEquals(expectedResult, vehicleByNumberPlate.get(0));
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLot_WhenParkedBlueToyotaCar_ShouldReturnLocationAndAttendantNameAndPlateNumber() {
		parkingLot.setCapacity(20);
		parkingLot.initializeParkingLot();
		Vehicle vehicle1 = new Vehicle("white","toyota","MH-12-A-1234");
		Vehicle vehicle2 = new Vehicle("blue","BMW","MH-12-A-1234");
		Vehicle vehicle3 = new Vehicle("blue","toyota","MH-12-A-1234");
		Vehicle vehicle4 = new Vehicle("white","toyota","MH-12-A-1234");
		Vehicle vehicle5 = new Vehicle("white","BMW","MH-12-A-1234");
		Vehicle vehicle6 = new Vehicle("blue","toyota","MH-12-B-1234");
		try {
			parkingLot.parkingVehicle(vehicle1, DriverType.NORMAL, "asb");
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle3, DriverType.NORMAL, "pqr");
			parkingLot.parkingVehicle(vehicle4, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle5, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle6, DriverType.NORMAL, "xyz");
			List<String> onField = parkingLot.findParkedToyatoVehicleDetails("blue","toyota");
			List<String> whiteCarList = new ArrayList();
			whiteCarList.add("pqr  2  MH-12-A-1234");
			whiteCarList.add("xyz  5  MH-12-B-1234");
			Assert.assertEquals(whiteCarList, onField);
		} catch (ParkingLotException e) {
		}
	}


	@Test
	public void givenParkingLot_WhenParkedBMWVehicle_ShouldReturnLocation() {
		parkingLot.setCapacity(20);
		parkingLot.initializeParkingLot();
		Vehicle vehicle1 = new Vehicle("white","toyota","MH-12-A-1234");
		Vehicle vehicle2 = new Vehicle("blue","BMW","MH-12-A-1234");
		Vehicle vehicle3 = new Vehicle("blue","toyota","MH-12-A-1234");
		Vehicle vehicle4 = new Vehicle("white","toyota","MH-12-A-1234");
		Vehicle vehicle5 = new Vehicle("white","BMW","MH-12-A-1234");
		Vehicle vehicle6 = new Vehicle("blue","toyota","MH-12-B-1234");
		try {
			parkingLot.parkingVehicle(vehicle1, DriverType.NORMAL, "asb");
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle3, DriverType.NORMAL, "pqr");
			parkingLot.parkingVehicle(vehicle4, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle5, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle6, DriverType.NORMAL, "xyz");
			List<Integer> vehicleByNumberPlate = parkingLot.findParkedBMWVehicleDetails( "BMW");
			List expectedResult = new ArrayList();
			expectedResult.add(1);
			expectedResult.add(4);
			Assert.assertEquals(expectedResult, vehicleByNumberPlate);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLot_ShouldReturnAllParkingListBefore30Min() {
		parkingLot.setCapacity(20);
		parkingLot.initializeParkingLot();
		Vehicle vehicle1 = new Vehicle("white", "toyota", "MH-12-A-1234");
		Vehicle vehicle2 = new Vehicle("blue", "BMW", "MH-12-A-1234");
		Vehicle vehicle3 = new Vehicle("blue", "toyota", "MH-12-A-1234");
		try {
			parkingLot.parkingVehicle(vehicle1, DriverType.NORMAL, "asb");
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle3, DriverType.NORMAL, "pqr");
			List<String> vehicleByNumberPlate = parkingLot.getVehiclesWhichIsParkedFrom30Min();
			List expectedResult = new ArrayList();
			expectedResult.add("0 toyota MH-12-A-1234");
			expectedResult.add("1 BMW MH-12-A-1234");
			expectedResult.add("2 toyota MH-12-A-1234");
			Assert.assertEquals(expectedResult, vehicleByNumberPlate);
		} catch (ParkingLotException e) {
		}
	}

	@Test
	public void givenParkingLot_WhenParkedVehicle_ShouldReturnLocation() {
		parkingLot.setCapacity(20);
		parkingLot.initializeParkingLot();
		Vehicle vehicle1 = new Vehicle("white","toyota","MH-12-A-1234");
		Vehicle vehicle2 = new Vehicle("blue","BMW","MH-12-A-1234");
		Vehicle vehicle3 = new Vehicle("blue","toyota","MH-12-A-1234");
		Vehicle vehicle4 = new Vehicle("white","toyota","MH-12-A-1234");
		Vehicle vehicle5 = new Vehicle("white","BMW","MH-12-A-1234");
		Vehicle vehicle6 = new Vehicle("blue","toyota","MH-12-B-1234");
		try {
			parkingLot.parkingVehicle(vehicle1, DriverType.NORMAL, "asb");
			parkingLot.parkingVehicle(vehicle2, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle3, DriverType.NORMAL, "pqr");
			parkingLot.parkingVehicle(vehicle4, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle5, DriverType.NORMAL, "xyz");
			parkingLot.parkingVehicle(vehicle6, DriverType.NORMAL, "xyz");
			List<Integer> vehicleByNumberPlate = parkingLot.findVehicleDetails("xxx");
			List expectedResult = new ArrayList();
			expectedResult.add(1);
			expectedResult.add(4);
			Assert.assertEquals(expectedResult, vehicleByNumberPlate);
		} catch (ParkingLotException e) {
		}
	}

}
