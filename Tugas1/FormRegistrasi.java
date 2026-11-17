import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormRegistrasi extends JFrame {

    private JTextField tfNama, tfEmail;
    private JPasswordField pfSandi;
    private JRadioButton rbPria, rbWanita;
    private JComboBox<String> cbProdi;
    private JCheckBox cbMusik, cbOlahraga, cbGaming, cbMembaca, cbMasak;
    private JButton btnDaftar, btnReset;

    public FormRegistrasi() {
        setTitle("Form Registrasi Mahasiswa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Judul
        JLabel lblJudul = new JLabel("FORM REGISTRASI", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(lblJudul, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- NAMA ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Nama Lengkap :"), gbc);
        tfNama = new JTextField(22);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        formPanel.add(tfNama, gbc);

        // --- EMAIL ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Email :"), gbc);
        tfEmail = new JTextField(22);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        formPanel.add(tfEmail, gbc);

        // --- SANDI (dengan validasi login admin/1234) ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Sandi :"), gbc);
        pfSandi = new JPasswordField(12);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        formPanel.add(pfSandi, gbc);



        // --- GENDER ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Gender :"), gbc);
        JPanel panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbPria = new JRadioButton("Laki-laki");
        rbWanita = new JRadioButton("Perempuan");
        JRadioButton l = rbPria, p = rbWanita;   // alias sesuai syntax
        ButtonGroup g = new ButtonGroup(); g.add(l); g.add(p);
        rbPria.setSelected(true);

        // Label sapa muncul langsung saat pilih gender
        JLabel lblSapa = new JLabel("👋 Halo, Mas!");
        lblSapa.setFont(new Font("Arial", Font.ITALIC, 12));
        lblSapa.setForeground(new Color(0, 100, 180));
        panelGender.add(l);
        panelGender.add(p);
        panelGender.add(Box.createHorizontalStrut(10)); // spasi
        panelGender.add(lblSapa);

        l.addActionListener(e -> {
            if (l.isSelected()) {
                lblSapa.setText("👋 Halo, Mas!");
                lblSapa.setForeground(new Color(0, 100, 180));
            }
        });
        p.addActionListener(e -> {
            if (p.isSelected()) {
                lblSapa.setText("👋 Halo, Mbak!");
                lblSapa.setForeground(new Color(180, 0, 100));
            }
        });

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(panelGender, gbc);

        // --- PROGRAM STUDI ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Program Studi :"), gbc);
        String[] listProdi = {"-- Pilih Prodi --", "Informatika", "Sistem Informasi",
                "Teknik Elektro", "Manajemen Informatika", "Ilmu Komputer"};
        cbProdi = new JComboBox<>(listProdi);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(cbProdi, gbc);

        // --- HOBI (CheckBox) ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Hobi :"), gbc);

        JPanel panelHobi = new JPanel(new GridLayout(3, 2, 5, 2));
        cbMusik    = new JCheckBox("🎵 Musik");
        cbOlahraga = new JCheckBox("⚽ Olahraga");
        cbGaming   = new JCheckBox("🎮 Gaming");
        cbMembaca  = new JCheckBox("📖 Membaca");
        cbMasak    = new JCheckBox("🍳 Memasak");
        panelHobi.add(cbMusik);
        panelHobi.add(cbOlahraga);
        panelHobi.add(cbGaming);
        panelHobi.add(cbMembaca);
        panelHobi.add(cbMasak);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(panelHobi, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- TOMBOL ---
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnDaftar = new JButton("✅ Daftar");
        btnReset  = new JButton("🔄 Reset");
        btnDaftar.setPreferredSize(new Dimension(120, 35));
        btnReset.setPreferredSize(new Dimension(120, 35));
        btnDaftar.setBackground(new Color(46, 139, 87));
        btnDaftar.setForeground(Color.WHITE);
        btnDaftar.setFont(new Font("Arial", Font.BOLD, 13));
        panelBtn.add(btnDaftar);
        panelBtn.add(btnReset);
        mainPanel.add(panelBtn, BorderLayout.SOUTH);

        // Tombol Daftar
        btnDaftar.addActionListener(e -> tampilkanRingkasan());

        // Tombol Reset
        btnReset.addActionListener(e -> resetForm());

        add(mainPanel);
        setVisible(true);
    }

    private void tampilkanRingkasan() {
        // Validasi
        if (tfNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tfEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pfSandi.getPassword().length < 6) {
            JOptionPane.showMessageDialog(this, "Sandi minimal 6 karakter!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbProdi.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Pilih program studi terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String hobiStr = "";

        if (cbMusik.isSelected()) {
            hobiStr = hobiStr + "Musik";
        }
        if (cbOlahraga.isSelected()) {
            if (!hobiStr.isEmpty()) hobiStr = hobiStr + ", ";
            hobiStr = hobiStr + "Olahraga";
        }
        if (cbGaming.isSelected()) {
            if (!hobiStr.isEmpty()) hobiStr = hobiStr + ", ";
            hobiStr = hobiStr + "Gaming";
        }
        if (cbMembaca.isSelected()) {
            if (!hobiStr.isEmpty()) hobiStr = hobiStr + ", ";
            hobiStr = hobiStr + "Membaca";
        }
        if (cbMasak.isSelected()) {
            if (!hobiStr.isEmpty()) hobiStr = hobiStr + ", ";
            hobiStr = hobiStr + "Memasak";
        }
        if (hobiStr.isEmpty()) {
            hobiStr = "Tidak ada";
        }

        // Gender dengan syntax else if (sesuai pola yang diminta)
        String gender;
        if (rbPria.isSelected()) {
            gender = "Laki-laki";
        } else if (rbWanita.isSelected()) {
            gender = "Perempuan";
        } else {
            gender = "Belum dipilih";
        }

        // Ringkasan
        String ringkasan = String.format(
            "╔══════════════════════════════╗\n" +
            "       RINGKASAN REGISTRASI\n" +
            "╚══════════════════════════════╝\n\n" +
            "  Nama     : %s\n" +
            "  Email    : %s\n" +
            "  Sandi    : %s\n" +
            "  Gender   : %s\n" +
            "  Prodi    : %s\n" +
            "  Hobi     : %s\n",
            tfNama.getText().trim(),
            tfEmail.getText().trim(),
            "*".repeat(pfSandi.getPassword().length),
            gender,
            cbProdi.getSelectedItem(),
            hobiStr
        );

        JOptionPane.showMessageDialog(this, ringkasan,
                "Registrasi Berhasil ✅", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        tfNama.setText("");
        tfEmail.setText("");
        pfSandi.setText("");
        rbPria.setSelected(true);
        cbProdi.setSelectedIndex(0);
        cbMusik.setSelected(false);
        cbOlahraga.setSelected(false);
        cbGaming.setSelected(false);
        cbMembaca.setSelected(false);
        cbMasak.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormRegistrasi::new);
    }
}
