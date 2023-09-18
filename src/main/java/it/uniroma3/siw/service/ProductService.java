package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Set;

import it.uniroma3.siw.model.Product;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.Supplier;
import it.uniroma3.siw.repository.ProductRepository;
import it.uniroma3.siw.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductRepository productRepository;

//    @Autowired
//    ImageRepository imageRepository;

//    @Transactional
//    public void createMovie(Book book, MultipartFile image) throws IOException {
//        Image bookImg = new Image(image.getBytes());
//        this.imageRepository.save(bookImg);
//
//        book.setImage(bookImg);
//        this.productRepository.save(book);
//    }

    @Transactional
    public void createProduct(Product product){

    	this.productRepository.save(product);
    }
    
    @Transactional
    public void setSupplierToProduct(Product product, Long productId) {
        Supplier supplier = this.supplierRepository.findById(productId).get();

        supplier.getProducts().add(product);
        product.getSuppliers().add(supplier);

        this.supplierRepository.save(supplier);
        this.productRepository.save(product);
    }

    @Transactional
    public void removeSupplierToProduct(Product product, Long productId) {
        Supplier supplier = this.supplierRepository.findById(productId).get();

        supplier.getProducts().remove(product);
        product.getSuppliers().remove(supplier);

        this.supplierRepository.save(supplier);
        this.productRepository.save(product);
    }

    public boolean hasReviewFromAuthor(Long productId, String username){
        Product product = this.productRepository.findById(productId).get();
        Set<Review> reviews = product.getReviews();
        for (Review review: reviews) {
            if(review.getAuthor().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Product findById(Long id){
        return this.productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void removeProduct(Product product){
        this.productRepository.delete(product);
    }

    public Iterable<Product> findAll(){
        return this.productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(Product product) {
        return this.productRepository.save(product);
    }
}
