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
 * <b>active</b>:
 * <ul>
 * <li>-1 - disabled (by neoplasm)
 * <li>0 - not active (used)
 * <li>1 - active
 * <li>2 - permanently used (filled fat tissue)
 * <li>9 - blocked (by intellect)
 * </ul>
 * 
 * @author Alex_Ihnatsiuck
 */
public class Property implements Serializable, CommonEntity {

    private int id;
    private int type;
    private String description;
    private int linkedAnimalId;
    private boolean sub;
    private int active;
    private int refreshing;

    
    private List<Animal> animals;

    public void setId(int pId) {
        id = pId;
    }

    public int getId() {
        return id;
    }

    public void setType(int pType) {
        type = pType;
    }

    public int getType() {
        return type;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return the animals
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * @param animals the animals to set
     */
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public boolean isAnimal() {
        return (getType() == 0) || (getType() == 29);
    }

    public boolean isTwin() {
        return (getType() == 17) || (getType() == 21) || (getType() == 32) || (getType() == 37) || (getType() == 41);
    }

    public int getNeddedFeed() {
        switch (getType()) {
            case 36:
                return 2;
            case 1:
            case 4:
            case 10:
            case 26:
            case 31:
            case 41:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * @return the linkedAnimalId
     */
    public int getLinkedAnimalId() {
        return linkedAnimalId;
    }

    /**
     * @param linkedAnimalId the linkedAnimalId to set
     */
    public void setLinkedAnimalId(int linkedAnimalId) {
        this.linkedAnimalId = linkedAnimalId;
    }

    /**
     * @return the sub
     */
    public boolean isSub() {
        return sub;
    }

    /**
     * @param sub the sub to set
     */
    public void setSub(boolean sub) {
        this.sub = sub;
    }

    /**
     * @return the active
     */
    public boolean isActiveState() {
        switch (getType()) {
            case 1:
            case 13:
            case 23:
            case 24:
            case 31:
            case 32:
            case 33:
            case 35:
            case 38:
            case 39:
                return this.getActive() == 1;
            default:
                return false;
        }
    }

    /**
     * @param active the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * @return the active
     */
    public int getActive() {
        return active;
    }

    /**
     * @return the refreshing
     */
    public int getRefreshing() {
        return refreshing;
    }

    /**
     * @param refreshing the refreshing to set
     */
    public void setRefreshing(int refreshing) {
        this.refreshing = refreshing;
    }

    
    
}
