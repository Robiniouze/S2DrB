package com.drbanner.geolocation.s2;

import com.google.common.geometry.S2CellUnion;

import java.io.IOException;

import static com.drbanner.geolocation.s2.utils.Example.someJason;
import static com.drbanner.geolocation.s2.utils.Example.someMultiPolygonJson;
import static com.drbanner.geolocation.s2.utils.GeojsonProcessor.geojsonToS2CellUnion;

public class Main {

    public static void main(String[] args) throws IOException, Exception {
        S2CellUnion s2CellUnion = geojsonToS2CellUnion(someMultiPolygonJson);
        System.out.println(s2CellUnion.cellIds().size());

        S2CellUnion s2CellUnion_2 = geojsonToS2CellUnion(someJason);
        System.out.println(s2CellUnion_2.cellIds().size());
    }
}