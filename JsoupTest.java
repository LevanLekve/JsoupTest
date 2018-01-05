package AboutYouParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JsoupTest {

    private static List<OfferClass> offersList = new ArrayList<OfferClass>();                    //ArrayList of each offer covered in class

    List<OfferClass> getOffersList () {
        return offersList;
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        String urlMain = "https://www.aboutyou.de";
        String url = urlMain + "/maenner/bekleidung/" + args[0];   //main url + keywords ("jeans")

        Document document = Jsoup.connect(url).get();                                 //get connection to the site and make DOM document

        Elements pageElement = document.getElementsByClass("pageNumbers_ffrt32"); //getting maximum number of pages
        String pagesText = pageElement.get(3).text();                                      //for making loop (digit "3" means that there are two preceding elements that we are don`t need to use)
        int numberOfPages = Integer.parseInt(pagesText);

        for (int i = 1; i <= numberOfPages ; i++) {
            Elements elements = document.select(".wrapper_1eu800j");              //select all elements which links to the offers

            for (Element element: elements) {                                             //making loop through the elements
                Element link = element.select("a").first();                       //in each element, find link to the page with description
                String linkHref = link.attr("href");

                Document internalDocument = Jsoup.connect(urlMain + linkHref).get();  //entering to the description page

                Element brandUnderAlt = internalDocument.select(".brand_1h3c7xk").select("img").first();            //find brand name under image (<alt> attribute
                String brand = brandUnderAlt.attr("alt");

                String name = internalDocument.getElementsByClass("name_1jqcvyg").text();                         //find model of the brand

                String initialPrice = internalDocument.getElementsByClass("originalPrice_17gsomb-o_O-strikeOut_32pxry").text().replaceAll("[a-zA-Z]+\\s", ""); //find initial price if exist
                String price = internalDocument.getElementsByClass("finalPrice_klth9m-o_O-highlight_1t1mqn4").text().replaceAll("[a-zA-Z]+\\s", "");           //find actual price if exist

                if (initialPrice.equals("") && price.equals("")) {                                                                                      //check if we have no sale to this product
                    price = internalDocument.getElementsByClass("finalPrice_klth9m").text().replaceAll("[a-zA-Z]+\\s", "");              //then we find another existing price
                    offersList.add(new OfferClass(name, brand, price));                                   //adding date to the offer list (with no sales)
                } else {
                    offersList.add(new OfferClass(name, brand, price, initialPrice));                     //adding date to the offer list (with sales)
                }
            }
            document = Jsoup.connect(url + "?page=" + i).get();                                       //get document from next page
        }

        XmlWriter xmlWriter = new XmlWriter();
        try {
            org.w3c.dom.Document jeansOffers = xmlWriter.makeDocument(offersList);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();

            DOMSource source = new DOMSource(jeansOffers);
            StreamResult streamResult = new StreamResult(new File("jeans.xml"));

            t.transform(source, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long finish = System.currentTimeMillis();
        long consumedTimeMillis = finish - start;

        System.out.println("Amount of extracted products: " + offersList.size());
        System.out.println("Run Time (in seconds): " + consumedTimeMillis/1000);
    }
}


//Thank for your attention. But I could not understand how to extract color. Because some offers have different colors with different prices but some don`t.
//And I don`t know what description i should extract (as it says in task)