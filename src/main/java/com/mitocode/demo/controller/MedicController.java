package com.mitocode.demo.controller;

import com.mitocode.demo.dto.MedicDTO;
import com.mitocode.demo.model.Medic;
import com.mitocode.demo.service.IMedicService;
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
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {

    private final IMedicService service;
    @Qualifier("medicMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MedicDTO>>  findAll() throws Exception{

        List<MedicDTO> list = service.findAll().stream().map(e ->
                convertToDto(e)).toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Medic obj = service.findById(id);

        MedicDTO dto = convertToDto(obj);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MedicDTO dto) throws Exception{
        Medic obj = service.save(convertToEntity(dto));

        //localhost:8000/medics/7
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDTO> update(@Valid @RequestBody MedicDTO dto,@PathVariable("id") Integer id) throws Exception{
        Medic obj =  service.update(convertToEntity(dto), id);

        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        //return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception{
       EntityModel<MedicDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
       // generar links informativos
       //localhost:8080/medics/1

       WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
       WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
       resource.add(link1.withRel("medic-info"));
       resource.add(link1.withRel("medics-info"));

       return resource;
   }

    private MedicDTO convertToDto(Medic obj){
            return mapper.map(obj, MedicDTO.class);
    }

    private Medic convertToEntity(MedicDTO dto){
        return mapper.map(dto, Medic.class);
    }

}
