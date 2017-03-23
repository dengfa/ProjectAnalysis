package com.vincent.projectanalysis.mapScene.parser;

import com.vincent.projectanalysis.mapScene.base.CMap;

/**
 * Created by yangzc on 16/4/21.
 */
public interface MapParser {

    CMap parse(String xml, int screenWidth, int screenHeight);

}
