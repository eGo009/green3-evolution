/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;
import java.sql.Connection;
import java.util.Map;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GetGameboardAction extends GameAction{
    
    @Override
    public CommonEntity doAction(Connection con, Map<String,Object> params){
        int gameId = (int)params.get(GameConstants.PARAM_GAME_ID);
        
        GameBoard game = getGameManager().getGameInfo(gameId, con);
        
        int cardsLeft = getGameManager().getCardsLeft(gameId, con);
        game.setCardsLeft(cardsLeft);
        
        return game;
    }
}
