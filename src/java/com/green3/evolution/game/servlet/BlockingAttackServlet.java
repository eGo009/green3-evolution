/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.servlet;

import com.green3.evolution.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green3.evolution.game.model.Error;
import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.action.AttackAction;
import com.green3.evolution.game.action.GameActionType;
import com.green3.evolution.model.CommonEntity;
import java.util.Map;


/**
 *
 * @author Alex_Ihnatsiuck
 */
public class BlockingAttackServlet extends GameServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameActionType operationType = GameActionType.JOIN_GAME;
        Action action = new AttackAction();

        String playerIdParam = request.getParameter(GameConstants.PARAM_PLAYER_ID);
        String animalIdParam = request.getParameter(GameConstants.PARAM_ANIMAL_ID);
        String propertyIdParam = request.getParameter(GameConstants.PARAM_PROPERTY_ID);
        if (animalIdParam == null || "".equals(animalIdParam)){
            animalIdParam = "-1";
        }
        String linkedAnimalIdParam = request.getParameter(GameConstants.PARAM_LINKED_ANIMAL_ID);
        if (linkedAnimalIdParam == null || "".equals(linkedAnimalIdParam)){
            linkedAnimalIdParam = "-1";
        }
        
        
        Map<String,Object> params = createParamsMap(request, operationType, playerIdParam, animalIdParam, linkedAnimalIdParam, propertyIdParam);
        CommonEntity error = action.execute(params);
        if (error != null){
            response.sendRedirect("/green3-evolution/game?error="+((Error)error).getCode());
            return;
        }
        response.sendRedirect("/green3-evolution/game");
    }
    
    protected Map<String, Object> createParamsMap(HttpServletRequest request, GameActionType operationType, String playerIdParam, String animalIdParam, String linkedAnimalIdParam, String propertyIdParam) {
        Map<String, Object> params = super.createParamsMap(request, operationType);
        
        params.put(GameConstants.PARAM_PLAYER_ID, playerIdParam);
        params.put(GameConstants.PARAM_ANIMAL_ID, animalIdParam);
        params.put(GameConstants.PARAM_LINKED_ANIMAL_ID, linkedAnimalIdParam);
        params.put(GameConstants.PARAM_PROPERTY_ID, propertyIdParam);
        return params;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
