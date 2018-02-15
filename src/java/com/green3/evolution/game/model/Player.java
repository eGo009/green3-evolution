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
public class Player implements Serializable, CommonEntity{
    
    private int id;
    private String user;
    
    private List<Card> cardsOnHand;
    private List<Animal> animals;
    private int actionOrder;
    private int roundOrder;
    private int active;
    
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    } 
    
    
    public void setUser(String pUser){
        user = pUser;
    }

    public String getUser(){
        return user;
    } 
    
    
    public void setCardsOnHand(List<Card> pCardsOnHand){
        cardsOnHand = pCardsOnHand;
    }

    public List<Card> getCardsOnHand(){
        return cardsOnHand;
    } 
    
    
    public void setAnimals(List<Animal> pAnimals){
        animals = pAnimals;
    }

    public List<Animal> getAnimals(){
        return animals;
    }

    /**
     * @return the actionOrder
     */
    public int getActionOrder() {
        return actionOrder;
    }

    /**
     * @param actionOrder the actionOrder to set
     */
    public void setActionOrder(int actionOrder) {
        this.actionOrder = actionOrder;
    }    
    
    /**
     * @return the active
     */
    public int getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }
    
    public boolean isHasPredator(){
        for (Animal animal : this.getAnimals()){
            if (animal.isPredator()){
                return true;
            }
        }
        return false;
    }

    public int getSpecializationACount(){
        int specializationACount = 0;
        for (Animal animal : this.getAnimals()){
            if (animal.isSpecializationA()){
                specializationACount++;
            }
        }
        return specializationACount;
    }
    
    /**
     * @return the roundOrder
     */
    public int getRoundOrder() {
        return roundOrder;
    }

    /**
     * @param roundOrder the roundOrder to set
     */
    public void setRoundOrder(int roundOrder) {
        this.roundOrder = roundOrder;
    }
    
    public int getHungryAnimalsCount(){
        int count = 0;
        for (Animal animal : this.getAnimals()){
            if ((animal.getInShell() == 0 && animal.getFeed() < animal.getNeededFeed()) || (animal.getEmptyFatTissueProps().size() > 0)){
                count++;
            }
        }
        return count;
    } 
    
}
