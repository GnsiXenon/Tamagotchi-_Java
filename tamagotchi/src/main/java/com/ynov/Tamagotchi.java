package com.ynov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




public class Tamagotchi extends TimerTask {

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
     * @arg Evolution : 0 = Oeuf, 1 = enfant, 2 = adulte, 3 = vieux
     */
    String Evolution = "vieux";
    Integer TimePlayed = 0;
    /**
     * @arg Time : Temps de jeu en Secondes
     */
    final Integer TimeDay = 10;   
    Integer Time = TimeDay;
    Integer Jours = 0;
    /**
     * 
     * @arg DayBecomeAdult : Jour où le tamagotchi devient adulte
     */
    Integer DayBeforeAdult = 40;
    Integer DayBeforeOld = 5;

    

    Tamagotchi(String Nom) {
        this.Nom = Nom;
    }

    public static String Name(){
        System.out.println("Rentrer le nom de votre Tamagotchi :");
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
        try {
            return buffer.readLine();
        }
        catch(IOException e){
            System.out.println("Quelque chose s'est mal passé, recommencez.");
            return Name();
        }
    }

    public void Timer(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
                Jours++;
                System.out.println("Jour " + Jours);
                switch (Jours){
                    case 1:
                        System.out.println("Vous avez un Tamagotchi nommé " + Nom);
                        break;
                    case 2:
                        if (Evolution == "oeuf") {
                            System.out.println("Votre Tamagotchi est né !");
                            Evolution = "enfant";
                            break;
                        }
                    default:
                       if (Evolution == "vieux") {
                            if (Malade == true) {
                                System.out.println(Nom + " est mort de maladie");
                                System.exit(0);
                            }else{
                                Malade = chanceDeTomberMalade();
                            }
                        }
                        if (Bonheur <= 0) {
                            System.out.println(Nom + " est mort de tristesse");
                            System.exit(0);
                        }
                        if (Faim == false) {
                            JoursSansManger += 1;
                            Bonheur -= JoursSansManger*5;
                        }
                        if (Propreté == false) {
                            Bonheur -= 3;
                        }
                        if (Faim == true) {
                            Faim = false;
                            JoursSansManger = 0;
                        }
                        if (Bonheur>=40 && Evolution == "enfant" && SuccessiveFeedDays>=4){
                            Evolution = "adulte";
                            DayBeforeAdult--;
                            System.out.println(Nom + " est devenu adulte !");
                        }
                        break;
                        
                }
                Faim = false;
                Propreté = false;
                TimePlayed = 0;
                if (DayBeforeAdult <40 ) {
                    DayBeforeAdult--;
                    if (DayBeforeAdult == 0) {
                        Evolution = "vieux";
                        System.out.println(Nom + " est devenu vieux !");
                    }
                }

                if (DayBeforeOld <5 ) {
                    DayBeforeOld--;
                    if (DayBeforeOld == 0) {
                        Evolution = "mort";
                        System.out.println(Nom + " est mort de vieillesse !");
                        System.exit(0);
                    }
                }

                System.out.println("Appuyez sur Entrée pour continuer...");
                       
        }


    
    

    public static void main() {
        System.out.println("Bienvenue dans le jeu du Tamagotchi !");       
        String futurName = Tamagotchi.Name(); 
        Tamagotchi tamagotchi = new Tamagotchi(futurName);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        Runnable timerTamagotchi = () -> {
            tamagotchi.Timer();
        };

        Runnable menuTamagotchi = () -> {
            tamagotchi.Menu();
        };

        executor.scheduleAtFixedRate(timerTamagotchi, 0, tamagotchi.TimeDay, TimeUnit.SECONDS);
        executor.schedule(menuTamagotchi,(tamagotchi.Evolution =="oeuf" ? tamagotchi.TimeDay : 0), TimeUnit.SECONDS);
        System.out.println((tamagotchi.Evolution =="oeuf" ? tamagotchi.TimeDay : 0));


    }
    


    


  
    public void ShowStat(){
        System.out.print("Nom : " + Nom);
        System.out.println(" | Bonheur : " + Bonheur);
        System.out.print((Faim == true ? " | J'ai bien mangé" : " | J'ai faim"));
        System.out.println((JoursSansManger == 0) ? " | Je mange bien depuis des jours" : " | Ça fait " + JoursSansManger + " jours que je n'ai pas mangé");
        System.out.print((Propreté == true ? " | Je suis propre" : " | Je suis sale"));
        System.out.println((Malade == true ? " | Je suis malade" : " | Je suis en bonne santé"));
        System.out.println(" | On a joué " + TimePlayed + " fois aujourd'hui");
        System.out.println(" | Je suis un " + Evolution);
        System.out.println(" | Jours : " + Jours);
        System.out.println("");
    }




    public void Menu(){
        ShowStat();
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1 - Nettoyer");
        System.out.println("2 - Nourrir");
        System.out.println("3 - Jouer");
        System.out.println("4 - Soigner");
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



    public void choice(Integer choice) {
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
            default:
                System.out.println("Erreur de saisie");
                Menu();
                break;
        }
    }

    public void Nettoyer() {
        if (this.Propreté == false) {
            this.Propreté = true;
            System.out.println("Vous venez de nettoyer votre tamagotchi");
        }else if (this.Propreté == true) {
            System.out.println("Vous avez déjà nettoyé");
        }
    }

    public void Nourrir() {
         JoursSansManger = 0;
        if (this.Faim == false) {
            this.Faim = true;
            SuccessiveFeedDays += 1;
            System.out.println("Vous venez de nourrir votre tamagotchi");
        }else if (this.Faim == true) {
            System.out.println("Vous avez déjà nourri");
        }
    }

    public void Jouer () {
        if (this.TimePlayed < 3) {
            this.TimePlayed += 1;
            this.Bonheur += 3;
            System.out.println("Vous venez de jouer avec votre tamagotchi");
        }else if (this.TimePlayed == 3) {
            System.out.println("Vous avez déjà joué 3 fois");
        }
    }

    public void tombeMalade() {
        if (!malade()) {
            if (chanceDeTomberMalade()) {
                Bonheur -= 10;
                System.out.println(Nom + " est tombé malade !");
            } else {
                System.out.println(Nom + " n'est pas tombé malade cette fois-ci.");
            }
        } else {
            System.out.println(Nom + " est déjà malade.");
        }
    }

    public void Soigner () {
        if (this.Malade == true) {
            this.Malade = false;
            System.out.println("Vous venez de soigner votre tamagotchi");
        }else if (this.Malade == false) {
            System.out.println("Votre tamagotchi n'est pas malade");
        }
    }
    
    public boolean chanceDeTomberMalade() {
        int chance = (int) (Math.random() * 3) + 1;
        return chance == 1;
    }
    
    public boolean malade() {
        return Bonheur < 50;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}