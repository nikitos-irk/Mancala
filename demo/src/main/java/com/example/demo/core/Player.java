package com.example.demo.core;

public class Player {

    private final PlayerSides id;
    public Player(PlayerSides id) {
        this.id = id;
    }

    PlayerSides getPlayerSide(){
        return this.id;
    }

    int getBigPitIndex(){
        if (this.id == PlayerSides.FIRST) {
            return Board.PLAYER_FIRST_BIG_PIT_INDEX;
        }
        return Board.PLAYER_SECOND_BIG_PIT_INDEX;
    }

    boolean checkPitId(Integer pitId){
        if (this.id == PlayerSides.FIRST) {
            return pitId <= Board.PLAYER_FIRST_BIG_PIT_INDEX;
        }
        return pitId > Board.PLAYER_FIRST_BIG_PIT_INDEX;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
