/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectakhir;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author macbookpro
 */
public class FrontView extends javax.swing.JFrame {

    /**
     * Creates new form FrontView
     */
    public FrontView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main_pane = new javax.swing.JPanel();
        tombol_login = new javax.swing.JLabel();
        tombol_signup = new javax.swing.JLabel();
        gambarAwal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        main_pane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tombol_login.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        tombol_login.setForeground(new java.awt.Color(1, 193, 198));
        tombol_login.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tombol_login.setText("Login");
        tombol_login.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        tombol_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombol_loginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombol_loginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tombol_loginMouseExited(evt);
            }
        });
        main_pane.add(tombol_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 190, 40));

        tombol_signup.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        tombol_signup.setForeground(new java.awt.Color(187, 222, 148));
        tombol_signup.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tombol_signup.setText("Register");
        tombol_signup.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        tombol_signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tombol_signupMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tombol_signupMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombol_signupMouseEntered(evt);
            }
        });
        main_pane.add(tombol_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 190, 40));

        gambarAwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/front-view.jpg"))); // NOI18N
        main_pane.add(gambarAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(main_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(main_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tombol_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombol_loginMouseClicked
        // TODO add your handling code here
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tombol_loginMouseClicked

    private void tombol_loginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombol_loginMouseEntered
        // TODO add your handling code here:
        tombol_login.setForeground(Color.white);
    }//GEN-LAST:event_tombol_loginMouseEntered

    private void tombol_loginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombol_loginMouseExited
        // TODO add your handling code here:
        tombol_login.setForeground(new Color(1,193,198));
    }//GEN-LAST:event_tombol_loginMouseExited

    private void tombol_signupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombol_signupMouseClicked
        // TODO add your handling code here:
        new Signup().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tombol_signupMouseClicked

    private void tombol_signupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombol_signupMouseEntered
        // TODO add your handling code here:
        tombol_signup.setForeground(new Color(1,193,198));
    }//GEN-LAST:event_tombol_signupMouseEntered

    private void tombol_signupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tombol_signupMouseExited
        // TODO add your handling code here:
        tombol_signup.setForeground(new Color(187,222,148));
    }//GEN-LAST:event_tombol_signupMouseExited

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
            java.util.logging.Logger.getLogger(FrontView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrontView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrontView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrontView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel gambarAwal;
    private javax.swing.JPanel main_pane;
    private javax.swing.JLabel tombol_login;
    private javax.swing.JLabel tombol_signup;
    // End of variables declaration//GEN-END:variables
}