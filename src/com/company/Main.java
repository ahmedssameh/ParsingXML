package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



public class Main {
    // write doc to output stream

    private static void writeXml(Document doc,
                                 File output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    static void addFirstTime(File file,int flag) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        int numberOfBooks;
        Scanner in =new Scanner(System.in);
        System.out.println("Enter number of books you want to add");
        numberOfBooks=in.nextInt();
        String author;
        String title;
        String genre;
        double price;
        String date;
        String description;
        String Bookid;
        Document doc;
        Element rootElement;
        if(flag==0){
            doc=docBuilder.newDocument();
            rootElement = doc.createElement("book");
        doc.appendChild(rootElement);
        }else{
            doc=docBuilder.parse(file);
            rootElement = doc.getDocumentElement();
        }
        for(int i=0;i<numberOfBooks;i++){
            Bookid=in.next();
            Element bookid = doc.createElement("id");
            // add staff to root
            rootElement.appendChild(bookid);
            // add xml attribute
            bookid.setAttribute("id", Bookid);
            author= in.next();
            Element Author = doc.createElement("Author");
            Author.setTextContent(author);
            bookid.appendChild(Author);
            title=in.next();
            Element Title = doc.createElement("Title");
            Title.setTextContent(title);
            bookid.appendChild(Title);
            genre=in.next();
            Element Genre = doc.createElement("Genre");
            Genre.setTextContent(genre);
            bookid.appendChild(Genre);
            price=in.nextDouble();
            Element Price = doc.createElement("Price");
            Price.setTextContent("%s".formatted(price));
            bookid.appendChild(Price);
            date=in.next();
            Element Date = doc.createElement("Date");
            Date.setTextContent(date);
            bookid.appendChild(Date);
            description=in.next();
            Element Desc = doc.createElement("Description");
            Desc.setTextContent(description);
            bookid.appendChild(Desc);
        }
        writeXml(doc,file);
    }

    static List<Book> loadInMemory(File file) throws ParserConfigurationException, IOException, SAXException {
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the input XML document, parse it and return an instance of the
            // Document class.
            Document document = builder.parse(file);

            List<Book> books = new ArrayList<Book>();
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of the ID attribute.
                    String ID = node.getAttributes().getNamedItem("id").getNodeValue();

                    // Get the value of all sub-elements.
                    String Author = elem.getElementsByTagName("Author")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    String Title = elem.getElementsByTagName("Title").item(0)
                            .getChildNodes().item(0).getNodeValue();

                    String Genre = elem.getElementsByTagName("Genre").item(0)
                            .getChildNodes().item(0).getNodeValue();


                    Double Price = Double.parseDouble(elem.getElementsByTagName("Price")
                            .item(0).getChildNodes().item(0).getNodeValue());

                    String Date = elem.getElementsByTagName("Date").item(0)
                            .getChildNodes().item(0).getNodeValue();

                    String Description = elem.getElementsByTagName("Description").item(0)
                            .getChildNodes().item(0).getNodeValue();

                    books.add(new Book(ID, Author,Title,Genre,Price,Date,Description));
                }
            }
        return books;
    }

        public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        File file=new File("./books.xml");
        int flag;
        if(file.length()==0){
            flag=0;
        }else flag=1;

        Scanner in=new Scanner(System.in);

        int Choice= in.nextInt();
        List<Book> books=new ArrayList<>();
        if(Choice==1) addFirstTime(file,flag);
        if(Choice==2){
            books=loadInMemory(file);
            int choice2= in.nextInt();
            String Title="";
            String Author="";
            if (choice2==1){
                System.out.println("Enter Author Name");
                Author= in.next();
            }else if(choice2==2){
                System.out.println("Enter Title Name");
                Title= in.next();
            }

            for(Book b:books){
                if(b.getAuthor().equals(Author) || b.getTitle().equals(Title)){
                    System.out.println(b);
                }
            }



        }








    }
}