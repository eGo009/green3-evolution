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
import com.green3.evolution.game.action.GameActionType;
import com.green3.evolution.game.action.MetamorphoseAction;
import com.green3.evolution.model.CommonEntity;
import java.util.Map;


/**
 *
 * @author Alex_Ihnatsiuck
 */
public class MetamorphoseServlet extends GameServlet {

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
        Action action = new MetamorphoseAction();

        String playerIdParam = request.getParameter(GameConstants.PARAM_PLAYER_ID);
        String propertyId = request.getParameter(GameConstants.PARAM_PROPERTY_ID);
        String animalIdParam = request.getParameter(GameConstants.PARAM_ANIMAL_ID);
       
        
        Map<String,Object> params = createParamsMap(request, operationType, playerIdParam, animalIdParam, propertyId);
        CommonEntity error = action.execute(params);
        if (error != null){
            response.sendRedirect("/green3-evolution/game?error="+((Error)error).getCode());
            return;
        }
        response.sendRedirect("/green3-evolution/game");
    }
    
    protected Map<String, Object> createParamsMap(HttpServletRequest request, GameActionType operationType, String playerIdParam, String animalIdParam, String propertyId) {
        Map<String, Object> params = super.createParamsMap(request, operationType);
        
        params.put(GameConstants.PARAM_PLAYER_ID, playerIdParam);
        params.put(GameConstants.PARAM_ANIMAL_ID, animalIdParam);
        params.put(GameConstants.PARAM_PROPERTY_ID, propertyId);
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
