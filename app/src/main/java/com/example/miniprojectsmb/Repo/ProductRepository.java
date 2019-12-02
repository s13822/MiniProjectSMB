package com.example.miniprojectsmb.Repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.miniprojectsmb.DBContext.Product;
import com.example.miniprojectsmb.DBContext.ProductDAO;
import com.example.miniprojectsmb.DBContext.ProductDbContext;

import java.util.List;

public class ProductRepository {
    private LiveData<List<Product>> productList;
    private ProductDAO productDAO;

    public ProductRepository(Application app){
        ProductDbContext dbContext = ProductDbContext.getDatabase(app);
        productDAO = dbContext.productDAO();
        productList = productDAO.getProductList();
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public void insertProduct(Product product) {
        new insertAsyncTask(productDAO).execute(product);
    }
    public void updateProduct(Product product) {
        new updateAsyncTask(productDAO).execute(product);
    }
    public void deleteProduct(Product product) {
        new deleteAsyncTask(productDAO).execute(product);
    }

    private class insertAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO asyncTaskDao;

        insertAsyncTask(ProductDAO dao) { asyncTaskDao = dao;}
        @Override
        protected Void doInBackground(Product... products) {
            asyncTaskDao.insertProduct(products[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO asyncTaskDao;

        public updateAsyncTask(ProductDAO productDAO) {
            asyncTaskDao = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncTaskDao.updateProduct(products[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO asyncTaskDao;

        public deleteAsyncTask(ProductDAO productDAO) {
            asyncTaskDao = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncTaskDao.deleteProduct(products[0]);
            return null;
        }
    }
}
