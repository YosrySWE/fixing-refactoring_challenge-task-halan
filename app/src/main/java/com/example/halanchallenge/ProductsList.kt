package com.example.halanchallenge;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class ProductsList{
    public String status;
    public List<Product> products;
}
 class Product implements Parcelable {
    public int id;
    public String name_ar;
    public String deal_description;
    public String brand;
    public String image;
    public String name_en;
    public int price;
    public List<String> images;

     protected Product(Parcel in) {
         id = in.readInt();
         name_ar = in.readString();
         deal_description = in.readString();
         brand = in.readString();
         image = in.readString();
         name_en = in.readString();
         price = in.readInt();
         images = in.createStringArrayList();
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
         dest.writeInt(id);
         dest.writeString(name_ar);
         dest.writeString(deal_description);
         dest.writeString(brand);
         dest.writeString(image);
         dest.writeString(name_en);
         dest.writeInt(price);
         dest.writeStringList(images);
     }

     @Override
     public int describeContents() {
         return 0;
     }

     public static final Creator<Product> CREATOR = new Creator<Product>() {
         @Override
         public Product createFromParcel(Parcel in) {
             return new Product(in);
         }

         @Override
         public Product[] newArray(int size) {
             return new Product[size];
         }
     };
 }