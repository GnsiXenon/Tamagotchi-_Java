package com.ynov;


import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class Graphique extends Application {

    Tamagotchi t = new Tamagotchi("toto");


        @Override
        public void start(Stage primaryStage) {

        Stage stage = new Stage();

        Label label = new Label("Quelle mode de jeux voulez vous ?");
        Button btnConsole = new Button();
        btnConsole.setText("Console");
        btnConsole.setOnAction(e -> {
            Tamagotchi.main();
            stage.close();
        });

        Button btnGraphique = new Button();
        btnGraphique.setText("Graphique");
        btnGraphique.setOnAction(e -> {
            stage.close();
            action(primaryStage);
        });
        HBox buttons = new HBox(30, btnConsole, btnGraphique);
        buttons.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(label, buttons);
        Scene confirmScene = new Scene(vBox, 300, 250);
        stage.setScene(confirmScene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.show();
        }


        private void action(Stage primaryStage) {
            primaryStage.setTitle("Tamagotchi");
            Button Manger = new Button();
            Manger.setText("Manger");
            Manger.setOnAction(e -> {
            t.Nourrir();
            });
                
            Button Jouer = new Button();
            Jouer.setText("Jouer");
            Jouer.setOnAction(e -> {
                t.Jouer();
            });
            Button Soigner = new Button();
            Soigner.setText("Soigner");
            Soigner.setOnAction(e -> {
                t.Soigner();
            });
            Button Nettoyer = new Button();
            Nettoyer.setText("Nettoyer");
            Nettoyer.setOnAction(e -> {
                t.Nettoyer();
            });

            HBox buttons = new HBox(30, Manger, Jouer, Soigner, Nettoyer);
            buttons.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(buttons);
            Scene confirmScene = new Scene(vBox, 300, 250);
            primaryStage.setScene(confirmScene);
            primaryStage.show();
        }
        
            public void test() {
                launch();
            }

        }


