package com.mitocode.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SpecialtyDTO {
    @EqualsAndHashCode.Include
    private Integer idSpecialty;

    @NotNull
    @NotEmpty
    private String nameSpecialty;

    @NotNull
    @NotEmpty
    private String descriptionSpecialty;
}
