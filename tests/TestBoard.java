package common;
import main.java.sample.Player;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBoard {
    List<Point> endingCells=new ArrayList<>();
    Cell[][] cells = new Cell[2][4];
    Map<PlayerTag, List<Point>> map=new HashMap<>();
    Point point = new Point(0, 0);
    Board board;

    private Board prepareBoard() {
        endingCells.add(point);
        map.put(PlayerTag.PLAYER_2, endingCells);
        cells[0][0] = new Cell(0, 0, PlayerTag.PLAYER_2);
        cells[0][1] = new Cell(0,1,PlayerTag.PLAYER_4);
        return new Board(cells, map);
    }
    @Test
    public void testIsTaken() {
       prepareBoard();
       board = prepareBoard();
       Assert.assertTrue(board.isTaken(point));
    }

    @Test
    public void testSetCellEmpty(){
        board=prepareBoard();
        Board board = new Board(cells, map);
        board.setCellEmpty(point);
        Assert.assertEquals(PlayerTag.NONE,board.board[point.getQ()][point.getR()].getOwner());
    }

    @Test
    public void testCellEmpty(){
        board=prepareBoard();
        Point point = new Point(0,0);
        board.setCellEmpty(point);
        Assert.assertEquals(PlayerTag.NONE, board.board[point.getQ()][point.getR()].getOwner());
    }


    @Test
    public void testCopyOwner(){
        board=prepareBoard();
        Point pointFrom = new Point(0,0);
        Point pointTo = new Point(0,1);
        board.copyOwner(pointFrom,pointTo);
        Assert.assertEquals(board.board[pointFrom.getQ()][pointFrom.getR()].getOwner(),
                board.board[pointTo.getQ()][pointTo.getR()].getOwner() );
        }

        @Test
        public void testGetCell(){
            board=prepareBoard();
            Assert.assertEquals(board.board[0][0],board.getCell(new Point(0,0)));
        }

        @Test
        public void testIsDiagonal(){
            board=prepareBoard();
            Point point =  new Point(2, -1);

            Assert.assertTrue(board.isDiagonal(point));
        }

        @Test
        public void testGetTmpPoin(){
            board=prepareBoard();
            Point tmpPoint=new Point(2,3);
            board.setTmpPoint(tmpPoint);
            Assert.assertEquals(tmpPoint.getQ(),board.getTmpPoint().getQ());
        }

        @Test
        public void testSetTmpPoin(){
            board=prepareBoard();
            board.setTmpPoint(new Point(2,3));
            Assert.assertEquals(2,board.getTmpPoint().getQ());
    }

    }


