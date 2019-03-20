package sample.Chat;

import java.util.*;

public class GameHandler extends Thread {

    private static final List<String> GAME_TYPES = new ArrayList<>();

    static {
        GAME_TYPES.add("hot");
        GAME_TYPES.add("sport");
        GAME_TYPES.add("cybersport");
    }

    @Override
    public void run() {
        while (true) {
            for (String key : GAME_TYPES) {
                Map<ClientInfo, String> safeMap = Maps.waitList;
                int w = Collections.frequency(safeMap.values(), key);
                if (w == 2) {
                    createSession(searchIdentity(safeMap, key), key);
                }
            }
        }
    }

    private static void createSession(List<ClientInfo> clients, String GAME_TYPE) {
        String GAME_ID = UniqueID.getUUID().toString();
        Maps.waitList.remove(clients.get(0));
        Maps.waitList.remove(clients.get(1));
        GameSession session = new GameSession(clients, GAME_TYPE, GAME_ID);
        Maps.currentGames.put(GAME_ID, session);
        session.start();
    }

    private List<ClientInfo> searchIdentity(Map<ClientInfo, String> clients, String key) {
        List<ClientInfo> list = new ArrayList<>();
        for (Map.Entry<ClientInfo, String> entry : clients.entrySet()) {
            if (entry.getValue().equals(key)) {
                list.add(entry.getKey());
                Maps.waitList.remove(entry.getKey(), entry.getValue());
            }
        }
        return list;
    }
}
