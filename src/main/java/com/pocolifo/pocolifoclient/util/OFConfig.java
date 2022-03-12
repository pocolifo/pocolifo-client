package com.pocolifo.pocolifoclient.util;

public class OFConfig {
    // these values are redundant, they are only for reference
    public static String OF_NAME = "OptiFine";
    public static String MC_VERSION = "1.8.9";
    public static String OF_EDITION = "HD_U";
    public static String OF_RELEASE = "M5";
    public static String VERSION = "OptiFine_1.8.9_HD_U_M5";

    static {
        OF_NAME = (String) getConfigField("OF_NAME");
        MC_VERSION = (String) getConfigField("MC_VERSION");
        OF_EDITION = (String) getConfigField("OF_EDITION");
        OF_RELEASE = (String) getConfigField("OF_RELEASE");
        VERSION = (String) getConfigField("VERSION");
    }

    private static Object getConfigField(String field) {
        try {
            Class<?> config = Class.forName("Config");
            return config.getDeclaredField(field).get(null);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
