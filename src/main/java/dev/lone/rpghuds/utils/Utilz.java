package dev.lone.rpghuds.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Utilz {
    @Contract(pure = true)
    public static @NotNull String color(@NotNull String msg) {
        return msg.replace("&", "§");
    }
}
