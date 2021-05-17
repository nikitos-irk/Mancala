package com.example.demo.core;

import com.example.demo.dbaccess.domain.Game;
import com.example.demo.service.GameManagment;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
public class PlayerTests {
    Player playerFirst, playerSecond;

    @BeforeAll
    public void initGame() {
        playerFirst = new Player(PlayerSides.FIRST);
        playerSecond = new Player(PlayerSides.SECOND);

    }

    @Test
    public void testSide(){
        Assertions.assertEquals(playerFirst.getPlayerSide(), PlayerSides.FIRST);
        Assertions.assertEquals(playerSecond.getPlayerSide(), PlayerSides.SECOND);
    }

    @Test
    public void testBigPitIndex(){
        Assertions.assertEquals(playerFirst.getBigPitIndex(), Board.PLAYER_FIRST_BIG_PIT_INDEX);
        Assertions.assertEquals(playerSecond.getBigPitIndex(), Board.PLAYER_SECOND_BIG_PIT_INDEX);
    }

    @Test
    public void testToString(){
        Assertions.assertEquals(PlayerSides.FIRST.toString(), playerFirst.toString());
        Assertions.assertEquals(PlayerSides.SECOND.toString(), playerSecond.toString());
    }
}
