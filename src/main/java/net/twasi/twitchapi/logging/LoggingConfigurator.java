package net.twasi.twitchapi.logging;

import java.io.IOException;
import java.util.logging.*;

public class LoggingConfigurator {

    private static boolean isConfigured = false;

    public static void configure() {
        // Set up logging
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.INFO);
        try {
            FileHandler fh = new FileHandler("twasi-twitch-api.log");
            rootLogger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isConfigured = true;
    }

    public static Logger getLogger(String name) {
        if (!isConfigured) {
            configure();
        }

        return Logger.getLogger(name);
    }

}
