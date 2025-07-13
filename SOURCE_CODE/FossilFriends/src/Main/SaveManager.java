package Main;

import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class SaveManager {

    /*
        * variable list for all the sql presets.
        *   - con - used for the connection to the database.
        *   - url - where to look for the game save.
        *   - createSQL - SQL code to create a new table if none is found in the file.
        *   - updateSQL - used to update specific fields of the SQL table.
        *   - newSQL - used to create a new row in SQL table.
        *   - loadSQL - used to load all the saves from the SQL table.
        *   - deleteSQL - used to delete a specific row from the SQL table.
     */
    private Connection con;
    private String url = "jdbc:ucanaccess://FossilFriendsSaves.accdb";
    private String createSaves = "CREATE TABLE saveGames ("
            + "SaveNum INT PRIMARY KEY, "
            + "DName TEXT, "
            + "Type INT, "
            + "LHunger INT, "
            + "LThirst INT, "
            + "LClean INT, "
            + "LAge INT, "
            + "LLonely INT, "
            + "BDeath BOOLEAN, "
            + "DTStart TEXT, "
            + "DTLast TEXT)";
    private String updateSaves = "UPDATE SaveGames SET LHunger = ?, LThirst = ?, LClean = ?, LAge = ?, LLonely = ?, BDeath = ?, DTLast = ? WHERE SaveNum = ?";
    private String newSaves = "INSERT INTO SaveGames (SaveNum, DName, Type, LHunger, LThirst, LClean, LAge, LLonely, BDeath, DTStart, DTLast) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String loadSaves = "SELECT * FROM SaveGames";
    private String deleteSaves = "DELETE FROM SaveGames WHERE SaveNum = ?";

    private String loadSettings = "SELECT * FROM Settings";
    private String saveSettings = "UPDATE Settings SET Volume = ?, TutorialComplete = ?";
    private String createSettings = "CREATE TABLE Settings (Volume INT, TutorialComplete BOOLEAN)";
    private String newSettings = "INSERT INTO Settings (Volume, TutorialComplete) VALUES (?, ?)";


    /*
        *constructor method where it connects the database and will through and error is there is none.
     */
    public SaveManager() {
        try {
            DBConnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection to save file error.");
        }
    }

    /*
        * connects the database and throws and error if no file found. and then closes the application when "ok" is selected.
        * message directs you to the read me file where it will show how to create the file.
     */
    public void DBConnect() {
        try {
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No save file detected. Please Refer to the READ ME.");
            MainManager.close();
        }
    }

    /*
        * creates a table, if there isnt a suitable table found in the .accdb file. 
     */
    public void createTable(int tableNum) {
        try {
            if (tableNum == 0) {
                Statement st = con.createStatement();
                st.executeUpdate(createSaves);
                st.close();
            } else if (tableNum == 1){
                Statement st = con.createStatement();
                st.executeUpdate(createSettings);
                st.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to create table.");
        }

    }

    //Saves the currentDino, if no rows are effected that means the dino doesnt exist and so it creates a new save.
    public void saveGame(Dinosaur currentDino) {
        try {
            PreparedStatement ps = con.prepareStatement(updateSaves);

            ps.setInt(1, currentDino.getHunger());
            ps.setInt(2, currentDino.getThirst());
            ps.setInt(3, currentDino.getClean());
            ps.setInt(4, currentDino.getAge());
            ps.setInt(5, currentDino.getLonely());
            ps.setBoolean(6, currentDino.getDeath());
            ps.setString(7, currentDino.getLastRaw());
            ps.setInt(8, currentDino.getNo());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                try {
                    PreparedStatement nps = con.prepareStatement(newSaves);

                    nps.setInt(1, currentDino.getNo());
                    nps.setString(2, currentDino.getName());
                    nps.setInt(3, currentDino.getType());
                    nps.setInt(4, currentDino.getHunger());
                    nps.setInt(5, currentDino.getThirst());
                    nps.setInt(6, currentDino.getClean());
                    nps.setInt(7, currentDino.getAge());
                    nps.setInt(8, currentDino.getLonely());
                    nps.setBoolean(9, currentDino.getDeath());
                    nps.setString(10, currentDino.getStartRaw());
                    nps.setString(11, currentDino.getLastRaw());

                    nps.executeUpdate();
                } catch (Exception i) {
                    System.out.println("new save game failed: " + i);
                }
            }

            ps.close();
        } catch (Exception e) {
            System.out.println("save game failed: " + e);
        }
    }

    //Deletes the currentDino
    public void deleteSave(Dinosaur dino) {
        try {
            PreparedStatement ps = con.prepareStatement(deleteSaves);
            ps.setInt(1, dino.getNo());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, dino.getName() + " deleted.");
            } else {
                System.out.println("No save with SaveNum " + dino.getNo() + " found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete error.");
        }
    }

    //loads an array of all the detected dinosaurs in the save game.
    public Dinosaur[] loadGames() {
        Dinosaur[] allSaves = new Dinosaur[0];
        try {
            PreparedStatement ps = con.prepareStatement(loadSaves);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Dinosaur[] tempSaves = allSaves;
                allSaves = new Dinosaur[tempSaves.length + 1];

                for (int i = 0; i < tempSaves.length; i++) {
                    allSaves[i] = tempSaves[i];
                }

                int saveNum = rs.getInt("SaveNum");
                String DName = rs.getString("DName");
                int Type = rs.getInt("Type");
                int LHunger = rs.getInt("LHunger");
                int LThirst = rs.getInt("LThirst");
                int LClean = rs.getInt("LClean");
                int LAge = rs.getInt("LAge");
                int LLonely = rs.getInt("LLonely");
                Boolean BDeath = rs.getBoolean("BDeath");
                String DTStart = rs.getString("DTStart");
                String DTLast = rs.getString("DTLast");

                allSaves[allSaves.length - 1] = new Dinosaur(saveNum, DName, Type, LHunger,
                        LThirst, LClean, LAge, LLonely, BDeath,
                        LocalDateTime.parse(DTStart), LocalDateTime.parse(DTLast));
            }

            ps.close();
            rs.close();
        } catch (Exception e) {
            createTable(0);
            allSaves = loadGames();
            System.out.println("fail load: Creating new table!");
        }
        return allSaves;
    }

    public void loadSettings() {
        try {
            PreparedStatement ps = con.prepareStatement(loadSettings);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MainManager.setSettings(rs.getInt("Volume"), rs.getBoolean("TutorialComplete"));
            }

        } catch (Exception e) {
            createTable(1);
            System.out.println("settings load failure: creating table");
        }
    }

    public void saveSettings(int vol, boolean tutComp) {
        try {
            PreparedStatement ps = con.prepareStatement(saveSettings);
            ps.setInt(1, vol);
            ps.setBoolean(2, tutComp);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                try{
                    PreparedStatement nps = con.prepareStatement(newSettings);
                    nps.setInt(1, vol);
                    nps.setBoolean(2, false);
                    nps.executeUpdate();
                }catch(Exception i){
                    System.out.println("fail to create new settings: " + i);
                }
            }
        } catch (Exception e) {
            System.out.println("save failed");

        }

    }
}
