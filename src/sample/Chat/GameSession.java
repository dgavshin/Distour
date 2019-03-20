package sample.Chat;

import sample.DatabaseHandler;
import sample.settings.GameMessage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class GameSession extends Thread {

    private List<ClientInfo> clients;
    private String GAME_TYPE;
    private String GAME_ID;
    private String exercise;
    private Random random = new Random();
    private DatabaseHandler handler = new DatabaseHandler();
    private String dir;

    GameSession(List<ClientInfo> clients, String GAME_TYPE, String GAME_ID) {
        this.clients = clients;
        this.GAME_TYPE = GAME_TYPE;
        this.GAME_ID = GAME_ID;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public List<ClientInfo> getClients() {
        return clients;
    }

    @Override
    public void run() {
        try {
            setDir("C:\\Users\\shokk\\desktop\\Media\\" + GAME_ID);
            new File(getDir()).mkdir();
            randomExercise(GAME_TYPE);
            GameMessage gameMessage = new GameMessage(
                    GAME_ID,
                    exercise,
                    clients.get(0).getNickname(),
                    clients.get(1).getNickname(),
                    null,
                    GAME_TYPE.toLowerCase()
            );
            handler.writeGame(gameMessage);
            readyMessage();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void randomExercise(String GAME_TYPE) throws IOException {
        exercise = "opa";
        if (GAME_TYPE.equals("hot")) {
            List<String> line = Files.readAllLines(Paths.get("src\\sample\\Chat\\CategoriesSamples\\hot.txt"));
            exercise = line.get(random.nextInt(line.size()));
            System.out.println(exercise);
        }
        if (GAME_TYPE.equals("sport")) {
            List<String> line = Files.readAllLines(Paths.get("src\\sample\\Chat\\CategoriesSamples\\sport.txt"));
            exercise = line.get(random.nextInt(line.size()));
            System.out.println(exercise);
        }
    }

    private void readyMessage() {
        for (ClientInfo client : clients) {
            try {
                Socket socket = new Socket(client.getIp(), client.getPort());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(11);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
