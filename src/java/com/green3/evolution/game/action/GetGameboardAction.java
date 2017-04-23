/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.game.model.GamesContainer;
import com.green3.evolution.model.CommonEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GetGameboardAction extends DBAction{
    
    @Override
    public CommonEntity doAction(Connection con, Map<String,Object> params){
        int gameId = (int)params.get(GameConstants.PARAM_GAME_ID);
        
        GameBoard game = new GameBoard();
        /*try{
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT g.id, g.state FROM ev_g_player AS p \n" +
"JOIN ev_g_game AS g ON p.game_id=g.id\n" +
"WHERE p.user_id=?;");
            getGameInfoStatement.setInt(1, gameId);
            ResultSet rs = getGameInfoStatement.executeQuery();
            
            while (rs.next()) {
                game.setId(rs.getInt("id"));
                int state = rs.getInt("state");
                game.setStatus(state);
                switch (state){
                    case 0:
                        
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
             }      
        }
        catch (SQLException ex){
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return game;
        */
        
        System.out.println("Get gameboard");
        game.setId(gameId);
        game.setStatus(0);
        game.setCurrentRound(1);
        game.setRoundStage(0);
        List<String> cardDeck = new ArrayList<String>();
        cardDeck.add("asdasd");
        cardDeck.add("gfdgdfgd");
        game.setCardDeck(cardDeck);
        Set<String> players = new HashSet<String>();
        players.add("dew");
        players.add("ace");
        players.add("nick");
        game.setPlayers(players);
        return game;
    }
    
    
}
