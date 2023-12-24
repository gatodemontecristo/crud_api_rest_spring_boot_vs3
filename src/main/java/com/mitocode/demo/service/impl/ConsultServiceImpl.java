package com.mitocode.demo.service.impl;

import com.mitocode.demo.model.Consult;
import com.mitocode.demo.model.Exam;
import com.mitocode.demo.repo.IConsultExamRepo;
import com.mitocode.demo.repo.IConsultRepo;
import com.mitocode.demo.repo.IGenericRepo;
import com.mitocode.demo.service.IConsultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends CRUDImpl<Consult,Integer> implements IConsultService {

    private final IConsultRepo consultRepo;
    private final IConsultExamRepo ceRepo;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return consultRepo;
    }

    @Transactional
    @Override
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        consultRepo.save(consult); // GUARDADNDO MAESTRO DETALLE
        exams.forEach(ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

        return consult;
    }
}
