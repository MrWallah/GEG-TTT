package ch.mrwallah.ttt.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import ch.mrwallah.ttt.utils.Data;
 
 
public class MySQL {
   
    public static String host = "localhost";
    public static String port = "3306";
    public static String database = "CoinsAPI";
    public static String username = "Coins";
    public static String password = "pcIh997#";
   
    public static Connection con;
   
    public static boolean isConnected(){
        return con != null;
    }
   
    public static void connect(){
        if(!isConnected()){
            try {
                con = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                Bukkit.getConsoleSender().sendMessage(Data.Coinsprefix + "§aEs konnte erfolgreich mit der Datenbank verbunden werden");
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(Data.Coinsprefix + "§cEs konnte nicht mit der Datenbank verbunden werden");
            }
        }
    }
   
    public static void disconnect(){
        try {
            con.close();
            Bukkit.getConsoleSender().sendMessage(Data.Coinsprefix + "§aDie Verbindung zur Datenbank konnte erfolgreich geschlossen werden");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Data.Coinsprefix + "§cDie Verbindung zur Datenbank konnte nicht geschlossen werden");
        }
    }
 
    public static PreparedStatement getStatement(String sql){
        if(isConnected()){
            PreparedStatement ps;
            try {
                ps = (PreparedStatement) con.prepareStatement(sql);
                return ps;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
   
    public static ResultSet getResult(String sql){
        if(isConnected()){
            PreparedStatement ps;
            ResultSet rs;
            try {
                ps = getStatement(sql);
                rs = ps.executeQuery();
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }  
}