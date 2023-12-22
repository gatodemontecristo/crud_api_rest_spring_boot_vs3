package com.mitocode.demo.service.impl;

import com.mitocode.demo.model.Medic;
import com.mitocode.demo.repo.IGenericRepo;
import com.mitocode.demo.repo.IMedicRepo;
import com.mitocode.demo.service.IMedicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl extends CRUDImpl<Medic,Integer>  implements IMedicService {

    private final IMedicRepo repo;


    @Override
    protected IGenericRepo<Medic, Integer> getRepo() {
        return repo;
    }
}
