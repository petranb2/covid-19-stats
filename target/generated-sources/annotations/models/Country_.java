package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Coviddata;

@Generated(value="EclipseLink-2.7.3.v20180807-rNA", date="2021-02-07T12:34:55")
@StaticMetamodel(Country.class)
public class Country_ { 

    public static volatile SingularAttribute<Country, Integer> country;
    public static volatile SingularAttribute<Country, Double> long1;
    public static volatile SingularAttribute<Country, String> name;
    public static volatile CollectionAttribute<Country, Coviddata> coviddataCollection;
    public static volatile SingularAttribute<Country, Double> lat;

}