package com.smorales.javalab.business.containers.boundary;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.channels.Channels;

public class ContainerControllerTest {
    public static void main(String[] args) throws IOException {
        java.io.File path = new java.io.File("/var/run/docker.sock");
        String data = "";
        UnixSocketAddress address = new UnixSocketAddress(path);
        UnixSocketChannel channel = UnixSocketChannel.open(address);
        System.out.println("connected to " + channel.getRemoteSocketAddress());
        PrintWriter w = new PrintWriter(Channels.newOutputStream(channel));
        w.print("GET /images/json localhost HTTP/1.1\r\n");
        w.print("Host: http\r\n\r\n");

//        w.print(data);
        w.flush();

        InputStreamReader r = new InputStreamReader(Channels.newInputStream(channel));
        CharBuffer result = CharBuffer.allocate(1024);
        r.read(result);
        result.flip();
        System.out.println("read from server: " + result.toString());
        if (!result.toString().equals(data)) {
            System.out.println("ERROR: data mismatch");
        } else {
            System.out.println("SUCCESS");
        }
    }
}