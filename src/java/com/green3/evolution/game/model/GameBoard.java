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
    private List<Card> cardDeck;
    private List<Player> players;
    private int cardsLeft;   
    
    
     
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
    
    
    public void setCardDeck(List<Card> pCardDeck){
        cardDeck = pCardDeck;
    }

    public List<Card> getCardDeck(){
        return cardDeck;
    } 
    
    
    public void setPlayers(List<Player> pPlayers){
        players = pPlayers;
    }

    public List<Player> getPlayers(){
        return players;
    }
    
    public int getCardsLeft(){
         return cardsLeft;
    }
    
    public void setCardsLeft(int pCardsLeft){
        cardsLeft = pCardsLeft;
    }
    
    public String getTurnPlayer(){
        int minOrder = Integer.MAX_VALUE;
        String turnPlayer = "";
        for (Player player : getPlayers()){
            if (player.getActionOrder() < minOrder){
                minOrder = player.getActionOrder();
                turnPlayer = player.getUser();
            }
        }
        return turnPlayer;
    }
    
    /*public void setAvailableAnimalsToCards(){
        for (Player player : getPlayers()){
            for (Card card : player.getCardsOnHand()){
                for (Property prop : card.getProperties()){
                   int cardType = prop.getType();
                   switch (cardType){
                       case 0:
                           break;
                       case 1:
                           break;
                       case 2:
                           break;
                       case 3:
                           break;
                       case 4:
                           break;
                       case 5:
                           break;
                       case 6:
                           break;
                       case 7:
                           break;
                       case 8:
                           break;
                       case 9:
                           break;
                       case 10:
                           break;
                       case 11:
                           break;
                       case 12:
                           break;
                       case 13:
                           break;
                       case 14:
                           break;
                       case 15:
                           break;
                       case 16:
                           break;
                       case 17:
                           break;
                       case 18:
                           break;
                       case 19:
                           break;
                       case 20:
                           break;
                       case 21:
                           break;
                       case 22:
                           break;
                       case 23:
                           break;
                       case 24:
                           break;
                       case 25:
                           break;
                       case 26:
                           break;
                       case 27:
                           break;
                       case 28:
                           break;
                       case 29:
                           break;
                       case 30:
                           break;
                       case 31:
                           break;
                       case 32:
                           break;
                       case 33:
                           break;
                       case 34:
                           break;
                       case 35:
                           break;
                       case 36:
                           break;
                       case 37:
                           break;
                       case 38:
                           break;
                       case 39:
                           break;
                       case 40:
                           break;
                       case 41:
                           break;                               
                   }
                }
            }
        }
    }*/
    
}
