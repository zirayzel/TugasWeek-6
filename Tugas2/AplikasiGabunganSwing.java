import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AplikasiGabunganSwing extends JFrame {

    public AplikasiGabunganSwing() {
        setTitle("Aplikasi Gabungan Java Swing (Soal 1 - 5)");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 5. Tambahkan Menu File > Keluar dan Bantuan > Tentang pada aplikasi
        buatMenuBar();

        // Menggunakan JTabbedPane untuk menggabungkan 5 soal dalam 1 jendela
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("1. Layout", buatPanelSoal1());
        tabbedPane.addTab("2. Login", buatPanelSoal2());
        tabbedPane.addTab("3. Belanja", buatPanelSoal3());
        tabbedPane.addTab("4. Jam Digital", buatPanelSoal4());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // --- SOAL 1: FlowLayout ke GridLayout ---
    private JPanel buatPanelSoal1() {
        JPanel panelUtama = new JPanel(new BorderLayout());
        JPanel panelTombol = new JPanel(new FlowLayout());
        
        panelTombol.add(new JButton("Tombol 1"));
        panelTombol.add(new JButton("Tombol 2"));
        panelTombol.add(new JButton("Tombol 3"));

        JButton btnUbah = new JButton("Ubah ke GridLayout (1x3)");
        final boolean[] isGrid = {false};

        btnUbah.addActionListener(e -> {
            if (!isGrid[0]) {
                panelTombol.setLayout(new GridLayout(1, 3, 5, 5));
                btnUbah.setText("Kembalikan ke FlowLayout");
            } else {
                panelTombol.setLayout(new FlowLayout());
                btnUbah.setText("Ubah ke GridLayout (1x3)");
            }
            isGrid[0] = !isGrid[0];
            panelTombol.revalidate();
            panelTombol.repaint();
        });

        panelUtama.add(panelTombol, BorderLayout.CENTER);
        panelUtama.add(btnUbah, BorderLayout.SOUTH);
        return panelUtama;
    }

    // --- SOAL 2: Form Login GridBagLayout ---
    private JPanel buatPanelSoal2() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField txtUser = new JTextField(15);
        panel.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JPasswordField txtPass = new JPasswordField(15);
        panel.add(txtPass, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JButton btnMasuk = new JButton("Masuk");
        panel.add(btnMasuk, gbc);

        btnMasuk.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selamat datang, " + user + "!", "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    // --- SOAL 3: Daftar Belanja ---
    private JPanel buatPanelSoal3() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelAtas = new JPanel(new BorderLayout(5, 5));
        JTextField txtItem = new JTextField();
        JButton btnTambah = new JButton("Tambah");
        panelAtas.add(txtItem, BorderLayout.CENTER);
        panelAtas.add(btnTambah, BorderLayout.EAST);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listBelanja = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listBelanja);

        JButton btnHapus = new JButton("Hapus Terpilih");

        btnTambah.addActionListener(e -> {
            String item = txtItem.getText().trim();
            if (!item.isEmpty()) {
                listModel.addElement(item);
                txtItem.setText("");
                txtItem.requestFocus();
            }
        });

        btnHapus.addActionListener(e -> {
            int selectedIndex = listBelanja.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih item yang ingin dihapus terlebih dahulu.");
            }
        });

        panel.add(panelAtas, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnHapus, BorderLayout.SOUTH);
        return panel;
    }

    // --- SOAL 4: Jam Digital dengan Timer ---
    private JPanel buatPanelSoal4() {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel lblJam = new JLabel();
        lblJam.setFont(new Font("Monospaced", Font.BOLD, 36));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblJam.setText(LocalTime.now().format(formatter));

        Timer timer = new Timer(1000, e -> lblJam.setText(LocalTime.now().format(formatter)));
        timer.start();

        panel.add(lblJam);
        return panel;
    }

    // --- SOAL 5: Menu Bar (File > Keluar & Bantuan > Tentang) ---
    private void buatMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem itemKeluar = new JMenuItem("Keluar");
        itemKeluar.addActionListener(e -> System.exit(0));
        menuFile.add(itemKeluar);

        JMenu menuBantuan = new JMenu("Bantuan");
        JMenuItem itemTentang = new JMenuItem("Tentang");
        itemTentang.addActionListener(e -> JOptionPane.showMessageDialog(this, "Aplikasi Gabungan Swing\nVersi 1.0", "Tentang Aplikasi", JOptionPane.INFORMATION_MESSAGE));
        menuBantuan.add(itemTentang);

        menuBar.add(menuFile);
        menuBar.add(menuBantuan);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiGabunganSwing().setVisible(true));
    }
}