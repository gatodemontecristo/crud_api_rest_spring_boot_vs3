package com.mitocode.demo.controller;

import com.mitocode.demo.dto.ConsultDTO;
import com.mitocode.demo.dto.ConsultListExamDTO;
import com.mitocode.demo.model.Consult;
import com.mitocode.demo.model.Exam;
import com.mitocode.demo.service.IConsultService;
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
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ConsultDTO>>  findAll() throws Exception{

        List<ConsultDTO> list = service.findAll().stream().map(e ->
                convertToDto(e)).toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Consult obj = service.findById(id);

        ConsultDTO dto = convertToDto(obj);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultListExamDTO dto) throws Exception{
        Consult cons = convertToEntity(dto.getConsult());
        List<Exam> exams = dto.getLstExam().stream().map(e -> mapper.map(e,Exam.class)).toList();
        Consult obj = service.saveTransactional(cons,exams);

        //localhost:8000/consults/7
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> update(@Valid @RequestBody ConsultDTO dto,@PathVariable("id") Integer id) throws Exception{
        Consult obj =  service.update(convertToEntity(dto), id);

        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        //return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception{
       EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
       // generar links informativos
       //localhost:8080/consults/1

       WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
       WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
       resource.add(link1.withRel("consult-info"));
       resource.add(link1.withRel("consults-info"));

       return resource;
   }

    private ConsultDTO convertToDto(Consult obj){
            return mapper.map(obj, ConsultDTO.class);
    }

    private Consult convertToEntity(ConsultDTO dto){
        return mapper.map(dto, Consult.class);
    }

}
