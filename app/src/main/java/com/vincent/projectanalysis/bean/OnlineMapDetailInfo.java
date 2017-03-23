package com.vincent.projectanalysis.bean;


import com.vincent.projectanalysis.Framework.BaseObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzc on 16/5/3.
 */
public class OnlineMapDetailInfo extends BaseObject implements Serializable {

    public int mCurrentIndex = 1;
    public OnlineMapListInfo.OnlineMapInfo mMapInfo;
    public List<OnlineLevelInfo>           mLevelIfs;
    public List<OnlineBagInfo>             mBagIfs;
    public int mMapKeys = 0;

    @Override
    public void parse(JSONObject json) {
        super.parse(json);

        mLevelIfs = new ArrayList<OnlineLevelInfo>();
        mBagIfs = new ArrayList<OnlineBagInfo>();

        JSONObject data = json.optJSONObject("data");
        if (data != null) {
            this.mCurrentIndex = data.optInt("currentLevelIndex");
            this.mMapKeys = data.optInt("mapKeys");
            //解析地图信息
            JSONObject mapInfo = data.optJSONObject("mapInfo");
            mMapInfo = new OnlineMapListInfo.OnlineMapInfo(mapInfo);

            //初始化关卡信息
            JSONArray jsonItems = data.optJSONArray("levels");
            if (jsonItems != null) {
                for (int i = 0; i < jsonItems.length(); i++) {
                    JSONObject jsonItem = jsonItems.optJSONObject(i);
                    if (jsonItem != null) {
                        mLevelIfs.add(new OnlineLevelInfo(jsonItem));
                    }
                }
            }
            //初始化宝箱信息
            JSONArray bagItems = data.optJSONArray("treasures");
            if (bagItems != null) {
                for (int i = 0; i < bagItems.length(); i++) {
                    JSONObject bagItem = bagItems.optJSONObject(i);
                    if (bagItem != null) {
                        mBagIfs.add(new OnlineBagInfo(bagItem));
                    }
                }
            }
        }
    }

    public static class OnlineBagInfo implements Serializable {

        public static final int STATUS_LOCK = 0;
        public static final int STATUS_CLOSE = 1;
        public static final int STATUS_OPENED = 2;

        public String mBagId;
        public String mSectionId;
        public String mCardName;
        public int    mCardType;
        public int    mCartoonFrom;
        public int    mCartoonTo;
        public int    mStatus;
        public String mIndex;

        public OnlineBagInfo(JSONObject json){
            this.mBagId = json.optString("treasureID");
            this.mSectionId = json.optString("sectionID");
            this.mCardName = json.optString("cardName");
            this.mCardType = json.optInt("cardType");
            this.mCartoonFrom = json.optInt("cartoonFrom");
            this.mCartoonTo = json.optInt("cartoonTo");
            this.mIndex = json.optString("index");
            this.mStatus = json.optInt("status");
        }
    }

    public static class OnlineLevelInfo implements Serializable {

        public static final int STATUS_LOCK = 0;
        public static final int STATUS_DISABLE = 1;
        public static final int STATUS_OPENED = 2;

        public String  mLevelId;
        public String  mIndex;
        public String  mLevelName;
        public boolean mIsDemo;
        public int     mMissionNum;
        public int     mFinishNum;
        public int     mStatus;
        public String  mVideoUrl;
        public long    mVideoLength;
        public String  mFriendHeadPhoto;

        public OnlineLevelInfo(JSONObject json) {
            this.mIsDemo = json.optBoolean("isDemo", false);
            this.mIndex = json.optString("index");
            this.mStatus = json.optInt("status");
            this.mLevelName = json.optString("sectionName");
            this.mLevelId = json.optString("courseSectionID");
            this.mMissionNum = json.optInt("missionNum");
            this.mFinishNum = json.optInt("finishMission");
            this.mVideoUrl = json.optString("videos");
            this.mVideoLength = json.optLong("videoLength");
            this.mFriendHeadPhoto = json.optString("friendHeadPhoto");
        }
    }
}
