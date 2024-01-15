package examples.references;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;

public class SoftCachedImageProcessor {


    public static void main(String... args){
        StringBuilder a = new StringBuilder();
        WeakReference<StringBuilder> wr = new WeakReference<>(a);
        a = null;

        //StringBuilder b = wr.get();
        //if(b!=null){

        //} else{

        //}











    }

}
