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
public class Masuk {
    public String idmasuk;
    public Date tanggal;
    public int jumlah;
    public String idsupplier;
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getIdmasuk() {
        return idmasuk;
    }

    public void setIdmasuk(String idmasuk) {
        this.idmasuk = idmasuk;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(String idsupplier) {
        this.idsupplier = idsupplier;
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
