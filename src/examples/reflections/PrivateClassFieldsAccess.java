package examples.reflections;

import java.lang.reflect.Field;

public class PrivateClassFieldsAccess {

    public static void main(String... args) throws NoSuchFieldException, IllegalAccessException {
        final Test t = new Test();
        t.setName("dddd");
        t.setIntFiled(3232);

        Class clazz = t.getClass();

        for (Field f: clazz.getDeclaredFields()){
            System.out.println(f.get(t));
        }

    }

    public static class Test {
        private String name;
        private int intFiled;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIntFiled() {
            return intFiled;
        }

        public void setIntFiled(int intFiled) {
            this.intFiled = intFiled;
        }
    }


}
