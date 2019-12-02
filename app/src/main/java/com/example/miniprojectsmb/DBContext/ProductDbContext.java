package com.example.miniprojectsmb.DBContext;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDbContext extends RoomDatabase {

    public abstract ProductDAO productDAO();
    private static volatile ProductDbContext INSTANCE;

    public static ProductDbContext getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (ProductDbContext.class){
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductDbContext.class, "product_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
