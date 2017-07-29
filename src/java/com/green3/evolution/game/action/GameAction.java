/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.game.manager.GameManager;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public abstract class GameAction extends DBAction{
    
    private GameManager gameManager = new GameManager();
    
    protected GameManager getGameManager(){
        return gameManager;
    }
}
