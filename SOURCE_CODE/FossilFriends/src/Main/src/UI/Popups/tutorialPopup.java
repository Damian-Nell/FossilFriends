/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Main.src.UI.Popups;

import Main.src.Managers.MainManager;

/**
 *
 * @author damia
 */
public class tutorialPopup extends javax.swing.JFrame {

    /*
        * All the English tutorial texts.
        * All the Afrikaans translations.
        * And all the Zulu translations.
        *
        * Counter to determine which text to show.
        * x, y - to determine where the tutorial popup will move to.
        * lang - the current language.
    */
    private String EngText1 = "Welcome to FossilFriends!\nThis is a pet simulation game developed\nfor my Grade 12 IT Practical Assessment Task.\nI will now show you how to play.";
    private String EngText2 = "Up here is the information header.\nIt will show you the dinosaur’s name, age, mood, happiness,\nhunger, thirst, and cleanliness.";
    private String EngText3 = "The age will increase once per day.\nOver time, your other stats will decrease.\nMood is determined by how often you visit your dinosaur.\nHappiness is determined by the average of all the other stats.";
    private String EngText4 = "Here’s the settings button\nif you ever need to open this tutorial again or to change the volume.";
    private String EngText5 = "Here are the minigame buttons:\nHunger, Thirst, and Cleanliness.\nEach minigame has a cooldown of 60 seconds.\nI will explain each game now.";
    private String EngText6 = "Hunger:\nYou control your dinosaur and will have to catch falling apples in its mouth.";
    private String EngText7 = "Thirst:\nYou control a water bottle that periodically drops a water droplet.\nYour dinosaur moves side to side, and you will have to time it correctly.";
    private String EngText8 = "Clean:\nYou control a sponge, and you must scrub off dirt spots.";
    private String EngText9 = "You can pet your dinosaur to increase its mood,\nbut be careful—as you may overwhelm your dinosaur.";
    private String EngText10 = "Here’s the home button if you would\nlike to go change your dinosaur.";
    private String EngText11 = "You may leave the game at any moment—don’t worry,\nyour dinosaurs are saved.\nBut be sure to come back, otherwise your dinosaur might die!";
    private String EngText12 = "I hope you enjoy :)";

    private String AfrText1 = "Welkom by FossilFriends!\nHierdie is 'n troeteldier-simulasiespeletjie wat\nek ontwikkel het vir my Graad 12 IT Praktiese Assesseringstaak.\nEk gaan jou nou wys hoe om te speel.";
    private String AfrText2 = "Hier bo is die inligtingkop.\nDit wys vir jou die dinosourus se naam, ouderdom, bui, geluk,\nhonger, dors en netheid.";
    private String AfrText3 = "Die ouderdom verhoog een keer per dag.\nMet verloop van tyd sal jou ander statistieke afneem.\nBui word bepaal deur hoe gereeld jy jou dinosourus besoek.\nGeluk word bepaal deur die gemiddelde van al die ander statistieke.";
    private String AfrText4 = "Hier is die instellingsknoppie\nas jy weer hierdie tutoriaal wil oopmaak of die volume wil verander.";
    private String AfrText5 = "Hier is die minispeletjie-knoppies:\nHonger, Dors, en Netheid.\nElke minispeletjie het ’n afkoeltyd van 60 sekondes.\nEk sal nou elke speletjie verduidelik.";
    private String AfrText6 = "Honger:\nJy beheer jou dinosourus en moet vallende appels met sy mond vang.";
    private String AfrText7 = "Dors:\nJy beheer ’n waterbottel wat periodiek ’n waterdruppel laat val.\nJou dinosourus beweeg heen en weer, en jy moet dit reg tyd.";
    private String AfrText8 = "Netheid:\nJy beheer ’n spons, en jy moet vuil kolle afskrop. ";
    private String AfrText9 = "Jy kan jou dinosourus streel om sy bui te verbeter,\nmaar wees versigtig—jy kan hom dalk oorweldig.";
    private String AfrText10 = "Hier is die tuisknoppie as jy jou dinosourus wil verander.";
    private String AfrText11 = "Jy kan die speletjie enige tyd verlaat—moenie bekommer nie,\njou dinosourusse word gestoor.\nMaar wees seker om terug te kom, anders kan jou dinosourus dalk sterf!";
    private String AfrText12 = "Ek hoop jy geniet dit :)";
    
    private String ZulText1 = "Siyakwamukela ku-FossilFriends!\nLo ngumdlalo wokulingisa izilwane ezifuywayo owenziwe\nngomsebenzi wami wokuhlola we-IT weBanga le-12.\nManje ngizokukhombisa ukuthi ungawudlala kanjani.";
    private String ZulText2 = "Phezulu lapha kukhona isihloko solwazi.\nSizokukhombisa igama ledinosaur, iminyaka, isimo sengqondo,\ninjabulo, indlala, ukoma, nokuhlanzeka.";
    private String ZulText3 = "Iminyaka izokhula kanye ngosuku.\nNgokuhamba kwesikhathi, ezinye izibalo zizokwehla.\nIsimo sengqondo sinqunywa ukuthi uvakashela kangaki idinosaur yakho.\nInjabulo inqunywa isilinganiso sazo zonke ezinye izibalo.";
    private String ZulText4 = "Nansi inkinobho yezilungiselelo\numa udinga ukuvula lesi sifundo futhi noma ushintshe ivolumu.";
    private String ZulText5 = "Nazi izinkinobho zemidlalo emincane:\nIndlala, Ukoma, nokuhlanzeka.\nUmdlalo ngamunye unemizuzwana engama-60\nyokuphola ngaphambi kokuthi uwudlale futhi.\nManje ngizochaza umdlalo ngamunye.";
    private String ZulText6 = "Indlala:\nUlawula idinosaur yakho futhi kufanele\nubambe ama-apula awelayo ngomlomo wayo.";
    private String ZulText7 = "Ukoma:\nUlawula ibhodlela lamanzi eliphonsa amaconsi amanzi ngezikhathi ezithile.\nIdinosaur yakho iyahamba isuka ohlangothini olulodwa iye kolunye,\nfuthi kufanele ulinganise kahle isikhathi.";
    private String ZulText8 = "Ukuhlanzeka:\nUlawula isiponji, kufanele ususe amabala angcolile.";
    private String ZulText9 = "Ungayithinta idinosaur yakho ukuyenza ijabule,\nkodwa qaphela—ungayigcwala kakhulu.";
    private String ZulText10 = "Nansi inkinobho yasekhaya uma ungathanda ukushintsha idinosaur yakho.";
    private String ZulText11 = "Ungaphuma noma nini emdlalweni—ungakhathazeki,\nama-dinosaur akho agcinwa.Kodwa qiniseka ukuthi uyabuya,\numa kungenjalo idinosaur yakho ingafa!";
    private String ZulText12 = "Ngiyethemba uzokujabulela :)";

    private int counter = 0;
    private int x, y;

    private String lang;

    /*
     * Creates new form tutorialPopup
     */
    public tutorialPopup() {
        initComponents();
    }

    //initialises the first popup with correct language.
    public void initPop(int x, int y) {
        lang = MainManager.getLang();
        switch (lang) {
            case "English":
                tutText.setText(EngText1);
                break;
            case "Afrikaans":
                tutText.setText(AfrText1);
                break;
            case "Zulu":
                tutText.setText(ZulText1);
                break;
            default:
                MainManager.setSettings(MainManager.getVol(), MainManager.getTut(), "English");
                this.dispose();
                break;
        }
        this.x = x;
        this.y = y;
    }

    //gets the next text in the popup with the correct language.
    private void nextText() {
        if (lang.equals("English")) {
            switch (counter) {
                case 0:
                    tutText.setText(EngText2);
                    this.setLocation(x, y - 40);
                    break;
                case 1:
                    tutText.setText(EngText3);
                    break;
                case 2:
                    tutText.setText(EngText4);
                    this.setLocation(x + 300, y);
                    break;
                case 3:
                    tutText.setText(EngText5);
                    this.setLocation(x + 500, y + 200);
                    break;
                case 4:
                    tutText.setText(EngText6);
                    break;
                case 5:
                    tutText.setText(EngText7);
                    break;
                case 6:
                    tutText.setText(EngText8);
                    break;
                case 7:
                    tutText.setText(EngText9);
                    this.setLocation(x, y + 300);
                    break;
                case 8:
                    tutText.setText(EngText10);
                    this.setLocation(x, y + 450);
                    break;
                case 9:
                    tutText.setText(EngText11);
                    this.setLocation(x, y);
                    break;
                case 10:
                    tutText.setText(EngText12);
                    break;
                case 11:
                    MainManager.setSettings(MainManager.getVol(), true, MainManager.getLang());
                    this.dispose();
                    break;
                default:
                    break;
            }
        } else if (lang.equals("Afrikaans")){
            switch (counter) {
                case 0:
                    tutText.setText(AfrText2);
                    this.setLocation(x, y - 40);
                    break;
                case 1:
                    tutText.setText(AfrText3);
                    break;
                case 2:
                    tutText.setText(AfrText4);
                    this.setLocation(x + 300, y);
                    break;
                case 3:
                    tutText.setText(AfrText5);
                    this.setLocation(x + 500, y + 200);
                    break;
                case 4:
                    tutText.setText(AfrText6);
                    break;
                case 5:
                    tutText.setText(AfrText7);
                    break;
                case 6:
                    tutText.setText(AfrText8);
                    break;
                case 7:
                    tutText.setText(AfrText9);
                    this.setLocation(x, y + 300);
                    break;
                case 8:
                    tutText.setText(AfrText10);
                    this.setLocation(x, y + 450);
                    break;
                case 9:
                    tutText.setText(AfrText11);
                    this.setLocation(x, y);
                    break;
                case 10:
                    tutText.setText(AfrText12);
                    break;
                case 11:
                    MainManager.setSettings(MainManager.getVol(), true, MainManager.getLang());
                    this.dispose();
                    break;
                default:
                    break;
            }
        } else if (lang.equals("Zulu")){
            switch (counter) {
                case 0:
                    tutText.setText(ZulText2);
                    this.setLocation(x, y - 40);
                    break;
                case 1:
                    tutText.setText(ZulText3);
                    break;
                case 2:
                    tutText.setText(ZulText4);
                    this.setLocation(x + 300, y);
                    break;
                case 3:
                    tutText.setText(ZulText5);
                    this.setLocation(x + 500, y + 200);
                    break;
                case 4:
                    tutText.setText(ZulText6);
                    break;
                case 5:
                    tutText.setText(ZulText7);
                    break;
                case 6:
                    tutText.setText(ZulText8);
                    break;
                case 7:
                    tutText.setText(ZulText9);
                    this.setLocation(x, y + 300);
                    break;
                case 8:
                    tutText.setText(ZulText10);
                    this.setLocation(x, y + 450);
                    break;
                case 9:
                    tutText.setText(ZulText11);
                    this.setLocation(x, y);
                    break;
                case 10:
                    tutText.setText(ZulText12);
                    break;
                case 11:
                    MainManager.setSettings(MainManager.getVol(), true, MainManager.getLang());
                    this.dispose();
                    break;
                default:
                    break;
            }
        }

        counter++;
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
        nextButton = new javax.swing.JButton();
        tutText = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(217, 203, 179));
        jPanel1.setForeground(new java.awt.Color(217, 203, 179));
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 400));

        nextButton.setBackground(new java.awt.Color(249, 245, 227));
        nextButton.setForeground(new java.awt.Color(74, 95, 51));
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        tutText.setBackground(new java.awt.Color(249, 245, 227));
        tutText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tutText.setEditable(false);
        tutText.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        tutText.setForeground(new java.awt.Color(74, 95, 51));
        tutText.setMaximumSize(new java.awt.Dimension(200, 160));
        tutText.setMinimumSize(new java.awt.Dimension(1, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nextButton)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tutText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tutText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nextButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        nextText();
    }//GEN-LAST:event_nextButtonActionPerformed

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
            java.util.logging.Logger.getLogger(tutorialPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tutorialPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tutorialPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tutorialPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tutorialPopup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton nextButton;
    private java.awt.TextArea tutText;
    // End of variables declaration//GEN-END:variables
}
