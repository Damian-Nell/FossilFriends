package Main;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameScreen extends javax.swing.JFrame {

    private Timer gameTimer;
    private int frameTime = 10;
    private int frameCounter = 0;
    private Dinosaur currentDino;
    private JLabel dinoIMG;
    JLabel[] hearts = new JLabel[0];

    private boolean ran = false, pet = false, deadConfirm = false;

    public GameScreen() {
        initComponents();
    }

    public void initGame() {
        initSaves();

        gameTimer = new Timer(frameTime, e -> updateGame());
        gameTimer.start();
    }

    private void initSaves() {
        currentDino = MainManager.getDino();

        dinoIMG = new JLabel("");
        dinoIMG.setLocation((backPanel.getWidth() / 2) - 75 - 34, (backPanel.getHeight() / 2) - 75 + 60);
        dinoIMG.setSize(150, 150);
    }

    private void updateGame() {
        if (currentDino.getDeath() == false || ran == false) {
            updateFields();
            currentDino.updateStats(0.01, true);
            MainManager.setDino(currentDino);

            petDino();
            updateHearts();

            if (frameCounter >= 100) {
                frameCounter = 0;
                MainManager.SaveGame();
            } else {
                frameCounter++;
            }

            ran = true;
            pet = false;
        } else {
            deadDino();
        }
    }

    private void petDino() {
        dinoIMG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pet == false && currentDino.getLonely() < 100) {
                    JLabel[] temp = hearts;
                    hearts = new JLabel[temp.length + 1];
                    try {
                        for (int i = 0; i < hearts.length; i++) {
                            hearts[i] = temp[i];
                        }
                    } catch (Exception i) {
                        hearts[hearts.length - 1] = new JLabel();
                        hearts[hearts.length - 1].setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/heart50.png")));
                        hearts[hearts.length - 1].setSize(50, 40);
                        hearts[hearts.length - 1].setLocation((int) (Math.random() * dinoIMG.getWidth() + dinoIMG.getLocation().x), (int) (Math.random() * dinoIMG.getHeight() + dinoIMG.getLocation().y));

                        backPanel.add(hearts[hearts.length - 1]);
                    }
                    currentDino.setLonely(currentDino.getLonely() + 1);
                    pet = true;
                }
            }
        }
        );

    }

    private void updateHearts() {
        for (int i = 0; i < hearts.length; i++) {
            if (hearts[i].getLocation().y > 80) {
                hearts[i].setLocation(hearts[i].getLocation().x, hearts[i].getLocation().y - 1);
            } else {
                backPanel.remove(hearts[i]);
            }
        }
    }

    private void updateFields() {
        LName.setText(currentDino.getName());
        LAge.setText(String.valueOf(currentDino.getAge()));

        if (currentDino.getDeath() == false) {

            if (currentDino.getType() == 1) {
                dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/BrontoPic150.png")));
            } else if (currentDino.getType() == 2) {
                dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/RaptorPic150.png")));
            } else {
                dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/StegoPic150.png")));
            }
            backPanel.add(dinoIMG);

            if (currentDino.getHappiness() > 90) {
                LHappy.setText("Amazing");
            } else if (currentDino.getHappiness() > 70) {
                LHappy.setText("Happy");
            } else if (currentDino.getHappiness() > 40) {
                LHappy.setText("Normal");
            } else if (currentDino.getHappiness() > 20) {
                LHappy.setText("Sad");
            } else {
                LHappy.setText("Depressed");
            }

            if (currentDino.getLonely() > 90) {
                LLonely.setText("Overwhelmed");
                currentDino.setStatMulti(3);
            } else if (currentDino.getLonely() > 80) {
                LLonely.setText("Normal");
                currentDino.setStatMulti(1.5);
            } else if (currentDino.getLonely() > 60) {
                LLonely.setText("Loved");
                currentDino.setStatMulti(1);
            } else if (currentDino.getLonely() > 40) {
                currentDino.setStatMulti(1.5);
                LLonely.setText("Normal");
            } else {
                currentDino.setStatMulti(3);
                LLonely.setText("Lonely");
            }

            if (currentDino.getHCool() > 0) {
                hungerButton.setEnabled(false);
                hCoolDownLabel.setText(String.valueOf(currentDino.getHCool() / 100));
            } else {
                hungerButton.setEnabled(true);
                hCoolDownLabel.setText("");
            }

            if (currentDino.getTCool() > 0) {
                thirstButton.setEnabled(false);
                tCoolDownLabel.setText(String.valueOf(currentDino.getTCool() / 100));
            } else {
                thirstButton.setEnabled(true);
                tCoolDownLabel.setText("");
            }

            if (currentDino.getCCool() > 0) {
                cleanButton.setEnabled(false);
                cCoolDownLabel.setText(String.valueOf(currentDino.getCCool() / 100));
            } else {
                cleanButton.setEnabled(true);
                cCoolDownLabel.setText("");
            }

        } else {
            hungerButton.setEnabled(false);
            thirstButton.setEnabled(false);
            cleanButton.setEnabled(false);
        }

        hungerBar.setValue(currentDino.getHunger());
        LHunger.setText(currentDino.getHunger() + "%");
        thirstBar.setValue(currentDino.getThirst());
        LThirst.setText(currentDino.getThirst() + "%");
        cleanBar.setValue(currentDino.getClean());
        LClean.setText(currentDino.getClean() + "%");
    }

    public void deadDino() {
        if (dinoIMG == null) {
            dinoIMG = new JLabel("");
            dinoIMG.setLocation((backPanel.getWidth() / 2) - 75 - 34, (backPanel.getHeight() / 2) - 75 + 60);
            dinoIMG.setSize(150, 150);
        }

        if (currentDino.getType() == 1) {
            dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/DeadBrontoPic150.png")));
        } else if (currentDino.getType() == 2) {
            dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/DeadRaptorPic150.png")));
        } else {
            dinoIMG.setIcon(new ImageIcon(getClass().getResource("/Main/MainResources/DeadStegoPic150.png")));
        }

        backPanel.add(dinoIMG);
        hungerBar.setValue(currentDino.getHunger());
        LHunger.setText(currentDino.getHunger() + "%");
        thirstBar.setValue(currentDino.getThirst());
        LThirst.setText(currentDino.getThirst() + "%");
        cleanBar.setValue(currentDino.getClean());
        LClean.setText(currentDino.getClean() + "%");
        LLonely.setText("Dead");
        LHappy.setText("Dead");
        hCoolDownLabel.setText("");
        tCoolDownLabel.setText("");
        cCoolDownLabel.setText("");

        if (deadConfirm == false) {
            int result = JOptionPane.showConfirmDialog(backPanel, "Your dino is dead. Would you like to delete " + currentDino.getName(),
                    "", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                MainManager.deleteDino();
                MainManager.selectPage(0, this.getLocation().x, this.getLocation().y);
                gameTimer.stop();
                this.dispose();
            } else {
                deadConfirm = true;
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

        jLabel3 = new javax.swing.JLabel();
        backPanel = new javax.swing.JPanel();
        hungerButton = new javax.swing.JButton();
        thirstButton = new javax.swing.JButton();
        cleanButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        LHappy = new javax.swing.JLabel();
        happyLabel = new javax.swing.JLabel();
        LLonely = new javax.swing.JLabel();
        moodLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        LName = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        LAge = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        hungerLabel = new javax.swing.JLabel();
        hungerBar = new javax.swing.JProgressBar();
        LHunger = new javax.swing.JLabel();
        thirstLabel = new javax.swing.JLabel();
        thirstBar = new javax.swing.JProgressBar();
        LThirst = new javax.swing.JLabel();
        LClean = new javax.swing.JLabel();
        cleanBar = new javax.swing.JProgressBar();
        cleanLabel = new javax.swing.JLabel();
        cCoolDownLabel = new javax.swing.JLabel();
        tCoolDownLabel = new javax.swing.JLabel();
        hCoolDownLabel = new javax.swing.JLabel();
        homeButton = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        backPanel.setBackground(new java.awt.Color(217, 203, 179));
        backPanel.setForeground(new java.awt.Color(217, 203, 179));

        hungerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/MainResources/HungerIcon70.png"))); // NOI18N
        hungerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hungerButtonActionPerformed(evt);
            }
        });

        thirstButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/MainResources/ThirstIcon70.png"))); // NOI18N
        thirstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirstButtonActionPerformed(evt);
            }
        });

        cleanButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/MainResources/CleanIcon70.png"))); // NOI18N
        cleanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(183, 157, 111));
        jPanel2.setForeground(new java.awt.Color(183, 157, 111));

        LHappy.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        LHappy.setForeground(new java.awt.Color(74, 95, 51));
        LHappy.setText("depressed");

        happyLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        happyLabel.setForeground(new java.awt.Color(74, 95, 51));
        happyLabel.setText("Happiness:");

        LLonely.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        LLonely.setForeground(new java.awt.Color(74, 95, 51));
        LLonely.setText("Overwhelmed");

        moodLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        moodLabel.setForeground(new java.awt.Color(74, 95, 51));
        moodLabel.setText("Mood: ");

        nameLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(74, 95, 51));
        nameLabel.setText("Name:");

        LName.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        LName.setForeground(new java.awt.Color(74, 95, 51));
        LName.setText("123456789abcdef");

        ageLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        ageLabel.setForeground(new java.awt.Color(74, 95, 51));
        ageLabel.setText("Age:");

        LAge.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        LAge.setForeground(new java.awt.Color(74, 95, 51));
        LAge.setText("1000");

        jPanel3.setBackground(new java.awt.Color(200, 180, 145));
        jPanel3.setForeground(new java.awt.Color(200, 180, 145));

        hungerLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerLabel.setForeground(new java.awt.Color(74, 95, 51));
        hungerLabel.setText("Hunger:");

        hungerBar.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        hungerBar.setForeground(new java.awt.Color(179, 217, 203));
        hungerBar.setValue(50);

        LHunger.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        LHunger.setForeground(new java.awt.Color(74, 95, 51));
        LHunger.setText("100%");

        thirstLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        thirstLabel.setForeground(new java.awt.Color(74, 95, 51));
        thirstLabel.setText("Thirst:");

        thirstBar.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        thirstBar.setForeground(new java.awt.Color(179, 217, 203));
        thirstBar.setValue(50);

        LThirst.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        LThirst.setForeground(new java.awt.Color(74, 95, 51));
        LThirst.setText("100%");

        LClean.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        LClean.setForeground(new java.awt.Color(74, 95, 51));
        LClean.setText("100%");

        cleanBar.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        cleanBar.setForeground(new java.awt.Color(179, 217, 203));
        cleanBar.setValue(50);

        cleanLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        cleanLabel.setForeground(new java.awt.Color(74, 95, 51));
        cleanLabel.setText("Cleanliness:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(hungerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hungerBar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LHunger)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 67, Short.MAX_VALUE)
                        .addComponent(cleanLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cleanBar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LClean))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(thirstLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thirstBar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LThirst)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LHunger)
                        .addComponent(LClean)
                        .addComponent(cleanLabel))
                    .addComponent(hungerBar, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hungerLabel)
                    .addComponent(cleanBar, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(thirstLabel)
                    .addComponent(thirstBar, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LThirst))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(moodLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LLonely))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LAge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(happyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LHappy)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LLonely)
                    .addComponent(moodLabel)
                    .addComponent(nameLabel)
                    .addComponent(LName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LHappy)
                    .addComponent(happyLabel)
                    .addComponent(ageLabel)
                    .addComponent(LAge))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cCoolDownLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        cCoolDownLabel.setForeground(new java.awt.Color(74, 95, 51));
        cCoolDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cCoolDownLabel.setText("6:00:00");

        tCoolDownLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        tCoolDownLabel.setForeground(new java.awt.Color(74, 95, 51));
        tCoolDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tCoolDownLabel.setText("6:00:00");

        hCoolDownLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        hCoolDownLabel.setForeground(new java.awt.Color(74, 95, 51));
        hCoolDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hCoolDownLabel.setText("6:00:00");

        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/MainResources/Home70.png"))); // NOI18N
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backPanelLayout = new javax.swing.GroupLayout(backPanel);
        backPanel.setLayout(backPanelLayout);
        backPanelLayout.setHorizontalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backPanelLayout.createSequentialGroup()
                        .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cleanButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cCoolDownLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(thirstButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tCoolDownLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hCoolDownLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hungerButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        backPanelLayout.setVerticalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(hungerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hCoolDownLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thirstButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tCoolDownLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backPanelLayout.createSequentialGroup()
                        .addComponent(cleanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cCoolDownLabel))
                    .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    //if buttons are pressed then it will take you to the respective minigame.
    private void hungerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hungerButtonActionPerformed
        // TODO add your handling code here:
        MainManager.SaveGame();
        MainManager.selectPage(2, this.getLocation().x, this.getLocation().y);
        gameTimer.stop();
        this.dispose();
    }//GEN-LAST:event_hungerButtonActionPerformed

    private void thirstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirstButtonActionPerformed
        // TODO add your handling code here:
        MainManager.SaveGame();
        MainManager.selectPage(3, this.getLocation().x, this.getLocation().y);
        gameTimer.stop();
        this.dispose();
    }//GEN-LAST:event_thirstButtonActionPerformed

    private void cleanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanButtonActionPerformed
        // TODO add your handling code here:
        MainManager.SaveGame();
        MainManager.selectPage(4, this.getLocation().x, this.getLocation().y);
        gameTimer.stop();
        this.dispose();
    }//GEN-LAST:event_cleanButtonActionPerformed

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        // TODO add your handling code here:
        MainManager.SaveGame();
        MainManager.selectPage(0, this.getLocation().x, this.getLocation().y);
        gameTimer.stop();
        this.dispose();
    }//GEN-LAST:event_homeButtonActionPerformed

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
            java.util.logging.Logger.getLogger(GameScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LAge;
    private javax.swing.JLabel LClean;
    private javax.swing.JLabel LHappy;
    private javax.swing.JLabel LHunger;
    private javax.swing.JLabel LLonely;
    private javax.swing.JLabel LName;
    private javax.swing.JLabel LThirst;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JPanel backPanel;
    private javax.swing.JLabel cCoolDownLabel;
    private javax.swing.JProgressBar cleanBar;
    private javax.swing.JButton cleanButton;
    private javax.swing.JLabel cleanLabel;
    private javax.swing.JLabel hCoolDownLabel;
    private javax.swing.JLabel happyLabel;
    private javax.swing.JButton homeButton;
    private javax.swing.JProgressBar hungerBar;
    private javax.swing.JButton hungerButton;
    private javax.swing.JLabel hungerLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel moodLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel tCoolDownLabel;
    private javax.swing.JProgressBar thirstBar;
    private javax.swing.JButton thirstButton;
    private javax.swing.JLabel thirstLabel;
    // End of variables declaration//GEN-END:variables
}
