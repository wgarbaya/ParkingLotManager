package fr.walidg.park.parking.ParkingStructure.entity;

import fr.walidg.park.parking.ParkingStructure.entity.SlotType;
import fr.walidg.park.parking.ParkingStructure.ParkingSlotsAbstract;

public final class PetrolSlots extends ParkingSlotsAbstract {
	
	public PetrolSlots(int availability) {
		super();
		init(availability, SlotType.PERTROL);
	}
	

}
