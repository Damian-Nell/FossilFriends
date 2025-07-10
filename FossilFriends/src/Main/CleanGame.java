package Main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CleanGame extends javax.swing.JFrame {

    private Dinosaur currentDino;
    private Timer gameTimer;

    private int frameTime = 10;
    private int frameCount = 0;
    private int aDirt;

    private JLabel[] dirt = new JLabel[25];
    private JLabel sponge = new JLabel("");
    private JLabel dinoIMG = new JLabel("");

    public CleanGame() {
        initComponents();
    }
    
    public void initGame() {
        currentDino = MainManager.getDino();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(new byte[0]);
        Cursor invisibleCursor = toolkit.createCustomCursor(image, new Point(0, 0), "Invisible");
        this.setCursor(invisibleCursor);

        sponge.setSize(50, 50);
        sponge.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/sponge50.png")));

        dinoIMG.setSize(150, 150);
        dinoIMG.setLocation((this.getWidth() / 2) - 75, (this.getHeight() / 2) - 75);
        if (currentDino.getType() == 1) {
            dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/BrontoPic150.png")));
        } else if (currentDino.getType() == 2) {
            dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/RaptorPic150.png")));
        } else {
            dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/StegoPic150.png")));
        }

        backPanel.add(sponge);
        setDirt();
        backPanel.add(dinoIMG);

        gameTimer = new Timer(frameTime, e -> updateGame());
        gameTimer.start();
    }
    
    private void updateGame() {
        if (aDirt < dirt.length) {

            Point mousePos = MouseInfo.getPointerInfo().getLocation();
            Point frameLocation = this.getLocationOnScreen();
            sponge.setLocation(mousePos.x - frameLocation.x - sponge.getWidth()/2, mousePos.y - frameLocation.y  - sponge.getHeight()/2);

            frameCount++;

            currentCleanBar.setValue(currentDino.getClean());
            currentDino.updateStats(0.01, true);
            if (frameCount > 100) {
                updateDirt();
                frameCount = 0;
            }
        } else {
            gameTimer.stop();
            backPanel.remove(sponge);
            sponge = null;
            dirt = null;
            
            currentDino.setCCool(60*100);
            MainManager.setDino(currentDino);
            
            MainManager.SaveGame();
            MainManager.selectPage(1, this.getLocation().x, this.getLocation().y);

            this.dispose();
        }

    }
    
    //for every place in the dirt array it will create a new image and set it to a random position
    private void setDirt() {
        for (int i = 0; i < dirt.length; i++) {
            dirt[i] = new JLabel();
            dirt[i].setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/dirtSpot25.png")));
            dirt[i].setSize(25, 25);
            dirt[i].setLocation((int) Math.round(Math.random() * 300 + 50), (int) Math.round(Math.random() * 500 + 75));

            backPanel.add(dirt[i]);
        }
    }

    //called once every second in update game, checks if it is intersecting with the sponge. if it is then it removes the dirt peice and increase clean stat.
    private void updateDirt() {
        for (int i = 0; i < dirt.length; i++) {
            if (dirt[i].getBounds().intersects(sponge.getBounds())) {

                dirt[i].setLocation(this.getWidth() + 100, this.getHeight() - 1000000);
                backPanel.remove(dirt[i]);
                aDirt++;

                if (currentDino.getClean() < 100) {
                    currentDino.setClean(currentDino.getClean() + 1);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backPanel = new javax.swing.JPanel();
        currentLabel = new javax.swing.JLabel();
        currentCleanBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backPanel.setBackground(new java.awt.Color(217, 203, 179));
        backPanel.setForeground(new java.awt.Color(217, 203, 179));

        currentLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        currentLabel.setForeground(new java.awt.Color(74, 95, 51));
        currentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentLabel.setText("Current Cleanliness:");

        javax.swing.GroupLayout backPanelLayout = new javax.swing.GroupLayout(backPanel);
        backPanel.setLayout(backPanelLayout);
        backPanelLayout.setHorizontalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentLabel)
                    .addComponent(currentCleanBar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        backPanelLayout.setVerticalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentCleanBar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(550, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(CleanGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CleanGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CleanGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CleanGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CleanGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPanel;
    private javax.swing.JProgressBar currentCleanBar;
    private javax.swing.JLabel currentLabel;
    // End of variables declaration//GEN-END:variables
}
