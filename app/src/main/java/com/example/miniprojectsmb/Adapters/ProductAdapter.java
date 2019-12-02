package com.example.miniprojectsmb.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojectsmb.DBContext.Product;
import com.example.miniprojectsmb.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    // viewholder jest odpowiedzialny za dobieranie kolejnych elementow ktore beda przechowywane w liscie
    private OnItemCLickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private OnBoughtClickListener boughtClickListener;
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tu jest tworzony widok na podstawie stworzonego layoutu
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_element, parent, false);

        ProductViewHolder pvh = new ProductViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // tu ejst to co bedzie sie dzialo jak viewholder bedzie polaczony z nasza lista (onCreateViewHolder)
        // czyli reakcja na dodanie
        if(productList != null) {
            Product p = productList.get(position);
            holder.name.setText(p.getName());
            holder.price.setText(String.valueOf(p.getPrice()));
            holder.bought.setChecked(p.isBought());
            holder.count.setText(String.valueOf(p.getCount()));
        }
        else {
            holder.name.setText("Brak danych");
        }
    }

    @Override
    public int getItemCount() {

        if(productList != null) {
            return productList.size();
        }else{
            return 0;
        }

    }

    public void setProducts(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    // onclicklistener daje nam opcje zaznaczania pol
    class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;
        private TextView count;
        private CheckBox bought;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getPrice() {
            return price;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }

        public TextView getCount() {
            return count;
        }

        public void setCount(TextView count) {
            this.count = count;
        }

        public CheckBox getBought() {
            return bought;
        }

        public void setBought(CheckBox bought) {
            this.bought = bought;
        }



        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.textView);
            this.price = itemView.findViewById(R.id.textView2);
            this.count = itemView.findViewById(R.id.textView3);
            this.bought = itemView.findViewById(R.id.checkBox);

            itemView.setOnLongClickListener((view) -> {
                int position = getAdapterPosition();
                if(itemLongClickListener != null && position != RecyclerView.NO_POSITION) {
                    itemLongClickListener.OnItemLongClick(productList.get(position));
                }
                return true;
            });

            bought.setOnClickListener((view) -> {
                int position = getAdapterPosition();
                if(boughtClickListener != null && position != RecyclerView.NO_POSITION) {
                    boughtClickListener.OnBoughtClickListener(productList.get(position));
                }
            });
            itemView.setOnClickListener((view) -> {
                int position = getAdapterPosition();
                if(itemClickListener != null && position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(productList.get(position));
                }
            });

        }
    }

    public interface OnItemCLickListener {
        void onItemClick(Product product);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(Product product);
    }
    public interface OnBoughtClickListener {
        void OnBoughtClickListener(Product product);
    }

    public void setOnItemClickListener(OnItemCLickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }
    public void setOnBoughtClickListener(OnBoughtClickListener onClickListener) {
        this.boughtClickListener = onClickListener;
    }
}
