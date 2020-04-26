package com.nipuream.easyserialport;


import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parseProtocol(){

//        Field[] fileds = PackageRule.class.getDeclaredFields();
//        for(Field field : fileds){
//            Type type = field.getGenericType();
//            String name = field.getName();
//            Protocol protocol = field.getAnnotation(Protocol.class);
//            if(protocol != null){
//                System.out.println("type = "+type.toString() + ", name = "+name + protocol.toString());
//            }
//        }

//        MethodSpec main = MethodSpec.methodBuilder("show")
//                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
//                .addStatement("$T.out.println($S)",System.class,"Hello World")
//                .build();
//
//        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
//                .addModifiers(Modifier.PUBLIC)
//                .addMethod(main)
//                .build();
//
//        JavaFile javaFile = JavaFile.builder("com.nipuream.easyserialport",helloWorld).build();
//        File outputFile = new File("src/");
//        try {
//            javaFile.writeTo(outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}