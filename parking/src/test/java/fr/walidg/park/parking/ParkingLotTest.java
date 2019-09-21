package fr.walidg.park.parking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.walidg.park.parking.ParkingClient.*;
import fr.walidg.park.parking.ParkingStructure.ParkingFullException;
import fr.walidg.park.parking.ParkingLotBuilder;
import fr.walidg.park.parking.ParkingLotManager;


public class ParkingLotTest {

	private static final int MAX_SIZE_PARK = 10;
	ParkingLotManager cut;

	@BeforeEach
	public void init(){
		ParkingLotBuilder plb = new ParkingLotBuilder();
		this.cut = plb.withPetrolSlots(MAX_SIZE_PARK).build();

	}

	@Test
	public void Parking_getMoreThanAvailable_ParkingFullException()  {
		assertThrows(ParkingFullException.class,()->{
			for (int i = 0; i < MAX_SIZE_PARK + 1; i++) {
				cut.getASlot(new PetrolVehicule("Duke"));
			}});

	}

	
	@Test
	public void Parking_ExitMultipleTimes() {
	    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
	    	PetrolVehicule v = new PetrolVehicule("Duke");
			cut.getASlot(v);
			cut.freeASlot(v);
			cut.freeASlot(v);
	    });
	    assertEquals(exception.getMessage(), "Vehicule not inside this parking");
	}

}