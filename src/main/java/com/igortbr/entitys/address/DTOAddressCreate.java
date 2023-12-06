package com.igortbr.entitys.address;

import com.igortbr.entitys.users.Users;

public record DTOAddressCreate(String streetAddress, String city, String acronymState, String postalCode,
		String country, String reference, String name) {

}
