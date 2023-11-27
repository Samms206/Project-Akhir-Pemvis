package projectakhir;
import controller.koneksi;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import projectakhir.MainFrame.Admin;
import projectakhir.MainFrame.Main;

/**
 *
 * @author macbookpro
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    private ResultSet rs;
    private Statement st;
    private PreparedStatement ps;
    Connection conn = koneksi.Koneksi();
    
    public Login() {
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

        login_pane = new javax.swing.JPanel();
        tf_mail = new javax.swing.JTextField();
        tf_psw = new javax.swing.JPasswordField();
        cb_cek = new javax.swing.JCheckBox();
        gada = new javax.swing.JLabel();
        button_login = new javax.swing.JPanel();
        btn_login = new javax.swing.JLabel();
        btn_back = new javax.swing.JLabel();
        gambarlogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        login_pane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tf_mail.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        tf_mail.setForeground(new java.awt.Color(153, 153, 153));
        tf_mail.setBorder(null);
        login_pane.add(tf_mail, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 289, 220, 38));

        tf_psw.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        tf_psw.setForeground(new java.awt.Color(153, 153, 153));
        tf_psw.setBorder(null);
        login_pane.add(tf_psw, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 348, 220, 40));

        cb_cek.setBackground(new java.awt.Color(255, 255, 255));
        cb_cek.setBorder(null);
        cb_cek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_cekActionPerformed(evt);
            }
        });
        login_pane.add(cb_cek, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 396, -1, -1));

        gada.setFont(new java.awt.Font("Comic Sans MS", 3, 12)); // NOI18N
        gada.setForeground(new java.awt.Color(255, 255, 255));
        gada.setText("show password");
        login_pane.add(gada, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 393, 90, 20));

        button_login.setBackground(new java.awt.Color(255, 255, 255));
        button_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_loginMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button_loginMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button_loginMouseEntered(evt);
            }
        });
        button_login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_login.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        btn_login.setForeground(new java.awt.Color(1, 193, 198));
        btn_login.setText("Login");
        btn_login.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        button_login.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 3, -1, -1));

        login_pane.add(button_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 453, 350, 40));

        btn_back.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        btn_back.setForeground(new java.awt.Color(255, 255, 255));
        btn_back.setText("<");
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });
        login_pane.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        gambarlogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/masuk_libeeo.jpg"))); // NOI18N
        login_pane.add(gambarlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(login_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(login_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cb_cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_cekActionPerformed
        // TODO add your handling code here:
        if (cb_cek.isSelected()) {
            tf_psw.setEchoChar((char)0);
        }else{
            tf_psw.setEchoChar('*');
        }
    }//GEN-LAST:event_cb_cekActionPerformed

    private void button_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_loginMouseClicked
        // TODO add your handling code here:
        button_login.setBackground(Color.white);
        btn_login.setForeground(Color.lightGray);
        String username, password;
        username = tf_mail.getText();
        password = tf_psw.getText();
        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "form masih kosong!");
        }else{
            try {
                String query = "SELECT * FROM user WHERE email=? AND password=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                int role = rs.getInt("role");
                if (role == 1) {
                    JOptionPane.showMessageDialog(this, "Login Berhasil sebagai Admin", "Login", JOptionPane.DEFAULT_OPTION);
                    Admin adminPage = new Admin();
                    adminPage.setVisible(true);
                    this.dispose();
                } else if (role == 2) {
                    JOptionPane.showMessageDialog(this, "Login Berhasil sebagai User", "Login", JOptionPane.DEFAULT_OPTION);
                    Main userPage = new Main(username, password);
                    userPage.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Role tidak valid", "Login", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email atau Password Salah!!!", "Login", JOptionPane.ERROR_MESSAGE);
            }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "fail " + e.getMessage());
            }
        }
        
        
    }//GEN-LAST:event_button_loginMouseClicked

    private void button_loginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_loginMouseEntered
        // TODO add your handling code here:
        button_login.setBackground(new Color(1,193,198));
        btn_login.setForeground(Color.white);
    }//GEN-LAST:event_button_loginMouseEntered

    private void button_loginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_loginMouseExited
        // TODO add your handling code here:
        button_login.setBackground(Color.white);
        btn_login.setForeground(new Color(1,193,198));
    }//GEN-LAST:event_button_loginMouseExited

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
        new FrontView().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_backMouseClicked

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_back;
    private javax.swing.JLabel btn_login;
    private javax.swing.JPanel button_login;
    private javax.swing.JCheckBox cb_cek;
    private javax.swing.JLabel gada;
    private javax.swing.JLabel gambarlogin;
    private javax.swing.JPanel login_pane;
    private javax.swing.JTextField tf_mail;
    private javax.swing.JPasswordField tf_psw;
    // End of variables declaration//GEN-END:variables
}
