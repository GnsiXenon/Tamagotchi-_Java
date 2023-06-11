package com.ynov;




import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.VBox;

public class Graphique extends Application {

    Tamagotchi t = new Tamagotchi("");


        @Override
        public void start(Stage primaryStage) {

        Stage stage = new Stage();

        Label label = new Label("Quelle mode de jeux voulez vous ?");
        Button btnConsole = new Button();
        btnConsole.setText("Console");//On crée un bouton pour le mode console
        btnConsole.setOnAction(e -> {
            stage.close();
            Tamagotchi.main();
        });

        Button btnGraphique = new Button();
        btnGraphique.setText("Graphique"); //On crée un bouton pour le mode graphique
        btnGraphique.setOnAction(e -> {
            stage.close();
            t.Load();
            if (t.Nom == ""){
                askName(primaryStage);  //si il y a pas de sauvegarde on demande le nom
            }
            else{
                action(primaryStage); //sinon on lance le jeu

            }
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

        private void askName(Stage primaryStage){

            Stage stage = new Stage();

            Label label = new Label("Quel est le nom de votre Tamagotchi ?");
            TextField name = new TextField();

            name.setMaxWidth(200);
            Button btn = new Button();
            btn.setText("Valider");
            btn.setOnAction(e -> {
                t.Nom = name.getText();//On récupère le nom du tamagotchi
                stage.close();
                action(primaryStage); //On lance le jeu
            });
            HBox buttons = new HBox(30, btn);
            buttons.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(label,name,buttons);
            Scene confirmScene = new Scene(vBox, 300, 250);
            stage.setScene(confirmScene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.show();

        }

        private void action(Stage primaryStage) { //Possede les bouttons pour effectuer les action et l'affichage des informations du tamagotchi 
            t.Timer();
             Label Bonheur = new Label("Bonheur : " + t.Bonheur);
            Label Faim = new Label("Faim : " + t.Faim);
            Label Sante = new Label("Sante : " + t.Malade);
            Label Proprete = new Label("Proprete : " + t.Propreté);
            Label Nom = new Label("Nom : " + t.Nom);
            Label Age = new Label("Age : " + t.Evolution);
            Label Jour = new Label("Jour : " + t.Jours);
            primaryStage.setTitle("Tamagotchi");

            Button Manger = new Button();
            Manger.setText("Manger");
            Manger.setOnAction(e -> {
                if (t.Evolution != "oeuf"){
            t.Nourrir();
            Faim.setText("Faim : " + t.Faim);
                }
            });
                
            Button Jouer = new Button();
            Jouer.setText("Jouer");
            Jouer.setOnAction(e -> {
                if (t.Evolution != "oeuf"){
                t.Jouer();
                    Bonheur.setText("Bonheur : " + t.Bonheur);
                }
               
            });
            Button Soigner = new Button();
            Soigner.setText("Soigner");
            Soigner.setOnAction(e -> {
                if (t.Evolution != "oeuf"){
                t.Soigner();
                Sante.setText("Sante : " + t.Malade);
                Bonheur.setText("Bonheur : " + t.Bonheur);
                }
            });
            Button Nettoyer = new Button();
            Nettoyer.setText("Nettoyer");
            Nettoyer.setOnAction(e -> {
                if (t.Evolution != "oeuf"){
                t.Nettoyer();
                Bonheur.setText("Bonheur : " + t.Bonheur);
                Proprete.setText("Proprete : " + t.Propreté);
                }
            });

            Button PassseDay = new Button(); //Le button est une alternive a la répetition de la fonction Timer tout les x temps c'est a l'utilisateur de choisir quand il veut passer un jour
            PassseDay.setText("Passer un jour");
            PassseDay.setOnAction(e -> {
                t.Timer();
                Faim.setText("Faim : " + t.Faim);
                Sante.setText("Sante : " + t.Malade);
                Bonheur.setText("Bonheur : " + t.Bonheur);
                Proprete.setText("Proprete : " + t.Propreté);
                Nom.setText("Nom : " + t.Nom);
                Age.setText("Age : " + t.Evolution);
                Jour.setText("Jour : " + t.Jours);
            });

            Button saveButton = new Button();  //Sauvegarde les informations du tamagotchi
            saveButton.setText("Sauvegarder");
            saveButton.setOnAction(e -> {
                t.Save();
                primaryStage.close();
            });

            HBox infos = new HBox(30, Bonheur, Faim, Sante, Proprete, Nom, Age, Jour);
            infos.setAlignment(Pos.CENTER);
            HBox buttons = new HBox(30, Manger, Jouer, Soigner, Nettoyer, PassseDay, saveButton);
            buttons.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(buttons, infos);
            Scene confirmScene = new Scene(vBox, 1280, 1024);
            primaryStage.setScene(confirmScene);
            primaryStage.show();
        }



        
            public void Gamemode() {
                launch();
            }

        }


