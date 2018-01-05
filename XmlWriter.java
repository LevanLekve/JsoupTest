package AboutYouParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

class XmlWriter {

    Document makeDocument(List<OfferClass> offerClassList) throws ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("Offers");
        doc.appendChild(rootElement);

        for (OfferClass anOfferClassList : offerClassList) {
            Element offer = doc.createElement("Offer");
            rootElement.appendChild(offer);

            Element brandElement = doc.createElement("Brand");
            brandElement.setTextContent(anOfferClassList.getBrand());
            offer.appendChild(brandElement);

            Element nameElement = doc.createElement("Name");
            nameElement.setTextContent(anOfferClassList.getName());
            offer.appendChild(nameElement);

            if (anOfferClassList.getInitialPrice() == null) {
                Element priceElement = doc.createElement("Price");
                priceElement.setTextContent(anOfferClassList.getPrice());
                offer.appendChild(priceElement);
            } else {
                Element initialPriceElement = doc.createElement("initial_Price");
                initialPriceElement.setTextContent(anOfferClassList.getInitialPrice());
                offer.appendChild(initialPriceElement);

                Element priceElement = doc.createElement("Price");
                priceElement.setTextContent(anOfferClassList.getPrice());
                offer.appendChild(priceElement);
            }



        }
        return doc;
    }

}
