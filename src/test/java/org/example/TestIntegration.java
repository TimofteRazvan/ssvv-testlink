package org.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class TestIntegration {
    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private NotaValidator notaValidator;

    @Mock
    private StudentXMLRepo studentXMLRepository;

    @Mock
    private TemaXMLRepo temaXMLRepository;

    @Mock
    private NotaXMLRepo notaXMLRepository;

    private Service service;

    @BeforeEach
    public void setup() {
        studentValidator = mock(StudentValidator.class);
        temaValidator = mock(TemaValidator.class);
        notaValidator = mock(NotaValidator.class);
        studentXMLRepository = mock(StudentXMLRepo.class);
        temaXMLRepository = mock(TemaXMLRepo.class);
        notaXMLRepository = mock(NotaXMLRepo.class);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    public void tearDown() {
        studentValidator = null;
        temaValidator = null;
        notaValidator = null;
        studentXMLRepository = null;
        temaXMLRepository = null;
        notaXMLRepository = null;
        service = null;
    }

    @Test
    public void addStudent_InvalidNume_ThrowsException() {
        String studentId = "1";
        String nume = "";
        int grupa = 931;
        String email = "miguel@yahoo.com";

        Student student = new Student(studentId, nume, grupa, email);

        try {
            doThrow(new ValidationException("Nume incorect!")).when(studentValidator).validate(student);
            Assertions.assertThrows(ValidationException.class, () -> {
                service.addStudent(student);
            });
        } catch (ValidationException ve) {
            ve.printStackTrace();
        }
    }

    @Test
    public void addTema_InvalidDeadline_ThrowsException() {
        String nrTema = "1";
        String descriere = "descriere";
        int deadline = 20;
        int primire = 2;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            doThrow(new ValidationException("Deadlineul trebuie sa fie intre 1-14.")).when(temaValidator).validate(tema);
            Assertions.assertThrows(ValidationException.class, () -> {
                service.addTema(tema);
            });
        } catch (ValidationException ve) {
            ve.printStackTrace();
        }
    }

    @Test
    public void addNota_InvalidStudent_InvalidTema_ThrowsException() {
        String notaId = "nt1";
        double notaVal = 9.5;
        LocalDate date = LocalDate.of(2023, 4, 25);
        Nota nota = new Nota(notaId, "0", "0", notaVal, date);
        try {
            //Nota returnedNota = service.addNota(nota, "feedback");
            Assertions.assertThrows(NullPointerException.class, () -> {
                service.addNota(nota, "feedback");
            });
            //Assertions.assertNull(returnedNota);
            //Assertions.assertEquals(9.5, nota.getNota());
        } catch (Exception ve) {
            ve.printStackTrace();
        }
    }

    @Test
    public void addStudent_Valid_addTema_Valid_addNota_InvalidDate_ThrowsException() {
        String studentId = "1";
        String nume = "Miguel";
        int grupa = 931;
        String email = "miguel@yahoo.com";
        Student student = new Student(studentId, nume, grupa, email);

        String nrTema = "1";
        String descriere = "descriere";
        int deadline = 12;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        String notaId = "nt1";
        double notaVal = 9.5;
        LocalDate date = LocalDate.of(2024, 4, 15);
        Nota nota = new Nota(notaId, studentId, nrTema, notaVal, date);

        try {
            doNothing().when(studentValidator).validate(student);
            when(studentXMLRepository.save(student)).thenReturn(null);

            doNothing().when(temaValidator).validate(tema);
            when(temaXMLRepository.save(tema)).thenReturn(null);

            when(studentXMLRepository.findOne(studentId)).thenReturn(student);
            when(temaXMLRepository.findOne(nrTema)).thenReturn(tema);

            Student returnedStudent = service.addStudent(student);
            Assertions.assertNull(returnedStudent);

            Tema returnedTema = service.addTema(tema);
            Assertions.assertNull(returnedTema);

            Assertions.assertThrows(ValidationException.class, () -> {
                service.addNota(nota, "feedback");
            });
            //Nota returnedNota = service.addNota(nota, "feedback");
            //Assertions.assertNull(returnedNota);
            //Assertions.assertEquals(9.5, nota.getNota());
        } catch (Exception ve) {
            ve.printStackTrace();
        }
    }
}