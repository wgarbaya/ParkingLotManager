package fr.walidg.park.parking;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.apache.log4j.Logger;

import fr.walidg.park.parking.ParkingClient.Electic20KwVehicule;
import fr.walidg.park.parking.ParkingClient.Electic50kwVehicule;
import fr.walidg.park.parking.ParkingClient.PetrolVehicule;
import fr.walidg.park.parking.ParkingClient.Vehicule;
import fr.walidg.park.parking.ParkingStructure.entity.Slot;
import fr.walidg.park.parking.ParkingStructure.ParkingFullException;
import fr.walidg.park.parking.ParkingStructure.entity.E50Slots;
import fr.walidg.park.parking.ParkingStructure.entity.E20Slots;
import fr.walidg.park.parking.ParkingStructure.entity.PetrolSlots;

public class ParkingLotManager {
	private static final String EXP_MSG_INCORRECT_TYPE_OF_VEHICULE = "Incorrect type of vehicule";
	private static final String EXP_MSG_VEHICULE_NOT_INSIDE = "Vehicule not inside this parking";
	final static Logger logger = Logger.getLogger(ParkingLotManager.class);

	private PetrolSlots p;
	private E20Slots e20;
	private E50Slots e50;
	private ConcurrentHashMap<String, Slot> occupiedSlots = new ConcurrentHashMap<String, Slot>();
	private Function<Slot, Float> pricing;

	protected ParkingLotManager(int nb_of_petrol, int nb_of_electric20, int nb_of_electric50, Function<Slot, Float> pricing) {
		p = new PetrolSlots(nb_of_petrol);
		e20 = new E20Slots(nb_of_electric20);
		e50 = new E50Slots(nb_of_electric50);
		this.pricing = pricing;

	}
	/**
	 * Reserve a slot for the type of Vehicle using polymorphism
	 * @param ev The vehicle tring to enter
	 * @return The slot occupied by the vehicle, please park at is slot only
	 * @throws ParkingFullException, for this type of ev. the parking is full
	 */
	public Slot getASlot(Electic20KwVehicule ev) throws ParkingFullException{
		logger.debug("Asking for a slot for EV 20K for V ID: " + ev.id);
		Slot s = e20.getASlot();
		occupiedSlots.put(ev.id, s);
		return s;
	}
	public Slot getASlot(Electic50kwVehicule ev) throws ParkingFullException{
		logger.debug("Asking for a slot for EV 50K for V ID: " + ev.id);
		Slot s = e50.getASlot(); 
		occupiedSlots.put(ev.id, s);
		return s;
	}
	public Slot getASlot(PetrolVehicule pv) throws ParkingFullException{
		logger.debug("Asking for a slot for PV Petrol for V ID: " + pv.id);
		Slot s = p.getASlot(); 
		occupiedSlots.put(pv.id, s);
		return s;
	}
	/**
	 * Free a slot of a parking using Vehicle ID
	 * @param v Vehicle occupying the place, v.id will be uses
	 * @return the price as Double
	 * @throws IllegalArgumentException if vehicle is incorrect or unknown type of vehicule
	 */
	public Float freeASlot(Vehicule v) {
		logger.debug("Vehicule is leaving : " + v.id);

		Slot slot = occupiedSlots.get(v.id);
		if (slot == null) {
			logger.error("Vehicule is not found in OccupiedSlots");

			throw new IllegalArgumentException(EXP_MSG_VEHICULE_NOT_INSIDE);
		}
		Float price = pricing.apply(slot);
		Slot replacement = new Slot(slot.getId(), slot.getType());
		switch (slot.getType()) {
		case ELECTRIC_20:			 
			e20.freeASlot(replacement); break;
		case ELECTRIC_50:
			e50.freeASlot(replacement); break;
		case PERTROL:
			p.freeASlot(replacement); break;
		default:
			logger.error("Vehicule type is uncorrect please check code, or call parking attendant");
			throw new IllegalArgumentException(EXP_MSG_INCORRECT_TYPE_OF_VEHICULE + slot.getType());
		}
		occupiedSlots.remove(v.id);
		logger.debug("Vehicule is out with pricing of " + price);
		return price;
	}

}
