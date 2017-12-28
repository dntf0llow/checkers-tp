package client.gui;

import common.Cell;
import common.PlayerTag;
import common.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.HashMap;
import java.util.Map;

public class DrawEngine {
  public static final int SIZE = 20;
  public static final double HEIGHT = SIZE * 2;
  public static final double WIDTH = Math.sqrt(3)/2 * HEIGHT;

  Color[] colors = new Color[]{Color.TRANSPARENT, Color.LIGHTGRAY, Color.LIGHTGREEN, Color.LIGHTPINK, Color.LIGHTCORAL, Color.YELLOWGREEN, Color.STEELBLUE};
  Map<PlayerTag, Color> playerColors = new HashMap<>();

  private Polygon polygons[][];

  private GameScene scene;
  private Window window;

  public DrawEngine(Window window) {
    this.window = window;
    int i = 0;
    for (PlayerTag tag : PlayerTag.values()) {
      playerColors.put(tag, colors[i++]);
    }
  }

  public void onMove(Cell from, Cell to){
    polygons[(int)to.getPoint().getQ()][(int)to.getPoint().getR()].setFill(playerColors.get(from.getOwner()));
    polygons[(int)from.getPoint().getQ()][(int)from.getPoint().getR()].setFill(Color.WHITE);
    to.setOwner(from.getOwner());
    from.setOwner(PlayerTag.NONE);
  }

  public void selectCell(int x, int y) {
    polygons[x][y].setStroke(Color.RED);
    polygons[x][y].setStrokeWidth(4);
  }

  public void deselectCell(int x, int y) {
    polygons[x][y].setStroke(Color.BLACK);
    polygons[x][y].setStrokeWidth(1);
  }

  public void startGameGUI(int width, int height, Cell[][] board) {
    prepareBoard(width, height, board);
    scene = new GameScene((int) (width * WIDTH), (int) (height * HEIGHT));
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++) {
        if(polygons[x][y] != null)
        scene.root.getChildren().add(polygons[x][y]);
      }
    }

    scene.scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
      window.onMouseClicked(e);
    });

    scene.scene.addEventFilter(MouseEvent.MOUSE_ENTERED, en -> {
      window.onMouseEntered(en);
    });

    scene.scene.addEventFilter(MouseEvent.MOUSE_EXITED, ex -> {
      window.onMouseExited(ex);
    });

    window.setScene(scene.scene);
  }

  private void prepareBoard(int width, int height, Cell[][] board) {
    polygons = new Polygon[width][height];
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++) {
        if(board[x][y] == null)
          continue;
        Polygon poly = getDrawableShape(x, y);
        poly.setFill(playerColors.get(board[x][y].getOwner()));
        poly.setStroke(Color.BLACK);
        polygons[x][y] = poly;
      }
    }
  }

  public Polygon getDrawableShape(int x, int y) {
    Polygon poly = new Polygon();
    for (int i = 0; i < 6; i ++) {
      int angleDeg = 60 * i + 30;
      double angleRad = Math.PI / 180 * angleDeg;
      double centerX = x * WIDTH + WIDTH / 2 * y - 5.5 * WIDTH;
      double centerY = SIZE + y * HEIGHT * 3/4 ;
      Point p = new Point(centerX + SIZE * Math.cos(angleRad), centerY + SIZE * Math.sin(angleRad));
      poly.getPoints().addAll(p.getQ(), p.getR());
    }
    return poly;
  }

}
