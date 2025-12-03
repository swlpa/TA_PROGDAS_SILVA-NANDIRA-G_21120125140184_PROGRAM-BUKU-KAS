package com.company;

import javax.swing.*;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.text.NumberFormat;
import java.util.*;
import java.awt.Color;
import java.awt.Font;

public class VencoCash {
    public JPanel mainPanel;
    public JComboBox<String> comboBox5; // Tanggal
    public JComboBox<String> comboBox1; // Bulan
    public JComboBox<String> comboBox2; // Via
    public JComboBox<String> comboBox3; // Kelas
    public JComboBox<String> comboBox4; // Nama
    public JFormattedTextField jumlahField;
    public JButton simpanButton;
    public JButton keluarButton;
    public JButton lihatTabelButton;
    public JLabel tanggal;

    public DefaultTableModel tableModel;
    public ArrayList<String[]> dataKas = DataStore.dataKasGlobal;
    private String[] currentRow = new String[6];

    // Map nama per kelas
    private final Map<String, List<String>> namaPerKelas = new HashMap<>() {{
        put("A", Arrays.asList("Aftito Nur Faturohim", "Alisha Putri Cahya Kirani", "Annida Fauziatunnisa Yusuf ", "Aprilia Yuvita", "Arbi Anugerah Dinata", "Ardiva Arya Diandra", "Arsa Rafif Parama", "Bimoseno", "Marcello Saint", "Clara Mela Pristina", "Danis Audric Mahadana", "Darrel Athallah Bhagawanta", "Dewi Kumalasari", "Dewi Lestari Ningsih", "Dhavarel Revariza Akbar", "Dhea Faela Sufa", "Diaz Ridho Yuristianto", "Dinda Naysilla Aziz", "Evan Early Fulvian", "Fairuz Amelya Khalisa ", "Febby Mintias Saputri", "Haifa Dhiyanatha Aqila", "Hanum Anggitasari", "Hesya Albi Prasya", "Ibrahim Abyan Movic", "Isytia Khoironnida Tahara", "Muchamad Shofiyul Qalby", "Muhamad Salimul Qolbi", "Muhammad Arif Kurniawan", "Muhammad Fahrul Rizki", "Muhammad Rais Qumaini ", "Nikita Tesalonika Panjaitan", "Novian Tsany Aurora Wibowo", "Nuha Anindya Berliyanti Utomo", "Octavian Dwi Ramadhan", "Panji Bagas Respati", "Putra Satrio Wibowo", "Putri Mazro'aturrosyada" , "Rafsanjani Raffa Syahzidan", "Rasya Hafizh Hizbullah", "Raudatul Khairanis", "Ravly Bonus Ramdhani", "Reeva Mutiara Madhani", "Reindra Pasha Yudhistira", "Robi Mulia Santoso ", "Salsa Washilah", "Sonia Aprilliya", "Vikky Apdiansyah", "Wahyu Oktaviyana Ramandhani"));
        put("B", Arrays.asList("Abel Hidayat", "Adika Brahmana Rafi Sejati", "Akbar Maulana", "Bayhaqi Danurendra Naryo", "Bayu Seno Aji", "Daffa Abiyu Murdani", "Dafiq Mafaaza Mukti", "Danish Aqila Warganegara", "Devin Raihan Ferynaldo", "Difa Yusuf Adryan", "Erlangga Tirtamadani", "Fadhil Dzaki Faiz", "Farrell Athallah Vembrianto", "Fathur Arya Susena", "Fatih Adianta Hutomo", "Favian Farrel Anggara", "Fikhar AlHadi Faza", "Fulviansyah Galang Clianta", "FX. Pedro Novrianto Simbolon", "Ghamar Mahija Destama", "Imaduddin Faiz", "Jorvan Gavino", "Kautsar Naiyl Septa", "Lenita Ayuningtias", "Macauley Maheina Richard Handoko", "Muhamad Faisal Rahman", "Muhammad Ghozian Putra Barikan", "Muhammad Habib", "Muhammad Rafli Kurniawan", "Muhammad Yusuf Sintara", "Muhammad Zaki Anwar Firdaus", "Nadine Qotrunada", "Naila Afika", "Naufal Rafee Abgary", "Noven Miletano Argani Herlambang", "Okto Paul Barlin Damanik", "Paian Jonathan Situmorang", "Prabowo Adhiyatma", "Putri Oktaviani", "Radhitya Kurnia Asmara", "Raditya Adrian Nugraha", "Raihan Faiq Pratama", "Rakyan Bhumi Nagari", "Raya Nur Fikri", "Raziq Ihsan Ansori", "Sabrina Anindya Wulandari", "Sabrina Haqni Naura Arif", "Safwa Zafira Kinasih", "Sifia Engga Fahsyenka", "Sultan Gustin Ritonga", "Victo Haidar Pramudya"));
        put("C", Arrays.asList("Cici", "Cahya", "Candra"));
        put("D", Arrays.asList("Deni", "Dita", "Damar"));
    }};

    private final Map<Integer> tanggalBulan = new HashMap<>() {{
        put("Januari", for (int i = 1; i <= 31; i++) comboBox5.addItem(String.valueOf(i));)

    }};

    public VencoCash() {

        tableModel = new DefaultTableModel(
                new String[]{"Tanggal", "Bulan", "Nama", "Kelas", "Jumlah", "Via"}, 0
        );

        // isi tabel dari file (dataKasGlobal)
        for (String[] row : dataKas) {
            tableModel.addRow(row);
        }

        // isi comboBox tanggal
        comboBox5.removeAllItems();
        for (int i = 1; i <= 31; i++) comboBox5.addItem(String.valueOf(i));

        // isi comboBox bulan
        comboBox1.removeAllItems();
        String[] bulanList = {
                "Januari","Februari","Maret","April","Mei","Juni",
                "Juli","Agustus","September","Oktober","November","Desember"
        };
        for (String b : bulanList) comboBox1.addItem(b);

        // isi comboBox kelas
        comboBox3.removeAllItems();
        comboBox3.addItem(" ");
        comboBox3.addItem("A");
        comboBox3.addItem("B");
        comboBox3.addItem("C");
        comboBox3.addItem("D");

        //via
        comboBox2.removeAllItems();
        comboBox2.addItem("Cash");
        comboBox2.addItem("QRIS");

        //bulan
        comboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int bulanDipilih = comboBox1.getSelectedItem().toString();
                currentRow[1] = bulanDipilih;

                comboBox5.removeAllItems();
                List<Integer>  listTanggal = tanggalBulan.get(bulanDipilih);
                if (ListTanggal != null) {
                    for (int n : ListTanggal) {
                        comboBox5.addItem(n);
                    }
                }
            }
        });

        //tanggal
        comboBox5.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentRow[0] = comboBox5.getSelectedItem().toString();

            }
        });

        // via
        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentRow[5] = comboBox2.getSelectedItem().toString();
            }
        });

        // listener kelas → isi nama sesuai kelas
        comboBox3.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String kelasDipilih = comboBox3.getSelectedItem().toString();
                currentRow[2] = kelasDipilih;

                comboBox4.removeAllItems();
                List<String> listNama = namaPerKelas.get(kelasDipilih);
                if (listNama != null) {
                    for (String n : listNama) {
                        comboBox4.addItem(n);
                    }
                }
            }
        });

        // listener nama
        comboBox4.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && comboBox4.getSelectedItem() != null) {
                currentRow[3] = comboBox4.getSelectedItem().toString();
            }
        });

        // listener jumlah
        jumlahField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                String jumlah = jumlahField.getText();
                if (jumlah != null && !jumlah.isEmpty()) {
                    currentRow[4] = jumlah;
                }
            }
        });

        // tombol simpan
        simpanButton.addActionListener(e -> {
            String tanggal = (String) comboBox5.getSelectedItem();
            String bulan = (String) comboBox1.getSelectedItem();
            String kelas = (String) comboBox3.getSelectedItem();
            String nama = (String) comboBox4.getSelectedItem();
            String via = (String) comboBox2.getSelectedItem();

            String jumlah = jumlahField.getText()
                    .replace("Rp", "")
                    .replace(".", "")
                    .replace(",", "")
                    .trim();

            //pengkondisian
            if (tanggal == null || tanggal.trim().isEmpty() ||
                    bulan   == null || bulan.trim().isEmpty()   ||
                    jumlah  == null || jumlah.trim().isEmpty()  ||
                    kelas   == null || kelas.trim().isEmpty()   ||
                    nama    == null || nama.trim().isEmpty()) {

                JOptionPane.showMessageDialog(mainPanel,
                        "Tanggal, Bulan, Jumlah, Kelas, dan Nama harus diisi.");
                return;
            }

            String[] row = {tanggal, bulan, kelas, nama, jumlah, via};
            dataKas.add(row);
            tableModel.addRow(row);
            DataStore.saveData(); // simpan ke file
            jumlahField.setText("");
        });

        // tombol lihat tabel
        lihatTabelButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            frame.dispose();

            JFrame laporanFrame = new JFrame("Laporan Kas");
            laporanFrame.setContentPane(new TabelKas(dataKas).LaporanPanel);
            laporanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            laporanFrame.pack();
            laporanFrame.setVisible(true);
        });

        // tombol keluar
        keluarButton.addActionListener(e -> {
            DataStore.saveData(); // simpan sebelum keluar
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            frame.dispose();
        });
    }

    private void createUIComponents() {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(indonesia);
        jumlahField = new JFormattedTextField(rupiahFormat);
    }

    public static void main(String[] args) {
        DataStore.loadData(); // load data dari file

        JFrame frame = new JFrame("VencoCash");
        frame.setContentPane(new VencoCash().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
