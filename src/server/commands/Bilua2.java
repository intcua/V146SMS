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
import java.util.Arrays;
import java.util.List;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.OverrideMonsterStats;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;

/**
 *
 * @author Administrator
 */
class Bilua2 {
   public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.BILUA2;
    } 

}
