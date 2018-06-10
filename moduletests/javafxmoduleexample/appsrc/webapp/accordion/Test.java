import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import netscape.javascript.JSObject;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Test extends Application {
	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();

	@Override
	public void start(Stage primaryStage) {
		webEngine.load("file:///Users/shermanmarshall/Workspace/Github/GLSLIDE/accordion/test.html");
		
		StackPane root = new StackPane();
		
		Scene scene = new Scene(root, 800, 600);

		Button one = new Button("call method");
		one.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a0) {
				webEngine.executeScript(
					" load('" + Math.random() + "') "
				);
			}
		});


		//Button two = new Button("loadjs");
		//two.setOnAction(new EventHandler<ActionEvent>() {
		//	@Override 
		//	public void handle(ActionEvent a0) {
						//}
		//});

		VBox toolbox = new VBox();
		toolbox.getChildren().add(one);
		//toolbox.getChildren().add(two);

		root.getChildren().add(toolbox);
		root.getChildren().add(browser);

		primaryStage.setTitle("hello");
		primaryStage.setScene(scene);
		primaryStage.show();

		JSObject window = (JSObject) webEngine.executeScript("window");
		window.setMember("app", new TestApp());
		window.call("init");

	}

	public static void main(String...args) {
		Thread t = new Thread(new Runnable() {
                        public void run() {
                                try {
                                        Thread.sleep(1000);
                                } catch (InterruptedException ie) {
                                        System.out.println(ie);
                                }   
                                Runtime rt = Runtime.getRuntime();
                                System.out.println("Free memory: " + rt.freeMemory());
                                System.out.println("Max memory: " + rt.maxMemory());
                                System.out.println("Total memory: " + rt.totalMemory());
                        }   
                }); 
                t.start();
		launch(args);
	}

	public static class TestApp {
		public TestApp() {}
		public String method(String val) {
			System.out.println(val);
			return val;
		}
	}
}
