package com.nipuream.annotation;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;


public class MyClass {

    public static void main(String[] args){

//        List<Descriptor> descriptors = new ArrayList<>();

//        Field[] fileds = PackageRule.class.getDeclaredFields();
//        for(Field field : fileds){
//            Type type = field.getGenericType();
//            String name = field.getName();
//            Protocol protocol = field.getAnnotation(Protocol.class);
//            if(protocol != null){
//                System.out.println("type = "+type.toString() + ", name = "+name + protocol.toString());
//                descriptors.add(new Descriptor(name,type,protocol.length(),
//                        protocol.type(),protocol.position()));
//            }
//        }
        /*
        ClassName type = ClassName.get("com.nipuream.apt_process","Descriptor");
        ClassName list = ClassName.get("java.util","List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");

        TypeName listType = ParameterizedTypeName.get(list, type);

        TypeSpec.Builder recorderBuilder = TypeSpec.classBuilder("Recorder")
                .addModifiers(Modifier.PUBLIC);

        FieldSpec descriptorSpec = FieldSpec.builder(listType,"descriptors",Modifier.PUBLIC,Modifier.STATIC)
                .build();

        TypeSpec recorder = recorderBuilder.addField(descriptorSpec)
                .build();

        JavaFile recorderFile = JavaFile.builder("com.nipuream.apt_process.protocol", recorder).build();
        File outputFile = new File("apt_process/src/main/java");
        try {
            recorderFile.writeTo(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

//        File outputFile = new File("apt_process/src/main/java");
//
//        TypeSpec.Builder deviceInfoBuilder = TypeSpec.classBuilder("DeviceInfo")
//                .addModifiers(Modifier.PUBLIC);
//
//        for(Descriptor descriptor : descriptors){
//
//            FieldSpec fieldSpec = FieldSpec.builder(descriptor.getType(),
//                    descriptor.getName(),Modifier.PUBLIC).build();
//            deviceInfoBuilder.addField(fieldSpec);
//        }
//
//        TypeSpec deviceInfo = deviceInfoBuilder.build();
//
//        JavaFile deviceInfoFile = JavaFile.builder("com.nipuream.apt_process.protocol",deviceInfo).build();
//        try {
//            deviceInfoFile.writeTo(outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
