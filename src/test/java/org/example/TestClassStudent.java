package org.example;

import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import static org.junit.jupiter.api.Assertions.*;

public class TestClassStudent
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
        TestClassStudent.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void test1_addStudent_ValidStudent_Success()
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
    public void test2_addStudent_IdEmpty_Fail()
    {
        Student testStudent = new Student("", "Test Student", 937, "test_email@stud.ubbcluj.ro");
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test3_addStudent_IdNull_Fail()
    {
        Student testStudent = new Student(null, "Test Student", 937, "test_email@stud.ubbcluj.ro");
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test4_addStudent_IdNotUnique_Fail()
    {
        Student testStudent = new Student("test_id", "Test Student N", 937, "test_email@stud.ubbcluj.ro");
        try {
            Student result = service.addStudent(testStudent);
            assert(result == testStudent);
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test5_addStudent_NameNull_Fail()
    {
        Student testStudent = new Student("test_id_1", null, 937, "test_email@stud.ubbcluj.ro");
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test6_addStudent_NameEmpty_Fail()
    {
        Student testStudent = new Student("test_id_2", "", 937, "test_email@stud.ubbcluj.ro");
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test7_addStudent_GroupNegative_Fail()
    {
        Student testStudent = new Student("test_id_3", "Test Student", -937, "test_email@stud.ubbcluj.ro");
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test8_addStudent_EmailEmpty_Fail()
    {
        Student testStudent = new Student("test_id_4", "Test Student", 937, "");
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }

    @Test
    public void test9_addStudent_EmailNull_Fail()
    {
        Student testStudent = new Student("test_id_5", "Test Student", 937, null);
        try {
            service.addStudent(testStudent);
            fail();
        } catch (Exception e){
            System.out.println(e);
            assertTrue( true );
        }
    }


}