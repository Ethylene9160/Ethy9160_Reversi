import java.io.*;
import java.net.*;


/**
 * 这是一个用于测试我们能否获取用户地址的一个测试类。
 * <p>
 * 它不能发送任何消息，仅供测试。
 * 除了获得您的ip地址之外，没有任何的功能。
 * <p>
 * 感谢您的参与。
 * @author Ethylene9160
 */
public class WebTestForAllUsers {

    private DataOutputStream outputStream;

    public static void main(String[] args) throws Exception {
        new WebTestForAllUsers();
    }
    public WebTestForAllUsers() throws Exception{

        Socket clientSocket = new Socket("127.0.0.1", 9999);
        System.out.println("连接成功！");

        this.outputStream = new DataOutputStream(clientSocket.getOutputStream());
        String str = clientSocket.getLocalAddress().toString();

        outputStream.writeUTF(str);
        System.out.println(str);

        outputStream.close();
        clientSocket.close();
    }
}
