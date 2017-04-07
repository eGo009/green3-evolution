/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.factory;


import com.green3.evolution.action.Action;
import com.green3.evolution.game.action.GameActionType;
import com.green3.evolution.game.action.FeedAction;
import com.green3.evolution.game.action.UseCardAction;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GameActionFactory {
    
    public static Action createAction(GameActionType actionType){
        Action action = null;
        switch (actionType){
            case FEED: 
                action = new FeedAction();
                break;
            case USE_CARD:
                action = new UseCardAction();
                break;            
        }
        return action;
    }
    
    

}
