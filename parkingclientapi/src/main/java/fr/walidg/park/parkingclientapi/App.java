package fr.walidg.park.parkingclientapi;

import java.time.LocalDateTime;

import fr.walidg.park.parking.ParkingLotBuilder;
import fr.walidg.park.parking.ParkingLotManager;
import fr.walidg.park.parking.ParkingClient.Electic20KwVehicule;
import fr.walidg.park.parking.ParkingStructure.ParkingFullException;
import fr.walidg.park.parking.ParkingStructure.entity.Slot;

public class App 
{
	public App() {
	}
	public static Float price(Slot lo) {
		return 0f;
	}

	public static void main( String[] args )
	{
		ParkingLotBuilder plb = new ParkingLotBuilder();
		ParkingLotManager plm = plb
				.withElectric20Slots(1)
				.withElectric50Slots(0)
				.withPetrolSlots(10)
				.withPrincing(ParkingLotBuilder::FixedAndTimePricing)
				.build();
		try {
			Electic20KwVehicule v = new Electic20KwVehicule("ID");
			Slot s = plm.getASlot(v);
			Float price = plm.freeASlot(v);
			System.out.println(String.format("The price is :  %10.2f €", price));

		} catch (ParkingFullException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
