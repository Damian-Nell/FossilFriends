package Main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class ThirstGame extends javax.swing.JFrame {

    private Timer gameTimer;
    private Dinosaur currentDino;

    private int frameTime = 10;
    private int dropFrameCount = 0;
    private int score;
    private int aDrops;
    private int timeTillNext;
    private int playerFrameCount = 0;

    private boolean dir = true;

    private JLabel bottle = new JLabel("");
    private JLabel Player = new JLabel("");
    private JLabel[] Drops = new JLabel[0];

    public ThirstGame() {
        initComponents();
    }

    public void initGame() {
        currentDino = MainManager.getDino();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(new byte[0]);
        Cursor invisibleCursor = toolkit.createCustomCursor(image, new Point(0, 0), "Invisible");
        this.setCursor(invisibleCursor);

        bottle.setSize(100, 100);
        bottle.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/waterBottle100.png")));
        Player.setSize(150, 150);
        if (currentDino.getType() == 1) {
            Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/BrontoPic150.png")));
        } else if (currentDino.getType() == 2) {
            Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/RaptorPic150.png")));
        } else {
            Player.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/StegoPic150.png")));
        }

        backPanel.add(Player);
        backPanel.add(bottle);

        gameTimer = new Timer(frameTime, e -> updateGame());
        gameTimer.start();
    }

    private void updateGame() {
        if (aDrops < 35) {
            Point mousePos = MouseInfo.getPointerInfo().getLocation();
            Point frameLocation = this.getLocationOnScreen();
            int x = mousePos.x - frameLocation.x;
            bottle.setLocation(x - (bottle.getWidth() / 2), 100);

            scoreLabel.setText(score + "/35");
            currentThirstBar.setValue(currentDino.getThirst());

            updateDrops();
            updatePlayer();
            currentDino.updateStats(0.01, true);
        } else {
            gameTimer.stop();
            backPanel.remove(Player);
            Player = null;
            backPanel.remove(bottle);
            bottle = null;

            for (int i = 0; i < Drops.length; i++) {
                backPanel.remove(Drops[i]);
                Drops[i] = null;
            }

            currentDino.setTCool(60 * 100);
            MainManager.setDino(currentDino);
            MainManager.SaveGame();
            MainManager.selectPage(1, this.getLocation().x, this.getLocation().y);

            this.dispose();
        }
    }

    public void updateDrops() {
        dropFrameCount++;

        if (Drops.length < 35) {
            if (dropFrameCount > timeTillNext) {
                JLabel[] temp = Drops;
                Drops = new JLabel[temp.length + 1];

                for (int i = 0; i < temp.length; i++) {
                    Drops[i] = temp[i];
                }

                JLabel newDrop = new JLabel();
                newDrop.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/droplet25.png")));
                newDrop.setLocation(bottle.getLocation().x, bottle.getLocation().y);
                newDrop.setSize(25, 25);
                Drops[Drops.length - 1] = newDrop;
                backPanel.add(newDrop);

                this.revalidate();
                this.repaint();

                dropFrameCount = 0;
                timeTillNext = ((int) (Math.random() * 100) + 20);
            }
        }
        for (int i = 0; i < Drops.length; i++) {
            Drops[i].setLocation(Drops[i].getLocation().x, Drops[i].getLocation().y + 3);

            if (Drops[i].getBounds().intersects(Player.getBounds())) {
                score++;
                aDrops++;
                Drops[i].setLocation(this.getWidth() + 100, this.getHeight() - 1000000);
                backPanel.remove(Drops[i]);
                if (currentDino.getThirst() < 100) {
                    currentDino.setThirst(currentDino.getThirst() + 1);
                }
            }

            if (Drops[i].getLocation().y > this.getHeight()) {
                Drops[i].setLocation(this.getWidth() + 100, this.getHeight() - 1000000);
                Drops[i].setText("");
                aDrops++;
            }
        }
    }

    public void updatePlayer() {
        int x = Player.getLocation().x;
        playerFrameCount++;

        if (playerFrameCount > 2) {
            if (dir == true) {
                if (x < 250) {
                    Player.setLocation(x + 1, this.getHeight() - 175);
                } else {
                    dir = false;
                }
            } else if (dir == false) {
                if (x > 0) {
                    Player.setLocation(x - 1, this.getHeight() - 175);
                } else {
                    dir = true;
                }
            }
            playerFrameCount = 0;
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

        backPanel = new javax.swing.JPanel();
        scoreLabel = new javax.swing.JLabel();
        currentLabel = new javax.swing.JLabel();
        currentThirstBar = new javax.swing.JProgressBar();
        scoreLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backPanel.setBackground(new java.awt.Color(217, 203, 179));
        backPanel.setForeground(new java.awt.Color(217, 203, 179));
        backPanel.setToolTipText("");

        scoreLabel.setBackground(new java.awt.Color(74, 95, 51));
        scoreLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 36)); // NOI18N
        scoreLabel.setForeground(new java.awt.Color(74, 95, 51));
        scoreLabel.setText("10");

        currentLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        currentLabel.setForeground(new java.awt.Color(74, 95, 51));
        currentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentLabel.setText("Current Thirst:");

        scoreLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        scoreLabel2.setForeground(new java.awt.Color(74, 95, 51));
        scoreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel2.setText("Score:");

        javax.swing.GroupLayout backPanelLayout = new javax.swing.GroupLayout(backPanel);
        backPanel.setLayout(backPanelLayout);
        backPanelLayout.setHorizontalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backPanelLayout.createSequentialGroup()
                        .addComponent(currentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                        .addComponent(scoreLabel2))
                    .addComponent(currentThirstBar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(currentLabel)
                            .addComponent(scoreLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentThirstBar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(ThirstGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThirstGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThirstGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThirstGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThirstGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPanel;
    private javax.swing.JLabel currentLabel;
    private javax.swing.JProgressBar currentThirstBar;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel scoreLabel2;
    // End of variables declaration//GEN-END:variables
}
