package org.nlpcn.commons.lang;

public class TestUtils {

    public static String mainResources(final String file) {
        //System.getenv().forEach((k, v) -> System.out.println(k));
        //System.getProperties().forEach((k,v) -> System.out.println(k));
        //System.out.println(System.getProperties().getProperty("user.dir"));
        return System.getProperties().getProperty("user.dir")
                + "/src/main/resources"
                + (file.startsWith("/") ? file : "/" + file);
    }

    public static String testResources(final String file) {
        return System.getProperties().getProperty("user.dir")
                + "/src/test/resources"
                + (file.startsWith("/") ? file : "/" + file);
    }
}
