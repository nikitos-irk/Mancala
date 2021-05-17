package com.example.demo.service;

import com.example.demo.core.Board;
import com.example.demo.core.PlayerSides;
import com.example.demo.dbaccess.domain.Game;
import com.example.demo.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameManagmentTests {

    @Autowired
    private GameManagment gameManagment;

    @Test
    public void testCheckGameId(){
        Game game = gameManagment.addGame();
        Assertions.assertNotNull(game);
    }

    @Test
    public void testGetGames(){
        Game game = gameManagment.addGame();
        Assertions.assertTrue(gameManagment.getGamesIds().contains(game.getId()));
    }

    @Test
    public void testGetGameById() throws WrongGameIdException {
        Game game = gameManagment.addGame();
        Assertions.assertNotNull(gameManagment.getGame(game.getId()));
    }

    @Test
    public void testGetGameByWrongId() {
        Assertions.assertThrows(WrongGameIdException.class, () -> gameManagment.getGame("wrongId"));
    }

    @Test
    public void testIncorrectPlayerNameException(){
        Game game = gameManagment.addGame();
        game.getBoard().setTurn(PlayerSides.FIRST);
        Assertions.assertThrows(IncorrectPlayerName.class,
                () -> gameManagment.makeMove(game.getId(), "wrongName", 0));
    }

    @Test
    public void testWrongGameIdException(){
        Game game = gameManagment.addGame();
        game.getBoard().setTurn(PlayerSides.FIRST);
        Assertions.assertThrows(WrongGameIdException.class,
                () -> gameManagment.makeMove("wrongGameId", PlayerSides.FIRST.toString(), 0));
    }

    @Test
    public void testGameFinishedException() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Game game = gameManagment.addGame();
        Board board = game.getBoard();
        board.setTurn(PlayerSides.FIRST);
        for(int i = 0; i < Board.PIT_LAST_INDEX; i++){
            board.getPits().get(i).setStones(0);
        }
        board.getPits().get(Board.PLAYER_FIRST_BIG_PIT_INDEX-1).setStones(1);
        board.makeMove(Board.PLAYER_FIRST_BIG_PIT_INDEX-1, PlayerSides.FIRST);

        Assertions.assertThrows(GameFinishedException.class,
                () -> gameManagment.makeMove(game.getId(), PlayerSides.FIRST.toString(), 0));
    }

    @Test
    public void testEmptyPitException(){
        Game game = gameManagment.addGame();
        Board board = game.getBoard();
        board.setTurn(PlayerSides.FIRST);
        for(int i = 0; i < Board.PIT_LAST_INDEX; i++){
            board.getPits().get(i).setStones(0);
        }
        board.getPits().get(Board.PLAYER_FIRST_BIG_PIT_INDEX-1).setStones(1);

        Assertions.assertThrows(EmptyPitException.class,
                () -> gameManagment.makeMove(game.getId(), PlayerSides.FIRST.toString(), 0));
    }

    @Test
    public void testWrongPlayerException(){
        Game game = gameManagment.addGame();
        Board board = game.getBoard();
        board.setTurn(PlayerSides.FIRST);
        Assertions.assertThrows(WrongPlayerException.class,
                () -> gameManagment.makeMove(game.getId(), PlayerSides.SECOND.toString(), 0));
    }

    @Test
    public void testWrongPairPlayerPitException(){
        Game game = gameManagment.addGame();
        Board board = game.getBoard();
        board.setTurn(PlayerSides.FIRST);
        Assertions.assertThrows(WrongPairPlayerPitException.class,
                () -> gameManagment.makeMove(game.getId(), PlayerSides.FIRST.toString(),
                        board.getOppositePitIndex(board.getPitIdsForMove().get(0))));
    }

    @Test
    public void testIncorrectPitIdException(){
        Game game = gameManagment.addGame();
        Board board = game.getBoard();
        Assertions.assertThrows(IncorrectPitIdException.class,
                () -> gameManagment.makeMove(game.getId(), PlayerSides.FIRST.toString(), -1));
    }
}
