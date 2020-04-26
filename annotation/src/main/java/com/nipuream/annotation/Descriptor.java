package com.nipuream.annotation;

import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;

public class Descriptor {

    private String name;
    private TypeName type;
    private int length;
    private int bitType;
    private int position;

    public Descriptor(String name,TypeName type, int length, int bitType, int position) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.bitType = bitType;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public TypeName getType() {
        return type;
    }

    public int getLength() {
        return length;
    }


    public int getBitType() {
        return bitType;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Descriptor{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", length=" + length +
                ", bitType=" + bitType +
                ", position=" + position +
                '}';
    }
}
