package fr.walidg.park.parking;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.walidg.park.parking.ParkingStructure.ParkingFullException;
import fr.walidg.park.parking.ParkingStructure.ParkingSlotsAbstract;
import fr.walidg.park.parking.ParkingStructure.entity.Slot;
import static org.mockito.Mockito.*;

public class ParkingSlotsAbstractTest {
	private static final int SIZE_10 = 10;
	ParkingSlotsAbstract cut;

	@BeforeEach
	public void init(){
		cut = Mockito.mock(ParkingSlotsAbstract.class, Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void given10slot_afterinit_parkingsize10() {
		Queue<Slot> mockedavailableSlots = new LinkedList<>();
		cut.availableSlots = mockedavailableSlots;
		cut.init(SIZE_10,null);
		assertAll( () -> assertThat(cut.availableSlots, hasSize(SIZE_10)),
				() -> assertEquals(cut.availableSlots.peek().getId(), "1" ));

	}

	@Test
	public void givenaslot_afterinit_parkingsize10() throws ParkingFullException {
		Queue<Slot> mockedavailableSlots = new LinkedList<>();
 		Slot mockedSlot = new Slot("", null);
 		Queue<Slot> spy = spy(mockedavailableSlots);
	    doReturn(mockedSlot).when(spy ).poll();
	    cut.availableSlots = spy;
	    assertEquals(cut.getASlot(),mockedSlot );
	}
	
	@Test
	public void Parking_ExitMultipleTimes() {
		Queue<Slot> mockedavailableSlots = new LinkedList<>();
 		Queue<Slot> spy = spy(mockedavailableSlots);
	    doReturn(null).when(spy ).poll();
	    cut.availableSlots = spy;
		
		Throwable exception = assertThrows(ParkingFullException.class, () -> {
			cut.getASlot();
	    });
	    assertEquals(exception.getMessage(), "Parking full for your type of vehicule");
	}
}
