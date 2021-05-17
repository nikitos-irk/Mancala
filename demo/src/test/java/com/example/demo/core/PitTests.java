package com.example.demo.core;

import com.example.demo.service.GameManagment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PitTests {

    Board board;

    @Autowired
    private GameManagment gameManagment;

    @BeforeAll
    public void initGame() {
        board = gameManagment.addGame().getBoard();
    }

    @Test
    public void testOwners() {
        Assertions.assertEquals(board.getPits().get(0).getOwner(), PlayerSides.FIRST);
        Assertions.assertEquals(board.getPits().get(1).getOwner(), PlayerSides.FIRST);
        Assertions.assertEquals(board.getPits().get(2).getOwner(), PlayerSides.FIRST);
        Assertions.assertEquals(board.getPits().get(3).getOwner(), PlayerSides.FIRST);
        Assertions.assertEquals(board.getPits().get(4).getOwner(), PlayerSides.FIRST);
        Assertions.assertEquals(board.getPits().get(5).getOwner(), PlayerSides.FIRST);
        Assertions.assertEquals(board.getPits().get(6).getOwner(), PlayerSides.FIRST);

        Assertions.assertEquals(board.getPits().get(7).getOwner(), PlayerSides.SECOND);
        Assertions.assertEquals(board.getPits().get(8).getOwner(), PlayerSides.SECOND);
        Assertions.assertEquals(board.getPits().get(9).getOwner(), PlayerSides.SECOND);
        Assertions.assertEquals(board.getPits().get(10).getOwner(), PlayerSides.SECOND);
        Assertions.assertEquals(board.getPits().get(11).getOwner(), PlayerSides.SECOND);
        Assertions.assertEquals(board.getPits().get(12).getOwner(), PlayerSides.SECOND);
        Assertions.assertEquals(board.getPits().get(13).getOwner(), PlayerSides.SECOND);
    }

    @Test
    public void testBigPits() {
        Assertions.assertFalse(board.getPits().get(0).isBigPit());
        Assertions.assertFalse(board.getPits().get(1).isBigPit());
        Assertions.assertFalse(board.getPits().get(2).isBigPit());
        Assertions.assertFalse(board.getPits().get(3).isBigPit());
        Assertions.assertFalse(board.getPits().get(4).isBigPit());
        Assertions.assertFalse(board.getPits().get(5).isBigPit());
        Assertions.assertTrue(board.getPits().get(6).isBigPit());

        Assertions.assertFalse(board.getPits().get(7).isBigPit());
        Assertions.assertFalse(board.getPits().get(8).isBigPit());
        Assertions.assertFalse(board.getPits().get(9).isBigPit());
        Assertions.assertFalse(board.getPits().get(10).isBigPit());
        Assertions.assertFalse(board.getPits().get(11).isBigPit());
        Assertions.assertFalse(board.getPits().get(12).isBigPit());
        Assertions.assertTrue(board.getPits().get(13).isBigPit());
    }

    @Test
    public void testAddStones() {
        Pit pit = board.getPits().get(0);
        Assertions.assertEquals(pit.getStones(), Board.BASE_AMOUNT_OF_STONES);
        pit.setStones(0);
        Assertions.assertEquals(pit.getStones(), 0);
        Integer randomValue = 1 + new Random().nextInt(10);
        pit.addsStones(randomValue);
        Assertions.assertEquals(pit.getStones(), randomValue);
    }
}
