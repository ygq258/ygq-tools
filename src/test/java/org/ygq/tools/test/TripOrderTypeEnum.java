package org.ygq.tools.test;


public enum TripOrderTypeEnum {


    ONLINE("ONLINE", "网络订单"),
    OFFLINE("OFFLINE","线下订单"),
    QUICK_PASS("QUICK_PASS","闪付订单"),
    FREE("FREE","免票订单"),
    BOOK("BOOK","预约订单"),
    OFFLINE_RECEIVE("OFFLINE_RECEIVE","接待订单"),
    ADDITIONAL("ADDITIONAL","增购订单");

    private String value;
    private String code;

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    TripOrderTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static TripOrderTypeEnum codeOf(String code) {
        for (TripOrderTypeEnum enumty : values()) {
            if (enumty.getCode().equals(code)) {
                return enumty;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
    
    public static boolean isValidName(String code) {
        for (TripOrderTypeEnum tripOrderTypeEnum : values()) {
            if (tripOrderTypeEnum.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
