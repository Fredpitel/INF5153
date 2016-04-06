/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Controleur.Partie.Difficulte;
import Domaine.AIDebutant;
import Domaine.Coup;
import Domaine.Joueur;
import Domaine.JoueurLocal;
import Domaine.Navire;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;

/**
 *
 * @author Frederic.Pitel
 */
public class SaveLoadXML {

    private final String URL_FICHIER_XML = "./sauvegarde.xml";

    public boolean sauvegarderPartie(Joueur joueurLocal, Joueur joueurAdversaire, Difficulte diff) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element racine = doc.createElement("partie");
            doc.appendChild(racine);
            Attr difficulte = doc.createAttribute("diff");
            difficulte.setValue("" + diff);
            racine.setAttributeNode(difficulte);

            Element elemJoueurLocal = doc.createElement("joueurLocal");
            elemJoueurLocal.appendChild(creerListeNavireXML(joueurLocal, doc));
            elemJoueurLocal.appendChild(creerListeCoupXML(joueurLocal, doc));
            racine.appendChild(elemJoueurLocal);

            Element elemJoueurAdversaire = doc.createElement("joueurAdversaire");
            elemJoueurAdversaire.appendChild(creerListeNavireXML(joueurLocal, doc));
            elemJoueurAdversaire.appendChild(creerListeCoupXML(joueurLocal, doc));
            racine.appendChild(elemJoueurAdversaire);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(URL_FICHIER_XML));

            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            return false;
        }
        return true;
    }

    private Element creerListeNavireXML(Joueur joueur, Document doc) throws DOMException {
        Element elemListNavire = doc.createElement("listNavire");
        List<Navire> listeNavire = joueur.getListNavire();
        Element elemNavire;
        Attr positionNavireX;
        Attr positionNavireY;
        Attr longueurNavire;
        Attr tourneNavire;
        int i = 0;

        for (Navire nav : listeNavire) {
            elemNavire = doc.createElement("navire" + i);
            positionNavireX = doc.createAttribute("positionNavireX");
            positionNavireX.setValue("" + nav.getCases().get(0).getX());
            positionNavireY = doc.createAttribute("positionNavireY");
            positionNavireY.setValue("" + nav.getCases().get(0).getY());
            longueurNavire = doc.createAttribute("longueurNavire");
            longueurNavire.setValue("" + nav.getCases().size());
            tourneNavire = doc.createAttribute("tourneNavire");
            tourneNavire.setValue("" + nav.getTourne());
            elemNavire.setAttributeNode(positionNavireX);
            elemNavire.setAttributeNode(positionNavireY);
            elemNavire.setAttributeNode(longueurNavire);
            elemNavire.setAttributeNode(tourneNavire);
            elemListNavire.appendChild(elemNavire);
            i++;
        }

        return elemListNavire;
    }

    private Element creerListeCoupXML(Joueur joueur, Document doc) throws DOMException {
        Element elemListCoup = doc.createElement("elemListCoup");
        List<Coup> listeCoup = joueur.getListCoup();
        Element elemCoup;
        Attr positionCoupX;
        Attr positionCoupY;
        Attr resultatCoup;
        int i = 0;

        for (Coup coup : listeCoup) {
            elemCoup = doc.createElement("coup" + i);
            positionCoupX = doc.createAttribute("positionCoupX");
            positionCoupX.setNodeValue("" + coup.getX());
            positionCoupY = doc.createAttribute("positionCoupY");
            positionCoupY.setNodeValue("" + coup.getY());
            resultatCoup = doc.createAttribute("resultatCoup");
            resultatCoup.setNodeValue("" + coup.getResultat());
            elemCoup.setAttributeNode(positionCoupX);
            elemCoup.setAttributeNode(positionCoupY);
            elemCoup.setAttributeNode(resultatCoup);
            elemListCoup.appendChild(elemCoup);
            i++;
        }

        return elemListCoup;
    }

    public Difficulte chargerDificulte() {
        return Difficulte.DEBUTANT;
    }

    public Joueur chargerJoueurLocal() {
        return new JoueurLocal();
    }

    public Joueur chargerJoueurAdversaire(Difficulte diff) {
        return new AIDebutant();
    }
}
