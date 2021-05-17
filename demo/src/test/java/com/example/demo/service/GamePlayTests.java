package com.example.demo.service;

import com.example.demo.core.Board;
import com.example.demo.core.Pit;
import com.example.demo.core.Player;
import com.example.demo.core.PlayerSides;
import com.example.demo.dbaccess.domain.Game;
import com.example.demo.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GamePlayTests {

    Game game;

    @Autowired
    private GameManagment gameManagment;

    @BeforeAll
    public void initGame() {
        game = gameManagment.addGame();
    }

    public void showPits(){
        List<Integer> a = new ArrayList<>();
        game.getBoard().getPits().forEach((pit) -> a.add(pit.getStones()));
        String line = "";
        for (int i = 0; i < a.size(); i++){
            line = line + " " + a.get(i).toString();
        }
        System.out.println("line = " + line);
    }

    public void showPits(List<Pit> pits){
        String fPlayerLine = "  ";
        String sPlayerLine = "  ";
        String middle = pits.get(13).getStones().toString() + "                " + pits.get(6).getStones().toString();
        for (int i = 0; i < 6; i++){
            fPlayerLine = fPlayerLine + " " + pits.get(i).getStones().toString();
            sPlayerLine = pits.get(i + 7).getStones().toString() + " " + sPlayerLine;
        }
        System.out.println("   " + sPlayerLine);
        System.out.println(middle);
        System.out.println(fPlayerLine);

        System.out.println("-----------------");
    };

    public void showActivePlayer(Board board){
        List<Integer> a = new ArrayList<>();
        String line = "";
        System.out.println("Active player = " + board.getActivePlayer().getPlayerSide().toString());
        for (Integer integer : board.getPitIdsForMove()) {
            line += integer + " ";
        }
        System.out.println("Available pits for move: " + line);
    }

    @Test
    public void testActivePlayerFirstMove() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Board board = game.getBoard();
        board.setTurn(PlayerSides.SECOND);
        Player activePlayer = board.getActivePlayer();
        Integer initialPitId = board.getPitIdsForMove().get(0);
        Integer initialNumberOfStones = board.getPits().get(initialPitId).getStones();
        board.makeMove(initialPitId, activePlayer.getPlayerSide());
        Assertions.assertEquals(0, board.getPits().get(initialPitId).getStones());
        Assertions.assertEquals(1, board.getPits().get(activePlayer.getBigPitIndex()).getStones());
        initialPitId++;
        while (initialPitId != activePlayer.getBigPitIndex()) {
            Assertions.assertEquals(
                    Board.BASE_AMOUNT_OF_STONES + 1, board.getPits().get(initialPitId).getStones());
            initialPitId++;
        }
        Assertions.assertEquals(activePlayer.getPlayerSide(), board.getActivePlayer().getPlayerSide());
        showPits();
    }

    @Test
    public void testActivePlayerChanged() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Board board = game.getBoard();
        Player activePlayer = board.getActivePlayer();
        Integer initialPitId = board.getPitIdsForMove().get(board.getPitIdsForMove().size()-1);
        board.makeMove(initialPitId, activePlayer.getPlayerSide());
        Assertions.assertEquals(activePlayer.getPlayerSide(), board.getInactivePlayer().getPlayerSide());
        showPits();
    }

    @Test
    public void testSkipOtherBigPit() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Game independentGame = gameManagment.addGame();
        Board board = independentGame.getBoard();
        board.setTurn(PlayerSides.FIRST);
        Player first = board.getActivePlayer();
        board.makeMove(board.getPitIdsForMove().get(0), board.getActivePlayer().getPlayerSide());
        board.makeMove(board.getPitIdsForMove().get(board.getPitIdsForMove().size()-1), board.getActivePlayer().getPlayerSide());
        board.makeMove(board.getPitIdsForMove().get(0), board.getActivePlayer().getPlayerSide());
        board.makeMove(board.getPitIdsForMove().get(0), board.getActivePlayer().getPlayerSide());
        board.makeMove(board.getPitIdsForMove().get(0), board.getActivePlayer().getPlayerSide());
        board.makeMove(board.getPitIdsForMove().get(0), board.getActivePlayer().getPlayerSide());
        Integer bigPitStonesAmount = board.getPits().get(first.getBigPitIndex()).getStones();
        board.makeMove(board.getPitIdsForMove().get(board.getPitIdsForMove().size()-1), board.getActivePlayer().getPlayerSide());
        Assertions.assertEquals(bigPitStonesAmount, board.getPits().get(first.getBigPitIndex()).getStones());
    }

    @Test
    public void testCheckCapturing() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Game independentGame = gameManagment.addGame();
        Board board = independentGame.getBoard();
        board.setTurn(PlayerSides.FIRST);
        board.getPits().get(0).setStones(1);
        board.getPits().get(1).setStones(0);
        board.makeMove(0, PlayerSides.FIRST);
        Assertions.assertEquals(7, board.getPits().get(Board.PLAYER_FIRST_BIG_PIT_INDEX).getStones());
    }

    @Test
    public void testGameFinishes() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException, DrawException, GameIsPlayingException {
        Game independentGame = gameManagment.addGame();
        Board board = independentGame.getBoard();
        board.setTurn(PlayerSides.FIRST);
        for (int i = 0; i <= Board.PIT_LAST_INDEX; i++){
            board.getPits().get(i).setStones(0);
        }
        board.getPits().get(0).setStones(1);
        board.makeMove(board.getPitIdsForMove().get(0), PlayerSides.FIRST);
        Assertions.assertEquals(board.getWinner(), PlayerSides.FIRST);
    }

    @Test
    public void testCheckDraw() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Game independentGame = gameManagment.addGame();
        Board board = independentGame.getBoard();
        board.setTurn(PlayerSides.FIRST);
        for (int i = 0; i <= Board.PIT_LAST_INDEX; i++){
            board.getPits().get(i).setStones(0);
        }
        board.getPits().get(0).setStones(1);
        board.getPits().get(Board.PLAYER_SECOND_BIG_PIT_INDEX).setStones(1);
        board.makeMove(board.getPitIdsForMove().get(0), PlayerSides.FIRST);
        Assertions.assertThrows(DrawException.class, board::getWinner);
    }

    @Test
    public void testCheckGameFinishedException() throws GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        Game independentGame = gameManagment.addGame();
        Board board = independentGame.getBoard();
        board.setTurn(PlayerSides.FIRST);
        for (int i = 0; i <= Board.PIT_LAST_INDEX; i++){
            board.getPits().get(i).setStones(0);
        }
        board.getPits().get(0).setStones(1);
        board.makeMove(board.getPitIdsForMove().get(0), PlayerSides.FIRST);
        Assertions.assertThrows(GameFinishedException.class, () -> board.makeMove(0, PlayerSides.FIRST));
    }
}
