package sample.Chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class Maps {

    static ConcurrentMap<ClientInfo, String> waitList = new ConcurrentHashMap<>();
    static ConcurrentMap<String, GameSession> currentGames = new ConcurrentHashMap<>();
    static Map<Integer, ClientInfo> currentUsers = new ConcurrentHashMap<>();

}
