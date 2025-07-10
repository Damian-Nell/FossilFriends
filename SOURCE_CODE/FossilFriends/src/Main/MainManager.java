package Main;

public class MainManager {

    //creates a SaveManager and Dinosaur object at the beginning for the rest of the project to refer to when wanting to save/load.
    public static SaveManager SM = new SaveManager();
    public static Dinosaur currentDino;

    
    //main method runs when the project is opened. then calls the method "selectPage" with everything at 0.
    public static void main(String[] args) {
        selectPage(0, 0, 0);
    }
    
    /*
        *method called to save the game. 
        *creates a new thread so that the game doesn't wait until saving is done to create the next frame.
    */
    public static void SaveGame(){
        new Thread(() -> {
        SM.saveGame(currentDino);
        }).start();
    }
    
   
    //used to set the currentDino from other classes.
    public static void setDino(Dinosaur Dino){
        currentDino = Dino;
    }
    
    //used to get the currentDino from other classes.
    public static Dinosaur getDino(){
        return currentDino;
    }
    
    
    //used to delete the current dino from other classes.
    public static void deleteDino(){
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
    
    //closes the whole game
    public static void close(){
        System.exit(1);
    }
}
