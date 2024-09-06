package com.secure.store.service;

import com.secure.store.modal.BinDTO;
import com.secure.store.modal.Response;

import java.util.List;

public interface BinService {
    BinDTO findAll();
    Response delete(Long id);
    Response delete(List<Long> ids);
    Response deleteAll();
    Response restore(Long id);
    Response restore(List<Long> ids);
    Response restoreAll();
}
