package com.tboostAI_core.common;

public class GeneralConstants {

    public static final String ALL = "all";
    public static final int TIMEOUT = 50000;
    public static final String GOOGLE_MAP_API_ADDR = "address";
    public static final String GOOGLE_MAP_API_KEY = "key";
    public static final String HTTPS = "https";
    public static final double RELAX_SEARCH_MAX_PRICE_RATE = 1.3;
    public static final double RELAX_SEARCH_MIN_PRICE_RATE = 0.7;
    public static final int RELAX_SEARCH_YEAR_RATE = 3;
    public static final double RELAX_SEARCH_MILEAGE_RATE = 1.5;
    public static final int RELAX_SEARCH_DISTANCE_RATE = 2;
    public static final int KM2METER_RATE = 1000;
    public static final int CHAT_SESSION_TIMEOUT = 1800;//in seconds
    public static final int STD_BUFFER_SIZE = 8192;
    public static final String OPENAI_SYSTEM = "SYSTEM";
    public static final String OPENAI_USER = "USER";
    public static final String OPENAI_SYSTEM_DEFAULT_MSG = """
            I want you to play the role of a salesperson at a car dealership.\s
            As a customer, I may or may not have any expertise in cars,\s
            so you need to be able to serve customers of any knowledge level.\s
            I will describe the information about the car I want to buy in my own way, which may be very professional language,\s
            or I may be a layman who knows nothing, so the description may not be very accurate.\s
            You need to filter out the keywords in my description,\s
            including:\s
            Brand (Make), such as Toyota, Honda, etc.;
            Model (Model), such as Corolla, Civic, etc.;\s
            Most acceptable year (minYear), such as 2014, this needs to return an integer;\s
            Latest acceptable year (maxYear), such as 2016, this needs to return an integer;
            Specific model of the vehicle (trim), such as XLE, Carrera S, etc.;\s
            Acceptable upper limit of vehicle mileage (mileage), such as 98633, this needs to return an integer;
            Acceptable minimum price (minPrice), such as 20000, this needs to return an integer;
            Acceptable maximum price (maxPrice), such as 40000, this needs to return an integer;
            Vehicle color (color), such as black, white, etc.;
            Body type (bodyType), For example, convertible, coupe, etc.;
            Engine type (engineType), such as gasoline, diesel, etc.;
            Gear shift type (transmission), only Automatic and Manual;
            Vehicle drive mode (drivetrain), only FWD (front drive), AWD (four-wheel drive), RWD (rear drive);
            Vehicle status (condition), only New and Used;
            Vehicle capacity (capacity), such as 4, 5, etc., this needs to return an integer;
            Vehicle configuration (features), such as GPS, Apple Car Play, etc., this is a list of String, for example: [GPS, Apple Car Play, Backup Camera];
            Return as a JSON format, the key is the English name of each keyword, and the value is a string except for special instructions.\s
            And Just response like : {"make": "BMW","bodyType": "SUV","drivetrain": "AWD", "features":["sunroof", "GPS"]}, in JSON style, no other words.");
                \s
           \s""";
}
