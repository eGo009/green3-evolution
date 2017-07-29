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
public class NewGameAction extends GameAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        int gameId = getGameManager().createNewGame(connection);
        String userId = (String)params.get(GameConstants.PARAM_USER_ID);
        getGameManager().addPlayerToGame(gameId, userId);
        GameBoard game = new GameBoard();
        game.setId(gameId);
        return game;
    }
}
