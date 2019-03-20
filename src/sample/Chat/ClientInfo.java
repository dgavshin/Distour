package sample.Chat;

public class ClientInfo extends Thread {

    private String nickname;
    private String ip;
    private int port;
    private int idUser;

    String getIp() {
        return ip;
    }

    int getPort() {
        return port;
    }

    String getNickname() {
        return nickname;
    }


    int getIdUser() {
        return idUser;
    }

    ClientInfo(String nickname, String ip, int port, int idUser) {
        this.idUser = idUser;
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}