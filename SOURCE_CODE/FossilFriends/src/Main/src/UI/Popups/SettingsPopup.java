package Main.src.UI.Popups;

import Main.src.Managers.MainManager;

public class SettingsPopup extends javax.swing.JFrame {

    // Current volume and language variables.
    private int currentVol;
    private String currentLang;
    
    // Initialises UI components.
    public SettingsPopup() {
        initComponents();
    }
    
    // Gets the currentVol and currentLang from the MainManager and displays it on the slider and dropdown.
    public void initPop(){
        currentVol = MainManager.getVol();
        volumeSlider.setValue(currentVol);
        currentLang = MainManager.getLang();
        languageDrop.setSelectedItem(currentLang);
        
        switch (MainManager.getLang()){
                case "English":
                    languageLabel.setText("Language: ");
                    volumeLabel.setText("Volume: ");
                    tutorialButton.setText("Open Tutorial");
                    saveButton.setText("Save Settings");
                    discardButton.setText("Discard Settings");
                    break;
                case "Afrikaans":
                    languageLabel.setText("Taal: ");
                    volumeLabel.setText("Volume: ");
                    tutorialButton.setText("Maak tutoriaal oop");
                    saveButton.setText("Stoor instellings");
                    discardButton.setText("Verwerp instellings");
                    break;
                case "Zulu":
                    languageLabel.setText("Ulimi: ");
                    volumeLabel.setText("Ivolumu: ");
                    tutorialButton.setText("Vula isifundo");
                    saveButton.setText("Gcina izilungiselelo");
                    discardButton.setText("Lahla izilungiselelo");
                    break;
            }
    }
    
    // Saves the currentVol.
    private void save(){
        int vol = volumeSlider.getValue();
        String lang = String.valueOf(languageDrop.getSelectedItem());
        MainManager.setSettings(vol, MainManager.getTut(), lang);
        this.dispose();
    }
    
    // Closes this popup without saving.
    private void discard(){
        this.dispose();
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
        volumeLabel = new javax.swing.JLabel();
        volumeSlider = new javax.swing.JSlider();
        tutorialButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        discardButton = new javax.swing.JButton();
        languageDrop = new javax.swing.JComboBox<>();
        languageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(217, 203, 179));
        jPanel1.setForeground(new java.awt.Color(217, 203, 179));

        volumeLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        volumeLabel.setForeground(new java.awt.Color(74, 95, 51));
        volumeLabel.setText("Volume:");

        volumeSlider.setBackground(new java.awt.Color(249, 245, 227));
        volumeSlider.setForeground(new java.awt.Color(74, 95, 51));

        tutorialButton.setBackground(new java.awt.Color(249, 245, 227));
        tutorialButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        tutorialButton.setForeground(new java.awt.Color(74, 95, 51));
        tutorialButton.setText("Open Tutorial");
        tutorialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tutorialButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(249, 245, 227));
        saveButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        saveButton.setForeground(new java.awt.Color(74, 95, 51));
        saveButton.setText("Save Settings");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        discardButton.setBackground(new java.awt.Color(249, 245, 227));
        discardButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        discardButton.setForeground(new java.awt.Color(74, 95, 51));
        discardButton.setText("Discard Settings");
        discardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardButtonActionPerformed(evt);
            }
        });

        languageDrop.setBackground(new java.awt.Color(249, 245, 227));
        languageDrop.setForeground(new java.awt.Color(74, 95, 51));
        languageDrop.setMaximumRowCount(3);
        languageDrop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Afrikaans", "Zulu" }));

        languageLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        languageLabel.setForeground(new java.awt.Color(74, 95, 51));
        languageLabel.setText("Language:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tutorialButton, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(languageLabel)
                            .addComponent(volumeLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(languageDrop, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(volumeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(languageDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(languageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volumeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tutorialButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(discardButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void discardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardButtonActionPerformed
        // TODO add your handling code here:
        discard();
    }//GEN-LAST:event_discardButtonActionPerformed

    private void tutorialButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tutorialButtonActionPerformed
        // TODO add your handling code here:
        int vol = volumeSlider.getValue();
        String lang = String.valueOf(languageDrop.getSelectedItem());
        MainManager.setSettings(vol, MainManager.getTut(), lang);
        MainManager.openPopup(1, this.getLocation().x, this.getLocation().y);
        this.dispose();
    }//GEN-LAST:event_tutorialButtonActionPerformed

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
            java.util.logging.Logger.getLogger(SettingsPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsPopup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton discardButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> languageDrop;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton tutorialButton;
    private javax.swing.JLabel volumeLabel;
    private javax.swing.JSlider volumeSlider;
    // End of variables declaration//GEN-END:variables
}
