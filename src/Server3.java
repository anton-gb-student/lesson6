import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server3 {
    private boolean isOff = false;
    private ServerSocket server = null;
    private Socket socket = null;

    public Server3(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            socket = server.accept();
            System.out.println("Connection established");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isOff) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void inputMethod () {
            try {
                Scanner inputScanner = new Scanner(socket.getInputStream());
                while (!isOff) {
                    String inMsg = inputScanner.nextLine();
                    if (inMsg.equals("/end")) {
                        System.out.println("Connection closed");
                        isOff = true;
                        break;
                    }
                    System.out.println("Input: " + inMsg);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void outputMethod () {
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                Scanner outputScanner = new Scanner(System.in);
                while (!isOff) {
                    String outMsg = outputScanner.nextLine();
                    output.println(outMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void main (String[]args){
            Server3 s = new Server3(8185);
            Thread sender = new Thread(() ->
                    s.outputMethod());
            Thread listener = new Thread(() ->
                    s.inputMethod());
            listener.start();
            sender.setDaemon(true);
               sender.start();
    }
 }

