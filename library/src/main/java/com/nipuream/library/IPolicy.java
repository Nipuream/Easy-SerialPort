package com.nipuream.library;

public interface IPolicy {

    String header();

    void verificate(byte[] data);

}
