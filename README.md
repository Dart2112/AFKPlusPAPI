# AFKPlus Placeholder API Add-on

This plugin adds Placeholder API support, you can edit some of the strings it will provide to PAPI in the config.yml it will generate

The placeholders it provides are "AFKPlus_Status", "AFKPlus_AFKTime", "AFKPlus_TotalTimeAFK" and "AFKPlus_PlayersAFK"

### Below is a short description of each placeholder:

Status outputs the players' current AFK status, the strings for true and false can be set in the config

AFKTime outputs the players' current time AFK, the string to be returned when the player isn't AFK can be set in the config, the number of time units to show can be set in the config. e.g. 3 time units might look like "1 hour 10 minutes and 4 seconds", whereas 2 would be "1 hour and 10 minutes"

TotalTimeAFK outputs the total time that the player has ever been AFK

PlayersAFK outputs the same result for all players, it shows the current count of AFK players online. The string returned when there aren't any AFK players can be set in the config
