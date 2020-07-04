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
public class Barang {
    public String idbarang;
    public String nama;
    public String kategori;
    public String satuan;
    public int hargabeli;
    public int hargajual;
    public int stok;
    public int minstok;
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getIdbarang() {
        return idbarang;
    }

    public void setIdbarang(String idbarang) {
        this.idbarang = idbarang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(int hargabeli) {
        this.hargabeli = hargabeli;
    }

    public int getHargajual() {
        return hargajual;
    }

    public void setHargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
    
    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getMinstok() {
        return minstok;
    }

    public void setMinstok(int minstok) {
        this.minstok = minstok;
    }
    
    public void Save(String idbarang, String nama, String kategori, String satuan, int hargabeli, int hargajual, int stok, int minstok) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into tb_barang(idbarang, nama, kategori, satuan, hargabeli, hargajual,stok, minstok)values('" + idbarang + "','" + nama + "','" + kategori + "','" +satuan + "','" + hargabeli + "','" + hargajual + "','" + stok + "','" + minstok + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan");
        } catch (SQLException e) {
        }
    }
    
    public void Update(String idbarang, String nama, String kategori, String satuan, int hargabeli, int hargajual, int stok, int minstok) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update tb_barang set nama='" + nama + "', kategori='" + kategori + "', satuan='" + satuan + "', hargabeli='" + hargabeli + "', hargajual='" + hargajual + "', stok = '"+ stok +"', minstok='" + minstok + "' where idbarang = '" + idbarang + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        } catch (SQLException e) {

        }
    }
    
    public void Delete(String idbarang) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from tb_barang where idbarang = '" + idbarang + "'";
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
            query = "SELECT COUNT(idbarang) AS Jumlah FROM tb_barang;";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from tb_barang";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][8];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("idbarang");
                data[r][1] = res.getString("nama");
                data[r][2] = res.getString("kategori");
                data[r][3] = res.getString("satuan");
                data[r][4] = res.getString("hargabeli");
                data[r][5] = res.getString("hargajual");
                data[r][6] = res.getString("stok");
                data[r][7] = res.getString("minstok");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][8];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 8; c++) {
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

    public String[][] SearchData(String search) {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(idbarang) AS Jumlah FROM tb_barang";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from tb_barang where idbarang like '%"+ search +"%' or nama like '%"+ search +"%'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][8];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("idbarang");
                data[r][1] = res.getString("nama");
                data[r][2] = res.getString("kategori");
                data[r][3] = res.getString("satuan");
                data[r][4] = res.getString("hargabeli");
                data[r][5] = res.getString("hargajual");
                data[r][6] = res.getString("stok");
                data[r][7] = res.getString("minstok");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][8];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c <8; c++) {
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
