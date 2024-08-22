package com.simple.bank_system.service;

import java.util.List;

public interface CRUDService<M, D, ID> {

    M create(D dto);
    List<M> readAll();
    M update(D dto, ID id);
    void delete(ID id);

}
