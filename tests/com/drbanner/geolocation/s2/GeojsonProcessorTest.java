package com.drbanner.geolocation.s2;

import org.geojson.GeoJsonObject;
import org.geojson.MultiPolygon;
import org.geojson.Point;
import org.geojson.Polygon;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.drbanner.geolocation.s2.utils.Example.*;
import static com.drbanner.geolocation.s2.utils.GeojsonProcessor.geojsonToGeojsonObject;
import static org.junit.Assert.assertTrue;

public class GeojsonProcessorTest {

    @Test
    public void testGeojsonMultiPolygonInstance() throws IOException {
        List<GeoJsonObject> geoJsonObjects = geojsonToGeojsonObject(someMultiPolygonJson);
        assertTrue(geoJsonObjects.size()==1);
        assertTrue(geoJsonObjects.get(0) instanceof MultiPolygon);
    }

    @Test
    public void testGeojsonPolygonInstance() throws IOException {
        List<GeoJsonObject> geoJsonObjects = geojsonToGeojsonObject(someJason);
        assertTrue(geoJsonObjects.size()==1);
        GeoJsonObject geoJsonObject = geoJsonObjects.get(0);
        assertTrue(geoJsonObject instanceof Polygon);
        assertTrue(((Polygon)(geoJsonObject)).getCoordinates().get(0).size()==142);
    }

    @Test
    public void testGeojsonPointInstance() throws IOException {
        List<GeoJsonObject> geoJsonObjects = geojsonToGeojsonObject(pointGeoJson);
        assertTrue(geoJsonObjects.size()==1);
        assertTrue(geoJsonObjects.get(0) instanceof Point);
    }

    //TODO
    //tester une feature collection contenant plusieurs elements

    @Test
    public void testGeojsonPointsFeatureCollection() throws IOException, Exception {
        List<GeoJsonObject> geoJsonObjects = geojsonToGeojsonObject(pointsFeatureCollectionGeoJson);
        assertTrue(geoJsonObjects.size()==3);
        for(GeoJsonObject geoJsonObject : geoJsonObjects) {
            assertTrue(geoJsonObject instanceof Point);
        }
    }

}
