package com.smorales.unix;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.channels.Channels;

public class UnixTest {

    public static void main(String[] args) {
        try {
            File path = new java.io.File("/var/run/docker.sock");
            UnixSocketAddress address = new UnixSocketAddress(path);
            UnixSocketChannel channel = UnixSocketChannel.open(address);
            System.out.println("connected to " + channel.getRemoteSocketAddress());
            PrintWriter w = new PrintWriter(Channels.newOutputStream(channel));
            w.print("GET /images/json HTTP/1.1\r\n");
            w.print("Host: http\r\n");
            w.print("Accept: */*\r\n\r\n");
            w.flush();

            InputStreamReader r = new InputStreamReader(Channels.newInputStream(channel));
            CharBuffer result = CharBuffer.allocate(1024);
            r.read(result);
            result.flip();
            System.out.println("read from server: " + result.toString());

            if (channel.isConnected()) {
                channel.finishConnect();
            }
            System.out.println("finished connection");
        } catch (Exception ex) {
            System.out.println("exception:::" + ex);
        }
    }
}
