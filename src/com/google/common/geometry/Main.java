package com.google.common.geometry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import org.geojson.*;

import com.fasterxml.jackson.core.*;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;


public class Main {


    public static int DEFAULT_MIN_LEVEL = 1;
    public static int DEFAULT_MAXCELLS = 9999;
    public static int DEFAULT_MAX_LEVEL = 30;

    public static String centralParisJason="{\n" +
            "        \"type\": \"Polygon\",\n" +
            "        \"coordinates\": [\n" +
            "          [\n" +
            "            [\n" +
            "              2.339315414428711,\n" +
            "              48.85929404028653\n" +
            "            ],\n" +
            "            [\n" +
            "              2.3401737213134766,\n" +
            "              48.8608186775544\n" +
            "            ],\n" +
            "            [\n" +
            "              2.2841262817382812,\n" +
            "              48.87984450217651\n" +
            "            ],\n" +
            "            [\n" +
            "              2.2811222076416016,\n" +
            "              48.874989934765395\n" +
            "            ],\n" +
            "            [\n" +
            "              2.33682632446289,\n" +
            "              48.85663993129474\n" +
            "            ],\n" +
            "            [\n" +
            "              2.339315414428711,\n" +
            "              48.85929404028653\n" +
            "            ]\n" +
            "          ]\n" +
            "        ]\n" +
            "      }";

    public static String someJason = "{\"type\": \"Polygon\", \"coordinates\": [[[-118.4375838, 33.949583], [-118.4370865, 33.9488622], [-118.4366407, 33.9481529], [-118.4364277, 33.9476954], [-118.4362923, 33.9472674], [-118.4362053, 33.9469334], [-118.4361629, 33.9466421], [-118.4361032, 33.9460338]," +
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


//    public static String menterodaGeoJson= "{\"type\":\"GeometryCollection\",\"geometries\":["+
//            "{\"type\":\"MultiPolygon\",\"coordinates\":[[[[10.4629888,51.314301700000001],"+
//                "[10.464289600000001,51.3144694],[10.4644394,51.314098199999997],[10.4647465,51.314161599999998],"+
//                "[10.4684305,51.314920800000003],[10.469088599999999,51.315021100000003],[10.469671699999999,51.315067999999997],"+
//                "[10.4705277,51.315074600000003],[10.473946400000001,51.3149576],[10.4775683,51.317565799999997],[10.4796815,51.3178634],"+
//                "[10.4797297,51.319237700000002],[10.4797618,51.319418200000001],[10.480623100000001,51.321925899999997],[10.481238400000001,51.3241759],"+
//                "[10.4818055,51.3241893],[10.482533099999999,51.324272899999997],[10.483046699999999,51.324393299999997],[10.483875899999999,51.3246641],"+
//                "[10.4850476,51.325048500000001],[10.485561199999999,51.325178899999997],[10.486005199999999,51.325255800000001],"+
//                "[10.491125200000001,51.326131699999998],[10.491922300000001,51.326258799999998],[10.4924091,51.326275500000001],"+
//                "[10.493019,51.326225299999997],[10.494517,51.325941200000003],[10.494501,51.326456],[10.4973793,51.326730099999999],"+
//                "[10.4987756,51.326877199999998],[10.501769400000001,51.327262300000001],[10.506751100000001,51.327949500000003],"+
//                "[10.5063432,51.324813499999998],[10.5016853,51.321171200000002],[10.4989531,51.320892700000002],[10.4984254,51.316071299999997],"+
//                "[10.495620799999999,51.311227100000004],[10.498261299999999,51.310538700000002],[10.500207899999999,51.310093000000002],"+
//                "[10.501391999999999,51.309858900000002],[10.5049048,51.309202300000003],[10.5087431,51.308956799999997],[10.5184845,51.306857899999997],"+
//                "[10.521026300000001,51.305727099999999],[10.5251167,51.305002799999997],[10.528166799999999,51.305379799999997],"+
//                "[10.529573600000001,51.306857899999997],[10.5307677,51.307138799999997],[10.532564600000001,51.309348499999999],"+
//                "[10.5335459,51.309215399999999],[10.5357802,51.311728000000002],[10.5433582,51.313013900000001],[10.543381800000001,51.314720800000003],"+
//                "[10.545521600000001,51.314979399999999],[10.5461837,51.313073000000003],[10.5523925,51.314174600000001],[10.552910799999999,51.316024300000002],"+
//                "[10.5539641,51.3161706],[10.5544657,51.318281399999996],[10.558077000000001,51.317915599999999],[10.5584615,51.319409899999997]," +
//            "[10.5594479,51.319242699999997],[10.561407300000001,51.325712500000002],[10.5680158,51.326081899999998],[10.5695172,51.323577499999999]," +
//            "[10.572910200000001,51.3240725],[10.5747781,51.323355800000002],[10.5764213,51.323156400000002],[10.578904,51.3222624],[10.5821314,51.322240299999997],[10.586612000000001,51.321109900000003]" +
//            ",[10.588113399999999,51.320858700000002],[10.587486800000001,51.320371000000002],[10.5940481,51.318228300000001],[10.6037658,51.319986800000002],[10.604829799999999,51.3187529],[10.6097242,51.318257899999999]," +
//            "[10.613791000000001,51.316004300000003],[10.6133299,51.314467299999997],[10.6126679,51.313344100000002],[10.6145949,51.312671700000003]," +
//            "[10.6242181,51.306390100000002],[10.622681200000001,51.303256300000001],[10.625636699999999,51.302265900000002],[10.6252111,51.301415900000002]," +
//            "[10.628805,51.300913299999998],[10.6289587,51.2998932],[10.631382199999999,51.299242700000001],[10.631772399999999,51.295923700000003]," +
//            "[10.632623600000001,51.2959459],[10.634160400000001,51.289580700000002],[10.6405917,51.290564000000003],[10.641235500000001,51.289902099999999]," +
//            "[10.643567600000001,51.286890499999998],[10.6455404,51.286033199999999],[10.631259,51.286053799999998],[10.6208922,51.2866778]," +
//            "[10.620841199999999,51.2849735],[10.618902,51.285228799999999],[10.6191266,51.286799100000003],[10.6180039,51.286881999999999]," +
//            "[10.6176263,51.284130900000001],[10.610369800000001,51.284239399999997],[10.6026133,51.2865757],[10.603440000000001,51.288330999999999]," +
//            "[10.6003068,51.288860700000001],[10.598602400000001,51.285273500000002],[10.596030499999999,51.286103300000001],[10.596254999999999,51.286990600000003]," +
//            "[10.5937137,51.287379899999998],[10.593223800000001,51.286773500000002],[10.591509200000001,51.288330999999999],[10.588406600000001,51.288209700000003],[10.587324799999999,51.288809700000002],[10.583456699999999,51.289282],[10.5827627,51.287092700000002],[10.585651,51.286958599999998],[10.585538700000001,51.285094800000003],[10.581099099999999,51.285605400000001],[10.5810073,51.284686200000003],[10.5790784,51.284813900000003],[10.574301999999999,51.285560699999998],[10.5741285,51.286365000000004],[10.572490800000001,51.289533900000002],[10.5675618,51.2893951],[10.566493700000001,51.291055900000003],[10.5669989,51.292387300000001]," +
//            "[10.567251499999999,51.2946527],[10.5674607,51.296602200000002],[10.568334,51.297739300000003],[10.568168,51.299584899999999]," +
//            "[10.5634266,51.301019699999998],[10.5631784,51.301084099999997],[10.558259400000001,51.302359799999998],[10.553698499999999,51.297906300000001]," +
//            "[10.551394999999999,51.2906513],[10.5502273,51.291692300000001],[10.549534400000001,51.293772699999998],[10.548407600000001,51.300273900000001]," +
//            "[10.545822100000001,51.300106],[10.5415338,51.301563199999997],[10.532314599999999,51.300167000000002],[10.517692800000001,51.3031121]," +
//            "[10.5153838,51.302043900000001],[10.5143161,51.301003700000003],[10.5118867,51.298637200000002],[10.495272,51.303218800000003]," +
//            "[10.4915269,51.302146899999997],[10.489573,51.300642799999999],[10.488319300000001,51.300135500000003],[10.4872332,51.300571400000003]," +
//            "[10.486113599999999,51.3005943],[10.4838877,51.300647599999998],[10.479439899999999,51.300754499999996],[10.4762413,51.298248200000003]," +
//            "[10.465606899999999,51.298293999999999],[10.4656249,51.300090699999998],[10.4636212,51.300647599999998],[10.4625217,51.305511600000003]," +
//            "[10.4654075,51.307121500000001],[10.466018800000001,51.310718799999997]"+
//            ",[10.4629888,51.314301700000001]]]]}]}";


    public static String someGermanPlace = "{\"type\":\"GeometryCollection\",\"geometries\":[{\"type\":\"MultiPolygon\",\"coordinates\":" +
            "[[[[13.264234200000001,52.626864500000003],[13.264962300000001,52.626528399999998],[13.265819,52.6263048]," +
            "[13.267231499999999,52.626230800000002],[13.2677824,52.626025800000001],[13.2682866,52.626004299999998]," +
            "[13.269675899999999,52.626108600000002],[13.270391200000001,52.626138500000003],[13.2710101,52.626135499999997]," +
            "[13.2713845,52.6261352],[13.272038,52.626109599999999],[13.2725811,52.626027899999997],[13.2734542,52.625654500000003]," +
            "[13.273678200000001,52.625546399999998],[13.27392,52.625438899999999],[13.2743474,52.625290200000002],[13.2749389,52.625087100000002]," +
            "[13.2756326,52.624896800000002],[13.276161699999999,52.6248048],[13.277276199999999,52.624770300000002],                               " +
            "[13.278221800000001,52.624858000000003],[13.2791648,52.625001900000001],[13.280157000000001,52.625113599999999],                         " +
            "[13.281219200000001,52.625187500000003],[13.282076699999999,52.625417200000001],[13.2827924,52.625503399999999],                           " +
            "[13.283901500000001,52.625672899999998],[13.285897,52.625751999999999],[13.2863369,52.625853399999997],[13.287001800000001,                  " +
            "52.625899400000002],[13.2875517,52.625968999999998],[13.288385699999999,52.626133299999999],[13.2895787,52.626199800000002]," +
            "[13.290654099999999,52.626297399999999],[13.291100399999999,52.626382499999998],[13.292169299999999,52.626526800000001]," +
            "[13.2924927,52.626500999999998],[13.2935882,52.626608699999998],[13.2965245,52.626979300000002]," +
            "[13.2972296,52.627046800000002],[13.299093900000001,52.627092699999999],[13.300356499999999,52.627001399999997],[13.300951299999999,52.627026999999998]," +
            "" +
            "" +
            "" +
            "[13.264434899999999,52.628422100000002],[13.2644035,52.628174100000003],[13.264234200000001,52.626864500000003]]]]}]}";

        public static String someMultiPolygonJson = "{\n" +
                "  \"type\": \"MultiPolygon\",\n" +
                "  \"coordinates\": [\n" +
                "    [\n" +
                "      [\n" +
                "        [\n" +
                "          -47.900390625,\n" +
                "          -14.944784875088372\n" +
                "        ],\n" +
                "        [\n" +
                "          -51.591796875,\n" +
                "          -19.91138351415555\n" +
                "        ],\n" +
                "        [\n" +
                "          -41.11083984375,\n" +
                "          -21.309846141087192\n" +
                "        ],\n" +
                "        [\n" +
                "          -43.39599609375,\n" +
                "          -15.390135715305204\n" +
                "        ],\n" +
                "        [\n" +
                "          -47.900390625,\n" +
                "          -14.944784875088372\n" +
                "        ]\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          -46.6259765625,\n" +
                "          -17.14079039331664\n" +
                "        ],\n" +
                "        [\n" +
                "          -47.548828125,\n" +
                "          -16.804541076383455\n" +
                "        ],\n" +
                "        [\n" +
                "          -46.23046874999999,\n" +
                "          -16.699340234594537\n" +
                "        ],\n" +
                "        [\n" +
                "          -45.3515625,\n" +
                "          -19.31114335506464\n" +
                "        ],\n" +
                "        [\n" +
                "          -46.6259765625,\n" +
                "          -17.14079039331664\n" +
                "        ]\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          -44.40673828125,\n" +
                "          -18.375379094031825\n" +
                "        ],\n" +
                "        [\n" +
                "          -44.4287109375,\n" +
                "          -20.097206227083888\n" +
                "        ],\n" +
                "        [\n" +
                "          -42.9345703125,\n" +
                "          -18.979025953255267\n" +
                "        ],\n" +
                "        [\n" +
                "          -43.52783203125,\n" +
                "          -17.602139123350838\n" +
                "        ],\n" +
                "        [\n" +
                "          -44.40673828125,\n" +
                "          -18.375379094031825\n" +
                "        ]\n" +
                "      ]\n" +
                "    ]\n" +
                "  ]\n" +
                "}";

    public static String nettoSupermarket = "{"+
            "\"type\": \"FeatureCollection\","+
            "\"features\": ["+
    "{"+
        "\"type\": \"Feature\","+
            "\"properties\": {},"+
        "\"geometry\": {"+
        "\"type\": \"Polygon\","+
                "\"coordinates\": [ [[-0.6468672,45.7493155],[-0.6468939,45.7492196],[-0.646927,45.7491021],[-0.6465112,45.7490335],[-0.6464441,45.7493129],[-0.6468167,45.7493647],[-0.6468522,45.7493696],[-0.6468672,45.7493155]]] }}]}";

    //public void timePointInPolygonOperations(List<S2Cell> cellList,List<S2Point> pointList) {

    //public static void timeCellInCellUnionOperations() { }

    public static void timePointInPolygonOperations(List<S2Cell> cellList, S2Point point) {
        System.out.println("Time per loop (in ms.):");
        int listSize = cellList.size();
        long startTime = System.nanoTime();
        for (S2Cell cell : cellList) {
            cell.contains(point);
        }
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / (1000. * listSize));
    }

    public static void timeCellInCellOperations(List<S2Cell> cellList, S2Cell supposedlySmallerCell) {
        System.out.println("Time per loop (in ms.):");
        int listSize = cellList.size();
        long startTime = System.nanoTime();
        for (S2Cell cell : cellList) {
            cell.contains(supposedlySmallerCell);
        }
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / (1000. * listSize));
        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void timePointInCellUnionOperations(List<S2CellUnion> s2CellUnionList, S2Point s2Point) {
        System.out.println("Time per loop (in ms.):");
        int listSize = s2CellUnionList.size();
        long startTime = System.nanoTime();
        for (S2CellUnion cellUnion : s2CellUnionList) {
            cellUnion.contains(s2Point);
        }
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / (1000. * listSize));
    }

    public static void timePointInCellUnionAvg(S2CellUnion s2CellUnion, S2Point s2Point) {
        List<S2CellUnion> s2CellUnionList = Collections.nCopies(100000, s2CellUnion);
        timePointInCellUnionOperations(s2CellUnionList, s2Point);
    }

    // for now it does'nt take multi polygons as an input

    public static S2Point s2PointFromGPSCoordinates(double lat, double lng) {
        return new S2Point(S2LatLng.fromDegrees(lat, lng));
    }

    // returns an S2Polygon object from a geojson string
//    public static S2Polygon geojsonToS2Polygon(String someJson) {
//        Gson gson = new GsonBuilder().create();
//        ObjectMapper objectMapper = new ObjectMapper();
//        Polygon polygon = new Polygon();
//        try {
//            polygon = objectMapper.readValue(someJson, Polygon.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //assertThat(!polygon.getCoordinates().equals(Collections.emptyList()));
//        // is it a deep or shallow equal ?
//
//        List<S2Point> littleS2PointList = new ArrayList<>();
//        for (org.geojson.LngLatAlt littlePoint : polygon.getCoordinates().get(0)) {
//            littleS2PointList.add(new S2Point(S2LatLng.fromDegrees(littlePoint.getLatitude(), littlePoint.getLongitude())));
//        }
//        return new S2Polygon(new S2Loop(littleS2PointList));
//    }


    public static S2Polygon singlePolygonGeojsonToS2Polygon(String someJson) {
        Gson gson = new GsonBuilder().create();
        ObjectMapper objectMapper = new ObjectMapper();
        Polygon polygon = new Polygon();
        FeatureCollection featureCollection;

        try {
            featureCollection = objectMapper.readValue(someJson, FeatureCollection.class);
            //feature = featureCollection.getFeatures().get(0);
            polygon = (Polygon)(featureCollection.getFeatures().get(0).getGeometry());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //assertThat(!polygon.getCoordinates().equals(Collections.emptyList()));
        // is it a deep or shallow equal ?

        List<S2Point> littleS2PointList = new ArrayList<>();
        for (org.geojson.LngLatAlt littlePoint : polygon.getCoordinates().get(0)) {
            littleS2PointList.add(new S2Point(S2LatLng.fromDegrees(littlePoint.getLatitude(), littlePoint.getLongitude())));
        }
        return new S2Polygon(new S2Loop(littleS2PointList));
    }

//    public static S2Polygon multiPolygonGeoJsonToS2Polygon(String someJson) {
//
//    }

    public static S2CellUnion s2PolygonToS2CellUnion(S2Polygon s2Polygon) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        return newCoverer.getCovering(s2Polygon);
    }

    // returns  an S2CellUnion object from a geojson string

    public static S2CellUnion singlePolygonGeojsonToS2CellUnion(String someJson, int maxCells) { //geojsonToContainingS2CellUnion
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMaxCells(maxCells);
        //return newCoverer.getCovering(geojsonToS2Polygon(someJson));
        return newCoverer.getCovering(singlePolygonGeojsonToS2Polygon(someJson));
    }

    public static S2CellUnion singlePolygonGeojsonToS2CellUnion(String someJson) { //geojsonToContainingS2CellUnion
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        //return newCoverer.getCovering(geojsonToS2Polygon(someJson));
        return newCoverer.getCovering(singlePolygonGeojsonToS2Polygon(someJson));
    }


//    public static S2CellUnion geojsonToS2CellUnion(String someJson) { //geojsonToContainingS2CellUnion
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        //return newCoverer.getCovering(geojsonToS2Polygon(someJson));
//        return newCoverer.getCovering(geojsonToS2Polygon(someJson));
//    }

    // same with maxCells options
//    public static S2CellUnion geojsonToS2CellUnion(String someJson, int maxCells) {
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        newCoverer.setMaxCells(maxCells);
//        return newCoverer.getCovering(geojsonToS2Polygon(someJson));
//    }

    // same with minLevel and maxCells options
//    public static S2CellUnion geojsonToS2CellUnion(String someJson, int minLevel, int maxCells) {
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        newCoverer.setMinLevel(minLevel);
//        newCoverer.setMaxCells(maxCells);
//        return newCoverer.getCovering(geojsonToS2Polygon(someJson));
//    }

//    public static S2CellUnion geojsonToS2CellUnion(String someJson, int minLevel, int maxCells, int maxLevel) {
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        newCoverer.setMinLevel(minLevel);
//        newCoverer.setMaxLevel(maxLevel);
//        newCoverer.setMaxCells(maxCells);
//        return newCoverer.getCovering(geojsonToS2Polygon(someJson));
//    }

    // returns an S2CellUnion object covering the interior of the polygon defined in a geojson string
//    public static S2CellUnion geojsonToInteriorS2CellUnion(String someJson) {
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        return newCoverer.getInteriorCovering(geojsonToS2Polygon(someJson));
//    }

    public static S2CellUnion singlePolygonGeojsonToInteriorS2CellUnion(String someJson) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        return newCoverer.getInteriorCovering(singlePolygonGeojsonToS2Polygon(someJson));
    }

//    public static S2CellUnion geojsonToInteriorS2CellUnion(String someJson, int minLevel, int maxCells) {
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        newCoverer.setMinLevel(minLevel);
//        newCoverer.setMaxCells(maxCells);
//        return newCoverer.getInteriorCovering(geojsonToS2Polygon(someJson));
//    }

    public static S2CellUnion singlePolygonGeojsonToInteriorS2CellUnion(String someJson, int minLevel, int maxCells) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);
        newCoverer.setMaxCells(maxCells);
        return newCoverer.getInteriorCovering(singlePolygonGeojsonToS2Polygon(someJson));
    }

//    public static S2CellUnion geojsonToInteriorS2CellUnion(String someJson, int minLevel, int maxCells, int maxLevel) {
//        S2RegionCoverer newCoverer = new S2RegionCoverer();
//        newCoverer.setMinLevel(minLevel);
//        newCoverer.setMaxCells(maxCells);
//        newCoverer.setMaxLevel(maxLevel);
//        return newCoverer.getInteriorCovering(geojsonToS2Polygon(someJson));
//    }

    public static S2CellUnion singlePolygonGeojsonToInteriorS2CellUnion(String someJson, int minLevel, int maxCells, int maxLevel) {
        S2RegionCoverer newCoverer = new S2RegionCoverer();
        newCoverer.setMinLevel(minLevel);
        newCoverer.setMaxCells(maxCells);
        newCoverer.setMaxLevel(maxLevel);
        return newCoverer.getInteriorCovering(singlePolygonGeojsonToS2Polygon(someJson));
    }

//    public static void testInnerBorderlinePointInsideInteriorCovering(int[] minLevels, int[] maxCellValues, S2Point innerBorderlinePoint) {
//        boolean containment = false;
//        for (int i = 0; i < maxCellValues.length; i++) {
//            containment = containment || geojsonToInteriorS2CellUnion(someJason, minLevels[i], maxCellValues[i]).contains(innerBorderlinePoint);
//            if (containment) {
//                break;
//            }
//        }
//        assertTrue(containment);
//    }
//
//    public static void testThatOuterBorderlinePointOutsideNormalCovering(int[] minLevels, int[] maxCellValues, S2Point outerBorderlinePoint) {
//        boolean containment = true;
//        for (int i = 0; i < maxCellValues.length; i++) {
//            containment = containment && geojsonToS2CellUnion(someJason, minLevels[i], maxCellValues[i]).contains(outerBorderlinePoint);
//            if (!containment) {
//                break;
//            }
//        }
//        assertFalse(containment);
//    }
//
//    public static void testInnerPointNormalCovering(S2Point innerPoint) {
//        assertTrue(geojsonToS2CellUnion(someJason).contains(innerPoint));
//    }
//
//    public static void testInnerPointNormalCovering(List<S2Point> innerPoints) {
//        for (S2Point innerPoint : innerPoints) {
//            assertTrue(geojsonToS2CellUnion(someJason).contains(innerPoint));
//        }
//    }
//
//    public static void testTrulyOuterPoint(S2Point outerPoint) {
//        assertFalse(geojsonToS2CellUnion(someJason).contains(outerPoint));
//    }
//
//    public static void testTrulyOuterPoint(List<S2Point> outerPoints) { //if the point is too close, use the testThatOuterBorderlinePointOutsideOfNormalCovering function
//        for (S2Point outerPoint : outerPoints) {
//            assertFalse(geojsonToS2CellUnion(someJason).contains(outerPoint));
//        }
//    }
//
//    public static void testOuterPointInteriorCovering(S2Point outerPoint) {
//        assertFalse(geojsonToInteriorS2CellUnion(someJason).contains(outerPoint));
//    }
//
//    public static void testOuterPointInteriorCovering(List<S2Point> outerPoints) { //
//        for (S2Point outerPoint : outerPoints) {
//            assertFalse(geojsonToInteriorS2CellUnion(someJason).contains(outerPoint));
//        }
//    }
//
//    public static double[] getAreaRatiosIntCov(int[] maxCellValues, int[] minLevels, String someJson) {
//        double[] areaRatios = new double[maxCellValues.length];
//        for (int i = 0; i < maxCellValues.length; i++) {
//            areaRatios[i] = geojsonToInteriorS2CellUnion(someJson, minLevels[i], maxCellValues[i]).exactArea() / geojsonToS2Polygon(someJson).getArea();
//        }
//        return areaRatios;
//    }
//
//    public static double[] getAreaRatiosNormCov(int[] maxCellValues, int[] minLevels, String someJson) {
//        double[] areaRatios = new double[maxCellValues.length];
//        for (int i = 0; i < maxCellValues.length; i++) {
//            areaRatios[i] = geojsonToS2CellUnion(someJson, minLevels[i], maxCellValues[i]).exactArea() / geojsonToS2Polygon(someJson).getArea();
//        }
//        return areaRatios;
//    }
//
//    public static double[] getAreaRatiosNormCov(int[] maxCellValues, String someJson) {
//        double[] areaRatios = new double[maxCellValues.length];
//        for (int i = 0; i < maxCellValues.length; i++) {
//            areaRatios[i] = geojsonToS2CellUnion(someJson, maxCellValues[i]).exactArea() / geojsonToS2Polygon(someJson).getArea();
//        }
//        return areaRatios;
//    }

    public static double getAreaRatioNormCov(int maxCellValue, String someJson) {
        //return geojsonToS2CellUnion(someJson, maxCellValue).exactArea() / geojsonToS2Polygon(someJson).getArea();
        return singlePolygonGeojsonToS2CellUnion(someJson, maxCellValue).exactArea() / singlePolygonGeojsonToS2Polygon(someJson).getArea();
    }

    public static double getAreaRatioNormCovSinglePolygon(int maxCellValue, String someJson) {
        return singlePolygonGeojsonToS2CellUnion(someJson, maxCellValue).exactArea() / singlePolygonGeojsonToS2Polygon(someJson).getArea();
    }

//    public static void testContainments() {
//        int[] maxCellValues = {1, 1, 4, 8, 32, 128, 512};
//        int[] minLevels = {5, 10, 15, 16, 18, 20, 22};
//        S2Point borderlineOuterPoint = new S2Point(S2LatLng.fromDegrees(33.94582090884453, -118.39608550071718));
//        S2Point borderlineInnerPoint = new S2Point(S2LatLng.fromDegrees(33.94068751909308, -118.37578922510147));
//        S2Point slightlyLessBorderlineInnerPoint = new S2Point(S2LatLng.fromDegrees(33.940667492258996, -118.37584555149077));
//        S2Point notThatBorderlineInnerPoint = new S2Point(S2LatLng.fromDegrees(33.940634114191745, -118.37592333555222));
//        S2Point notReallyBorderlineInnerPoint = new S2Point(S2LatLng.fromDegrees(33.94044942198306, -118.37627738714218));
//        S2Point notBorderlineAtAllInnerPoint = new S2Point(S2LatLng.fromDegrees(33.93937241365318, -118.37807178497313));
//        testThatOuterBorderlinePointOutsideNormalCovering(minLevels, maxCellValues, borderlineOuterPoint);
//        List<S2Point> innerPoints = new ArrayList<>();
//        List<S2Point> outerPoints = new ArrayList<>();
//        innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94364476321219, -118.41742515563963)));
//        innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94343115082649, -118.40575218200685)));
//        innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94554054964585, -118.4001624584198)));
//        innerPoints.add(new S2Point(S2LatLng.fromDegrees(33.94554499979908, -118.39641809463501)));
//        testInnerPointNormalCovering(innerPoints);
//        outerPoints.add(new S2Point(S2LatLng.fromDegrees(33.96009130706897, -118.41347694396971)));
//        outerPoints.add(new S2Point(S2LatLng.fromDegrees(33.95247360616282, -118.3890151977539)));
//        testTrulyOuterPoint(outerPoints);
//        testOuterPointInteriorCovering(borderlineOuterPoint);
//        testOuterPointInteriorCovering(new S2Point(S2LatLng.fromDegrees(33.94407198637549, -118.38935852050781)));
//        testOuterPointInteriorCovering(new S2Point(S2LatLng.fromDegrees(33.94069641990671, -118.37576240301132)));
//        int[] maxIntCovCellValues = {5, 80, 320, 1280, 5120, 20480};
//        int[] minIntCovLevels = {12, 14, 15, 16, 17, 18};
//        testInnerBorderlinePointInsideInteriorCovering(minIntCovLevels, maxIntCovCellValues, borderlineInnerPoint);
//    }

//    public static double computeCellArea(S2Cell s2Cell) {
//        return computeTriangleArea(s2Cell.getVertex(0), s2Cell.getVertex(1), s2Cell.getVertex(2))
//                + computeTriangleArea(s2Cell.getVertex(0), s2Cell.getVertex(2), s2Cell.getVertex(3));
//    }

//    public static double computeTriangleArea(S2Point a, S2Point b, S2Point c) {
//        final double sa = b.angle(c);
//        final double sb = c.angle(a);
//        final double sc = a.angle(b);
//        final double s = 0.5 * (sa + sb + sc);
//        return 4 *
//                Math.atan(
//                        Math.sqrt(
//                                Math.max(0.0,
//                                        Math.tan(0.5 * s) * Math.tan((s - sa) * 0.5) * Math.tan((s - sb) * 0.5) * Math.tan((s - sc) * 0.5))));
//    }

//    public static void printAreaRatios() {
//        int[] maxIntCovCellValues = {5, 80, 320, 1280, 5120, 20480};
//        int[] minIntCovLevels = {12, 14, 15, 16, 17, 18};
//        double[] areaRatios = getAreaRatiosIntCov(maxIntCovCellValues, minIntCovLevels, someJason);
//        for (double ratio : areaRatios) {
//            System.out.println(ratio);
//        }
//        double[] areaRatiosNormCov = getAreaRatiosNormCov(maxIntCovCellValues, minIntCovLevels, someJason);
//        for (double ratio : areaRatiosNormCov) {
//            System.out.println(ratio);
//        }
//    }

//    public static void checkAreasComputations() {
//        int[] maxIntCovCellValues = {5, 80, 320, 1280, 5120, 20480};
//        int[] minIntCovLevels = {12, 14, 15, 16, 17, 18};
//        double summedArea = 0.;
//        double customArea = 0.;
//        for (int idx = 0; idx < geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().size(); idx++) {
//            summedArea += (new S2Cell(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(idx))).exactArea();
//            customArea += computeCellArea(new S2Cell(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(idx)));
//        }
//        assertEquals(summedArea, customArea, 0.0000000000000001);
//    }

//    public static void printSomeCellIDsIds() {
//        int[] maxIntCovCellValues = {5, 80, 320, 1280, 5120, 20480};
//        int[] minIntCovLevels = {12, 14, 15, 16, 17, 18};
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(1).id());
//
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).prev().prev());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).prev());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0));
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).next());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).next().next());
//
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(1).prev().prev());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(1).prev());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(1));
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(1).next());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(1).next().next());
//
//
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).prev().prev().id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).prev().id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).next().id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).next().next().id());
//
//        System.out.println("");
//
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent().parent().parent().id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent().parent().id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent().id());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).id());
//
//        System.out.println("");
//
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent().parent().parent());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent().parent());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent());
//        System.out.println(geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0));
//    }

//    public static void checkThatParentContainsChild() {
//        int[] maxIntCovCellValues = {5, 80, 320, 1280, 5120, 20480};
//        int[] minIntCovLevels = {12, 14, 15, 16, 17, 18};
//        S2CellId parentId = geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent(); //new S2CellId()
//        S2CellId childId = geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0);
//        assertTrue(parentId.contains(childId));
//    }

//    public static void someRandomExampleStuff() {
//        int[] maxIntCovCellValues = {5, 80, 320, 1280, 5120, 20480};
//        int[] minIntCovLevels = {12, 14, 15, 16, 17, 18};
//        System.out.println("");
//        S2CellId parentId = geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0).parent(); //new S2CellId()
//        S2CellId childId = geojsonToInteriorS2CellUnion(someJason, minIntCovLevels[0], maxIntCovCellValues[0]).cellIds().get(0);
//        System.out.println(parentId.toToken());
//        System.out.println(childId.toToken());
//        System.out.println("");
//        System.out.println(parentId);
//        System.out.println(S2CellId.fromToken(parentId.toToken()));
//        System.out.println(new S2CellId(parentId.id()));
//        //System.out.println(parentId.id());
//        System.out.println("");
//        System.out.println(childId);
//        System.out.println(S2CellId.fromToken(childId.toToken()));
//        System.out.println(childId.id());
//    }

//    public static void somePerformanceTests() {
//        S2Cell cell = new S2Cell(S2CellId.fromFacePosLevel(0, 0, 0));
//        S2Cell cell2 = new S2Cell(S2CellId.fromFacePosLevel(3, 10, 5));
//        S2Cell cell3 = new S2Cell(S2CellId.fromFacePosLevel(5, 8, 10));
//        List<S2Cell> cellList = Collections.nCopies(100000, cell);
//        //cellList.addAll(Collections.nCopies(10000,cell2));
//        timePointInPolygonOperations(cellList, S2.origin());
//        List<S2Cell> cellList2 = Collections.nCopies(100000, cell2);
//        timePointInPolygonOperations(cellList2, S2.origin());
//        List<S2Cell> cellList3 = Collections.nCopies(100000, cell3);
//        timePointInPolygonOperations(cellList3, S2.origin());
//
//        timePointInPolygonOperations(cellList3, S2.origin().ortho());
//
//        List<S2Cell> newList = ListUtils.union(cellList, cellList2);
//        timePointInPolygonOperations(newList, S2.origin());
//        S2CellId bigCell = S2CellId.fromFacePosLevel(3, 0, 0);
//        S2CellId smallCell = S2CellId.fromFacePosLevel(3, 0, 5);
//        S2CellId otherSmallCell = S2CellId.fromFacePosLevel(3, 1, 5);
//        S2CellId otherOtherSmallCell = S2CellId.fromFacePosLevel(3, 999000000, 1);
//
//        timeCellInCellOperations(List.of(new S2Cell(bigCell)), new S2Cell(otherOtherSmallCell));
//        timeCellInCellOperations(List.of(new S2Cell(bigCell)), new S2Cell(otherSmallCell));
//
//        S2RegionCoverer s2RegionCoverer = new S2RegionCoverer();
//        S2Loop s2Loop = new S2Loop(List.of(S2.origin().ortho(), S2.origin(), new S2Point(1, 1, 0), new S2Point(4, 5, 0)));
//        S2Polygon s2Polygon = new S2Polygon(s2Loop);
//
//        // by default min and max levels are 0 and 30, change that for that case
//        s2RegionCoverer.setMinLevel(3);
//        s2RegionCoverer.setMaxLevel(5);
//        System.out.println("Min and Max levels:");
//        System.out.println(s2RegionCoverer.minLevel());
//        System.out.println(s2RegionCoverer.maxLevel());
//
//        S2CellUnion s2CellUnion = s2RegionCoverer.getInteriorCovering(s2Polygon);
//
//        System.out.println("Operation with 8 cells");
//        timePointInCellUnionAvg(s2CellUnion, new S2Point(1, 1, 0));
//
//        // -- check the same operation with more cells --
//        s2RegionCoverer.setMaxCells(30);
//        S2CellUnion widerS2CellUnion = s2RegionCoverer.getInteriorCovering(s2Polygon);
//        System.out.println("Operation with 30 cells");
//        timePointInCellUnionAvg(widerS2CellUnion, new S2Point(1, 1, 0));
//    }

//    public static void testVaryingNumberOfCells() { // varying the number of cells - the levels being fixed
//        int level = 20;
//        int maxCells = 10000;
//        S2Point testPoint = new S2Point(S2LatLng.fromDegrees(33.94554499979908, -118.39641809463501));
//        S2CellUnion s2CellUnion = geojsonToS2CellUnion(someJason, level, maxCells, level);
//        S2CellUnion s2CUSample = new S2CellUnion();
//        timePointInCellUnionAvg(s2CUSample, testPoint);
//        System.out.println(s2CUSample.contains(testPoint));
//        System.out.println(s2CellUnion.contains(testPoint));
//        //System.out.println(s2CellUnion.cellIds().size());
//        for (int i = 0; i < 115; i++) {
//            System.out.println("Number of cells:");
//            System.out.println(20 * i + 1);
//            s2CUSample.initFromCellIds(new ArrayList<>(s2CellUnion.cellIds().subList(1202 - 10 * i, 1203 + 10 * i)));
//            timePointInCellUnionAvg(s2CUSample, testPoint);
//        }
//    }

//    public static void someRandomChecks() {
//        System.out.println("TESTS");
//        System.out.println("-----------------------------");
//        /*
//        System.out.println(S2.origin().x);
//        System.out.println(S2.origin().y);
//        System.out.println(S2.origin().z);
//        */
//        System.out.println("Origin");
//        System.out.println(S2.origin());
//        System.out.println("Origin's orthogonal");
//        System.out.println(S2.ortho(S2.origin()));
//        S2LatLng s2LatLng = new S2LatLng(S2.origin());
//        System.out.println("Origin's GPS coordinates");
//        System.out.println(s2LatLng);
//
//        System.out.println("-----------------------");
//        int face = 0;
//        S2CellId id = S2CellId.fromFacePosLevel(face, 0, 0);
//        S2Cell cell = new S2Cell(id);
//        System.out.println("id token");
//        System.out.println(id.toToken().toString());
//        System.out.println("Rectangle bounds");
//        System.out.println(cell.getRectBound().toString());
//        System.out.println("Level");
//        System.out.println(cell.level());
//        System.out.println("id2");
//        S2CellId id2 = S2CellId.fromFacePosLevel(3, 10, 5);
//        S2Cell cell2 = new S2Cell(id2);
//
//        S2CellId id3 = S2CellId.fromFacePosLevel(5, 8, 10);
//        S2Cell cell3 = new S2Cell(id3);
//
//        System.out.println(id2);
//        System.out.println("id2 token");
//        System.out.println(id2.toToken().toString());
//
//        System.out.println("----------------------------");
//        List<S2Cell> cellList = Collections.nCopies(100000, cell);
//        //cellList.addAll(Collections.nCopies(10000,cell2));
//        timePointInPolygonOperations(cellList, S2.origin());
//        List<S2Cell> cellList2 = Collections.nCopies(100000, cell2);
//        timePointInPolygonOperations(cellList2, S2.origin());
//        List<S2Cell> cellList3 = Collections.nCopies(100000, cell3);
//        timePointInPolygonOperations(cellList3, S2.origin());
//
//        timePointInPolygonOperations(cellList3, S2.origin().ortho());
//
//        List<S2Cell> newList = ListUtils.union(cellList, cellList2);
//        timePointInPolygonOperations(newList, S2.origin());
//
//        S2CellId bigCell = S2CellId.fromFacePosLevel(3, 0, 0);
//        S2CellId smallCell = S2CellId.fromFacePosLevel(3, 0, 5);
//
//        System.out.println("---------------------------");
//
//        if (bigCell.contains(smallCell)) {
//            System.out.println("the big cell contains the small one");
//        }
//
//        S2CellId otherSmallCell = S2CellId.fromFacePosLevel(3, 1, 5);
//        System.out.println("---------------------------");
//
//        if (bigCell.contains(otherSmallCell)) {
//            System.out.println("the big cell contains the small one");
//        } else {
//            System.out.println("doesnt contain");
//        }
//
//
//        S2CellId otherOtherSmallCell = S2CellId.fromFacePosLevel(3, 999000000, 1);
//        System.out.println("----------------------------------------");
//
//        if (bigCell.contains(otherOtherSmallCell)) {
//            System.out.println("the big cell contains the small one");
//        } else {
//            System.out.println("doesnt contain");
//        }
//
//        System.out.println("-------------------------");
//        timeCellInCellOperations(List.of(new S2Cell(bigCell)), new S2Cell(otherOtherSmallCell));
//
//        System.out.println("-------------------------");
//        timeCellInCellOperations(List.of(new S2Cell(bigCell)), new S2Cell(otherSmallCell));
//
//
//        System.out.println(otherOtherSmallCell.face());
//        System.out.println(smallCell.face());
//
//
////            public S2CellUnion getInteriorCovering(S2Region region) {
////                S2CellUnion covering = new S2CellUnion();
////                getInteriorCovering(region, covering);
////                return covering;
////            }
//
////region, point, level, array list S2CellId;
//        //S2RegionCoverer.getSimpleCovering();
//
//        S2RegionCoverer s2RegionCoverer = new S2RegionCoverer();
//        List<S2Point> s2Points = new ArrayList<>();
//        //s2Points =
//        //s2Points =
//
//        s2Points.add(S2.origin().ortho());
//        s2Points.add(S2.origin());
//        s2Points.add(new S2Point(1, 1, 0));
//        s2Points.add(new S2Point(4, 5, 0));
//        //List<S2Point> vertices;
//        S2Loop s2Loop = new S2Loop(s2Points);
//        System.out.println(s2Loop.numVertices());
//        S2Polygon s2Polygon = new S2Polygon(s2Loop);
//
//        System.out.println("Loop rect. bound");
//        System.out.println(s2Loop.getRectBound());
//
//
//        System.out.println("--------------------------------");
//        System.out.println("Interior covering");
//        //S2CellUnion s2CellUnion = s2RegionCoverer.getInteriorCovering(new S2Polygon());
//
//
//        // by default min and max levels are 0 and 30, change that for that case
//        s2RegionCoverer.setMinLevel(3);
//        s2RegionCoverer.setMaxLevel(5);
//        System.out.println("Min and Max levels:");
//        System.out.println(s2RegionCoverer.minLevel());
//        System.out.println(s2RegionCoverer.maxLevel());
//
//        //maxCells is the maximum number of cells used
//
//        // how to get the actual number of cells ?
//
//        //s2RegionCoverer.maxCells();
//        S2CellUnion s2CellUnion = s2RegionCoverer.getInteriorCovering(s2Polygon);
//
//
//        //s2CellUnion.get
//
//        //how to get one cell out of the cell union
//
//
//        System.out.println("---------------------------");
//        System.out.println("cell union size");
//        System.out.println(s2CellUnion.size());
//        //System.out.println(s2CellUnion.cellId(0));
//
//
//        System.out.println("-----------------------");
//        System.out.println("S2Point hashCode");
//        System.out.println((new S2Point(1, 1, 1)).hashCode());
//        //S2Point.
//
//        System.out.println("-------------");
//        System.out.println("S2CellUnion hashCode");
//        System.out.println(s2CellUnion.hashCode());
//
//        System.out.println("---------");
//        System.out.println("get the hashCode of an inner S2Cell");
//        System.out.println(s2CellUnion.cellId(0).hashCode());
//
//        S2Cell newCell = new S2Cell(s2CellUnion.cellId(0));
//        System.out.println(newCell.hashCode());
//
//        System.out.println("-----------------");
//        System.out.println("cell ids");
//        System.out.println(s2CellUnion.cellIds());
//        System.out.println("inner cell id");
//        System.out.println(s2CellUnion.cellId(0));
//
//        System.out.println("---------------------");
//        System.out.println("S2 cells area error ratio:");
//        System.out.println((s2CellUnion.exactArea() - s2Loop.getArea()) / s2Loop.getArea());
//        System.out.println("Polygon area:");
//        System.out.println(s2Loop.getArea());
//        System.out.println("S2 cell union area:");
//        System.out.println(s2CellUnion.averageBasedArea());
//        System.out.println(s2CellUnion.approxArea());
//        System.out.println(s2CellUnion.exactArea());
//
//
//        System.out.println("----------------------");
//        System.out.println("Region covering");
//        System.out.println(s2CellUnion.contains(S2.origin()));
//        System.out.println(s2CellUnion.contains(S2.origin().ortho()));
//        System.out.println(s2CellUnion.contains(new S2Point(1, 1, 0)));
//
//        //chercher un point plus intérieur qui soit pris en compte
//        // par exemple un point dans la rect. bound.
//
//        // how to create a point from GPS coordinates...
//
//        // what is the polygon builder thing ?
//
//        System.out.println(s2Polygon.getRectBound());
//
////            long startTime = System.nanoTime();
////            s2CellUnion.contains(new S2Point(1,1,0));
////            long endTime = System.nanoTime();
////            System.out.println((endTime-startTime)/1000.);
//        System.out.println("Operation with 8 cells");
//        timePointInCellUnionAvg(s2CellUnion, new S2Point(1, 1, 0));
//
//
//        // -- check the same operation with more cells --
//        s2RegionCoverer.setMaxCells(30);
//        S2CellUnion widerS2CellUnion = s2RegionCoverer.getInteriorCovering(s2Polygon);
//        System.out.println("Operation with 30 cells");
//        timePointInCellUnionAvg(widerS2CellUnion, new S2Point(1, 1, 0));
//
//
//        System.out.println("---------------------");
//        System.out.println("(30 cells) S2 cells area error ratio:");
//        System.out.println((widerS2CellUnion.exactArea() - s2Loop.getArea()) / s2Loop.getArea());
//
//
//        System.out.println("---------------------------------");
//        System.out.println("Point");
//        S2Point s2PointFromLatLng = new S2Point(S2LatLng.fromRadians(0.0, 0.4));
//        System.out.println(s2PointFromLatLng);
//
//
//        S2Point otherS2PointFromLatLng = new S2Point(S2LatLng.fromRadians(0.3, 0.2));
//        System.out.println(otherS2PointFromLatLng);
////            System.out.println();
////            System.out.println("-----------------");
////            System.out.println("contained cells ids");
////            System.out.println(bigCell.id());
////            System.out.println(smallCell.id());
////            System.out.println(otherSmallCell.id());
////            System.out.println(bigCell.hashCode());
////            System.out.println(smallCell.hashCode());
////            System.out.println(bigCell.toLatLng());
//        // Polygon to S2Cell
//        // S2RegionCoverer ?
//
//
//        // check that S2Cell contains a smaller S2Cell
//
//
//        //ListUtils
////        S2CellId id = S2CellId.fromFacePosLevel(face, 0, 0);
////        S2Cell cell = new S2Cell(id);
//
//
//// if (cell.contains(S2.origin())) {
////     System.out.println("cell contains the origin");
//// }
//// else {
////     System.out.println("doesnt contain");
//// }
//    }

//    public static int getMaxCellValue(String someJson) {
//        double maxAreaRatio = 1.5;
//        int initialStep = 5;
//        int maxCellValue = 1;
//        while (getAreaRatioNormCov(maxCellValue, someJson) > 1.5) {
//            maxCellValue += 10;
//        }
//        return maxCellValue;
//    }

//    public static void testsAndS2CellUnionSelection() {
//        int[] maxCellValues = {1, 10, 200, 500, 1024, 3000};
//        for (int maxCellVal : maxCellValues) {
//            System.out.println(getAreaRatioNormCov(maxCellVal, someJason));
//
//        }
//        System.out.println();
//
//        int maxCellValue = getMaxCellValue(someJason);
//        System.out.println(maxCellValue);
//        System.out.println(getAreaRatioNormCov(maxCellValue, someJason));
//
//        S2CellUnion laAirport = geojsonToS2CellUnion(someJason, maxCellValue);
//        System.out.println("LA airport cells ids");
//        for (S2CellId cellId : laAirport.cellIds()) {
//            System.out.println(cellId.id());
////            System.out.println(cellId.pos());
////            System.out.println(cellId.hashCode());
////            System.out.println("");
////            //System.out.println(cellId);
////            System.out.println();
////            S2CellId possibleChild = new S2CellId(-9168571479678255104L);
//////            System.out.println("contains possible child ?");
////            if(cellId.contains(possibleChild)) {
////                System.out.println(cellId.id());
////                System.out.println(possibleChild.id());
////                System.out.println(cellId.childBegin().id());
////                System.out.println(cellId.contains(cellId.childBegin()));
////
////                S2CellId other = cellId.childBegin();
////                boolean contains = other.greaterOrEquals(cellId.rangeMin()) && other.lessOrEquals(cellId.rangeMax());
////                System.out.println(contains);
////                //return other.greaterOrEquals(rangeMin()) && other.lessOrEquals(rangeMax());
//
//
//            //System.out.println(new S2CellId(-9168571479678255104L));
//
//
////            System.out.println("next cell id:");
////            System.out.println(cellId.next());
//        }
//
//        System.out.println("");
//        System.out.println("refined airport coverage");
//        System.out.println("");
//
//        S2CellUnion refinedLaAirportCoverage = geojsonToS2CellUnion(someJason, maxCellValue + 50);
//        for (S2CellId cellId : refinedLaAirportCoverage.cellIds()) {
//            System.out.println(cellId.id());
//        }
//
//
//        double latDeg = 33.942647900826174;
//        double lngDeg = -118.41356277465819;
//        S2Point someTraveller = new S2Point(S2LatLng.fromDegrees(latDeg, lngDeg));
//
//        System.out.println("time for 1000 point in L.A. airport polygon operations");
//        long startTime = System.nanoTime();
//        for (int i = 0; i < 1000; i++) {
//            refinedLaAirportCoverage.contains(someTraveller);
//        }
//        System.out.println((double) (System.nanoTime() - startTime) / (1000000000));
//
//
//        System.out.println("");
//        System.out.println("area ratio");
//        System.out.println(getAreaRatioNormCov(81, someJason));
//
//
//        System.out.println("");
//        System.out.println(refinedLaAirportCoverage.contains(someTraveller));
//
//
////        S2Point someGermanUser = new S2Point(S2LatLng.fromDegrees(51.311086449004534,10.570049285888672));
////
////        S2CellUnion menteroda = geojsonToS2CellUnion(someGermanPlace,1000);
////
////
////        startTime = System.nanoTime();
////        for(int i=0;i<1000;i++) {
////            menteroda.contains(someGermanUser);
////        }
////        System.out.println((double)(System.nanoTime()-startTime)/(1000000000));
////
////        System.out.println("time for 1000 point in L.A. airport polygon operations");
//    }
//
//
//    public static void getParisJsonCellIds() {
//
//        int maxCellValue = getMaxCellValue(centralParisJason);
//        System.out.println(maxCellValue);
//        System.out.println(getAreaRatioNormCov(maxCellValue, centralParisJason));
//
//
//        System.out.println("");
//        System.out.println("paris coverage");
//        System.out.println("");
//
//        S2CellUnion parisCoverage = geojsonToS2CellUnion(centralParisJason, maxCellValue + 50);
//        for (S2CellId cellId : parisCoverage.cellIds()) {
//            System.out.println(cellId.id());
//            System.out.println(cellId.level());
//        }
//    }
//
//    public static void getNettoJsonCellIds() {
//
//        //geojsonToS2Polygon(nettoSupermarket);
//        singlePolygonGeojsonToS2Polygon(nettoSupermarket);
//
////        int maxCellValue = getMaxCellValue(nettoSupermarket);
////        System.out.println(maxCellValue);
////        System.out.println(getAreaRatioNormCov(maxCellValue, nettoSupermarket));
////
////        System.out.println("");
////        System.out.println("Netto coverage");
////        System.out.println("");
////
////        S2CellUnion parisCoverage = geojsonToS2CellUnion(nettoSupermarket, maxCellValue + 50);
////        for (S2CellId cellId : parisCoverage.cellIds()) {
////            System.out.println(cellId.id());
////            System.out.println(cellId.level());
//        }


//    public static GeoJsonObject readGeojson(String someJson) {
//        Gson gson = new GsonBuilder().create();
//        ObjectMapper objectMapper = new ObjectMapper();
//        GeoJsonObject geoJsonObject;
//        try {
//            geoJsonObject = objectMapper.readValue(someJson, GeoJsonObject.class);
//            // que faire selon le type...
////            if(geoJsonObject instanceof Polygon) {
////                //geoJsonObject = (Polygon)(geoJsonObject);
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
////        }
//            return geoJsonObject;
//        }
//    }


    public static S2CellUnion geojsonPolygonToS2CellUnion(Polygon polygon) {
        return geojsonPolygonToS2CellUnion(polygon, DEFAULT_MIN_LEVEL,DEFAULT_MAXCELLS,DEFAULT_MAX_LEVEL);
    }

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


    //TODO
    // TEST THE geojson ... ToS2CellUnion functions

    //rendre la fonction generique


    public static S2CellUnion geojsonMultiPolygonToS2CellUnion(MultiPolygon multiPolygon) {
        return geojsonMultiPolygonToS2CellUnion(multiPolygon, DEFAULT_MIN_LEVEL,DEFAULT_MAXCELLS,DEFAULT_MAX_LEVEL);
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
        return geojsonObjectToS2CellUnion(geoJsonObject, DEFAULT_MIN_LEVEL,DEFAULT_MAXCELLS,DEFAULT_MAX_LEVEL);
    }

    public static S2Cell geojsonPointToS2Cell(Point point) {
        return new S2Cell(new S2Point(S2LatLng.fromDegrees(point.getCoordinates().getLatitude(),point.getCoordinates().getLongitude())));
    }

    public static S2CellUnion geojsonPointToS2CellUnion(Point point) {
        S2CellUnion s2CellUnion = new S2CellUnion();
        List<S2CellId> s2CellIds = new ArrayList<>();
        s2CellIds.add(geojsonPointToS2Cell(point).cellId);
        //s2CellUnion.initRawCellIds(List.of(geojsonPointToS2Cell(point).cellId));
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


    //


    public static void main(String[] args) {
        //            someRandomChecks();
        //            testContainments();
        //            printAreaRatios();
        //            checkAreasComputations();

        //printSomeCellIDsIds();
        //checkThatParentContainsChild();

        //someRandomExampleStuff();
        //somePerformanceTests();
        // performance tests
        //testVaryingNumberOfCells();
        //testsAndS2CellUnionSelection();


        //getParisJsonCellIds();
        //getNettoJsonCellIds();




        S2CellUnion s2CellUnion = geojsonToS2CellUnion(someMultiPolygonJson);
        System.out.println(s2CellUnion.cellIds().size());


        S2CellUnion s2CellUnion_2 = geojsonToS2CellUnion(someJason);
        System.out.println(s2CellUnion_2.cellIds().size());


        // debug that
//        // deserialize multipolygon
//        String multipolygonString = "{ \"type\": \"MultiPolygon\"," +
//                "\"coordinates\": [" +
//                "[[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]]," +
//                "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]," +
//                "[[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]]}";
//
//
//        Gson gson = new GsonBuilder().create();
//        ObjectMapper objectMapper = new ObjectMapper();
//        //Polygon polygon = new Polygon();
//        MultiPolygon multiPolygon = new MultiPolygon();
//        try {
//            multiPolygon = objectMapper.readValue(multipolygonString, MultiPolygon.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //assertThat(!polygon.getCoordinates().equals(Collections.emptyList()));
//        // is it a deep or shallow equal ?
//
//        List<List<List<S2Point>>> multiPolygonCoordinates = new ArrayList<>();
//        List<List<S2Point>> subCoordinatesList = new ArrayList<>();
//        List<S2Point> coordinatesList = new ArrayList<>();
//        int polygonIndex = 0;
//        for (List<List<org.geojson.LngLatAlt>> someList : multiPolygon.getCoordinates()) {
//            subCoordinatesList = new ArrayList<>();
//            //multiPolygonCoordinates.add(new ArrayList<S2Point>());
//            System.out.println("New Polygon");
//            for (List<org.geojson.LngLatAlt> someSubList : someList) {
//                coordinatesList = new ArrayList<>();
//                //System.out.println("new sub polygon");
//                for (org.geojson.LngLatAlt someLngLat : someSubList) {
//                    //System.out.println(someLngLat.getLongitude());
//                    coordinatesList.add(new S2Point(S2LatLng.fromDegrees(someLngLat.getLatitude(), someLngLat.getLongitude())));
//                }
//                subCoordinatesList.add(coordinatesList);
//            }
//            multiPolygonCoordinates.add(subCoordinatesList);
//            //System.out.println(someList.getLongitude());
//            //polygonIndex++;
//        }
//
//        System.out.println("coordinates");
//        System.out.println(multiPolygonCoordinates.get(0).get(0));
//
//
//        // use the constructor with lists of loops
//
////        for (List<S2Point>) {
////
////        }
//
//        S2Loop firstLoop = new S2Loop(multiPolygonCoordinates.get(1).get(0));
//        S2Loop secondLoop = new S2Loop(multiPolygonCoordinates.get(1).get(1));
//        List<S2Loop> loopsList = List.of(firstLoop, secondLoop);
//
//        //loops.get(0);
//
//        System.out.println(loopsList.get(0).numVertices());
//
//        S2Polygon s2Polygon = new S2Polygon(loopsList);
//        System.out.println("does the perforated polygon shows holes");
//        System.out.println(s2Polygon.isPerforated());
//        System.out.println(s2Polygon.getArea());
//
//        //System.out.println(new S2CellUnion(s2Polygon));
//
////        System.out.println(s2PolygonToS2CellUnion(s2Polygon).cellIds().get(0));
////
////        for (S2CellId s2CellId : s2PolygonToS2CellUnion(s2Polygon).cellIds()) {
////            System.out.println(s2CellId.id());
////        }
////        S2Cell s2Cell = new S2Cell(s2PolygonToS2CellUnion(s2Polygon).cellIds().get(0));
////        System.out.println(s2Cell.getVertex(0).toDegreesString());
    }
}