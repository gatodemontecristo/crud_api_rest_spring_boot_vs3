package com.mitocode.demo.controller;

import com.mitocode.demo.dto.SpecialtyDTO;
import com.mitocode.demo.model.Specialty;
import com.mitocode.demo.service.ISpecialtyService;
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
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final ISpecialtyService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>>  findAll() throws Exception{

        List<SpecialtyDTO> list = service.findAll().stream().map(e ->
                convertToDto(e)).toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Specialty obj = service.findById(id);

        SpecialtyDTO dto = convertToDto(obj);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SpecialtyDTO dto) throws Exception{
        Specialty obj = service.save(convertToEntity(dto));

        //localhost:8000/specialtys/7
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpecialty()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> update(@Valid @RequestBody SpecialtyDTO dto,@PathVariable("id") Integer id) throws Exception{
        Specialty obj =  service.update(convertToEntity(dto), id);

        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        //return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception{
       EntityModel<SpecialtyDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
       // generar links informativos
       //localhost:8080/specialtys/1

       WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
       WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
       resource.add(link1.withRel("specialty-info"));
       resource.add(link1.withRel("specialtys-info"));

       return resource;
   }

    private SpecialtyDTO convertToDto(Specialty obj){
            return mapper.map(obj, SpecialtyDTO.class);
    }

    private Specialty convertToEntity(SpecialtyDTO dto){
        return mapper.map(dto, Specialty.class);
    }

}
