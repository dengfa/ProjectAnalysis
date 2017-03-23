package com.vincent.projectanalysis.bean;


import com.vincent.projectanalysis.Framework.BaseObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/4/29.
 */
public class OnlineMapListInfo extends BaseObject {

    public List<OnlineMapInfo> mMapInfoList;

    @Override
    public void parse(JSONObject json) {
        super.parse(json);
        JSONArray items = json.optJSONArray("data");
        mMapInfoList = new ArrayList<OnlineMapInfo>();
        if (items != null) {
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.optJSONObject(i);
                if (item != null) {
                    mMapInfoList.add(new OnlineMapInfo(item));
                }
            }
        }
    }

    public static class OnlineMapInfo implements Serializable {

        public static final int STATUS_UNOPEN = 0;
        public static final int STATUS_OPEN = 1;

        public String mMapId;
        public String mMapName;
        public String mMapPlot;
        public String mMedalTitle;
        public String mMedalDesc;
        public int    mMinAge;
        public int    mMaxAge;
        public int    mLevelNum;
        public int    mMissionNum;
        public int    mSimilarNum;
        public int    mTreasureNum;
        public int    mFinishMission;
        public String mBackGround;
        public String mCartoonUrl;
        public int    mVisibleCartoonNum;
        public int    mStudentNum;
        public String mSubTitle;

        public int mStatus;

        public OnlineMapInfo(JSONObject json){
            this.mMapId = json.optString("mapID");
            this.mMapName = json.optString("mapName");
            this.mMapPlot = json.optString("mapPlot");
            this.mMedalTitle = json.optString("medalTitle");
            this.mMedalDesc = json.optString("medalDesc");
            this.mMinAge = json.optInt("minAge");
            this.mMaxAge = json.optInt("maxAge");
            this.mLevelNum = json.optInt("levelNums");
            this.mFinishMission = json.optInt("finishMissionNum");
            this.mMissionNum = json.optInt("totalMissionNum");
            this.mSimilarNum = json.optInt("problemNums");
            this.mTreasureNum = json.optInt("treasureNums");
            this.mBackGround = json.optString("backgroundUrl");
            this.mStatus = json.optInt("status");
            this.mCartoonUrl = json.optString("cartoonUrl");
            this.mVisibleCartoonNum = json.optInt("cartoonVisibleNum");
            this.mStudentNum = json.optInt("studentNum");
            this.mSubTitle = json.optString("subTitle");
        }
    }
}
