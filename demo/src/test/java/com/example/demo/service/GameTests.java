package com.example.demo.service;

import com.example.demo.core.Board;
import com.example.demo.dbaccess.domain.Game;
import com.example.demo.exceptions.GameIsPlayingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTests {

    Game game;

    @Autowired
    private GameManagment gameManagment;

    @BeforeAll
    public void initGame() {
        game = gameManagment.addGame();
    }

    @Test
    public void testGameMenagmentIsNotNull() {
        Assertions.assertNotNull(gameManagment);
    }

    @Test
    public void testCreateGame() {
        Assertions.assertNotNull(this.game);
    }

    @Test
    public void testCheckGameInitialState() {
        Assertions.assertFalse(game.getBoard().finished());
    }

    @Test
    public void testCheckNoWinnerYet() {
        Assertions.assertThrows(GameIsPlayingException.class, () -> game.getBoard().getWinner());
    }

    @Test
    public void testCheckInitialPitsStates() {
        Board board = game.getBoard();
        Assertions.assertEquals(0, board.getPits().get(Board.PLAYER_FIRST_BIG_PIT_INDEX).getStones());
        Assertions.assertEquals(0, board.getPits().get(Board.PLAYER_SECOND_BIG_PIT_INDEX).getStones());

        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(0).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(1).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(2).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(3).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(4).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(5).getStones());

        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(7).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(8).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(9).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(10).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(11).getStones());
        Assertions.assertEquals(Board.BASE_AMOUNT_OF_STONES, board.getPits().get(12).getStones());
    }
}
