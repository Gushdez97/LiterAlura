package com.AluraProjects.LiterAlura.repository;

import com.AluraProjects.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutoresRepository extends JpaRepository<Autor, Long> {

    Autor save(Autor libro);

    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento < :años AND a.fechaDeMuerte > :años")
    List<Autor> findAutoresVivosEnAño(String años);

    @Query("SELECT a FROM Libros l JOIN l.autores a WHERE a.nombre LIKE %:nombre%")
    Optional<Autor> buscarAutorPorNombre(String nombre);


}
