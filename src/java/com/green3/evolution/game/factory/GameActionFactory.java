/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.factory;


import com.green3.evolution.action.Action;
import com.green3.evolution.game.action.*;

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
            case GET_GAMEBOARD:
                action = new GetGameboardAction();
                break;
            case GET_CARDS:
                action = new GetCardsAction();
                break;
            case NEW_GAME:
                action = new NewGameAction();
                break;
            default:
                action = new GetGameboardAction();
        }
        return action;
    }

}
