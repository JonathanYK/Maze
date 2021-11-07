public class mazeStarPoint extends mazePoint {


    //private int fCost; // = Integer.MAX_VALUE;
    public int gCost;
    public int hCost;
    public boolean possibleMove = false;
    public int movesAmount;


    public int getgCost() {
        return this.gCost;
    }

    public int gethCost() {
        return this.hCost;
    }

    public int getfCost() {
        return this.gCost + this.hCost;
    }



    public boolean getPossibleMove() { return possibleMove; }
    public void setPossibleMove(boolean val) {
        this.possibleMove = val;
    }


    public mazeStarPoint(int x, int y) {
        super(x,y);

    }


    public int CalculateHcost(mazeStarPoint currPoint, mazeStarPoint extPoint) {
        return (extPoint.getX() - currPoint.getX()) + (extPoint.getY() - currPoint.getY());
    }

    public void setGcost(mazeStarPoint parentPoint) {
        this.gCost = parentPoint.getgCost() + 1;
    }


    public void setHcost(mazeStarPoint extPoint) {
        this.hCost = CalculateHcost(this, extPoint);
    }


    public boolean equals(mazeStarPoint other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
