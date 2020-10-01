package langton_ant.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import langton_ant.model.*;

public class Controller {
    private Movement timer;
    World world;
    @FXML
    Pane pane;
    @FXML
    public void initialize(){
        world = new World();
        timer = new Movement();
        pane.setBackground(new Background(new BackgroundFill(Color.GREY,null,null)));
    }



    @FXML
    Button startButton;

    @FXML
          Button   stepButton;

    @FXML
    Button randomButton;

    @FXML
    public void randomButtonFunction(){
            /*world.setGenerateRandomActiveCell();*/
    }

    @FXML
    ToggleButton startStopButton;

    @FXML
    public void setStartStopButtonFunction(){
        if (startStopButton.isSelected()) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    @FXML
    public void setpButtonFucntion(){
        step();

    }

    @FXML
    Button allCellsActiveButton;

    public void allActiveButtonFuction(){
//        world.activateEveryCell();
    }


    @FXML
    public void startButtonAction(){
        int numberOfCells = world.calculateNumberOfCells(pane.getWidth());
        System.out.println(numberOfCells);
        world.initializeCells(numberOfCells);

        world.drawCellOnThePane(numberOfCells, pane);


    }



    @FXML
    public void getClik(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        world.loopCellsFindTheOneClickedOn(x,y);
    }

    private class Movement extends AnimationTimer {

        final long FRAMES_PER_SECOND = 1000L;
        long INTERVAL = 1_00_000_000L / FRAMES_PER_SECOND;
        long last = 0;
//        int tick =0;
        @Override
        public void handle(long now) {
            if (now - last >= INTERVAL) {
                last = now;
                step();
            }
        }
    }

    private void step(){
        world.step();
    }
}
