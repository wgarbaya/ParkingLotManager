package fr.walidg.park.parking.ParkingStructure;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import fr.walidg.park.parking.ParkingStructure.ParkingFullException;
import fr.walidg.park.parking.ParkingStructure.entity.Slot;
import fr.walidg.park.parking.ParkingStructure.entity.SlotType;

public abstract class ParkingSlotsAbstract {
	private static final String PARKING_FULL_EXP_MSG = "Parking full for your type of vehicule";
	public Queue<Slot> availableSlots = new ConcurrentLinkedQueue<>();
	final static Logger logger = Logger.getLogger(ParkingSlotsAbstract.class);

	
	/**
	 * Post constructor method for initializing the parking availablity
	 * @param nb_avaible_slots to be available
	 * @param st Parking type
	 */
	public void init(int nb_avaible_slots, SlotType st) {
		logger.debug("Initialisation of the Parking Slot");
		availableSlots.addAll(IntStream.rangeClosed(1, nb_avaible_slots).boxed()
				.map(String::valueOf)
				.map(i -> new Slot(i, st))
				.collect(Collectors.toCollection(LinkedList<Slot>::new)));

	}
	public Slot getASlot() throws ParkingFullException {
		logger.debug("Asking for a slot");
		Slot a = availableSlots.poll();
		if (a == null) {
			logger.warn(PARKING_FULL_EXP_MSG);
			throw new ParkingFullException(PARKING_FULL_EXP_MSG);	
		}
		a.setOccupiedSince(LocalDateTime.now());
		return a;
	}
	
	public void freeASlot(Slot s) {
		logger.debug("freeing for a slot");
		availableSlots.add(s);
	}
}
