/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.model;

import com.green3.evolution.model.CommonEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GameBoard implements Serializable, CommonEntity{
    
    private int number;
    private String id;
    private int currentRound;
    
    // 0 - created;
    // 1 - started;
    // 2 - finished;
    private int status;
    private List<String> cardDeck;
    private Set<String> players;
    
     
    public void setNumber(int pNumber){
        number = pNumber;
    }

    public int getNumber(){
        return number;
    }    
    
    
    public void setId(String pId){
        id = pId;
    }

    public String getId(){
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
    
    
    public void setCardDeck(List<String> pCardDeck){
        cardDeck = pCardDeck;
    }

    public List<String> getCardDeck(){
        return cardDeck;
    } 
    
    
    public void setPlayers(Set<String> pPlayers){
        players = pPlayers;
    }

    public Set<String> getPlayers(){
        return players;
    }
    
    public int getCardsLeft(){
        return getCardDeck().size();
    }
    
}
