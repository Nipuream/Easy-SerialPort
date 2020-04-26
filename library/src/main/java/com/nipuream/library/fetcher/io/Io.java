package com.nipuream.library.fetcher.io;

import java.io.IOException;

/**
 * Created by yanghui11 on 2020/4/16.
 */

public interface Io {

    int read(byte[] buf) throws IOException;

    int read(byte[] buf, int offset, int count) throws IOException;

    void write(byte[] buf) throws IOException;

    void close() throws IOException;

    int available() throws IOException;
}
