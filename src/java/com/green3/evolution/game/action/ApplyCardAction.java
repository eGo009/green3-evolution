/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.util.Map;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class ApplyCardAction extends GameAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        String playerId = (String)params.get(GameConstants.PARAM_PLAYER_ID);
        String cardId = (String)params.get(GameConstants.PARAM_CARD_ID);
        String propertyType = (String)params.get(GameConstants.PARAM_PROPERTY_TYPE);    
        String animalId = (String)params.get(GameConstants.PARAM_ANIMAL_ID);    
        String linkedAnimalId = (String)params.get(GameConstants.PARAM_LINKED_ANIMAL_ID);    
        
        getGameManager().applyCard(connection, Integer.valueOf(playerId), Integer.valueOf(cardId), Integer.valueOf(propertyType), Integer.valueOf(animalId), Integer.valueOf(linkedAnimalId));
        getGameManager().switchStageTurn(connection, Integer.valueOf(playerId));
                
        return null;
    }
}
