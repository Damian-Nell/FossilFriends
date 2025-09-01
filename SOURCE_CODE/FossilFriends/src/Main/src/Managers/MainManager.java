package Main.src.Managers;

import Main.src.UI.Game.CleanGame;
import Main.src.UI.Game.GameScreen;
import Main.src.UI.Game.HungerGame;
import Main.src.UI.Game.LoginPage;
import Main.src.UI.Popups.SettingsPopup;
import Main.src.UI.Game.ThirstGame;
import Main.src.UI.Popups.tutorialPopup;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainManager {

    //Creates a SaveManager, SoundManager, Dinosaur, and Settings object at startup for the rest of the project to refer to when wanting to perform specific actions.
    public static SaveManager SM = new SaveManager();
    private static SoundManager soundM = new SoundManager();
    private static Dinosaur currentDino;
    private static Settings settings;

    //this is what is ran when the program is openened. it initialises your devices dimensions and opens the loging screen in the correct position and correct values.
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;

        SM.loadSettings();
        selectPage(0, screenWidth / 2 - 400, 0);
    }

    //plays a certain sound using the sound manager at correct volume
    public static void playSound(String sound) {
        float tempVol = (float) settings.getVol() / 100;
        soundM.play(sound, tempVol);
    }

    //used to set the settings from other classes.
    public static void setSettings(Settings sets) {
        settings = sets;
        SM.saveSettings(settings);
    }

    //used when another class wants to see the current settings.
    public static Settings getSettings() {
        return settings;
    }

    /*
        * method called to save the game. 
        * creates a new thread so that the game doesn't wait until saving is done to create the next frame, reducing stutters.
     */
    public static void SaveGame() {
        new Thread(() -> {
            SM.saveGame(currentDino);
        }).start();
    }

    //used to set the currentDino from other classes.
    public static void setDino(Dinosaur dino) {
        currentDino = dino;
    }

    //used when another class wants to see the stats of the currentDino.
    public static Dinosaur getDino() {
        return currentDino;
    }

    //used to delete the current dino from other classes.
    public static void deleteDino() {
        SM.deleteSave(currentDino);
    }

    /*
        * selectPage method. Takes in:
        *   - pageNum   - to determine which page to switch to
        *   - x & y     - where the next form will load in (keeping the look uniform)
        * it checks the pageNum and loads the page, then sets the location and make the form visible, 
        * and calls the respective initGame.
     */
    public static void selectPage(int pageNum, int x, int y) {
        if (pageNum == 0) {
            LoginPage currentFrame = new LoginPage();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);

        } else if (pageNum == 1) {

            GameScreen currentFrame = new GameScreen();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);
            currentFrame.initGame();
        } else if (pageNum == 2) {
            HungerGame currentFrame = new HungerGame();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);
            currentFrame.initGame();
        } else if (pageNum == 3) {
            ThirstGame currentFrame = new ThirstGame();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);
            currentFrame.initGame();
        } else if (pageNum == 4) {
            CleanGame currentFrame = new CleanGame();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);
            currentFrame.initGame();
        }
    }

    /*
        * openPopup method. Takes in:
        *   - popNum   - to determine which popup to switch to
        *   - x & y     - where the next form will load in (keeping the look uniform)
        * it checks the popNum and loads the page, then sets the location and make the form visible, 
        * and calls the respective initPop.
     */
    public static void openPopup(int popNum, int x, int y) {
        if (popNum == 0) {
            SettingsPopup currentPop = new SettingsPopup();
            currentPop.setLocation(x + 50, y + 300);
            currentPop.setVisible(true);
            currentPop.initPop();
        } else if (popNum == 1) {
            if (settings.getTut() == false) {
                tutorialPopup currentPop = new tutorialPopup();
                currentPop.setLocation(x - 50, y + 250);
                currentPop.setVisible(true);
                currentPop.initPop(x - 50, y + 200);
            } else {
                tutorialPopup currentPop = new tutorialPopup();
                currentPop.setLocation(x - 100, y - 100);
                currentPop.setVisible(true);
                currentPop.initPop(x - 100, y - 100);
            }
        }
    }

    //closes the whole game
    public static void close() {
        System.exit(1);
    }
}
