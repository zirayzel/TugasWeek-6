import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class DataMahasiswa extends JFrame {

    // Kolom tabel
    private final String[] KOLOM = {"NIM", "Nama", "Prodi", "Semester", "IPK"};

    private DefaultTableModel model;
    private JTable tabel;

    // Input fields
    private JTextField tfNIM, tfNama, tfIPK;
    private JComboBox<String> cbProdi;
    private JSpinner spSemester;
    private JButton btnTambah, btnUbah, btnHapus, btnBersihkan;

    public DataMahasiswa() {
        setTitle("Data Mahasiswa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);

        // ── Model & Tabel ───────────────────────────────────
        model = new DefaultTableModel(KOLOM, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // Tabel hanya bisa edit via form
            }
        };
        tabel = new JTable(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.setRowHeight(24);
        tabel.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabel.setFont(new Font("Arial", Font.PLAIN, 13));
        tabel.setGridColor(Color.LIGHT_GRAY);

        // Warna baris selang-seling
        tabel.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (sel) {
                    setBackground(new Color(173, 216, 230));
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                // Rata tengah kolom IPK & Semester
                if (col == 3 || col == 4) setHorizontalAlignment(CENTER);
                else setHorizontalAlignment(LEFT);
                return this;
            }
        });

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) isiFormDariBaris();
        });

        JScrollPane scrollPane = new JScrollPane(tabel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Data Mahasiswa", 0, 0,
                new Font("Arial", Font.BOLD, 13)));
        formPanel.setPreferredSize(new Dimension(280, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(5, 8, 5, 8);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // NIM
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("NIM :"), gbc);
        gbc.gridy = 1; tfNIM = new JTextField();
        formPanel.add(tfNIM, gbc);

        // Nama
        gbc.gridy = 2; formPanel.add(new JLabel("Nama :"), gbc);
        gbc.gridy = 3; tfNama = new JTextField();
        formPanel.add(tfNama, gbc);

        // Prodi
        gbc.gridy = 4; formPanel.add(new JLabel("Program Studi :"), gbc);
        gbc.gridy = 5;
        cbProdi = new JComboBox<>(new String[]{
            "Informatika", "Sistem Informasi", "Teknik Elektro",
            "Manajemen Informatika", "Ilmu Komputer"
        });
        formPanel.add(cbProdi, gbc);

        // Semester
        gbc.gridy = 6; formPanel.add(new JLabel("Semester :"), gbc);
        gbc.gridy = 7;
        spSemester = new JSpinner(new SpinnerNumberModel(1, 1, 14, 1));
        formPanel.add(spSemester, gbc);

        // IPK  ← KOLOM BARU
        gbc.gridy = 8; formPanel.add(new JLabel("IPK (0.00 - 4.00) :"), gbc);
        gbc.gridy = 9; tfIPK = new JTextField();
        formPanel.add(tfIPK, gbc);

        // ── Tombol ─────────────────────────────────────────
        gbc.gridy = 10; gbc.insets = new Insets(12, 8, 4, 8);
        btnTambah    = buatTombol("➕ Tambah", new Color(46, 139, 87), Color.WHITE);
        formPanel.add(btnTambah, gbc);

        gbc.gridy = 11; gbc.insets = new Insets(4, 8, 4, 8);
        btnUbah      = buatTombol("✏️ Ubah", new Color(70, 130, 180), Color.WHITE);
        formPanel.add(btnUbah, gbc);

        gbc.gridy = 12;
        btnHapus     = buatTombol("🗑️ Hapus", new Color(178, 34, 34), Color.WHITE);
        formPanel.add(btnHapus, gbc);

        gbc.gridy = 13;
        btnBersihkan = buatTombol("🔄 Bersihkan", Color.LIGHT_GRAY, Color.BLACK);
        formPanel.add(btnBersihkan, gbc);

        // ── Event Tombol ───────────────────────────────────
        btnTambah.addActionListener(e -> aksiTambah());
        btnUbah.addActionListener(e -> aksiUbah());
        btnHapus.addActionListener(e -> aksiHapus());
        btnBersihkan.addActionListener(e -> bersihkanForm());

        // ── Layout Utama ───────────────────────────────────
        JPanel panelKanan = new JPanel(new BorderLayout(5, 5));
        panelKanan.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Daftar Mahasiswa", 0, 0,
                new Font("Arial", Font.BOLD, 13)));
        panelKanan.add(scrollPane, BorderLayout.CENTER);

        // Status bar bawah
        JLabel lblTotal = new JLabel("  Total data: 0");
        lblTotal.setFont(new Font("Arial", Font.ITALIC, 11));
        panelKanan.add(lblTotal, BorderLayout.SOUTH);

        // Update total tiap tabel berubah
        model.addTableModelListener(e -> lblTotal.setText("  Total data: " + model.getRowCount()));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, panelKanan);
        splitPane.setDividerLocation(285);
        splitPane.setResizeWeight(0);

        add(splitPane, BorderLayout.CENTER);

        // Data contoh awal
        tambahDataContoh();

        setVisible(true);
    }

    // ─── Validasi & ambil data form ───────────────────────
    private boolean validasiForm() {
        if (tfNIM.getText().trim().isEmpty()) {
            pesan("NIM tidak boleh kosong!"); return false;
        }
        if (tfNama.getText().trim().isEmpty()) {
            pesan("Nama tidak boleh kosong!"); return false;
        }
        try {
            double ipk = Double.parseDouble(tfIPK.getText().trim());
            if (ipk < 0 || ipk > 4.0) {
                pesan("IPK harus antara 0.00 dan 4.00!"); return false;
            }
        } catch (NumberFormatException ex) {
            pesan("IPK harus berupa angka! Contoh: 3.75"); return false;
        }
        return true;
    }

    private Vector<Object> dataForm() {
        Vector<Object> baris = new Vector<>();
        baris.add(tfNIM.getText().trim().toUpperCase());
        baris.add(tfNama.getText().trim());
        baris.add(cbProdi.getSelectedItem());
        baris.add(spSemester.getValue());
        baris.add(String.format("%.2f", Double.parseDouble(tfIPK.getText().trim())));
        return baris;
    }

    // ─── Aksi Tambah ──────────────────────────────────────
    private void aksiTambah() {
        if (!validasiForm()) return;
        // Cek NIM duplikat
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(tfNIM.getText().trim().toUpperCase())) {
                pesan("NIM sudah ada! Gunakan NIM yang berbeda."); return;
            }
        }
        model.addRow(dataForm());
        bersihkanForm();
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses ✅", JOptionPane.INFORMATION_MESSAGE);
    }

    // ─── Aksi Ubah ──────────────────────────────────────
    private void aksiUbah() {
        int barisDipilih = tabel.getSelectedRow();
        if (barisDipilih < 0) {
            pesan("Pilih data yang ingin diubah dari tabel terlebih dahulu!"); return;
        }
        if (!validasiForm()) return;
        int konfirm = JOptionPane.showConfirmDialog(this,
                "Ubah data mahasiswa ini?", "Konfirmasi Ubah", JOptionPane.YES_NO_OPTION);
        if (konfirm != JOptionPane.YES_OPTION) return;

        Vector<Object> data = dataForm();
        for (int col = 0; col < KOLOM.length; col++) {
            model.setValueAt(data.get(col), barisDipilih, col);
        }
        bersihkanForm();
        JOptionPane.showMessageDialog(this, "Data berhasil diubah!", "Sukses ✅", JOptionPane.INFORMATION_MESSAGE);
    }

    // ─── Aksi Hapus ───────────────────────────────────────
    private void aksiHapus() {
        int barisDipilih = tabel.getSelectedRow();
        if (barisDipilih < 0) {
            pesan("Pilih data yang ingin dihapus dari tabel terlebih dahulu!"); return;
        }
        int konfirm = JOptionPane.showConfirmDialog(this,
                "Hapus data " + model.getValueAt(barisDipilih, 1) + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirm == JOptionPane.YES_OPTION) {
            model.removeRow(barisDipilih);
            bersihkanForm();
        }
    }

    // ─── Isi form saat baris tabel diklik ─────────────────
    private void isiFormDariBaris() {
        int row = tabel.getSelectedRow();
        if (row < 0) return;
        tfNIM.setText((String) model.getValueAt(row, 0));
        tfNama.setText((String) model.getValueAt(row, 1));
        cbProdi.setSelectedItem(model.getValueAt(row, 2));
        spSemester.setValue(model.getValueAt(row, 3));
        tfIPK.setText((String) model.getValueAt(row, 4));
    }

    private void bersihkanForm() {
        tfNIM.setText(""); tfNama.setText(""); tfIPK.setText("");
        cbProdi.setSelectedIndex(0);
        spSemester.setValue(1);
        tabel.clearSelection();
    }

    private void pesan(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

    private JButton buatTombol(String teks, Color bg, Color fg) {
        JButton btn = new JButton(teks);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(200, 32));
        return btn;
    }

    private void tambahDataContoh() {
        model.addRow(new Object[]{"2301001", "Budi Santoso", "Informatika", 3, "3.85"});
        model.addRow(new Object[]{"2301002", "Ani Lestari", "Sistem Informasi", 3, "3.60"});
        model.addRow(new Object[]{"2301003", "Reza Firmansyah", "Teknik Elektro", 5, "3.42"});
        model.addRow(new Object[]{"2301004", "Dewi Rahayu", "Manajemen Informatika", 1, "3.90"});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DataMahasiswa::new);
    }
}
