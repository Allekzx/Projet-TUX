/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author bouvi
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    final Env env;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    protected Profil profil;
    private final Dico dico;
    ArrayList<Letter> lettres;
    protected EnvTextMap menuText;                         
    protected int nbLettresRestantes;
    private EditeurDico editDico;
    

    public Jeu() throws SAXException, IOException, ParserConfigurationException {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        //Instancie la liste de lettre du mot
        lettres = new ArrayList<>();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        // Dictionnaire
        dico = new Dico("src/data/xml/dico.xml");
        dico.lireDictionnaire();
        dico.afficherDico();
        
        // Instancie un éditeur de dico
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        editDico = new EditeurDico(doc);
        editDico.lireDOM("src/data/xml/dico.xml");

        // instancie le menuText
        menuText = new EnvTextMap(env);

        // Textes affichés à l'écran
        // Menu joueur
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);

        // Menu principal
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("4. Ajouter un mot dans le dictionnaire", "ajoutMot", 250, 220);
        menuText.addText("Veuillez entrer le niveau du mot que vous voulez ajouter (de 1 à 5) : ", "ajoutMotNiveau", 50, 300);
        menuText.addText("Ajouter maintenant le mot que vous voulez.\n (ne doit pas contenir de caractères spéciaux) : ", "ajoutMotMot", 25, 300);
        // Choix niveau
        menuText.addText("Choisissez un niveau", "Niveau", 200, 300);
        menuText.addText("1. Niveau 1", "Niveau1", 250, 280);
        menuText.addText("2. Niveau 2", "Niveau2", 250, 260);
        menuText.addText("3. Niveau 3", "Niveau3", 250, 240);
        menuText.addText("4. Niveau 4", "Niveau4", 250, 220);
        menuText.addText("5. Niveau 5", "Niveau5", 250, 200);
        //Profil et partie
        menuText.addText("1. Afficher ma partie", "demandePartie", 200, 300);
        menuText.addText("2. Afficher mon profil complet", "demandeProfil", 200, 280);

        menuText.addText("Toutes vos parties précédentes on été terminé à 100%,.\nVeuilleZ commencer une nouvelle partie", "pasDePartie", 100, 300);
        menuText.addText("1. Exit", "exit", 250, 150);
        
        //Avatar 
        menuText.addText("Choisissez votre avatar", "Avatar", 200,300);
        menuText.addText("1. Voldemort (AKA le méchant le plus stylé de l'histoire)","Voldemort", 200, 280);
        menuText.addText("2. Thanos","Thanos", 200, 260);
        menuText.addText("3. Dark Vador", "Dark Vador", 200, 240);

    }

    /**
     * Gère le menu principal
     *
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws javax.xml.transform.TransformerException
     */
    public void execute() throws ParserConfigurationException, TransformerException {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }

    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }
    // fourni, à compléter
    private MENU_VAL menuJeu() throws TransformerException {

        LocalDate _todaysDate = LocalDate.now();
        String todaysDate = "" + _todaysDate;

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie = null;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {

                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico

                    // On affiche les niveaux possibles a l'écran
                    menuText.getText("Niveau").display();
                    menuText.getText("Niveau1").display();
                    menuText.getText("Niveau2").display();
                    menuText.getText("Niveau3").display();
                    menuText.getText("Niveau4").display();
                    menuText.getText("Niveau5").display();

                    // On initialise une partie temporaire au cas ou il y ai un probleme
                    partie = new Partie("2018-09-07", "temporaire", 1);

                    // On recupère la touche qui correspond au niveau choisi
                    int toucheNiveau = 0;
                    while (!(toucheNiveau == Keyboard.KEY_1 || toucheNiveau == Keyboard.KEY_2 || toucheNiveau == Keyboard.KEY_3 || toucheNiveau == Keyboard.KEY_4 || toucheNiveau == Keyboard.KEY_5)) {
                        toucheNiveau = env.getKey();
                        env.advanceOneFrame();
                    }

                    menuText.getText("Niveau").clean();
                    menuText.getText("Niveau1").clean();
                    menuText.getText("Niveau2").clean();
                    menuText.getText("Niveau3").clean();
                    menuText.getText("Niveau4").clean();
                    menuText.getText("Niveau5").clean();

                    // En fonction du niveau on prend un mot au hasard dans la liste de mot correspondante et on change la partie en conséquence
                    String mot = "";
                    int niveauTemp = 0;
                    switch (toucheNiveau) {
                        case Keyboard.KEY_1:
                            niveauTemp = 1;
                            break;
                        case Keyboard.KEY_2:
                            niveauTemp = 2;
                            break;
                        case Keyboard.KEY_3:
                            niveauTemp = 3;
                            break;
                        case Keyboard.KEY_4:
                            niveauTemp = 4;
                            break;
                        case Keyboard.KEY_5:
                            niveauTemp = 5;
                            break;
                    }
                    mot = dico.getMotDepuisListeNiveaux(niveauTemp);
                    displayMotATrouver(mot);
                    partie = new Partie(todaysDate, mot, niveauTemp);
                    partie.setNiveau(niveauTemp);

                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    profil.ajoutePartie(partie);
                    profil.sauvegarder("src/data/xml/profil-" + profil.getNom() + ".xml");
                    menuFinDePartie(partie, profil);
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    partie = profil.chargePartie();

                    if (partie == null) {
                        env.setRoom(menuRoom);
                        menuText.getText("pasDePartie").display();
                        menuText.getText("exit").display();
                        int touchePasDePartie = 0;
                        while (touchePasDePartie != Keyboard.KEY_1) {
                            touchePasDePartie = env.getKey();
                            env.advanceOneFrame();
                        }
                        menuText.getText("pasDePartie").clean();
                        menuText.getText("exit").clean();
                        playTheGame = menuJeu();

                    } else {
                        String motChargé = partie.getMot();
                        int niveau = partie.getNiveau();
                        partie = new Partie("26/11/2022", motChargé, niveau);
                        // Recupère le mot de la partie existante
                        // .........
                        //XXXXXXXX
                        // joue
                        joue(partie);
                        // enregistre la partie dans le profil --> enregistre le profil
                        profil.ajoutePartie(partie);
                        profil.sauvegarder("src/game/profil-" + profil.getNom() + ".xml");
                        menuFinDePartie(partie, profil);
                        playTheGame = MENU_VAL.MENU_JOUE;
                    }

                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }

        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() throws ParserConfigurationException, TransformerException {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;
        String avatar;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
        menuText.getText("ajoutMot").display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();
        menuText.getText("ajoutMot").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                profil = new Profil("src/data/xml/profil-" + nomJoueur + ".xml");
                if (profil.charge(nomJoueur)) {
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_CONTINUE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                avatar = getAvatarJoueur();
                profil = new Profil(nomJoueur, "24/08/2001");
                profil.setAvatar(avatar);
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
                break;
                
                
            // -------------------------------------
            // Touche 4 :Ajouter un mot au dictionnaire
            // -------------------------------------
            case Keyboard.KEY_4:
                int niveauTemp = 0;
                String niveau = "";
                String mot = "";
                do {
                    menuText.getText("ajoutMotNiveau").display();
                    niveau = menuText.getText("ajoutMotNiveau").lire(true);
                    menuText.getText("ajoutMotNiveau").clean();
                    try{
                        niveauTemp = Integer.parseInt(niveau);
                    }catch(NumberFormatException e){
                        e.toString();
                    }
                } while (niveauTemp < 1 && niveauTemp > 5);

                menuText.getText("ajoutMotMot").display();
                mot = menuText.getText("ajoutMotMot").lire(true);
                menuText.getText("ajoutMotMot").clean();
                editDico.ajouterMot(mot, niveauTemp);
                dico.ajoutMotDico(niveauTemp, mot);
                editDico.ecrireDOM("src/data/xml/dico.xml");
        }
        return choix;
    }

    public void joue(Partie partie) {

        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);

        String mot = partie.getMot();
        double x = 0;
        double y = 0;
        for (int i = 0; i < mot.length(); i++) {
            do {
                x = (double) Math.random() * (mainRoom.getWidth());
                y = (double) Math.random() * mainRoom.getDepth();
            } while (estDansLettres(x, y));

            lettres.add(new Letter(mot.charAt(i), x, y));
        }
        for (Letter lettre : lettres) {
            env.addObject(lettre);
        }

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);

        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }

            // Check si le joueur a trouvé toutes les lettres
            if (nbLettresRestantes == 0) {
                finished = true;
            }

            // appliqueRegle(partie) renvoie :
            //                      - true si il reste du temps 
            //                      - false sinon
            if (!appliqueRegles(partie)) {
                finished = true;
            }

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace();

            // Ici, on applique les regles
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);

    }

    protected abstract void démarrePartie(Partie partie);

    protected abstract boolean appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

    protected double distance(Letter letter) {
        return tux.distance(letter);
    }

    protected boolean collision(Letter letter) {
        boolean res = false;
        if (distance(letter) < letter.getScale() + tux.getScale()) {
            res = true;
        }

        return res;
    }

    public int getNbLettresRestantes() {
        return nbLettresRestantes;
    }

    private boolean estDansLettres(double x, double y) {
        boolean res = false;
        for (Letter lettre : lettres) {
            if ((lettre.getX() + lettre.getScale() / 2 >= x && lettre.getX() - lettre.getScale() / 2 <= x) || (lettre.getY() + lettre.getScale() / 2 >= y && lettre.getY() - lettre.getScale() / 2 <= y)) {
                res = true;
            }
        }
        return res;
    }

    private void displayMotATrouver(String mot) {

        menuText.addText("Trouve ce mot : " + mot, "motNiv", 250, 300);
        menuText.addText("1. C'est parti ! ", "Validation", 250, 260);
        menuText.getText("motNiv").display();
        menuText.getText("Validation").display();

        int touche = 0;
        while (touche != Keyboard.KEY_1) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("motNiv").clean();
        menuText.getText("Validation").clean();

    }

    public void menuFinDePartie(Partie partie, Profil profil) {

        env.setRoom(menuRoom);

        menuText.getText("demandePartie").display();
        menuText.getText("demandeProfil").display();

        int toucheAffichage = 0;
        while (!(toucheAffichage == Keyboard.KEY_1 || toucheAffichage == Keyboard.KEY_2)) {
            toucheAffichage = env.getKey();
            env.advanceOneFrame();
        }
        System.out.println(partie.toString());

        menuText.getText("demandePartie").clean();
        menuText.getText("demandeProfil").clean();

        switch (toucheAffichage) {
            case Keyboard.KEY_1:
                String affichage = partie.toString();

                menuText.addText(affichage, "affichePartie", 150, 300);

                menuText.getText("affichePartie").display();
                menuText.getText("exit").display();

                int touche = 0;
                while (touche != Keyboard.KEY_1) {
                    touche = env.getKey();
                    env.advanceOneFrame();
                }
                menuText.getText("affichePartie").clean();
                menuText.getText("exit").clean();
                break;
            case Keyboard.KEY_2:
                afficheProfil(profil);
                break;
        }
    }

    public void affichePartie(Partie partie) {
        String affichage = partie.toString();
        menuText.addText(affichage, "affichePartie", 250, 300);
        menuText.addText("1. Exit", "exit", 250, 150);

        menuText.getText("affichePartie").display();
        menuText.getText("exit").display();

        int touche = 0;
        while (!(touche == Keyboard.KEY_1)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }
        menuText.getText("affichePartie").clean();
        menuText.getText("exit").clean();
    }

    public void afficheProfil(Profil profil) {
        profil.afficheProfil("src/data/xslt/profil.xsl");
    }

    private String getAvatarJoueur(){
        String res = "";
        menuText.getText("Avatar").display();
        menuText.getText("Voldemort").display();
        menuText.getText("Thanos").display();
        menuText.getText("Dark Vador").display();
        int touche = 0;
        while(!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 ||touche == Keyboard.KEY_3)){
            touche = env.getKey();
            env.advanceOneFrame();
        }
        menuText.getText("Avatar").clean();
        menuText.getText("Voldemort").clean();
        menuText.getText("Thanos").clean();
        menuText.getText("Dark Vador").clean();
        
        switch(touche){
            case Keyboard.KEY_1 : 
                res = "../images/voldemort.jpg";
                break;
            case Keyboard.KEY_2 : 
                res = "../images/thanos.jpg";
                break;
            case Keyboard.KEY_3 : 
                res = "../images/dark-vador.jpg";
                break;
            default : 
                break;
                    
        }
        return res;
    }
    
    private void enleverMotTrouveParProfil(Profil profil, Dico dico){
        
    }
    
    
}
