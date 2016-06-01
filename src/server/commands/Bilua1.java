/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleJob;
import client.SkillFactory;
import constants.ServerConstants;
import handling.channel.ChannelServer;
import handling.world.World;
import server.MaplePortal;
import server.maps.MapleMap;

/**
 *
 * @author Administrator
 */
class Bilua1 {
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.BILUA1;
    }
      
}
