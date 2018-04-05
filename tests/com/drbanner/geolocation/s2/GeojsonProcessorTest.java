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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeojsonProcessorTest {

    double epsilon = 1.0E-7;

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

    @Test
    public void testGeojsonPointsFeatureCollection() throws IOException, Exception {
        List<GeoJsonObject> geoJsonObjects = geojsonToGeojsonObject(pointsFeatureCollectionGeoJson);
        assertTrue(geoJsonObjects.size()==3);
        for(GeoJsonObject geoJsonObject : geoJsonObjects) {
            assertTrue(geoJsonObject instanceof Point);
        }
        assertEquals(99.84374999999999,((Point) (geoJsonObjects.get(0))).getCoordinates().getLongitude(),epsilon);
        assertEquals(68.00757101804004,((Point) (geoJsonObjects.get(0))).getCoordinates().getLatitude(),epsilon);
        assertEquals(59.0625,((Point) (geoJsonObjects.get(1))).getCoordinates().getLongitude(),epsilon);
        assertEquals(37.71859032558816,((Point) (geoJsonObjects.get(1))).getCoordinates().getLatitude(),epsilon);
        assertEquals(19.6875,((Point) (geoJsonObjects.get(2))).getCoordinates().getLongitude(),epsilon);
        assertEquals(54.36775852406841,((Point) (geoJsonObjects.get(2))).getCoordinates().getLatitude(),epsilon);
    }

    @Test
    public void testGeojsonMultiPolygonSerialization() throws Exception {
        List<GeoJsonObject> geoJsonObjects = geojsonToGeojsonObject(someGermanMultiPolygonJson);
        assertTrue(geoJsonObjects.size()==1);
        assertTrue(geoJsonObjects.get(0) instanceof MultiPolygon);
    }

    //TODO
    //write tests regarding the S2CellUnion construction //and regarding the S2PolygonBuilder construction


    

}
