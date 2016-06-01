/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import constants.ServerConstants;
import tools.StringUtil;
import tools.packet.CField;

/**
 *
 * @author Administrator
 */
class Bilua4 {
 public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.BILUA4;
    }    
 
}
