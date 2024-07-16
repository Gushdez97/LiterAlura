package com.AluraProjects.LiterAlura.model;

import com.AluraProjects.LiterAlura.DTO.DatosLibro;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libros {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;
        @Column(unique = true)
        private String titulo;
        @ManyToOne
        @JoinColumn(name = "autores_Id")
        private Autor autores;
        private String idioma;
        private Integer numeroDeDescargas;

public Libros(){}
        public Libros(DatosLibro datosLibro){
            this.Id = datosLibro.id();
            this.titulo= datosLibro.titulo();
            this.idioma= datosLibro.idioma().toString();
            this.numeroDeDescargas= datosLibro.numeroDeDescargas();

}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autores;
    }

    public void setAutor(Autor autores) {
        this.autores = autores;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autores +
                ", idioma='" + idioma + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }
}
