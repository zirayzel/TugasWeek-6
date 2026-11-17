import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class EditorTeks extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File fileSaatIni = null;
    private JLabel lblStatus;

    public EditorTeks() {
        setTitle("Editor Teks - [Tanpa Judul]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 550);
        setLocationRelativeTo(null);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("File Teks (*.txt)", "txt"));

        // ── Text Area ──────────────────────────────────────
        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 8, 5, 8));
        JScrollPane scrollPane = new JScrollPane(textArea);

        // ── Menu Bar ───────────────────────────────────────
        JMenuBar menuBar = new JMenuBar();

        // Menu FILE
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic('F');

        JMenuItem miNaru = buatMenuItem("📄 Baru", KeyEvent.VK_N, ActionEvent.CTRL_MASK, e -> aksiNaru());
        JMenuItem miBuka = buatMenuItem("📂 Buka...", KeyEvent.VK_O, ActionEvent.CTRL_MASK, e -> aksiBuka());
        JMenuItem miSimpan = buatMenuItem("💾 Simpan", KeyEvent.VK_S, ActionEvent.CTRL_MASK, e -> aksiSimpan());
        JMenuItem miSimpanSebagai = new JMenuItem("💾 Simpan Sebagai...");
        miSimpanSebagai.addActionListener(e -> aksiSimpanSebagai());
        JMenuItem miKeluar = buatMenuItem("🚪 Keluar", KeyEvent.VK_Q, ActionEvent.CTRL_MASK, e -> aksiKeluar());

        menuFile.add(miNaru);
        menuFile.add(miBuka);
        menuFile.addSeparator();
        menuFile.add(miSimpan);
        menuFile.add(miSimpanSebagai);
        menuFile.addSeparator();
        menuFile.add(miKeluar);

        // Menu EDIT
        JMenu menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic('E');

        JMenuItem miPotong = buatMenuItem("✂️ Potong", KeyEvent.VK_X, ActionEvent.CTRL_MASK, e -> textArea.cut());
        JMenuItem miSalin  = buatMenuItem("📋 Salin", KeyEvent.VK_C, ActionEvent.CTRL_MASK, e -> textArea.copy());
        JMenuItem miTempel = buatMenuItem("📌 Tempel", KeyEvent.VK_V, ActionEvent.CTRL_MASK, e -> textArea.paste());
        JMenuItem miPilihSemua = buatMenuItem("🔲 Pilih Semua", KeyEvent.VK_A, ActionEvent.CTRL_MASK, e -> textArea.selectAll());

        menuEdit.add(miPotong);
        menuEdit.add(miSalin);
        menuEdit.add(miTempel);
        menuEdit.addSeparator();
        menuEdit.add(miPilihSemua);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        setJMenuBar(menuBar);

        // ── Toolbar ────────────────────────────────────────
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBorder(BorderFactory.createEtchedBorder());

        JButton tbNaru    = buatTombolToolbar("📄", "Baru (Ctrl+N)", e -> aksiNaru());
        JButton tbBuka    = buatTombolToolbar("📂", "Buka (Ctrl+O)", e -> aksiBuka());
        JButton tbSimpan  = buatTombolToolbar("💾", "Simpan (Ctrl+S)", e -> aksiSimpan());
        toolbar.add(tbNaru);
        toolbar.add(tbBuka);
        toolbar.add(tbSimpan);
        toolbar.addSeparator();
        JButton tbPotong  = buatTombolToolbar("✂️", "Potong (Ctrl+X)", e -> textArea.cut());
        JButton tbSalin   = buatTombolToolbar("📋", "Salin (Ctrl+C)", e -> textArea.copy());
        JButton tbTempel  = buatTombolToolbar("📌", "Tempel (Ctrl+V)", e -> textArea.paste());
        toolbar.add(tbPotong);
        toolbar.add(tbSalin);
        toolbar.add(tbTempel);

        // ── Status Bar ─────────────────────────────────────
        lblStatus = new JLabel("  Siap  |  Ln 1, Kol 1");
        lblStatus.setBorder(BorderFactory.createEtchedBorder());
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));

        // Update status bar saat kursor bergerak
        textArea.addCaretListener(e -> {
            try {
                int caretPos = textArea.getCaretPosition();
                int baris    = textArea.getLineOfOffset(caretPos) + 1;
                int kolom    = caretPos - textArea.getLineStartOffset(baris - 1) + 1;
                int jumlahChar = textArea.getText().length();
                lblStatus.setText("  Ln " + baris + ", Kol " + kolom + "  |  " + jumlahChar + " karakter");
            } catch (Exception ex) { /* Abaikan */ }
        });

        // ── Susun Layout ────────────────────────────────────
        add(toolbar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ─── Helper: buat menu item ───────────────────────────
    private JMenuItem buatMenuItem(String teks, int key, int mask, ActionListener al) {
        JMenuItem item = new JMenuItem(teks);
        item.setAccelerator(KeyStroke.getKeyStroke(key, mask));
        item.addActionListener(al);
        return item;
    }

    // ─── Helper: buat tombol toolbar ──────────────────────
    private JButton buatTombolToolbar(String ikon, String tooltip, ActionListener al) {
        JButton btn = new JButton(ikon);
        btn.setToolTipText(tooltip);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(40, 35));
        btn.addActionListener(al);
        return btn;
    }

    // ─── Aksi File ────────────────────────────────────────
    private void aksiNaru() {
        if (textArea.getText().isEmpty() ||
                konfirmasi("Buat file baru? Perubahan yang belum disimpan akan hilang.")) {
            textArea.setText("");
            fileSaatIni = null;
            setTitle("Editor Teks - [Tanpa Judul]");
        }
    }

    private void aksiBuka() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                fileSaatIni = fileChooser.getSelectedFile();
                String isi  = Files.readString(fileSaatIni.toPath());
                textArea.setText(isi);
                textArea.setCaretPosition(0);
                setTitle("Editor Teks - " + fileSaatIni.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal membuka file!\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void aksiSimpan() {
        if (fileSaatIni == null) {
            aksiSimpanSebagai();
        } else {
            simpanKeFile(fileSaatIni);
        }
    }

    private void aksiSimpanSebagai() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".txt"))
                file = new File(file.getAbsolutePath() + ".txt");
            fileSaatIni = file;
            simpanKeFile(fileSaatIni);
        }
    }

    private void simpanKeFile(File file) {
        try {
            Files.writeString(file.toPath(), textArea.getText());
            setTitle("Editor Teks - " + file.getName());
            JOptionPane.showMessageDialog(this, "File berhasil disimpan!", "Simpan", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan file!\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aksiKeluar() {
        if (konfirmasi("Keluar dari Editor Teks?"))
            System.exit(0);
    }

    private boolean konfirmasi(String pesan) {
        return JOptionPane.showConfirmDialog(this, pesan, "Konfirmasi",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorTeks::new);
    }
}
