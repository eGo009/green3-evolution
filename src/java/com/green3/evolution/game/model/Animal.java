/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.model;

import com.green3.evolution.model.CommonEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class Animal implements Serializable, CommonEntity{
    
    private int id;
    
    private int feed;
    
    private int inShell;
    
    private int inHibernation;
    
    private int flighty;
   
    private int inRegen;
    
    private List<Property> properties;
    
   
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    }
    
    
    public void setProperties(List<Property> pProperties){
        properties = pProperties;
    }

    public List<Property> getProperties(){
        return properties;
    }

    /**
     * @return the feed
     */
    public int getFeed() {
        return feed;
    }

    /**
     * @param feed the feed to set
     */
    public void setFeed(int feed) {
        this.feed = feed;
    }

    /**
     * @return the neededFeed
     */
    public int getNeededFeed() {
        int neededFeed=1;
        for (Property prop : getProperties()){
            if (prop.getActive() >= 0){
                neededFeed+=prop.getNeddedFeed();
            }            
        }
        
        return neededFeed;
    }
    
    public List<Property> getFatTissueProps(){
        List<Property> fatTissue = new ArrayList<Property>();
        for (Property prop : getProperties()){
            if (prop.getType() == 3){
                fatTissue.add(prop);
            }            
        }
        return fatTissue;
    }
    
    public List<Property> getFilledFatTissueProps(){
        List<Property> filledFatTissue = new ArrayList<Property>();
        for (Property prop : getFatTissueProps()){
            if (prop.getActive() == 2){
                filledFatTissue.add(prop);
            }            
        }
        return filledFatTissue;
    }
    
    public int getEmptyFatTissueSize(){
        return getEmptyFatTissueProps().size();
    }
    
    public List<Property> getEmptyFatTissueProps(){
        List<Property> emptyFatTissue = new ArrayList<Property>();
        for (Property prop : getFatTissueProps()){
            if (prop.getActive() == 1){
                emptyFatTissue.add(prop);
            }            
        }
        return emptyFatTissue;
    }
    
    
    
    public boolean isPredator(){
        for (Property property : getProperties()){
            if (property.getType() == 1 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    } 
    
    
    public boolean isVivaparous(){
        for (Property property : getProperties()){
            if (property.getType() == 10){
                return true;
            }
        }
        return false;
    } 
    
    public boolean isHerding(){
        for (Property property : getProperties()){
            if (property.getType() == 11){
                return true;
            }
        }
        return false;
    }
    
    public boolean isTailLossActive(){
        for (Property property : getProperties()){
            if (property.getType() == 14 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isMimicryActive(){
        for (Property property : getProperties()){
            if (property.getType() == 25 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isFlightyActive(){
        for (Property property : getProperties()){
            if (property.getType() == 15 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isRStrategyActive(){
        for (Property property : getProperties()){
            if (property.getType() == 16 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isSubSimbiont(){
        for (Property property : getProperties()){
            if (property.getType() == 17 && property.isSub()){
                return true;
            }
        }
        return false;
    }
    
    public boolean isAedificator(){
        for (Property property : getProperties()){
            if (property.getType() == 18 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isNeoplasm(){
        for (Property property : getProperties()){
            if (property.getType() == 19 ){
                return true;
            }
        }
        return false;
    }
    
    public boolean isRegen(){
        for (Property property : getProperties()){
            if (property.getType() == 2 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isCamouflage(){
        for (Property property : getProperties()){
            if (property.getType() == 22 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isSpecializationA(){
        for (Property property : getProperties()){
            if (property.getType() == 23 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isMetamorphose(){
        for (Property property : getProperties()){
            if (property.getType() == 24 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isBig(){
        for (Property property : getProperties()){
            if (property.getType() == 26 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isSharpVision(){
        for (Property property : getProperties()){
            if (property.getType() == 28 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isAnglerfish(){
        for (Property property : getProperties()){
            if (property.getType() == 29 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    public boolean isActiveDefence(){
        for (Property property : getProperties()){
            if (property.getActive()!=1){
                continue;
            }
            switch (property.getType()){
                case 12:
                case 25:
                case 27:
                    return true;
            }            
        }
        return false;
    }
    
    public boolean isActiveDefence(int blockedPropertyType){
        for (Property property : getProperties()){
            if (property.getActive()!=1 || property.getType() == blockedPropertyType){
                continue;
            }
            switch (property.getType()){
                case 12:
                case 25:
                case 27:
                    return true;
            }            
        }
        return false;
    }

    /**
     * @return the inShell
     */
    public int getInShell() {
        return inShell;
    }

    /**
     * @param inShell the inShell to set
     */
    public void setInShell(int inShell) {
        this.inShell = inShell;
    }

    /**
     * @return the inHibernation
     */
    public int getInHibernation() {
        return inHibernation;
    }

    /**
     * @param inHibernation the inHibernation to set
     */
    public void setInHibernation(int inHibernation) {
        this.inHibernation = inHibernation;
    }

    /**
     * @return the flighty
     */
    public int getFlighty() {
        return flighty;
    }

    /**
     * @param flighty the flighty to set
     */
    public void setFlighty(int flighty) {
        this.flighty = flighty;
    }

    /**
     * @return the inRegen
     */
    public int getInRegen() {
        return inRegen;
    }

    /**
     * @param inRegen the inRegen to set
     */
    public void setInRegen(int inRegen) {
        this.inRegen = inRegen;
    }
    
    
    public boolean isIntellectual(){
        for (Property property : getProperties()){
            if (property.getType() == 4 && property.getActive() == 1){
                return true;
            }
        }
        return false;
    }
    
    
    
  
}
