package zz.indi.dayi;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshConnection {

    //跳板机
    String username;
    String password;
    String host;
    int port = 22;

    //本地连接端口
    int local_port = 3307;
    //远程数据库
    String remote_host;
    int remote_port = 3306;
    Session session;

    public SshConnection() {

    }

    public SshConnection(String host, String username, String password, Integer port,
                         String remoteHost, int remotePort, int localPort) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.remote_host = remoteHost;
        this.remote_port = remotePort;
        this.local_port = localPort;
    }

    /**
     * 建设SSH连贯
     */
    public void init() {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(local_port, remote_host, remote_port);
            System.out.println("ssh隧道配置： localhost:" + port + " -> " + remote_host + ":" + remote_port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 断开SSH连贯
     */
    public void destroy() {
        this.session.disconnect();
    }
}