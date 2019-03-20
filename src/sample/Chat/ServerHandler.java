package sample.Chat;

import sample.DatabaseHandler;
import sample.settings.GameMessage;
import sample.settings.User;
import sample.settings.UserMessage;
import sample.settings.UserMessageS;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerHandler extends Thread {

    private final ServerSocket server;
    private DatabaseHandler handler = new DatabaseHandler();

    ServerHandler(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                DataInputStream dis = new DataInputStream(client.getInputStream());
                int id = dis.readInt();
                System.out.println("Message id = " + id);
                switch (id) {
                    case 0:
                        break;

                    //SENDING MESSAGES
                    case 1:
                        int ID_USER = dis.readInt();
                        String line = dis.readUTF();

                        String message = "[" + Maps.currentUsers.get(ID_USER).getNickname() + "]: " + line;
                        sendToAll(message);
                        break;
                    //Прекратить поиск соперника
                    case 2:
                        ID_USER = dis.readInt();
                        dis.readUTF();
                        Maps.waitList.remove(Maps.currentUsers.get(ID_USER));
                        System.out.println("Игрок " + Maps.currentUsers.get(ID_USER).getNickname() + " был убран из поиска игры");
                        break;

                    //Выход клиента
                    case 3:
                        try {
                            ID_USER = dis.readInt();
                            dis.readUTF();
                            System.out.println("Пользователь " + Maps.currentUsers.get(ID_USER).getNickname() + " вышел");
                            Maps.waitList.remove(Maps.currentUsers.get(ID_USER));
                            Maps.currentUsers.remove(ID_USER);

                        } catch (NullPointerException ignored) {
                        }

                        break;

                    //Регистрация пользователя
                    case 4:

                        break;

                    //Проверка при регистрации
                    case 5:
                        System.out.println("Попытка регистрации");

                        String ip = dis.readUTF();
                        int port = dis.readInt();

                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        User regUser = (User) ois.readObject();
                        ResultSet set = handler.checkUser(regUser);
                        int counter = 0;
                        while (set.next())
                            counter++;
                        if (counter <= 0) {
                            handler.signUpUser(regUser);
                            send(true, 5, ip, port);
                            break;
                        } else
                            send(false, 5, ip, port);
                        break;
                    //Проверка авторизации
                    case 6:
                        System.out.println("Попытка авторизации");

                        ip = dis.readUTF();
                        port = dis.readInt();

                        ois = new ObjectInputStream(client.getInputStream());
                        User user = (User) ois.readObject();
                        String nickname = user.getUsername();
                        ResultSet resultAuthorize = handler.getUser(user);
                        System.out.println("некто под ником " + user.getUsername() + " пытается войти");
                        counter = 0;
                        while (resultAuthorize.next()) counter++;
                        if (counter > 0) {
                            createHandler(nickname, ip, port);
                        } else
                            send(false, 6, ip, port);
                        break;

                    //Поиск по базе данных, специально для админа
                    case 7:
                        ois = new ObjectInputStream(client.getInputStream());
                        UserMessageS ums = (UserMessageS) ois.readObject();
                        ID_USER = dis.readInt();

                        String insert = " FROM chat WHERE 1 ";
                        if (!ums.getUser().equals("")) {
                            insert += " AND sender ='" + ums.getUser() + "'";
                        }
                        if (!ums.getMessage().equals("")) {
                            insert += " AND message LIKE '%" + ums.getMessage() + "%'";
                        }
                        if (!ums.getDateFrom().equals("")) {
                            insert += " AND messageDate >=" + ums.getDateFrom();
                        }
                        if (!ums.getDateTo().equals("")) {
                            insert += " AND messageDate <=" + ums.getDateTo();
                        }

                        ResultSet result = handler.find(insert);
                        List<UserMessage> list = new ArrayList<>();
                        while (result.next()) {
                            int columns = result.getMetaData().getColumnCount();
                            String[] value = new String[4];
                            for (int i = 1; i <= columns; value[i] = result.getString(i), i++) ;
                            UserMessage mes = new UserMessage(value[1], value[2], value[3]);
                            list.add(mes);
                        }
                        sendResult(list, ID_USER);
                        break;

                    //===== Categories codes ======

                    //HOT
                    case 8:
                        ID_USER = dis.readInt();
                        dis.readUTF();

                        System.out.println("Добавил пользователя " + Maps.currentUsers.get(ID_USER).getNickname() + " c ID " + ID_USER + "  в waitList в категории Hot");
                        Maps.waitList.put(Maps.currentUsers.get(ID_USER), "hot");
                        break;
                    //SPORT
                    case 9:
                        ID_USER = dis.readInt();
                        dis.readUTF();

                        System.out.println("Добавил пользователя " + Maps.currentUsers.get(ID_USER).getNickname() + " c ID " + ID_USER + "  в waitList в категории Sport");
                        Maps.waitList.put(Maps.currentUsers.get(ID_USER), "sport");
                        break;
                    //CYBER
                    case 10:
                        ID_USER = dis.readInt();
                        dis.readUTF();

                        System.out.println("Добавил пользователя " + Maps.currentUsers.get(ID_USER).getNickname() + " c ID " + ID_USER + "  в waitList в категории Cybersport");
                        Maps.waitList.put(Maps.currentUsers.get(ID_USER), "cybersport");
                        break;

                    case 11:

                        break;

                    case 12:
                        ID_USER = dis.readInt();
                        dis.readUTF();
                        try {
                            List<GameMessage> game = handler.getGame(Maps.currentUsers.get(ID_USER).getNickname());
                            send(game, ID_USER);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        break;
                    //GET VIDEO
                    case 13:
                        ID_USER = dis.readInt();
                        String ID_GAME = dis.readUTF();
                        System.out.println("ID_GAME = " + ID_GAME);
                        String dir = "c:/users/shokk/desktop/media/" + ID_GAME;
                        String nick = Maps.currentUsers.get(ID_USER).getNickname();
                        File file = new File(dir + "/" + nick + ".mp4");

                        byte[] byteArray = new byte[8192];
                        int in;
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

                        while ((in = dis.read(byteArray)) > 0) {
                            bos.write(byteArray, 0, in);
                        }

                        bos.close();
                        handler.setVideoPath(file.getPath(), Maps.currentUsers.get(ID_USER).getNickname(), ID_GAME);
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    //CHANGE AVATAR
                    case 16:
                        ID_USER = dis.readInt();
                        String path = dis.readUTF();
                        handler.setAvatar(Maps.currentUsers.get(ID_USER).getNickname(), path);
                        sendAvatar(ID_USER);
                        break;

                    //GET NICK
                    case 17:
                        break;

                    //SET NICK
                    case 18:
                        ID_USER = dis.readInt();
                        nick = dis.readUTF();
                        if (handler.checkNickname(nick)) {
                            handler.setNickname(Maps.currentUsers.get(ID_USER).getNickname(), nick);
                            Maps.currentUsers.get(ID_USER).setNickname(nick);
                            send(nick, ID_USER);
                        } else send("wrong nickname", ID_USER);
                        break;
                }
            } catch (SocketException | ClassNotFoundException | SQLException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAvatar(int ID_USER) {
        try {
            byte[] byteArray = new byte[8192];
            int in;
            try (Socket socket = new Socket(Maps.currentUsers.get(ID_USER).getIp(), Maps.currentUsers.get(ID_USER).getPort())) {
                try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                    dos.writeInt(15);
                    BufferedInputStream bis =
                            new BufferedInputStream(
                                    new FileInputStream(
                                            new File(handler.getAvatar(Maps.currentUsers.get(ID_USER).getNickname()))));

                    while ((in = bis.read(byteArray)) > 0) {
                        dos.write(byteArray, 0, in);
                    }
                    dos.flush();
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void send(String message, int ID_USER) {
        try (Socket socket = new Socket(Maps.currentUsers.get(ID_USER).getIp(), Maps.currentUsers.get(ID_USER).getPort())) {
            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.writeInt(17);
                dos.writeUTF(message);
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(List<GameMessage> game, int ID_USER) {
        try (Socket socket = new Socket(Maps.currentUsers.get(ID_USER).getIp(), Maps.currentUsers.get(ID_USER).getPort())) {
            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.writeInt(12);
                dos.flush();
                try (ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream())) {
                    ous.writeObject(game);
                    ous.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHandler(String nickname, String ip, int port) {
        int ID_USER = UniqueID.getUniqueInt();
        System.out.println("ID_USER = " + ID_USER);

        ClientInfo client = new ClientInfo(nickname, ip, port, ID_USER);
        Maps.currentUsers.put(ID_USER, client);

        send(true, 6, Maps.currentUsers.get(ID_USER).getIp(), Maps.currentUsers.get(ID_USER).getPort());
        sendId(ID_USER);
        sendAvatar(ID_USER);
    }

    private static void sendId(int ID_USER) {
        try (Socket socket = new Socket(Maps.currentUsers.get(ID_USER).getIp(), Maps.currentUsers.get(ID_USER).getPort())) {
            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.writeInt(0);
                dos.writeInt(ID_USER);
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(String message) {
        for (Map.Entry<Integer, ClientInfo> entry : Maps.currentUsers.entrySet()) {
            new Thread(() -> {
                try (Socket socket = new Socket(InetAddress.getByName(entry.getValue().getIp()), entry.getValue().getPort())) {
                    try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                        dos.writeInt(1);
                        dos.writeUTF(message);
                        dos.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void sendResult(List<UserMessage> result, int ID_USER) {
        try {
            try (Socket socket = new Socket(Maps.currentUsers.get(ID_USER).getIp(), Maps.currentUsers.get(ID_USER).getPort())) {
                try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                    dos.writeInt(7);
                    dos.flush();
                    try (ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream())) {
                        ous.writeObject(result);
                        ous.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void send(boolean free, int ID_MESSAGE, String ip, int port) {
        try (Socket socket = new Socket(ip, port)) {
            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.writeInt(ID_MESSAGE);
                dos.writeBoolean(free);
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

