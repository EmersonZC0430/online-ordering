package com.my.woelegobuy.utils;


/**
 * @author wu.haitao ,Created on {DATE}
 * Major Function：<b></b>
 * @author mender，Modified Date Modify Content:
 */
public class IDUtils {
    public static String getId(){
        return String.valueOf(SnowFlakeShortUrl.nextId());
    }

}
