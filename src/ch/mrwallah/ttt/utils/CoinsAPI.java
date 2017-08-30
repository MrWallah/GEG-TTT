package ch.mrwallah.ttt.utils;
import java.sql.ResultSet;

import org.bukkit.entity.Player;
 
import com.mysql.jdbc.PreparedStatement;

import ch.mrwallah.ttt.mysql.MySQL;
 
 
public class CoinsAPI {
       
        public static void createTable(){
            try{
                PreparedStatement ps = MySQL.getStatement("CREATE TABLE IF NOT EXISTS Coins (Spielername VARCHAR(100), UUID VARCHAR(100), Coins INT(100))");
                ps.executeUpdate();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static void register(Player p){
            try{
                PreparedStatement ps = MySQL.getStatement("INSERT INTO Coins (Spielername, UUID, Coins) VALUES (?, ?, ?)");
                ps.setString(1, p.getName());
                ps.setString(2, p.getUniqueId().toString());
                ps.setInt(3, 0);
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static boolean isRegistered(Player p){
            try{
                PreparedStatement ps = MySQL.getStatement("SELECT * FROM Coins WHERE UUID= ?");
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                boolean user = rs.next();
                rs.close();
                rs.close();
                return user;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return false;
        }
       
        public static boolean isRegistered(String name){
            try{
                PreparedStatement ps = MySQL.getStatement("SELECT * FROM Coins WHERE Spielername= ?");
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                boolean user = rs.next();
                rs.close();
                rs.close();
                return user;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return false;
        }
 
        public static int getCoins(Player p){
            try{
                PreparedStatement ps = MySQL.getStatement("SELECT * FROM Coins WHERE UUID= ?");
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                int coins = rs.getInt("Coins");
                rs.close();
                ps.close();
                return coins;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return -1;
        }
 
        public static void setCoins(Player p, int coins){
            try{
                PreparedStatement ps = MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE UUID= ?");
                ps.setInt(1, coins);
                ps.setString(2, p.getUniqueId().toString());
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static void addCoins(Player p, int coins){
            try{
                PreparedStatement ps = MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE UUID= ?");
                ps.setInt(1, getCoins(p) + coins);
                ps.setString(2, p.getUniqueId().toString());
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static void removeCoins(Player p, int coins){
            try{
                PreparedStatement ps = MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE UUID= ?");
                ps.setInt(1, getCoins(p) - coins);
                ps.setString(2, p.getUniqueId().toString());
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static int getCoins(String name){
            try{
                PreparedStatement ps = MySQL.getStatement("SELECT * FROM Coins WHERE Spielername= ?");
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                rs.next();
                int coins = rs.getInt("Coins");
                rs.close();
                ps.close();
                return coins;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return -1;
        }
 
        public static void setCoins(String name, int coins){
            try{
                PreparedStatement ps = MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE Spielername= ?");
                ps.setInt(1, coins);
                ps.setString(2, name);
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static void addCoins(String name, int coins){
            try{
                PreparedStatement ps = MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE Spielername= ?");
                ps.setInt(1, getCoins(name) + coins);
                ps.setString(2, name);
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
 
        public static void removeCoins(String name, int coins){
            try{
                PreparedStatement ps = MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE Spielername= ?");
                ps.setInt(1, getCoins(name) - coins);
                ps.setString(2, name);
                ps.executeUpdate();
                ps.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
       
    }