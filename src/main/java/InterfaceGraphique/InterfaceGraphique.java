/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Controleur.*;
import Domaine.Coup;

import java.util.ArrayList;

import java.awt.Robot;
import java.awt.event.InputEvent;

import javafx.application.Application;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 *
 * @author Frederic.Pitel
 */
public class InterfaceGraphique extends Application {

    private static Stage stage;
    private static Scene sceneMenu;
    private static Scene scenePartie;
    private static AnchorPane partie;
    private static double orgSceneX, orgSceneY;
    private static double orgTranslateX, orgTranslateY;
    private static ImageNavire bateauSelectionne;
    private static Partie partiePrincipale;
    private static Boolean click = false;
    private static Label labelTour;
    private boolean premierNavire = true;
    private MenuItem sauvegarderPartie;

    final ArrayList<Case> cases = new ArrayList();
    final ArrayList<Case> caseAdverses = new ArrayList();
    final ArrayList<Case> casesJouees = new ArrayList();
    final ArrayList<ImageNavire> listeNavires = new ArrayList();
    final ObservableList<Case> intersections = FXCollections.observableArrayList();

    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage stageTemp) {
        stage = stageTemp;

        stage.setTitle("Bataille Navale");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setResizable(false);

        afficherMenu();
        stage.show();
    }

    public void afficherMenu() {
        sceneMenu = creerMenu();
        stage.setScene(sceneMenu);
        stage.setFullScreen(true);
    }

    public void afficherPartie() {
        scenePartie = creerPartie(true);
        stage.setScene(scenePartie);
        stage.setFullScreen(true);
    }

    public static void quitter() {
        System.exit(0);
    }

    public Scene creerMenu() {
        StackPane menu = new StackPane();
        BackgroundSize bgSize = new BackgroundSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), true, true, true, true);
        BackgroundImage backMenu = new BackgroundImage(new Image("file:menu.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bgSize);
        menu.setBackground(new Background(backMenu));
        menu.setPadding(new Insets(50, 75, 100, 75));

        Button nouvellePartie = new Button("Nouvelle Partie");
        nouvellePartie.setMaxWidth(Double.MAX_VALUE);
        nouvellePartie.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        Button chargerPartie = new Button("Charger Partie");
        chargerPartie.setMaxWidth(Double.MAX_VALUE);
        chargerPartie.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        Button quitter = new Button("Quitter");
        quitter.setMaxWidth(Double.MAX_VALUE);
        quitter.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");

        Button AIdebutant = new Button("Débutant");
        AIdebutant.setMaxWidth(Double.MAX_VALUE);
        AIdebutant.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        Button AIavance = new Button("Avancé");
        AIavance.setMaxWidth(Double.MAX_VALUE);
        AIavance.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        Button annulerOrdinateur = new Button("Annuler");
        annulerOrdinateur.setMaxWidth(Double.MAX_VALUE);
        annulerOrdinateur.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");

        HBox boutonsMenu = new HBox();
        boutonsMenu.setSpacing(50);
        boutonsMenu.getChildren().addAll(nouvellePartie, chargerPartie, quitter);
        boutonsMenu.setAlignment(Pos.BOTTOM_CENTER);

        HBox boutonsOrdinateur = new HBox();
        boutonsOrdinateur.setSpacing(50);
        boutonsOrdinateur.getChildren().addAll(AIdebutant, AIavance, annulerOrdinateur);
        boutonsOrdinateur.setAlignment(Pos.BOTTOM_CENTER);
        boutonsOrdinateur.setVisible(false);

        Label labelNomUtilisateur = new Label("Quel est votre nom, amiral?");
        labelNomUtilisateur.setStyle("-fx-text-fill: black;"
                + "-fx-font: 70 stencil");
        TextField fieldNomUtilisateur = new TextField();
        fieldNomUtilisateur.setMaxWidth(500);
        fieldNomUtilisateur.setStyle("-fx-text-fill: black;"
                + "-fx-font: 30 stencil;"
                + "-fx-background-color: white;"
                + "-fx-border-style: solid;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        Button choisirNom = new Button("Choisir");
        choisirNom.setMaxWidth(Double.MAX_VALUE);
        choisirNom.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        HBox boxChoisirNom = new HBox(50);
        boxChoisirNom.getChildren().addAll(fieldNomUtilisateur, choisirNom);
        boxChoisirNom.setAlignment(Pos.CENTER);
        VBox boxNomUtilisateur = new VBox(50);
        boxNomUtilisateur.setMaxSize(1250, 350);
        boxNomUtilisateur.setStyle("-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-border-color: black");
        Label labelErreurNomUtilisateur = new Label("Votre nom doit comporter entre 1 et " + Partie.NOM_MAX + " caractères");
        labelErreurNomUtilisateur.setStyle("-fx-text-fill: red;"
                + "-fx-font: 25 stencil");
        labelErreurNomUtilisateur.setVisible(false);
        boxNomUtilisateur.getChildren().addAll(labelNomUtilisateur, boxChoisirNom, labelErreurNomUtilisateur);
        boxNomUtilisateur.setAlignment(Pos.CENTER);
        boxNomUtilisateur.setVisible(false);

        fieldNomUtilisateur.setOnKeyReleased((e) -> {
            String nomUtilisateur = fieldNomUtilisateur.getText();

            if (e.getCode().equals(KeyCode.ENTER)) {
                if (!nomUtilisateur.equals("") && nomUtilisateur.length() <= Partie.NOM_MAX) {
                    partiePrincipale.setNomUtilisateur(nomUtilisateur);
                    menu.getChildren().clear();
                    afficherPartie();
                } else {
                    labelErreurNomUtilisateur.setVisible(true);
                }
            }
        });

        choisirNom.setOnAction((e) -> {
            String nomUtilisateur = fieldNomUtilisateur.getText();

            if (!nomUtilisateur.equals("") && nomUtilisateur.length() <= Partie.NOM_MAX) {
                partiePrincipale.setNomUtilisateur(nomUtilisateur);
                menu.getChildren().clear();
                afficherPartie();
            } else {
                labelErreurNomUtilisateur.setVisible(true);
            }
        });

        menu.getChildren().addAll(boutonsMenu, boutonsOrdinateur, boxNomUtilisateur);
        Scene sceneMenuTemp = new Scene(menu, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        nouvellePartie.setOnAction((e) -> {
            boutonsMenu.setVisible(false);
            boutonsOrdinateur.setVisible(true);
        });

        chargerPartie.setOnAction((e) -> {
            partiePrincipale = new Partie();
            
            partiePrincipale.chargerPartie();
            
            IteratorJoueur iterJoueur = partiePrincipale.getIteratorJoueurLocal();
            IteratorJoueur iterAdv = partiePrincipale.getIteratorAdversaire();
                       
            scenePartie = creerPartie(false);
            
            placerNavires(iterJoueur);
            
            jouerCoups(iterJoueur, true);
            jouerCoups(iterAdv, false);
            
            for(Case cas : caseAdverses){
                cas.setMouseTransparent(false);
            }
            
            for(ImageNavire nav : listeNavires){
                nav.setMouseTransparent(true);
            }
            
            labelTour.setVisible(true);
            
            stage.setScene(scenePartie);
            stage.setFullScreen(true);
        });

        quitter.setOnAction((e) -> {
            quitter();
        });

        AIdebutant.setOnAction((e) -> {
            boutonsOrdinateur.setVisible(false);
            boxNomUtilisateur.setVisible(true);
            partiePrincipale = new Partie(Partie.Difficulte.DEBUTANT);
            partiePrincipale.creerPartie();
        });

        AIavance.setOnAction((e) -> {
            boutonsOrdinateur.setVisible(false);
            boxNomUtilisateur.setVisible(true);
            partiePrincipale = new Partie(Partie.Difficulte.AVANCE);
            partiePrincipale.creerPartie();
        });

        annulerOrdinateur.setOnAction((e) -> {
            boutonsOrdinateur.setVisible(false);
            boutonsMenu.setVisible(true);
        });

        return sceneMenuTemp;
    }
    
    private void placerNavires(IteratorJoueur iterJoueur){            
        do{
            switch(iterJoueur.getLongueurNavire()){
                case 2:
                    Image chaloupe;
                    if(iterJoueur.getOrientationNavire()){
                        chaloupe = new Image("file:chaloupeTourne.png");
                    } else {
                        chaloupe = new Image("file:chaloupe.png");
                    }
                    ImageNavire ivch = new ImageNavire(chaloupe);
                    ivch.longueur = 4;
                    listeNavires.add(ivch);
                    AnchorPane.setTopAnchor(ivch, (50.0 + (80 * iterJoueur.getPositionNavire().getX())));
                    AnchorPane.setLeftAnchor(ivch, (50.0 + (80 * iterJoueur.getPositionNavire().getY())));
                    partie.getChildren().add(ivch);
                    break;
                case 3:
                    if(premierNavire){
                        Image sousmarin;
                        if(iterJoueur.getOrientationNavire()){
                            sousmarin = new Image("file:sousmarinTourne.png");
                        } else {
                            sousmarin = new Image("file:sousmarin.png");
                        }
                        ImageNavire ivsm = new ImageNavire(sousmarin);
                        ivsm.longueur = 3;
                        listeNavires.add(ivsm);
                        AnchorPane.setTopAnchor(ivsm, (50.0 + (80 * iterJoueur.getPositionNavire().getX())));
                        AnchorPane.setLeftAnchor(ivsm, (50.0 + (80 * iterJoueur.getPositionNavire().getY())));
                        partie.getChildren().add(ivsm);
                        premierNavire = false;
                    } else {
                        Image cruiser;
                        if(iterJoueur.getOrientationNavire()){
                            cruiser = new Image("file:cruiserTourne.png");
                        } else {
                            cruiser = new Image("file:cruiser.png");
                        }
                        ImageNavire ivc = new ImageNavire(cruiser);
                        ivc.longueur = 3;
                        listeNavires.add(ivc);
                        AnchorPane.setTopAnchor(ivc, (50.0 + (80 * iterJoueur.getPositionNavire().getX())));
                        AnchorPane.setLeftAnchor(ivc, (50.0 + (80 * iterJoueur.getPositionNavire().getY())));
                        partie.getChildren().add(ivc);
                        premierNavire = true;
                    }
                    break;
                case 4:
                    Image destroyer;
                    if(iterJoueur.getOrientationNavire()){
                        destroyer = new Image("file:destroyerTourne.png");
                    } else {
                        destroyer = new Image("file:destroyer.png");
                    }
                    ImageNavire ivd = new ImageNavire(destroyer);
                    ivd.longueur = 4;
                    listeNavires.add(ivd);
                    AnchorPane.setTopAnchor(ivd, (50.0 + (80 * iterJoueur.getPositionNavire().getX())));
                    AnchorPane.setLeftAnchor(ivd, (50.0 + (80 * iterJoueur.getPositionNavire().getY())));
                    partie.getChildren().add(ivd);
                    break;
                case 5:
                    Image porteAvion;
                    if(iterJoueur.getOrientationNavire()){
                        porteAvion = new Image("file:porteavionTourne.png");
                    } else {
                        porteAvion = new Image("file:porteavion.png");
                    }
                    ImageNavire ivpa = new ImageNavire(porteAvion);
                    ivpa.longueur = 5;
                    listeNavires.add(ivpa);
                    AnchorPane.setTopAnchor(ivpa, (50.0 + (80 * iterJoueur.getPositionNavire().getX())));
                    AnchorPane.setLeftAnchor(ivpa, (50.0 + (80 * iterJoueur.getPositionNavire().getY())));
                    partie.getChildren().add(ivpa);
                    break;
                default:
                    System.exit(1);
            }
        } while(iterJoueur.prochainNavire());
    }
    
    private void jouerCoups(IteratorJoueur iterJoueur, boolean joueurLocal){
        Coup coup = iterJoueur.prochainCoup();
        while(coup != null) {
            ImageView iv;

            if (coup.getResultat() == Partie.Resultat.MANQUE) {
                Image image = new Image("file:rater.png");
                iv = new ImageView(image);
            } else {
                Image image = new Image("file:toucher.png");
                iv = new ImageView(image);
            }

            Case caseChoisie = null;
            for(Case cas : cases){
                if(cas.x == coup.getX() && cas.y == coup.getY()){
                    caseChoisie = cas;
                    break;
                }
            }
            
            AnchorPane.setTopAnchor(iv, (50.0 + (80 * caseChoisie.x)));
            if(joueurLocal){
                AnchorPane.setRightAnchor(iv, (50.0 + (80 * caseChoisie.y)));
                casesJouees.add(caseChoisie);
            } else {
                AnchorPane.setLeftAnchor(iv, (50.0 + (80 * caseChoisie.y)));                
            }
            
            partie.getChildren().add(iv);
            caseChoisie.setMouseTransparent(true);

            coup = iterJoueur.prochainCoup();
        }
    }

    public Scene creerPartie(boolean nouvellePartie) {
        partie = new AnchorPane();
        partie.setPadding(new Insets(10, 30, 10, 30));
        BackgroundImage backPartie = new BackgroundImage(new Image("file:partie.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        partie.setBackground(new Background(backPartie));

        MenuBar menuBar = new MenuBar();
        Menu menuMenu = new Menu("Menu");
        menuBar.getMenus().add(menuMenu);

        sauvegarderPartie = new MenuItem("Sauvegarder la partie");
        MenuItem menuPrincipal = new MenuItem("Retourner au menu principal");
        MenuItem menuQuitter = new MenuItem("Quitter");

        if(nouvellePartie){
            sauvegarderPartie.setDisable(true);
        }
        
        menuMenu.getItems().addAll(sauvegarderPartie, menuPrincipal, menuQuitter);

        partie.getChildren().add(menuBar);

        sauvegarderPartie.setOnAction((e) -> {
            partiePrincipale.sauvegarderPartie();
        });

        menuPrincipal.setOnAction((e) -> {
            cases.clear();
            caseAdverses.clear();
            casesJouees.clear();
            listeNavires.clear();
            intersections.clear();
            afficherMenu();
        });

        menuQuitter.setOnAction((e) -> {
            System.exit(0);
        });
        

        Label labelJoueur = new Label();
        labelJoueur.setText("Amiral " + partiePrincipale.getNomUtilisateur());
        labelJoueur.setStyle("-fx-text-fill: black;"
                + "-fx-font: 30 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5");

        AnchorPane.setTopAnchor(labelJoueur, 0.0);
        AnchorPane.setLeftAnchor(labelJoueur, 350.0);
        partie.getChildren().add(labelJoueur);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                Case bouton = new Case(i, j);
                bouton.setPrefSize(80.0, 80.0);
                bouton.setStyle("-fx-text-fill: transparent;"
                        + "-fx-background-color: transparent;"
                        + "-fx-border-style: solid;"
                        + "-fx-border-color: black;"
                        + "-fx-border-width: 5;"
                        + "-fx-focus-color: tranparent;"
                        + "-fx-faint-focus-color: transparent");
                partie.getChildren().add(bouton);
                AnchorPane.setTopAnchor(bouton, (50.0 + (i * 80)));
                AnchorPane.setLeftAnchor(bouton, (50.0 + (j * 80)));
                cases.add(bouton);
            }
        }

        Label labelAdversaire = new Label("Ennemi");
        labelAdversaire.setStyle("-fx-text-fill: black;"
                + "-fx-font: 30 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5");
        AnchorPane.setTopAnchor(labelAdversaire, 0.0);
        AnchorPane.setRightAnchor(labelAdversaire, 390.0);

        partie.getChildren().add(labelAdversaire);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                Case bouton = new Case(i, j);
                bouton.setPrefSize(80.0, 80.0);
                bouton.setStyle("-fx-text-fill: transparent;"
                        + "-fx-background-color: transparent;"
                        + "-fx-border-style: solid;"
                        + "-fx-border-color: black;"
                        + "-fx-border-width: 5;"
                        + "-fx-focus-color: tranparent;"
                        + "-fx-faint-focus-color: transparent");
                partie.getChildren().add(bouton);
                AnchorPane.setTopAnchor(bouton, (50.0 + (i * 80)));
                AnchorPane.setRightAnchor(bouton, (50.0 + (j * 80)));
                bouton.setMouseTransparent(true);
                enableClick(bouton);
                caseAdverses.add(bouton);
            }
        }

        Rectangle boiteNavires = new Rectangle(1650, 200);
        boiteNavires.setFill(Color.LIGHTGREY);
        boiteNavires.setStroke(Color.BLACK);
        boiteNavires.setStrokeWidth(5);
        AnchorPane.setBottomAnchor(boiteNavires, 0.0);
        AnchorPane.setLeftAnchor(boiteNavires, 100.0);
        partie.getChildren().add(boiteNavires);

        labelTour = new Label("À votre tour Amiral");
        labelTour.setStyle("-fx-text-fill: black;"
                + "-fx-font: 40 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        AnchorPane.setBottomAnchor(labelTour, 75.0);
        AnchorPane.setLeftAnchor(labelTour, 700.0);
        labelTour.setVisible(false);
        partie.getChildren().add(labelTour);
        
        Scene scenePartieTemp = new Scene(partie, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        
        if(nouvellePartie){
            Image porteAvion = new Image("file:porteavion.png");
            ImageNavire ivpa = new ImageNavire(porteAvion);
            ivpa.longueur = 5;
            listeNavires.add(ivpa);
            enableDrag(ivpa);
            AnchorPane.setBottomAnchor(ivpa, 100.0);
            AnchorPane.setLeftAnchor(ivpa, 150.0);
            partie.getChildren().add(ivpa);

            Image destroyer = new Image("file:destroyer.png");
            ImageNavire ivd = new ImageNavire(destroyer);
            ivd.longueur = 4;
            listeNavires.add(ivd);
            enableDrag(ivd);
            AnchorPane.setBottomAnchor(ivd, 100.0);
            AnchorPane.setLeftAnchor(ivd, 600.0);
            partie.getChildren().add(ivd);

            Image sousmarin = new Image("file:sousmarin.png");
            ImageNavire ivsm = new ImageNavire(sousmarin);
            listeNavires.add(ivsm);
            ivsm.longueur = 3;
            enableDrag(ivsm);
            AnchorPane.setBottomAnchor(ivsm, 100.0);
            AnchorPane.setLeftAnchor(ivsm, 950.0);
            partie.getChildren().add(ivsm);

            Image cruiser = new Image("file:cruiser.png");
            ImageNavire ivc = new ImageNavire(cruiser);
            listeNavires.add(ivc);
            enableDrag(ivc);
            ivc.longueur = 3;
            AnchorPane.setBottomAnchor(ivc, 100.0);
            AnchorPane.setLeftAnchor(ivc, 1250.0);
            partie.getChildren().add(ivc);

            Image chaloupe = new Image("file:chaloupe.png");
            ImageNavire ivch = new ImageNavire(chaloupe);
            listeNavires.add(ivch);
            enableDrag(ivch);
            ivch.longueur = 2;
            AnchorPane.setBottomAnchor(ivch, 100.0);
            AnchorPane.setLeftAnchor(ivch, 1550.0);
            partie.getChildren().add(ivch);
       
            Label labelInstructions = new Label("Appuyez sur SHIFT pour changer l'orientation du navire selectionné");
            labelInstructions.setStyle("-fx-text-fill: black;"
                    + "-fx-font: 25 stencil");
            AnchorPane.setBottomAnchor(labelInstructions, 30.0);
            AnchorPane.setLeftAnchor(labelInstructions, 300.0);
            partie.getChildren().add(labelInstructions);

            Button confirmer = new Button("Confirmer");
            confirmer.setMaxWidth(400);
            confirmer.setStyle("-fx-text-fill: black;"
                    + "-fx-font: 25 stencil;"
                    + "-fx-background-color: lightgrey;"
                    + "-fx-border-style: solid;"
                    + "-fx-border-width: 5;"
                    + "-fx-focus-color: tranparent;"
                    + "-fx-faint-focus-color: transparent");
            AnchorPane.setBottomAnchor(confirmer, 20.0);
            AnchorPane.setLeftAnchor(confirmer, 1300.0);
            partie.getChildren().add(confirmer);

            Label erreurLabel = new Label(" Veuillez placer tous vos navires à l'intérieur de la grille. ");
            erreurLabel.setStyle("-fx-text-fill: black;"
                    + "-fx-font: 40 stencil;"
                    + "-fx-background-color: lightgrey;"
                    + "-fx-border-style: solid;"
                    + "-fx-border-width: 5;"
                    + "-fx-focus-color: tranparent;"
                    + "-fx-faint-focus-color: transparent");
            erreurLabel.setVisible(false);
            erreurLabel.setPrefHeight(200);
            AnchorPane.setTopAnchor(erreurLabel, 500.0);
            AnchorPane.setLeftAnchor(erreurLabel, 285.0);
            partie.getChildren().add(erreurLabel);

            Button erreurBouton = new Button("OK");
            erreurBouton.setMaxWidth(400);
            erreurBouton.setStyle("-fx-text-fill: black;"
                    + "-fx-font: 25 stencil;"
                    + "-fx-background-color: lightgrey;"
                    + "-fx-border-style: solid;"
                    + "-fx-border-width: 5;"
                    + "-fx-focus-color: tranparent;"
                    + "-fx-faint-focus-color: transparent");
            AnchorPane.setTopAnchor(erreurBouton, 625.0);
            AnchorPane.setLeftAnchor(erreurBouton, 900.0);
            erreurBouton.setVisible(false);
            partie.getChildren().add(erreurBouton);

            confirmer.setOnMouseClicked((e) -> {
                boolean ok = true;

                for (ImageNavire nav : listeNavires) {
                    if (nav.cases.size() < nav.longueur) {
                        ok = false;
                    }
                }

                if (ok) {
                    for (ImageNavire nav : listeNavires) {
                        int minX = 10;
                        int minY = 10;
                        for (Case cas : nav.cases) {
                            if (cas.x < minX) {
                                minX = cas.x;
                            }
                            if (cas.y < minY) {
                                minY = cas.y;
                            }
                        }
                        partiePrincipale.placerNavire(minX, minY, nav.longueur, nav.tourne);
                        nav.setMouseTransparent(true);
                    }
                    for (Case btn : intersections) {
                        btn.setStyle("-fx-text-fill: transparent;"
                                + "-fx-background-color: transparent;"
                                + "-fx-border-style: solid;"
                                + "-fx-border-width: 5;"
                                + "-fx-border-color: black;"
                                + "-fx-focus-color: tranparent;"
                                + "-fx-faint-focus-color: transparent");
                    }
                    for (Case cas : caseAdverses) {
                        cas.setMouseTransparent(false);
                    }
                    labelInstructions.setVisible(false);
                    confirmer.setVisible(false);
                    labelTour.setVisible(true);
                    sauvegarderPartie.setDisable(false);
                } else {
                    erreurLabel.setVisible(true);
                    erreurBouton.setVisible(true);
                    for (ImageNavire nav : listeNavires) {
                        nav.setMouseTransparent(true);
                    }
                }
            });

            scenePartieTemp.setOnKeyReleased((e) -> {
                if (e.getCode() == KeyCode.SHIFT && bateauSelectionne != null) {
                    RotateTransition rotate = new RotateTransition(Duration.seconds(0.0001), bateauSelectionne);
                    if (bateauSelectionne.tourne) {
                        rotate.setToAngle(0);
                        bateauSelectionne.tourne = false;
                    } else {
                        rotate.setToAngle(90);
                        bateauSelectionne.tourne = true;
                    }
                    rotate.play();
                    rotate.setOnFinished((f) -> {
                        Bounds bounds = bateauSelectionne.localToScene(bateauSelectionne.getBoundsInLocal());

                        bateauSelectionne.cases.clear();
                        testIntersections(bateauSelectionne);
                        if (!click) {
                            if (testerCollision(bateauSelectionne)) {
                                replacerNavire(bateauSelectionne, (bounds.getMaxX() - (bounds.getWidth() / 2)), bounds.getMaxY() - (bounds.getHeight() / 2));
                            } else {
                                try {
                                    Robot robot = new Robot();

                                    bounds = bateauSelectionne.localToScene(bateauSelectionne.getBoundsInLocal());

                                    robot.mouseMove((int) (bounds.getMaxX() - (bounds.getWidth() / 2)), (int) (bounds.getMaxY() - (bounds.getHeight() / 2)));
                                    robot.mousePress(InputEvent.BUTTON1_MASK);
                                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                } catch (Exception ex) {
                                }
                            }
                            testIntersections(bateauSelectionne);
                        }
                    });
                }
            });

            erreurBouton.setOnMouseReleased((e) -> {
                erreurLabel.setVisible(false);
                erreurBouton.setVisible(false);
                for (ImageNavire nav : listeNavires) {
                    nav.setMouseTransparent(false);
                }
            });
        }

        return scenePartieTemp;
    }

    private void enableClick(Case bouton) {
        bouton.setOnMouseReleased((e) -> {
            for (Case cas : caseAdverses) {
                cas.setMouseTransparent(true);
            }
            labelTour.setVisible(false);

            Partie.Resultat resultat = partiePrincipale.envoyerCoup(bouton.x, bouton.y);
            ImageView iv;

            if (resultat == Partie.Resultat.MANQUE) {
                Image image = new Image("file:rater.png");
                iv = new ImageView(image);
            } else {
                Image image = new Image("file:toucher.png");
                iv = new ImageView(image);
            }
            AnchorPane.setTopAnchor(iv, (50.0 + (80 * bouton.x)));
            AnchorPane.setRightAnchor(iv, (50.0 + (80 * bouton.y)));
            partie.getChildren().add(iv);
            bouton.setMouseTransparent(true);

            Label res = new Label();
            res.setStyle("-fx-text-fill: black;"
                    + "-fx-font: 30 stencil;"
                    + "-fx-background-color: lightgrey");

            if (resultat == Partie.Resultat.MANQUE) {
                res.setText("Dans l'eau...");
            } else if (resultat == Partie.Resultat.TOUCHE) {
                res.setText("Touché!");
            } else {
                res.setText("Touché et coulé!!!");
            }
            AnchorPane.setBottomAnchor(res, 125.0);
            AnchorPane.setLeftAnchor(res, 850.0);
            partie.getChildren().add(res);

            if (resultat == Partie.Resultat.TERMINE) {
                finPartie();
            } else {
                Label ennemi = new Label("Tour de l'ennemi...");
                ennemi.setStyle("-fx-text-fill: black;"
                        + "-fx-font: 30 stencil;"
                        + "-fx-background-color: lightgrey");
                AnchorPane.setBottomAnchor(ennemi, 75.0);
                AnchorPane.setLeftAnchor(ennemi, 800.0);

                partie.getChildren().add(ennemi);

                Coup coup = partiePrincipale.demanderCoup();

                if (coup.getResultat() == Partie.Resultat.MANQUE) {
                    Image image = new Image("file:rater.png");
                    iv = new ImageView(image);
                } else {
                    Image image = new Image("file:toucher.png");
                    iv = new ImageView(image);
                }

                Label res2 = new Label();
                res2.setStyle("-fx-text-fill: black;"
                        + "-fx-font: 30 stencil;"
                        + "-fx-background-color: lightgrey");

                if (coup.getResultat() == Partie.Resultat.MANQUE) {
                    res2.setText("Dans l'eau...");
                } else if (coup.getResultat() == Partie.Resultat.TOUCHE) {
                    res2.setText("Touché!");
                } else {
                    res2.setText("Touché et coulé!!!");
                }
                AnchorPane.setBottomAnchor(res2, 25.0);
                AnchorPane.setLeftAnchor(res2, 850.0);

                partie.getChildren().add(res2);

                AnchorPane.setTopAnchor(iv, (50.0 + (80 * coup.getX())));
                AnchorPane.setLeftAnchor(iv, (50.0 + (80 * coup.getY())));
                partie.getChildren().add(iv);
                bouton.setMouseTransparent(true);
                casesJouees.add(bouton);

                if (coup.getResultat() == Partie.Resultat.TERMINE) {
                    finPartie();
                } else {
                    Button prochainTour = new Button("Prochain tour");
                    prochainTour.setStyle("-fx-text-fill: black;"
                            + "-fx-font: 25 stencil;"
                            + "-fx-background-color: lightgrey;"
                            + "-fx-border-style: solid;"
                            + "-fx-border-width: 5;"
                            + "-fx-focus-color: tranparent;"
                            + "-fx-faint-focus-color: transparent");
                    AnchorPane.setBottomAnchor(prochainTour, 75.0);
                    AnchorPane.setLeftAnchor(prochainTour, 1300.0);
                    partie.getChildren().add(prochainTour);

                    prochainTour.setOnMouseReleased((f) -> {
                        partie.getChildren().remove(res);
                        partie.getChildren().remove(ennemi);
                        partie.getChildren().remove(res2);
                        partie.getChildren().remove(prochainTour);
                        for (Case cas : caseAdverses) {
                            if (!casesJouees.contains(cas)) {
                                cas.setMouseTransparent(false);
                            }
                        }
                        labelTour.setVisible(true);
                    });
                }
            }
        });
    }

    private void enableDrag(ImageNavire source) {
        source.setOnMousePressed((e) -> {
            click = true;
            if (!source.bouger) {
                Bounds bounds = source.localToScene(source.getBoundsInLocal());
                source.xOrg = bounds.getMinX();
                source.yOrg = bounds.getMinY();
                source.bouger = true;
            }
            bateauSelectionne = source;
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = source.getTranslateX();
            orgTranslateY = source.getTranslateY();
            source.getScene().setCursor(Cursor.MOVE);
        });

        source.setOnMouseDragged((e) -> {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            source.setTranslateX(newTranslateX);
            source.setTranslateY(newTranslateY);

            testIntersections(source);
        });

        source.setOnMouseEntered((e) -> {
            if (!e.isPrimaryButtonDown()) {
                source.getScene().setCursor(Cursor.HAND);
            }
        });

        source.setOnMouseExited((e) -> {
            if (!e.isPrimaryButtonDown()) {
                source.getScene().setCursor(Cursor.DEFAULT);
            }
        });

        source.setOnMouseReleased((e) -> {
            click = false;
            source.cases.clear();
            source.getScene().setCursor(Cursor.HAND);

            Boolean collision = testerCollision(source);
            testIntersections(source);

            if (intersections.size() >= source.longueur && !collision) {
                alignerNavire(source, e.getSceneX(), e.getSceneY());
            } else {
                replacerNavire(source, e.getSceneX(), e.getSceneY());
                bateauSelectionne = null;
            }
            testIntersections(source);
        });
    }

    private void testIntersections(ImageNavire source) {
        intersections.clear();
        for (Button btn : cases) {
            btn.setStyle("-fx-text-fill: transparent;"
                    + "-fx-background-color: transparent;"
                    + "-fx-border-style: solid;"
                    + "-fx-border-width: 5;"
                    + "-fx-border-color: black;"
                    + "-fx-focus-color: tranparent;"
                    + "-fx-faint-focus-color: transparent");
        }
        for (Case btn : cases) {
            Bounds btnBounds = btn.localToScene(btn.getBoundsInLocal());
            double tresholdX = btnBounds.getMinX() + ((btnBounds.getMaxX() - btnBounds.getMinX()) / 2);
            double tresholdY = btnBounds.getMinY() + ((btnBounds.getMaxY() - btnBounds.getMinY()) / 2);

            if (!(intersections.contains(btn)) && source.localToScene(source.getBoundsInLocal()).intersects(tresholdX, tresholdY, 1, 1)) {
                intersections.add(btn);
                btn.setStyle("-fx-text-fill: transparent;"
                        + "-fx-background-color: transparent;"
                        + "-fx-border-style: solid;"
                        + "-fx-border-width: 5;"
                        + "-fx-border-color: red;"
                        + "-fx-focus-color: tranparent;"
                        + "-fx-faint-focus-color: transparent");
            }
        }
        source.cases.clear();
        for (Case btn : intersections) {
            source.cases.add(btn);
        }
    }

    private void replacerNavire(ImageNavire source, double sceneX, double sceneY) {
        double offsetX = sceneX - orgSceneX;
        double offsetY = sceneY - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        if (source.tourne) {
            RotateTransition rotate = new RotateTransition(Duration.seconds(0.0001), source);
            rotate.setToAngle(0);
            source.tourne = false;
            rotate.play();

            rotate.setOnFinished((f) -> {
                Bounds bounds = source.localToScene(source.getBoundsInLocal());

                source.setTranslateX(newTranslateX - (bounds.getMinX() - source.xOrg));
                source.setTranslateY(newTranslateY - (bounds.getMinY() - source.yOrg));

                try {
                    Robot robot = new Robot();

                    bounds = source.localToScene(source.getBoundsInLocal());

                    robot.mouseMove((int) (bounds.getMaxX() - (bounds.getWidth() / 2)), (int) (bounds.getMaxY() - (bounds.getHeight() / 2)));
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                } catch (Exception ex) {
                }

                testIntersections(source);
            });
        } else {
            Bounds bounds = source.localToScene(source.getBoundsInLocal());

            source.setTranslateX(newTranslateX - (bounds.getMinX() - source.xOrg));
            source.setTranslateY(newTranslateY - (bounds.getMinY() - source.yOrg));
        }
    }

    private void alignerNavire(ImageNavire source, double eventX, double eventY) {
        double minX = 50000000, minY = 50000000;

        for (Button btn : intersections) {
            Bounds bounds = btn.localToScene(btn.getBoundsInLocal());
            if (minX > bounds.getMinX()) {
                minX = bounds.getMinX();
            }
            if (minY > bounds.getMinY()) {
                minY = bounds.getMinY();
            }
        }

        Bounds bounds = source.localToScene(source.getBoundsInLocal());
        double offsetX = eventX - orgSceneX;
        double offsetY = eventY - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        source.setTranslateX(newTranslateX - (bounds.getMinX() - minX));
        source.setTranslateY(newTranslateY - (bounds.getMinY() - minY));
    }

    private Boolean testerCollision(ImageNavire source) {
        Boolean collision = false;

        for (ImageNavire nav : listeNavires) {
            if (nav != source) {
                for (Case btn : nav.cases) {
                    if (intersections.contains(btn)) {
                        collision = true;
                        break;
                    }
                }
                if (collision) {
                    break;
                }
            }
        }

        return collision;
    }

    private void finPartie() {
        for (Case cas : caseAdverses) {
            cas.setMouseTransparent(true);
        }
        
        sauvegarderPartie.setDisable(true);

        partie.setMouseTransparent(false);

        Label finPartie = new Label(" Partie terminée ");
        finPartie.setStyle("-fx-text-fill: black;"
                + "-fx-font: 30 stencil;");
        AnchorPane.setBottomAnchor(finPartie, 125.0);
        AnchorPane.setLeftAnchor(finPartie, 1200.0);
        partie.getChildren().add(finPartie);

        Button boutonFin = new Button("OK");
        boutonFin.setMaxWidth(400);
        boutonFin.setStyle("-fx-text-fill: black;"
                + "-fx-font: 30 stencil;"
                + "-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 5;"
                + "-fx-focus-color: tranparent;"
                + "-fx-faint-focus-color: transparent");
        AnchorPane.setBottomAnchor(boutonFin, 25.0);
        AnchorPane.setLeftAnchor(boutonFin, 1200.0);
        partie.getChildren().add(boutonFin);

        boutonFin.setOnMouseReleased((e) -> {
            cases.clear();
            caseAdverses.clear();
            casesJouees.clear();
            listeNavires.clear();
            intersections.clear();
            afficherMenu();
        });
    }
    
    class ImageNavire extends ImageView {
        int longueur;
        double xOrg;
        double yOrg;
        boolean bouger;
        boolean tourne;
        ArrayList<Case> cases;

        ImageNavire(Image image) {
            super(image);
            this.bouger = false;
            this.tourne = false;
            this.cases = new ArrayList();
        }
    }

    class Case extends Button {
        int x;
        int y;

        Case(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }
    }
}
