// socket 1 , des strings , chaine de caractères (stre)
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
    private static ServerSocket initialServer;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur FTP démarré sur le port " + PORT);

            Socket client = serverSocket.accept();
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            out.write("220 Service Ready\r\n".getBytes());
            Scanner scanner = new Scanner(in);
            boolean isAuthenticated = false;
            String user = null;
            boolean start = true;
            while (start) {
                String str = scanner.nextLine();
                System.out.println(str);
                if (!isAuthenticated) {
                    if (str.startsWith("USER")) {
                        user = str.substring(5).trim();
                        out.write("331 Password required\r\n".getBytes());
                    }
                    else if(str.startsWith("PASS")){
                        String password = str.substring(5).trim();
                        if (USER.equals(user) && PASSWORD.equals(password)) {
                            isAuthenticated = true;
                            out.write("230 User logged in proceed\r\n".getBytes());
                        }
                        else {
                            out.write("530 Login incorrect\r\n".getBytes());
                        }
                    }
                
                    else {
                        out.write("530 Please login with USER and PASS\r\n".getBytes());
                    }
                }
                else {  
                    if (str.equalsIgnoreCase("QUIT")){
                        client.close();
                        serverSocket.close();
                        start = false;
                    }
                    else if (str.equalsIgnoreCase("EPSV")) {
                        initialServer = new ServerSocket(0);
                        int dataPort = initialServer.getLocalPort();
                        String epsvResponse = "229 Entering Extended Passive Mode (|||" + dataPort + "|)\r\n";
                        out.write(epsvResponse.getBytes());
                    } 
                    else if (str.startsWith("RETR ")) {
                        String fileName = str.substring(5).trim();
                        File file = new File(fileName);

                        if (!file.exists()) {
                            out.write("550 File not found\r\n".getBytes());
                            continue;
                        }

                        if (initialServer == null || initialServer.isClosed()) {
                            initialServer = new ServerSocket(0);
                            int dataPort = initialServer.getLocalPort();
                            String epsvResponse = "229 Entering Extended Passive Mode (|||" + dataPort + "|)\r\n";
                            out.write(epsvResponse.getBytes());
                        }

                        out.write("150 Opening data connection\r\n".getBytes());

                        try (
                            Socket dataSocket = initialServer.accept();
                            FileInputStream fileInput = new FileInputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(fileInput);
                            OutputStream dataOut = dataSocket.getOutputStream()
                        ) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = bis.read(buffer)) != -1) {
                                dataOut.write(buffer, 0, bytesRead);
                            }
                            dataOut.flush();
                            out.write("226 Transfer complete\r\n".getBytes());
                        } catch (IOException e) {
                            out.write("426 Connection closed; transfer aborted\r\n".getBytes());
                        } finally {
                            initialServer.close();
                            initialServer = null;
                        }
                    } else {
                        out.write("502 Command not implemented\r\n".getBytes());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

