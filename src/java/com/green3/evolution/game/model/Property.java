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
public class Property implements Serializable, CommonEntity{
    
    private int id;
    private int type;
    private String description;
    private int linkedAnimalId;
    private boolean sub;
    
    private List<Animal> animals;
    
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    } 
    
    public void setType(int pType){
        type = pType;
    }

    public int getType(){
        return type;
    }
    
    public void setDescription(String pDescription){
        description = pDescription;
    }

    public String getDescription(){
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
    
    public boolean isAnimal(){
        return (getType() == 0) || (getType() == 29);
    }
    
    public boolean isTwin(){
        return (getType() == 21) || (getType() == 32) || (getType() == 37) || (getType() == 41);
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
    
}
