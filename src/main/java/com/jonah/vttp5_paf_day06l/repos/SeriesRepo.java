package com.jonah.vttp5_paf_day06l.repos;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static com.jonah.vttp5_paf_day06l.repos.Constants.*;

import java.util.List;

@Repository
public class SeriesRepo {
    @Autowired
    private MongoTemplate template;

    /* 
      db.series.find({
        name:{
        $regex:'name',
        $options: "i"       options makes it case insensitive
        }
         }).projections
     */

    public List<Document> findSeriesByName(String name){
        //criterea
        Criteria criteria = Criteria.where(F_NAME) //F_NAME is the field name in the database
            .regex(name, "i");

        //create the query
        Query query = Query.query(criteria);//make sure to import the springboot query
        query.fields().include("name", "id", "summary","image.original","schedule").exclude("_id"); //for projection, so database does not return unnecessary fields

        //perform the query
        List<Document> results = template.find(query, Document.class, "series"); //Series is the name of the collection inside the database, 
        // Import Document is org.bson
        return results;



    }
}
