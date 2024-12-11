// socket 1 , des strings , chaine de caractères (commande)
// socket 2 , octets , (données)

//retour a la ligne termine par 10 / 13 --> \r\n

/* 
Socket socket =  ;

InputStream in = socket.getInputStream();
Scanner scanner = new Scanner(in);
String str = scanner.nextline();

OutputStream out = socket.getOutputStream();
String str = "helloguys\r\n" ;
out.write(str.getBytes());
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class FTPServer {

    private static final int PORT = 2121;
    private static final String USERNAME = "miage";
    private static final String PASSWORD = "car";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur FTP démarré sur le port " + PORT);

            while (true) {
                Socket client = serverSocket.accept();
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                out.write("220 Service Ready\r\n".getBytes());
                Scanner scanner = new Scanner(in);
                String str = scanner.nextLine();
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

