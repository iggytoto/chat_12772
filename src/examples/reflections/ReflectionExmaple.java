package examples.reflections;

import com.mysql.cj.xdevapi.Table;
import org.apache.poi.ss.formula.functions.T;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionExmaple {


    public static void main(String... args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {



        Class tClass = Class.forName("examples.reflections.ReflectionExmaple$Cat");


        while (true){

        }

    }


    @Dddd
   public static class Cat extends Animal {




        @Dddd
        private Cat(String name) {
            super(name);
        }

        @Override
        @Dddd
        public void doVoice() {
            System.out.println("meow meow meow");
        }


    }


    public static class Dog extends Animal {

        public Dog(String name) {
            super(name);
        }

        @Override
        public void doVoice() {
            System.out.println("gaf gaf gaf");
        }
    }

    public static abstract class Animal implements Voice {

        @Dddd
        protected String name;

        protected Animal(String name){
            this.name = name;
        }

    }

    public interface Voice {

        void doVoice();

    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Dddd{

    }

}

