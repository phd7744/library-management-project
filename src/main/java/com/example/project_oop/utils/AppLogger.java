package com.example.project_oop.utils;

import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class AppLogger {

    private static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());
    private static final String LOG_FILE = "logs/library-manager.log";
    private static boolean configured = false;

    private AppLogger() {
    }

    public static synchronized void configure() {
        if (configured) {
            return;
        }

        try {
            Path logPath = Path.of(LOG_FILE);
            Path parent = logPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            FileHandler fileHandler = new FileHandler(logPath.toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);

            Logger rootLogger = Logger.getLogger("");
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.INFO);

            configured = true;
            LOGGER.info("File logging configured at logs/library-manager.log");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot configure file logging", e);
        }
    }

    public static void logUserAction(String action) {
        LoginRole role = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
        String username = LoginSession.getCurrentUsername() != null
                ? LoginSession.getCurrentUsername()
                : role.getDefaultUsername();

        Logger.getLogger("com.example.project_oop.user.activity")
                .log(Level.INFO, "User action: role={0}, username={1}, action={2}",
                        new Object[]{role.getDisplayName(), username, action});
    }
}