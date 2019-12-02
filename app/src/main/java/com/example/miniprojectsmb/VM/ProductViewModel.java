package com.example.miniprojectsmb.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.miniprojectsmb.DBContext.Product;
import com.example.miniprojectsmb.Repo.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private LiveData<List<Product>> productList;
    private ProductRepository productRepository;

    public ProductViewModel(Application app){
        super(app);
        //repo
        productRepository = new ProductRepository(app);
        //products
        productList = productRepository.getProductList();
    }

    public LiveData<List<Product>> getProducts(){
        return productList;
    }

    public void insertProduct(Product product) {
        productRepository.insertProduct(product);
    }

    public void updateProduct(Product product){
        productRepository.updateProduct(product);
    }

    public void deleteProduct(Product product) {
        productRepository.deleteProduct(product);
    }
}
