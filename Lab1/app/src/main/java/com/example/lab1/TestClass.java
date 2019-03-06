package com.example.lab1;

import java.util.Scanner;

public class TestClass {
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter student's first name: ");
        String firstName = input.next();
        System.out.println("Enter student's last name: ");
        String lastName = input.next();
        System.out.println("Enter student's ID: ");
        int id= input.nextInt();

        // create a student object
        Student newStudent = new Student(firstName, lastName, id);

        System.out.println("Student's detail: ");
        System.out.println("Student's ID: " + newStudent.getId());
        System.out.println("Student's Name: " +newStudent.getFirstName() + " " + newStudent.getLastName());
    }

}
