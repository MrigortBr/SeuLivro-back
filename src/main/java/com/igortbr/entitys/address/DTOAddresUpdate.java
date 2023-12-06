package com.igortbr.entitys.address;

import java.util.UUID;

public record DTOAddresUpdate(UUID id,String streetAddress, String city, String acronymState, String postalCode,
		String country, String reference, String name) {

}
