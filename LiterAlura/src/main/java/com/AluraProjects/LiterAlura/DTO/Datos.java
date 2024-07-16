package com.AluraProjects.LiterAlura.DTO;

import com.AluraProjects.LiterAlura.DTO.DatosLibro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(@JsonAlias("count") Integer total,
                      @JsonAlias("results") List<DatosLibro> libros) {
}
