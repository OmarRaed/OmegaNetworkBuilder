
package client;

import client.logic.OmegaScriptBuilder;

import java.awt.Dimension;

import java.awt.Toolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author OMAAR
 */
public class main extends java.awt.Frame {

    /** Creates new form main */
    public main() {
        initComponents();
        this.setTitle("Omega Network Builder");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        doneButton = new javax.swing.JButton();
        sizeTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        doneButton.setText("Generate");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        sizeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Console", 1, 24)); // NOI18N
        jLabel1.setText("Omega Network Builder");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Enter N value :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(0, 43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(64, 64, 64)
                        .addComponent(sizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        pack();
    }//GEN-END:initComponents

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        
        try {
            //get entered number
            int size = Integer.parseInt(sizeTextField.getText());
            
            //if the number is not a Power of 2 number, notify user
            if(log2(size) % 1 != 0){
                JOptionPane.showMessageDialog(this, "Please enter a Power of 2 number");
                return;
            }
            
            //export the file
            exportFile(size);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please Enter a valid number");
        }

        
        
    }//GEN-LAST:event_doneButtonActionPerformed

    private void sizeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sizeTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt
            .EventQueue
            .invokeLater(new Runnable() {
                public void run() {
                    new main().setVisible(true);
                }
            });
    }

    /**
     *a method used to export the verilog file
     */
    private void exportFile(int size) {

        //Create JFileChooser Object
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Save");
        //set mode to view files and directories
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //set USER DESKTOP as the opening directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        int returnValue = fileChooser.showOpenDialog(null); //get returned value
        //if returned value equals APPROVE_OPTION
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile(); //get selected file
            String filePath = selectedFile.getAbsolutePath(); //get selected path

            //check if file already exist
            File xmlFile = new File(filePath + ".v");
            if (xmlFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null, //
                                                             "File already exists, Do you want to replace it ?", //
                                                             "Confirm", JOptionPane.YES_NO_OPTION, //
                                                             JOptionPane.QUESTION_MESSAGE);
                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            try {
                //write output verilog file
                OmegaScriptBuilder builder = new OmegaScriptBuilder(size);
                builder.generateScript();

                PrintWriter out = new PrintWriter(filePath + ".v");
                out.println(builder.getScript());
                out.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Unkown Error Occured, Please try again later");
            }
        }
    }

    /**
     *a method used to return the string builder holding the script
     */
    private double log2(int n) {
        return (Math.log(n) / Math.log(2));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton doneButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField sizeTextField;
    // End of variables declaration//GEN-END:variables

}