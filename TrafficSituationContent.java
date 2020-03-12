package com.example.trafficsituations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.client.model.TrafficSituationApiModel;

/**
 * Holds Traffic situation data.
 */
public class TrafficSituationContent {

    public static List<TrafficSituationApiModel> ITEMS = new ArrayList<TrafficSituationApiModel>();
    public static Map<String, TrafficSituationApiModel> ITEM_MAP = new HashMap<String, TrafficSituationApiModel>();

    public static void setTrafficSituations(List<TrafficSituationApiModel> trafficSituationApiModels) {

        if (trafficSituationApiModels == null) { return; }

        ITEMS = trafficSituationApiModels;
        ITEM_MAP = new HashMap<String, TrafficSituationApiModel>();

        for (TrafficSituationApiModel model : trafficSituationApiModels) {
            ITEM_MAP.put(model.getSituationNumber(), model);
        }
    }
}
