package com.mitocode.demo.service.impl;

import com.mitocode.demo.model.Specialty;
import com.mitocode.demo.repo.IGenericRepo;
import com.mitocode.demo.repo.ISpecialtyRepo;
import com.mitocode.demo.service.ISpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends CRUDImpl<Specialty,Integer>  implements ISpecialtyService {

    private final ISpecialtyRepo repo;


    @Override
    protected IGenericRepo<Specialty, Integer> getRepo() {
        return repo;
    }
}
