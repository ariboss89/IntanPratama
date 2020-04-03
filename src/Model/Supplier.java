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
public class Supplier {
    public String idsupplier;
    public String nama;
    public String kontak;
    public String alamat;
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(String idsupplier) {
        this.idsupplier = idsupplier;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public void Save(String idsupplier, String nama, String kontak, String alamat) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into tb_supplier(idsupplier, nama, kontak, alamat)values('" + idsupplier + "','" + nama + "','" + kontak + "','" + alamat + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan");
        } catch (SQLException e) {
        }
    }
    
    public void Update(String idsupplier, String nama, String kontak, String alamat) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update tb_supplier set nama='" + nama + "', kontak = '"+kontak+"', alamat='" + alamat + "' where idsupplier = '" + idsupplier + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        } catch (SQLException e) {

        }
    }
    
    public void Delete(String idsupplier) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from tb_supplier where idsupplier = '" + idsupplier + "'";
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
            query = "SELECT COUNT(idsupplier) AS Jumlah FROM tb_supplier;";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from tb_supplier";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("idsupplier");
                data[r][1] = res.getString("nama");
                data[r][2] = res.getString("kontak");
                data[r][3] = res.getString("alamat");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][4];
            for (r = 0; r < jmlBaris; r++) {
                for(int c = 0; c < 4; c++) {
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

    public String[][] SearchData(String idsupplier) {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(idsupplier) AS Jumlah FROM tb_supplier";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from tb_supplier where idsupplier like '%" + idsupplier + "%'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("idsupplier");
                data[r][1] = res.getString("nama");
                data[r][2] = res.getString("kontak");
                data[r][3] = res.getString("alamat");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][4];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 4; c++) {
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
