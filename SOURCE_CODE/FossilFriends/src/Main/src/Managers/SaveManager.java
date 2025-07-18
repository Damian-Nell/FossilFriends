package Main.src.Managers;

import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class SaveManager {

    /*
        * variable list for all the sql presets.
        *   - con - used for the connection to the database.
        *   - url - where to look for the game save.
        *   - createSaves - SQL code to create a new table if none is found in the file.
        *   - updateSaves - used to update specific fields of the SQL table.
        *   - newSaves - used to create a new row in SQL table.
        *   - loadSaves - used to load all the saves from the SQL table.
        *   - deleteSaves - used to delete a specific row from the SQL table.
        *
        *   Seperate table for the settings so they are persistant throughout saves.
        *   - loadSettings - loads everything from the settings table.
        *   - saveSettings - updates the settings table.
        *   - createSettings - used to create a table for the settings.
        *   - newSettings - used to create a new row in the settings table (incase it's missing).
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
            + "BEgg BOOLEAN,"
            + "DTStart TEXT, "
            + "DTLast TEXT)";
    private String updateSaves = "UPDATE SaveGames SET LHunger = ?, LThirst = ?, LClean = ?, LAge = ?, LLonely = ?, BDeath = ?, BEgg = ?, DTLast = ? WHERE SaveNum = ?";
    private String newSaves = "INSERT INTO SaveGames (SaveNum, DName, Type, LHunger, LThirst, LClean, LAge, LLonely, BDeath, BEgg, DTStart, DTLast) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String loadSaves = "SELECT * FROM SaveGames";
    private String deleteSaves = "DELETE FROM SaveGames WHERE SaveNum = ?";

    private String loadSettings = "SELECT * FROM Settings";
    private String saveSettings = "UPDATE Settings SET Volume = ?, TutorialComplete = ?, Language = ?";
    private String createSettings = "CREATE TABLE Settings (Volume INT, TutorialComplete BOOLEAN, Language TEXT)";
    private String newSettings = "INSERT INTO Settings (Volume, TutorialComplete, Language) VALUES (?, ?, ?)";


    /*
        *constructor method where it calls the connect function.
     */
    public SaveManager() {
        DBConnect();
    }

    /*
        * connects to the database and throws an error if no file found and then closes the application when "ok" is selected.
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

    // creates a table, if there isnt a suitable table found in the .accdb file. 
    public void createTable(int tableNum) {
        try {
            if (tableNum == 0) {
                PreparedStatement ps = con.prepareStatement(createSaves);
                ps.executeUpdate();
                ps.close();
            } else if (tableNum == 1) {
                PreparedStatement ps = con.prepareStatement(createSettings);
                ps.executeUpdate();
                ps.close();
                PreparedStatement nps = con.prepareStatement(saveSettings);
                nps.setInt(1, 100);
                nps.setBoolean(2, false);
                nps.setString(3, "English");
                nps.executeUpdate();
                nps.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to create table. " + tableNum);
            MainManager.close();
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
            ps.setBoolean(7, currentDino.getEgg());
            ps.setString(8, currentDino.getLastRaw());
            ps.setInt(9, currentDino.getNo());

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
                    nps.setBoolean(10, currentDino.getEgg());
                    nps.setString(11, currentDino.getStartRaw());
                    nps.setString(12, currentDino.getLastRaw());

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

    //Deletes the selected Dino
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
                Boolean BEgg = rs.getBoolean("BEgg");
                String DTStart = rs.getString("DTStart");
                String DTLast = rs.getString("DTLast");

                allSaves[allSaves.length - 1] = new Dinosaur(saveNum, DName, Type, LHunger,
                        LThirst, LClean, LAge, LLonely, BDeath, BEgg,
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

    //loads the settings and puts them into the MainManager. If it fails that means theres no table available so it creates one.
    public void loadSettings() {
        try {
            PreparedStatement ps = con.prepareStatement(loadSettings);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String lang;
                if (rs.getString("Language").equals(null)) {
                    lang = "English";
                } else {
                    lang = rs.getString("Language");
                }
                MainManager.setSettings(rs.getInt("Volume"), rs.getBoolean("TutorialComplete"), lang);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            createTable(1);
            System.out.println("settings load failure: creating table");
        }
    }

    //Saves all the inputed settings into the table
    public void saveSettings(int vol, boolean tutComp, String lang) {
        try {
            PreparedStatement ps = con.prepareStatement(saveSettings);
            ps.setInt(1, vol);
            ps.setBoolean(2, tutComp);
            ps.setString(3, lang);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                try {
                    PreparedStatement nps = con.prepareStatement(newSettings);
                    nps.setInt(1, vol);
                    nps.setBoolean(2, false);
                    nps.setString(3, lang);
                    nps.executeUpdate();
                } catch (Exception i) {
                    System.out.println("fail to create new settings: " + i);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "save failed");

        }

    }
}
