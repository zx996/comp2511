package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DummyApplication extends Application {

	public String filename = "boulders.json";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Dungeon");

        //"maze.json", "boulders.json", "advanced.json"
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(filename);

        DungeonController controller = dungeonLoader.loadController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
