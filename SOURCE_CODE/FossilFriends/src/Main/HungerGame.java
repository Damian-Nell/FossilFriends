package Main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class HungerGame extends javax.swing.JFrame {

    /*
        * all the variables needed for the hunger minigame.
        *   - gameTimer, frameTime, frameCounter - same as the loginPage.
        *   - currentDino to effect only the needed dino.
        *   - score - to keep track of how many apples you have caught
        *   - aApple - to keep track of all the apples fallen (including missed and caught ones) to limit the number of apples you can catch to a set num
        *   - timeTillNext - the time taken for the next apple to spawn.
        *   - maxApples - determines how many apples will spawn.
        *   - speed - how fast the apples fall per frame.
        *   - prevX - used to store the previous x position, to determine which direction the Dino is moving
        *   - Apples - array containing all the apples
        *   - Player - the image you will control.
     */
    private Timer gameTimer;
    private Dinosaur currentDino;

    private int frameTime = 10;
    private int frameCount = 0;
    private int score = 0;
    private int aApple = 0;
    private int timeTillNext = 70;
    private int maxApples = 30;
    private int speed = 3;
    private int prevX;

    private JLabel[] Apples = new JLabel[0];
    private JLabel Player = new JLabel("");

    //initialises UI components.
    public HungerGame() {
        initComponents();
    }

    /*
        * Initialises the game by getting the currentDino
        * makes your mouse invisible
        * sets the player JLabel to a match your currentDino
        * starts the gameTimer.
     */
    public void initGame() {
        currentDino = MainManager.getDino();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(new byte[0]);
        Cursor invisibleCursor = toolkit.createCustomCursor(image, new Point(0, 0), "Invisible");
        this.setCursor(invisibleCursor);

        Player.setSize(150, 150);
        if (currentDino.getType() == 1) {
            Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/BrontoPic150.png")));
        } else if (currentDino.getType() == 2) {
            Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/RaptorPic150.png")));
        } else {
            Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/StegoPic150.png")));
        }
        backPanel.add(Player);

        gameTimer = new Timer(frameTime, e -> updateGame());
        gameTimer.start();
    }

    /*
        * will carry on running if the counter all apples is below 30
        *   - will set the Player to the same x location as your mouse
        *   - will update all the apples
        *   - sets the score text and current hunger bar to corressponding values
        *   - updates the dino to the direction it is moving (if not moving then keeps its same direction)
        * if all apples have reached above 30 then it will:
        *   - stop the timer and save the game
        *   - remove the player and apples JLabels to save memory
        *   - set the hCoolDown of the currentDino.
        *   - save the game and go back to the main area and dispose of this frame.
     */
    private void updateGame() {
        if (aApple < maxApples) {
            Point mousePos = MouseInfo.getPointerInfo().getLocation();
            Point frameLocation = this.getLocationOnScreen();
            int x = mousePos.x - frameLocation.x;
            Player.setLocation(x - (Player.getWidth() / 2), this.getHeight() - 175);

            updateApples();
            frameCount++;
            scoreLabel.setText(score + "/" + maxApples);
            currentHungerBar.setValue(currentDino.getHunger());
            currentDino.updateStats(0.01, true);

            if (prevX < Player.getLocation().x) {
                if (currentDino.getType() == 1) {
                    Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/BrontoPic150.png")));
                } else if (currentDino.getType() == 2) {
                    Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/RaptorPic150.png")));
                } else {
                    Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/StegoPic150.png")));
                }
            }else if (prevX > Player.getLocation().x){
                if (currentDino.getType() == 1) {
                    Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/leftBrontoPic150.png")));
                } else if (currentDino.getType() == 2) {
                    Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/leftRaptorPic150.png")));
                } else {
                    Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/leftStegoPic150.png")));
                }
            }
            
            prevX = Player.getLocation().x;
        } else {
            gameTimer.stop();
            backPanel.remove(Player);
            Player = null;
            for (int i = 0; i < Apples.length; i++) {
                backPanel.remove(Apples[i]);
                Apples[i] = null;
            }

            currentDino.setHCool(60 * 100);
            MainManager.setDino(currentDino);

            MainManager.SaveGame();
            MainManager.selectPage(1, this.getLocation().x, this.getLocation().y);

            this.dispose();
        }
    }

    /*
        * checks if there are less than 30 apples, 
        *   if there is then it will at a new apple in a random position.
        * and then it will move all the apples down 3 pixels.
        * it will detect once an apple goes offscreen or collides with the player and will update aApples and score accoringly
        * and send the apples somewhere where they wont bother us and remove them to free up memory and cpu.
        
     */
    private void updateApples() {
        if (Apples.length < maxApples) {
            if (frameCount > timeTillNext) {
                JLabel[] tempApples = Apples;
                Apples = new JLabel[tempApples.length + 1];

                for (int i = 0; i < tempApples.length; i++) {
                    Apples[i] = tempApples[i];
                }

                JLabel newApple = new JLabel();
                newApple.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/Apple25.png")));
                newApple.setLocation((int) (Math.random() * (this.getWidth() - 20)) + 1, 20);
                newApple.setSize(25, 25);
                Apples[Apples.length - 1] = newApple;
                backPanel.add(newApple);

                this.revalidate();
                this.repaint();

                frameCount = 0;
                timeTillNext = ((int) (Math.random() * 100) + 20);
            }
        }

        for (int i = 0; i < Apples.length; i++) {
            Apples[i].setLocation(Apples[i].getLocation().x, Apples[i].getLocation().y + speed);

            if (Apples[i].getBounds().intersects(Player.getBounds())) {
                MainManager.playSound("res/sounds/eat.wav");
                score++;
                aApple++;
                Apples[i].setLocation(this.getWidth() + 100, this.getHeight() - 1000000);
                backPanel.remove(Apples[i]);
                if (currentDino.getHunger() < 100) {
                    currentDino.setHunger(currentDino.getHunger() + 1);
                }
            }

            if (Apples[i].getLocation().y > this.getHeight()) {
                Apples[i].setLocation(this.getWidth() + 100, this.getHeight() - 1000000);
                Apples[i].setText("");
                aApple++;
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        backPanel = new javax.swing.JPanel();
        scoreLabel = new javax.swing.JLabel();
        currentHungerBar = new javax.swing.JProgressBar();
        hungerLabel = new javax.swing.JLabel();
        scoreLabel2 = new javax.swing.JLabel();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backPanel.setBackground(new java.awt.Color(217, 203, 179));
        backPanel.setForeground(new java.awt.Color(217, 203, 179));

        scoreLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 36)); // NOI18N
        scoreLabel.setForeground(new java.awt.Color(74, 95, 51));
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("10");

        hungerLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        hungerLabel.setForeground(new java.awt.Color(74, 95, 51));
        hungerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hungerLabel.setText("Hunger Level:");

        scoreLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        scoreLabel2.setForeground(new java.awt.Color(74, 95, 51));
        scoreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel2.setText("Score:");

        javax.swing.GroupLayout backPanelLayout = new javax.swing.GroupLayout(backPanel);
        backPanel.setLayout(backPanelLayout);
        backPanelLayout.setHorizontalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backPanelLayout.createSequentialGroup()
                        .addComponent(hungerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scoreLabel2))
                    .addGroup(backPanelLayout.createSequentialGroup()
                        .addComponent(currentHungerBar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 144, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreLabel)
                .addContainerGap())
        );
        backPanelLayout.setVerticalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backPanelLayout.createSequentialGroup()
                        .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scoreLabel2)
                            .addComponent(hungerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentHungerBar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scoreLabel))
                .addContainerGap(543, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HungerGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HungerGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HungerGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HungerGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HungerGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPanel;
    private javax.swing.JProgressBar currentHungerBar;
    private javax.swing.JLabel hungerLabel;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel scoreLabel2;
    // End of variables declaration//GEN-END:variables
}
