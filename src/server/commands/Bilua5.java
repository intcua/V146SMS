/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

import client.MapleClient;
import client.Skill;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import constants.GameConstants;
import constants.ServerConstants;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.ItemInformation;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.quest.MapleQuest;
import tools.HexTool;
import tools.Pair;
import tools.StringUtil;
import tools.packet.CField;

/**
 *
 * @author Administrator
 */
class Bilua5 {
     public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.BILUA5;
    }  
     
   
}
