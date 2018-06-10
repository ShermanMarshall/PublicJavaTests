package com.shermanmarshall.jfxcode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import com.shermanmarshall.jfxmodules.Test;

public class JFXTest extends Application {
	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();

	@Override
	public void start(Stage primaryStage) {
		webEngine.load(Test.baseURL());
		
		StackPane root = new StackPane();
		root.getChildren().add(browser);

		Scene scene = new Scene(root, 800, 800);

		primaryStage.setTitle("hello");
		primaryStage.setScene(scene);
		primaryStage.show();
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
}
