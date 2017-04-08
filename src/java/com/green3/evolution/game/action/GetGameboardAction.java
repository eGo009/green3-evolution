/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.Action;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GetGameboardAction implements Action{
    
    @Override
    public CommonEntity execute(){
        System.out.println("Get gameboard");
        GameBoard gameboard = new GameBoard();
        gameboard.setNumber(222);
        gameboard.setCurrentRound(1);
        List<String> cardDeck = new ArrayList<String>();
        cardDeck.add("asdasd");
        cardDeck.add("gfdgdfgd");
        gameboard.setCardDeck(cardDeck);
        gameboard.setStatus(1);
        Set<String> players = new HashSet<String>();
        players.add("dew");
        players.add("ace");
        players.add("nick");
        gameboard.setPlayers(players);
        return gameboard;
    }
}
