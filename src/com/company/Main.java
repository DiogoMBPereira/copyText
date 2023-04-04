package com.company;

import com.itextpdf.text.DocumentException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;


public class Main {
//TODO
//Criar Class Livros[Titulo,NumeroDePaginas,Autor,Link] - Done
//Fazer ArrayList de livros - Done
//Criar tudo ao mesmo tempo(em vez de fazer link a link) - Done
//Criar Pasta com nome do autor - Done
//Criar uma maneira de ler o numero de paginas pelo link - Done
//Editar o texto para criar capa
//Aumentar tamanho de titulos (talvez mudar cor)
//Criar pagebreak antes dos titulos
    public static void main(String[] args) throws IOException {

        Book book1 = new Book("(Technomancer #1) Tech Mage",
                "D.L. Harrison","https://readfrom.net/d-l-harrison/542206-tech_mage_technomancer_book_one.html");
        Book book2 = new Book("(Technomancer #2) Astraeus Station",
                "D.L. Harrison","https://readfrom.net/d-l-harrison/542665-astraeus_station.html");
        Book book3 = new Book("(Technomancer #3) The Grays",
                "D.L. Harrison","https://readfrom.net/d-l-harrison/542666-the_grays.html");
        Book book4 = new Book("(Technomancer #4) Fallen Empire",
                "D.L. Harrison","https://readfrom.net/d-l-harrison/579479-fallen_empire.html");
        Book book5 = new Book("(Technomancer #5) Intergalactic Union",
                "D.L. Harrison","https://readfrom.net/d-l-harrison/579480-intergalactic_union.html");
        Book book6 = new Book("(Technomancer #6) Infinite Exploration",
                "D.L. Harrison","https://readfrom.net/d-l-harrison/579481-infinite_exploration.html");
        Book book7 = new Book("Mage Throne Prophecy", "James Haddock",
                "https://readfrom.net/james-haddock/588444-mage_throne_prophecy.html");

        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);
        String path = System.getProperty("user.dir")+"/";

       /*
        Document doc = Jsoup.connect("https://readfrom.net/d-l-harrison/page,1,542666-the_grays.html").get();
        Element content = doc.getElementById("textToRead");
        String inputFile = "content2.html";
        emptyHTML(inputFile);
        //content.html("<p>lorem ipsum</p>");
        content.select("center").remove();
        String c = content.html();
        String arrayOfChapter[] = c.split("Chapter");
        //writeHTML(inputFile, c);
        String finalV = " ";
        for(int i = 0; i< arrayOfChapter.length; i++)
        {
            if(i != 0)
            {
                //System.out.println(i+" "+arrayOfChapter[i]);
                finalV += "<p> Chapter "+ arrayOfChapter[i] + " <p>";
                //break;
            }
            else
            {
                finalV += arrayOfChapter[i];
            }

        }
        writeHTML(inputFile,finalV);*/

       for(int i=0; i < books.size();i++)
        {
            int n = 1;
            String inputFile = "content.html";
            String originalLink = books.get(i).getLink();
            String[] arrayOfLink = originalLink.split("/");
            do {
                String page = "";
                for(int j=0; j < arrayOfLink.length; j++)
                {
                    if(j == 4){
                        page +="page,"+n+","+arrayOfLink[j];
                        break;
                    }else{
                        page +=arrayOfLink[j]+"/";
                    }
                }
                Document doc = Jsoup.connect(page).get();
                Element content = doc.getElementById("textToRead");
                if(n == 1)
                {
                    Elements pageNumber = content.getElementsByClass("pages");
                    String pageNum = pageNumber.html();
                    String[] splitForPages = pageNum.split("<a");
                    books.get(i).setPageNumber(splitForPages.length);
                }
                content.select("center").remove();
                String c = content.html();
                writeHTML(inputFile, c);
                n++;
            }while(n <= books.get(i).getPageNumber());
            String pathForAuthor = path+books.get(i).getAuthor();
            new File(pathForAuthor).mkdirs();
            String html = new String(Files.readAllBytes(Paths.get(inputFile)));
            final Document document = Jsoup.parse(html, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            String outputFile = books.get(i).getTitle()+".pdf";

            createPdf(document, outputFile,pathForAuthor);
            emptyHTML(inputFile);
        }

    }

    private static void writeHTML(String inputFile, String c) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, true));
        writer.write(c);
        writer.close();
    }
    private static void emptyHTML(String inputFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        writer.write("");
        writer.close();
    }

    private static void createPdf(Document document, String outputFile,String pathForAuthor) throws IOException {
        try (OutputStream os = new FileOutputStream(pathForAuthor+"\\"+outputFile)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputFile);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "UTF-8");
            builder.run();
        }
    }
    //content.removeClass("splitnewsnavigation2 ignore-select");
    /*ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(document.html());
    renderer.layout();

    try (OutputStream os = Files.newOutputStream(Paths.get(outputFile))) {
    renderer.createPDF(os);
    } catch (DocumentException e) {
    e.printStackTrace();
    }*/
}

