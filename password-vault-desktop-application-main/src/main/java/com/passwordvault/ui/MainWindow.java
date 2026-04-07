package com.passwordvault.ui;

import com.passwordvault.auth.session.SessionManager;
import com.passwordvault.core.model.PasswordEntry;
import com.passwordvault.core.service.PasswordService;
import com.passwordvault.data.service.SearchService;
import com.passwordvault.data.util.FilterBuilder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Person 5: UX/UI & Frontend Developer
 * 
 * Main application dashboard showing the password vault.
 */
public class MainWindow extends JFrame {
    private JTable passwordTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel statusLabel;
    private final PasswordService passwordService;

    public MainWindow() {
        this.passwordService = new PasswordService();
        initUI();
        refreshTable();
    }

    private void initUI() {
        String username = SessionManager.getInstance().getCurrentUser().username;
        setTitle("Password Vault - Welcome " + username);
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(5, 5));

        // Top Panel (Search and Toolbar)
        add(createTopPanel(), BorderLayout.NORTH);

        // Center Panel (Table)
        add(createCenterPanel(), BorderLayout.CENTER);

        // Bottom Panel (Status)
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        searchField = new JTextField(25);
        JButton searchBtn = DialogFactory.createStyledButton("Search", new Color(33, 150, 243));
        searchBtn.addActionListener(e -> performSearch());

        JButton addBtn = DialogFactory.createStyledButton("+ Add New", new Color(76, 175, 80));
        addBtn.addActionListener(e -> showAddDialog());

        panel.add(new JLabel("Search:"));
        panel.add(searchField);
        panel.add(searchBtn);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(addBtn);

        return panel;
    }

    private JScrollPane createCenterPanel() {
        String[] columns = {"Website", "Username", "Date Added"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        passwordTable = new JTable(tableModel);
        passwordTable.setRowHeight(30);
        passwordTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        return new JScrollPane(passwordTable);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("System Status: Secure & Encrypted (AES-256)");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panel.add(statusLabel, BorderLayout.WEST);

        JButton logoutBtn = DialogFactory.createStyledButton("Logout", new Color(158, 158, 158));
        logoutBtn.addActionListener(e -> {
            SessionManager.getInstance().logout();
            new LoginWindow().setVisible(true);
            this.dispose();
        });
        panel.add(logoutBtn, BorderLayout.EAST);

        return panel;
    }

    private void refreshTable() {
        int userId = SessionManager.getInstance().getCurrentUser().id;
        List<PasswordEntry> entries = passwordService.getPasswords(userId);
        updateTableModel(entries);
    }

    private void updateTableModel(List<PasswordEntry> entries) {
        tableModel.setRowCount(0);
        for (PasswordEntry entry : entries) {
            tableModel.addRow(new Object[]{
                entry.getWebsite(),
                entry.getUsername(),
                entry.getCreatedDate().toString().substring(0, 10)
            });
        }
    }

    private void performSearch() {
        int userId = SessionManager.getInstance().getCurrentUser().id;
        String query = searchField.getText();
        List<PasswordEntry> all = passwordService.getPasswords(userId);
        List<PasswordEntry> filtered = SearchService.search(all, query);
        updateTableModel(filtered);
    }

    private void showAddDialog() {
        // Simple add dialog for demonstration
        JTextField webField = new JTextField();
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        Object[] message = {
            "Website:", webField,
            "Username:", userField,
            "Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Register New Secure Entry", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String web = webField.getText();
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            String masterPass = "vault_master_key"; // In reality, this would be the user's login password
            String salt = SessionManager.getInstance().getCurrentUser().salt;
            int userId = SessionManager.getInstance().getCurrentUser().id;
            
            if (passwordService.addPassword(userId, web, user, pass, 1, masterPass, salt)) {
                DialogFactory.showInfo(this, "Success! Entry encrypted and saved.");
                refreshTable();
            } else {
                DialogFactory.showError(this, "Error: Failed to save entry.");
            }
        }
    }
}
