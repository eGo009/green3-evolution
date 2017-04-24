/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.model;

import com.green3.evolution.model.CommonEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GameBoard implements Serializable, CommonEntity{
    
    private int number;
    private int id;
    private int currentRound;
    
    // 0 - evolution
    // 1 - feed
    // 2 - extinction   
    private int roundStage;
        
    
    // 0 - created;
    // 1 - started;
    // 2 - finished;
    private int status;
    private List<String> cardDeck;
    private List<Player> players;
    
     
    public void setNumber(int pNumber){
        number = pNumber;
    }

    public int getNumber(){
        return number;
    }    
    
    
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    } 
    
    
    public void setStatus(int pStatus){
        status = pStatus;
    }

    public int getStatus(){
        return status;
    } 
    
    
    public void setCurrentRound(int pCurrentRound){
        currentRound = pCurrentRound;
    }

    public int getCurrentRound(){
        return currentRound;
    } 
    
    public void setRoundStage(int pRoundStage){
        roundStage = pRoundStage;
    }

    public int getRoundStage(){
        return roundStage;
    }
    
    
    public void setCardDeck(List<String> pCardDeck){
        cardDeck = pCardDeck;
    }

    public List<String> getCardDeck(){
        return cardDeck;
    } 
    
    
    public void setPlayers(List<Player> pPlayers){
        players = pPlayers;
    }

    public List<Player> getPlayers(){
        return players;
    }
    
    public int getCardsLeft(){
        if (getCardDeck()!=null){
            return getCardDeck().size();
        }
        return 0;
    }
    
}
