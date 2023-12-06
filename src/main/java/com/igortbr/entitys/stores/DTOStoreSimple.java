package com.igortbr.entitys.stores;

import java.util.UUID;

public record DTOStoreSimple(UUID id, String fantasyName, String image) {
	public DTOStoreSimple(Stores store) {
		this(store.getId(), store.getFantasyName(), store.getImage());
	}
}
