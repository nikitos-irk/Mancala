package com.example.demo.domain;

import com.example.demo.core.Board;
import com.example.demo.core.Game;
import com.example.demo.core.Pit;
import com.example.demo.core.PlayerSides;
import com.example.demo.exceptions.DrawException;
import com.example.demo.exceptions.GameIsPlayingException;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGameData {
    String activePlayer;
    String inactivePlayer;
    String gameState;
    String winner;
    HashMap<Integer, Integer> allPits;
    List<Integer> pitsForMove;

    public JsonGameData(Game game){
        Board board = game.getBoard();
        this.activePlayer = board.getActivePlayer().toString();
        this.inactivePlayer = board.getInactivePlayer().toString();
        this.gameState = board.finished() ? "Finished": "Playing";
        this.winner = "";
        try{
            PlayerSides tmp = board.getWinner();
            this.winner = tmp.toString();
        } catch (DrawException | GameIsPlayingException e) {
            e.printStackTrace();
        }
        this.pitsForMove = board.getPitIdsForMove();
        this.allPits = new HashMap<>();
        for(Pit pit: board.getPits()){
            this.allPits.put(pit.getId(), pit.getStones());
        }
    }
}
