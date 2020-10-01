package langton_ant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    World world;

    public void setUpWorld(){
        world = new World();
        world.initializeCells(3);
    }
    @Test
    void initializeCells() {
        setUpWorld();
        assertTrue(this::loopThroughArrayOfCellsAndCheckIfAllAreInitialized);


    }
    boolean loopThroughArrayOfCellsAndCheckIfAllAreInitialized(){
        Cell[][] cells = world.getCells();

        for (int i = 0; i <cells.length ; i++) {
            for (int j = 0; j <cells.length ; j++) {
                if(cells[i][j] == null){
                    return false;
                }
            } 
        }
        return true;
    }

    @Test
    public void test() {

        setUpWorld();
        world.setAntCurrentPos(1, 1);
        world.setAntPreviousPos(1, 2);
        world.step();
        assertArrayEquals(new int[]{2, 1}, world.getAntCurrentPos());
        assertArrayEquals(new int[]{1, 1}, world.getAntPreviousPos());
    }


    @Test
    void calculateNumberOfCells() {
        setUpWorld();
        int numberOfCells = world.calculateNumberOfCells(100);
        assertTrue(numberOfCells == 20);
    }

}