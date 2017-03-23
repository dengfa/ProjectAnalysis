package com.vincent.projectanalysis.bean;


import com.vincent.projectanalysis.Framework.BaseObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilongzhang on 16/6/12.
 */
public class OnlineMapBagDetailsInfo extends BaseObject {

    public int          mapCount;
    public List<SubMap> subMapList;


    @Override
    public void parse(JSONObject json) {
        super.parse(json);
        JSONArray jsonArray = json.optJSONArray("data");
        mapCount = jsonArray.length();
        subMapList = new ArrayList<SubMap>();
        for (int i = 0; i < jsonArray.length(); i++) {
            SubMap subMap = new SubMap();
            subMap.subBackgroundUrl = jsonArray.optJSONObject(i).optString("background");
            subMap.subTitle = jsonArray.optJSONObject(i).optString("name");
            subMap.type = jsonArray.optJSONObject(i).optString("type");
            subMap.id = jsonArray.optJSONObject(i).optString("id");
            subMap.desc = jsonArray.optJSONObject(i).optString("desc");
            subMap.nums = jsonArray.optJSONObject(i).optString("nums");
            subMapList.add(subMap);
        }
    }

    public class SubMap {
        public String type;
        public String id;
        public String desc;
        public String nums;
        public String subBackgroundUrl;
        public String subTitle;
    }


}
