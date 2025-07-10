/*
    *imports needed files
*/
package Main;

public class MainManager {

    /*
        *creates a SaveManager at the beginning for the rest of the project to refer to when wanting to save/load
    */
    public static SaveManager SM = new SaveManager();
    public static Dinosaur currentDino;

    /*
        *main method runs when the project is opened. then calls the method "selectPage" with everything at 0
    */
    public static void main(String[] args) {
        selectPage(0, 0, 0);
    }
    
    public static void SaveGame(){
        new Thread(() -> {
        SM.saveGame(currentDino);
        }).start();
    }
    
    public static void setDino(Dinosaur Dino){
        currentDino = Dino;
    }
    
    public static Dinosaur getDino(){
        return currentDino;
    }
    
    public static void deleteDino(){
        SM.deleteSave(currentDino);
    }

    /*
        * selectPage method. Takes in:
        *   - pageNum   - to determine which page to switch to
        *   - save      - represents which saveNum to use and individual pages load the rest of the Dinosaur
        *   - x & y     - where the next form will load in (keeping the look uniform)
        *   - hCoolDown, tCoolDown, cCoolDown   -   different cooldown stats to track how long till the next respective minigame
        * it checks the pageNum and loads the page, then sets the location and make the form visible, and calls the respective initGame with required field
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
        } else if(pageNum == 3){
            ThirstGame currentFrame = new ThirstGame();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);
            currentFrame.initGame();
        } else if(pageNum == 4){
            CleanGame currentFrame = new CleanGame();
            currentFrame.setLocation(x, y);
            currentFrame.setVisible(true);
            currentFrame.initGame();
        }
    }
    
    /*
        *closes the whole game
    */
    public static void close(){
        System.exit(1);
    }
}
