package com.igortbr.entitys.users.identifier;

public enum Identifier {
	CPF("cpf"),
	CNPJ("cnpj");
	
	
	private String role;
	
	Identifier(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
