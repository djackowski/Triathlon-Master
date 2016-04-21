package com.djackowski.trenings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Damcios on 2015-12-13.
 */
public class DataProvider {

    public static HashMap<String, List<String>> getInfo(){
        HashMap<String, List<String>> trainingsDetails = new HashMap<>();
        List<String> superSprinter = new ArrayList<>();
        superSprinter.add("Pływanie");
        superSprinter.add("Jazda na rowerze");
        superSprinter.add("Bieganie");

        List<String> sprint = new ArrayList<>();
        sprint.add("Pływanie");
        sprint.add("Jazda na rowerze");
        sprint.add("Bieganie");

        List<String> olimpic = new ArrayList<>();
        olimpic.add("Pływanie");
        olimpic.add("Jazda na rowerze");
        olimpic.add("Bieganie");

        List<String> longDistance = new ArrayList<>();
        longDistance.add("Pływanie");
        longDistance.add("Jazda na rowerze");
        longDistance.add("Bieganie");

        List<String> international = new ArrayList<>();
        international.add("Pływanie");
        international.add("Jazda na rowerze");
        international.add("Bieganie");

        List<String> halfIronman = new ArrayList<>();
        halfIronman.add("Pływanie");
        halfIronman.add("Jazda na rowerze");
        halfIronman.add("Bieganie");

        List<String> ironman = new ArrayList<>();
        ironman.add("Pływanie");
        ironman.add("Jazda na rowerze");
        ironman.add("Bieganie");

        List<String> ultraman = new ArrayList<>();
        ultraman.add("Pływanie");
        ultraman.add("Jazda na rowerze");
        ultraman.add("Bieganie");

        List<String> free = new ArrayList<>();
        free.add("Pływanie");
        free.add("Jazda na rowerze");
        free.add("Bieganie");

        trainingsDetails.put("Swobodny", free);
        trainingsDetails.put("International Triathlon Union", international);
        trainingsDetails.put("Sprinterski", sprint);
        trainingsDetails.put("Super Sprinterski", superSprinter);
        trainingsDetails.put("Olimpijski (standard)", olimpic);
        trainingsDetails.put("Dystans Długi", longDistance);
        trainingsDetails.put("Half-Ironman (Ironman 70.3)", halfIronman);
        trainingsDetails.put("IRONMAN", ironman);
        trainingsDetails.put("Ultraman Triathlon", ultraman);


        return trainingsDetails;
    }
}
