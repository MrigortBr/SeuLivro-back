package com.igortbr.services.stores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igortbr.entitys.stores.DTOStoreUpdate;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;
import com.igortbr.model.stores.RepositoryStores;
import com.igortbr.services.auth.AuthorizationService;

@Service
public class StoresService {

	@Autowired
	private RepositoryStores repository;

	@Autowired
	public AuthorizationService token;

	public boolean userHaveStore(Users user) {

		if (user.getStore() == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean enableOrDisableStore(Stores store) {
		if (store.isEnabled()) {
			store.setEnabled(false);
			return false;
		} else {
			store.setEnabled(true);
			return true;
		}
	}

	public Stores update(Stores store, DTOStoreUpdate newStore) {
        if (newStore.cnpj() != null && !newStore.cnpj().equals(store.getCnpj())) {
            store.setCnpj(newStore.cnpj());
        }

        if (newStore.name() != null && !newStore.name().equals(store.getName())) {
            store.setName(newStore.name());
        }

        if (newStore.fantasyName() != null && !newStore.fantasyName().equals(store.getFantasyName())) {
            store.setFantasyName(newStore.fantasyName());
        }

        if (newStore.description() != null && !newStore.description().equals(store.getDescription())) {
            store.setDescription(newStore.description());
        }
        
        if (!newStore.enabled() == store.isEnabled()) {
            store.setEnabled(newStore.enabled());
        }
        
        if (newStore.image() != null && !newStore.image().equals(store.getImage())) {
            store.setImage(newStore.image());
        }
        
        return store;


	}
	

}


