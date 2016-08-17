package org.webshelf.business.service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.webshelf.business.data.CassandraCluster;
import org.webshelf.business.model.Book;
import org.webshelf.business.model.User;

import com.datastax.driver.core.BatchStatement;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BookBean {

    @Inject
    private CassandraCluster cassandraCluster;

    @Inject
    private Validator validator;

    public void insertBook(Book book) {
	executeBeanValidation(book);

	BatchStatement batch = new BatchStatement();
	batch.add(this.cassandraCluster.boundInsertBookByISBN().bind(book.getIsbn(), book.getTitle(), book.getAuthor(),
		book.getCountry(), book.getPublisher(), book.getImageBuffer()));
	batch.add(this.cassandraCluster.boundInsertBookByTitle().bind(book.getIsbn(), book.getTitle(), book.getAuthor(),
		book.getCountry(), book.getPublisher(), book.getImageBuffer()));
	batch.add(this.cassandraCluster.boundInsertBookByAuthor().bind(book.getIsbn(), book.getTitle(), book.getAuthor(),
		book.getCountry(), book.getPublisher(), book.getImageBuffer()));
	
	this.cassandraCluster.execute(batch);

    }

    private void executeBeanValidation(Book book) {
	Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);
	
	if (!constraintViolations.isEmpty()) {
	    throw new ConstraintViolationException(constraintViolations);
	}

    }

}
