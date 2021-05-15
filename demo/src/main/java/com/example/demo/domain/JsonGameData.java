package com.example.demo.domain;

import com.example.demo.core.Board;
import com.example.demo.core.Game;
import com.example.demo.core.Pit;
import com.example.demo.core.PlayerSides;
import com.example.demo.exceptions.DrawException;
import com.example.demo.exceptions.GameIsPlayingException;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class JsonGameData {
    String activePlayer;
    String inactivePlayer;
    String gameState;
    String winner;
    List<Pair<Integer, Integer>> allPits;
    List<Integer> pitsForMove;

    public JsonGameData(Game game){
        Board board = game.getBoard();
        this.activePlayer = board.getActivePlayer().toString();
        this.inactivePlayer = board.getInactivePlayer().toString();
        this.gameState = board.finished() ? "Finished": "Playing";
        this.winner = "";
        try{
            this.winner = board.getWinner().toString();
        } catch (DrawException | GameIsPlayingException e) {
            e.printStackTrace();
        }
        this.pitsForMove = board.getPitIdsForMove();
        this.allPits = new ArrayList<>();
        for(Pit pit: board.getPits()){
            this.allPits.add(Pair.of(pit.getId(), pit.getStones()));
        }
    }
}
