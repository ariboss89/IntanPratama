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
public class Kategori {
    private String Nama;
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }
    
    public void Save(String nama) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into tb_kategori(kategori)values('" + nama +"')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan");
        } catch (SQLException e) {
        }
    }
    
    public void Delete(String kategori) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from tb_kategori where kategori = '" + kategori + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus");
        } catch (SQLException e) {

        }
    }

    public String[][] ShowData() {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(kategori) AS Jumlah FROM tb_kategori";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from tb_kategori";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("kategori");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][1];
            for (r = 0; r < jmlBaris; r++) {
                for(int c = 0; c < 1; c++) {
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

    public String[][] SearchData(String nama) {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(kategori) AS Jumlah FROM tb_kategori";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from tb_kategori where kategori like '%"+nama+"%'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("kategori");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][1];
            for (r = 0; r < jmlBaris; r++) {
                for(int c = 0; c < 1; c++) {
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
