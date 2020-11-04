package langton_ant.model;

import javafx.scene.layout.Pane;


import java.util.Arrays;

public class World {
    private Cell[][] cells;

    private static final int CELL_WIDTH = 2;
    private int[] antPreviousPos;
    private int[] antCurrentPos;

    public int[] getAntPreviousPos() {
        return antPreviousPos;
    }

    public int[] getAntCurrentPos() {
        return antCurrentPos;
    }

    public void setAntPreviousPos(int x , int y ) {
        this.antPreviousPos = new int[]{x,y};
    }

    public void setAntCurrentPos(int x , int y) {
        this.antCurrentPos = new int[]{x,y};
    }

    public World() {
    }

    public void initializeCells(int numberOfCells) {
        cells = new Cell[numberOfCells][numberOfCells];
        for (int i = 0; i <numberOfCells ; i++) {
            for (int j = 0; j < numberOfCells ; j++) {
                cells[i][j] = new Cell();
            }
        }
    }


    public int calculateNumberOfCells(double paneWidth) {
        return (int) (paneWidth / CELL_WIDTH);
    }

    public void drawCellOnThePane(int numberOfCells, Pane pane) {
        int startPosY = 0;
        for (int i = 0; i < numberOfCells; i++) {
            int startPosX = 0;

            for (int j = 0; j < numberOfCells; j++) {
                cells[i][j].draw(startPosX + 0.1, startPosY + 0.1, CELL_WIDTH, CELL_WIDTH);
                pane.getChildren().add(cells[i][j].getRect());
                pane.getChildren().add(cells[i][j].getAnt());

                startPosX += CELL_WIDTH;
            }
            startPosY += CELL_WIDTH;
        }
    }


    public void loopCellsFindTheOneClickedOn(double x, double y) {
        for (int i = 0; i < cells[i].length - 1; i++) {
            for (int j = 0; j < cells.length - 1; j++) {
                ifThatCellWasClickedActivateIt(cells[i][j], x, y);
            }
        }
    }

    private void ifThatCellWasClickedActivateIt(Cell cell, double clickX, double clickY) {
        if (cell.isClickedOn(clickX, clickY)) {
            cell.activate();
        }
    }

    public void step() {
        // 1.Determine ant direction
        String direction = determineAntDirection();
        // 2.Determine the state of the cell
        determineStateOfCells(direction);
    }

    private String determineAntDirection() {
        randomizeAntPosition();
        return calculateDirection();
    }

    private void randomizeAntPosition() {
        if (antCurrentPos == null) {
            antCurrentPos = setRandomPosition();
            cells[antCurrentPos[0]][antCurrentPos[1]].deactivate();
        }
        if (antPreviousPos == null) {
            antPreviousPos = setRandomPreviousPosition();
        }

    }

    private int[] setRandomPosition() {
        int[] position = new int[2];
        position[0] = (int) (Math.random() * (cells.length - 1));
        position[1] = (int) (Math.random() * (cells.length - 1));
        return position;
    }

    private int[] setRandomPreviousPosition() {
        int i = 0;
        int j = 0;
        if ((int) (Math.random() * 2) == 1) {
            if ((int) (Math.random() * 2) == 1) {
                i = 1;
            } else {
                i = -1;
            }
        } else {

            if ((int) (Math.random() * 2) == 1) {
                j = 1;
            } else {
                j = -1;
            }
        }
        return new int[]{antCurrentPos[0] + i, antCurrentPos[1] + j};
    }

    private String calculateDirection() {
        String direction = "";
        if (antCurrentPos[0] == antPreviousPos[0]) {
            if ((antCurrentPos[1] == antPreviousPos[1] - 1) ||
                    (antCurrentPos[1] == (cells.length - 1) && antPreviousPos[1] == 0)) {
                direction = "North";
            } else if ((antCurrentPos[1] == antPreviousPos[1] + 1) ||
                    (antCurrentPos[1] == 0 && antPreviousPos[1] == (cells.length - 1))) {
                direction = "South";
            }
        } else if (antCurrentPos[1] == antPreviousPos[1]) {
            if ((antCurrentPos[0] == antPreviousPos[0] + 1) ||
                    (antCurrentPos[0] == 0 && antPreviousPos[0] == (cells.length - 1))) {
                direction = "East";
            } else if ((antCurrentPos[0] == antPreviousPos[0] - 1) ||
                    (antCurrentPos[0] == (cells.length - 1) && antPreviousPos[0] == 0)) {
                direction = "West";
            }
        }
        System.out.println(direction);
        System.out.println(Arrays.toString(antPreviousPos) + " --->" + Arrays.toString(antCurrentPos));
        return direction;
    }

    private void determineStateOfCells(String direction) {
        int[][] neighbourhood = getNeighbourhood(direction);
        transitionFunction(neighbourhood);

    }

    private void transitionFunction(int[][] neighbourhood) {
        Cell cell = cells[antCurrentPos[0]][antCurrentPos[1]];
        if (cell.isActive) {
            antPreviousPos = antCurrentPos;
            antCurrentPos = neighbourhood[0];
            cell.deactivate();


        } else {
            antPreviousPos = antCurrentPos;
            antCurrentPos = neighbourhood[1];
            cell.activate();
        }
        cell.setAnt(false);
        cells[antCurrentPos[0]][antCurrentPos[1]].setAnt(true);

    }


    private int[][] getNeighbourhood(String direction) {
        int[] leftCell = new int[2];
        int[] rightCell = new int[2];
        switch (direction) {
            case "North":
                rightCell[0] = (antCurrentPos[0] - 1) == -1 ? cells.length - 1 : (antCurrentPos[0] - 1);
                leftCell[0] = (antCurrentPos[0] + 1) % (cells.length);
                leftCell[1] = rightCell[1] = antCurrentPos[1];
                break;
            case "South":
                rightCell[0] = (antCurrentPos[0] + 1) % (cells.length);
                leftCell[0] = (antCurrentPos[0] - 1) == -1 ? cells.length - 1 : (antCurrentPos[0] - 1);
                leftCell[1] = rightCell[1] = antCurrentPos[1];
                break;
            case "East":
                leftCell[0] = rightCell[0] = antCurrentPos[0];
                leftCell[1] = (antCurrentPos[1] + 1) % (cells.length);
                rightCell[1] = (antCurrentPos[1] - 1) == -1 ? cells.length - 1 : (antCurrentPos[1] - 1);

                break;
            case "West":
                leftCell[0] = rightCell[0] = antCurrentPos[0];
                leftCell[1] = (antCurrentPos[1] - 1) == -1 ? cells.length - 1 : (antCurrentPos[1] - 1);
                rightCell[1] = (antCurrentPos[1] + 1) % (cells.length);


                break;
            default:
                throw new IllegalArgumentException("Bad Ant Direction");
        }


        System.out.println("Left :" + Arrays.toString(leftCell) + " Right : " + Arrays.toString(rightCell));
        return new int[][]{leftCell, rightCell};
    }

    Cell[][] getCells(){
        return cells;
    }
}
