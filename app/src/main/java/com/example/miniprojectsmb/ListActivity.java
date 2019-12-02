package com.example.miniprojectsmb;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniprojectsmb.Adapters.ProductAdapter;
import com.example.miniprojectsmb.DBContext.Product;
import com.example.miniprojectsmb.DBContext.ProductDbContext;
import com.example.miniprojectsmb.VM.ProductViewModel;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ProductDbContext dbContext;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setCnfiguration();
        setListeners();

    }

    private void setCnfiguration() {
        rv = findViewById(R.id.rv1);
        dbContext = ProductDbContext.getDatabase(this);
        productAdapter = new ProductAdapter(this);
        getListOfProducts(productAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(productAdapter);
    }

    private void setListeners(){
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(Product product) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Aktualizuj produkt");
                builder.setMessage("Wprowadź dane produktu");

                LinearLayout ll = new LinearLayout((ListActivity.this));
                ll.setOrientation(LinearLayout.VERTICAL);
                final EditText name = new EditText(ListActivity.this);
                name.setText(product.getName());
                name.setHint("Nazwa");
                final EditText price = new EditText(ListActivity.this);
                price.setHint("Cena");
                price.setText(product.getPrice() + "");
                //price.setInputType(InputType.TYPE_CLASS_NUMBER);
                final EditText count = new EditText(ListActivity.this);
                count.setHint("Ilosc");
                count.setText(product.getCount() + "");

                ll.addView(name);
                ll.addView(price);
                ll.addView(count);
                // Add the buttons
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        product.setName(name.getText().toString());
                        product.setPrice(Integer.parseInt(price.getText().toString()));
                        product.setCount(Integer.parseInt(count.getText().toString()));
                        productViewModel.updateProduct(product);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.setView(ll);
                builder.show();

            }
        });

        productAdapter.setOnBoughtClickListener(new ProductAdapter.OnBoughtClickListener() {

            @Override
            public void OnBoughtClickListener(Product product) {
                if(product.isBought()) {
                    product.setBought(false);
                    Toast.makeText(ListActivity.this, "Produkt " + product.getName() + " został oddany", Toast.LENGTH_LONG).show();
                } else {
                    product.setBought(true);
                    Toast.makeText(ListActivity.this, "Produkt " + product.getName()+ " został kupiony", Toast.LENGTH_LONG).show();
                }
                productViewModel.updateProduct(product);
            }
        });


        productAdapter.setOnItemLongClickListener(new ProductAdapter.OnItemLongClickListener() {

            @Override
            public void OnItemLongClick(Product product) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Usuwanie produktu");
                builder.setMessage("Czy na pewno chces usunąć produkt?");

                // Add the buttons
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        productViewModel.deleteProduct(product);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();




            }
        });
    }


    public void addNewProductClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle("Dodaj nowy produkt");
        builder.setMessage("Wprowadź nowy produkt");

        LinearLayout ll = new LinearLayout((ListActivity.this));
        ll.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(ListActivity.this);
        name.setHint("Nazwa");
        final EditText price = new EditText(ListActivity.this);
        price.setHint("Cena");
        //price.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText count = new EditText(ListActivity.this);
        count.setHint("Ilosc");

        ll.addView(name);
        ll.addView(price);
        ll.addView(count);
            // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Product product = new Product(name.getText().toString(), Integer.parseInt(price.getText().toString()), Integer.parseInt(count.getText().toString()), false);
                productViewModel.insertProduct(product);
                Log.i("ListActivity", name.getText().toString());

                String usedIntentString = "com.example.miniprojectsmb.intent.action.EVENT1";
//                String permission1 = Manifest.permission.READ_SMS;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.setAction(usedIntentString);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("price", Integer.parseInt(price.getText().toString()));
                intent.putExtra("count", Integer.parseInt(count.getText().toString()));
                intent.putExtra("bought", false);

//                sendBroadcast(intent);
//                Sending broadcast with permissions
                sendBroadcast(intent, "com.example.my_permissions.SIMPLE_PERMISSION");

//                sendOrderedBroadcast(intent, permission1);
//                sendOrderedBroadcast(intent, permission1,
//                        new BroadcastReceiver() {
//                            @Override
//                            public void onReceive(Context context, Intent intent) {
//                                Toast.makeText(context, "Rezultat" + "blablabla", Toast.LENGTH_LONG).show();
//                            }
//                        }, null, 0, null, null
//                );

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setView(ll);
        builder.show();
    }

    private void getListOfProducts(ProductAdapter adapter) {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });
    }
}

