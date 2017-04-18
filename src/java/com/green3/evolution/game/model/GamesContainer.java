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
public class GamesContainer implements Serializable, CommonEntity{
    
    private List<GameBoard> createdGames;
    private List<GameBoard> ongoingGames;
    private List<GameBoard> finishedGames;
    
    public GamesContainer(){
        createdGames = new ArrayList<GameBoard>();
        ongoingGames = new ArrayList<GameBoard>();
        finishedGames = new ArrayList<GameBoard>();
        
    }
    
    public void setCreatedGames(List<GameBoard> pCreatedGames){
        createdGames = pCreatedGames;
    }

    public List<GameBoard> getCreatedGames(){
        return createdGames;
    }    
    
    public void setOngoingGames(List<GameBoard> pOngoingGames){
        ongoingGames = pOngoingGames;
    }

    public List<GameBoard> getOngoingGames(){
        return ongoingGames;
    }   
    
    public void setFinishedGames(List<GameBoard> pFinishedGames){
        finishedGames = pFinishedGames;
    }

    public List<GameBoard> getFinishedGames(){
        return finishedGames;
    }   
    
}