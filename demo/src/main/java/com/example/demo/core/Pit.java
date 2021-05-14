package com.example.demo.core;

public class Pit {

    private Integer value;
    private Integer id;

    public Pit(Integer id, Integer value){
        this.id = id;
        if (this.isBigPit()){
            value = 0;
        }
        this.value = value;
    }

    public PlayerSides getOwner(){
        if (this.id <= Board.PLAYER_FIRST_BIG_PIT_INDEX){
            return PlayerSides.FIRST;
        }
        return PlayerSides.SECOND;
    }

    public Integer getStones(){
        return this.value;
    }

    public void setStones(Integer value){
        this.value = value;
    }

    public void addsStones(Integer value){
        this.value += value;
    }

    public boolean isBigPit(){
        return this.id == Board.PLAYER_FIRST_BIG_PIT_INDEX || this.id == Board.PLAYER_SECOND_BIG_PIT_INDEX;
    }
}
