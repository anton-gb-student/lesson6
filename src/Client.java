import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket connect = null;
    private String ip = "localhost";
    private int port = 8185;
    private boolean isOff= false;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            connect = new Socket(ip,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isOff == true) {
            try {
                connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void inputMethod () {
        try {
            Scanner inputScanner = new Scanner(connect.getInputStream());
            while (isOff != true) {
                String inMsg = inputScanner.nextLine();
                System.out.println("Input: " + inMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputMethod () {
        try {
            PrintWriter output = new PrintWriter(connect.getOutputStream(), true);
            Scanner outputScanner = new Scanner(System.in);
            while (isOff != true) {
                String outMsg = outputScanner.nextLine();
                output.println(outMsg);
                if (outMsg.equals("/end")) {
                    isOff = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void main(String[] args) {
        Client c1 = new Client("localhost", 8185);
        Thread clientIn = new Thread(()->
                c1.inputMethod());
        clientIn.setDaemon(true);
            clientIn.start();
        Thread clientOut = new Thread(()->
                c1.outputMethod());
        clientOut.start();
    }
}
