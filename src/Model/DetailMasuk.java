/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Controller.Koneksi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DetailMasuk {
    public String iddetailmasuk;
    public String idmasuk;
    public String idbarang;
    public int jumlah;
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getIddetailmasuk() {
        return iddetailmasuk;
    }

    public void setIddetailmasuk(String iddetailmasuk) {
        this.iddetailmasuk = iddetailmasuk;
    }

    public String getIdmasuk() {
        return idmasuk;
    }

    public void setIdmasuk(String idmasuk) {
        this.idmasuk = idmasuk;
    }

    public String getIdbarang() {
        return idbarang;
    }

    public void setIdbarang(String idbarang) {
        this.idbarang = idbarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    
    public void SaveDetail(String iddetailmasuk, String idmasuk, String idbarang, int jumlah) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into dt_masuk(iddetailmasuk, idmasuk, idbarang, jumlah)values('" + iddetailmasuk + "','" + idmasuk + "','" + idbarang + "','" + jumlah + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Menambahkan Data");
        } catch (SQLException e) {
        }
    }
    
    public void Update(String iddetailmasuk, String idmasuk, String idbarang, int jumlah) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update dt_masuk set idbarang='" + idbarang + "' where iddetailmasuk = '" + iddetailmasuk + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        } catch (SQLException e) {

        }
    }
    
    public void DeleteDetail(String iddetailmasuk) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from dt_masuk where iddetailmasuk = '" + iddetailmasuk + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus");
        } catch (SQLException e) {

        }
    }

    public String[][] ShowData( String idMasuk) {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(iddetailmasuk) AS Jumlah FROM dt_masuk;";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from dt_masuk where idmasuk = '"+idMasuk+"'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("iddetailmasuk");
                data[r][1] = res.getString("idbarang");
                data[r][2] = res.getString("jumlah");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][3];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 3; c++) {
                    data[r][c] = tmpArray[r][c];
                }
            }
            st.close();
            con.conn.close();
        } catch (SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
        }
        return data;
    }
}
