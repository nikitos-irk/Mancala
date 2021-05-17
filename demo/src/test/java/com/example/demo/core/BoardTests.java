package com.example.demo.core;

import com.example.demo.dbaccess.domain.Game;
import com.example.demo.exceptions.GameIsPlayingException;
import com.example.demo.exceptions.IncorrectPitIdException;
import com.example.demo.exceptions.WrongPairPlayerPitException;
import com.example.demo.exceptions.WrongPlayerException;
import com.example.demo.service.GameManagment;
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
public class BoardTests {

    Game game;
    Board board;

    @Autowired
    private GameManagment gameManagment;

    @BeforeAll
    public void initGame() {
        game = gameManagment.addGame();
        board = game.getBoard();
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
        Assertions.assertFalse(board.finished());
    }

    @Test
    public void testCheckInitialPitsStates() {
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

    @Test
    public void testCheckNoWinnerYet() {
        Assertions.assertThrows(GameIsPlayingException.class, () -> board.getWinner());
    }

    @Test
    public void testCheckAvailablePitsForMoves() {
        Assertions.assertEquals(board.getPitIdsForMove().size(), 6);
    }

    @Test
    public void testCheckAvailablePitsForMovesBelongToActivePlayer() {
        board.getPitIdsForMove().forEach(
                (pitId) -> Assertions.assertTrue(board.getActivePlayer().pitBelongsToPlayer(pitId))
        );
    }

    @Test
    public void testCheckGigPitBelongsToActivePlayer() {
        Assertions.assertTrue(board.getActivePlayer().pitBelongsToPlayer(board.getActivePlayer().getBigPitIndex()));
    }

    @Test
    public void testCheckAvailablePitsForMovesBelongToInactivePlayer() {
        List<Integer> pitIds = new ArrayList<>();
        board.getPits().forEach((pit) -> pitIds.add(pit.getId()));
        pitIds.removeIf(index -> board.getActivePlayer().pitBelongsToPlayer(index));
        pitIds.forEach((pitId) -> Assertions.assertTrue(board.getInactivePlayer().pitBelongsToPlayer(pitId)));
    }

    @Test
    public void testCheckGigPitBelongsToInactivePlayer() {
        Assertions.assertTrue(board.getInactivePlayer().pitBelongsToPlayer(board.getInactivePlayer().getBigPitIndex()));
    }

    @Test
    public void testMoveByIncorrectThrowsException() {
        Integer correctPitId = board.getPitIdsForMove().get(0);
        Player inactivePlayer = board.getInactivePlayer();
        Assertions.assertThrows(WrongPlayerException.class,
                () -> board.makeMove(correctPitId, inactivePlayer.getPlayerSide()));
    }

    @Test
    public void testMoveUsingInactivePitIdThrowsException() {
        Integer incorrectPitId = board.getOppositePitIndex(board.getPitIdsForMove().get(0));
        Player activePlayer = board.getActivePlayer();
        Assertions.assertThrows(WrongPairPlayerPitException.class,
                () -> board.makeMove(incorrectPitId, activePlayer.getPlayerSide()));
    }

    @Test
    public void testMoveUsingIncorrectPitIdThrowsException() {
        Player activePlayer = board.getActivePlayer();
        Assertions.assertThrows(IncorrectPitIdException.class,
                () -> board.makeMove(-1, activePlayer.getPlayerSide()));
        Assertions.assertThrows(IncorrectPitIdException.class,
                () -> board.makeMove(Board.PLAYER_SECOND_BIG_PIT_INDEX, activePlayer.getPlayerSide()));
        Assertions.assertThrows(IncorrectPitIdException.class,
                () -> board.makeMove(Board.PLAYER_FIRST_BIG_PIT_INDEX, activePlayer.getPlayerSide()));
    }
}
