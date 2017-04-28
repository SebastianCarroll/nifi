package org.apache.nifi.processors.mqtt.common;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SSLTunnelSocketFactory extends SSLSocketFactory {
    @Override
    public String[] getDefaultCipherSuites() {
        return socketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return socketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    public Socket createSocket(Socket socket, InputStream inputStream, boolean b) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    public Socket createSocket() throws IOException {
        return new SSLTunnelSocket(socketFactory, proxyHost, proxyPort);
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        throw new NotImplementedException();
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
        throw new NotImplementedException();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        throw new NotImplementedException();
    }

    private final SSLSocketFactory socketFactory;
    private final String proxyHost;
    private final int proxyPort;

    public SSLTunnelSocketFactory(SSLSocketFactory socketFactory, String proxyHost, int proxyPort) {

        this.socketFactory = socketFactory == null ? (SSLSocketFactory) SSLSocketFactory.getDefault() : socketFactory;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }
}
