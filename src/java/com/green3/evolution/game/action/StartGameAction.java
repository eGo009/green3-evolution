/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.Player;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class StartGameAction extends GameAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        int gameId = (int)params.get(GameConstants.PARAM_GAME_ID);
        List<Integer> cardIds = getGameManager().getCardIds(connection);
        getGameManager().createCardDeck(connection, gameId, cardIds);        
        List<Player> gamePlayers = getGameManager().getGamePlayers(gameId, connection);
        
        getGameManager().setInitialCardsToPlayers(gamePlayers, cardIds, gameId, connection);
        
        getGameManager().setInitialTurnOrderForPlayers(gamePlayers, connection);
        
        getGameManager().changeGameState(gameId, 1, connection);
      
       return null;
    } 
}
