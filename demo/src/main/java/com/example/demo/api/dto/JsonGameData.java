package com.example.demo.api.dto;


import java.util.List;
import java.util.Map;

public class JsonGameData {
    private String activePlayer;
    private String inactivePlayer;
    private String gameState;
    private String winner;
    private Map<Integer, Integer> allPits;
    private List<Integer> pitsForMove;


    public JsonGameData() {
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getInactivePlayer() {
        return inactivePlayer;
    }

    public void setInactivePlayer(String inactivePlayer) {
        this.inactivePlayer = inactivePlayer;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Map<Integer, Integer> getAllPits() {
        return allPits;
    }

    public void setAllPits(Map<Integer, Integer> allPits) {
        this.allPits = allPits;
    }

    public List<Integer> getPitsForMove() {
        return pitsForMove;
    }

    public void setPitsForMove(List<Integer> pitsForMove) {
        this.pitsForMove = pitsForMove;
    }
}
