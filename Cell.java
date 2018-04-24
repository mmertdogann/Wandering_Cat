package mert;

import javafx.scene.paint.Color;

public class Cell {
	public int type;
	public Color cellColor;

	public Cell(int type) {
		this.type = type;
		if (this.type == 1) {
			cellColor = Color.DODGERBLUE;
		} else {
			cellColor = Color.WHITE;
		}
	}
}
