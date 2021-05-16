package com.example.demo.api.converter;

import com.example.demo.api.dto.JsonGameData;
import com.example.demo.api.dto.JsonResult;
import com.example.demo.core.Board;
import com.example.demo.core.Pit;
import com.example.demo.core.PlayerSides;
import com.example.demo.dbaccess.domain.Game;
import com.example.demo.exceptions.DrawException;
import com.example.demo.exceptions.GameIsPlayingException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JsonGameConverter {

    public JsonGameData toJson(Game game) {

        JsonGameData json = new JsonGameData();
        Board board = game.getBoard();
        String winner = "";

        try{
            PlayerSides tmp = board.getWinner();
            winner = tmp.toString();
        } catch (DrawException | GameIsPlayingException e) {
            e.printStackTrace();
        }
        json.setWinner(winner);
        json.setPitsForMove(board.getPitIdsForMove());
        Map<Integer, Integer> allPits = new HashMap<>();
        for(Pit pit: board.getPits()){
            allPits.put(pit.getId(), pit.getStones());
        }
        json.setAllPits(allPits);

        json.setActivePlayer(game.getBoard().getActivePlayer().toString());
        json.setGameState(board.finished() ? "Finished": "Playing");
        json.setInactivePlayer(board.getInactivePlayer().toString());
        return json;
    }
    public JsonResult<JsonGameData> toJsonResult(Game game) {
        return new JsonResult<>(toJson(game));
    }
}
