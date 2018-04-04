package com.drbanner.geolocation.s2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.geometry.*;
import org.geojson.GeoJsonObject;
import org.geojson.MultiPolygon;
import org.geojson.Point;
import org.geojson.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.drbanner.geolocation.s2.utils.Example.DEFAULT_MAX_CELLS;
import static com.drbanner.geolocation.s2.utils.Example.DEFAULT_MAX_LEVEL;
import static com.drbanner.geolocation.s2.utils.Example.DEFAULT_MIN_LEVEL;

public class GeojsonProcessor {

    public static S2CellUnion geojsonPolygonToS2CellUnion(Polygon polygon, int minLevel, int maxCells, int maxLevel) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);
        newCoverer.setMaxLevel(maxLevel);
        newCoverer.setMaxCells(maxCells);
        List<S2Point> littleS2PointList = new ArrayList<>();
        for (org.geojson.LngLatAlt littlePoint : polygon.getCoordinates().get(0)) {
            littleS2PointList.add(new S2Point(S2LatLng.fromDegrees(littlePoint.getLatitude(), littlePoint.getLongitude())));
        }
        return newCoverer.getCovering(new S2Polygon(new S2Loop(littleS2PointList)));
    }

    public static S2CellUnion geojsonMultiPolygonToS2CellUnion(MultiPolygon multiPolygon) {
        return geojsonMultiPolygonToS2CellUnion(multiPolygon, DEFAULT_MIN_LEVEL,DEFAULT_MAX_CELLS,DEFAULT_MAX_LEVEL);
    }

    public static S2CellUnion geojsonMultiPolygonToS2CellUnion(MultiPolygon multiPolygon, int minLevel, int maxCells, int maxLevel) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);newCoverer.setMaxLevel(maxLevel);newCoverer.setMaxCells(maxCells);
        List<S2Loop> loops = new ArrayList<>();
        List<S2Point> coordinatesList;
        for(List<List<org.geojson.LngLatAlt>> polygonCoordinatesList : multiPolygon.getCoordinates()) {
            for (List<org.geojson.LngLatAlt> polygonCoordinates : polygonCoordinatesList) {
                coordinatesList = new ArrayList<>();
                for (org.geojson.LngLatAlt someLngLat : polygonCoordinates) {
                    coordinatesList.add(new S2Point(S2LatLng.fromDegrees(someLngLat.getLatitude(), someLngLat.getLongitude())));
                }
                loops.add(new S2Loop(coordinatesList));
            }
        }
        return newCoverer.getCovering(new S2Polygon(loops));
    }

    public static S2CellUnion geojsonObjectToS2CellUnion(GeoJsonObject geoJsonObject) {
        return geojsonObjectToS2CellUnion(geoJsonObject, DEFAULT_MIN_LEVEL,DEFAULT_MAX_CELLS,DEFAULT_MAX_LEVEL);
    }

    public static S2Cell geojsonPointToS2Cell(Point point) {
        return new S2Cell(new S2Point(S2LatLng.fromDegrees(point.getCoordinates().getLatitude(),point.getCoordinates().getLongitude())));
    }

    public static S2CellUnion geojsonPointToS2CellUnion(Point point) {
        S2CellUnion s2CellUnion = new S2CellUnion();
        List<S2CellId> s2CellIds = new ArrayList<>();
        s2CellIds.add(geojsonPointToS2Cell(point).getCellId());
        s2CellUnion.initRawCellIds((ArrayList<S2CellId>) (s2CellIds));
        return s2CellUnion;
    }

    public static S2CellUnion geojsonObjectToS2CellUnion(GeoJsonObject geoJsonObject, int minLevel, int maxCells, int maxLevel) {
        S2CellUnion s2CellUnion = new S2CellUnion();
        if(geoJsonObject instanceof Polygon) {
            return geojsonPolygonToS2CellUnion((Polygon)(geoJsonObject), minLevel, maxCells, maxLevel);
        } else if (geoJsonObject instanceof MultiPolygon) {
            return geojsonMultiPolygonToS2CellUnion((MultiPolygon) (geoJsonObject), minLevel, maxCells, maxLevel);
        } else if(geoJsonObject instanceof Point) {
            return geojsonPointToS2CellUnion((Point)(geoJsonObject));
        }
        return s2CellUnion;
    }

    public static S2CellUnion geojsonToS2CellUnion(String someJson) {
        S2CellUnion s2CellUnion = new S2CellUnion();
        ObjectMapper objectMapper = new ObjectMapper();
        GeoJsonObject geoJsonObject;
        try {
            geoJsonObject = objectMapper.readValue(someJson, GeoJsonObject.class);
            return geojsonObjectToS2CellUnion(geoJsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s2CellUnion;
    }
}
