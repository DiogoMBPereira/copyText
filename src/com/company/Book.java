package com.company;

//Criar Class Livros[Titulo,NumeroDePaginas,Autor,Link]
public class Book {
    private String title;
    private int pageNumber;
    private String author;
    private String link;

    public Book(String title, int pageNumber, String author, String link) {
        this.title = title;
        this.pageNumber = pageNumber;
        this.author = author;
        this.link = link;
    }

    public Book(String title, String author, String link) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.pageNumber = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
