package com.company;

import java.io.*;
import java.util.ArrayList;

public class DataStore {
    public static ArrayList<String[]> dataKasGlobal = new ArrayList<>();

    // Simpan data ke file CSV
    public static void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("dataKas.csv"))) {
            for (String[] row : dataKasGlobal) {
                pw.println(String.join(",", row));
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load data dari file CSV
    public static void loadData() {
        dataKasGlobal.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("dataKas.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                //mastiin baris ngga kosong
                if (!line.trim().isEmpty()) {
                    dataKasGlobal.add(line.split(","));
                }
            }
        } catch (FileNotFoundException e) {
            // kalau file belum ada, biarkan kosong
            System.out.println("File dataKas.csv belum ada, mulai dengan data kosong.");
        } catch (IOException e) {
            System.err.println("Gagal membaca data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
