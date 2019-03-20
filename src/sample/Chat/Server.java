package sample.Chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {


    private static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException {
        start();
        work();
        end();
    }

    private static void end() throws IOException {
        serverSocket.close();
    }

    private static void work() throws IOException {
        GameHandler gameHandler = new GameHandler();
        ServerHandler handler = new ServerHandler(serverSocket);
        gameHandler.start();
        handler.start();
        readChat();
    }

    private static void start() throws IOException {
        serverSocket = new ServerSocket(1235, 0, InetAddress.getByName("localhost"));
    }

    private static void readChat() throws IOException {
        while (true) {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.equals("/end")) {
                    end();
                } else {
                    System.out.println("Вы ввели неизвестную команду");
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}