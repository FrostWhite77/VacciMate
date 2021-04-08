package com.katcdavi.vaccimate.modules;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOModule {
    public static String readFileToString(InputStream stream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
