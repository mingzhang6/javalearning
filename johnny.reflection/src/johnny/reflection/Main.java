package johnny.reflection;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Person person = new Person();
        person.name = "Johnny";
        Class cls = Class.forName("johnny.reflection.Person");

        System.out.println("Class Person: ");
        System.out.println("Class name: " + cls.getName());
        System.out.println("Class simple name: " + cls.getSimpleName());
        System.out.println("Class canonical name: " + cls.getCanonicalName());
        System.out.println("Class type name: " + cls.getTypeName());
        System.out.println("Is enum: " + cls.isEnum());
        System.out.println("Is array: " + cls.isArray());
        //#region 设置年龄
        java.lang.reflect.Method setAgeMethod = cls.getMethod("setAge", int.class);
        setAgeMethod.invoke(person, 100);
        //#endregion
        System.out.println("Johnny's age is " + person.getAge());

        System.out.println();
        Class cls2 = Class.forName("johnny.reflection.Gender");
        System.out.println("Enum Gender: ");
        System.out.println("Class name: " + cls2.getName());
        System.out.println("Class simple name: " + cls2.getSimpleName());
        System.out.println("Class canonical name: " + cls2.getCanonicalName());
        System.out.println("Class type name: " + cls2.getTypeName());
        System.out.println("Is enum: " + cls2.isEnum());
        System.out.println("Is array: " + cls2.isArray());

        System.out.println();
        Person[] people = new Person[1];

        people[0] = null;
        Class cls3 = people.getClass();
        System.out.println("Person[]: ");
        System.out.println("Class name: " + cls3.getName());
        System.out.println("Class simple name: " + cls3.getSimpleName());
        System.out.println("Class canonical name: " + cls3.getCanonicalName());
        System.out.println("Class type name: " + cls3.getTypeName());
        System.out.println("Is enum: " + cls3.isEnum());
        System.out.println("Is array: " + cls3.isArray());
    }
}

class Person{
    public String name;

    private int age;

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return this.age;
    }
}

enum Gender{
    Male,
    Female,
}