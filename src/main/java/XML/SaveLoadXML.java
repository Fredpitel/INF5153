/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Controleur.Partie.Difficulte;
import Controleur.Partie.Resultat;
import Domaine.AIAvance;
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
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

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
            Attr nbNaviresCoules = doc.createAttribute("nbNaviresCoules");
            nbNaviresCoules.setValue("" + joueurLocal.getNaviresCoules());
            elemJoueurLocal.appendChild(nbNaviresCoules);
            racine.appendChild(elemJoueurLocal);

            Element elemJoueurAdversaire = doc.createElement("joueurAdversaire");
            elemJoueurAdversaire.appendChild(creerListeNavireXML(joueurLocal, doc));
            elemJoueurAdversaire.appendChild(creerListeCoupXML(joueurLocal, doc));
            nbNaviresCoules = doc.createAttribute("nbNaviresCoules");
            nbNaviresCoules.setValue("" + joueurAdversaire.getNaviresCoules());
            elemJoueurAdversaire.appendChild(nbNaviresCoules);
            if(diff == Difficulte.AVANCE){
                Element dernierNavireTouche = doc.createElement("dernierNavireTouche");
                Attr positionCoupX = doc.createAttribute("positionCoupX");
                positionCoupX.setNodeValue("" + ((AIAvance)joueurAdversaire).getDernierNavireTouche().getX());
                Attr positionCoupY = doc.createAttribute("positionCoupY");
                positionCoupY.setNodeValue("" + ((AIAvance)joueurAdversaire).getDernierNavireTouche().getY());
                dernierNavireTouche.setAttributeNode(positionCoupX);
                dernierNavireTouche.setAttributeNode(positionCoupY);
                elemJoueurAdversaire.appendChild(dernierNavireTouche);
            }
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

    public Difficulte chargerDifficulte() {
        try{
            File fichierXml = new File(URL_FICHIER_XML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fichierXml);
            Element eDiff = (Element)doc.getElementsByTagName("partie").item(0);
            String diff = eDiff.getAttribute("diff");
            switch (diff) {
                case "DEBUTANT":
                    return Difficulte.DEBUTANT;
                case "AVANCE":
                    return Difficulte.AVANCE;
                default:
                    return null;
            }
        }catch(ParserConfigurationException | SAXException | IOException e){
            return null;
        }
    }

    public Joueur chargerJoueurLocal() {
        return new JoueurLocal();
    }

    public Joueur chargerJoueurAdversaire(Difficulte diff) {
        return new AIDebutant();
    }
    
    private Coup elementXmlToCoup(Element eCoup){
        int x = Integer.getInteger(eCoup.getAttribute("positionCoupX"));
        int y = Integer.getInteger(eCoup.getAttribute("positionCoupY"));
        String resultat = eCoup.getAttribute("resultatCoup");
        switch (resultat) {
            case "COULE":
                return new Coup(x,y,Resultat.COULE);
            case "MANQUE":
                return new Coup(x,y,Resultat.MANQUE);
            case "TOUCHE":
                return new Coup(x,y,Resultat.TOUCHE);
            default:
                return new Coup(x,y,Resultat.TERMINE);
        }
    }
    
    private Element creerListeNavireXML(Joueur joueur, Document doc) throws DOMException {
        Element elemListNavire = doc.createElement("listNavire");
        List<Navire> listeNavire = joueur.getListNavire();
        Element elemNavire;
        Attr positionNavireX;
        Attr positionNavireY;
        Attr longueurNavire;
        Attr tourneNavire;
        Attr caseToucheNavire;

        for (Navire nav : listeNavire) {
            elemNavire = doc.createElement("navire");
            positionNavireX = doc.createAttribute("positionNavireX");
            positionNavireX.setValue("" + nav.getCases().get(0).getX());
            positionNavireY = doc.createAttribute("positionNavireY");
            positionNavireY.setValue("" + nav.getCases().get(0).getY());
            longueurNavire = doc.createAttribute("longueurNavire");
            longueurNavire.setValue("" + nav.getCases().size());
            tourneNavire = doc.createAttribute("tourneNavire");
            tourneNavire.setValue("" + nav.getTourne());
            caseToucheNavire = doc.createAttribute("caseToucheNavire");
            caseToucheNavire.setValue("" + nav.getCasesTouchees());
            elemNavire.setAttributeNode(positionNavireX);
            elemNavire.setAttributeNode(positionNavireY);
            elemNavire.setAttributeNode(longueurNavire);
            elemNavire.setAttributeNode(tourneNavire);
            elemNavire.setAttributeNode(caseToucheNavire);
            elemListNavire.appendChild(elemNavire);
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

        for (Coup coup : listeCoup) {
            elemCoup = doc.createElement("coup");
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
        }

        return elemListCoup;
    }
}
