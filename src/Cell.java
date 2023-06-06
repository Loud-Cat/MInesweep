import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Point;
import java.awt.Dimension;

public class Cell {
	private JButton button;
	private ImageIcon icon;
	private Point pos;

	private boolean hidden;
	private int count;

	static final int CELLWIDTH = 35;
	static final int CELLHEIGHT = 35;

	public Cell(int row, int col) {
		button = new JButton();
		button.setPreferredSize( new Dimension(CELLWIDTH, CELLHEIGHT) );
		button.setFont( button.getFont().deriveFont(20f) );

		pos = new Point(col, row);
		hidden = true;

		if ( Math.random() < 0.25 ) {
			count = -1;

			icon = new ImageIcon(
				new ImageIcon("bomb.png")
					.getImage()
					.getScaledInstance(CELLWIDTH, CELLHEIGHT, Image.SCALE_SMOOTH)
			);
		}
		else {
			count = 0;
			icon = null;
		}

	}

	public JButton getButton() {
		return button;
	}

	public Point getPosition() {
		return pos;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;

		if (hidden) {
			button.setIcon(null);
			button.setText("");
		}
		else {
			if (count > 0)
				button.setText( String.valueOf(count) );
			else if (count == 0)
				button.setText("");
			else
				button.setIcon(icon);
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isBomb() {
		return count == -1;
	}

	public boolean isBlank() {
		return count == 0;
	}

	@Override
	public String toString() {
		return String.format(
			"Cell{pos=%s, count=%d, bomb=%b}",
			pos, count, isBomb()
		);
	}
}
