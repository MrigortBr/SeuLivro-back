package com.igortbr.entitys.address;

import java.util.UUID;

public record DTOAddresRead(UUID id, String streetAddress, String city, String acronymState, String postalCode,
		String country, String reference, String name) {
	
	public DTOAddresRead(Address address) {
		this(
				address.getId(),
				address.getStreetAddress(),
				address.getCity(),
				address.getAcronymState(),
				address.getPostalCode(),
				address.getCountry(),
				address.getReference(),
				address.getName()
			);
	}

}
