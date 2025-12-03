package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TabelKas {
    public JPanel LaporanPanel;
    public JScrollPane LaporanPanel2;
    public JScrollPane laporanTable;
    public JTable LaporanTable;
    public JComboBox<String> bulancomboBox1;
    public JButton kembaliButton;
    public JButton keluarButton;
    private JButton resetButton;
    public DefaultTableModel laporanModel;
    private ArrayList<String[]> dataKas = DataStore.dataKasGlobal;

    public TabelKas(ArrayList<String[]> dataKas) {
        this.dataKas = DataStore.dataKasGlobal;

        laporanModel = new DefaultTableModel(
                new String[]{"Tanggal", "Bulan", "Nama", "Kelas", "Jumlah", "Via"}, 0
        );
        LaporanTable.setModel(laporanModel);

        bulancomboBox1.setModel(new DefaultComboBoxModel<>(new String[]{
                "Semua", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        }));

        tampilkanData("Semua");

        // filter bulan
        bulancomboBox1.addActionListener(e -> {
            String bulanDipilih = (String) bulancomboBox1.getSelectedItem();
            tampilkanData(bulanDipilih);
        });

        // tombol kembali → buka form input
        kembaliButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LaporanPanel);
            frame.dispose();

            JFrame inputFrame = new JFrame("VencoCash");
            VencoCash venco = new VencoCash();

            for (String[] row : dataKas) {
                venco.tableModel.addRow(row);
            }

            inputFrame.setContentPane(venco.mainPanel);
            inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            inputFrame.pack();
            inputFrame.setVisible(true);
        });

        // tombol keluar → simpan data sebelum tutup
        keluarButton.addActionListener(e -> {
            DataStore.saveData(); // simpan data terakhir
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LaporanPanel);
            frame.dispose();
        });

        // tombol reset → hapus data permanen
        resetButton.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(
                    LaporanPanel,
                    "Yakin ingin menghapus semua data?",
                    "Konfirmasi Reset",
                    JOptionPane.YES_NO_OPTION
            );

            if (konfirmasi == JOptionPane.YES_OPTION) {
                dataKas.clear();
                laporanModel.setRowCount(0);
                DataStore.saveData(); // simpan kondisi kosong ke file
            }
        });
    }

    private void tampilkanData(String bulanFilter) {
        List<String[]> list = new ArrayList<>(dataKas);
        sortDataKas(list);

        laporanModel.setRowCount(0);
        for (String[] row : list) {
            String bulan = row[1];
            if (bulanFilter.equals("Semua") || bulan.equalsIgnoreCase(bulanFilter)) {
                laporanModel.addRow(row);
            }
        }
    }

    private void sortDataKas(List<String[]> list) {
        String[] bulanList = {"Januari","Februari","Maret","April","Mei","Juni",
                "Juli","Agustus","September","Oktober","November","Desember"};

        list.sort((a, b) -> {
            String bulanA = a[1];
            String bulanB = b[1];
            int tanggalA = Integer.parseInt(a[0]);
            int tanggalB = Integer.parseInt(b[0]);

            int indexA = java.util.Arrays.asList(bulanList).indexOf(bulanA);
            int indexB = java.util.Arrays.asList(bulanList).indexOf(bulanB);

            if (indexA != indexB) {
                return Integer.compare(indexA, indexB); // urut bulan
            } else {
                return Integer.compare(tanggalA, tanggalB); // urut tanggal
            }
        });
    }
}
