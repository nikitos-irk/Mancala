package com.example.demo.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerSidesTests {

    @Test
    public void testToString(){
        Assertions.assertEquals(PlayerSides.FIRST.toString(), "first");
        Assertions.assertEquals(PlayerSides.SECOND.toString(), "second");
    }
}
