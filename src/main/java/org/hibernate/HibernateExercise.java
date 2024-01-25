package org.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateExercise {
    static SessionFactory factory;

    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();

        // Add
        insertStudent("Antonio", "Martinez", "antonio@email.com", "971112312", 18);
        System.out.println("Student Antonio inserted successfully.");

        insertStudent("Diego", "Martinez", "diego@email.com", "971888828", 20);
        System.out.println("Student Diego inserted successfully.");

        insertStudent("Lucas", "Rodriguez", "lucas@email.com", "971822827", 19);
        System.out.println("Student Lucas inserted successfully.");

        insertStudent("Manolo", "Iglesias", "manolo@email.com", "971888821", 43);
        System.out.println("Student Manolo inserted successfully.");
        System.out.println();

        // Update
        updateStudent(1, "Change name", "Martinez", "antonio@email.com", "971112312");
        System.out.println("Updated Student: id 1, Martinez, antonio@email.com, 971112312");
        System.out.println();

        // Delete
        deleteStudent(2);
        System.out.println("Student deleted with id 2");
        System.out.println();

        // Get one item
        System.out.println("Get name of student 1: " + getStudent(1).getName());
        System.out.println();

        // Print all students
        System.out.println("Students: ");
        for (Student student : listStudents()) {
            System.out.println(student.getId() + " : " + student.getName());
        }

        factory.close();
    }

    // Add
    private static void insertStudent(String name, String lastName, String email, String phone, int age) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Student student = new Student(name, lastName, email, phone, age);
            session.save(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    // Update
    private static void updateStudent(int id, String name, String lastName, String email, String phone) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                student.setName(name);
                student.setLastname(lastName);
                student.setEmail(email);
                student.setPhone(phone);
                session.update(student);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Delete
    private static void deleteStudent(int id) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Get one item
    private static Student getStudent(int id) {
        Session session = factory.openSession();
        try {
            return session.get(Student.class, id);
        } finally {
            session.close();
        }
    }

    // Print all students
    private static List<Student> listStudents() {
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM Student", Student.class).list();
        } finally {
            session.close();
        }
    }
}
