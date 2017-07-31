/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.factory;


import com.green3.evolution.action.EmptyAction;
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
            case USER_GAMES:
                action = new GetUserGamesAction();
                break;
            case CHOOSE_GAME:
                action = new EmptyAction();
                break;
            case START_GAME:
                action = new StartGameAction();
                break;
            case LIST_CREATED_GAMES:
                action = new GetHostedGamesAction();
                break;
            case JOIN_GAME:
                action = new JoinGameAction();
                break;
            default:
                action = new GetGameboardAction();
        }
        return action;
    }

}
