package com.igortbr.entitys.invite.status;

public enum Status {
	CREATED("created"),
	SAVED("saved"),
	FINISHED("finished");
	
	
	private String status;
	
	Status(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
