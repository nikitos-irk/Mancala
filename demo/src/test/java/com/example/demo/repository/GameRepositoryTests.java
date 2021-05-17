package com.example.demo.repository;

import com.example.demo.dbaccess.domain.Game;
import com.example.demo.dbaccess.repository.GameRepository;
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
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;
    String gameId;

    @BeforeAll
    public void initGame() {
        Game game = new Game();
        gameId = game.getId();
        gameRepository.save(game);
    }

    @Test
    public void testCheckSizeOfRepo(){
        Assertions.assertTrue(gameRepository.getAllGameIDs().size() > 0);
    }

    @Test
    public void testCheckGameId(){
        Assertions.assertTrue(gameRepository.getAllGameIDs().contains(gameId));
    }

    @Test
    public void testFindById(){
        Assertions.assertSame(gameRepository.findById(gameId).getId(), gameId);
    }

    @Test
    public void testSave(){
        Game newGame = new Game();
        gameRepository.save(newGame);
        Assertions.assertTrue(gameRepository.getAllGameIDs().contains(newGame.getId()));
    }

}
