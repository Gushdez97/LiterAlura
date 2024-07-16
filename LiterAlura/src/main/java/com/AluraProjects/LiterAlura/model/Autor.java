package com.AluraProjects.LiterAlura.model;

import com.AluraProjects.LiterAlura.DTO.DatosAutor;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long Id;
    private String nombre;
    private String fechaNacimiento;
    private String fechaDeMuerte;
    @OneToMany (mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;

public Autor (){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaDeMuerte = datosAutor.fechaDeMuerte();
        }




    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaDeMuerte=" + fechaDeMuerte +
                ", libros=" + libros +
                '}';
    }
}