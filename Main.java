package uas_pak_ipul;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Parent class: Buku
class Buku {
    String judul;
    String namaPengarang;
    String penerbit;
    int tahunCetak;
    char kategori;

    public Buku(String judul, String namaPengarang, String penerbit, int tahunCetak, char kategori) {
        this.judul = judul;
        this.namaPengarang = namaPengarang;
        this.penerbit = penerbit;
        this.tahunCetak = tahunCetak;
        this.kategori = kategori;
    }

    public String getKategoriString() {
        switch (kategori) {
            case 's': return "Semua Umur";
            case 'r': return "Remaja";
            case 'd': return "Dewasa";
            case 'a': return "Anak";
            default: return "Tidak Diketahui";
        }
    }
}

// Child class: TokoBuku
class TokoBuku extends Buku {
    public TokoBuku(String judul, String namaPengarang, String penerbit, int tahunCetak, char kategori) {
        super(judul, namaPengarang, penerbit, tahunCetak, kategori);
    }
}

public class Main extends JFrame {
    private JTextField judulField, pengarangField, penerbitField, tahunField;
    private JComboBox<String> kategoriBox;
    private DefaultTableModel model;

    public Main() {
        setTitle("Aplikasi Toko Buku");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBackground(Color.LIGHT_GRAY);

        inputPanel.add(new JLabel("Judul:"));
        judulField = new JTextField();
        inputPanel.add(judulField);

        inputPanel.add(new JLabel("Nama Pengarang:"));
        pengarangField = new JTextField();
        inputPanel.add(pengarangField);

        inputPanel.add(new JLabel("Penerbit:"));
        penerbitField = new JTextField();
        inputPanel.add(penerbitField);

        inputPanel.add(new JLabel("Tahun Cetak:"));
        tahunField = new JTextField();
        inputPanel.add(tahunField);

        inputPanel.add(new JLabel("Kategori:"));
        kategoriBox = new JComboBox<>(new String[]{"s", "r", "d", "a"});
        inputPanel.add(kategoriBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.CYAN);
        JButton addButton = new JButton("Tambah Buku");
        JButton deleteButton = new JButton("Hapus Buku");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        model = new DefaultTableModel(new String[]{"Judul", "Nama Pengarang", "Penerbit", "Tahun Cetak", "Kategori"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Pilih baris yang akan dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void addBook() {
        String judul = judulField.getText();
        String namaPengarang = pengarangField.getText();
        String penerbit = penerbitField.getText();
        int tahunCetak;
        try {
            tahunCetak = Integer.parseInt(tahunField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun Cetak harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        char kategori = kategoriBox.getSelectedItem().toString().charAt(0);

        TokoBuku buku = new TokoBuku(judul, namaPengarang, penerbit, tahunCetak, kategori);
        model.addRow(new Object[]{buku.judul, buku.namaPengarang, buku.penerbit, buku.tahunCetak, buku.getKategoriString()});

        clearFields();
    }

    private void clearFields() {
        judulField.setText("");
        pengarangField.setText("");
        penerbitField.setText("");
        tahunField.setText("");
        kategoriBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
