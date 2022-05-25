package Model;

public class AreaJuego {

    /**
     * Default constructor
     */
    public AreaJuego() {
    }

    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;

    public AreaJuego(int xMax , int xMin, int yMax, int yMin) {
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
    }

    public int getXMax() {
        return this.xMax;
    }

    public int getXMin() {
        return this.xMin;
    }

    public int getYMax() {
        return this.yMax;
    }

    public int getYMin() {
        return this.yMin;
    }

}