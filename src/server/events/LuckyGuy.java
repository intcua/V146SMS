/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.apache.mina.common.IoSession
 */
package server.events;

import client.MapleCharacter;
import client.MapleClient;
import database.DatabaseConnection;
import handling.channel.ChannelServer;
import handling.channel.PlayerStorage;
import handling.world.World;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.mina.common.IoSession;
import server.Randomizer;
import tools.MapleAESOFB;
import tools.MockIOSession;
import tools.packet.CWvsContext;

public class LuckyGuy {
    private int status = 0;
    private MapleCharacter winner = null;
    private List<Integer> idplayer = new LinkedList<Integer>();

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int s) {
        this.status = s;
    }

    public MapleCharacter getWinner() {
        return this.winner;
    }

    public void setWinner(MapleCharacter m) throws SQLException {
        this.winner = m;
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE luckyguy set winner = ? where playerid = ?");
        ps.setInt(1, 1);
        ps.setInt(2, m.getId());
        ps.executeUpdate();
    }

    public List<Integer> getPlayers() {
        return this.idplayer;
    }

    public void setPlayers(List<Integer> l) {
        this.idplayer = l;
    }

    public void loadPlayers() throws SQLException {
        this.idplayer = new LinkedList<Integer>();
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM luckyguy");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            this.idplayer.add(rs.getInt("playerid"));
        }
        ps.close();
        rs.close();
    }

    public void out(MapleCharacter a) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE from luckyguy WHERE playerid = ?");
        ps.setInt(1, a.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void deleteWinner() {
        this.winner = null;
    }

    public void Join(MapleCharacter a) throws SQLException {
        MapleClient c = new MapleClient(null, null, (IoSession)new MockIOSession());
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO luckyguy VALUES (DEFAULT,?,0)");
        ps.setInt(1, a.getId());
        ps.executeUpdate();
        ps.close();
    }

    public boolean alreadyJoin(MapleCharacter a) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        boolean result = false;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM luckyguy WHERE playerid = ?");
        ps.setInt(1, a.getId());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result = true;
        }
        ps.close();
        return result;
    }

    public void deleteAll() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE from luckyguy");
        ps.executeUpdate();
        this.status = 0;
        this.idplayer = new LinkedList<Integer>();
    }

    public void loadWinner() throws SQLException {
        MapleClient c = new MapleClient(null, null, (IoSession)new MockIOSession());
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * from luckyguy WHERE winner = 1");
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            ps.close();
            rs.close();
            throw new RuntimeException("Loading the Char Failed (char not found)");
        }
        this.winner = MapleCharacter.loadCharFromDB(rs.getInt("playerid"), c, false);
    }

    public void reward() throws SQLException {
        int a = this.idplayer.size() == 1 ? 4000 : this.idplayer.size() * 50 * 80;
            a = a / 100;
        if (this.idplayer.isEmpty()) {
            World.Broadcast.broadcastMessage(CWvsContext.broadcastMsg(6, "No one join Lucky Guy today, so sad."));
        } else {
            Connection con = DatabaseConnection.getConnection();
            MapleClient c = new MapleClient(null, null, (IoSession)new MockIOSession());
            PreparedStatement ps = con.prepareStatement("SELECT * from luckyguy WHERE winner = 1");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                throw new RuntimeException("Loading the Char Failed (char not found)");
            }
            int id = rs.getInt("playerid");
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterById(id);
            if (victim == null) {
                MapleCharacter aa = MapleCharacter.loadCharFromDB(id, c, false);
                int ab = aa.getDPoints() + a;
                ps = con.prepareStatement("UPDATE accounts set dpoints = ? where id = ?");
                ps.setInt(1, ab);
                ps.setInt(2, aa.getAccountID());
                ps.executeUpdate();
            } else {
                victim.setDPoints(victim.getDPoints() + a);
            }
            World.Broadcast.broadcastMessage(CWvsContext.broadcastMsg(6, "Pass " + (this.idplayer.size() - 1) + " other players and win " + a + " Point, The Lucky Guy today is " + this.winner.getName() + ". Congratulation !"));
        }
    }

    public boolean haveWinner() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * from luckyguy WHERE winner = 1");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public void chooseWinner() throws SQLException {
        MapleClient c = new MapleClient(null, null, (IoSession)new MockIOSession());
        if (this.idplayer.isEmpty()) {
            System.out.println("None of player =))");
        } else {
            int a = Randomizer.nextInt(this.idplayer.size());
            this.setWinner(MapleCharacter.loadCharFromDB(this.idplayer.get(a), c, false));
            this.status = 0;
        }
    }
}

