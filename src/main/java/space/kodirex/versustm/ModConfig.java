package space.kodirex.versustm;

import net.minecraftforge.common.config.Config;

@Config(modid = "versustm", category = "general")
public class ModConfig {
    @Config.Comment({
            "Enable using uuids for player identification, more secure and doesn't allow players to change their name",
            "to reset their time..."
    })
    public static boolean uuid = false;

    @Config.Comment({
            "A comma seperated list of users that the mod versus time manager mod should handle."
    })
    public static String enforcedUsers = "";

    @Config.Comment({
            "The amount of time a player is allowed to spend per day, without any multipliers. (Measured in hours)"
    })
    public static Double timeLimit = 4.0;

    @Config.Comment({
            "The mode to control how time limits work, they are as follows (all rules stated are in respect to",
            "those users which are stated in the \"enforcedUsers\" config above): ",
            "\tmultiplier : The ratio of users online to allowed users is treated as a multiplier. IE: The more users",
            "online, the \"slower\" time passes.",
            "\tall-or-nothing : If all allowed users are online, then the time limit is disabled until at least on user",
            "is not present.",
            "\tbasic : All users allotted time decreases at a fixed rate, no matter what.",
            "\tdefault : Uses the default mode. (multiplier)"
    })
    public static String mode = "default";

    public static String[] getEnforcedUsers() {
        return enforcedUsers.split("[,]");
    }
}