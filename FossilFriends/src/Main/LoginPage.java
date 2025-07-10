/*
    *imports needed files.
*/
package Main;

import java.awt.Color;
import java.time.*;
import javax.swing.*;

public class LoginPage extends javax.swing.JFrame {

    /*
        * lists all the variables
        *    - saves - an array of the dinosaur class to represent all the saves
        *    - Available - a boolean to determine if there is a save available in the spinnerNum (else it will create a new save).
        *    - isError - a boolean to check if there is an error.
        *    - TBClear - a boolean to check if the textbox has been cleared already (to avoid clearing it multiple times).
        *    - gameTimer - a timer that is initialised later on. it will update the game everytime it goes off.
        *    - frameTime - the time it takes for the gameTimer to go off (in ms).
        *    - frameCount - goes up one everyFrame for time based mechanics.
    */
    public Dinosaur[] saves;

    private boolean Available;
    private boolean isError;
    private boolean TBClear;

    private Timer gameTimer;
    private int frameTime = 10;
    private int frameCount = 0;

    
    /*
        *initialises this page, call the initSave method, and creates a new timer which goes off every frameTime
        *and calls updateGame() method everytime it goes off.
    */
    public LoginPage() {
        initComponents();
        initSaves();

        gameTimer = new Timer(frameTime, e -> updateGame());
        gameTimer.start();

    }
    
    /*
        *firstly, it calls check(), then the updateFields(), and increases the frameCount.
        *Then it checks if there is an available save for the slot number. if there is then it disables the text box 
        *if there isnt then it enables the text box for the user to create a new save.
    */
    public void updateGame() {
        check();
        updateFields();
        frameCount++;

        if (Available == true) {
            inputName.setEnabled(false);
            inputName.setText("Disabled");
            TBClear = false;
        } else {
            if (TBClear == false) {
                inputName.setEnabled(true);
                inputName.setText("");
                TBClear = true;
            }
        }
    }

    /*
        *initialises all the dinosaur saves, then calls updateFromLast().
    */
    public void initSaves() {
        saves = MainManager.SM.loadGames();
        updateFromLast();
    }
    
    /*
        *It is will try to set all the labels to their respective values, 
        *but if the try fails then that indicates there isnt a save for the selected spinner num, 
        *so it will default to empty to indicate the user to create a new save
        *also checks if there is an error. if it is then it will set the error text to red for 100 frames (1000ms or 1 second)
        *if there is no error then it will make the text the same colour as the background to make it invisible
    
    */
    private void updateFields() {
        try {
            saveNameLabel.setText(getSaveName(getSpinnerNum()));
            hungerLabel.setText("" + saves[getSpinnerNum()].getHunger());
            thirstLabel.setText("" + saves[getSpinnerNum()].getThirst());
            cleanLabel.setText("" + saves[getSpinnerNum()].getClean());
            if (saves[getSpinnerNum()].getDeath() == true) {
                deadLabel.setForeground(Color.red);
                deadLabel.setText("Dead");
            } else {
                deadLabel.setForeground(new java.awt.Color(74, 95, 51));
                deadLabel.setText("Alive");
            }
            ageLabel.setText("" + saves[getSpinnerNum()].getAge());
            lonelyLabel.setText("" + saves[getSpinnerNum()].getLonely());
            dateCreateLabel.setText("" + saves[getSpinnerNum()].getStart());
            dateVisitLabel.setText("" + saves[getSpinnerNum()].getLast());

            if (saves[getSpinnerNum()].getType() == 1) {
                dinoTypeIMG1.setEnabled(true);
                dinoSelectBronto.setSelected(true);
                dinoTypeIMG2.setEnabled(false);
                dinoSelectRaptor.setSelected(false);
                dinoTypeIMG3.setEnabled(false);
                dinoSelectStego.setSelected(false);
            } else if (saves[getSpinnerNum()].getType() == 2) {
                dinoTypeIMG1.setEnabled(false);
                dinoSelectBronto.setSelected(false);
                dinoTypeIMG2.setEnabled(true);
                dinoSelectRaptor.setSelected(true);
                dinoTypeIMG3.setEnabled(false);
                dinoSelectStego.setSelected(false);
            } else {
                dinoTypeIMG1.setEnabled(false);
                dinoSelectBronto.setSelected(false);
                dinoTypeIMG2.setEnabled(false);
                dinoSelectRaptor.setSelected(false);
                dinoTypeIMG3.setEnabled(true);
                dinoSelectStego.setSelected(true);
            }
            dinoSelectBronto.setEnabled(false);
            dinoSelectRaptor.setEnabled(false);
            dinoSelectStego.setEnabled(false);
        } catch (Exception e) {
            saveNameLabel.setText(getSaveName(getSpinnerNum()));
            hungerLabel.setText("");
            thirstLabel.setText("");
            cleanLabel.setText("");
            deadLabel.setText("");
            ageLabel.setText("");
            lonelyLabel.setText("");
            dateCreateLabel.setText("");
            dateVisitLabel.setText("");
            dinoTypeIMG1.setEnabled(true);
            dinoTypeIMG2.setEnabled(true);
            dinoTypeIMG3.setEnabled(true);
            dinoSelectBronto.setEnabled(true);
            dinoSelectRaptor.setEnabled(true);
            dinoSelectStego.setEnabled(true);
        }

        if (isError == true) {
            errorText.setForeground(Color.red);

            if (frameCount >= 100) {
                isError = false;
                frameCount = 0;
            }
        } else {
            errorText.setForeground(new java.awt.Color(217, 203, 179));
        }
    }
    
    /*
        *call when the Start Game button is pressed. checks if there is an available save.
        *   -if there is then it will load the page with the selected save
        *    -if there isnt then it do some data validation to check if the name and type is valid, 
        *                        and then create a new save with the default stats.
        *Also closes this page once next one is loaded to prevent clutter
    */
    private void loadGame() {
        if (Available == true) {
            int i = getSpinnerNum();
            MainManager.setDino(saves[i]);
            MainManager.selectPage(1, this.getLocation().x, this.getLocation().y);
            this.dispose();
        } else {
            int type;

            if (inputName.getText().trim().isEmpty()) {
                frameCount = 0;
                isError = true;
                errorText.setText("Please enter a name");
            } else {
                if (dinoSelectBronto.isSelected() == true) {
                    type = 1;
                } else if (dinoSelectRaptor.isSelected() == true) {
                    type = 2;
                } else {
                    type = 3;
                }

                Dinosaur currentDino = new Dinosaur(getSpinnerNum(), inputName.getText().trim(), type, 50, 50, 50, 1, 50, false, LocalDateTime.now(), LocalDateTime.now());
                MainManager.setDino(currentDino);
                MainManager.SM.saveGame(currentDino);
                
                MainManager.selectPage(1, this.getLocation().x, this.getLocation().y);
                this.dispose();
            }
        }
    }

    /*
        *will try to return the select saves name. if it doesnt exist then it will fail and indicate a new save can be made.
    */
    private String getSaveName(int i) {
        try {
            Available = true;
            return saves[i].getName();
        } catch (Exception e) {
            Available = false;
            return "Create A New Dino";
        }
    }

    /*
        *Data validation:
        *   - doesnt allow you to create a negative save and a save that is more than 1 more the total length of the saves (to keep consistency).
        *   - then limits the inputName textbox to a name thats less than 15 and if it goes over, removes the extra letters
    */
    private void check() {
        if (getSpinnerNum() < 0) {
            saveSpinner.setValue(0);
        } else if (getSpinnerNum() > saves.length) {
            saveSpinner.setValue(saves.length);
        }

        if (inputName.getText().length() > 15) {
            frameCount = 0;
            isError = true;
            errorText.setText("Please Keep Your Pets Name Under 15 Characters");
            String tempInputBox;
            String tempInputName = "";
            tempInputBox = inputName.getText().trim();
            inputName.setText("");

            for (int i = 0; i < 15; i++) {
                tempInputName += tempInputBox.charAt(i);
            }

            inputName.setText(tempInputName);
        }
    }
    
    /*
        * just returns the spinner number value
    */
    private int getSpinnerNum() {
        return (int) saveSpinner.getValue();
    }
    
    /*
        *checks the current stats and then checks the last time you visited and updates the stats accordingly:
        *   - hunger decreases by 15% every 6 hours.
        *   - thirst decreases by 20% every 6 hours.
        *   - Cleanliness decreases by 1% every hour (6% every 6 hours).
        *   - Loneliness decreases by 2% every hour (12% every 6 hours).
        * then checks if any of the stats has reached 0 and if so then sets the dinosaur to dead.
        * and caps loneliness to 1 and then saves the new stats.
    */
    private void updateFromLast() {
        for (int i = 0; i < saves.length; i++) {
            saves[i].setStatMulti(1);
            saves[i].updateStats(saves[i].secsSinceLast(), false);

            MainManager.SM.saveGame(saves[i]);

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

        jPanel1 = new javax.swing.JPanel();
        saveSpinner = new javax.swing.JSpinner();
        saveNameLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        TitleLabel = new javax.swing.JLabel();
        inputName = new javax.swing.JTextField();
        ageLabel = new javax.swing.JLabel();
        hungerLabel = new javax.swing.JLabel();
        thirstLabel = new javax.swing.JLabel();
        cleanLabel = new javax.swing.JLabel();
        dateCreateLabel = new javax.swing.JLabel();
        deadLabel = new javax.swing.JLabel();
        lonelyLabel = new javax.swing.JLabel();
        errorText = new javax.swing.JLabel();
        dinoSelectBronto = new javax.swing.JRadioButton();
        dinoSelectRaptor = new javax.swing.JRadioButton();
        dinoSelectStego = new javax.swing.JRadioButton();
        dinoTypeIMG1 = new javax.swing.JLabel();
        dinoTypeIMG2 = new javax.swing.JLabel();
        dinoTypeIMG3 = new javax.swing.JLabel();
        hungerStatLabel = new javax.swing.JLabel();
        hungerStatLabel1 = new javax.swing.JLabel();
        hungerStatLabel2 = new javax.swing.JLabel();
        hungerStatLabel3 = new javax.swing.JLabel();
        hungerStatLabel4 = new javax.swing.JLabel();
        dateCreateStatLabel = new javax.swing.JLabel();
        lonelyStatLabel = new javax.swing.JLabel();
        dateVisitStatLabel = new javax.swing.JLabel();
        dateVisitLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(217, 203, 179));
        jPanel1.setForeground(new java.awt.Color(217, 203, 179));

        saveNameLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        saveNameLabel.setForeground(new java.awt.Color(74, 95, 51));
        saveNameLabel.setText("DinoName");

        startButton.setText("Start Button");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        TitleLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        TitleLabel.setForeground(new java.awt.Color(74, 95, 51));
        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText("FOSSIL FRIENDS!");

        inputName.setBackground(new java.awt.Color(249, 245, 227));
        inputName.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        inputName.setForeground(new java.awt.Color(74, 95, 51));
        inputName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputName.setText("Disabled");
        inputName.setToolTipText("Enter New Dinosaur Name");
        inputName.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        inputName.setSelectionColor(new java.awt.Color(74, 95, 51));

        ageLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        ageLabel.setForeground(new java.awt.Color(74, 95, 51));
        ageLabel.setText("Age:");

        hungerLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerLabel.setForeground(new java.awt.Color(74, 95, 51));
        hungerLabel.setText("Hunger:");

        thirstLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        thirstLabel.setForeground(new java.awt.Color(74, 95, 51));
        thirstLabel.setText("Thirst:");

        cleanLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        cleanLabel.setForeground(new java.awt.Color(74, 95, 51));
        cleanLabel.setText("Cleanliness:");

        dateCreateLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dateCreateLabel.setForeground(new java.awt.Color(74, 95, 51));
        dateCreateLabel.setText("Date Created:");

        deadLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        deadLabel.setForeground(new java.awt.Color(74, 95, 51));
        deadLabel.setText("Dead:");

        lonelyLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        lonelyLabel.setForeground(new java.awt.Color(74, 95, 51));
        lonelyLabel.setText("Loneliness");

        errorText.setForeground(new java.awt.Color(217, 203, 179));
        errorText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorText.setText("jLabel1");

        dinoSelectBronto.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dinoSelectBronto.setForeground(new java.awt.Color(74, 95, 51));
        dinoSelectBronto.setText("Brontosaurus");
        dinoSelectBronto.setContentAreaFilled(false);
        dinoSelectBronto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dinoSelectBrontoActionPerformed(evt);
            }
        });

        dinoSelectRaptor.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dinoSelectRaptor.setForeground(new java.awt.Color(74, 95, 51));
        dinoSelectRaptor.setText("Raptor");
        dinoSelectRaptor.setContentAreaFilled(false);
        dinoSelectRaptor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dinoSelectRaptorActionPerformed(evt);
            }
        });

        dinoSelectStego.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dinoSelectStego.setForeground(new java.awt.Color(74, 95, 51));
        dinoSelectStego.setText("Stegosaurus");
        dinoSelectStego.setContentAreaFilled(false);
        dinoSelectStego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dinoSelectStegoActionPerformed(evt);
            }
        });

        dinoTypeIMG1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/LoginResources/BrontoDino.png"))); // NOI18N

        dinoTypeIMG2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/LoginResources/RaptorDino.png"))); // NOI18N

        dinoTypeIMG3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/LoginResources/StegoDino.png"))); // NOI18N

        hungerStatLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerStatLabel.setForeground(new java.awt.Color(74, 95, 51));
        hungerStatLabel.setText("Thirst:");

        hungerStatLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerStatLabel1.setForeground(new java.awt.Color(74, 95, 51));
        hungerStatLabel1.setText("Hunger:");

        hungerStatLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerStatLabel2.setForeground(new java.awt.Color(74, 95, 51));
        hungerStatLabel2.setText("Condition:");

        hungerStatLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerStatLabel3.setForeground(new java.awt.Color(74, 95, 51));
        hungerStatLabel3.setText("Cleanliness:");

        hungerStatLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        hungerStatLabel4.setForeground(new java.awt.Color(74, 95, 51));
        hungerStatLabel4.setText("Age:");

        dateCreateStatLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dateCreateStatLabel.setForeground(new java.awt.Color(74, 95, 51));
        dateCreateStatLabel.setText("Date Created:");

        lonelyStatLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        lonelyStatLabel.setForeground(new java.awt.Color(74, 95, 51));
        lonelyStatLabel.setText("Loneliness:");

        dateVisitStatLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dateVisitStatLabel.setForeground(new java.awt.Color(74, 95, 51));
        dateVisitStatLabel.setText("Date Visited:");

        dateVisitLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        dateVisitLabel.setForeground(new java.awt.Color(74, 95, 51));
        dateVisitLabel.setText("Date Visited:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(errorText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inputName)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(saveSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(saveNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(hungerStatLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(hungerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(hungerStatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(thirstLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(hungerStatLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cleanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(hungerStatLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(deadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(hungerStatLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lonelyStatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lonelyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(dateVisitStatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dateVisitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dateCreateStatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dinoTypeIMG1)
                    .addComponent(dinoSelectBronto))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dinoSelectRaptor)
                    .addComponent(dinoTypeIMG2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dinoSelectStego)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(dinoTypeIMG3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hungerStatLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hungerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thirstLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hungerStatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hungerStatLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cleanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hungerStatLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hungerStatLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lonelyStatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lonelyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateCreateStatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCreateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateVisitStatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateVisitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dinoSelectRaptor)
                    .addComponent(dinoSelectBronto)
                    .addComponent(dinoSelectStego))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dinoTypeIMG1)
                    .addComponent(dinoTypeIMG2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dinoTypeIMG3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        //when the button is pressed then the loadGame method is called.
        loadGame();
    }//GEN-LAST:event_startButtonActionPerformed

    private void dinoSelectBrontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dinoSelectBrontoActionPerformed
        // TODO add your handling code here:
        // if the brontosaurus is selected, sets the others as unselected
        dinoSelectRaptor.setSelected(false);
        dinoSelectStego.setSelected(false);
    }//GEN-LAST:event_dinoSelectBrontoActionPerformed

    private void dinoSelectRaptorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dinoSelectRaptorActionPerformed
        // TODO add your handling code here:
        // if the raptor is selected, sets the others as unselected
        dinoSelectBronto.setSelected(false);
        dinoSelectStego.setSelected(false);
    }//GEN-LAST:event_dinoSelectRaptorActionPerformed

    private void dinoSelectStegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dinoSelectStegoActionPerformed
        // TODO add your handling code here:
        // if the stegosaurus is selected, sets the others as unselected
        dinoSelectBronto.setSelected(false);
        dinoSelectRaptor.setSelected(false);
    }//GEN-LAST:event_dinoSelectStegoActionPerformed

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
            java.util.logging.Logger.getLogger(LoginPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JLabel cleanLabel;
    private javax.swing.JLabel dateCreateLabel;
    private javax.swing.JLabel dateCreateStatLabel;
    private javax.swing.JLabel dateVisitLabel;
    private javax.swing.JLabel dateVisitStatLabel;
    private javax.swing.JLabel deadLabel;
    private javax.swing.JRadioButton dinoSelectBronto;
    private javax.swing.JRadioButton dinoSelectRaptor;
    private javax.swing.JRadioButton dinoSelectStego;
    private javax.swing.JLabel dinoTypeIMG1;
    private javax.swing.JLabel dinoTypeIMG2;
    private javax.swing.JLabel dinoTypeIMG3;
    private javax.swing.JLabel errorText;
    private javax.swing.JLabel hungerLabel;
    private javax.swing.JLabel hungerStatLabel;
    private javax.swing.JLabel hungerStatLabel1;
    private javax.swing.JLabel hungerStatLabel2;
    private javax.swing.JLabel hungerStatLabel3;
    private javax.swing.JLabel hungerStatLabel4;
    private javax.swing.JTextField inputName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lonelyLabel;
    private javax.swing.JLabel lonelyStatLabel;
    private javax.swing.JLabel saveNameLabel;
    private javax.swing.JSpinner saveSpinner;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel thirstLabel;
    // End of variables declaration//GEN-END:variables
}
