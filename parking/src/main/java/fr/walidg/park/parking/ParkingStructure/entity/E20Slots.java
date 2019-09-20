package fr.walidg.park.parking.ParkingStructure.entity;

import fr.walidg.park.parking.ParkingStructure.entity.SlotType;
import fr.walidg.park.parking.ParkingStructure.ParkingSlotsAbstract;

public final class E20Slots extends ParkingSlotsAbstract {

	public E20Slots(int availability) {
		super();
		init(availability,SlotType.ELECTRIC_20);
	}

}
