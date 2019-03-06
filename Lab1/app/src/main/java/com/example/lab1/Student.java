package com.example.lab1;

public class Student extends Person {
    private int id;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public Student(String firstName,String lastName,int id){
       super(firstName,lastName);
       setId(id);
    }
}
