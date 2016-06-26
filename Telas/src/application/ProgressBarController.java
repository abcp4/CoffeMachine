package application;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class ProgressBarController {
    private Task copyWorker;

    @FXML
    private ProgressBar waterBar;
    private float progress;
    @FXML
    private Button pbPlus, pbMinus;
    

    @FXML
    private void onButtonClick() {
         copyWorker = createWorker();
         waterBar.progressProperty().unbind();
         waterBar.progressProperty().bind(copyWorker.progressProperty());
         new Thread(copyWorker).start();
    }

    
    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(1000);
                    updateMessage("1000 milliseconds");
                    updateProgress(i, 10);
                    System.out.println(waterBar.getProgress());
                }
                return true;
            }
        };
    }
    
}