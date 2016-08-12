package org.webshelf.business.model;

import java.nio.ByteBuffer;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Book {

    @NotNull(message = "ISBN: Can not be blank")
    private Long isbn;

    @NotBlank(message = "Title: Can not be blank")
    private String title;

    @NotBlank(message = "Author: Can not be blank")
    private String author;

    private String country;

    private String publisher;

    @NotNull(message = "Image: Can not be blank")
    private byte[] image;
    
    public ByteBuffer getImageBuffer() {
	return ByteBuffer.wrap(image);
    }

    public Long getIsbn() {
	return isbn;
    }

    public void setIsbn(Long isbn) {
	this.isbn = isbn;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getPublisher() {
	return publisher;
    }

    public void setPublisher(String publisher) {
	this.publisher = publisher;
    }

    public byte[] getImage() {
	return image;
    }

    public void setImage(byte[] image) {
	this.image = image;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Book other = (Book) obj;
	if (isbn == null) {
	    if (other.isbn != null)
		return false;
	} else if (!isbn.equals(other.isbn))
	    return false;
	return true;
    }

}
