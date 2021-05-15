package com.example.demo.core;

import com.example.demo.exceptions.*;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Board {

    public static final int PIT_FIRST_INDEX = 0;
    public static final int PIT_LAST_INDEX = 13;

    public static final int PLAYER_FIRST_BIG_PIT_INDEX = 6;
    public static final int PLAYER_SECOND_BIG_PIT_INDEX = PIT_LAST_INDEX;

    public static final int BASE_AMOUNT_OF_STONES = 6;
    private final List<Pit> pits;

    private final Player firstPlayer;
    private final Player secondPlayer;

    private boolean turn; // first player starts

    private boolean finished;

    public Board(){
        this.pits = new ArrayList<>();
        this.finished = false;
        for (int i = PIT_FIRST_INDEX; i < PLAYER_FIRST_BIG_PIT_INDEX; i++){
            this.pits.add(new Pit(i, BASE_AMOUNT_OF_STONES));
        }
        this.firstPlayer = new Player(PlayerSides.FIRST);
        this.secondPlayer = new Player(PlayerSides.SECOND);

        this.turn = new Random().nextBoolean();
    }

    public PlayerSides getWinner() throws GameIsPlayingException, DrawException {
        if (!this.finished) {
            throw new GameIsPlayingException("Game has not finished yet!");
        }
        if (pits.get(PLAYER_FIRST_BIG_PIT_INDEX).getStones().equals(pits.get(PLAYER_SECOND_BIG_PIT_INDEX).getStones())){
            throw new DrawException("Draw...");
        }
        return pits.get(PLAYER_FIRST_BIG_PIT_INDEX).getStones() > pits.get(PLAYER_SECOND_BIG_PIT_INDEX).getStones()
                ? PlayerSides.FIRST : PlayerSides.SECOND;
    }

    public Player getFirst() {
        return this.firstPlayer;
    }

    public Player getSecond() {
        return this.secondPlayer;
    }

    public Player getActivePlayer() {
        if (this.turn){
            return firstPlayer;
        }
        return secondPlayer;
    }

    public Player getInactivePlayer() {
        if (!this.turn){
            return firstPlayer;
        }
        return secondPlayer;
    }

    public Integer getOppositePitIndex(Integer index){
        Integer diff = 2 * Math.abs(PLAYER_FIRST_BIG_PIT_INDEX - index);
        if (index < PLAYER_FIRST_BIG_PIT_INDEX) {
            return index + diff;
        }
        return index - diff;
    }

    public void checkStonesCapture(Integer index){
        if (this.pits.get(index).getStones() != 1){
            return;
        }
        Integer activePlayerStones = this.pits.get(index).getStones();
        Integer inactivePlayerStones = this.pits.get(this.getOppositePitIndex(index)).getStones();
        if (activePlayerStones == 1 && inactivePlayerStones != 0){
            if (index < PLAYER_FIRST_BIG_PIT_INDEX) {
                this.pits.get(PLAYER_FIRST_BIG_PIT_INDEX).addsStones(activePlayerStones + inactivePlayerStones);
            } else {
                this.pits.get(PLAYER_SECOND_BIG_PIT_INDEX).addsStones(activePlayerStones + inactivePlayerStones);
            }
            this.pits.get(index).setStones(0);
            this.pits.get(this.getOppositePitIndex(index)).setStones(0);
        }
    }

    public void makeMove(Integer pitId, PlayerSides player)
            throws WrongPlayerException, WrongPairPlayerPitException, EmptyPitException, IncorrectPitIdException, GameFinishedException
    {
        if (this.finished) {
            throw new GameFinishedException("Game is finished!");
        }
        if (pitId < PIT_FIRST_INDEX || pitId > PIT_LAST_INDEX){
            throw new IncorrectPitIdException("Incorrect pitId - [" + pitId + "]");
        }
        if (player != this.getActivePlayer().getPlayerSide()){
            throw new WrongPlayerException("That player is not active - [" + player.toString() + "]");
        }
        if (!this.getActivePlayer().checkPitId(pitId)){
            throw new WrongPairPlayerPitException("That player " + player.toString() + "is not the owner of that pit - [" + pitId.toString() + "]");
        }
        if (this.pits.get(pitId).getStones() == 0){
            throw new EmptyPitException("Pit [" + pitId + "] is empty");
        }
        Player activePlayer = this.getActivePlayer();
        int value = this.pits.get(pitId).getStones();
        int index = pitId + 1;
        while (value > 0) {
            if (index > PIT_LAST_INDEX){
                index = 0;
                continue;
            }

            // Skip big pit of another player
            if (this.pits.get(index).isBigPit() && activePlayer.getBigPitIndex() != index){
                index++;
                continue;
            }
            this.pits.get(index).addsStones(1);
            value--;
            index++;
        }
        // back to the last used pit
        index--;

        // Check stones capturing
        this.checkStonesCapture(index);

        // Check if last stone put into active player's big pit
        if (activePlayer.getBigPitIndex() != index){
            this.turn = !this.turn;
        }
        this.checkState();
    }

    public List<Integer> getPitIdsForMove(){
        List<Integer> result = new ArrayList<>();
        int index = PIT_FIRST_INDEX;
        if (!this.turn) {
            index = PLAYER_FIRST_BIG_PIT_INDEX + 1;
        }
        for (int i = index; i < index + PLAYER_FIRST_BIG_PIT_INDEX; i++){
            if (this.pits.get(i).getStones() > 0){
                result.add(i);
            }
        }
        return result;
    }

    public void checkState(){
        Integer firstPlayerStones = 0;
        Integer secondPlayerStones = 0;
        for (int i = 0; i < PLAYER_FIRST_BIG_PIT_INDEX; i++){
            firstPlayerStones += this.pits.get(i).getStones();
        }
        for (int i = PLAYER_FIRST_BIG_PIT_INDEX + 1; i < PIT_LAST_INDEX; i++){
            secondPlayerStones += this.pits.get(i).getStones();
        }

        if (firstPlayerStones == 0 || secondPlayerStones == 0){
            this.finished = true;

            this.pits.get(PIT_FIRST_INDEX).addsStones(firstPlayerStones);
            this.pits.get(PIT_LAST_INDEX).addsStones(secondPlayerStones);

            for (int i = PIT_FIRST_INDEX + 1; i < PIT_LAST_INDEX - 1; i++){
                if (!this.pits.get(i).isBigPit())
                    this.pits.get(i).setStones(0);
            }
        }
    }

    public List<Pit> getPits(){
        return this.pits;
    }

    public boolean finished(){
        return this.finished;
    }

}