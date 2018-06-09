import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Test extends Application {
	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();

	@Override
	public void start(Stage primaryStage) {
		webEngine.load("file:///Users/shermanmarshall/Workspace/Github/GLSLIDE/test.html");
		
		StackPane root = new StackPane();
		root.getChildren().add(browser);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("hello");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String...args) {
		launch(args);
	}
}
