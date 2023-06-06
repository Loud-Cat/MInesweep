import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

public class MainWindow extends JFrame {
	private JPanel container;
	private Cell[][] grid;

	static final int ROWS = 16;
	static final int COLS = 16;

	public MainWindow() {
		super("Minesweep");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		container = new JPanel( new GridLayout(ROWS, COLS, 2, 2) );
		grid = new Cell[ROWS][COLS];

		container.setBorder( BorderFactory.createEmptyBorder(2, 2, 2, 2) );

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Cell cell = new Cell(i, j);

				grid[i][j] = cell;
				container.add( cell.getButton() );

				cell.getButton().addActionListener(evt -> {
					Point pos = cell.getPosition();
					reveal(pos.y, pos.x);
				});
			}
		}

		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if ( !grid[r][c].isBomb() )
					updateCount(r, c);

		add(container);
		pack();
	}

	public boolean isValidIndex(int r, int c) {
		return r >= 0 && r < ROWS && c >= 0 && c < COLS;
	}

	public boolean isHidden(int r, int c) {
		return isValidIndex(r, c) && grid[r][c].isHidden();
	}

	public void updateCount(int row, int col) {
		int count = 0;

		for (int r = row - 1; r <= row + 1; r++)
			for (int c = col - 1; c <= col + 1; c++)
				if ( !(r == row && c == col) && isValidIndex(r, c) )
					if ( grid[r][c].isBomb() )
						count++;

		grid[row][col].setCount(count);
	}

	public void reveal(int row, int col) {
		if ( isHidden(row, col) ) {
			grid[row][col].setHidden(false);

			if ( grid[row][col].isBlank() ) {
				reveal(row - 1, col); // up
				reveal(row + 1, col); // down
				reveal(row, col + 1); // left
				reveal(row, col - 1); // right
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName()
				);
			}
			catch (Exception ex) {
				System.out.println("Warning: could not set L+F");
			}

			MainWindow window = new MainWindow();
			window.setVisible(true);
		});
	}
}
