package fr.walidg.park.parking.ParkingStructure.entity;

import fr.walidg.park.parking.ParkingStructure.entity.SlotType;
import fr.walidg.park.parking.ParkingStructure.ParkingSlotsAbstract;

public final class E50Slots extends ParkingSlotsAbstract {
	
	public E50Slots(int availability) {
		super();
		init(availability, SlotType.ELECTRIC_50);
	}

}
