package sample.Chat;

import javafx.application.Platform;
import javafx.scene.image.Image;
import sample.Controllers.CategoriesController;
import sample.Controllers.ChatController;
import sample.Controllers.ControllersConst;
import sample.Controllers.StartWindowController;
import sample.StartWindow;
import sample.StartWindow1;
import sample.settings.*;

import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Client extends Thread {

    private static ServerSocket serverSocket;
    private static int port = 1241;
    private static int ID_USER;
    private static ObjectInputStream ois;
    public static String nickname;

    //Server's info
    public static String SERVER_HOST = "localhost";
    public static int SERVER_PORT = 1235;

    public static void connect() throws IOException {
        while (true)
            try {
                serverSocket = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
                break;
            } catch (BindException e) {
                port++;
            }
        handle();
    }

    public static void helloServer(User user, int ID_MESSAGE) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.writeInt(ID_MESSAGE);
                dos.writeUTF("localhost");
                dos.writeInt(port);
                dos.flush();
                try (ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream())) {
                    ous.writeObject(user);
                    ous.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Сервер не подключен");
        }
    }

    public static void send(String message, int ID_MESSAGE) {
        try {
            try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
                try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                    dos.writeInt(ID_MESSAGE);
                    dos.writeInt(ID_USER);
                    dos.writeUTF(message);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(UserMessageS user, int ID_MESSAGE) {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("отправил");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(ID_MESSAGE);
            dos.writeInt(ID_USER);
            ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            ous.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void handle() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    int id = dis.readInt();
                    System.out.println(id);
                    switch (id) {
                        case 0:
                            ID_USER = dis.readInt();
                            break;

                        case 1:
                            String message = dis.readUTF();
                            ChatController.setMessage(message);
                            ChatController.added = true;
                            break;

                        case 2:
                            break;

                        case 3:
                            break;

                        case 4:
                            break;

                        case 5:
                            boolean free = dis.readBoolean();
                            if (free) {
                                StartWindow.create("fxmlFiles/StartWindow.fxml");
                            } else
                                ControllersConst.signUpController.wrongAuthorize();
                            break;

                        case 6:
                            boolean authAccept = dis.readBoolean();
                            if (authAccept) {
                                Configs.userName = StartWindowController.getUser().getUsername();
                                StartWindow1.create("fxmlFiles/Categories.fxml");
                            } else {
                                ControllersConst.startWindowController.wrongAuthorize();
                            }
                            break;

                        case 7:
                            ois = new ObjectInputStream(socket.getInputStream());
                            List<UserMessage> result = (List<UserMessage>) ois.readObject();
                            ChatController.setList(result);
                            ChatController.resultReady = true;
                            ois = null;
                            break;
                        //===== Categories codes ======

                        //HOT
                        case 8:

                            CategoriesController.ready = true;
                            CategoriesController.nowGameId = 8;
                            break;
                        //SPORT
                        case 9:
                            CategoriesController.ready = true;
                            CategoriesController.nowGameId = 9;
                            break;
                        //CYBER
                        case 10:

                        case 11:
                            CategoriesController.showReadyGame();
                            break;

                        //SET DATA
                        case 12:
                            ois = new ObjectInputStream(socket.getInputStream());
                            boolean last = false;
                            boolean current = false;
                            List<GameMessage> list = (List<GameMessage>) ois.readObject();
                            for (GameMessage entry : list) {
                                if (entry.getWinner().equals("nothing"))
                                    current = true;
                                else last = true;
                            }

                            if (last)
                                Platform.runLater(() -> CategoriesController.setLastGames(list));
                            if (current)
                                Platform.runLater(() -> CategoriesController.createCurrentGame(list));
                            break;
                        //GET VIDEO
                        case 13:

                            break;
                        case 14:
                            break;

                        //GET AVATAR
                        case 15:
                            String dir = "C:/Users/shokk/Desktop/distour/src/sample/assets/avatar.png";
                            File file = new File(dir);

                            byte[] byteArray = new byte[8192];
                            int in;
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            while ((in = dis.read(byteArray)) > 0) {
                                bos.write(byteArray, 0, in);
                            }

                            try {
                                Platform.runLater(() -> {
                                    ControllersConst.categoriesController.avatarView.setImage(
                                            new Image("file:///C:/Users/shokk/Desktop/distour/src/sample/assets/avatar.png"));
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            bos.close();
                            break;

                        //CHANGE AVATAR
                        case 16:

                            break;

                        //GET NICK
                        case 17:
                            nickname = dis.readUTF();

                            if (!nickname.equals("wrong nickname"))
                                Platform.runLater(() -> ControllersConst.categoriesController.nickname.setText(nickname));
                            else //
                            break;

                        //SET NICK
                        case 18:

                            break;

                        case 1337:


                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendMedia(File file, String ID_GAME) {
        try {
            byte[] byteArray = new byte[8192];
            int in;
            try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
                try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                    dos.writeInt(13);
                    dos.writeInt(ID_USER);
                    dos.writeUTF(ID_GAME);
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    while ((in = bis.read(byteArray)) > 0) {
                        dos.write(byteArray, 0, in);
                    }
                    dos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}