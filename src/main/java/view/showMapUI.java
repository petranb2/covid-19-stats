/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.toedter.calendar.JTextFieldDateEditor;
import controllers.MapUIController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author olia
 */
public class showMapUI extends javax.swing.JFrame {

    private Date startDate = null;
    private Date endDate = null;

    /**
     * Creates new form ArxikoMenu
     */
    public showMapUI() {
        initComponents();
        MapUIController.fillDropDown(countriesChoice);
        Calendar calendar = Calendar.getInstance();

        // Start date
        JTextFieldDateEditor editor1 = (JTextFieldDateEditor) jDateChooser1.getDateEditor();
        editor1.setEditable(false);
        calendar.add(Calendar.YEAR, -1);
        startDate = calendar.getTime();
        jDateChooser1.setDate(startDate);
        jDateChooser1.getDateEditor().addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (e.getPropertyName().equals("date")) {
                startDate = (Date) e.getNewValue();
                if (startDate.after(endDate)) {
                    jDateChooser2.setDate(startDate);
                }
            }
        });

        // End date
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) jDateChooser2.getDateEditor();
        editor2.setEditable(false);
        calendar.add(Calendar.YEAR, 1);
        endDate = calendar.getTime();
        jDateChooser2.setDate(endDate);
        jDateChooser2.getDateEditor().addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (e.getPropertyName().equals("date")) {
                endDate = (Date) e.getNewValue();
                if (endDate.before(startDate)) {
                    jDateChooser1.setDate(endDate);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        dateLabelFormatter1 = new org.jdatepicker.DateLabelFormatter();
        dateComponentFormatter1 = new org.jdatepicker.DateComponentFormatter();
        jPanel1 = new javax.swing.JPanel();
        showMapBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        chooseDateLabel = new java.awt.Label();
        countriesChoice = new java.awt.Choice();
        chooseCountryLbl = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        countriesList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedCountriesList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addCountriesBtn = new javax.swing.JButton();
        removeCountriesBtn = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        menu1.setLabel("File");
        menu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu1ActionPerformed(evt);
            }
        });
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Covid-19-Stats");

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        showMapBtn.setBackground(new java.awt.Color(0, 204, 204));
        showMapBtn.setText("Προβολή σε χάρτη");
        showMapBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMapBtnActionPerformed(evt);
            }
        });

        returnBtn.setBackground(new java.awt.Color(0, 204, 204));
        returnBtn.setText("Επιστροφή στο αρχικό Menu");
        returnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBtnActionPerformed(evt);
            }
        });

        chooseDateLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        chooseDateLabel.setForeground(new java.awt.Color(102, 255, 255));
        chooseDateLabel.setText("Επιλεξτε ημερομηνια");

        countriesChoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                countriesChoiceItemStateChanged(evt);
            }
        });

        chooseCountryLbl.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        chooseCountryLbl.setForeground(new java.awt.Color(102, 255, 255));
        chooseCountryLbl.setText("Επιλεξτε βασικη χωρα");

        label3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label3.setForeground(new java.awt.Color(102, 255, 255));
        label3.setText("Εως");

        label4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label4.setForeground(new java.awt.Color(102, 255, 255));
        label4.setText("Απο");

        label1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label1.setForeground(new java.awt.Color(102, 255, 255));
        label1.setText("Επιλεξτε τις υπολοιπες χωρες");

        jScrollPane1.setViewportView(countriesList);

        jScrollPane2.setViewportView(selectedCountriesList);

        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setText("Επιλέξτε χώρες");

        jLabel2.setForeground(new java.awt.Color(102, 255, 255));
        jLabel2.setText("Χωρες που εχετε επιλεξει");

        addCountriesBtn.setText(">>>");
        addCountriesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCountriesBtnActionPerformed(evt);
            }
        });

        removeCountriesBtn.setText("<<<");
        removeCountriesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCountriesBtnActionPerformed(evt);
            }
        });

        jDateChooser1.setDateFormatString("dd-MM-yyyy");

        jDateChooser2.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chooseCountryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(chooseDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(removeCountriesBtn)
                                            .addComponent(addCountriesBtn)
                                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(countriesChoice, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(49, 49, 49)))
                                    .addComponent(jLabel2)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(returnBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                                .addComponent(showMapBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 40, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chooseCountryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(countriesChoice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chooseDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(addCountriesBtn)
                        .addGap(18, 18, 18)
                        .addComponent(removeCountriesBtn)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)))
                .addComponent(showMapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(returnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );

        chooseDateLabel.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menu1ActionPerformed

    private void showMapBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMapBtnActionPerformed
        if (countriesChoice.getSelectedItem().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Παρακαλώ επιλέξτε χώρα!",
                    "ΣΦΑΛΜΑ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // TODO add your handling code here:
            MapUIController.showMap(countriesChoice.getSelectedItem(), selectedCountriesList, startDate, endDate);
        } catch (IOException ex) {
            Logger.getLogger(showMapUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_showMapBtnActionPerformed

    private void returnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBtnActionPerformed

        new ArxikoMenu().setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_returnBtnActionPerformed

    private void addCountriesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCountriesBtnActionPerformed
        // TODO add your handling code here:
        DefaultListModel listModel = new DefaultListModel();
        ListModel<String> model = selectedCountriesList.getModel();
        if (model.getSize() > 0) {
            //old items
            for (int i = 0; i < selectedCountriesList.getModel().getSize(); i++) {
                listModel.addElement(selectedCountriesList.getModel().getElementAt(i));

            }
        }
        // new items
        for (int i = 0; i < countriesList.getSelectedValuesList().size(); i++) {
            boolean dublicate = false;
            if (model.getSize() > 0) {
                //old items
                for (int j = 0; j < selectedCountriesList.getModel().getSize(); j++) {
                    if (selectedCountriesList.getModel().getElementAt(j).equals(countriesList.getSelectedValuesList().get(i))) {
                        dublicate = true;
                    }
                }
            }
            if (!dublicate) {
                listModel.addElement(countriesList.getSelectedValuesList().get(i));
            }
        }
        selectedCountriesList.setModel(listModel);
    }//GEN-LAST:event_addCountriesBtnActionPerformed

    private void countriesChoiceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_countriesChoiceItemStateChanged
        // TODO add your handling code here:
        MapUIController.empltyList(selectedCountriesList);
        MapUIController.fillCountriesList(evt.getItem().toString(), countriesList);

    }//GEN-LAST:event_countriesChoiceItemStateChanged

    private void removeCountriesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCountriesBtnActionPerformed
        // TODO add your handling code here:
        MapUIController.removeSelectedItemsFromList(selectedCountriesList);

    }//GEN-LAST:event_removeCountriesBtnActionPerformed

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
            java.util.logging.Logger.getLogger(showCountryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(showCountryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(showCountryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(showCountryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new showCountryUI().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCountriesBtn;
    private java.awt.Label chooseCountryLbl;
    private java.awt.Label chooseDateLabel;
    private java.awt.Choice countriesChoice;
    private javax.swing.JList<String> countriesList;
    private org.jdatepicker.DateComponentFormatter dateComponentFormatter1;
    private org.jdatepicker.DateLabelFormatter dateLabelFormatter1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JButton removeCountriesBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JList<String> selectedCountriesList;
    private javax.swing.JButton showMapBtn;
    // End of variables declaration//GEN-END:variables
}
