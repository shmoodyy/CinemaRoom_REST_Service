package cinema.businesslayer;

public class CinemaSeat {

    private int row;
    private int column;
    private int price;

    CinemaSeat() {}

    public CinemaSeat(int row, int column) {
        this.row = row;
        this.column = column;
        price = row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}