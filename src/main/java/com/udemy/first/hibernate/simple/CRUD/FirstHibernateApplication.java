package com.udemy.first.hibernate.simple.CRUD;

import com.udemy.first.hibernate.simple.CRUD.models.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.List;

@SpringBootApplication
public class FirstHibernateApplication {

    public static void main(String[] args) {


        //Configure session factory
        SessionFactory factory= new Configuration()
                                .configure("hibernate.cfg.xml")
                                .addAnnotatedClass(Student.class)
                                .buildSessionFactory();
        //create session
        Session session=factory.getCurrentSession();


        try{
            System.out.println("Creating new student!");
            Student student=new Student("Nikoloz","Latsabidze","nikalatsabidze@yahoo.com");
            Student student2=new Student("Nikoloz2","Kokaia","nikalatsabidze@gmail.com");
            Student student3=new Student("Nikoloz3","Latsabidze","nikalatsabidze@gmail.com");

            session.beginTransaction();

            session.save(student);
            session.save(student2);
            session.save(student3);
            session.getTransaction().commit();
            //Get All students
            session=factory.getCurrentSession();
            session.beginTransaction();
            System.out.println("------------ALLL ------------");
            List<Student> studentList=session.createQuery("from Student ").getResultList();
            for(Student st:studentList)
                System.out.println(st);

            //Get Only students whose last name is something
            System.out.println("------------LAST NAME ------------");
            studentList=session.createQuery("from Student s where s.lastName='Latsabidze'").getResultList();
            for(Student st:studentList)
                System.out.println(st);
            //Get Students whose email ends with @gmail.com
            System.out.println("------------EMAIL ------------");
            studentList=session.createQuery("from Student s where s.email LIKE '%@gmail.com'").getResultList();
            for(Student st:studentList)
                System.out.println(st);
            //Update by id no need to save student it will be automatically updated after commit
            Student updateStudent=session.get(Student.class,2);
            updateStudent.setEmail("test@test.com");

            //Update all students
            session.createQuery("update Student set email='Test@test.com'")
                    .executeUpdate();

            //Delete by ID
            Student deleteStudent=session.get(Student.class,1);

            session.delete(deleteStudent);
            //Delete with query
            session
                    .createQuery("delete from Student where id=2")
                    .executeUpdate();

            session.getTransaction().commit();
            System.out.println("Done!");
        }
        finally {
            factory.close();
        }
    }
}
