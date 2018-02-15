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
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT g.id, g.state, g.round, g.stage, g.supply, g.at_animal_id, g.def_animal_id, g.int_animal_id FROM ev_g_game AS g WHERE g.id=?;");
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
                        int supply = rs.getInt("supply");
                        int attAnimalId = rs.getInt("at_animal_id");
                        int defAnimalId = rs.getInt("def_animal_id");
                        int intAnimalId = rs.getInt("int_animal_id");
                        game.setCurrentRound(round);
                        game.setRoundStage(stage);
                        game.setSupply(supply);
                        game.setAttackingAnimalId(attAnimalId);
                        game.setDefendingAnimalId(defAnimalId);
                        game.setIntellectualAnimalId(intAnimalId);
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
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.user_id, p.stage_order, p.round_order, p.active FROM ev_g_player AS p\n"
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
                int roundOrder = rs.getInt("round_order");
                player.setRoundOrder(roundOrder);
                int active = rs.getInt("active");
                player.setActive(active);
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
            PreparedStatement getAnimalsStatement = con.prepareStatement("SELECT id, feed, in_shell, in_hibernation, flighty, in_regen FROM ev_g_animal WHERE player_id=?;");
            getAnimalsStatement.setInt(1, playerId);
            ResultSet rs = getAnimalsStatement.executeQuery();

            while (rs.next()) {
                Animal animal = new Animal();
                int id = rs.getInt("id");
                animal.setId(id);
                int feed = rs.getInt("feed");
                animal.setFeed(feed);
                int inShell = rs.getInt("in_shell");
                animal.setInShell(inShell);
                int inHibernation = rs.getInt("in_hibernation");
                animal.setInHibernation(inHibernation);
                int flighty = rs.getInt("flighty");
                animal.setFlighty(flighty);
                int inRegen = rs.getInt("in_regen");
                animal.setInRegen(inRegen);
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
            PreparedStatement getGameInfoStatement = con.prepareStatement("SELECT p.id, p.type, p.description, ap.linked_animal_id, ap.active, ap.refreshing FROM ev_g_animal_property AS ap\n"
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
                int active = rs.getInt("active");
                property.setActive(active);
                int refreshing = rs.getInt("refreshing");
                property.setRefreshing(refreshing);
                String description = rs.getString("description");
                property.setDescription(description);
                int linkedAnimal = rs.getInt("linked_animal_id");
                property.setLinkedAnimalId(linkedAnimal);
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

    public List<Integer> setRandomCardsToPlayer(Player player, List<Integer> cards, int cardsToSetQuantity, int gameId, Connection con) {
        List<Integer> selectedCardIds = new ArrayList<Integer>();
        for (int i = 0; i < cardsToSetQuantity; i++) {
            int cardsSize = cards.size();
            Random r = new Random(cardsSize);
            int selectedCardIndex = r.nextInt(cardsSize - 1);
            Integer selectedCardId = cards.remove(selectedCardIndex);
            selectedCardIds.add(selectedCardId);
            setCardToPlayer(player.getId(), selectedCardId, gameId, con);
        }
        return selectedCardIds;
    }
    
    public void setCardToPlayer(int playerId, int cardId, int gameId, Connection con){
        try{
            PreparedStatement statement = con.prepareStatement("update ev_g_player_card set player_id=?, game_id=null where card_id=? and game_id=?;");
            statement.setInt(1, playerId);
            statement.setInt(2, cardId);
            statement.setInt(3, gameId);
            statement.execute();
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
        setStageOrderToPlayer(player.getId(), order, con);
    }
    
    public void setStageOrderToPlayer(int id, int order, Connection con) {
        setOrderToPlayer(id, order, "update ev_g_player set stage_order=? where id=?;", con);
    }
    
    public void setRoundOrderToPlayer(Player player, int order, Connection con) {
        setOrderToPlayer(player.getId(), order, "update ev_g_player set round_order=? where id=?;", con);
    }
    
    public void setOrderToPlayer(int playerId, int order, String preparedStatement, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement(preparedStatement);
            statement.setInt(1, order);
            statement.setInt(2, playerId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateGameStageForPlayer(int playerId, int stage, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_game set stage=? WHERE id IN (SELECT game_id FROM ev_g_player WHERE id=?);");
            statement.setInt(1, stage);
            statement.setInt(2, playerId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateGameStage(int gameId, int stage, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_game set stage=? WHERE id =?;");
            statement.setInt(1, stage);
            statement.setInt(2, gameId);
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

    public boolean applyCard(Connection connection, int playerId, int cardId, int propertyType, int animalId, int linkedAnimalId) {
        if (propertyType != 0 && propertyType != 29 && !validateAppliedProperty(playerId, propertyType, animalId, linkedAnimalId, connection)){
            return false;
        }
     
        switch (propertyType){
            case 0: 
                addAnimal(connection, playerId);
                break;
            case 2:
                if (isRegenerationApplyPossible(animalId, connection)){
                    addAnimal(connection, playerId);
                }
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
        return true;
    }
    
    
    private boolean validateAppliedProperty(int playerId, int propertyType, int animalId, int linkedAnimalId, Connection con) {
        
        if (validateTargetAnimalNotExist(con, animalId)){
            return false;
        }
        if (linkedAnimalId != -1 && validateTargetAnimalNotExist(con, linkedAnimalId)){
            return false;
        }
        if (validateAlreadyExistProperty(animalId, propertyType, con)){
            return false;
        }
        if (validateWrongTargetPlayer(playerId, animalId, propertyType, con)){
            return false;
        }
        if (linkedAnimalId != -1 && validateWrongTargetPlayer(playerId, linkedAnimalId, propertyType, con)){
            return false;
        }
        if (validateRegeneration(animalId, propertyType, con)){
            return false;
        }
        if (linkedAnimalId != -1 && validateRegeneration(linkedAnimalId, propertyType, con)){
            return false;
        }
        
        return true;
    }
    
    private boolean validateTargetAnimalNotExist(Connection con, int animalId) {        
        return getAnimalById(con, animalId) == null;
    }
    
    private boolean validateAlreadyExistProperty(int animalId, int propertyType, Connection con) {
        switch (propertyType){
            case 17:
            case 21:
            case 32:
            case 37:
            case 41:
                return false;
        }
        return getAnimalPropertyByType(animalId, propertyType, con) != null;
    }

    private boolean validateWrongTargetPlayer(int playerId, int animalId, int propertyType, Connection con) {
        switch (propertyType){
            case 19:
            case 36:
            case 41:
                return getPlayerIdByAnimalId(animalId, con) == playerId;
        }
        return getPlayerIdByAnimalId(animalId, con) != playerId;
    }
    
    private Animal getAnimalById(Connection con, int animalId){
        Animal animal = null;
        try {
            PreparedStatement getAnimalsStatement = con.prepareStatement("SELECT id, feed, in_shell, in_hibernation, flighty, in_regen FROM ev_g_animal WHERE id=?;");
            getAnimalsStatement.setInt(1, animalId);
            ResultSet rs = getAnimalsStatement.executeQuery();

            while (rs.next()) {
                animal = new Animal();
                int id = rs.getInt("id");
                animal.setId(id);
                int feed = rs.getInt("feed");
                animal.setFeed(feed);
                int inShell = rs.getInt("in_shell");
                animal.setInShell(inShell);
                int inHibernation = rs.getInt("in_hibernation");
                animal.setInHibernation(inHibernation);
                int flighty = rs.getInt("flighty");
                animal.setFlighty(flighty);
                int inRegen = rs.getInt("in_regen");
                animal.setInRegen(inRegen);
                animal.setProperties(getAnimalProperties(id, con));
             }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return animal;
    }
    
    private Property getAnimalPropertyByType(int animalId, int propertyType, Connection con){
        Property property = null;
        try {
            PreparedStatement getAnimalPropertyStatement = con.prepareStatement("SELECT id FROM ev_g_animal_property WHERE animal_id=? and property_type=?;");
            getAnimalPropertyStatement.setInt(1, animalId);
            getAnimalPropertyStatement.setInt(2, propertyType);
            ResultSet rs = getAnimalPropertyStatement.executeQuery();

            while (rs.next()) {
                property = new Property();
                int id = rs.getInt("id");
                property.setId(id);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return property;
    }
    
    private int getPlayerIdByAnimalId(int animalId, Connection con){
        int playerId = -1;
        try {
            PreparedStatement getAnimalPropertyStatement = con.prepareStatement("SELECT player_id FROM ev_g_animal WHERE id=?;");
            getAnimalPropertyStatement.setInt(1, animalId);
            ResultSet rs = getAnimalPropertyStatement.executeQuery();

            while (rs.next()) {
                playerId = rs.getInt("player_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return playerId;
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
        return addAnimal(connection, playerId, 0);
    }
    
    private int addAnimal(Connection connection, int playerId, int feed) {
        int animalId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("insert into ev_g_animal (player_id, feed) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, playerId);
            statement.setInt(2, feed);
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
        
        removeAnglerfish(connection, animalId);
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
    
    private void removeAnglerfish(Connection connection, int animalId) {
        
        try {
            PreparedStatement statement = connection.prepareStatement("remove from ev_g_animal_property where animal_id=? and property_type=29;", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, animalId);
            statement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }        
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
    
    public int getGamePlayersCount(int gameId, Connection con) {
        int playersCount = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT COUNT(*) AS players_count FROM ev_g_player WHERE game_id =?;");
            getCardsStatement.setInt(1, gameId);
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
        int cardsLeft = getCardsLeftForPlayer(playerId, connection);
        switchStageTurn(connection, playerId, cardsLeft);
    }
    
    public void switchStageTurn(Connection connection, int playerId, int cardsLeft) {        
        if (cardsLeft == 0){
            int playersLeft = getPlayersLeft(playerId, connection);
            if (playersLeft <= 1){
                goToNextStage(playerId, connection);              
            }
            else{
                passPlayer(playerId, connection);
            }            
            return;
        }
        passStageTurn(connection, playerId);  
    }
    
    public void finishFeeding(Connection connection, int playerId){
        int playersLeft = getPlayersLeft(playerId, connection);
        if (playersLeft <= 1){
            goToNextStage(playerId, connection);              
        }
        else{
            passPlayer(playerId, connection);
        }           
    }
    
    public void passStageTurn(Connection connection, int playerId) {
        int playersCount = getPlayersCount(playerId, connection);
        int currentStageTurn = getCurrentPlayersStageTurn(playerId, connection);
        updateStageTurn(connection, playerId, playersCount + currentStageTurn); 
    }
    
    public int getCardsLeftForPlayer(int playerId, Connection con) {
        int cardsLeft = 0;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT COUNT(*) as cardsCount FROM ev_g_player_card WHERE player_id=? and status=0;");
            getCardsStatement.setInt(1, playerId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                cardsLeft = rs.getInt("cardsCount");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return cardsLeft;
    }

    public int getPlayersLeft(int playerId, Connection con) {
        int playersCount = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT COUNT(*) AS players_count FROM ev_g_player WHERE game_id IN (SELECT game_id FROM ev_g_player WHERE id=?) and stage_order > -1;");
            getCardsStatement.setInt(1, playerId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                playersCount = rs.getInt("players_count");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get players count from database!", sex);
        }
        return playersCount;
    }
    
    public int getGameStageForPlayer(int playerId, Connection con) {
        int stage = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT stage FROM ev_g_game WHERE id IN (SELECT game_id FROM ev_g_player WHERE id=?);");
            getCardsStatement.setInt(1, playerId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                stage = rs.getInt("stage");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get players count from database!", sex);
        }
        return stage;
    }
    
    public int getGameStage(int gameId, Connection con) {
        int stage = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT stage FROM ev_g_game WHERE id =?;");
            getCardsStatement.setInt(1, gameId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                stage = rs.getInt("stage");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get players count from database!", sex);
        }
        return stage;
    }
    
    public int getPlayerRoundOrder(int playerId, Connection con) {
        int roundOrder = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("select round_order from ev_g_player where id=?;");
            getCardsStatement.setInt(1, playerId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                roundOrder = rs.getInt("round_order");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get players count from database!", sex);
        }
        return roundOrder;
    }

    public void goToNextStage(int playerId, Connection connection) {
        int currentStage = getGameStageForPlayer(playerId, connection);
        updateGameStageForPlayer(playerId, currentStage+1, connection);
        resetStageOrderForAllPlayers(playerId, connection);
    }
    
    public void goToNextStageForGame(int gameId, Connection connection) {
        int currentStage = getGameStage(gameId, connection);
        updateGameStage(gameId, currentStage+1, connection);
    }

    public void passPlayer(int playerId, Connection connection) {
        setStageOrderToPlayer(playerId, -1, connection);
    }

    private void resetStageOrderForAllPlayers(int playerId, Connection connection) {
          try {
            PreparedStatement getPlayersStatement = connection.prepareStatement("SELECT id FROM ev_g_player WHERE game_id IN (SELECT game_id FROM ev_g_player WHERE id=?);");
            getPlayersStatement.setInt(1, playerId);
            ResultSet rs = getPlayersStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                resetStageOrderForPlayer(id, connection);
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get players count from database!", sex);
        }
    }

    private void resetStageOrderForPlayer(int id, Connection connection) {
        int roundOrder = getPlayerRoundOrder(id, connection);
        updateStageTurn(connection, id, roundOrder);
    }

    public void generateFoodSupply(int gameId, Connection connection) {
        int playersCount = getGamePlayersCount(gameId, connection);
        int foodSupply = generateFoodSupply(playersCount);
        int supplyFromAedificators = getSupplyFromAedificators(gameId, connection);
        updateSupply(gameId, foodSupply+supplyFromAedificators, connection);
        runNeoplasm(gameId, connection);
    }

    private int generateFoodSupply(int playersCount) {
        return generateFoodSupply(playersCount, false);
    }
    
    private int generateFoodSupply(int playersCount, boolean isMigration) {
        if (!isMigration){
            switch (playersCount){
                case 2:
                    int random = generateRandomSupply();
                    return random+2;
                case 3:
                    int random1 = generateRandomSupply();
                    int random2 = generateRandomSupply();
                    return random1+random2;
                case 4:
                    int random3 = generateRandomSupply();
                    int random4 = generateRandomSupply();
                    return random3+random4+2;
                case 5:
                    int random5 = generateRandomSupply();
                    int random6 = generateRandomSupply();
                    int random7 = generateRandomSupply();
                    return random5+random6+random7+2;
                case 6:
                    int random8 = generateRandomSupply();
                    int random9 = generateRandomSupply();
                    int random10 = generateRandomSupply();
                    return random8+random9+random10+3;
                case 7:
                    int random11 = generateRandomSupply();
                    int random12 = generateRandomSupply();
                    int random13 = generateRandomSupply();
                    int random14 = generateRandomSupply();
                    return random11+random12+random13+random14+2;
                case 8:
                    int random15 = generateRandomSupply();
                    int random16 = generateRandomSupply();
                    int random17 = generateRandomSupply();
                    int random18 = generateRandomSupply();
                    return random15+random16+random17+random18+4;
            }
        }
        return -1;
    }

    private void updateSupply(int gameId, int foodSupply, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_game set supply=? WHERE id=?;");
            statement.setInt(1, foodSupply);
            statement.setInt(2, gameId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int generateRandomSupply(){
        long random = Math.round(Math.random()*100);
        if (random < 17){
            return 1;
        }
        if (random < 33){
            return 2;
        }
        if (random < 50){
            return 3;
        }
        if (random < 67){
            return 4;
        }
        if (random < 83){
            return 5;
        }
        return 6;
    }
    
    public boolean feedAnimalFromFoodSupply(int animalId, int playerId, int gameId, Connection connection){        
        int currentSupply = getCurrentSupply(gameId, connection);
        if (currentSupply < 1){
            return false;
        }
        if (!validateStates(connection, animalId)){
            return false;
        }
        if (!incrementAnimalFeed(animalId, connection)){
            return false;
        }
        feedLinkedAnimals(animalId, 1, true, connection);
        updateSupply(gameId, currentSupply-1, connection);
        deactivatePlayer(playerId, connection);
        return true;
    }
    
    public void checkAndApplyVivaparous(int playerId, int animalId, Connection connection){
        Animal animal = getAnimalById(connection, animalId);
        if (animal.getFeed()>=animal.getNeededFeed() && animal.isVivaparous()){
            addAnimal(connection, playerId, 1);
        }
    }
    
    public boolean decrementSupply(int gameId, Connection connection){
        int currentSupply = getCurrentSupply(gameId, connection);
        if (currentSupply < 1){
            return false;
        }
        updateSupply(gameId, currentSupply-1, connection);
        return true;
    }
    
    public boolean incrementAnimalFeed(int animalId, Connection connection){
        return incrementAnimalFeed(animalId, 1, connection);
    }
    
    public boolean incrementAnimalFeed(int animalId, int increment, Connection connection){
        Animal animal = getAnimalById(connection, animalId);
        int currentFeed = getCurrentAnimalFeed(animalId, connection);
        int neededFeed = animal.getNeededFeed();
        List<Property> emptyFatTissue = animal.getEmptyFatTissueProps();
        if (currentFeed >= neededFeed){
            if (emptyFatTissue.size() == 0){
                return false;
            }            
        }
        int foodLeft = currentFeed + increment - neededFeed;
        int index = 0;
        while (foodLeft > 0 && emptyFatTissue.size() > index){
            fillFatTissue(emptyFatTissue.get(index), connection);
            foodLeft--;
            index++;
        }
        
        int newFeedValue = currentFeed+increment;
        if (newFeedValue > neededFeed){
            newFeedValue = neededFeed;
        }
        updateAnimalFeed(animalId, newFeedValue, connection);
        return true;
    }
    
    public void updateAnimalFeed(int animalId, int newFeedValue, Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_animal set feed=? WHERE id=?;");
            statement.setInt(1, newFeedValue);
            statement.setInt(2, animalId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int getCurrentSupply(int gameId, Connection con) {
        int supply = -1;
        try {
            PreparedStatement getSupplyStatement = con.prepareStatement("SELECT supply FROM ev_g_game WHERE id=?;");
            getSupplyStatement.setInt(1, gameId);
            ResultSet rs = getSupplyStatement.executeQuery();
            while (rs.next()) {
                supply = rs.getInt("supply");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return supply;
    }
    
    public int getCurrentAnimalFeed(int animalId, Connection con) {
        int feed = -1;
        try {
            PreparedStatement getAnimalFeedStatement = con.prepareStatement("SELECT feed FROM ev_g_animal WHERE id=?;");
            getAnimalFeedStatement.setInt(1, animalId);
            ResultSet rs = getAnimalFeedStatement.executeQuery();
            while (rs.next()) {
                feed = rs.getInt("feed");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
        return feed;
    }

    public void deactivatePlayer(int playerId, Connection connection) {
        updatePlayerActivity(playerId, 0, connection);
    }
    
    public void activatePlayer(int playerId, Connection connection) {
        updatePlayerActivity(playerId, 1, connection);
    }
    
    private void updatePlayerActivity(int playerId, int newValue, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_player set active=? WHERE id=?;");
            statement.setInt(1, newValue);
            statement.setInt(2, playerId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean prepareAttackAnimal(int gameId, int playerId, int predatorAnimalId, int targetAnimalId, Connection con){
        Animal animal = getAnimalById(con, predatorAnimalId);        
        return attackAnimal(gameId, playerId, predatorAnimalId, targetAnimalId, animal.isIntellectual(), con);
    }
   
    public boolean attackAnimal(int gameId, int playerId, int predatorAnimalId, int targetAnimalId, boolean intellectual, Connection con){
        return attackAnimal(gameId, playerId, predatorAnimalId, targetAnimalId, intellectual, -1, con);
    }
    
    public boolean attackAnimal(int gameId, int attackingPlayerId, int predatorAnimalId, int targetAnimalId, boolean intellectual, int blockedPropertyId, Connection con){
        int blockedPropertyType = -1;
        if (blockedPropertyId > 0){
            switchToIntellectualChoice(gameId, -1, -1, con);
            blockedPropertyType = getPropertyById(blockedPropertyId, con).getType();
        }
        if (!validateAttackAnimal(predatorAnimalId, targetAnimalId, con)){
            return false;
        }
        if (!validateStates(con, predatorAnimalId)){
            return false;
        }
        int defPlayerId = getPlayerIdByAnimalId(targetAnimalId, con);
        int defencies = couldBeAttacked(gameId, defPlayerId, predatorAnimalId, targetAnimalId, con);
        
        if ((defencies > 1) || ((defencies == 1) && !intellectual)){
            return false;
        }
        if (defencies == 1){
            intellectual = false;
            deactivateProperty(predatorAnimalId, 4, con);
        }
        if (isAnglerfish(targetAnimalId, con)){
            convertAnglerfishToPredator(targetAnimalId, con);
            deactivateProperty(predatorAnimalId, 1, con);
            deactivatePlayer(attackingPlayerId, con);
            return attackAnimal(gameId, defPlayerId, targetAnimalId, predatorAnimalId, true, con);
        }
        
        if (hasActiveDefence(gameId, defPlayerId, predatorAnimalId, targetAnimalId, blockedPropertyType, con)){
            if (intellectual){
               switchToIntellectualChoice(gameId, predatorAnimalId, targetAnimalId, con);
               return true;
            }
            if (blockedPropertyType > 0){
                blockProperty(blockedPropertyId, con);
            }
            switchToDefendingChoice(gameId, predatorAnimalId, targetAnimalId, con);
            return true;
        }
        
        processSuccessAttack(attackingPlayerId, predatorAnimalId, targetAnimalId, con);
        return true;
    }
    
    public boolean attackAnimal(int gameId, int playerId, int predatorAnimalId, int targetAnimalId, Connection con){
        return attackAnimal(gameId, playerId, predatorAnimalId, targetAnimalId, false, con);
    }

    private void processSuccessAttack(int attackingPlayerId, int predatorAnimalId, int targetAnimalId, Connection con){
        removeAnimal(targetAnimalId, con);
        incrementAnimalFeed(predatorAnimalId, 2, con);
        deactivateProperty(predatorAnimalId, 1, con);
        deactivatePlayer(attackingPlayerId, con);
    }
    
    public boolean validateAttackAnimal(int predatorAnimalId, int targetAnimalId, Connection con) {
        if (validateTargetAnimalNotExist(con, targetAnimalId)){
            return false;
        }
        
        if (predatorAnimalId == targetAnimalId){
            return false;
        }
        return true;
    }

    private boolean isAnglerfish(int animalId, Connection con){
        Animal animal = getAnimalById(con, animalId);
        return animal.isAnglerfish();
    }
    
    public void removeAnimal(int animalId, Connection con) {
        Animal animal = getAnimalById(con, animalId);
        if (animal.isRegen()){
            updateAnimalInRegen(animalId, 1, con);
            return;
        }
        removeLinkedProperties(animalId, con);
        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM ev_g_animal WHERE id=?;");
            statement.setInt(1, animalId);
            statement.execute();
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }        
    }
    
    public void deactivateProperty(int animalId, int propertyType, Connection connection) {
        updatePropertyActivity(animalId, propertyType, 0, connection);
    }
    
    public void activateProperty(int animalId, int propertyType, Connection connection) {
        updatePropertyActivity(animalId, propertyType, 1, connection);
    }
    
    private void updatePropertyActivity(int animalId, int propertyType, int newValue, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_animal_property set active=? WHERE animal_id=? and property_type=?;");
            statement.setInt(1, newValue);
            statement.setInt(2, animalId);
            statement.setInt(3, propertyType);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void runExtinction(int gameId, Connection con){
        killHungryAnimals(gameId, con);
        resetCurrentFeedAndUserActivity(gameId, con);
        giveNewCards(gameId, con);
        goToNextRound(gameId, con);
    }

    private void killHungryAnimals(int gameId, Connection con) {
        GameBoard game = getGameInfo(gameId, con);
        List<Player> players = game.getPlayers();
        for (Player player : players){
            for (Animal animal : player.getAnimals()){
                if (animal.getInRegen() == 1){
                    updateAnimalInRegen(animal.getId(), 0, con);
                    continue;
                }
                if (animal.getFeed() < animal.getNeededFeed()){
                    removeAnimal(animal.getId(), con);
                }
            }
        }
    }

    private void giveNewCards(int gameId, Connection con) {
        for (Player player : getGamePlayers(gameId, con)){
            
            List<Animal> animalsAlive = getPlayerAnimals(player.getId(), con);
            
            int rStrategyAnimalsCount = calculateRStrategyAnimals(animalsAlive);
        
            List<Integer> cardsLeft = getCardsLeftInTheDeck(gameId, con);
        
            List<Integer> selectedCards = setRandomCardsToPlayer(player, cardsLeft, animalsAlive.size()+rStrategyAnimalsCount+1, gameId, con);
            
            if (selectedCards.size()<=(animalsAlive.size()-rStrategyAnimalsCount+1)){
                rStrategyAnimalsCount = 0;
            }
            else if (selectedCards.size() < animalsAlive.size()+rStrategyAnimalsCount+1){
                rStrategyAnimalsCount = (selectedCards.size() - animalsAlive.size()+rStrategyAnimalsCount-1);
            }
            for (int i=0; i < rStrategyAnimalsCount; i++){
                int selectedCardId = selectedCards.get(i);
                applyCard(con, player.getId(), selectedCardId, 0, -1, -1);
            }
        }        
    }

    private void goToNextRound(int gameId, Connection con) {
        resetGameStage(gameId, con);
        changeRoundOrder(gameId, con);        
        int currentRound = getGameRound(gameId, con);
        updateGameRound(gameId, currentRound+1, con);
    }

    private List<Integer> getCardsLeftInTheDeck(int gameId, Connection con) {
        List<Integer> cardIds = new ArrayList<Integer>();
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT pc.card_id FROM ev_g_player_card AS pc WHERE pc.game_id=?;");
            getCardsStatement.setInt(1, gameId);
            ResultSet rs = getCardsStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("card_id");
                cardIds.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cardIds;
    }

    private int getGameRound(int gameId, Connection con) {
        int stage = -1;
        try {
            PreparedStatement getCardsStatement = con.prepareStatement("SELECT round FROM ev_g_game WHERE id =?;");
            getCardsStatement.setInt(1, gameId);
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                stage = rs.getInt("round");
            }
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get players count from database!", sex);
        }
        return stage;
    }

    private void updateGameRound(int gameId, int newValue, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_game set round=? WHERE id=?;");
            statement.setInt(1, newValue);
            statement.setInt(2, gameId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetGameStage(int gameId, Connection con) {
        updateGameStage(gameId, 0, con);
    }

    private void changeRoundOrder(int gameId, Connection con) {
        List<Player> players = getGamePlayers(gameId, con);
        int playersCount = players.size();
        Player firstPlayer = getFirstPlayer(players);
        setRoundOrderToPlayer(firstPlayer, firstPlayer.getRoundOrder()+playersCount, con);
        List<Player> updatedPlayers = getGamePlayers(gameId, con);
        resetStageOrder(updatedPlayers, con);
    }

    private Player getFirstPlayer(List<Player> players) {
        Player firstPlayer = null;
        int minOrder = Integer.MAX_VALUE;
        for (Player player : players){
            if (minOrder > player.getRoundOrder()){
                minOrder = player.getRoundOrder();
                firstPlayer = player;
            }
        }
        return firstPlayer;
    }

    private void resetStageOrder(List<Player> players, Connection con) {
        for (Player player : players){
           setStageOrderToPlayer(player, player.getRoundOrder(), con); 
        }        
    }

    private void resetCurrentFeedAndUserActivity(int gameId, Connection con) {
        List<Player> players = getGamePlayers(gameId, con);
        for (Player player : players){
            activatePlayer(player.getId(), con);
           for (Animal animal : player.getAnimals()){
               updateAnimalFeed(animal.getId(), 0, con);
               if (animal.getInShell() == 1){
                   moveOutFromShell(animal.getId(), con);
               }
               if (animal.getInHibernation()== 1){
                   getUpFromHibernation(animal.getId(), con);
               }
               if (animal.getFlighty() == 1){
                   makeAnimalNotFlighty(animal.getId(), con);
               }
               for (Property property : animal.getProperties()){
                    switch (property.getType()){
                       case 13:
                           if (property.getActive() == 0){
                               activateProperty(animal.getId(), property.getType(), con);
                               startRefreshing(property.getId(), con);
                           }
                           else if (property.getRefreshing()== 1){
                               markRefreshed(property.getId(), con);
                           }
                           break;
                       default:
                           if (property.getActive() == 0){
                               activateProperty(animal.getId(), property.getType(), con);
                           }
                   }                   
               }
           }
        }
    }

    private int couldBeAttacked(int gameId, int playerId, int predatorAnimalId, int targetAnimalId, Connection con) {
        Animal targetAnimal = getAnimalById(con, targetAnimalId);
        int defencies = 0;
        if (targetAnimal.isHerding() && checkHerding(gameId, con)){
            defencies++;
        }
        if (targetAnimal.getInShell() == 1){
            defencies++;
            if (defencies > 1){
                return 2;
            }
        }
        if (targetAnimal.getFlighty() == 1){
            defencies++;
            if (defencies > 1){
                return 2;
            }
        }
        if (targetAnimal.isSubSimbiont()){
            defencies++;
            if (defencies > 1){
                return 2;
            }
        }        
        if (targetAnimal.isCamouflage()){
            Animal atackingAnimal = getAnimalById(con, predatorAnimalId);
            if (!atackingAnimal.isSharpVision()){
                defencies++;
                if (defencies > 1){
                    return 2;
                }
            }            
        }
        if (targetAnimal.isBig()){
            Animal atackingAnimal = getAnimalById(con, predatorAnimalId);
            if (!atackingAnimal.isBig()){
                defencies++;
                if (defencies > 1){
                    return 2;
                }
            } 
        }        
        return defencies;
    }

    private boolean checkHerding(int gameId, Connection con) {
        List<Player> players = getGamePlayers(gameId, con);
        int carnivorousCount = 0;
        int herdingCount = 0;
        for (Player player : players){
            for (Animal animal : player.getAnimals()){
               if (animal.isPredator()){
                   carnivorousCount++;
               }
               if (animal.isHerding()){
                   herdingCount++;
               }
           }               
        }
        return herdingCount >= carnivorousCount;
    }

    private boolean hasActiveDefence(int gameId, int playerId, int predatorAnimalId, int targetAnimalId, int blockedPropertyType, Connection con) {
        Animal animal = getAnimalById(con, targetAnimalId);
        return animal.isActiveDefence(blockedPropertyType);
    }

    private void switchToDefendingChoice(int gameId, int predatorAnimalId, int targetAnimalId, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_game set at_animal_id=?,def_animal_id= WHERE id=?;");
            statement.setInt(1, predatorAnimalId);
            statement.setInt(2, targetAnimalId);
            statement.setInt(3, gameId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void switchToIntellectualChoice(int gameId, int predatorAnimalId, int targetAnimalId, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_game set int_animal_id=?,def_animal_id= WHERE id=?;");
            statement.setInt(1, predatorAnimalId);
            statement.setInt(2, targetAnimalId);
            statement.setInt(3, gameId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean defendAnimal(int gameId, int playerId, int predatorId, int defendingId, int propertyType, Connection con) {
        switch (propertyType){
            case 12:
                deactivateProperty(predatorId, 1, con);
                deactivateProperty(defendingId, 12, con);
                hideIntoShell(defendingId, con);
                break;
        }
        switchToDefendingChoice(gameId, -1, -1, con);
        checkAndApplyFlighty(defendingId, con);
        unblockProperty(defendingId, con);
        return true;
    }
    
    public void hideIntoShell(int animalId, Connection con){
        updateInShell(animalId, 1, con);
    }
    
    public void moveOutFromShell(int animalId, Connection con){
        updateInShell(animalId, 0, con);
    }
    
    private void updateInShell(int animalId, int intShell, Connection con){
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_animal set in_shell=? WHERE id=?;");
            statement.setInt(1, intShell);
            statement.setInt(2, animalId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void markRefreshed(int propertyId, Connection con) {
        updateRefreshing(propertyId, 0, con);
    }
    
    private void startRefreshing(int propertyId, Connection con) {
        updateRefreshing(propertyId, 1, con);
    }
    
    private void updateRefreshing(int propertyId, int newVlue, Connection con){
       try {
            PreparedStatement statement = con.prepareStatement("update ev_g_animal_property set refreshing=? WHERE id=?;");
            statement.setInt(1, newVlue);
            statement.setInt(2, propertyId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void toHibernation(int animalId, Connection con){
        fallIntoHibernation(animalId, con);
        deactivateProperty(animalId, 13, con);
    }
    
    public void fallIntoHibernation(int animalId, Connection con){
        updateInHibernation(animalId, 1, con);
    }
    
    public void getUpFromHibernation(int animalId, Connection con){
        updateInHibernation(animalId, 0, con);
    }
    
    private void updateInHibernation(int animalId, int intShell, Connection con){
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_animal set in_hibernation=? WHERE id=?;");
            statement.setInt(1, intShell);
            statement.setInt(2, animalId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void lossTail(int gameId, int playerId, int defendingId, int predatorId, int propertyId, Connection con){
        int attackingPlayerId = getPlayerIdByAnimalId(predatorId, con);
        deactivateProperty(predatorId, 1, con);
        incrementAnimalFeed(predatorId, 1, con);
        removeProperty(propertyId, defendingId, true, con);
        switchToDefendingChoice(gameId, -1, -1, con);
        checkAndApplyFlighty(defendingId, con);
        deactivatePlayer(attackingPlayerId, con);
    }
    
    public boolean mimicry(int gameId, int playerId, int defendingId, int mimicId, int predatorId, Connection con){
        checkAndApplyFlighty(defendingId, con);
        if (attackAnimal(gameId, playerId, predatorId, mimicId, con)){
            deactivateProperty(defendingId, 25, con);
            return true;
        }
        return false;
    }
    
    public void run(int gameId, int playerId, int defendingId, int predatorId, Connection con){
        int attackingPlayerId = getPlayerIdByAnimalId(predatorId, con);
        deactivateProperty(predatorId, 1, con);
        if (generateCubeValue() < 4){
            incrementAnimalFeed(predatorId, 2, con);
        }
        switchToDefendingChoice(gameId, -1, -1, con);
        checkAndApplyFlighty(defendingId, con);
        deactivatePlayer(attackingPlayerId, con);
    }
    
    private long generateCubeValue(){
        long random = Math.round(Math.random()*100);
        if (random < 17){
            return 1;
        }
        if (random < 33){
            return 2;
        }
        if (random < 50){
            return 3;
        }
        if (random < 67){
            return 4;
        }
        if (random < 83){
            return 5;
        }
        return 6;
    }
    
    public void removeProperty(int propertyId, int animalId, boolean removeLinked, Connection con) {
        if (removeLinked){
            Property property = getPropertyById(propertyId, con);
            removeLinkedProperties(property, animalId, con);
        }
        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM ev_g_animal_property WHERE id=?;");
            statement.setInt(1, propertyId);
            statement.execute();
        } catch (SQLException sex) {
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }
    }

    private void checkAndApplyFlighty(int animalId, Connection con) {
        Animal animal = getAnimalById(con, animalId);
        if (animal.isFlightyActive()){
            makeAnimalFlighty(animalId, con);
        }
    }

    private void makeAnimalFlighty(int animalId, Connection con) {
        updateAnimalFlighty(animalId, 1, con);
    }
    
    private void makeAnimalNotFlighty(int animalId, Connection con) {
        updateAnimalFlighty(animalId, 0, con);
    }
    
    private void updateAnimalFlighty(int animalId, int newValue, Connection con){
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_animal set flighty=? WHERE id=?;");
            statement.setInt(1, newValue);
            statement.setInt(2, animalId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeLinkedProperties(int animalId, Connection con) {
        Animal animal = getAnimalById(con, animalId);
        for (Property property : animal.getProperties()){
            removeLinkedProperties(property, animalId, con);
        }
    }
    
    private void removeLinkedProperties(Property property, int animalId, Connection con) {
        if (property.getLinkedAnimalId() > 0){
                Animal linkedAnimal = getAnimalById(con, property.getLinkedAnimalId());
                for (Property linkedProperty : linkedAnimal.getProperties()){
                    if (linkedProperty.getLinkedAnimalId() == animalId){
                        removeProperty(linkedProperty.getId(), animalId, false, con);
                    }
                }                
        }
    }

    private Property getPropertyById(int propertyId, Connection con) {
        Property property = null;
        try {
            PreparedStatement getAnimalPropertyStatement = con.prepareStatement("SELECT p.id, p.type, p.description, ap.linked_animal_id, ap.active, ap.refreshing FROM ev_g_animal_property AS ap\n"
                    + "  JOIN ev_g_property AS p ON ap.property_type=p.type\n"
                    + "WHERE ap.id=?;");
            getAnimalPropertyStatement.setInt(1, propertyId);
            ResultSet rs = getAnimalPropertyStatement.executeQuery();

            while (rs.next()) {
                property = new Property();
                int id = rs.getInt("id");
                property.setId(id);
                int type = rs.getInt("type");
                property.setType(type);
                int active = rs.getInt("active");
                property.setActive(active);
                int refreshing = rs.getInt("refreshing");
                property.setRefreshing(refreshing);
                String description = rs.getString("description");
                property.setDescription(description);
                int linkedAnimal = rs.getInt("linked_animal_id");
                property.setLinkedAnimalId(linkedAnimal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return property;
    }

    private int calculateRStrategyAnimals(List<Animal> animals) {
        if (animals == null){
            return 0;
        }
        int rStrategyAnimalsCount = 0;
        for (Animal animal: animals){
            if (animal.isRStrategyActive()){
                rStrategyAnimalsCount++;
            }
        }
        return rStrategyAnimalsCount;
    }

    private boolean validateStates(Connection connection, int animalId) {
        Animal animal = getAnimalById(connection, animalId);
        if (animal.getInShell() == 1){
            return false;
        }
        if (animal.getInHibernation()== 1){
            return false;
        }
        if (animal.isSubSimbiont() && !checkMasterSimbiont(animal, connection)){
            return false;
        }
        return true;
    }

    private boolean checkMasterSimbiont(Animal animal, Connection connection) {
        for (Property property: animal.getProperties()){
            if (property.isSub()){
                int masterSimbiontId = property.getLinkedAnimalId();  
                Animal masterSimbiontAnimal = getAnimalById(connection, masterSimbiontId);
                return !(masterSimbiontAnimal.getNeededFeed() > masterSimbiontAnimal.getFeed());
            }
        }
        return true;
    }

    private int getSupplyFromAedificators(int gameId, Connection connection) {
        int addSupply = 0; 
        GameBoard game = getGameInfo(gameId, connection);
        for (Player player : game.getPlayers()){
            for (Animal animal : player.getAnimals()){
                if (animal.isAedificator()){
                    addSupply+=2;
                }
            }
        }
        return addSupply;
    }

    private void runNeoplasm(int gameId, Connection connection) {
        GameBoard game = getGameInfo(gameId, connection);
        for (Player player : game.getPlayers()){
            for (Animal animal : player.getAnimals()){
                if (animal.isNeoplasm()){
                    for (Property property : animal.getProperties()){
                        if (property.getType() == 19 || property.isTwin() || property.getActive() == -1){
                            continue;
                        }
                        disableProperty(property.getId(), connection);
                        break;
                    }
                }
            }
        }
    }

    private void blockProperty(int id, Connection connection) {
        updatePropertyState(id, 9, connection);
    }
    
    private void disableProperty(int id, Connection connection) {
        updatePropertyState(id, -1, connection);
    }
    
    private void updatePropertyState(int id, int newValue, Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement("update ev_g_animal_property set active=? WHERE id=?;");
            statement.setInt(1, newValue);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isRegenerationApplyPossible(int animalId, Connection connection) {
        Animal animal = getAnimalById(connection, animalId);
        return (animal.getProperties().size() == 1) && (animal.getNeededFeed() == 1);
    }

    private boolean validateRegeneration(int animalId, int propertyType, Connection con) {
        Animal animal = getAnimalById(con, animalId);
        if (!animal.isRegen()){
            return false;
        }
        if (animal.getProperties().size() > 1){
            return true;
        }
        return (propertyType == 1) || (propertyType == 4) || (propertyType == 10) || (propertyType == 26) || 
                (propertyType == 31) || (propertyType == 36) || (propertyType == 41);
    }

    private void updateAnimalInRegen(int animalId, int newValue, Connection con) {
        try {
            PreparedStatement statement = con.prepareStatement("update ev_g_animal set in_regen=? WHERE id=?;");
            statement.setInt(1, newValue);
            statement.setInt(2, animalId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StartGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void feedLinkedAnimals(int mainAnimalId, int initialFoodCount, boolean initialRedFood, Connection connection) {
        Animal mainAnimal = getAnimalById(connection, mainAnimalId);
        for (Property property : mainAnimal.getProperties()){
            if (property.getType() == 21){
                int linkedAnimalId = property.getLinkedAnimalId();
                incrementAnimalFeed(linkedAnimalId, initialFoodCount, connection);
            }
        }
    }
    
    public void applyMetamorphose(int playerId, int animalId, int propertyId, Connection con){
        removeProperty(propertyId, animalId, true, con);
        incrementAnimalFeed(animalId, con);
        deactivateProperty(animalId, 24, con);
        deactivatePlayer(playerId, con);
    }

    private void convertAnglerfishToPredator(int targetAnimalId, Connection con) {
        Animal animal = getAnimalById(con, targetAnimalId);
        int anglerfishPropertyId = animal.getProperties().get(0).getId();
        removeProperty(anglerfishPropertyId, targetAnimalId, false, con);
        addProperty(con, targetAnimalId, 1, -1);
    }

    private void unblockProperty(int animalId, Connection con) {
        Animal animal = getAnimalById(con, animalId);
        
        for (Property property : animal.getProperties()){
            if (property.getActive() == 9){
                updatePropertyActivity(animalId, property.getType(), 1, con);
            }
        }
        
    }

    private void fillFatTissue(Property fatTissue, Connection connection) {        
        updatePropertyState(fatTissue.getId(), 2, connection);
    }
    
    private void cleanUpFatTissue(Property fatTissue, Connection connection) {        
        updatePropertyState(fatTissue.getId(), 1, connection);
    }    
    
    public void fatburn(int playerId, int animalId, int propertyId, Connection connection){
        incrementAnimalFeed(animalId, 1, connection);
        feedLinkedAnimals(animalId, 1, false, connection);
        cleanUpFatTissue(getPropertyById(propertyId, connection), connection);
        deactivatePlayer(playerId, connection);
    }
}
