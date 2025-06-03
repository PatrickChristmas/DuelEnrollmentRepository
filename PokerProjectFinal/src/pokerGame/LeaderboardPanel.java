package pokerGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * The LeaderboardPanel class is a JPanel component used to display a leaderboard
 * of both users and AI players, sorted by elo
 * @author Patrick Christmas 
 * @version May 31 2025
 */
public class LeaderboardPanel extends JPanel {

    /**
     * constructs a LeaderboardPanel displaying a table of users and AI players
     * sorted by elo
     * @param users        list of User objects to display
     * @param aiPlayers    list of AIPlayer objects to display
     * @param backListener ActionListener for the back button
     */
    public LeaderboardPanel(ArrayList<User> users, ArrayList<AIPlayer> aiPlayers, ActionListener backListener) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 40, 49)); // dark background

        // Title label
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Player", "Type", "Wins", "Losses", "ELO"};
        Object[][] data = buildTableData(users, aiPlayers);

        JTable table = new JTable(data, columns);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.setEnabled(false);
        table.setGridColor(new Color(85, 85, 85));
        table.setBackground(new Color(57, 62, 70));
        table.setForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setBackground(new Color(0, 128, 0)); // poker green
        header.setForeground(Color.WHITE);

        // Center alignment for cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(57, 62, 70));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("⬅ Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 128, 0)); 
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(backListener);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(34, 40, 49));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * builds and returns the data for the leaderboard table by combining and sorting
     * users and AI players by elo. STACK OVERFLOW SAMPLE!! Check works cited 
     *
     * @param users     list of User objects
     * @param aiPlayers list of AIPlayer objects
     * @return 2D Object array for JTable input
     */
    private Object[][] buildTableData(ArrayList<User> users, ArrayList<AIPlayer> aiPlayers) {
        ArrayList<Object[]> rows = new ArrayList<>();

        for (User u : users) {
            rows.add(new Object[]{u.getUser(), "User", u.getWins(), u.getLosses(), u.getElo()});
        }

        for (AIPlayer ai : aiPlayers) {
            rows.add(new Object[]{ai.getName(), "AI", ai.getWins(), ai.getLosses(), ai.getElo()});
        }

        rows.sort(Comparator.comparingInt((Object[] row) -> (Integer) row[4]).reversed());

        Object[][] data = new Object[rows.size()][5];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    /**
     * Returns a string representation of the leaderboard panel.
     *
     * @return a simple string description
     */
    @Override
    public String toString() {
        return "LeaderboardPanel[Displays sorted leaderboard of Users and AIs by ELO]";
    }
}
