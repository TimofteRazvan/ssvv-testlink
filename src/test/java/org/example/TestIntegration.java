package org.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntegration
{
    public static Service service;

    @BeforeAll
    public static void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        TestIntegration.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void test1_addStudent_ValidData_Success()
    {
        Student testStudent = new Student("test_id", "Test Student", 937, "test_email@stud.ubbcluj.ro");
        try {
            service.addStudent(testStudent);
            assertTrue( true );
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
    }

    @Test
    public void test2_addTema_ValidData_Success() {
        Tema tema = new Tema("101", "test", 12, 1);
        try {
            service.addTema(tema);
            assertTrue( true );
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
    }

    @Test
    public void test3_addNota_ValidData_Success() {
        String notaId = "nt1";
        double notaVal = 9.5;

        Nota nota = new Nota(notaId, "1", "1", notaVal, LocalDate.now());
        try {
            service.addNota(nota, "feedback");
            assertTrue( true );
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
    }

    @Test
    public void test4_fullIntegration_Success(){
        Student testStudent = new Student("test_id1", "Test Student", 937, "test_email@stud.ubbcluj.ro");
        service.addStudent(testStudent);

        Tema tema = new Tema("1057", "test", 2, 1);
        service.addTema(tema);

        assert(service.findTema("1057") != null);
        Nota nota = new Nota("101","test_id1","1057",9.3, LocalDate.now());
        service.addNota(nota,"feedback");

        Nota nota1 = service.findNota("101");
        assert(nota1.equals(nota));
    }

    @AfterEach
    public void cleanup(){
        service.deleteStudent("test_id1");
        service.deleteTema("1057");
        service.deleteNota("nt1");
        service.deleteNota("101");
    }
}