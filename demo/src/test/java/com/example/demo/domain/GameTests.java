package com.example.demo.domain;

import com.example.demo.dbaccess.domain.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameTests {

    @Test
    public void testGame(){
        Game game = new Game();
        Assertions.assertNotNull(game);
        Assertions.assertNotNull(game.getBoard());
        Assertions.assertFalse(game.getId().isEmpty());
    }
}
