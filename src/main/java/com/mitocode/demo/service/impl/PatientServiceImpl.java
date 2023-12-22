package com.mitocode.demo.service.impl;

import com.mitocode.demo.model.Patient;
import com.mitocode.demo.repo.IGenericRepo;
import com.mitocode.demo.repo.IPatientRepo;
import com.mitocode.demo.service.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends CRUDImpl<Patient,Integer> implements IPatientService {

    private final IPatientRepo repo;


    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }
}
