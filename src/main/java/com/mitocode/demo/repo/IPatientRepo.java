package com.mitocode.demo.repo;

import com.mitocode.demo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


//@Repository
public interface IPatientRepo extends IGenericRepo<Patient,Integer> {


}
