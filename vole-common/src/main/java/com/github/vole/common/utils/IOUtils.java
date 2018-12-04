package com.github.vole.common.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * IO 操作工具类
 */
public abstract class IOUtils {
    protected static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
    protected static final int DEFUALT_CHAR_BUFFER_SIZE = 100;

    /**
     * {@link #flow(InputStream, OutputStream, byte[], boolean, boolean)}
     */
    public static void flow(InputStream is, OutputStream os, boolean closeIn, boolean closeOut) throws IOException {
        byte[] buffer = new byte[DEFAULT_BYTE_BUFFER_SIZE];
        flow(is, os, buffer, closeIn, closeOut);
    }

    /**
     * 该方法不关闭流
     *
     * @param is     输入流
     * @param os     输出流
     * @param buffer
     * @throws IOException
     */
    public static void flow(InputStream is, OutputStream os, byte[] buffer, boolean closeIn, boolean closeOut) throws IOException {
        try {
            int readed;
            while (-1 < (readed = is.read(buffer))) {
                os.write(buffer, 0, readed);
            }
            os.flush();
        } finally {
            if (closeIn) {
                close(is);
            }

            if (closeOut) {
                close(os);
            }
        }
    }

    /**
     * {@link #flow(Reader, Writer, char[], boolean, boolean)}
     */
    public static void flow(Reader reader, Writer writer, boolean closeIn, boolean closeOut) throws IOException {
        char[] buffer = new char[DEFUALT_CHAR_BUFFER_SIZE];
        flow(reader, writer, buffer, closeIn, closeOut);
    }

    /**
     * 该方法不关闭流
     *
     * @param reader
     * @param writer
     * @param buffer
     * @throws IOException
     */
    public static void flow(Reader reader, Writer writer, char[] buffer, boolean closeIn, boolean closeOut) throws IOException {
        try {
            int readed;
            while (-1 < (readed = reader.read(buffer))) {
                writer.write(buffer, 0, readed);
            }
            writer.flush();
        } finally {
            if (closeIn) {
                close(reader);
            }
            if (closeOut) {
                close(writer);
            }
        }
    }


    /**
     * {@link #flow(ReadableByteChannel, WritableByteChannel, ByteBuffer, boolean, boolean)}
     */
    public static void flow(ReadableByteChannel rbc, WritableByteChannel wbc, boolean closeIn, boolean closeOut) throws IOException {
        flow(rbc, wbc, null, closeIn, closeOut);
    }

    /**
     * {@link FileInputStream#getChannel()}
     * {@link FileOutputStream#getChannel()}
     * {@link java.nio.channels.Channels#newChannel(InputStream)}
     * {@link java.nio.channels.Channels#newChannel(OutputStream)}
     * {@link ReadableByteChannel}
     * {@link WritableByteChannel}
     *
     * @param rbc
     * @param wbc
     * @param buffer
     * @throws IOException
     */
    public static void flow(ReadableByteChannel rbc, WritableByteChannel wbc, ByteBuffer buffer, boolean closeIn, boolean closeOut) throws IOException {
        if (buffer == null || buffer.capacity() < 1) {
            buffer = ByteBuffer.allocate(DEFAULT_BYTE_BUFFER_SIZE);
        }
        try {
            while (-1 < rbc.read(buffer)) {
                buffer.flip();
                wbc.write(buffer);
                buffer.clear();
            }
        } finally {
            if (closeIn) {
                close(rbc);
            }
            if (closeOut) {
                close(wbc);
            }
        }
    }

    /**
     * 从输入流中读入数据将缓冲区填满
     *
     * @param is
     * @param buff
     * @param closeIn
     * @throws IOException
     */
    public static void readFully(InputStream is, byte[] buff, boolean closeIn) throws IOException {
        readFully(is, buff, 0, closeIn);
    }

    /**
     * 从输入流中读入数据将缓冲区填满
     *
     * @param is
     * @param buff
     * @param offset
     * @param closeIn
     * @throws IOException
     */
    public static void readFully(InputStream is, byte[] buff, int offset, boolean closeIn) throws IOException {
        int length = buff.length - offset;
        int actual = read(is, buff, offset, length, closeIn);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    /**
     * 读取给定长度字节到缓冲区,返回读取的字节数
     *
     * @param is
     * @param buff
     * @param offset
     * @param length
     * @param closeIn
     * @return
     * @throws IOException
     */
    public static int read(InputStream is, byte[] buff, int offset, final int length, boolean closeIn) throws IOException {
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than or equal 0");
        }

        int remaining = length;
        while (remaining > 0) {
            int location = length - remaining;
            int readed = is.read(buff, offset + location, remaining);
            if (-1 == readed) {
                break;
            }
            remaining -= readed;
        }

        if (closeIn) {
            close(is);
        }

        return length - remaining;
    }

    public static byte[] readBytes(InputStream is, boolean closeIn) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

        flow(is, baos, closeIn, true);
        return baos.toByteArray();
    }

    public static String toString(InputStream is, Charset charset, boolean closeIn) throws IOException {
        Reader reader = charset == null ? new InputStreamReader(is) : new InputStreamReader(is, charset);
        return toString(reader, closeIn);
    }

    public static String toString(Reader reader, boolean closeIn) throws IOException {
        StringWriter writer = new StringWriter();
        flow(reader, writer, closeIn, true);

        return writer.toString();
    }

    public static String toString(File file) throws IOException {
        return toString(file, Charset.defaultCharset());
    }

    public static String toString(File file, Charset charset) throws IOException {
        return toString(new FileInputStream(file), charset, true);
    }

    public static void write(CharSequence charseq, OutputStream os, Charset charset, boolean closeOut) throws IOException {
        Writer writer = charset == null ? new OutputStreamWriter(os) : new OutputStreamWriter(os, charset);
        write(charseq, writer, closeOut);
    }

    public static void write(CharSequence charseq, Writer writer, boolean closeOut) throws IOException {
        try {
            writer.write(charseq.toString());
            writer.flush();
        } finally {
            if (closeOut) {
                close(writer);
            }
        }
    }

    public static BufferedInputStream toBuffered(InputStream is) {
        return null == is || is instanceof BufferedInputStream ? (BufferedInputStream) is : new BufferedInputStream(is);
    }

    public static BufferedReader toBuffered(Reader reader) {
        return null == reader || reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }


    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignore) {
            }
        }
    }

    private IOUtils() {
    }
}