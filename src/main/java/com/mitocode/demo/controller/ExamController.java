package com.mitocode.demo.controller;

import com.mitocode.demo.dto.ExamDTO;
import com.mitocode.demo.model.Exam;
import com.mitocode.demo.service.IExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final IExamService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ExamDTO>>  findAll() throws Exception{

        List<ExamDTO> list = service.findAll().stream().map(e ->
                convertToDto(e)).toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Exam obj = service.findById(id);

        ExamDTO dto = convertToDto(obj);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ExamDTO dto) throws Exception{
        Exam obj = service.save(convertToEntity(dto));

        //localhost:8000/exams/7
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@Valid @RequestBody ExamDTO dto,@PathVariable("id") Integer id) throws Exception{
        Exam obj =  service.update(convertToEntity(dto), id);

        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        //return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/hateoas/{id}")
    public EntityModel<ExamDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception{
       EntityModel<ExamDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
       // generar links informativos
       //localhost:8080/exams/1

       WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
       WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
       resource.add(link1.withRel("exam-info"));
       resource.add(link1.withRel("exams-info"));

       return resource;
   }

    private ExamDTO convertToDto(Exam obj){
            return mapper.map(obj, ExamDTO.class);
    }

    private Exam convertToEntity(ExamDTO dto){
        return mapper.map(dto, Exam.class);
    }

}
