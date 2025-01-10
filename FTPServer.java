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
    private static final String USER = "miage";
    private static final String PASSWORD = "car";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur FTP démarré sur le port " + PORT);

            Socket client = serverSocket.accept();
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            out.write("220 Service Ready\r\n".getBytes());
            Scanner scanner = new Scanner(in);
            boolean isAuthenticated = false;
            while (true) {
                String str = scanner.nextLine();
                System.out.println(str);
                if (!isAuthenticated) {
                    if (str.startsWith("USER")) {
                        String user = str.substring(5).trim();
                        out.write("331 Password required\r\n".getBytes());
                    }
                    else if(str.startsWith("PASS")){
                        String password = str.substring(5).trim();
                        if (PASSWORD.equals(password)) {
                            isAuthenticated = true;
                            out.write("230 User logged in proceed\r\n".getBytes());
                        }
                        else {
                            out.write("530 Login incorrect\r\n".getBytes());
                        }
                    }
                    else if (str.equalsIgnoreCase("QUIT")){
                        out.write("221 Goodbye\r\n".getBytes());
                        break;
                    }

                    else {
                        out.write("530 Incorrect authentication please login with USER and PASS\r\n".getBytes());
                    }

                }

            }
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

