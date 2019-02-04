package com.imcool.julian.ratinglist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Julian on 4/4/2018.
 */

public class RatingObject implements Serializable {
    //Instance used to save object in class and can be used throughout program
    private static RatingObject instance;
    private ArrayList<RatingObjectSingle> ratingObjects = new ArrayList();
    private RatingObjectSingle ratingObject;

    //Singleton method used to initiate Object
    public  static RatingObject Instance(){
        if (instance == null){
            instance = new RatingObject();
        }
        return instance;
    }
    //adds object to instance when called
    public void addRatingObject(float rating, String name){
        instance.ratingObjects.add(ratingObject = new RatingObjectSingle(rating,name));
    }
    //saves object when called
    public void saveRatingObject(int index, float rating, String name){
        setRatingObjectName(index,name);
        setRatingObjectRating(index,rating);
        instance.ratingObjects.set(index,ratingObject);
    }
    //gets single rating object rating
    public float getRatingObjectRating(int num){
        ratingObject = instance.ratingObjects.get(num);
        return ratingObject.getRating();
    }
    //sets single rating Object rating
    public void setRatingObjectRating(int index, float rating){
        ratingObject=instance.ratingObjects.get(index);
        ratingObject.setRating(rating);
        instance.ratingObjects.set(index,ratingObject);
    }
    public void setRatingObjectName(int index, String name){
        ratingObject=instance.ratingObjects.get(index);
        ratingObject.setName(name);
        instance.ratingObjects.set(index,ratingObject);
    }
    public String getRatingObjectName(int num){
        ratingObject = instance.ratingObjects.get(num);
        return ratingObject.getName();
    }
    public int getLength(){
        return instance.ratingObjects.size();
    }

    //Resets all objects to nothing
    public void resetObjects(){
        instance.ratingObjects = new ArrayList<>();
    }
    class RatingObjectSingle implements Serializable{
        private float rating;
        private String name;
        public float getRating() {
            return rating;
        }

        public String getName() {
            return name;
        }
        public RatingObjectSingle(float rating, String name) {
            this.rating = rating;
            this.name = name;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



}
