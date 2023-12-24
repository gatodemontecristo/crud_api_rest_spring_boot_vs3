package com.mitocode.demo.service.impl;

import com.mitocode.demo.model.Exam;
import com.mitocode.demo.repo.IGenericRepo;
import com.mitocode.demo.repo.IExamRepo;
import com.mitocode.demo.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam,Integer> implements IExamService {

    private final IExamRepo repo;


    @Override
    protected IGenericRepo<Exam, Integer> getRepo() {
        return repo;
    }
}
