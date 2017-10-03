/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.manager;

import com.green3.evolution.game.action.*;
import com.green3.evolution.game.model.Animal;
import com.green3.evolution.game.model.Card;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.game.model.GamesContainer;
import com.green3.evolution.game.model.Player;
import com.green3.evolution.game.model.Property;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GameManager {

    public GameBoard getGameInfo(int gameId, Connection con) {
        GameBoard game = new GameBoard();
        try {
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT g.id, g.state, g.round, g.stage FROM ev_g_game AS g WHERE g.id=?;");
            getGameInfoStatement.setInt(1, gameId);
            ResultSet rs = getGameInfoStatement.executeQuery();

            while (rs.next()) {
                game.setId(rs.getInt("id"));
                int state = rs.getInt("state");
                game.setStatus(state);
                switch (state) {
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
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return game;
    }

    public List<Player> getGamePlayers(int gameId, Connection con) {
        List<Player> players = new ArrayList<Player>();
        try {
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.user_id, p.stage_order FROM ev_g_player AS p\n"
                    + "WHERE p.game_id=?;");
            getGameInfoStatement.setInt(1, gameId);
            ResultSet rs = getGameInfoStatement.executeQuery();

            while (rs.next()) {
                Player player = new Player();
                int id = rs.getInt("id");
                player.setId(id);
                String user = rs.getString("user_id");
                player.setUser(user);
                int order = rs.getInt("stage_order");
                player.setActionOrder(order);
                player.setCardsOnHand(getPlayerCards(id, con));
                player.setAnimals(getPlayerAnimals(id, con));
                players.add(player);

            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

    public List<Card> getPlayerCards(int playerId, Connection con) {
        List<Card> cards = new ArrayList<Card>();
        try {
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT c.id FROM ev_g_player_card AS pc\n"
                    + "  JOIN ev_g_card AS c ON pc.card_id=c.id\n"
                    + "WHERE pc.player_id=? and pc.status=0;");
            getGameInfoStatement.setInt(1, playerId);
            ResultSet rs = getGameInfoStatement.executeQuery();

            while (rs.next()) {
                Card card = new Card();
                int id = rs.getInt("id");
                card.setId(id);
                card.setProperties(getCardsProperties(id, con));
                cards.add(card);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cards;
    }
    
    private List<Animal> getPlayerAnimals(int playerId, Connection con) {
        List<Animal> animals = new ArrayList<Animal>();
        try {
            PreparedStatement getAnimalsStatement = con.prepareStatement("SELECT id FROM ev_g_animal WHERE player_id=?;");
            getAnimalsStatement.setInt(1, playerId);
            ResultSet rs = getAnimalsStatement.executeQuery();

            while (rs.next()) {
                Animal animal = new Animal();
                int id = rs.getInt("id");
                animal.setId(id);
                animal.setProperties(getAnimalProperties(id, con));
                animals.add(animal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return animals;
    }

    public List<Property> getCardsProperties(int cardId, Connection con) {
        List<Property> properties = new ArrayList<Property>();
        try {
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.type, p.description FROM ev_g_card_property AS cp\n"
                    + "  JOIN ev_g_property AS p ON cp.property_id=p.id\n"
                    + "WHERE cp.card_id=?;");
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
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
    
    public List<Property> getAnimalProperties(int animalId, Connection con) {
        List<Property> properties = new ArrayList<Property>();
        try {
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.type, p.description FROM ev_g_animal_property AS ap\n"
                    + "  JOIN ev_g_property AS p ON ap.property_type=p.type\n"
                    + "WHERE ap.animal_id=?;");
            getGameInfoStatement.setInt(1, animalId);
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
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    public GamesContainer getUserGames(String userId, Connection con) {
        GamesContainer games = new GamesContainer();
        try {
            PreparedStatement getGamesByUserStatement = con.prepareStatement("SELECT g.id, g.state FROM ev_g_player AS p \n"
                    + "JOIN ev_g_game AS g ON p.game_id=g.id\n"
                    + "WHERE p.user_id=?;");
            getGamesByUserStatement.setString(1, userId);
            ResultSet rs = getGamesByUserStatement.executeQuery();

            while (rs.next()) {
                GameBoard game = new GameBoard();
                game.setId(rs.getInt("id"));
                int state = rs.getInt("state");
                game.setStatus(state);
                switch (state) {
                    case 0:
                        games.getCreatedGames().add(game);
                        break;
                    case 1:
                        games.getOngoingGames().add(game);
                        break;
                    case 2:
                        games.getFinishedGames().add(game);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return games;
    }
    
     public GamesContainer getHostedGames(String userId, Connection con) {
        GamesContainer games = new GamesContainer();
        try {
            PreparedStatement getHostedGamesStatement = con.prepareStatement("SELECT g.id FROM ev_g_player AS p \n"
                    + "JOIN ev_g_game AS g ON p.game_id=g.id\n"
                    + "WHERE NOT p.user_id=? AND g.state=?;");
            getHostedGamesStatement.setString(1, userId);
            getHostedGamesStatement.setInt(2, 0);
            ResultSet rs = getHostedGamesStatement.executeQuery();

            while (rs.next()) {
                GameBoard game = new GameBoard();
                game.setId(rs.getInt("id"));
                game.setStatus(0);
                games.getCreatedGames().add(game);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return games;
    }

    public int createNewGame(Connection connection) {
        int newGameId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("insert into ev_g_game (state) VALUES (?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, 0);
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newGameId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newGameId;
    }

    public void addPlayerToGame(Connection connection, int gameId, String userId) {
       try {
            PreparedStatement addPlayerStatement = connection.prepareStatement("insert into ev_g_player (user_id, game_id) VALUES (?,?);",
                             Statement.RETURN_GENERATED_KEYS);
            addPlayerStatement.setString(1, userId);
            addPlayerStatement.setInt(2, gameId);
            addPlayerStatement.execute();
       }
       catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    public void createCardDeck(Connection connection, int gameId, List<Integer> cardIds) {
        try {
            for (Integer cardId : cardIds) {
                PreparedStatement statement = connection.prepareStatement("insert into ev_g_player_card (card_id, status, game_id) VALUES (?,?,?);",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, cardId);
                statement.setInt(2, 0);
                statement.setInt(3, gameId);
                statement.execute();
            }

            /*int affectedRows = statement.executeUpdate();
            if (affectedRows==0){
                throw new SQLException("Creating user failed, no affected rows.");
            }*/
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Integer> getCardIds(Connection connection) {
        List<Integer> cardIds = new ArrayList<Integer>();
        try {
            PreparedStatement getCardsStatement = connection.prepareStatement("SELECT c.id FROM ev_g_card AS c");
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                cardIds.add(id);
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return cardIds;
    }

    public void setInitialCardsToPlayers(List<Player> players, List<Integer> cardIds, int gameId, Connection con) {
        for (Player player : players) {
            setRandomCardsToPlayer(player, cardIds, 6, gameId, con);
        }
    }

    public void setRandomCardsToPlayer(Player player, List<Integer> cards, int cardsToSetQuantity, int gameId, Connection con) {
        try {       
            for (int i = 0; i < cardsToSetQuantity; i++) {
                int cardsSize = cards.size();
                Random r = new Random(cardsSize);
                int selectedCardIndex = r.nextInt(cardsSize - 1);
                Integer selectedCardId = cards.remove(selectedCardIndex);

                PreparedStatement statement = con.prepareStatement("update ev_g_player_card set player_id=?, game_id=null where card_id=? and game_id=?;");
                statement.setInt(1, player.getId());
                statement.setInt(2, selectedCardId);
                statement.setInt(3, gameId);
                statement.execute();

            }
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setInitialTurnOrderForPlayers(List<Player> players, Connection con){
        int order = 0;
        for (Player player : players) {
            setStageOrderToPlayer(player, order, con);
            setRoundOrderToPlayer(player, order, con);
            order++;
        }
    }

    public void setStageOrderToPlayer(Player player, int order, Connection con) {
        setOrderToPlayer(player, order, "update ev_g_player set stage_order=? where id=?;", con);
    }
    
    public void setRoundOrderToPlayer(Player player, int order, Connection con) {
        setOrderToPlayer(player, order, "update ev_g_player set round_order=? where id=?;", con);
    }
    
    public void setOrderToPlayer(Player player, int order, String preparedStatement, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement(preparedStatement);
            statement.setInt(1, order);
            statement.setInt(2, player.getId());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changeGameState(int gameId, int newGameState, Connection con){
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_game set state=? where id=?;");
            statement.setInt(1, newGameState);
            statement.setInt(2, gameId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCardsLeft(int gameId, Connection con) {
        int cardsLeft = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT COUNT(*) as cardsCount FROM evolutiondb.ev_g_player_card WHERE game_id=?;");
            getCardsStatement.setInt(1, gameId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                cardsLeft = rs.getInt("cardsCount");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return cardsLeft;
    }

    public void applyCard(Connection connection, int playerId, int cardId, int propertyType, int animalId, int linkedAnimalId) {
        switch (propertyType){
            case 0: 
                addAnimal(connection, playerId);
                break;
            case 17:
                addProperty(connection, animalId, propertyType, linkedAnimalId);
                addProperty(connection, linkedAnimalId, propertyType, animalId, 1);
                break;
            case 21:
            case 32:
            case 37:
            case 41:
                addProperty(connection, animalId, propertyType, linkedAnimalId);
                addProperty(connection, linkedAnimalId, propertyType, animalId);
                break;
            case 29:
                animalId = addAnimal(connection, playerId);
                addProperty(connection, animalId, propertyType, linkedAnimalId);
                break;
            default:
                addProperty(connection, animalId, propertyType, linkedAnimalId);
                break;
        }
        
        markCardAsUsed(connection, playerId, cardId);
    }

    private void markCardAsUsed(Connection connection, int playerId, int cardId) {
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_player_card set status=1 where player_id=? and card_id=?;");
            statement.setInt(1, playerId);
            statement.setInt(2, cardId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int addAnimal(Connection connection, int playerId) {
        int animalId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("insert into ev_g_animal (player_id) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, playerId);
            statement.execute();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    animalId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return animalId;
    }
    
    private int addProperty(Connection connection, int animalId, int propertyType, int linkedAnimalId){
        return addProperty(connection, animalId, propertyType, linkedAnimalId, 0);
    }
            
    private int addProperty(Connection connection, int animalId, int propertyType, int linkedAnimalId, int sub) {
        int propertyId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("insert into ev_g_animal_property (animal_id, property_type, linked_animal_id, sub) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, animalId);
            statement.setInt(2, propertyType);
            statement.setInt(3, linkedAnimalId);
            statement.setInt(4, sub);
            statement.execute();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    propertyId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return propertyId;
    }
    
    public int getPlayersCount(int playerId, Connection con) {
        int playersCount = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT COUNT(*) AS players_count FROM ev_g_player WHERE game_id IN (SELECT game_id FROM ev_g_player WHERE id=?);");
            getCardsStatement.setInt(1, playerId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                playersCount = rs.getInt("players_count");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return playersCount;
    }
    
    public int getCurrentPlayersStageTurn(int playerId, Connection con) {
        int stageTurn = -1;
        try {
            PreparedStatement getStageTurnStatement = con.prepareStatement("SELECT stage_order FROM ev_g_player WHERE id=?;");
            getStageTurnStatement.setInt(1, playerId);
            ResultSet rs = getStageTurnStatement.executeQuery();
            while (rs.next()) {
                stageTurn = rs.getInt("stage_order");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return stageTurn;
    }


    public void updateStageTurn(Connection connection, int playerId, int newStageTurnValue) {
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_player set stage_order=? where id=?;");
            statement.setInt(1, newStageTurnValue);
            statement.setInt(2, playerId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchStageTurn(Connection connection, int playerId) {
        int playersCount = getPlayersCount(playerId, connection);
        int currentStageTurn = getCurrentPlayersStageTurn(playerId, connection);
        updateStageTurn(connection, playerId, playersCount + currentStageTurn);    
    }   
    
}
