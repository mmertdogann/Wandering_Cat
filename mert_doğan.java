/*
 * Author : Mert Doðan
 * Date : 17.12.2017
 * Program  simulate a randomly walking cat in a 2D discrete world. World consists of a 2D array of cells. Input text file (world.txt) contains the world information which is 0 lands, 1 sea.
 */
package mert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class mert_doðan extends Application {
	static int row = 50;
	static int col = 50;

	@Override
	public void start(Stage stage) throws FileNotFoundException {
		// parsing
		Cell[][] myMap = readingFileAndCreateMap("world.txt");
		// creation of cat
		Cat myCat = new Cat(Color.ORANGE);
		// simulation
		for (int i = 0; i < 100000; i++) {
			moveOneStep(myCat, myMap);
		}
		// animation

		stage.setTitle("Wandering Cat");
		Scene scene = new Scene(new Group(), (5 + row) * 12, (col + 5) * 12);
		scene.setFill(Color.GHOSTWHITE);

		TilePane tile = new TilePane();

		tile.setPrefColumns(col);
		tile.setPrefRows(row);
		for (int j = 0; j < row; j++)
			for (int i = 0; i < col; i++) {
				Rectangle r = new Rectangle(12, 12);
				r.setFill(myMap[j][i].cellColor);
				r.setStroke(Color.GREY);
				tile.getChildren().add(r);
			}

		Group root = (Group) scene.getRoot();
		root.getChildren().add(tile);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	private Cell[][] readingFileAndCreateMap(String worldtxt) throws FileNotFoundException {
		Cell[][] map = new Cell[row][col];
		Scanner input = new Scanner(new File(worldtxt));
		int row = 0;
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String[] cell = line.split("\t");
			for (int i = 0; i < col; i++) {
				map[row][i] = new Cell(Integer.parseInt(cell[i]));
			}
			row++;
		}
		return map;
	}

	private void moveOneStep(Cat myCat, Cell[][] myMap) {
		Random rnd = new Random();
		int[] x = { 0, 0, 1, 1, 1, -1, -1, -1 };
		int[] y = { 1, -1, 0, 1, -1, 0, 1, -1 };
		int randomNumber = Math.abs(rnd.nextInt() % 8);

		while ((!(myCat.col + y[randomNumber] < col && myCat.col + y[randomNumber] >= 0)
				|| !(myCat.row + x[randomNumber] < row && myCat.row + x[randomNumber] >= 0))
				|| (myMap[myCat.row + x[randomNumber]][myCat.col + y[randomNumber]].type == 1)) {
			randomNumber = Math.abs(rnd.nextInt() % 8);
		}
		myMap[myCat.row + x[randomNumber]][myCat.col + y[randomNumber]].cellColor = myCat.catColor;
		myCat.row = myCat.row + x[randomNumber];
		myCat.col = myCat.col + y[randomNumber];
	}

}
