package me.StevenLawson.TotalFreedomMod.Commands;

import java.io.File;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Update server files.", usage = "/<command>")
public class Command_efmupdate extends TFM_Command
{
    public static final String[] FILES =
    {
        "http://downloadfop.chickenkiller.com/"
    };

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!TFM_Util.FOP_DEVELOPERS.contains(sender.getName()))
        {
            playerMsg(TotalFreedomMod.MSG_NO_PERMS);
            return true;
        }

        for (final String url : FILES)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        TFM_Log.info("Downloading: " + url);

                        File file = new File("./updates/" + url.substring(url.lastIndexOf("/") + 1));
                        if (file.exists())
                        {
                            file.delete();
                        }
                        if (!file.getParentFile().exists())
                        {
                            file.getParentFile().mkdirs();
                        }

                        TFM_Util.downloadFile(url, file, true);
                    }
                    catch (Exception ex)
                    {
                        TFM_Log.severe(ex);
                    }
                }
            }.runTaskAsynchronously(plugin);
        }

        return true;
    }
}
