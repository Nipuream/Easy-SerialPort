package com.nipuream.apt_process;

import com.google.auto.service.AutoService;
import com.nipuream.annotation.Descriptor;
import com.nipuream.annotation.Protocol;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DeviceProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Protocol.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Protocol.class);
        if(elements.size() > 0){
            List<Descriptor> descriptors = new ArrayList<>();
            for(Element element : elements){
                messager.printMessage(Diagnostic.Kind.NOTE,"element : " + element.asType().toString());
                Protocol protocol = element.getAnnotation(Protocol.class);
                messager.printMessage(Diagnostic.Kind.NOTE,protocol.toString());


                descriptors.add(new Descriptor(element.getSimpleName().toString(),TypeName.get(element.asType()),
                        protocol.length(),protocol.type(),protocol.position()));
            }

            TypeSpec.Builder deviceInfoBuilder = TypeSpec.classBuilder("DeviceInfo")
                    .addModifiers(Modifier.PUBLIC);
            for(Descriptor descriptor : descriptors){
                FieldSpec fieldSpec = FieldSpec.builder(descriptor.getType(),
                        descriptor.getName(),Modifier.PUBLIC).build();
                deviceInfoBuilder.addField(fieldSpec);
            }

            TypeSpec deviceInfo = deviceInfoBuilder.build();
            JavaFile deviceInfoFile = JavaFile.builder("com.nipuream.easyserialport.protocol",deviceInfo).build();
            try {
                deviceInfoFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE,"no elements.");
        }

        return false;
    }
}
