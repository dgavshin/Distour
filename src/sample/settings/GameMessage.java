package sample.settings;

import java.io.Serializable;


public class GameMessage implements Serializable {

    public GameMessage(String gameId, String exercise, String player1, String player2, String winner, String category) {
        this.gameId = gameId;
        this.exercise = exercise;
        this.player1 = player1;
        this.player2 = player2;
        this.category = category;
        this.winner = winner;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String gameId;
    private String exercise;
    private String player1;
    private String player2;
    private String winner;
    private String category;

}
