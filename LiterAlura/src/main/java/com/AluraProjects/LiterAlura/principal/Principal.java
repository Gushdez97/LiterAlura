package com.AluraProjects.LiterAlura.principal;

import com.AluraProjects.LiterAlura.DTO.Datos;
import com.AluraProjects.LiterAlura.DTO.DatosAutor;
import com.AluraProjects.LiterAlura.DTO.DatosLibro;
import com.AluraProjects.LiterAlura.model.Autor;
import com.AluraProjects.LiterAlura.model.Libros;
import com.AluraProjects.LiterAlura.repository.AutoresRepository;
import com.AluraProjects.LiterAlura.repository.LibrosRepository;
import com.AluraProjects.LiterAlura.service.ConsumoAPI;
import com.AluraProjects.LiterAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private String urlBase = "https://gutendex.com/books/";
    @Autowired
    private LibrosRepository librosRepository;
    @Autowired
    private AutoresRepository autoresRepository;


    public void muestraElMenu() {

        int contadorDefault = 0;
        int opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ////////////////////////////////////////////
                    Elige la opción deseada:
                                        
                    1 - Buscar libro en la Web
                    2 - Listado de TODOS los libros
                    3 - Lista de TODOS los autores
                    4 - Libros guardados
                    5 - Autores Guardados
                    6 - Lista de autores vivos en cierto año
                    7 - Buscar libros por IDIOMA
                    8 - Buscar libros por TITULO
                    9 - Buscar libros por AUTOR
                    10 - Libros más descargados
                                            
                    0 - Salir
                                            
                    /////////////////////////////////////////////////////////
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    listadoDeLibros();
                    break;
                case 3:
                    listaDeAutores();
                    break;
                case 4:
                    librosGuardados();
                    break;
                case 5:
                    autoresGuardados();
                    break;
                case 6:
                    autoresVivos();
                    break;
                case 7:
                    librosPorIdioma();
                    break;
                case 8:
                    librosPorTitulo();
                    break;
                case 9:
                    librosPorAutor();
                case 10:
                    librosMasBuscados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void librosPorAutor() {
        System.out.println("""
                ***************************
                  BUSCAR LIBRO POR AUTOR
                ***************************
                """);
        System.out.println("Ingrese el nombre del autor que deseas buscar:");
        var nombre = teclado.nextLine();
        Optional<Autor> autor = autoresRepository.buscarAutorPorNombre(nombre);
        if (autor.isPresent()) {
            System.out.println(
                    "\nAutor: " + autor.get().getNombre() +
                            "\nFecha de nacimiento: " + autor.get().getFechaNacimiento() +
                            "\nFecha de fallecimiento: " + autor.get().getFechaDeMuerte() +
                            "\nLibros: " + autor.get().getLibros().stream()
                            .map(l -> l.getTitulo()).collect(Collectors.toList()) + "\n"
            );

        } else {
            System.out.println("El autor no existe en la BD!");
        }
    }

    private void librosPorTitulo() {
        System.out.println("""
                ********************************
                 BUSCAR LIBROS POR TITULO
                ********************************
                """);
        System.out.println("Ingrese el titulo del libro que desea buscar:");
        var titulo = teclado.nextLine();
        List<Libros> libros = librosRepository.findByTitulo(titulo);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo.");
        } else {
            System.out.println("Libros encontrados con el titulo: " + titulo + ":");
            for (Libros libro : libros) {
                System.out.println("");
                System.out.println("Libro encontrado:");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idiomas: " + libro.getIdioma());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("");
            }
        }
    }

    private void autoresGuardados() {
        System.out.println("""
                *****************************
                  AUTORES GUARDADOS
                *****************************
                """);
        List<Autor> autores = autoresRepository.findAll();
        System.out.println();
        autores.forEach(l -> System.out.println(
                "Autor: " + l.getNombre() +
                        "\nFecha de nacimiento: " + l.getFechaNacimiento() +
                        "\nFecha de fallecimiento: " + l.getFechaDeMuerte() +
                        "\nLibros: " + l.getLibros().stream()
                        .map(t -> t.getTitulo()).collect(Collectors.toList()) + "\n"
        ));
    }

    private void librosGuardados() {
        System.out.println("""
                ********************************
                 LIBROS GUARDADOS
                ********************************
                """);
        List<Libros> libros = librosRepository.findAll();
        libros.forEach(l -> System.out.println(
                "----- LIBRO -----" +
                        "\nTitulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor().getNombre() +
                        "\nIdioma: " + l.getIdioma() +
                        "\nNumero de descargas: " + l.getNumeroDeDescargas() +
                        "\n-----------------\n"
        ));
    }

        private Datos getDatosLibro (String tipo, String busqueda){
            var json = consumoApi.obtenerDatos(urlBase + tipo + busqueda);
            Datos datos = conversor.obtenerDatos(json, Datos.class);
            return datos;
        }


    private void buscarLibrosWeb() {
        System.out.println("""
                ********************************
                 BUSQUEDA DE LIBROS EN LA WEB
                ********************************
                """);
        var tipo = "?search=";
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        Optional<DatosLibro> libroBuscado = getDatosLibro(tipo, nombreLibro).libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        System.out.println(getDatosLibro(tipo, nombreLibro).libros());
        if (libroBuscado.isPresent()) {
            // Obtener el primer autor del libro (si hay)
            DatosAutor primerAutor = libroBuscado.get().autor().stream().findFirst().orElse(null);
            if (primerAutor != null) {
                Autor autor = autoresRepository.findByNombre(primerAutor.nombre());
                if (autor == null) {
                    autor = new Autor();
                    autor.setNombre(primerAutor.nombre());
                    autor.setFechaNacimiento(primerAutor.fechaNacimiento());
                    autor.setFechaDeMuerte(primerAutor.fechaDeMuerte());
                    autor.setLibros(new ArrayList<>()); // Inicializar la lista de libros
                }

                // Crear un nuevo libro y asignar el autor
                Libros libro = new Libros();
                libro.setTitulo(libroBuscado.get().titulo());
                libro.setAutor(autor);
                libro.setIdioma(String.join(", ", libroBuscado.get().idioma()));
                libro.setNumeroDeDescargas(libroBuscado.get().numeroDeDescargas());

                // Verificar si la lista de libros del autor es nula y crearla si es necesario
                if (autor.getLibros() == null) {
                    autor.setLibros(new ArrayList<>());
                }

                // Agregar el libro al autor y guardar ambos en la base de datos
                autor.getLibros().add(libro);
                autoresRepository.save(autor);

                // Mostrar información del libro encontrado por consola
                System.out.println("");
                System.out.println("Libro encontrado:");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idiomas: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento del autor: " + autor.getFechaNacimiento());
                System.out.println("Fecha de fallecimiento del autor: " + autor.getFechaDeMuerte());
                System.out.println("");
            } else {
                System.out.println("No se encontró autor para el libro");
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listadoDeLibros(){
        System.out.println("""
                ********************************
                     LISTADO DE LIBROS
                ********************************
                """);
        System.out.println("Libros disponibles:");
        System.out.println("");
        getDatosLibro("","").libros().stream()
                .sorted(Comparator.comparing(DatosLibro::titulo))
                .map(l -> l.titulo())
                .forEach(System.out::println);
    }

    private void listaDeAutores() {
        System.out.println("""
                ********************************
                    LISTADO DE AUTORES
                ********************************
                """);
        System.out.println("Autores disponibles:");
        System.out.println("");
       getDatosLibro("","").libros().stream()
                .flatMap(libro -> libro.autor().stream()) // Obtener stream de autores
                .map(DatosAutor::nombre) // Mapear a los nombres de los autores
                .distinct() // Eliminar duplicados
                .sorted() // Ordenar alfabéticamente
                .forEach(System.out::println); // Imprimir cada nombre de autor
    }



    private void autoresVivos() {
        System.out.println("""
                ********************************
                     AUTORES VIVOS EN EL AÑO
                ********************************
                """);
        System.out.println("Ingrese el año para listar los autores vivos:");
        String años = teclado.nextLine();
        List<Autor> autores = autoresRepository.findAutoresVivosEnAño(años);

        if(!autores.isEmpty()){
            System.out.println();
            autores.forEach(a -> System.out.println(
                    "Autor: " + a.getNombre() +
                            "\nFecha de nacimiento: " + a.getFechaNacimiento() +
                            "\nFecha de fallecimiento: " + a.getFechaDeMuerte() +
                            "\nLibros: " + a.getLibros().stream()
                            .map(l -> l.getTitulo()).collect(Collectors.toList()) + "\n"
            ));
    }}


    private void librosPorIdioma() {
        System.out.println("""
                ********************************
                 BUSCAR LIBROS POR IDIOMA
                ********************************
                """);
        System.out.println("Ingrese el idioma que desea buscar:");
        var idioma = teclado.nextLine();
        List<Libros> libros = librosRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma ingresado.");
        } else {
            System.out.println("Libros encontrados en el idioma " + idioma + ":");
            for (Libros libro : libros) {
                System.out.println("");
                System.out.println("Libro encontrado:");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idiomas: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("");
            }
        }
    }


    private void librosMasBuscados(){
        System.out.println("""
                ********************************
                    LIBROS MÁS BUSCADOS
                ********************************
                """);
        System.out.println("Top 10 libros más descargados");
        getDatosLibro("","").libros().stream()
                .sorted(Comparator.comparing(DatosLibro::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

}
