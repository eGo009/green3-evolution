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
    private int supply; 
    private int defendingAnimalId; 
    private int attackingAnimalId;
    private int intellectualAnimalId;
    
     
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
        boolean playerFound = false;
        for (Player player : getPlayers()){
            if (getIntellectualAnimalId() > 0){
                for (Animal animal : player.getAnimals()){
                    if (animal.getId() == getIntellectualAnimalId()){
                        turnPlayer = player.getUser();
                        playerFound = true;
                        break;
                    }
                }
                if (playerFound){
                    break;
                }
            }
            else if (getDefendingAnimalId() > 0){
                for (Animal animal : player.getAnimals()){
                    if (animal.getId() == getDefendingAnimalId()){
                        turnPlayer = player.getUser();
                        playerFound = true;
                        break;
                    }
                }
                if (playerFound){
                    break;
                }
            }
            
            if (player.getActionOrder() != -1 && player.getActionOrder() < minOrder){
                minOrder = player.getActionOrder();
                turnPlayer = player.getUser();
            }
        }
        return turnPlayer;
    }
    
    public int getSpecializationACount(){
        int specializationACount = 0;
        for (Player player : getPlayers()){
            specializationACount += player.getSpecializationACount();
        }
        return specializationACount;
    }

    /**
     * @return the supply
     */
    public int getSupply() {
        return supply;
    }

    /**
     * @param supply the supply to set
     */
    public void setSupply(int supply) {
        this.supply = supply;
    }

    /**
     * @return the defendingAnimalId
     */
    public int getDefendingAnimalId() {
        return defendingAnimalId;
    }

    /**
     * @param defendingAnimalId the defendingAnimalId to set
     */
    public void setDefendingAnimalId(int defendingAnimalId) {
        this.defendingAnimalId = defendingAnimalId;
    }

    /**
     * @return the attackingAnimalId
     */
    public int getAttackingAnimalId() {
        return attackingAnimalId;
    }

    /**
     * @param attackingAnimalId the attackingAnimalId to set
     */
    public void setAttackingAnimalId(int attackingAnimalId) {
        this.attackingAnimalId = attackingAnimalId;
    }

    /**
     * @return the intellectualAnimalId
     */
    public int getIntellectualAnimalId() {
        return intellectualAnimalId;
    }

    /**
     * @param intellectualAnimalId the intellectualAnimalId to set
     */
    public void setIntellectualAnimalId(int intellectualAnimalId) {
        this.intellectualAnimalId = intellectualAnimalId;
    }
    
       
    
}
