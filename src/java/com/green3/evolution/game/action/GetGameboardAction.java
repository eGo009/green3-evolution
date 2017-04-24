/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.Card;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.game.model.GamesContainer;
import com.green3.evolution.game.model.Player;
import com.green3.evolution.game.model.Property;
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
        
        GameBoard game = getGameInfo(gameId, con);

        return game;
    }
    
    private GameBoard getGameInfo(int gameId, Connection con){
        GameBoard game = new GameBoard();
        try{
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT g.id, g.state, g.round, g.stage FROM ev_g_game AS g WHERE g.id=?;");
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
                        int round = rs.getInt("round");
                        int stage = rs.getInt("stage");
                        game.setCurrentRound(round);
                        game.setRoundStage(stage);
                        break;
                    case 2:
                        break;
                }
                game.setPlayers(getGamePlayers(gameId, con));
             }      
        }
        catch (SQLException ex){
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return game;
    }
        
    private List<Player> getGamePlayers(int gameId, Connection con){
        List<Player> players = new ArrayList<Player>();
        try{
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.user_id FROM ev_g_player AS p\n" +
"WHERE p.game_id=?;");
            getGameInfoStatement.setInt(1, gameId);
            ResultSet rs = getGameInfoStatement.executeQuery();
            
            while (rs.next()) {
                Player player = new Player();
                int id = rs.getInt("id");
                player.setId(id);
                String user = rs.getString("user_id");
                player.setUser(user);  
                player.setCardsOnHand(getPlayerCards(id, con));                
                players.add(player);
                
             }      
        }
        catch (SQLException ex){
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }
    
     private List<Card> getPlayerCards(int playerId, Connection con){
        List<Card> cards = new ArrayList<Card>();
        try{
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT c.id FROM ev_g_player_card AS pc\n" +
"  JOIN ev_g_card AS c ON pc.card_id=c.id\n" +
"WHERE pc.player_id=?;");
            getGameInfoStatement.setInt(1, playerId);
            ResultSet rs = getGameInfoStatement.executeQuery();
            
            while (rs.next()) {
                Card card = new Card();
                int id = rs.getInt("id");
                card.setId(id);
                card.setProperties(getCardsProperties(id, con));
             }      
        }
        catch (SQLException ex){
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cards;
    }
    
    private List<Property> getCardsProperties(int cardId, Connection con){
        List<Property> properties = new ArrayList<Property>();
        try{
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.type, p.description FROM ev_g_card_property AS cp\n" +
"  JOIN ev_g_property AS p ON cp.property_id=p.id\n" +
"WHERE cp.card_id=?;");
            getGameInfoStatement.setInt(1, cardId);
            ResultSet rs = getGameInfoStatement.executeQuery();
            
            while (rs.next()) {
                Property property = new Property();
                int id = rs.getInt("id");
                property.setId(id);
                int type = rs.getInt("type");
                property.setType(type);
                String description = rs.getString("description");
                property.setDescription(description);
                properties.add(property);
             }      
        }
        catch (SQLException ex){
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}
