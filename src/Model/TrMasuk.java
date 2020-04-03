/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Controller.Koneksi;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class TrMasuk extends DetailMasuk{
    private String IdSupplier;
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getIdSupplier() {
        return IdSupplier;
    }

    public void setIdSupplier(String IdSupplier) {
        this.IdSupplier = IdSupplier;
    }
    
    public void Save(String idmasuk, Date tanggal, int jumlah, String idsupplier) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into tr_masuk(idmasuk, tanggal, jumlah, idsupplier)values('" + idmasuk + "','" + tanggal + "','" + jumlah + "','" + idsupplier + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan");
        } catch (SQLException e) {
        }
    }
}
