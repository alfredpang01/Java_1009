import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class imdbpulls {


    public static void parse() throws IOException {
        String mov = "charlie and the";

        mov = mov.replace(" ", "+");


        Document doc2 = Jsoup.connect("http://www.imdb.com/find?ref_=nv_sr_fn&q="+mov+"&s=all").userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36")
                .get();
        //Elements certainLinks = doc.select("a[href*=http://www.imdb.com/title/]");
        //Elements newsHeadlines = doc.select("#main > table.results tbody");
        //String elementText = certainLinks.text();
        //System.out.println(elementText);
        //String url ="http://www.imdb.com/title/tt";
        // String select = "a[href="+url+"], a[href^="+url+"#], a[href^="+url+"?]";

        Elements links = doc2.select("a[href*=tt_1]");
        Element link;
        String array[] = new String [links.size()];
        int i =0;
        for (Element n: links) {
            String url = n.attr("href");
            String str = url.substring(7,16);
            array[i] = str;
            i++;

        }



        Document doc = Jsoup.connect("http://www.omdbapi.com/?i=" + array[0] + "&apikey=e115cdf7&r=xml")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36")
                .get();
        Elements links2 = doc.select("movie[title]");
        for (Element element : links2) {

            System.out.println(element.attr("title"));
            System.out.println(element.attr("rated"));
            System.out.println(element.attr("runtime"));
            System.out.println(element.attr("genre"));
            System.out.println(element.attr("released"));
            System.out.println(element.attr("plot"));
            System.out.println(element.attr("poster"));
            //array[i] = (element.absUrl("href")); //<-------HERE*****
            //i++;

        }

        //for(Element element : links){

        //System.out.println(element.absUrl("href"));
        //array[i] = (element.absUrl("href")); //<-------HERE*****
        //i++;

        // }

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            parse();

        } catch (Exception e){
            System.out.println("Exception found!");
            e.printStackTrace();
        }
    }
}
