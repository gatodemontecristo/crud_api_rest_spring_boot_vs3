package com.mitocode.demo.dto;

public record PatientRecord(

         Integer idPatient,
         String firstName,
         String lastName,
         String dni,
         String address,
         String phone,
          String email
) {
}
