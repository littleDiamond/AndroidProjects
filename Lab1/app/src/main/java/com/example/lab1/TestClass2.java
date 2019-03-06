package com.example.lab1;

import java.util.*;


public class TestClass2 {
    public static ArrayList<Student> createStudent(){
        ArrayList<Student> studentList = new ArrayList<Student>();

        String answer="";

        Scanner input = new Scanner(System.in);
        while( true ){

            System.out.println("Enter student's first name: ");
            String firstName = input.next();
            System.out.println("Enter student's last name: ");
            String lastName = input.next();
            System.out.println("Enter student's ID: ");
            int id= input.nextInt();

            Student newStudent = new Student(firstName, lastName, id);
            studentList.add(newStudent);

            System.out.println("Enter another(y/n)?");
            answer = input.next();

            if ( answer.toLowerCase().equals("n") )
            {
                break;
            }
        }

        return studentList;
    }

    public static void main(String args[]){
        ArrayList<Student> studentList = createStudent();


        System.out.println("==========");
        System.out.println("student's ID | student's name");

        for (Student aStudent : studentList) {

            //
            System.out.println(aStudent.getId() + "\t\t" + aStudent .getFirstName() + " " + aStudent.getLastName());
        }

        System.out.println("==========");
    }
}
