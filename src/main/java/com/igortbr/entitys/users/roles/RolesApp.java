package com.igortbr.entitys.users.roles;

public enum RolesApp {
	OWNER("owner"),
	ADMIN("admin"),
	USER("user");
	
	
	private String role;
	
	RolesApp(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
