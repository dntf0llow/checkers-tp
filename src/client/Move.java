package client;


import common.Board;
import common.Cell;

public class Move {
    Board board;
    private Cell finded;
     int q,r;


    public Move(Board board){
        this.board=board;
        finded= new Cell();

    }

    public boolean canMove(Cell from, Cell to) {
        if (cellsDistance(from, to) <= 4) {
            if (((cellsDistance(from, to) == 4) && isCellBetween(from, to))){
                return true;
            }

            else if(cellsDistance(from, to) == 2) {
                return true;
            }
        }
        return false;
    }


    private boolean isCellBetween(Cell a, Cell b) {
        if(a.getPoint().getQ()==b.getPoint().getQ()) {
            if(findCellR(a, b)==true)
                return true;
        }
        else if(a.getPoint().getR()==b.getPoint().getR()){
            if(findCellQ(a,b)){
                return true;
            }
        }
        else{
            if(find(a,b))
                return true;
        }

        return false;
    }

    private boolean find(Cell a, Cell b) {

       r=findR(a,b);
       q=findQ(a,b);

        if (findCell(q,r)){
            return true;
        }

    return false;
    }

    private boolean findCellR(Cell a, Cell b){
        r=findR(a,b);
        q=(int)a.getPoint().getQ();

        if (findCell(q,r)){
            return true;
        }
        return false;
    }

    private boolean findCellQ(Cell a, Cell b){
        q=findQ(a,b);
        r=(int)a.getPoint().getR();

        if(findCell(q,r)){
            return true;
        }

        return false;
    }


   private boolean findCell(int q,int r){
        if (board.board[q][r]!=null){
            finded=board.board[q][r];
            if(finded.getOwner()!=PlayerTag.NONE)
                return true;
        }
        return false;
    }

    private int findR(Cell a, Cell b ){

        if(a.getPoint().getR()>b.getPoint().getR())
            return (int)a.getPoint().getR()-1;
        else
            return (int)b.getPoint().getR()-1;
    }

    private int findQ(Cell a, Cell b ){

        if(a.getPoint().getQ()>b.getPoint().getQ())
            return (int)a.getPoint().getQ()-1;
        else
            return (int)b.getPoint().getQ()-1;
    }

    private double cellsDistance(Cell a, Cell b) {

        return (Math.abs(a.getPoint().getQ() - b.getPoint().getQ()) + Math.abs(a.getPoint().getR() - b.getPoint().getR()) + Math.abs(a.getPoint().getS() - b.getPoint().getS()));
    }

}


