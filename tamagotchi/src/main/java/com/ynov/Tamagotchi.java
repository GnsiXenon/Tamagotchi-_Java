package com.ynov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;






public class Tamagotchi extends TimerTask implements Serializable {

    /**
     * Bonheur allant de 0 à 50
     * @arg Bonheur : Niveau de bonheur du tamagotchi
     */
    Integer Bonheur = 15;
    String Nom = "";
    /**
     * @arg Faim : False si le tamagotchi n'a pas été nourri sur l'unité de temps
    */
    Boolean Faim = false;
    /**
     * 
     * @arg JoursSansManger : Nombre de jours sans manger
     */
    Integer JoursSansManger = 0;
    /**
     * @arg 
     */
    Integer SuccessiveFeedDays = 0;
    /**
     * @arg Propreté : Diminue de 3 le bonheur si le tamagotchi est sale
     */
    Boolean Propreté = false;
    /**
     * @arg Malade : 1/3 de chance d'être malade quand il est vieux
     */
    Boolean Malade = false;
    /**
     * @arg TimePlayed : nombre de fois joué sur une unité de temps (0 à 3)
     */
    /**
     * @arg Evolution : 0 = oeuf, 1 = enfant, 2 = adulte, 3 = vieux
     */
    String Evolution = "oeuf";
    Integer TimePlayed = 0;
    /**
     * @arg Time : Temps de jeu en Secondes
     */
    final Integer TimeDay = 7;   
    Integer Time = TimeDay;
    Integer Jours = 0;
    /**
     * 
     * @arg DayBeforeAdult : Nombre de jours avant de devenir adulte
     */
    Integer DayBeforeAdult = 40;
    /**
     * 
     * @arg DayBeforeDeath : Nombre de jours avant de mourir
     */
    Integer DayBeforeDeath = 5;

    

    Tamagotchi(String Nom) {
        this.Nom = Nom;
    }

// Methode Name qui permet de choisis le nom du Tamagotchi au debut du jeu
    public void Name(){
        System.out.println("Quel est le nom de votre Tamagotchi ?");
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
        try {
            Nom = buffer.readLine();
        }
        catch(IOException e){
            System.out.println("Quelque chose s'est mal passé, recommencez.");
            Name();
        }
    }
    
//Methode Timer qui permet de faire passer le temps dans le jeu
    public void Timer(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
                Jours++;
                System.out.println("Jour " + Jours);
                switch (Jours){
                    case 1: //Premier jour ou le Tamagotchi n'est pas encore né
                        System.out.println("Vous avez un Tamagotchi nommé " + Nom);
                        break;
                    case 2: //Deuxième jour ou le Tamagotchi née
                        if (Evolution == "oeuf") {
                            System.out.println("Votre Tamagotchi est né !");
                            Evolution = "enfant";
                            break;
                        }
                    default:
                       if (Evolution == "vieux") {  //check si il est malade et vieux 
                            if (Malade == true) {
                                System.out.println(Nom + " est mort de maladie");
                                System.exit(0);
                            }else{
                                Malade = chanceDeTomberMalade();
                            }
                        }
                        if (Bonheur <= 0) { //check si son bonheur est a 0
                            System.out.println(Nom + " est mort de tristesse");
                            System.exit(0);
                        }
                        if (Faim == false) {    //check si il a faim et enleve tu bonheur si il a faim
                            JoursSansManger += 1;
                            SuccessiveFeedDays = 0;
                            Bonheur -= JoursSansManger*5;
                        }
                        if (Propreté == false) {   //check si il est sale et enleve du bonheur si il est sale
                            Bonheur -= 3;
                        }
                        if (Faim == true) { //check si il a manger 
                            Faim = false;
                            JoursSansManger = 0;
                        }
                        if (Bonheur>=40 && Evolution == "enfant" && SuccessiveFeedDays>=4){ //fonction pour qu'il devienne adulte
                            Evolution = "adulte";
                            DayBeforeAdult--;
                            System.out.println(Nom + " est devenu adulte !");
                        }
                        break;
                        
                }
                //Reset les variable pour le jour suivant
                Faim = false;
                Propreté = false;
                TimePlayed = 0;
                if (DayBeforeAdult <40 ) { //check si il est adulte et enleve un jour avant qu'il devienne vieux
                    DayBeforeAdult--;
                    if (DayBeforeAdult == 0) {
                        Evolution = "vieux";
                        DayBeforeDeath--;
                        System.out.println(Nom + " est devenu vieux !");
                    }
                }

                if (DayBeforeDeath <5 ) { //check si il est vieux et enleve un jour avant qu'il meurt
                    DayBeforeDeath--;
                    if (DayBeforeDeath == 0) {
                        Evolution = "mort";
                        System.out.println(Nom + " est mort de vieillesse !");
                        System.exit(0);
                    }
                }

                System.out.println("Appuyez sur Entrée pour continuer...");
                       
        }


    

    public static void main() {
        System.out.println("Bienvenue dans le jeu du Tamagotchi !");       
        Tamagotchi tamagotchi = new Tamagotchi("");
        tamagotchi.Load();
        if (tamagotchi.Nom == "") {
            tamagotchi.Name();
        }
        
        //Double Thread pour le timer et le menu
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        Runnable timerTamagotchi = () -> {
            tamagotchi.Timer();
        };

        Runnable menuTamagotchi = () -> {
            tamagotchi.Menu();
        };

        executor.scheduleAtFixedRate(timerTamagotchi, 0, tamagotchi.TimeDay, TimeUnit.SECONDS); //timer tout les x secondes (x = TimeDay)
        executor.schedule(menuTamagotchi,(tamagotchi.Evolution =="oeuf" ? tamagotchi.TimeDay+1 : 0), TimeUnit.SECONDS); //menu qui se lance au bout de x secondes (x = TimeDay) si il est oeuf sinon il se lance directement
    }
    


    


  
    public void ShowStat(){ //Affiche les stats du Tamagotchi
        System.out.print("Nom : " + Nom);
        System.out.println(" | Bonheur : " + Bonheur);
        System.out.print((Faim == true ? " | J'ai bien mangé" : " | J'ai faim"));
        System.out.println((JoursSansManger == 0) ? " | Je mange bien depuis "+SuccessiveFeedDays+"  jours" : " | Ça fait " + JoursSansManger + " jours que je n'ai pas mangé");
        System.out.print((Propreté == true ? " | Je suis propre" : " | Je suis sale"));
        System.out.println((Malade == true ? " | Je suis malade" : " | Je suis en bonne santé"));
        System.out.println(" | On a joué " + TimePlayed + " fois aujourd'hui");
        System.out.println(" | Je suis un " + Evolution);
        System.out.println(" | Jours : " + Jours);
        System.out.println("");
    }




    public void Menu(){ // Menu du jeu
        ShowStat();
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1 - Nettoyer");
        System.out.println("2 - Nourrir");
        System.out.println("3 - Jouer");
        System.out.println("4 - Soigner");
        System.out.println("5 - Save");
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
        try {
            String line = buffer.readLine();
            Integer choice = Integer.parseInt(line);
            choice(choice);
        } catch (Exception e) {
            Menu();
        }

        
        
    }



    public void choice(Integer choice) { //Methode qui check le choix du joueur
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (choice) {
            case 1:
                Nettoyer();
                Menu();
                break;
            case 2:
                Nourrir();
                Menu();
                break;
            case 3:
                Jouer();
                Menu();
                break;
            case 4:
                Soigner();
                Menu();
                break;
            case 5:
                Save();
                System.exit(0);
                break;
            default:
                System.out.println("Erreur de saisie");
                Menu();
                break;
        }
    }


    public void Nettoyer() { //Methode pour nettoyer le Tamagotchi
        if (this.Propreté == false) {
            this.Propreté = true;
            System.out.println("Vous venez de nettoyer votre tamagotchi");
        }else if (this.Propreté == true) {
            System.out.println("Vous avez déjà nettoyé");
        }
    }

    public void Nourrir() { //Methode pour nourrir le Tamagotchi
         JoursSansManger = 0;
        if (this.Faim == false) {
            this.Faim = true;
            SuccessiveFeedDays += 1;
            System.out.println("Vous venez de nourrir votre tamagotchi");
        }else if (this.Faim == true) {
            System.out.println("Vous avez déjà nourri");
        }
    }

    public void Jouer () { //Methode pour jouer avec le Tamagotchi
        if (this.TimePlayed < 3) {
            this.TimePlayed += 1;
            this.Bonheur += 3;
            if (this.Bonheur > 50) {
                this.Bonheur = 50;
            }
            System.out.println("Vous venez de jouer avec votre tamagotchi");
        }else if (this.TimePlayed == 3) {
            System.out.println("Vous avez déjà joué 3 fois");
        }
    }

    public void tombeMalade() { //Methode pour rendre malade le Tamagotchi
        if (Evolution == "vieux") {
            if (chanceDeTomberMalade()) {
                Bonheur -= 10;
                if (this.Bonheur < 0) {
                this.Bonheur = 0;
            }
                System.out.println(Nom + " est tombé malade !");
            } else {
                System.out.println(Nom + " n'est pas tombé malade cette fois-ci.");
            }
        } 
    }

    public void Soigner () { //Methode pour soigner le Tamagotchi
        if (this.Malade == true) {
            this.Malade = false;
            System.out.println("Vous venez de soigner votre tamagotchi");
        }else if (this.Malade == false) {
            System.out.println("Votre tamagotchi n'est pas malade");
        }
    }
    
    public boolean chanceDeTomberMalade() { //Methode pour calculer la chance de tomber malade 1 chance sur 3
        int chance = (int) (Math.random() * 3) + 1;
        return chance == 1;
    }




    public void Save() { //Methode pour sauvegarder le Tamagotchi
        try {
            FileOutputStream fileOut = new FileOutputStream("save.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Sauvegarde réussie.");
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de la sauvegarde.");
            e.printStackTrace();
        }
    }

    public void Load() { //Methode pour charger le Tamagotchi
        if (new File("save.ser").exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("save.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                Tamagotchi savedData = (Tamagotchi) in.readObject();
                in.close();
                fileIn.close();

                // Copier les valeurs des attributs de savedData vers l'instance courante
                this.Nom = savedData.Nom;
                this.Bonheur = savedData.Bonheur;
                this.Faim = savedData.Faim;
                this.JoursSansManger = savedData.JoursSansManger;
                this.Propreté = savedData.Propreté;
                this.Malade = savedData.Malade;
                this.TimePlayed = savedData.TimePlayed;
                this.Evolution = savedData.Evolution;
                this.Jours = savedData.Jours;
                this.SuccessiveFeedDays = savedData.SuccessiveFeedDays;
                this.DayBeforeAdult = savedData.DayBeforeAdult;
                this.DayBeforeDeath = savedData.DayBeforeDeath;

                System.out.println("Chargement réussi.");
            } catch (IOException e) {
                System.out.println("Le fichier n'a pas pu être chargé : " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Le format de fichier est invalide : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucune sauvegarde n'a été trouvée.");
        }
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}