package com.mitocode.demo.service;

import com.mitocode.demo.model.Consult;
import com.mitocode.demo.model.Exam;

import java.util.List;

public interface IConsultService extends ICRUD<Consult, Integer> {

    Consult saveTransactional(Consult consult, List<Exam> exams);

}