/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectakhir.MainFrame;

import controller.koneksi;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import projectakhir.FrontView;

/**
 *
 * @author macbookpro
 */
public class Admin extends javax.swing.JFrame {

    /**
     * Creates new form Admin
     */
    
    private ResultSet rs;
    private Statement st;
    private PreparedStatement ps;
    Connection conn = koneksi.Koneksi();
    
    String emailParam, passParam;
    
    DefaultTableModel model_anggota = new DefaultTableModel();
    DefaultTableModel model_buku = new DefaultTableModel();
    DefaultTableModel model_pinjam = new DefaultTableModel();
    DefaultTableModel model_kembali = new DefaultTableModel();
    DefaultTableModel model_statuspp = new DefaultTableModel();
    
    String id_buku = "0", id_user = "0";
    
    public Admin(String email, String pass) {
        initComponents();
        emailParam = email;
        passParam = pass;
        //
        warna();
        refresh();
        enable_false();
        show_datapeminjaman();
        show_datapengembalian();
        show_status_perpanjangan();
        show_dataAdmin();
        btn_setujui.setEnabled(false);
        btn_tolak.setEnabled(false);
        styleTable(tabel_buku);
        styleTable(tbl_peminjaman);
        styleTable(tbl_pengembalian);
        styleTable(tbl_statusperpanjangan);
        styleTable(table_anggota);
    }

    private Admin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    void styleTable(JTable tblTest){
        tblTest.setShowHorizontalLines(true);
        tblTest.setGridColor(new Color(230,230,230));
        tblTest.setBackground(Color.WHITE);
        tblTest.setRowHeight(40);
        tblTest.setOpaque(false);
        tblTest.getTableHeader().setReorderingAllowed(false);
        tblTest.getTableHeader().setBackground(Color.white);
        tblTest.getTableHeader().setDefaultRenderer(new HeaderColor());
    }
    public class HeaderColor extends DefaultTableCellRenderer {

        public HeaderColor() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            setBackground(new java.awt.Color(255,255,255));
            setBorder(noFocusBorder);
            return this;
        }

    }
    void refresh(){
        show_buku();
        show_user();
    }
    void enable_false(){
        tf_namaUser.setEnabled(false);
        tf_nimUser.setEnabled(false);
        tf_emailUser.setEnabled(false);
        tf_nohpUser.setEnabled(false);
        tf_passUser.setEnabled(false);
        cmb_role.setEnabled(false);
        tf_user.setEnabled(false);
        tf_email.setEnabled(false);
        tf_pass.setEnabled(false);
        tf_pass2.setEnabled(false);
    }
    void enable_true(){
        tf_namaUser.setEnabled(true);
        tf_nimUser.setEnabled(true);
        tf_emailUser.setEnabled(true);
        tf_nohpUser.setEnabled(true);
        tf_passUser.setEnabled(true);
        cmb_role.setEnabled(true);
        tf_user.setEnabled(true);
        tf_email.setEnabled(true);
        tf_pass.setEnabled(true);
        tf_pass2.setEnabled(true);
    }
    void show_buku(){
        Object[] kolom = {
            "ID", "Buku", "Stok", "Penulis", "Penerbit"
        };
        model_buku = new DefaultTableModel(null, kolom);
        tabel_buku.setModel(model_buku);
        try {
          st = conn.createStatement();
          rs = st.executeQuery("SELECT * FROM buku");
          while (rs.next()) {
            Object[] data = {
              rs.getString("id_buku"),
              rs.getString("namabuku"),
              rs.getString("stok"),
              rs.getString("penulis"),
              rs.getString("penerbit")
            };
              model_buku.addRow(data);
          }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void show_user(){
        Object[] kolom = {
            "ID", "Nama", "NIM", "Email", "No HP", "Password", "Role"
        };
        model_anggota = new DefaultTableModel(null, kolom);
        table_anggota.setModel(model_anggota);
        try {
          st = conn.createStatement();
          rs = st.executeQuery("SELECT * FROM user");
          while (rs.next()) {
            Object[] data = {
              rs.getString("id_user"),
              rs.getString("username"),
              rs.getString("nim"),
              rs.getString("email"),
              rs.getString("nohp"),
              rs.getString("password"),
              rs.getString("role")
            };
              model_anggota.addRow(data);
          }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void show_datapeminjaman(){
        Object[] kolom = {
            "ID", "Username", "Buku", "Qty", "Tgl Pinjam", "Tgl Tenggat"
        };
        model_pinjam = new DefaultTableModel(null, kolom);
        tbl_peminjaman.setModel(model_pinjam);
        tbl_peminjaman.setModel(model_pinjam);
        try {
          st = conn.createStatement();
          rs = st.executeQuery("SELECT "
                  + "id_trans, user.username, buku.namabuku, transaksi.qty, transaksi.tgl_pinjam, transaksi.tgl_tenggat, transaksi.tgl_kembali, transaksi.denda "
                  + "FROM `transaksi`, `buku`, `user` "
                  + "WHERE user.id_user = transaksi.id_user "
                  + "AND buku.id_buku = transaksi.id_buku "
                  + "AND transaksi.tgl_kembali IS NULL");
          while (rs.next()) {
            Object[] data = {
              rs.getString("id_trans"),
              rs.getString("username"),
              rs.getString("namabuku"),
              rs.getString("qty"),
              rs.getString("tgl_pinjam"),
              rs.getString("tgl_tenggat")
            };
              model_pinjam.addRow(data);
          }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void show_datapengembalian(){
        Object[] kolom = {
            "ID", "Username", "Buku", "Qty", "Tgl Pinjam", "Tgl Tenggat", "Tgl Kembali", "Denda", "Status"
        };
        model_kembali = new DefaultTableModel(null, kolom);
        tbl_pengembalian.setModel(model_kembali);
        tbl_pengembalian.setModel(model_kembali);
        try {
          st = conn.createStatement();
          rs = st.executeQuery("SELECT "
                  + "id_trans, user.username, buku.namabuku, transaksi.qty, transaksi.tgl_pinjam, transaksi.tgl_tenggat, transaksi.tgl_kembali, transaksi.denda, transaksi.status "
                  + "FROM `transaksi`, `buku`, `user` "
                  + "WHERE user.id_user = transaksi.id_user "
                  + "AND buku.id_buku = transaksi.id_buku "
                  + "AND transaksi.tgl_kembali IS NOT NULL");
          while (rs.next()) {
            Object[] data = {
              rs.getString("id_trans"),
              rs.getString("username"),
              rs.getString("namabuku"),
              rs.getString("qty"),
              rs.getString("tgl_pinjam"),
              rs.getString("tgl_tenggat"),
              rs.getString("tgl_kembali"),
              rs.getString("denda"),
              rs.getString("status")
            };
              model_kembali.addRow(data);
          }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void show_status_perpanjangan(){
        Object[] kolom = {
            "ID", "Username", "Buku", "Qty", "Tgl Pinjam", "Tgl Tenggat", "Tgl Perpanjangan", "Status"
        };
        model_statuspp = new DefaultTableModel(null, kolom);
        tbl_statusperpanjangan.setModel(model_statuspp);
        tbl_statusperpanjangan.setModel(model_statuspp);
        try {
          st = conn.createStatement();
          rs = st.executeQuery("SELECT perpanjangan.id_perpanjangan as id, user.username, buku.namabuku, transaksi.qty, transaksi.tgl_pinjam, perpanjangan.tgl_tenggat_lama, perpanjangan.tgl_perpanjangan, perpanjangan.status FROM perpanjangan,transaksi,buku,user where perpanjangan.id_trans = transaksi.id_trans AND transaksi.id_user = user.id_user AND transaksi.id_buku = buku.id_buku");
          while (rs.next()) {
            Object[] data = {
              rs.getString("id"),
              rs.getString("username"),
              rs.getString("namabuku"),
              rs.getString("qty"),
              rs.getString("tgl_pinjam"),
              rs.getString("tgl_tenggat_lama"),
              rs.getString("tgl_perpanjangan"),
              rs.getString("status")
            };
              model_statuspp.addRow(data);
          }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void show_dataAdmin(){
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT*FROM user WHERE email='"+emailParam+"' AND password='"+passParam+"' AND role=1");
            if (rs.next()) {
                tf_user.setText(rs.getString("username"));
                tf_email.setText(rs.getString("email"));
                tf_pass2.setText(rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
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

        MainPanel = new javax.swing.JPanel();
        kiri = new javax.swing.JPanel();
        btnProfile1 = new javax.swing.JLabel();
        btnAnggota = new javax.swing.JLabel();
        btnLibrary = new javax.swing.JLabel();
        btnTransaksi = new javax.swing.JLabel();
        btnApprove = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        kananpane = new javax.swing.JPanel();
        gambar = new javax.swing.JLabel();
        Profilepane = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tf_user = new javax.swing.JTextField();
        tf_email = new javax.swing.JTextField();
        tf_pass2 = new javax.swing.JTextField();
        btn_ubah = new javax.swing.JToggleButton();
        jLabel32 = new javax.swing.JLabel();
        input = new javax.swing.JLabel();
        tf_pass = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        Anggotapane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_anggota = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tf_nimUser = new javax.swing.JTextField();
        tf_namaUser = new javax.swing.JTextField();
        tf_emailUser = new javax.swing.JTextField();
        tf_passUser = new javax.swing.JTextField();
        tf_nohpUser = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        btn_tambahUser = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cmb_role = new javax.swing.JComboBox<>();
        Daftarbuku = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_buku = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        namabuku = new javax.swing.JTextField();
        pengarangbuku = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        penerbitbuku = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        stokbuku = new javax.swing.JTextField();
        jToggleButton10 = new javax.swing.JToggleButton();
        btn_tambahbuku = new javax.swing.JToggleButton();
        jToggleButton12 = new javax.swing.JToggleButton();
        jToggleButton13 = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        thnterbit = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        DataTransaksi = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_peminjaman = new javax.swing.JTable();
        jTextField15 = new javax.swing.JTextField();
        jToggleButton8 = new javax.swing.JToggleButton();
        refreshbutton2 = new javax.swing.JToggleButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_pengembalian = new javax.swing.JTable();
        jTextField16 = new javax.swing.JTextField();
        jToggleButton19 = new javax.swing.JToggleButton();
        jLabel24 = new javax.swing.JLabel();
        refreshbutton3 = new javax.swing.JToggleButton();
        jLabel30 = new javax.swing.JLabel();
        Dataperpanjangan = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_statusperpanjangan = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        tf_idpp = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        tf_namapp = new javax.swing.JTextField();
        tf_bukupp = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        tf_jumlahpp = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        btn_setujui = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        tf_tglpinjampp = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        tf_tenggatpp = new javax.swing.JTextField();
        btn_tolak = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        tf_tglpp = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kiri.setPreferredSize(new java.awt.Dimension(230, 600));
        kiri.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnProfile1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        btnProfile1.setForeground(new java.awt.Color(255, 255, 255));
        btnProfile1.setText("I");
        btnProfile1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfile1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProfile1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProfile1MouseExited(evt);
            }
        });
        kiri.add(btnProfile1, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 220, 223, 40));

        btnAnggota.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        btnAnggota.setForeground(new java.awt.Color(255, 255, 255));
        btnAnggota.setText("I");
        btnAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnggotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAnggotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAnggotaMouseExited(evt);
            }
        });
        kiri.add(btnAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 278, 223, 40));

        btnLibrary.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        btnLibrary.setForeground(new java.awt.Color(255, 255, 255));
        btnLibrary.setText("I");
        btnLibrary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLibraryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLibraryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLibraryMouseExited(evt);
            }
        });
        kiri.add(btnLibrary, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 334, 223, 40));

        btnTransaksi.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        btnTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaksi.setText("I");
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseEntered(evt);
            }
        });
        kiri.add(btnTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 393, 223, 40));

        btnApprove.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        btnApprove.setForeground(new java.awt.Color(255, 255, 255));
        btnApprove.setText("I");
        btnApprove.setEnabled(false);
        btnApprove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnApproveMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnApproveMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnApproveMouseEntered(evt);
            }
        });
        kiri.add(btnApprove, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 451, 223, 40));

        btnLogout.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("I");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });
        kiri.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 520, 223, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/kiri_admin.jpg"))); // NOI18N
        kiri.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 600));

        MainPanel.add(kiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        kananpane.setBackground(new java.awt.Color(255, 255, 255));
        kananpane.setLayout(new java.awt.CardLayout());
        kananpane.add(gambar, "card2");

        Profilepane.setBackground(new java.awt.Color(255, 255, 255));
        Profilepane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Profile_____");
        Profilepane.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 40));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Ulangi Password");
        Profilepane.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Username");
        Profilepane.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("E-mail");
        Profilepane.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Password");
        Profilepane.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));
        Profilepane.add(tf_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 570, 30));
        Profilepane.add(tf_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 570, 30));

        tf_pass2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_pass2ActionPerformed(evt);
            }
        });
        Profilepane.add(tf_pass2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 570, 30));

        btn_ubah.setBackground(new java.awt.Color(255, 255, 255));
        btn_ubah.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ubah.setText("Update");
        btn_ubah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_ubahMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn_ubahMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ubahMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ubahMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ubahMouseEntered(evt);
            }
        });
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        Profilepane.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, 130, 40));

        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel32MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel32MouseExited(evt);
            }
        });
        Profilepane.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 50, 50));

        input.setForeground(new java.awt.Color(255, 255, 255));
        input.setText("jLabel11");
        Profilepane.add(input, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, -1, -1));

        tf_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_passActionPerformed(evt);
            }
        });
        Profilepane.add(tf_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 570, 30));
        Profilepane.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Batal");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        Profilepane.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 290, 110, 40));

        kananpane.add(Profilepane, "card3");

        Anggotapane.setBackground(new java.awt.Color(255, 255, 255));
        Anggotapane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_anggota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_anggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_anggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_anggota);

        Anggotapane.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 400, 420));

        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setText("Cari Anggota...");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        Anggotapane.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 400, 30));

        jLabel19.setText("Nama");
        Anggotapane.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 185, 50, -1));

        jLabel20.setText("Email");
        Anggotapane.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 225, 50, -1));

        jLabel21.setText("Password");
        Anggotapane.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 265, 70, -1));

        jLabel22.setText("NoHP");
        Anggotapane.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 305, 50, -1));
        Anggotapane.add(tf_nimUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 340, 270, 30));
        Anggotapane.add(tf_namaUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 270, 30));
        Anggotapane.add(tf_emailUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 270, 30));
        Anggotapane.add(tf_passUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 260, 270, 30));

        tf_nohpUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_nohpUserKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_nohpUserKeyReleased(evt);
            }
        });
        Anggotapane.add(tf_nohpUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 300, 270, 30));

        jLabel23.setText("NIM");
        Anggotapane.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 345, 50, -1));

        jToggleButton1.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setText("Hapus");
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jToggleButton1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseReleased(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        Anggotapane.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 420, 90, 40));

        jToggleButton3.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton3.setText("cetak");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        Anggotapane.add(jToggleButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, -1, 40));

        jToggleButton4.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton4.setText("Update");
        jToggleButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jToggleButton4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jToggleButton4MouseReleased(evt);
            }
        });
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });
        Anggotapane.add(jToggleButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, -1, 40));

        btn_tambahUser.setBackground(new java.awt.Color(255, 255, 255));
        btn_tambahUser.setText("Tambah");
        btn_tambahUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_tambahUserMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn_tambahUserMouseReleased(evt);
            }
        });
        btn_tambahUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahUserActionPerformed(evt);
            }
        });
        Anggotapane.add(btn_tambahUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 100, 40));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Anggotapane.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 330, 40));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Anggota_____");
        Anggotapane.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 40));
        Anggotapane.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel31.setText("Role");
        Anggotapane.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 380, 50, -1));

        cmb_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--pilh role--", "User", "Admin" }));
        Anggotapane.add(cmb_role, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 380, 270, -1));

        kananpane.add(Anggotapane, "card4");

        Daftarbuku.setBackground(new java.awt.Color(255, 255, 255));
        Daftarbuku.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_buku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_buku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_bukuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_buku);

        Daftarbuku.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 400, 420));

        jTextField7.setForeground(new java.awt.Color(102, 102, 102));
        jTextField7.setText("Cari Buku...");
        jTextField7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField7MouseClicked(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });
        Daftarbuku.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 400, 30));
        Daftarbuku.add(namabuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 260, 30));
        Daftarbuku.add(pengarangbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 220, 260, 30));

        jLabel25.setText("Nama");
        Daftarbuku.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 185, -1, -1));

        jLabel26.setText("Penulis");
        Daftarbuku.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 225, -1, -1));

        jLabel27.setText("Penerbit");
        Daftarbuku.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 265, -1, -1));
        Daftarbuku.add(penerbitbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 260, 30));

        jLabel28.setText("Thn Terbit");
        Daftarbuku.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 305, -1, -1));

        jLabel29.setText("Stok");
        Daftarbuku.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 345, -1, -1));

        stokbuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stokbukuKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stokbukuKeyTyped(evt);
            }
        });
        Daftarbuku.add(stokbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, 260, 30));

        jToggleButton10.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton10.setText("Cetak");
        jToggleButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton10ActionPerformed(evt);
            }
        });
        Daftarbuku.add(jToggleButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, -1, 40));

        btn_tambahbuku.setBackground(new java.awt.Color(255, 255, 255));
        btn_tambahbuku.setText("Tambah");
        btn_tambahbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahbukuActionPerformed(evt);
            }
        });
        Daftarbuku.add(btn_tambahbuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, 100, 40));

        jToggleButton12.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton12.setText("Ubah");
        jToggleButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton12ActionPerformed(evt);
            }
        });
        Daftarbuku.add(jToggleButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, 80, 40));

        jToggleButton13.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton13.setText("Hapus");
        jToggleButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton13ActionPerformed(evt);
            }
        });
        Daftarbuku.add(jToggleButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 380, 90, 40));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        Daftarbuku.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 330, 40));

        thnterbit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Pilih Tahun---", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1900-an", " " }));
        Daftarbuku.add(thnterbit, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 130, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Buku_____");
        Daftarbuku.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 40));
        Daftarbuku.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        kananpane.add(Daftarbuku, "card7");

        DataTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        DataTransaksi.setLayout(new java.awt.CardLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_peminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_peminjamanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_peminjaman);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 730, 430));

        jTextField15.setForeground(new java.awt.Color(102, 102, 102));
        jTextField15.setText("Cari Data Pengembalian (nama, nim atau tanggal)");
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField15MouseClicked(evt);
            }
        });
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 340, 30));

        jToggleButton8.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton8.setText("Cetak");
        jToggleButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 30));

        refreshbutton2.setBackground(new java.awt.Color(255, 255, 255));
        refreshbutton2.setText("Cari");
        refreshbutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbutton2ActionPerformed(evt);
            }
        });
        jPanel1.add(refreshbutton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 90, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Data Peminjaman_____");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 320, 40));
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, -1, -1));

        jTabbedPane1.addTab("Peminjaman", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_pengembalian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_pengembalian.setGridColor(new java.awt.Color(204, 204, 204));
        tbl_pengembalian.setSelectionBackground(new java.awt.Color(39, 51, 60));
        tbl_pengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pengembalianMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_pengembalian);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 730, 430));

        jTextField16.setForeground(new java.awt.Color(102, 102, 102));
        jTextField16.setText("Cari Data Pengembalian (nama, nim atau tanggal)");
        jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField16MouseClicked(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 340, 30));

        jToggleButton19.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton19.setText("Cetak");
        jToggleButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton19ActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setText("Data Pengembalian_____");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 320, 40));

        refreshbutton3.setBackground(new java.awt.Color(255, 255, 255));
        refreshbutton3.setText("cari");
        refreshbutton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbutton3ActionPerformed(evt);
            }
        });
        jPanel2.add(refreshbutton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 90, 30));
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, -1, -1));

        jTabbedPane1.addTab("Pengembalian", jPanel2);

        DataTransaksi.add(jTabbedPane1, "card2");

        kananpane.add(DataTransaksi, "card6");

        Dataperpanjangan.setBackground(new java.awt.Color(255, 255, 255));
        Dataperpanjangan.setLayout(new java.awt.CardLayout());

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Data Perpanjangan_____");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 350, 40));

        tbl_statusperpanjangan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_statusperpanjangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_statusperpanjanganMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_statusperpanjangan);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 720, 260));

        jLabel33.setText("ID");
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        tf_idpp.setEditable(false);
        jPanel3.add(tf_idpp, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 190, 30));

        jLabel34.setText("Nama");
        jPanel3.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 30));

        tf_namapp.setEditable(false);
        jPanel3.add(tf_namapp, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 190, 30));

        tf_bukupp.setEditable(false);
        jPanel3.add(tf_bukupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 190, 30));

        jLabel36.setText("Buku");
        jPanel3.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 30));

        jLabel37.setText("Jumlah");
        jPanel3.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 30));

        tf_jumlahpp.setEditable(false);
        jPanel3.add(tf_jumlahpp, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 190, 30));

        btn_batal.setBackground(new java.awt.Color(255, 255, 255));
        btn_batal.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel3.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, 100, 40));

        btn_setujui.setBackground(new java.awt.Color(255, 255, 255));
        btn_setujui.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btn_setujui.setForeground(new java.awt.Color(0, 204, 51));
        btn_setujui.setText("Setujui");
        btn_setujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_setujuiActionPerformed(evt);
            }
        });
        jPanel3.add(btn_setujui, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 220, 190, 40));

        jLabel38.setText("Tanggal Pinjam");
        jPanel3.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, 30));

        tf_tglpinjampp.setEditable(false);
        jPanel3.add(tf_tglpinjampp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 270, 30));

        jLabel39.setText("Tanggal Tenggat");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, 30));

        tf_tenggatpp.setEditable(false);
        jPanel3.add(tf_tenggatpp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 270, 30));

        btn_tolak.setBackground(new java.awt.Color(255, 255, 255));
        btn_tolak.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btn_tolak.setForeground(new java.awt.Color(255, 0, 0));
        btn_tolak.setText("Tolak");
        btn_tolak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tolakActionPerformed(evt);
            }
        });
        jPanel3.add(btn_tolak, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 110, 40));

        jLabel40.setText("Tanggal Perpanjang");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, -1, 30));

        tf_tglpp.setEditable(false);
        jPanel3.add(tf_tglpp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 270, 30));

        jTabbedPane2.addTab("Data Perpanjangan", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("History Perpanjangan_____");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 370, 40));

        jTabbedPane2.addTab("History Perpanjangan", jPanel4);

        Dataperpanjangan.add(jTabbedPane2, "card2");

        kananpane.add(Dataperpanjangan, "card6");

        MainPanel.add(kananpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 0, 765, 600));

        getContentPane().add(MainPanel, "card2");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void warna(){
        btnProfile1.setForeground(new Color(0,0,0,0));
        btnApprove.setForeground(new Color(0,0,0,0));
        btnAnggota.setForeground(new Color(0,0,0,0));
        btnLibrary.setForeground(new Color(0,0,0,0));
        btnLogout.setForeground(new Color(0,0,0,0));
        btnTransaksi.setForeground(new Color(0,0,0,0));
    }
    private void btnProfile1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfile1MouseClicked
        // TODO add your handling code here:
        kananpane.removeAll();
        kananpane.repaint();
        kananpane.revalidate();
        //
        kananpane.add(Profilepane);
        kananpane.repaint();
        kananpane.revalidate();
    }//GEN-LAST:event_btnProfile1MouseClicked

    private void btnProfile1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfile1MouseEntered
        // TODO add your handling code here:
        btnProfile1.setForeground(Color.white);
    }//GEN-LAST:event_btnProfile1MouseEntered

    private void btnProfile1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfile1MouseExited
        // TODO add your handling code here:
        btnProfile1.setForeground(new Color(37,51,60));
    }//GEN-LAST:event_btnProfile1MouseExited

    private void btnAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnggotaMouseClicked
        // TODO add your handling code here:
        kananpane.removeAll();
        kananpane.repaint();
        kananpane.revalidate();
        //
        kananpane.add(Anggotapane);
        kananpane.repaint();
        kananpane.revalidate();
    }//GEN-LAST:event_btnAnggotaMouseClicked

    private void btnAnggotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnggotaMouseEntered
        // TODO add your handling code here:
        btnAnggota.setForeground(Color.white);
    }//GEN-LAST:event_btnAnggotaMouseEntered

    private void btnAnggotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnggotaMouseExited
        // TODO add your handling code here:
        btnAnggota.setForeground(new Color(37,51,60));
    }//GEN-LAST:event_btnAnggotaMouseExited

    private void btnLibraryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLibraryMouseClicked
        // TODO add your handling code here:
        kananpane.removeAll();
        kananpane.repaint();
        kananpane.revalidate();
        //
        kananpane.add(Daftarbuku);
        kananpane.repaint();
        kananpane.revalidate();
    }//GEN-LAST:event_btnLibraryMouseClicked

    private void btnLibraryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLibraryMouseEntered
        // TODO add your handling code here:
        btnLibrary.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnLibraryMouseEntered

    private void btnLibraryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLibraryMouseExited
        // TODO add your handling code here:
        btnLibrary.setForeground(new Color(37,51,60));
    }//GEN-LAST:event_btnLibraryMouseExited

    private void btnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseClicked
        // TODO add your handling code here:
        kananpane.removeAll();
        kananpane.repaint();
        kananpane.revalidate();
        //
        kananpane.add(DataTransaksi);
        kananpane.repaint();
        kananpane.revalidate();
    }//GEN-LAST:event_btnTransaksiMouseClicked

    private void btnTransaksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseEntered
        // TODO add your handling code here:
        btnTransaksi.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnTransaksiMouseEntered

    private void btnTransaksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseExited
        // TODO add your handling code here:
        btnTransaksi.setForeground(new Color(37,51,60));
    }//GEN-LAST:event_btnTransaksiMouseExited

    private void btnApproveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApproveMouseClicked
        kananpane.removeAll();
        kananpane.repaint();
        kananpane.revalidate();
        //
        kananpane.add(Dataperpanjangan);
        kananpane.repaint();
        kananpane.revalidate();
    }//GEN-LAST:event_btnApproveMouseClicked

    private void btnApproveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApproveMouseEntered
        // TODO add your handling code here:
        btnApprove.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnApproveMouseEntered

    private void btnApproveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApproveMouseExited
        // TODO add your handling code here:
        btnApprove.setForeground(new Color(37,51,60));
    }//GEN-LAST:event_btnApproveMouseExited

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // TODO add your handling code here:
        int opsi = JOptionPane.showConfirmDialog(null, "Apakah anda ingin Keluar ?");
        switch(opsi){
            case JOptionPane.YES_OPTION:
            new FrontView().setVisible(true);
            this.dispose();
            break;
            case JOptionPane.NO_OPTION:
            break;
            default:
            break;
        }
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        // TODO add your handling code here:
        btnLogout.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        // TODO add your handling code here:
        btnLogout.setForeground(new Color(37,51,60));
    }//GEN-LAST:event_btnLogoutMouseExited

    private void tf_pass2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_pass2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_pass2ActionPerformed

    private void btn_ubahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMouseClicked
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_ubahMouseClicked

    private void btn_ubahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMouseEntered
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_ubahMouseEntered

    private void btn_ubahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMouseExited
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_ubahMouseExited

    private void btn_ubahMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMousePressed
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_ubahMousePressed

    private void btn_ubahMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMouseReleased
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_ubahMouseReleased

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        
        if ("Update".equals(btn_ubah.getText())) {
            enable_true();
            btn_ubah.setText("Simpan");
        }else{
            if (tf_pass2.getText() == null ? tf_pass.getText() != null : !tf_pass2.getText().equals(tf_pass.getText())) {
                JOptionPane.showMessageDialog(this, "password tidak sama!!");
            }else{
                try {
                    String sql = "UPDATE user SET username=?,password=?,email=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, tf_user.getText());
                    ps.setString(2, tf_pass2.getText());
                    ps.setString(3, tf_email.getText());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data Berhasil di Update!");
                    btn_ubah.setText("Simpan");
                    enable_false();
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "gagal"+e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked

    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel32MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseEntered

    }//GEN-LAST:event_jLabel32MouseEntered

    private void jLabel32MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseExited

    }//GEN-LAST:event_jLabel32MouseExited

    private void tf_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_passActionPerformed

    private void table_anggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_anggotaMouseClicked
        // TODO add your handling code here:
        try {
            int row = table_anggota.getSelectedRow();
            TableModel model = table_anggota.getModel();
            //menampilkan data dari table ke texfield, jlabel dll
            id_user = model.getValueAt(row, 0).toString();
            tf_namaUser.setText(model.getValueAt(row, 1).toString());
            tf_nimUser.setText(model.getValueAt(row, 2).toString());
            tf_emailUser.setText(model.getValueAt(row, 3).toString());
            tf_nohpUser.setText(model.getValueAt(row, 4).toString());
            tf_passUser.setText(model.getValueAt(row, 5).toString());
            String role = model.getValueAt(row, 6).toString();
            if (role.equals("1")) {
                cmb_role.setSelectedIndex(2);
            }else{
                cmb_role.setSelectedIndex(1);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_table_anggotaMouseClicked

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        // TODO add your handling code here:
        jTextField1.setText("");

    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        int jumlahrow = table_anggota.getRowCount();
        for(int n=0; n < jumlahrow; n++){
            model_anggota.removeRow(0);
        }
        try {
            String sql ="select*from registrasi where nama like '%"+jTextField1.getText()+"%' or kelas like '%"+jTextField1.getText()+"%'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            int no = 1;
            while (rs.next()) {
                Object[] obj = new Object[6];
                obj [0] = rs.getString("id");
                obj [1] = rs.getString("nama");
                obj [2] = rs.getString("kelas");
                obj [3] = rs.getString("email");
                obj [4] = rs.getString("password");
                obj [5] = rs.getString("noHP");
                model_anggota.addRow(obj);
                no++;
            }
            table_anggota.setModel(model_anggota);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cari eroor "+e);
        }

    }//GEN-LAST:event_jTextField1KeyReleased

    private void tf_nohpUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_nohpUserKeyReleased
        // TODO add your handling code here:

        if (tf_nohpUser.getText().length()>= 14) {
            JOptionPane.showMessageDialog(this, "karakter terlalu panjang!!");
        }

    }//GEN-LAST:event_tf_nohpUserKeyReleased

    private void tf_nohpUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_nohpUserKeyTyped
        // TODO add your handling code here:
        char enter=evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_tf_nohpUserKeyTyped

    private void jToggleButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MousePressed
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_jToggleButton1MousePressed

    private void jToggleButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseReleased
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_jToggleButton1MouseReleased

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        try {
            int opsi = JOptionPane.showConfirmDialog(null, "Benarkah anda ingin menghapus data ini ?");
            switch(opsi){
                case JOptionPane.YES_OPTION:
                    String sql = "DELETE FROM user WHERE id_user ="+id_user ;
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }
            refresh();
//            bersih();
//            dataanggota();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
//        JasperReport reports;
//        String lokasifile = "C:\\Program Files (x86)\\Dreams Team\\Libeeo\\src\\reportEdu\\anggota_libeeo.jasper";
//        try {
//            reports = (JasperReport) JRLoader.loadObjectFromFile(lokasifile);
//            JasperPrint jprint = JasperFillManager.fillReport(lokasifile, null, conn);
//            JasperViewer jview = new JasperViewer(jprint, false);
//            jview.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//            jview.setVisible(true);
//
//        } catch (JRException e) {
//            JOptionPane.showMessageDialog(this, "Report GAGAL karena "+e.getMessage());
//        }
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton4MousePressed
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_jToggleButton4MousePressed

    private void jToggleButton4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton4MouseReleased
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_jToggleButton4MouseReleased

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
        if (tf_emailUser.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "email masih kosong!!");
        }else if(tf_namaUser.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "nama masih kosong!!");
        }else if(tf_nohpUser.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "No HP masih kosong!!");
        }else if(tf_passUser.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "Password masih kosong!!");
        }else if(tf_nimUser.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "Kelas masih kosong!!");
        }else{
            try {
                String sql = "UPDATE user SET "
                        + "username=?,nim=?,email=?,nohp=?,password=?,role=? "
                        + "WHERE id_user=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tf_namaUser.getText());
                ps.setString(2, tf_nimUser.getText());
                ps.setString(3, tf_emailUser.getText());
                ps.setString(4, tf_nohpUser.getText());
                ps.setString(5, tf_passUser.getText());
                String role = "1";
                String pil = cmb_role.getSelectedItem().toString();
                if ("User".equals(pil)) {
                    role = "2";
                }
                ps.setString(6, role);
                ps.setString(7, id_user);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Berhasil Mengupdate data User");
                refresh();
//                bersih();
//                dataanggota();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal Mengupdate data User"+e.getMessage());
            }
        }
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void btn_tambahUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahUserMousePressed
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_tambahUserMousePressed

    private void btn_tambahUserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahUserMouseReleased
        // TODO add your handling code here:
        btn_ubah.setBackground(Color.white);
    }//GEN-LAST:event_btn_tambahUserMouseReleased

    private void btn_tambahUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahUserActionPerformed
        // TODO add your handling code here:
        if ("Tambah".equals(btn_tambahUser.getText())) {
            enable_true();
            btn_tambahUser.setText("Simpan");
        }else{
            if (tf_emailUser.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "email masih kosong!!");
            }else if(tf_namaUser.getText().length() == 0){
                JOptionPane.showMessageDialog(this, "nama masih kosong!!");
            }else if(tf_nohpUser.getText().length() == 0){
                JOptionPane.showMessageDialog(this, "No HP masih kosong!!");
            }else if(tf_passUser.getText().length() == 0){
                JOptionPane.showMessageDialog(this, "Password masih kosong!!");
            }else if(tf_nimUser.getText().length() == 0){
                JOptionPane.showMessageDialog(this, "Kelas masih kosong!!");
            }else{
                try {
                    String sql = "INSERT INTO user(username,nim,email,nohp,password,role) values (?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, tf_namaUser.getText());
                    ps.setString(2, tf_nimUser.getText());
                    ps.setString(3, tf_emailUser.getText());
                    ps.setString(4, tf_nohpUser.getText());
                    ps.setString(5, tf_passUser.getText());
                    String role = "1";
                    String pil = cmb_role.getSelectedItem().toString();
                    if ("User".equals(pil)) {
                        role = "2";
                    }
                    ps.setString(6, role);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Berhasil Menambahkan User");
                    refresh();
                    enable_false();
                    btn_tambahUser.setText("Tambah");
    //                bersih();
    //                dataanggota();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gagal Menambahkan User "+e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btn_tambahUserActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        bersih();
//        dataanggota();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabel_bukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_bukuMouseClicked
        // TODO add your handling code here:
        try {
            int row = tabel_buku.getSelectedRow();
            TableModel model = tabel_buku.getModel();
            //menampilkan data dari table ke texfield, jlabel dll
            id_buku =  model.getValueAt(row, 0).toString();
            namabuku.setText(model.getValueAt(row, 1).toString());
            pengarangbuku.setText(model.getValueAt(row, 3).toString());
            stokbuku.setText(model.getValueAt(row, 2).toString());
            penerbitbuku.setText(model.getValueAt(row, 4).toString());
            thnterbit.setSelectedItem(model.getValueAt(row, 5).toString());
            //khusus gambar

        } catch (Exception e) {
        }
    }//GEN-LAST:event_tabel_bukuMouseClicked

    private void jTextField7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField7MouseClicked
        // TODO add your handling code here:
        jTextField7.setText("");
    }//GEN-LAST:event_jTextField7MouseClicked

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        // TODO add your handling code here:
        int jumlahrow = tabel_buku.getRowCount();
        for(int n=0; n < jumlahrow; n++){
            model_buku.removeRow(0);
        }
        try {
            String sql ="select*from buku where nama like '%"+jTextField7.getText()+"%' or penulis like '%"+jTextField7.getText()+"%'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            int no = 1;
            while (rs.next()) {
                Object[] obj = new Object[6];
                obj [0] = rs.getString("no");
                obj [1] = rs.getString("nama");
                obj [2] = rs.getString("penulis");
                obj [3] = rs.getString("stok");
                obj [4] = rs.getString("penerbit");
                obj [5] = rs.getString("thnterbit");

                model_buku.addRow(obj);
                no++;
            }
            tabel_buku.setModel(model_buku);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cari eroor "+e);
        }
    }//GEN-LAST:event_jTextField7KeyReleased

    private void stokbukuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stokbukuKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_stokbukuKeyReleased

    private void stokbukuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stokbukuKeyTyped
        // TODO add your handling code here:
        char enter=evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_stokbukuKeyTyped

    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed
        // TODO add your handling code here:
//        JasperReport reports;
//        String lokasifile = "C:\\Program Files (x86)\\Dreams Team\\Libeeo\\src\\reportEdu\\buku_libeeo.jasper";
//        try {
//            reports = (JasperReport) JRLoader.loadObjectFromFile(lokasifile);
//            JasperPrint jprint = JasperFillManager.fillReport(lokasifile, null, conn);
//            JasperViewer jview = new JasperViewer(jprint, false);
//            jview.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//            jview.setVisible(true);
//
//        } catch (JRException e) {
//            JOptionPane.showMessageDialog(this, "Report GAGAL karena "+e.getMessage());
//        }

    }//GEN-LAST:event_jToggleButton10ActionPerformed

    private void btn_tambahbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahbukuActionPerformed
        // TODO add your handling code here:
        if (namabuku.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Nama Buku Masih Kosong!!!");
        }else if(pengarangbuku.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "Pengarang Buku Masih Kosong!!!");
        }else if(stokbuku.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "Stok Buku Masih Kosong!!!");
        }else if(penerbitbuku.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "Penerbit Buku Masih Kosong!!!");
        }else if(thnterbit.getSelectedItem() == "---Pilih Tahun---"){
            JOptionPane.showMessageDialog(this, "Tahun Masih Kosong!!!");
        }else{
            try {
                String sql = "INSERT INTO buku(namabuku,penulis,stok,penerbit,tahun_terbit) values (?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, namabuku.getText());
                ps.setString(2, pengarangbuku.getText());
                ps.setString(3, stokbuku.getText());
                ps.setString(4, penerbitbuku.getText());
                ps.setString(5, thnterbit.getSelectedItem().toString());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Berhasil Menambahkan Buku Baru");
                model_buku.setRowCount(0);
//                bersih();
//                databuku();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal Menambahkan Buku "+e);
            }
        }
    }//GEN-LAST:event_btn_tambahbukuActionPerformed

    private void jToggleButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton12ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "UPDATE buku SET namabuku=?, penulis=?, stok=?, penerbit=?, tahun_terbit=? WHERE id_buku=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, namabuku.getText());
            ps.setString(2, pengarangbuku.getText());
            ps.setString(3, stokbuku.getText());
            ps.setString(4, penerbitbuku.getText());
            ps.setString(5, thnterbit.getSelectedItem().toString());
            ps.setString(6, id_buku);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Berhasil Mengubah Data Buku");
            model_buku.setRowCount(0);
//            bersih();
//            databuku();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal !!! "+e);
        }
    }//GEN-LAST:event_jToggleButton12ActionPerformed

    private void jToggleButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton13ActionPerformed
        // TODO add your handling code here:
        try {
            int opsi = JOptionPane.showConfirmDialog(null, "Benarkah anda ingin menghapus data ini ?");
            switch(opsi){
                case JOptionPane.YES_OPTION:
                    String sql = "DELETE FROM buku where id_buku ="+id_buku ;
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    model_buku.setRowCount(0);
    //                bersih();
    //                databuku();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_jToggleButton13ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        namabuku.setText("");
        pengarangbuku.setText("");
        stokbuku.setText("");
        penerbitbuku.setText("");
        thnterbit.setSelectedIndex(0);
//        bersih();
//        databuku();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbl_peminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_peminjamanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_peminjamanMouseClicked

    private void jTextField15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15MouseClicked

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15KeyReleased

    private void jToggleButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton8ActionPerformed
        // TODO add your handling code here:
        try {
            String path = "/Users/macbookpro/NetBeansProjects/ProjectAkhir/src/Report/data_peminjaman.jrxml";
            HashMap hash = new HashMap();
            JasperReport jrpt = JasperCompileManager.compileReport(path);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, hash, conn);
            JasperViewer.viewReport(jprint, false);
        } catch (JRException e) {
            System.out.println("error : " + e.getMessage());
        }
    }//GEN-LAST:event_jToggleButton8ActionPerformed

    private void refreshbutton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbutton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshbutton2ActionPerformed

    private void tbl_pengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pengembalianMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_pengembalianMouseClicked

    private void jTextField16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16MouseClicked

    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyReleased

    private void jToggleButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton19ActionPerformed
        // TODO add your handling code here:
        try {
            String path = "/Users/macbookpro/NetBeansProjects/ProjectAkhir/src/Report/data_pengembalian.jrxml";
            HashMap hash = new HashMap();
            JasperReport jrpt = JasperCompileManager.compileReport(path);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, hash, conn);
            JasperViewer.viewReport(jprint, false);
        } catch (JRException e) {
            System.out.println("error : " + e.getMessage());
        }
    }//GEN-LAST:event_jToggleButton19ActionPerformed

    private void refreshbutton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbutton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshbutton3ActionPerformed

    private void tbl_statusperpanjanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_statusperpanjanganMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbl_statusperpanjangan.getSelectedRow();
        btn_setujui.setEnabled(true);
        btn_tolak.setEnabled(true);
        if (selectedRow != -1) {
            tf_idpp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 0).toString());
            tf_namapp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 1).toString());
            tf_bukupp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 2).toString());
            tf_jumlahpp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 3).toString());
            tf_tglpinjampp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 4).toString());
            tf_tenggatpp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 5).toString());
            tf_tglpp.setText(tbl_statusperpanjangan.getValueAt(selectedRow, 6).toString());
        }
    }//GEN-LAST:event_tbl_statusperpanjanganMouseClicked
    void clear_pp(){
        tf_idpp.setText("");
        tf_namapp.setText("");
        tf_bukupp.setText("");
        tf_jumlahpp.setText("");
        tf_tglpinjampp.setText("");
        tf_tenggatpp.setText("");
        tf_tglpp.setText("");
        btn_setujui.setEnabled(false);
        btn_tolak.setEnabled(false);
    }
    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        clear_pp();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        enable_false();
        btn_ubah.setText("Update");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_setujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_setujuiActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "UPDATE perpanjangan SET status=? WHERE id_perpanjangan=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "disetujui");
            ps.setString(2, tf_idpp.getText());
            ps.executeUpdate();
            clear_pp();
            show_status_perpanjangan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_setujuiActionPerformed

    private void btn_tolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tolakActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "UPDATE perpanjangan SET status=? WHERE id_perpanjangan=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "ditolak");
            ps.setString(2, tf_idpp.getText());
            ps.executeUpdate();
            clear_pp();
            show_status_perpanjangan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_tolakActionPerformed

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Anggotapane;
    private javax.swing.JPanel Daftarbuku;
    private javax.swing.JPanel DataTransaksi;
    private javax.swing.JPanel Dataperpanjangan;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel Profilepane;
    private javax.swing.JLabel btnAnggota;
    private javax.swing.JLabel btnApprove;
    private javax.swing.JLabel btnLibrary;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnProfile1;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_setujui;
    private javax.swing.JToggleButton btn_tambahUser;
    private javax.swing.JToggleButton btn_tambahbuku;
    private javax.swing.JButton btn_tolak;
    private javax.swing.JToggleButton btn_ubah;
    private javax.swing.JComboBox<String> cmb_role;
    private javax.swing.JLabel gambar;
    private javax.swing.JLabel input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton12;
    private javax.swing.JToggleButton jToggleButton13;
    private javax.swing.JToggleButton jToggleButton19;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JPanel kananpane;
    private javax.swing.JPanel kiri;
    private javax.swing.JTextField namabuku;
    private javax.swing.JTextField penerbitbuku;
    private javax.swing.JTextField pengarangbuku;
    private javax.swing.JToggleButton refreshbutton2;
    private javax.swing.JToggleButton refreshbutton3;
    private javax.swing.JTextField stokbuku;
    private javax.swing.JTable tabel_buku;
    private javax.swing.JTable table_anggota;
    private javax.swing.JTable tbl_peminjaman;
    private javax.swing.JTable tbl_pengembalian;
    private javax.swing.JTable tbl_statusperpanjangan;
    private javax.swing.JTextField tf_bukupp;
    private javax.swing.JTextField tf_email;
    private javax.swing.JTextField tf_emailUser;
    private javax.swing.JTextField tf_idpp;
    private javax.swing.JTextField tf_jumlahpp;
    private javax.swing.JTextField tf_namaUser;
    private javax.swing.JTextField tf_namapp;
    private javax.swing.JTextField tf_nimUser;
    private javax.swing.JTextField tf_nohpUser;
    private javax.swing.JTextField tf_pass;
    private javax.swing.JTextField tf_pass2;
    private javax.swing.JTextField tf_passUser;
    private javax.swing.JTextField tf_tenggatpp;
    private javax.swing.JTextField tf_tglpinjampp;
    private javax.swing.JTextField tf_tglpp;
    private javax.swing.JTextField tf_user;
    private javax.swing.JComboBox<String> thnterbit;
    // End of variables declaration//GEN-END:variables
}
