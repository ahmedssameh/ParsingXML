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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    static boolean valid(Book book, File file) throws ParserConfigurationException, IOException, SAXException {
        List<Book> books = new ArrayList<>();
        books=loadInMemory(file);
        List<String> genres=new ArrayList<>();
        genres.add("science");
        genres.add("fiction");
        genres.add("drama");
        boolean flag=false;
        if(!book.getId().isEmpty()){
            boolean bookid=true;
            for (Book book1 : books){
                if(book1.getId().equals(book.getId())){
                    System.out.println("Id must be unique");
                    bookid=false;
                }
            }
            if (bookid) flag=true;
        }else{
            System.out.println("Book id can not be null");
        }

        if(!book.getAuthor().isEmpty()){
            if(book.getAuthor().matches("[a-zA-Z]+")){
                if(flag)
                    flag=true;
            }else{
                flag=false;
                System.out.println("Author must include only characters");
            }
        } else {
            flag=false;
            System.out.println("Author can not be null");
        }

        if(!book.getGenre().isEmpty()){
            if(genres.contains(book.getGenre().toLowerCase())){
                if(flag)
                    flag = true;
            }else{
                flag=false;
                System.out.println("Genre must be from this list (Science, Fiction, Drama)");
            }
        }else{
            flag=false;
            System.out.println("genre can not be null");
        }

        if(!book.getPrice().isEmpty()){
            try {
                Double doub = Double.parseDouble(book.getPrice());
                if(flag)
                    flag=true;
            }catch (NumberFormatException ex){
                flag=false;
                System.out.println("Price must be double");
            }
        }else {
            flag=false;
            System.out.println("Price can not be null");
        }

        if(!book.getDate().isEmpty()){
            String datePattern = "\\d{1,2}-\\d{1,2}-\\d{4}";
            if (book.getDate().matches(datePattern)){
                if(flag)
                    flag=true;
            }else{
                flag=false;
                System.out.println("date is not in the date format");
            }
        }else{
            flag=false;
            System.out.println("Date must not be null");
        }

        if(!book.getDate().isEmpty()){
            if(flag)
                flag=true;
        }else{
            flag=false;
            System.out.println("Title can not be null");
        }
        return flag;
    }

    static void addFirstTime(File file,int flag) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        int numberOfBooks;
        Scanner in =new Scanner(System.in);
        System.out.println("Enter number of books you want to add");
        numberOfBooks=in.nextInt();
        in.nextLine();
        String author;
        String title;
        String genre;
        String price;
        String date;
        String description;
        String Bookid;
        Document doc;
        Element rootElement;
        if(flag==0){
            doc=docBuilder.newDocument();
            rootElement = doc.createElement("Catalog");
        doc.appendChild(rootElement);
        }else{
            doc=docBuilder.parse(file);
            rootElement = doc.getDocumentElement();
        }
        for(int i=0;i<numberOfBooks;i++){
            System.out.println("Enter Book id");
            Bookid=in.nextLine();

            System.out.println("Enter Author");
            author= in.nextLine();

            System.out.println("Enter title ");
            title=in.nextLine();

            System.out.println("Enter genre ");
            genre=in.nextLine();

            System.out.println("Enter Price");
            price=in.nextLine();

            System.out.println("Enter date");
            date=in.nextLine();

            System.out.println("Enter description");
            description=in.nextLine();

            Book book=new Book(Bookid,author,title,genre,price,date,description);

            if(valid(book,file)) {
                Element bookid = doc.createElement("BookId");
                // add staff to root
                rootElement.appendChild(bookid);
                // add xml attribute
                bookid.setAttribute("id", Bookid);


                Element Author = doc.createElement("Author");
                Author.setTextContent(author);
                bookid.appendChild(Author);

                Element Title = doc.createElement("Title");
                Title.setTextContent(title);
                bookid.appendChild(Title);

                Element Genre = doc.createElement("Genre");
                Genre.setTextContent(genre);
                bookid.appendChild(Genre);

                Element Price = doc.createElement("Price");
                Price.setTextContent("%s".formatted(price));
                bookid.appendChild(Price);

                Element Date = doc.createElement("Date");
                Date.setTextContent(date);
                bookid.appendChild(Date);

                Element Desc = doc.createElement("Description");
                Desc.setTextContent(description);
                bookid.appendChild(Desc);

                System.out.println("Your book is added");
            }
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


                    String Price =elem.getElementsByTagName("Price")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    String Date = elem.getElementsByTagName("Date").item(0)
                            .getChildNodes().item(0).getNodeValue();

                    String Description = elem.getElementsByTagName("Description").item(0)
                            .getChildNodes().item(0).getNodeValue();

                    books.add(new Book(ID, Author,Title,Genre,Price,Date,Description));
                }
            }
        return books;
    }
    /////////////
     static void deletefromfile(File file,String BookID) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element book = (Element) node;
                System.out.println(book.getAttributes().getNamedItem("id"));
            if(book.getAttributes().getNamedItem("id").getNodeValue().equals(BookID)){
                System.out.println("done");
                book.getParentNode().removeChild(book);
            }}
            writeXml(doc,file);
        }}
////////////////////////////

        public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
            File file = new File("./books.xml");
            int flag;
            if (file.length() == 0) {
                flag = 0;
            } else flag = 1;

            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.println("1- Add Books \n" +
                        "2- Search \n" +
                        "3-Delete book by id \n" +
                        "4-Turn off \n" +
                        "Choose number");
                int Choice = in.nextInt();
                List<Book> books = new ArrayList<>();
                List<Book> search=new ArrayList<>();
                if (Choice == 1) addFirstTime(file, flag);
                else if (Choice == 2) {
                    books = loadInMemory(file);
                    System.out.println("Filter your books and skip what ever you want \n" +
                            "make your choice");
                    String Title;
                    String Author;
                    String date;
                    String bookid;
                    String price;
                    String desc;
                    String genre;
                    in.nextLine();
                    System.out.println("Enter Book id");
                    bookid=in.nextLine();
                    System.out.println("Enter Author");
                    Author= in.nextLine();
                    System.out.println("Enter title ");
                    Title=in.nextLine();
                    System.out.println("Enter genre ");
                    genre=in.nextLine();
                    System.out.println("Enter Price");
                    price=in.nextLine();
                    System.out.println("Enter date");
                    date=in.nextLine();
                    System.out.println("Enter description");
                    desc=in.nextLine();
                    for (Book b : books) {
                        if ((!bookid.isEmpty()&&!b.getId().equals(bookid))) {
                            search.add(b);
                        }
                        if ((!Author.isEmpty()&&!b.getAuthor().equals(Author))) {
                            search.add(b);
                        }
                        if ((!Title.isEmpty()&&!b.getTitle().equals(Title))) {
                            search.add(b);
                        }
                        if ((!genre.isEmpty()&&!b.getGenre().equals(genre))) {
                            search.add(b);
                        }
                        if ((!price.isEmpty()&&!b.getPrice().equals(price))) {
                            search.add(b);
                        }
                        if ((!date.isEmpty()&&!b.getDate().equals(date))) {
                            search.add(b);
                        }
                        if ((!desc.isEmpty()&&!b.getDescription().equals(desc))) {
                            search.add(b);
                        }
                    }
                    books.removeAll(search);
                    System.out.println("We found " + books.size() + " books for you");
                    for(Book b:books){
                        System.out.println(b);
                    }

                } else if (Choice == 3) {
                    System.out.println("Enter book id you want to delete");
                    String id = in.next();
                    deletefromfile(file, id);
                } else if (Choice==4) break;
                else {
                    System.out.println("Enter valid number");
                }

            }
        }
}
