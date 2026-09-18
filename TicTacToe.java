import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple 2-player Tic-Tac-Toe game built with Java Swing.
 * Compile:  javac TicTacToe.java
 * Run:      java TicTacToe
 */
public class TicTacToe extends JFrame {

    private final JButton[] buttons = new JButton[9];
    private final JLabel statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
    private boolean playerXTurn = true;
    private int movesMade = 0;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(420, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Status label at the top
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(statusLabel, BorderLayout.NORTH);

        // 3x3 grid of buttons
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton("");
            btn.setFont(new Font("SansSerif", Font.BOLD, 48));
            btn.setFocusPainted(false);
            final int index = i;
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleMove(index);
                }
            });
            buttons[i] = btn;
            gridPanel.add(btn);
        }
        add(gridPanel, BorderLayout.CENTER);

        // Restart button at the bottom
        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        restartButton.addActionListener(e -> resetBoard());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(restartButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleMove(int index) {
        JButton btn = buttons[index];

        // Ignore click if cell already filled
        if (!btn.getText().isEmpty()) {
            return;
        }

        btn.setText(playerXTurn ? "X" : "O");
        btn.setForeground(playerXTurn ? new Color(0, 102, 204) : new Color(204, 0, 0));
        movesMade++;

        if (checkWinner()) {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + " wins!");
            disableAllButtons();
            return;
        }

        if (movesMade == 9) {
            statusLabel.setText("It's a draw!");
            return;
        }

        playerXTurn = !playerXTurn;
        statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s turn");
    }

    private boolean checkWinner() {
        int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] pattern : winPatterns) {
            String a = buttons[pattern[0]].getText();
            String b = buttons[pattern[1]].getText();
            String c = buttons[pattern[2]].getText();

            if (!a.isEmpty() && a.equals(b) && b.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private void disableAllButtons() {
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }
    }

    private void resetBoard() {
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
        }
        playerXTurn = true;
        movesMade = 0;
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
