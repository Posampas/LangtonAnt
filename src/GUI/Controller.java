package sample;

import Model.World;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;



public class Controller {
    private Movement timer;
    World world;
    @FXML
    Pane pane;
    @FXML
    public void initialize(){
        world = new World(pane);
        timer = new Movement();
    }

    @FXML
    Button startButton;

    @FXML
          Button   stepButton;

    @FXML
    Button randomButton;

    @FXML
    public void randomButtonFunction(){
            world.setGenerateRandomActiveCell();
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

        world.step();
    }

    @FXML
    Button allCellsActiveButton;

    public void allActiveButtonFuction(){
        world.activateEveryCell();
    }


    @FXML
    public void startButtonAction(){
        world.initializeCells(pane.getWidth(),pane.getHeight());
    }

    @FXML
    public void getClik(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        world.loopCellsFindTheOneClickeOn(x,y);
    }

    private class Movement extends AnimationTimer {

        final long FRAMES_PER_SECOND = 50L;
        long INTERVAL = 1_00_000_000L / FRAMES_PER_SECOND;
        long last = 0;
        int tick =0;
        @Override
        public void handle(long now) {
            if (now - last >= INTERVAL) {
                last = now;
                step(tick ++);
            }
        }
    }

    private void step(int tick){
        world.step();
    }
}
