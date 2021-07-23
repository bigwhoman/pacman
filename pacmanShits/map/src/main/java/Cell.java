public class Cell {
    private String type;
    private boolean cellContainsCoin = true;
    private boolean canGotoCell = false;

    public Cell(String type) {
        this.type = type;
        init(type);
    }

    private void init(String type) {
        if (type.equals("1")) {
            canGotoCell = false;
            cellContainsCoin = false;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCellContainsCoin() {
        return cellContainsCoin;
    }

    public void setCellContainsCoin(boolean cellContainsCoin) {
        this.cellContainsCoin = cellContainsCoin;
    }

    public boolean isCanGotoCell() {
        return canGotoCell;
    }

    public void setCanGotoCell(boolean canGotoCell) {
        this.canGotoCell = canGotoCell;
    }
}
