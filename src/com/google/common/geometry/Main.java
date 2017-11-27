package com.google.common.geometry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import org.geojson.MultiPolygon;

import com.fasterxml.jackson.core.*;
import org.geojson.Polygon;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class Main {

    public static String someJason="{\"type\": \"Polygon\", \"coordinates\": [[[-118.4375838, 33.949583], [-118.4370865, 33.9488622], [-118.4366407, 33.9481529], [-118.4364277, 33.9476954], [-118.4362923, 33.9472674], [-118.4362053, 33.9469334], [-118.4361629, 33.9466421], [-118.4361032, 33.9460338]," +
            "[-118.4360994, 33.9457194], [-118.436106, 33.9453055], [-118.4361013, 33.9449661], [-118.4360624, 33.9446211], [-118.4358893, 33.9438668], [-118.4356962, 33.943404], [-118.4353743, 33.9429056], [-118.4340933, 33.9405895], [-118.4294726, 33.9325713], [-118.4288329, 33.9311771], [-118.4257733, 33.9315848], " +
            "[-118.4257555, 33.9315847], [-118.4256545, 33.9315841], [-118.39066, 33.9313889], [-118.3855059, 33.9313601], [-118.3837787, 33.9314131], [-118.3835982, 33.9314567], [-118.3835748, 33.9315698], [-118.3794531, 33.9315492], [-118.3792638, 33.9315834], [-118.3791456, 33.9316806], [-118.3790909, 33.9318227]," +
            "[-118.3791558, 33.9346045], [-118.3702028, 33.9346352], [-118.3701913, 33.9393248], [-118.3712272, 33.9393275], [-118.37123, 33.9393609], [-118.3712915, 33.9400929], [-118.3714417, 33.9401018], [-118.371468, 33.9407311], [-118.3757762, 33.9406893], [-118.3757655, 33.9413213], [-118.3788125, 33.9413035]," +
            "[-118.379188, 33.9413124], [-118.379145, 33.9449794], [-118.3814566, 33.94495], [-118.3814489, 33.9438632], [-118.3898607, 33.9438198], [-118.3899044, 33.9441493], [-118.389979, 33.944784], [-118.3899755, 33.9453326], [-118.3921743, 33.9453381], [-118.3934892, 33.9455581], [-118.3964075, 33.9455474]," +
            "[-118.3964464, 33.9488483], [-118.3964769, 33.950655], [-118.3964826, 33.9511028], [-118.396606, 33.9516635], [-118.3967132, 33.9519172], [-118.3969278, 33.952242], [-118.3971209, 33.9524511], [-118.397287, 33.9525906], [-118.3974374, 33.9526781], [-118.3977161, 33.9528354], [-118.397979, 33.9529333]," +
            "[-118.3983277, 33.9530267], [-118.3986227, 33.9530757], [-118.3986868, 33.9530756], [-118.4021474, 33.9530652], [-118.4051029, 33.9530579], [-118.4051458, 33.9530312], [-118.4069807, 33.9530386], [-118.4088556, 33.9530208], [-118.4090699, 33.9530178], [-118.4095742, 33.9529689], [-118.412133, 33.952693]," +
            "[-118.4145955, 33.9524446], [-118.41606, 33.9522888], [-118.4162156, 33.9522755], [-118.4163417, 33.9522777], [-118.416465, 33.9523022], [-118.4165694, 33.9523259], [-118.4167249, 33.9523748], [-118.416827, 33.952426], [-118.4169187, 33.9524858], [-118.4169753, 33.9524486], [-118.4171139, 33.9525728]," +
            "[-118.4171621, 33.9526351], [-118.4172909, 33.9528398], [-118.4173609, 33.9529762], [-118.4173606, 33.9530401], [-118.4174089, 33.9530623], [-118.4177039, 33.9530045], [-118.4180687, 33.9529466], [-118.4184767, 33.9528872], [-118.4190236, 33.9528443], [-118.419501, 33.952782], [-118.4209014, 33.9525535]," +
            "[-118.4211053, 33.9525179], [-118.4215932, 33.9524794], [-118.4220008, 33.9524349], [-118.4225, 33.9524022], [-118.4229453, 33.9523888], [-118.423471, 33.9523666], [-118.4239159, 33.952337], [-118.4242646, 33.952337], [-118.4247799, 33.9523621], [-118.4254555, 33.9524037], [-118.4258528, 33.9523933]," +
            "[-118.4263141, 33.952371], [-118.426802, 33.9523414], [-118.4268774, 33.9522464], [-118.4269415, 33.952248], [-118.4270005, 33.9522169], [-118.4271402, 33.9521663], [-118.4274084, 33.9521397], [-118.427961, 33.9520774], [-118.4286154, 33.9520551], [-118.4291677, 33.9520255], [-118.4298758, 33.9519454]," +
            "[-118.4311153, 33.9518237], [-118.432113, 33.9517347], [-118.4323813, 33.9516991], [-118.4342642, 33.9517125], [-118.4349482, 33.9517995], [-118.4356821, 33.9519235], [-118.4364182, 33.951968], [-118.436993, 33.9520096], [-118.4372982, 33.9520073], [-118.437599, 33.9519839], [-118.4378614, 33.9519432]," +
            "[-118.4382441, 33.9518573], [-118.4385637, 33.9517528], [-118.4388193, 33.9516509], [-118.4388931, 33.9515946], [-118.4388789, 33.9515354], [-118.4388303, 33.9514592], [-118.4385825, 33.9510964], [-118.4384432, 33.9508257], [-118.4381075, 33.9508327], [-118.4380994, 33.9506675], [-118.4383217, 33.9506656]," +
            "[-118.4375838, 33.949583], [-118.4375838, 33.949583]]]}";

    //public void timePointInPolygonOperations(List<S2Cell> cellList,List<S2Point> pointList) {
    public static void timePointInPolygonOperations(List<S2Cell> cellList, S2Point point) {
        System.out.println("Time per loop (in ms.):");
        int listSize = cellList.size();
        long startTime = System.nanoTime();
        for (S2Cell cell : cellList) {
            cell.contains(point);
        }
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/(1000.*listSize));
    }

    public static void timeCellInCellOperations(List<S2Cell> cellList, S2Cell supposedlySmallerCell) {
        System.out.println("Time per loop (in ms.):");
        int listSize = cellList.size();
        long startTime = System.nanoTime();
        for (S2Cell cell : cellList) {
            cell.contains(supposedlySmallerCell);
        }
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/(1000.*listSize));
    }

    public static void timePointInCellUnionOperations(List<S2CellUnion> s2CellUnionList, S2Point s2Point) {
        System.out.println("Time per loop (in ms.):");
        int listSize = s2CellUnionList.size();
        long startTime = System.nanoTime();
        for (S2CellUnion cellUnion : s2CellUnionList) {
            cellUnion.contains(s2Point);
        }
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/(1000.*listSize));
    }

    public static void timePointInCellUnionAvg(S2CellUnion s2CellUnion, S2Point s2Point) {
        List<S2CellUnion> s2CellUnionList = Collections.nCopies(100000,s2CellUnion);
        timePointInCellUnionOperations(s2CellUnionList,s2Point);
    }

    public static S2Point s2PointFromGPSCoordinates(double lat, double lng) {
        return new S2Point(S2LatLng.fromDegrees(lat,lng));
    }

    public static S2Polygon geojsonToS2Polygon(String someJson) {
        Gson gson = new GsonBuilder().create();
        ObjectMapper objectMapper = new ObjectMapper();
        Polygon polygon = new Polygon();
        try {
            polygon = objectMapper.readValue(someJson, Polygon.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //assertThat(!polygon.getCoordinates().equals(Collections.emptyList()));
        // is it a deep or shallow equal ?

        List<S2Point> littleS2PointList = new ArrayList<>();
        for (org.geojson.LngLatAlt littlePoint : polygon.getCoordinates().get(0)) {
            littleS2PointList.add(new S2Point(S2LatLng.fromDegrees(littlePoint.getLatitude(),littlePoint.getLongitude())));
        }
        return new S2Polygon(new S2Loop(littleS2PointList));
    }

    public static S2CellUnion geojsonToS2CellUnion(String someJson) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        return newCoverer.getCovering(geojsonToS2Polygon(someJson));
    }

    public static S2CellUnion geojsonToS2CellUnion(String someJson, int minLevel, int maxCells) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);
        newCoverer.setMaxCells(maxCells);
        return newCoverer.getCovering(geojsonToS2Polygon(someJson));
    }

    public static void testThatOuterBorderlinePointOutsideOfNormalCovering(int[] minLevels,int[] maxCellValues, S2Point outerBorderlinePoint) {
        boolean containment = true;
        for (int i=0; i<maxCellValues.length;i++) {
            containment = containment && geojsonToS2CellUnion(someJason,minLevels[i],maxCellValues[i]).contains(outerBorderlinePoint);
        }
        assertFalse(containment);
    }

    public static void testInnerPointNormalCovering(S2Point innerPoint) {
        assertTrue(geojsonToS2CellUnion(someJason).contains(innerPoint));
    }

    public static void testInnerPointNormalCovering(List<S2Point> innerPoints) {
        for(S2Point innerPoint : innerPoints) {
            assertTrue(geojsonToS2CellUnion(someJason).contains(innerPoint));
        }
    }

    public static void testTrulyOuterPoint(S2Point outerPoint) {
        assertFalse(geojsonToS2CellUnion(someJason).contains(outerPoint));
    }

    public static void testTrulyOuterPoint(List<S2Point> outerPoints) {
        for(S2Point outerPoint : outerPoints) {
            assertFalse(geojsonToS2CellUnion(someJason).contains(outerPoint));
        }
    }

    public static void handleMultiPolygons() {// OR NOT ???

    }

    public static void someRandomChecks() {
        System.out.println("TESTS");
        System.out.println("-----------------------------");
        /*
        System.out.println(S2.origin().x);
        System.out.println(S2.origin().y);
        System.out.println(S2.origin().z);
        */
        System.out.println("Origin");
        System.out.println(S2.origin());
        System.out.println("Origin's orthogonal");
        System.out.println(S2.ortho(S2.origin()));
        S2LatLng s2LatLng = new S2LatLng(S2.origin());
        System.out.println("Origin's GPS coordinates");
        System.out.println(s2LatLng);

        System.out.println("-----------------------");
        int face = 0;
        S2CellId id = S2CellId.fromFacePosLevel(face, 0, 0);
        S2Cell cell = new S2Cell(id);
        System.out.println("id token");
        System.out.println(id.toToken().toString());
        System.out.println("Rectangle bounds");
        System.out.println(cell.getRectBound().toString());
        System.out.println("Level");
        System.out.println(cell.level());
        System.out.println("id2");
        S2CellId id2 = S2CellId.fromFacePosLevel(3, 10, 5);
        S2Cell cell2 = new S2Cell(id2);

        S2CellId id3 = S2CellId.fromFacePosLevel(5, 8, 10);
        S2Cell cell3 = new S2Cell(id3);

        System.out.println(id2);
        System.out.println("id2 token");
        System.out.println(id2.toToken().toString());

        System.out.println("----------------------------");
        List<S2Cell> cellList = Collections.nCopies(100000,cell);
        //cellList.addAll(Collections.nCopies(10000,cell2));
        timePointInPolygonOperations(cellList,S2.origin());
        List<S2Cell> cellList2 = Collections.nCopies(100000,cell2);
        timePointInPolygonOperations(cellList2,S2.origin());
        List<S2Cell> cellList3 = Collections.nCopies(100000,cell3);
        timePointInPolygonOperations(cellList3,S2.origin());

        timePointInPolygonOperations(cellList3,S2.origin().ortho());

        List<S2Cell> newList = ListUtils.union(cellList,cellList2);
        timePointInPolygonOperations(newList,S2.origin());

        S2CellId bigCell = S2CellId.fromFacePosLevel(3, 0, 0);
        S2CellId smallCell = S2CellId.fromFacePosLevel(3, 0, 5);

        System.out.println("---------------------------");

        if(bigCell.contains(smallCell)) {
            System.out.println("the big cell contains the small one");
        }

        S2CellId otherSmallCell = S2CellId.fromFacePosLevel(3, 1, 5);
        System.out.println("---------------------------");

        if(bigCell.contains(otherSmallCell)) {
            System.out.println("the big cell contains the small one");
        }
        else {
            System.out.println("doesnt contain");
        }



        S2CellId otherOtherSmallCell = S2CellId.fromFacePosLevel(3, 999000000, 1);
        System.out.println("----------------------------------------");

        if(bigCell.contains(otherOtherSmallCell)) {
            System.out.println("the big cell contains the small one");
        }
        else {
            System.out.println("doesnt contain");
        }

        System.out.println("-------------------------");
        timeCellInCellOperations(List.of(new S2Cell(bigCell)),new S2Cell(otherOtherSmallCell));

        System.out.println("-------------------------");
        timeCellInCellOperations(List.of(new S2Cell(bigCell)),new S2Cell(otherSmallCell));


        System.out.println(otherOtherSmallCell.face());
        System.out.println(smallCell.face());


//            public S2CellUnion getInteriorCovering(S2Region region) {
//                S2CellUnion covering = new S2CellUnion();
//                getInteriorCovering(region, covering);
//                return covering;
//            }

//region, point, level, array list S2CellId;
        //S2RegionCoverer.getSimpleCovering();

        S2RegionCoverer s2RegionCoverer = new S2RegionCoverer();
        List<S2Point> s2Points = new ArrayList<>();
        //s2Points =
        //s2Points =

        s2Points.add(S2.origin().ortho());
        s2Points.add(S2.origin());
        s2Points.add(new S2Point(1,1,0));
        s2Points.add(new S2Point(4,5,0));
        //List<S2Point> vertices;
        S2Loop s2Loop = new S2Loop(s2Points);
        System.out.println(s2Loop.numVertices());
        S2Polygon s2Polygon = new S2Polygon(s2Loop);

        System.out.println("Loop rect. bound");
        System.out.println(s2Loop.getRectBound());


        System.out.println("--------------------------------");
        System.out.println("Interior covering");
        //S2CellUnion s2CellUnion = s2RegionCoverer.getInteriorCovering(new S2Polygon());


        // by default min and max levels are 0 and 30, change that for that case
        s2RegionCoverer.setMinLevel(3);
        s2RegionCoverer.setMaxLevel(5);
        System.out.println("Min and Max levels:");
        System.out.println(s2RegionCoverer.minLevel());
        System.out.println(s2RegionCoverer.maxLevel());

        //maxCells is the maximum number of cells used

        // how to get the actual number of cells ?

        //s2RegionCoverer.maxCells();
        S2CellUnion s2CellUnion = s2RegionCoverer.getInteriorCovering(s2Polygon);


        //s2CellUnion.get

        //how to get one cell out of the cell union


        System.out.println("---------------------------");
        System.out.println("cell union size");
        System.out.println(s2CellUnion.size());
        //System.out.println(s2CellUnion.cellId(0));


        System.out.println("-----------------------");
        System.out.println("S2Point hashCode");
        System.out.println((new S2Point(1,1,1)).hashCode());
        //S2Point.

        System.out.println("-------------");
        System.out.println("S2CellUnion hashCode");
        System.out.println(s2CellUnion.hashCode());

        System.out.println("---------");
        System.out.println("get the hashCode of an inner S2Cell");
        System.out.println(s2CellUnion.cellId(0).hashCode());

        S2Cell newCell = new S2Cell(s2CellUnion.cellId(0));
        System.out.println(newCell.hashCode());

        System.out.println("-----------------");
        System.out.println("cell ids");
        System.out.println(s2CellUnion.cellIds());
        System.out.println("inner cell id");
        System.out.println(s2CellUnion.cellId(0));

        System.out.println("---------------------");
        System.out.println("S2 cells area error ratio:");
        System.out.println((s2CellUnion.exactArea()-s2Loop.getArea())/s2Loop.getArea());
        System.out.println("Polygon area:");
        System.out.println(s2Loop.getArea());
        System.out.println("S2 cell union area:");
        System.out.println(s2CellUnion.averageBasedArea());
        System.out.println(s2CellUnion.approxArea());
        System.out.println(s2CellUnion.exactArea());


        System.out.println("----------------------");
        System.out.println("Region covering");
        System.out.println(s2CellUnion.contains(S2.origin()));
        System.out.println(s2CellUnion.contains(S2.origin().ortho()));
        System.out.println(s2CellUnion.contains(new S2Point(1,1,0)));

        //chercher un point plus intérieur qui soit pris en compte
        // par exemple un point dans la rect. bound.

        // how to create a point from GPS coordinates...

        // what is the polygon builder thing ?

        System.out.println(s2Polygon.getRectBound());

//            long startTime = System.nanoTime();
//            s2CellUnion.contains(new S2Point(1,1,0));
//            long endTime = System.nanoTime();
//            System.out.println((endTime-startTime)/1000.);
        System.out.println("Operation with 8 cells");
        timePointInCellUnionAvg(s2CellUnion,new S2Point(1,1,0));



        // -- check the same operation with more cells --
        s2RegionCoverer.setMaxCells(30);
        S2CellUnion widerS2CellUnion = s2RegionCoverer.getInteriorCovering(s2Polygon);
        System.out.println("Operation with 30 cells");
        timePointInCellUnionAvg(widerS2CellUnion,new S2Point(1,1,0));



        System.out.println("---------------------");
        System.out.println("(30 cells) S2 cells area error ratio:");
        System.out.println((widerS2CellUnion.exactArea()-s2Loop.getArea())/s2Loop.getArea());


        System.out.println("---------------------------------");
        System.out.println("Point");
        S2Point s2PointFromLatLng = new S2Point(S2LatLng.fromRadians(0.0,0.4));
        System.out.println(s2PointFromLatLng);


        S2Point otherS2PointFromLatLng = new S2Point(S2LatLng.fromRadians(0.3,0.2));
        System.out.println(otherS2PointFromLatLng);
//            System.out.println();
//            System.out.println("-----------------");
//            System.out.println("contained cells ids");
//            System.out.println(bigCell.id());
//            System.out.println(smallCell.id());
//            System.out.println(otherSmallCell.id());
//            System.out.println(bigCell.hashCode());
//            System.out.println(smallCell.hashCode());
//            System.out.println(bigCell.toLatLng());
        // Polygon to S2Cell
        // S2RegionCoverer ?


        // check that S2Cell contains a smaller S2Cell


        //ListUtils
//        S2CellId id = S2CellId.fromFacePosLevel(face, 0, 0);
//        S2Cell cell = new S2Cell(id);


// if (cell.contains(S2.origin())) {
//     System.out.println("cell contains the origin");
// }
// else {
//     System.out.println("doesnt contain");
// }
    }

        public static void main(String[] args) {
            //someRandomChecks();
            int[] maxCellValues = {1,1,4,8,32,128,512};
            int[] minLevels = {5,10,15,16,18,20,22};
            //int[] maxCellValues = {1,1,4,8,32,128,512,1024};
            //int[] minLevels = {5,10,15,16,18,20,22,23};
            S2Point borderlineOuterPoint = new S2Point(S2LatLng.fromDegrees(33.94582090884453,-118.39608550071718));
            testThatOuterBorderlinePointOutsideOfNormalCovering(minLevels,maxCellValues,borderlineOuterPoint);





            // there can be several coordinates fields
            // there can be polygon or multipolygon types

//            S2Point someLAX_dude_s2Point = s2PointFromGPSCoordinates(33.943623, -118.409002);
//            Gson gson = new GsonBuilder().create();
//            Map<String,List<List<List<Double>>>> coordinatesMap = gson.fromJson(someJason,Map.class);

//            System.out.println(coordinatesMap.get("coordinates"));
//            System.out.println(coordinatesMap.get("coordinates").getClass());
//            System.out.println(coordinatesMap.get("coordinates").get(0));
//            System.out.println(coordinatesMap.get("coordinates").get(0).getClass());
//            System.out.println(coordinatesMap.get("coordinates").get(0).get(0));
//            System.out.println(coordinatesMap.get("coordinates").get(0).get(0).getClass());
//            System.out.println(coordinatesMap.get("coordinates").get(0).get(0).get(0));

            // how to choose the right object (polygon, multipolygon, ...) given the geojson type ?
            // is there something that does that ? or should we read the geojson beforehand to decide how we're going
            // to deserialize it...

            ///
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
////                MultiPolygon littleMultiPolygon = objectMapper.readValue(someJason,MultiPolygon.class);
////                System.out.println(littleMultiPolygon.toString());
//                Polygon littlePoly = objectMapper.readValue(someJason,Polygon.class);
//                //System.out.println(littlePoly.toString());
//                System.out.println(littlePoly.getCoordinates());
//
//                //
//                System.out.println(littlePoly.getCoordinates().get(0));
//                System.out.println(littlePoly.getCoordinates().get(0).get(0));
//                System.out.println(littlePoly.getCoordinates().get(0).get(0).getClass());
//                System.out.println(littlePoly.getCoordinates().get(0).get(0).getLatitude());
//                System.out.println(littlePoly.getCoordinates().get(0).get(0).getLongitude());
//
//                System.out.println(littlePoly.getCoordinates().get(0).get(0).getLongitude());

//                List<S2Point> littleS2PointList = new ArrayList<S2Point>();
//                for (org.geojson.LngLatAlt littlePoint : littlePoly.getCoordinates().get(0)) {
//                    littleS2PointList.add(new S2Point(S2LatLng.fromDegrees(littlePoint.getLatitude(),littlePoint.getLongitude())));
//                }
//
//                S2Loop newS2Loop = new S2Loop(littleS2PointList);
//                S2Polygon newS2Polygon = new S2Polygon(newS2Loop);
//
//
//                S2RegionCoverer newCoverer = new S2RegionCoverer();
//                S2CellUnion newS2CellUnion = newCoverer.getCovering(newS2Polygon); //S2RegionCoverer.getCovering(newS2Polygon);
//                //System.out.println(newS2CellUnion.exactArea());
//                //newS2CellUnion.get
//                System.out.println(newS2CellUnion.cellIds());


                List<S2Point> innerPoints = new ArrayList<>();
                List<S2Point> outerPoints = new ArrayList<>();
                innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94364476321219,-118.41742515563963)));
                innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94343115082649,-118.40575218200685)));
                innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94554054964585,-118.4001624584198)));
                innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94554499979908,-118.39641809463501)));

                testInnerPointNormalCovering(innerPoints);

                outerPoints.add(new S2Point(S2LatLng.fromDegrees(33.96009130706897,-118.41347694396971)));
                outerPoints.add(new S2Point(S2LatLng.fromDegrees(33.95247360616282,-118.3890151977539)));

                testTrulyOuterPoint(outerPoints);

//                S2Point someSupposedlyInnerPoint = new S2Point(S2LatLng.fromDegrees(33.94364476321219,-118.41742515563963));
//                S2Point otherSupposedlyInnerPoint = new S2Point(S2LatLng.fromDegrees(33.94343115082649,-118.40575218200685));
//                S2Point otherOtherSupposedlyInnerPoint = new S2Point(S2LatLng.fromDegrees(33.94554054964585,-118.4001624584198));
//                S2Point borderlineInnerPoint = new S2Point(S2LatLng.fromDegrees(33.94554499979908,-118.39641809463501));

//                S2Point supposedlyOuterPoint = new S2Point(S2LatLng.fromDegrees(33.96009130706897,-118.41347694396971));
//                S2Point otherSupposedlyOuterPoint = new S2Point(S2LatLng.fromDegrees(33.95247360616282,-118.3890151977539));
//
//                S2Point borderlineOuterPoint = new S2Point(S2LatLng.fromDegrees(33.94582090884453,-118.39608550071718));

//                assertTrue(newS2CellUnion.contains(someSupposedlyInnerPoint));
//                assertTrue(newS2CellUnion.contains(otherSupposedlyInnerPoint));
//                assertTrue(newS2CellUnion.contains(otherOtherSupposedlyInnerPoint));
//                assertTrue(newS2CellUnion.contains(borderlineInnerPoint));
//
//                assertFalse(newS2CellUnion.contains(supposedlyOuterPoint));
//                assertFalse(newS2CellUnion.contains(otherSupposedlyOuterPoint));

                // find a scale from which the point is outside of the cell union.
//                System.out.println("borderline outer point");
//                System.out.println(newS2CellUnion.contains(borderlineOuterPoint));
//
//                System.out.println("test of outer points for various max cells values");
//
//                int[] maxCellValues = {1,1,4,8,32,128,512,1024};
//                int[] minLevels = {5,10,15,16,18,20,22,23};
//                boolean containment = true;
//                int ctr_ = 0;
//                List<S2RegionCoverer> regionCovererList = new ArrayList<>();
//                List<S2CellUnion> cellUnionList = new ArrayList<>();
//                for (int maxCellValue : maxCellValues) {
//                    regionCovererList.add(new S2RegionCoverer());
//                    regionCovererList.get(ctr_).setMaxCells(maxCellValue);
//                    regionCovererList.get(ctr_).setMinLevel(minLevels[ctr_]);
//                    cellUnionList.add(regionCovererList.get(ctr_).getCovering(newS2Polygon));
//                    System.out.println(cellUnionList.get(ctr_).contains(borderlineOuterPoint));
//                    containment = containment && cellUnionList.get(ctr_).contains(borderlineOuterPoint);
//                    ctr_++;
//                }
//                assertFalse(containment);
//            } catch (IOException e) {
//                e.printStackTrace();
        }
}
