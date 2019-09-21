package fr.walidg.park.parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

import org.apache.log4j.Logger;

import fr.walidg.park.parking.ParkingStructure.entity.Slot;

public class ParkingLotBuilder {
	private static final int PRICE_PER_HOUR = 3;
	private static final long FIXED_ENTRY_PRICE = 10;

	final static Logger logger = Logger.getLogger(ParkingLotBuilder.class);


	private int nb_of_electric20 = 0;
	private int nb_of_electric50 = 0;
	private int nb_of_petrol = 0;
	private Function<Slot, Float> pricingStategie = s -> 10.5f;
	public ParkingLotBuilder() {
		
	}
	
	public ParkingLotBuilder withPetrolSlots(int a) {
		nb_of_petrol = a;
		return this;
	}
	public ParkingLotBuilder withElectric20Slots(int a) {
		nb_of_electric20 = a;
		return this;
	}
	public ParkingLotBuilder withElectric50Slots(int a) {
		nb_of_electric50 = a;
		return this;
	}
	
	public ParkingLotBuilder withPrincing(Function<Slot, Float> ps) {
		this.pricingStategie = ps;
		return this;
	}

	public static Float TimeOnlyPricing(Slot s) {
		LocalDateTime now = LocalDateTime.now();
		long diff = Duration.between(s.getOccupiedSince(), now).toHours();
		return (float) (Math.round(diff  * PRICE_PER_HOUR * 100.0) / 100.0);
	}
	public static Float FixedAndTimePricing(Slot s) {
		LocalDateTime now = LocalDateTime.now();
		long diff = Duration.between(s.getOccupiedSince(), now).toHours();
		return (float) (Math.round((FIXED_ENTRY_PRICE + diff  * PRICE_PER_HOUR) * 100.0) / 100.0);
	}
	
	public ParkingLotManager build() {
		logger.info("Creating Parking lot");
		
		if(logger.isDebugEnabled()){
			logger.debug("Nb Classic: " + nb_of_petrol);
			logger.debug("Nb Electic: " + nb_of_electric20);
			logger.debug("Nb Electic50: " + nb_of_electric50);
		}
		
		return new ParkingLotManager(nb_of_petrol,nb_of_electric20, nb_of_electric50, pricingStategie);
	}

}
