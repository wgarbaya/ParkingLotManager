package fr.walidg.park.parking.ParkingStructure.entity;

import java.time.LocalDateTime;

public class Slot {
	private String id;	
	private SlotType type;	
	private LocalDateTime occupiedSince;
	public Slot(String id, SlotType type) {
		this.setId(id);
		this.setType(type);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SlotType getType() {
		return type;
	}

	public void setType(SlotType type) {
		this.type = type;
	}

	public LocalDateTime getOccupiedSince() {
		return occupiedSince;
	}

	public void setOccupiedSince(LocalDateTime occupiedSince) {
		this.occupiedSince = occupiedSince;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slot other = (Slot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
