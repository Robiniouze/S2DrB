package com.drbanner.geolocation.s2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.geometry.*;
import org.geojson.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.drbanner.geolocation.s2.utils.Example.*;

public class GeojsonProcessor {

    @Nonnull
    public static S2CellUnion geojsonPolygonToS2CellUnion(Polygon polygon, int minLevel, int maxCells, int maxLevel) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);
        newCoverer.setMaxLevel(maxLevel);
        newCoverer.setMaxCells(maxCells);
        return newCoverer.getCovering(geojsonPolygonToS2Polygon(polygon));
    }

    @Nonnull
    public static S2CellUnion geojsonMultiPolygonToS2CellUnion(MultiPolygon multiPolygon) {
        return geojsonMultiPolygonToS2CellUnion(multiPolygon, DEFAULT_MIN_LEVEL,DEFAULT_MAX_CELLS,DEFAULT_MAX_LEVEL);
    }

    @Nonnull
    public static S2CellUnion geojsonMultiPolygonToS2CellUnion(MultiPolygon multiPolygon, int minLevel, int maxCells, int maxLevel) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);newCoverer.setMaxLevel(maxLevel);newCoverer.setMaxCells(maxCells);
        return newCoverer.getCovering(geojsonMultiPolygonToS2Polygon(multiPolygon));
    }

    @Nonnull
    public static S2Polygon geojsonMultiPolygonToS2Polygon(MultiPolygon multiPolygon) {
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
        return new S2Polygon(loops);
    }

    @Nonnull
    public static S2CellUnion geojsonObjectToS2CellUnion(GeoJsonObject geoJsonObject) {
        return geojsonObjectToS2CellUnion(geoJsonObject, DEFAULT_MIN_LEVEL,DEFAULT_MAX_CELLS,DEFAULT_MAX_LEVEL);
    }

    @Nonnull
    public static S2Cell geojsonPointToS2Cell(Point point) {
        return new S2Cell(new S2Point(S2LatLng.fromDegrees(point.getCoordinates().getLatitude(),point.getCoordinates().getLongitude())));
    }

    @Nonnull
    public static S2CellUnion geojsonPointToS2CellUnion(Point point) {
        S2CellUnion s2CellUnion = new S2CellUnion();
        List<S2CellId> s2CellIds = new ArrayList<>();
        s2CellIds.add(geojsonPointToS2Cell(point).getCellId());
        s2CellUnion.initRawCellIds((ArrayList<S2CellId>) (s2CellIds));
        return s2CellUnion;
    }

    @Nonnull
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

    @Nonnull
    public static S2CellUnion geojsonObjectListToS2Polygon(List<GeoJsonObject> geoJsonObjects) throws Exception {
        return geojsonObjectListToS2CellUnion(geoJsonObjects, DEFAULT_MIN_LEVEL,DEFAULT_MAX_CELLS,DEFAULT_MAX_LEVEL);
    }

    @Nonnull
    public static S2CellUnion geojsonObjectListToS2CellUnion(List<GeoJsonObject> geoJsonObjects) throws Exception {
        return geojsonObjectListToS2CellUnion(geoJsonObjects,DEFAULT_MIN_LEVEL,DEFAULT_MAX_CELLS,DEFAULT_MAX_LEVEL);
    }

    @Nonnull
    public static S2CellUnion geojsonObjectListToS2CellUnion(List<GeoJsonObject> geoJsonObjects, int minLevel, int maxCells, int maxLevel) throws Exception {
        if(geoJsonObjects.size()==0) {
            throw new Exception();
        } else if(geoJsonObjects.size()==1) {
            return geojsonObjectToS2CellUnion(geoJsonObjects.get(0),minLevel,maxCells,maxLevel);
        } else {
            //build polygons
            S2PolygonBuilder s2PolygonBuilder = new S2PolygonBuilder();
            for(GeoJsonObject geoJsonObject : geoJsonObjects) {
                if(geoJsonObject instanceof Polygon) {
                    s2PolygonBuilder.addPolygon(geojsonPolygonToS2Polygon((Polygon)(geoJsonObject)));
                } else if(geoJsonObject instanceof MultiPolygon) {
                    s2PolygonBuilder.addPolygon(geojsonMultiPolygonToS2Polygon((MultiPolygon) (geoJsonObject)));
                }
                else {
                    throw new Exception();
                }
            }
            S2RegionCoverer newCoverer = new S2RegionCoverer();
            newCoverer.setMinLevel(minLevel);newCoverer.setMaxLevel(maxLevel);newCoverer.setMaxCells(maxCells);
            return newCoverer.getCovering(s2PolygonBuilder.assemblePolygon());
        }
    }

    @Nonnull
    public static S2Polygon geojsonPolygonToS2Polygon(Polygon polygon) {
        List<S2Point> s2Points = new ArrayList<>();
        for(List<org.geojson.LngLatAlt> polygonCoordinates : polygon.getCoordinates()) {
            for(LngLatAlt lngLatAlt : polygonCoordinates) {
                s2Points.add(new S2Point(S2LatLng.fromDegrees(lngLatAlt.getLatitude(), lngLatAlt.getLongitude())));
            }
        }
        return new S2Polygon(new S2Loop(s2Points));
    }

    @Nonnull
    public static List<GeoJsonObject> geojsonToGeojsonObject(String someJson) throws IOException, RuntimeException {
        GeoJsonObject geoJsonObject = (new ObjectMapper()).readValue(someJson, GeoJsonObject.class);
        List<GeoJsonObject> geoJsonObjects = new ArrayList<>();
        if(geoJsonObject instanceof Point || geoJsonObject instanceof Polygon || geoJsonObject instanceof MultiPolygon) {
            return List.of(geoJsonObject);
        }
        else if(geoJsonObject instanceof FeatureCollection) {
            for(Feature feature : ((FeatureCollection)(geoJsonObject)).getFeatures()) {
                GeoJsonObject currentGeoJsonObject = feature.getGeometry();
                if(currentGeoJsonObject instanceof Point || currentGeoJsonObject instanceof Polygon || currentGeoJsonObject instanceof MultiPolygon){
                    geoJsonObjects.add(currentGeoJsonObject);
                }
            }
        } else if(geoJsonObject instanceof GeometryCollection) {
            for(GeoJsonObject geoJsonObject1 : ((GeometryCollection) geoJsonObject).getGeometries()) {
                if(geoJsonObject1 instanceof Point || geoJsonObject1 instanceof Polygon || geoJsonObject1 instanceof MultiPolygon){
                    geoJsonObjects.add(geoJsonObject1);
                }
            }
        }
        else {
            throw new RuntimeException();
        }
        if(geoJsonObjects.isEmpty()) {
            throw new RuntimeException();
        }
        return geoJsonObjects;
    }


    //TODO
    // allow this function to take in more geojsonObjects
    @Nonnull
    public static S2Polygon geojsonToS2Polygon(String someJson) throws Exception {
        if (geojsonToGeojsonObject(someJson).size() == 1) {
            if (geojsonToGeojsonObject(someJson).get(0) instanceof Polygon) {
                Polygon polygon = (Polygon) (geojsonToGeojsonObject(someJson).get(0));
                return geojsonPolygonToS2Polygon(polygon);
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Nonnull
    public static S2CellUnion geojsonToS2CellUnion(String someJson, int minLevel, int maxCells, int maxLevel) throws IOException, Exception {
        return geojsonObjectListToS2CellUnion(geojsonToGeojsonObject(someJson), minLevel, maxCells, maxLevel);
    }

    @Nonnull
    public static S2CellUnion geojsonToS2CellUnion(String someJson) throws IOException, Exception {
        return geojsonObjectListToS2Polygon(geojsonToGeojsonObject(someJson));
    }

    @Nonnull
    public static int getMaxCellsValueForConstrainedOutsideCoverage(double areaRatio, String someJson) throws Exception {
        assert areaRatio > 1.0;
        int maxCells = 1;
        S2CellUnion s2CellUnion = geojsonToS2CellUnion(someJson,DEFAULT_MIN_LEVEL,maxCells,DEFAULT_MAX_LEVEL);
        S2Polygon s2Polygon = geojsonToS2Polygon(someJson);
        double currentAreaRatio = s2CellUnion.exactArea()/s2Polygon.getArea();
        while (currentAreaRatio > areaRatio) {
            //maxCells += 5;
            maxCells += 10;
            s2CellUnion = geojsonToS2CellUnion(someJson,DEFAULT_MIN_LEVEL,maxCells,DEFAULT_MAX_LEVEL);
            currentAreaRatio = s2CellUnion.exactArea()/s2Polygon.getArea();
        }
        return maxCells;
    }

    @Nonnull
    public static int getMaxCellsValueForConstrainedOutsideCoverage(String someJson) throws Exception {
        return getMaxCellsValueForConstrainedOutsideCoverage(DEFAULT_OUTER_COVERAGE_AREA_RATIO,someJson);
    }

}
