package com.ynov;



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
    Boolean Propreté = true;
    /**
     * @arg Malade : 1/3 de chance d'être malade quand il est vieux
     */
    Boolean Malade = false;
    /**
     * @arg TimePlayed : nombre de fois jouer sur une unité de temps (0 à 3)
     */
    Integer TimePlayed = 0;
    /**
     * @arg Time : Temps de jeu en secondes
     */
    Integer Time = 30;
    


}

