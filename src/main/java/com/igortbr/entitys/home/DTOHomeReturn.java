package com.igortbr.entitys.home;

import java.util.List;

import com.igortbr.entitys.books.DTOBooksSimple;
import com.igortbr.entitys.stores.DTOStoreSimple;
import com.igortbr.entitys.users.DTOUsersSImple;

public record DTOHomeReturn(List<DTOBooksSimple> books, List<DTOUsersSImple> authors, List<DTOStoreSimple> stores) {

}
