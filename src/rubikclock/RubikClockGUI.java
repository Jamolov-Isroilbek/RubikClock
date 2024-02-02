package rubikclock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class RubikClockGUI extends JFrame implements ActionListener {

    private static final int SIZE = 3; // The size of the grid
    private static final int MAX_VALUE = 12; // The maximum value of the clock
    private static final int MIN_VALUE = 1; // The minimum value of the clock
    private static final int BUTTON_SIZE = 50; // The size of the button
    private static final int CLOCK_SIZE = 100; // The size of the clocks
    private static final int GAP = 10; // The gap between the components
    private static final int FRAME_WIDTH = SIZE * CLOCK_SIZE / (SIZE + 2) * GAP; // The width of the frame
    private static final int FRAME_HEIGHT = SIZE * CLOCK_SIZE / (SIZE + 2) * GAP; // The height of thef rame
    private static final String TITLE = "Rubic Clock"; // The title of the frame
    private static final Font CLOCK_FONT = new Font("MV Boli", Font.BOLD, 36);
    private static final Color CLOCK_COLOR = Color.WHITE; // The color for the clocks
    private static final Color BUTTON_COLOR = Color.LIGHT_GRAY; // The color for the buttons
    private boolean initializing = true; // New flag

    // Variables for the game components
    private final JLabel[][] clocks; // The array of lables for the clocks
    private final JButton[] buttons; // The array of buttons for the corners
    private final int[][] values; // The array of values for the clocks
    private int steps; // The number of steps taken to solve the game

    public RubikClockGUI() {
        // Initialize the frame
        super(TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        GridBagConstraints constraints = new GridBagConstraints();

        // Initialize the arrays
        clocks = new JLabel[SIZE][SIZE];
        buttons = new JButton[4];
        values = new int[SIZE][SIZE];

        // Create the clocks and add them to the frame
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values[i][j] = 12;

                // Create a label for the clock
                clocks[i][j] = new JLabel(String.valueOf(values[i][j]), SwingConstants.CENTER);
                clocks[i][j].setPreferredSize(new Dimension(CLOCK_SIZE, CLOCK_SIZE));
                clocks[i][j].setFont(CLOCK_FONT);
                clocks[i][j].setForeground(CLOCK_COLOR);
                clocks[i][j].setOpaque(true);
                clocks[i][j].setBackground(Color.BLACK);
                clocks[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

                // Add the label to the frame
                constraints.gridx = j * 2;
                constraints.gridy = i * 2;
                add(clocks[i][j], constraints);
            }
        }

        // Create the buttons and add them to the frame
        for (int i = 0; i < 4; i++) {
            // Create a button for the corner
            buttons[i] = new JButton("B" + (i + 1));
            buttons[i].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            buttons[i].setBackground(BUTTON_COLOR);
            buttons[i].setBorderPainted(true);
            buttons[i].setFocusable(false);

            // Add an action listener to the button
            buttons[i].addActionListener(this);

            // Add the button to the frame
            constraints.gridx = (i % 2) * 2 + 1;
            constraints.gridy = (i / 2) * 2 + 1;
            add(buttons[i], constraints);
        }

        // Initiazlie the steps to zero
        steps = 0;

        simulateButtonClicks();

        // Make the frame visible
        setVisible(true);

    }

    /**
     * Checks if the game is over.
     *
     * @return true if all clock values are 12, false otherwise.
     */
    private boolean isGameOver() {
        // Loop through the values
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Check if the value is not 12
                if (values[i][j] != 12) {
                    // Return false
                    return false;
                }
            }
        }

        // Return true
        return true;
    }

    /**
     * Restarts the game by resetting all clock values to 12 and updating their
     * labels.
     */
    private void restartGame() {
        // Reset initizing flag
        initializing = true;

        // Reset all clock values to 12 and update their labels
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Set value to 12
                values[i][j] = 12;
                clocks[i][j].setText(String.valueOf(values[i][j]));
            }
        }

        simulateButtonClicks();

        // Reset steps to zero
        steps = 0;
    }

    /**
     * Simulates button clicks at the start of the game.
     */
    private void simulateButtonClicks() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            // Simulate a button click
            int buttonIndex = random.nextInt(4);
            simulateButtonPress(buttonIndex);
        }
        initializing = false;
    }

    /**
     * Simulates a button press, incrementing the values of the clocks in the
     * 2x2 grid associated with the button.
     *
     * @param buttonIndex the index of the button that was pressed.
     */
    private void simulateButtonPress(int buttonIndex) {
        int row = buttonIndex / 2;
        int col = buttonIndex % 2;

        incrementClock(row, col);
        incrementClock(row, col + 1);
        incrementClock(row + 1, col);
        incrementClock(row + 1, col + 1);

        if (!initializing) {
            steps++;
        }
    }

    /**
     * Increments the value of the clock at the specified row and column,
     * wrapping around to 1 if the value reaches 13.
     *
     * @param row the row of the clock.
     * @param col the column of the clock.
     * @return true if the operation is successful.
     */
    private boolean incrementClock(int row, int col) {
        // Increment the value if it's less than 12
        if (values[row][col] < MAX_VALUE) {
            values[row][col]++;
        } else {
            values[row][col] = MIN_VALUE;
        }

        // Update the label of the clock
        clocks[row][col].setText(String.valueOf(values[row][col]));

        return true;
    }

    /**
     * Handles the button clicks. When a button is clicked, it simulates a
     * button press and checks if the game is over. If the game is over, it
     * displays a message dialog showing the number of steps taken to solve the
     * game and restarts the game.
     *
     * @param e the action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int buttonIndex = 0; buttonIndex < buttons.length; buttonIndex++) {
            if (source == buttons[buttonIndex]) {
                simulateButtonPress(buttonIndex);

                if (!initializing && isGameOver()) {
                    JOptionPane.showMessageDialog(this, "You solved the game in " + steps + " steps!");
                    restartGame();
                }

                // Break the loop after the correct button is found
                break;
            }
        }
    }
}
