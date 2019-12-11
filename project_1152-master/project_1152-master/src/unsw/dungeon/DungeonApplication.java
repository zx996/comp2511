package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
    	String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
    	
    	
        primaryStage.setTitle("Dungeon");

        //"maze.json", "boulders.json", "advanced.json"
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("boulders.json");

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
