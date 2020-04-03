/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Koneksi;
import Model.Tabel;
import Model.TrMasuk;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class FormTransaksiPembelian extends javax.swing.JFrame {

    TrMasuk tmsk = new TrMasuk();
    Tabel tbl = new Tabel();
    private String rs[][];
    String[] namaKolom = {"Id Detail ", "Id Barang", "Jumlah"};
    int jmlKolom = namaKolom.length;
    int[] lebar = {400, 400, 400};
    private String idMasuk;
    private Date tanggal;
    private int jumlahItem;
    private String idSupplier;
    private String idDetailMasuk;
    private String idBarang;
    private int jumlah;

    /**
     * Creates new form FormTransaksiPembelian
     */
    public FormTransaksiPembelian() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - getWidth()) / 2;
        int y = (dim.height - getHeight()) / 2;
        setLocation(x, y);
        Refresh();
        ShowSupplier();
        ShowBarang();
    }

    private void Refresh() {
        NoPembelian();
        NoDetailPembelian();
        ShowTanggal();
        cbSupplier.setSelectedIndex(0);
        txtAlamat.setText("");
        txtKontak.setText("");
        txtJumlahItem.setText("0");
        cbNamaBarang.setSelectedIndex(0);
        txtJumlah.setText("0");
    }

    private void NoPembelian() {
        java.sql.Connection conn = new Koneksi().connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select *from tr_masuk ORDER BY idmasuk DESC");
            if (rs.first() == false) {
                txtNoPembelian.setText("IN-0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("idmasuk").substring(3, 7));
                int nokirim = Integer.valueOf(rs.getString("idmasuk").substring(3, 7)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    txtNoPembelian.setText("IN-000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    txtNoPembelian.setText("IN-00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    txtNoPembelian.setText("IN-0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    txtNoPembelian.setText("IN-" + nokirim);
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

    private void NoDetailPembelian() {
        java.sql.Connection conn = new Koneksi().connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select *from dt_masuk ORDER BY iddetailmasuk DESC");
            if (rs.first() == false) {
                tmsk.setIddetailmasuk("DT-IN0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("iddetailmasuk").substring(5, 9));
                int nokirim = Integer.valueOf(rs.getString("iddetailmasuk").substring(5, 9)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    tmsk.setIddetailmasuk("DT-IN000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    tmsk.setIddetailmasuk("DT-IN00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    tmsk.setIddetailmasuk("DT-IN0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 9999) {
                    tmsk.setIddetailmasuk("DT-IN" + nokirim);
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

    private void ShowSupplier() {
        java.sql.Connection conn = new Koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from tb_supplier");
            while (res.next()) {
                cbSupplier.addItem(res.getString("nama"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nama Supplier Gagal di Tampilkan !!");
        }
    }

    private void ShowBarang() {
        java.sql.Connection conn = new Koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from tb_barang");
            while (res.next()) {
                cbNamaBarang.addItem(res.getString("nama"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nama Supplier Gagal di Tampilkan !!");
        }
    }

    private void ShowTanggal() {

        Calendar kalender = new GregorianCalendar();

        int hari = kalender.get(Calendar.DATE);
        int bulan = kalender.get(Calendar.MONTH) + 1;
        int tahun = kalender.get(Calendar.YEAR);
        String formatTanggal = tahun + "-" + bulan + "-" + hari;
        txtTanggal.setText(formatTanggal);

    }

    private void Sum() {
        int rowCount = jTable1.getRowCount();
        int totalItem = 0;
        if (rowCount == 0) {

        } else {

            for (int i = 0; i < rowCount; i++) {
                int total = Integer.valueOf(jTable1.getValueAt(i, 2).toString());
                totalItem += total;
            }
            txtJumlahItem.setText(String.valueOf(totalItem));
        }
    }
    
    private void Cetak() {
        //ShowAlamat();
        java.sql.Connection conn = new Koneksi().connect();

        try {
            HashMap parameter = new HashMap();
            File file = new File("src/Report/NotaPembelian.jasper");
            String tanggal1 = txtTanggal.getText();
            String nota = txtNoPembelian.getText();
            String pelanggan = cbSupplier.getSelectedItem().toString();
            parameter.put("nota", nota);
            parameter.put("tanggal", tanggal1);
            parameter.put("pelanggan", pelanggan);
            JasperReport jp = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, parameter, conn);
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            Refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtKontak = new javax.swing.JTextField();
        cbSupplier = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cbNamaBarang = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtNoPembelian = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtJumlahItem = new javax.swing.JTextField();
        btnSelesai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Transaksi Pembelian");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 153, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMASI SUPPLIER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("NAMA SUPPLIER");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("ALAMAT");

        txtAlamat.setEditable(false);
        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("KONTAK");

        txtKontak.setEditable(false);
        txtKontak.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cbSupplier.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PILIH" }));
        cbSupplier.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSupplierItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addComponent(txtKontak)
                    .addComponent(cbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtKontak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 51));
        jLabel1.setText("NAMA BARANG");

        cbNamaBarang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbNamaBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PILIH" }));
        cbNamaBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNamaBarangItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 51));
        jLabel2.setText("JUMLAH");

        txtJumlah.setText("0");
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(255, 153, 51));
        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(255, 153, 51));
        btnExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExit.setText("EXIT");

        jTable1.setBackground(new java.awt.Color(255, 153, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(255, 153, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOTAL PEMBELIAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtNoPembelian.setEditable(false);
        txtNoPembelian.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("NO PEMBELIAN     ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TANGGAL");

        txtTanggal.setEditable(false);
        txtTanggal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("JUMLAH ITEM");

        txtJumlahItem.setEditable(false);
        txtJumlahItem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtJumlahItem.setText("0");

        btnSelesai.setBackground(new java.awt.Color(255, 153, 51));
        btnSelesai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSelesai.setText("SELESAI");
        btnSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelesaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSelesai, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(txtJumlahItem, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(txtTanggal)
                    .addComponent(txtNoPembelian))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNoPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtJumlahItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSelesai)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbNamaBarang, 0, 586, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(txtJumlah))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave)
                            .addComponent(btnExit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbSupplierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSupplierItemStateChanged
        // TODO add your handling code here:
        java.sql.Connection conn = new Koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from tb_supplier where nama = '" + cbSupplier.getSelectedItem() + "'");
            while (res.next()) {
                tmsk.setIdSupplier(res.getString("idsupplier"));
                txtAlamat.setText(res.getString("alamat"));
                txtKontak.setText(res.getString("kontak"));
            }
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_cbSupplierItemStateChanged

    private void btnSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelesaiActionPerformed
        // TODO add your handling code here:
        idMasuk = txtNoPembelian.getText().trim();
        tanggal = Date.valueOf(txtTanggal.getText().trim());
        jumlahItem = Integer.parseInt(txtJumlahItem.getText());
        idSupplier = tmsk.getIdSupplier();

        tmsk.Save(idMasuk, tanggal, jumlahItem, idSupplier);
        Cetak();
    }//GEN-LAST:event_btnSelesaiActionPerformed

    private void cbNamaBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNamaBarangItemStateChanged
        // TODO add your handling code here:
        java.sql.Connection conn = new Koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from tb_barang where nama = '" + cbNamaBarang.getSelectedItem() + "'");
            while (res.next()) {
                tmsk.setIdbarang(res.getString("idbarang"));
                txtJumlah.requestFocus();
            }
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_cbNamaBarangItemStateChanged

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        idDetailMasuk = tmsk.getIddetailmasuk();
        idMasuk = txtNoPembelian.getText().trim();
        idBarang = tmsk.getIdbarang();
        jumlah = Integer.parseInt(txtJumlah.getText().trim());
        jumlahItem = Integer.parseInt(txtJumlahItem.getText().trim());

        if (cbSupplier.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Supplier !!");
            cbSupplier.requestFocus();
        } else if (cbNamaBarang.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang !!");
            cbNamaBarang.requestFocus();
        } else if (jumlah == 0) {
            JOptionPane.showMessageDialog(null, "Jumlah Barang Kosong, Silahkan Isi !!");
            cbNamaBarang.requestFocus();
        } else {
            tmsk.SaveDetail(idDetailMasuk, idMasuk, idBarang, jumlah);
            Refresh();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        idMasuk = txtNoPembelian.getText().trim();
        rs = tmsk.ShowData(idMasuk);
        tbl.SetTabel(jTable1, rs, namaKolom, jmlKolom, lebar);
        Sum();
    }//GEN-LAST:event_formWindowActivated

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();

        if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE) || (karakter == KeyEvent.VK_ENTER)))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtJumlahKeyTyped

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
            java.util.logging.Logger.getLogger(FormTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksiPembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelesai;
    private javax.swing.JComboBox cbNamaBarang;
    private javax.swing.JComboBox cbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtJumlahItem;
    private javax.swing.JTextField txtKontak;
    private javax.swing.JTextField txtNoPembelian;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}
