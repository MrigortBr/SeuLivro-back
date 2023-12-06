package com.igortbr.entitys.books;

import java.util.UUID;

import lombok.Setter;

public record DTOBooksBuy(UUID idBook, UUID idAddress, Integer quantity) {

}
