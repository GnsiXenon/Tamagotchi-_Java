package com.ynov;

import java.util.Timer;
import java.util.TimerTask;



public class Tamagotchi {

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
    Integer TimePlayed = 0;
    /**
     * @arg Time : Temps de jeu en Secondes
     */

    final Integer TimeDay = 5;   
    Integer Time = TimeDay;
    Integer Jours = 0;

    

    Tamagotchi(String Nom) {
        this.Nom = Nom;
    }

    public void Timer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Jours==0){
                    System.out.println("Bienvenue dans le jeu du Tamagotchi !");
                    System.out.println("Vous avez un Tamagotchi nommé " + Nom);
                }
                Jours++;
                System.out.println("Jour " + Jours);
            }
        }, 0, TimeDay * 1000);
    }
    

    public static void main(String[] args) {
        Tamagotchi Tamagotchi = new Tamagotchi("Tama");
        Tamagotchi.Timer();
    }





    public Nettoyer() {
        if (this.Propreté == false) {
            this.Propreté = true;
            System.out.println("Vous venez de nettoyer votre tamagotchi");
        }else if (this.Propreté == true) {
            System.out.println("Vous avez déjà nettoyé");
        }
    }

    public Nourrir() {
        Integer nombreDeJourSansManger = 0;
        if (this.Faim == false) {
            this.Faim = true;
            System.out.println("Vous venez de nourrir votre tamagotchi");
        }else if (this.Faim == true) {
            System.out.println("Vous avez déjà nourri");
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

public boolean chanceDeTomberMalade() {
    int chance = (int) (Math.random() * 3) + 1;
    return chance == 1;
}

public boolean malade() {
    return Bonheur < 50;
}
}