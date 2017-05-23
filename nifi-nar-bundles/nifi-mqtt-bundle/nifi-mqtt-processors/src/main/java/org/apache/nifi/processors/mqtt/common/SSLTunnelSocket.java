package org.apache.nifi.processors.mqtt.common;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.channels.SocketChannel;

public class SSLTunnelSocket extends SSLSocket {
    private String tunnelHost;
    private int tunnelPort;
    private SSLSocketFactory dfactory;
    private String tunnelPassword;
    private String tunnelUserName;

    @Override
    public String[] getSupportedCipherSuites() {
        return dfactory.getSupportedCipherSuites();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return innerSocket.getEnabledCipherSuites();
    }

    @Override
    public void setEnabledCipherSuites(String[] strings) {
        innerSocket.setEnabledCipherSuites(strings);
    }

    @Override
    public String[] getSupportedProtocols() {
        return innerSocket.getSupportedProtocols();
    }

    @Override
    public String[] getEnabledProtocols() {
        return innerSocket.getEnabledProtocols();
    }

    @Override
    public void setEnabledProtocols(String[] strings) {
        innerSocket.setEnabledProtocols(strings);
    }

    @Override
    public SSLSession getSession() {
        return innerSocket.getSession();
    }

    @Override
    public SSLSession getHandshakeSession() {
        return innerSocket.getHandshakeSession();
    }

    @Override
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        innerSocket.addHandshakeCompletedListener(handshakeCompletedListener);
    }

    @Override
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        innerSocket.removeHandshakeCompletedListener(handshakeCompletedListener);
    }

    @Override
    public void startHandshake() throws IOException {
        innerSocket.startHandshake();
    }

    @Override
    public void setUseClientMode(boolean b) {
        innerSocket.setUseClientMode(b);
    }

    @Override
    public boolean getUseClientMode() {
        return innerSocket.getUseClientMode();
    }

    @Override
    public void setNeedClientAuth(boolean b) {
        innerSocket.setNeedClientAuth(b);
    }

    @Override
    public boolean getNeedClientAuth() {
        return innerSocket.getNeedClientAuth();
    }

    @Override
    public void setWantClientAuth(boolean b) {
        innerSocket.setWantClientAuth(b);
    }

    @Override
    public boolean getWantClientAuth() {
        return innerSocket.getWantClientAuth();
    }

    @Override
    public void setEnableSessionCreation(boolean b) {
        innerSocket.setEnableSessionCreation(b);
    }

    @Override
    public boolean getEnableSessionCreation() {
        return innerSocket.getEnableSessionCreation();
    }

    @Override
    public SSLParameters getSSLParameters() {
        return innerSocket.getSSLParameters();
    }

    @Override
    public void setSSLParameters(SSLParameters sslParameters) {
        innerSocket.setSSLParameters(sslParameters);
    }

    @Override
    public void connect(SocketAddress endpoint) throws IOException {
        connect(endpoint, 15*1000);
    }

    @Override
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        Socket tunnel = new Socket(tunnelHost, tunnelPort);

        InetSocketAddress inetSocketAddress = (InetSocketAddress) endpoint;

        doTunnelHandshake(tunnel, inetSocketAddress.getHostName(), inetSocketAddress.getPort());

        innerSocket = (SSLSocket) dfactory.createSocket(tunnel, inetSocketAddress.getHostName(), inetSocketAddress.getPort(), true);
        innerSocket.addHandshakeCompletedListener(
                event -> {
                    System.out.println("Handshake Finished!");
                    System.out.println("\t CipherSuite :" + event.getCipherSuite());
                    System.out.println("\t SessionId: " + event.getSession());
                    System.out.println("\t PeerHost: " + event.getSession().getPeerHost());
                });
        // thanks to David Lord in the java forums for figuring out this line is the problem
        // innerSocket.startHandshake(); //this line is the bug which stops Tip111 from working correctly
    }

    @Override
    public void bind(SocketAddress bindpoint) throws IOException {
        innerSocket.bind(bindpoint);
    }

    @Override
    public InetAddress getInetAddress() {
        return innerSocket.getInetAddress();
    }

    @Override
    public InetAddress getLocalAddress() {
        return innerSocket.getLocalAddress();
    }

    @Override
    public int getPort() {
        return innerSocket.getPort();
    }

    @Override
    public int getLocalPort() {
        return innerSocket.getLocalPort();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return innerSocket.getRemoteSocketAddress();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return innerSocket.getLocalSocketAddress();
    }

    @Override
    public SocketChannel getChannel() {
        return innerSocket.getChannel();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return innerSocket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return innerSocket.getOutputStream();
    }

    @Override
    public void setTcpNoDelay(boolean on) throws SocketException {
        innerSocket.setTcpNoDelay(on);
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        return innerSocket.getTcpNoDelay();
    }

    @Override
    public void setSoLinger(boolean on, int linger) throws SocketException {
        innerSocket.setSoLinger(on, linger);
    }

    @Override
    public int getSoLinger() throws SocketException {
        return innerSocket.getSoLinger();
    }

    @Override
    public void sendUrgentData(int data) throws IOException {
        innerSocket.sendUrgentData(data);
    }

    @Override
    public void setOOBInline(boolean on) throws SocketException {
        innerSocket.setOOBInline(on);
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        return innerSocket.getOOBInline();
    }

    @Override
    public void setSoTimeout(int timeout) throws SocketException {
        innerSocket.setSoTimeout(timeout);
    }

    @Override
    public int getSoTimeout() throws SocketException {
        return innerSocket.getSoTimeout();
    }

    @Override
    public void setSendBufferSize(int size) throws SocketException {
        innerSocket.setSendBufferSize(size);
    }

    @Override
    public int getSendBufferSize() throws SocketException {
        return innerSocket.getSendBufferSize();
    }

    @Override
    public void setReceiveBufferSize(int size) throws SocketException {
        innerSocket.setReceiveBufferSize(size);
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        return innerSocket.getReceiveBufferSize();
    }

    @Override
    public void setKeepAlive(boolean on) throws SocketException {
        innerSocket.setKeepAlive(on);
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        return innerSocket.getKeepAlive();
    }

    @Override
    public void setTrafficClass(int tc) throws SocketException {
        innerSocket.setTrafficClass(tc);
    }

    @Override
    public int getTrafficClass() throws SocketException {
        return innerSocket.getTrafficClass();
    }

    @Override
    public void setReuseAddress(boolean on) throws SocketException {
        innerSocket.setReuseAddress(on);
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        return innerSocket.getReuseAddress();
    }

    @Override
    public void close() throws IOException {
        innerSocket.close();
    }

    @Override
    public void shutdownInput() throws IOException {
        innerSocket.shutdownInput();
    }

    @Override
    public void shutdownOutput() throws IOException {
        innerSocket.shutdownOutput();
    }

    @Override
    public String toString() {
        return innerSocket.toString();
    }

    @Override
    public boolean isConnected() {
        return innerSocket.isConnected();
    }

    @Override
    public boolean isBound() {
        return innerSocket.isBound();
    }

    @Override
    public boolean isClosed() {
        return innerSocket.isClosed();
    }

    @Override
    public boolean isInputShutdown() {
        return innerSocket.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        return innerSocket.isOutputShutdown();
    }

    public static void setSocketImplFactory(SocketImplFactory fac) throws IOException {
        Socket.setSocketImplFactory(fac);
    }

    @Override
    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        innerSocket.setPerformancePreferences(connectionTime, latency, bandwidth);
    }

    private SSLSocket innerSocket;

    /**
     *  Constructor for the SSLTunnelSocketFactory object
     *
     *@param  proxyHost  The url of the proxy host
     *@param  proxyPort  the port of the proxy
     */
    public SSLTunnelSocket(SSLSocketFactory socketFactory, String proxyHost, int proxyPort) {
        System.err.println("creating Socket Factory");
        tunnelHost = proxyHost;
        tunnelPort = proxyPort;
        dfactory = socketFactory == null ? (SSLSocketFactory) SSLSocketFactory.getDefault() : socketFactory;
    }


    /**
     *  Constructor for the SSLTunnelSocketFactory object
     *
     *@param  proxyHost      The url of the proxy host
     *@param  proxyPort      the port of the proxy
     *@param  proxyUserName  username for authenticating with the proxy
     *@param  proxyPassword  password for authenticating with the proxy
     */
    public SSLTunnelSocket(SSLSocketFactory socketFactory, String proxyHost, int proxyPort, String proxyUserName, String proxyPassword) {
        this(socketFactory, proxyHost, proxyPort);
        tunnelUserName = proxyUserName;
        tunnelPassword = proxyPassword;
    }

    /**
     *  Sets the proxyUserName attribute of the SSLTunnelSocketFactory object
     *
     *@param  proxyUserName  The new proxyUserName value
     */
    public void setProxyUserName(String proxyUserName) {
        tunnelUserName = proxyUserName;
    }


    /**
     *  Sets the proxyPassword attribute of the SSLTunnelSocketFactory object
     *
     *@param  proxyPassword  The new proxyPassword value
     */
    public void setProxyPassword(String proxyPassword) {
        tunnelPassword = proxyPassword;
    }

    /**
     *  Description of the Method
     *
     *@param  tunnel           tunnel socket
     *@param  host             destination host
     *@param  port             destination port
     *@exception  IOException  raised when an IO error occurs
     */
    private void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
        OutputStream out = tunnel.getOutputStream();
        //generate connection string
        String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n"
                + "User-Agent: "
                + sun.net.www.protocol.http.HttpURLConnection.userAgent;
        if (tunnelUserName != null && tunnelPassword != null) {
            //add basic authentication header for the proxy
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String encodedPassword = enc.encode((tunnelUserName + ":" + tunnelPassword).getBytes());
            msg = msg + "\nProxy-Authorization: Basic " + encodedPassword;
        }
        msg = msg + "\nContent-Length: 0";
        msg = msg + "\nPragma: no-cache";

        msg = msg + "\r\n\r\n";

        System.err.println(msg);
        byte b[];
        try {
            //we really do want ASCII7 as the http protocol doesnt change with locale
            System.err.println("");
            b = msg.getBytes("ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            //If ASCII7 isn't there, something is seriously wrong!
            System.err.println("Ascii failed - getting raw bytes");
            b = msg.getBytes();
        }

        System.err.println("Writing bytes to tunnel out: " + out.toString());
        out.write(b);
        System.err.println("Flushing");
        out.flush();

        byte reply[] = new byte[200];
        int replyLen = 0;
        int newlinesSeen = 0;
        boolean headerDone = false;

        System.err.println("Getting tunnel input stream");
        InputStream in = tunnel.getInputStream();
        boolean error = false;

        while (newlinesSeen < 2) {
            int i = in.read();
            if (i < 0) {
                throw new IOException("Unexpected EOF from Proxy");
            }
            if (i == '\n') {
                headerDone = true;
                ++newlinesSeen;
            } else
            if (i != '\r') {
                newlinesSeen = 0;
                if (!headerDone && replyLen < reply.length) {
                    reply[replyLen++] = (byte) i;
                }
            }
        }

        System.err.println("Finished reading lines, newlines seen: " + newlinesSeen);

        //convert byte array to string
        String replyStr;
        try {
            replyStr = new String(reply, 0, replyLen, "ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            replyStr = new String(reply, 0, replyLen);
        }

        System.err.println("Reply: " + replyStr);

        //we check for connection established because our proxy returns http/1.1 instead of 1.0
        if (replyStr.toLowerCase().indexOf("200 connection established") == -1) {
            System.err.println(replyStr);
            throw new IOException("Unable to tunnel through " + tunnelHost + ":" + tunnelPort + ". Proxy returns\"" + replyStr + "\"");
        }
        //tunneling hanshake was successful
        System.err.println("Tunnen handshake was successful");
    }
}