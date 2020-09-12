package net.saifs.skycadeapplication.command;

import net.saifs.skycadeapplication.utils.SAMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SACommandHandler implements CommandExecutor {
    public static Map<String, SACommand> commands = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!commands.containsKey(args[0])) {
            SAMethods.send(sender, "&cThat is not a valid sub-command! Please do &7/cms listcommands &cfor more help!");
            return true;
        }
        SACommand cmsCommand = commands.get(args[0]);
        if (cmsCommand.getNode() != null) {
            if (!sender.isOp() && !sender.hasPermission(cmsCommand.getNode())) {
                SAMethods.send(sender, "&cYou do not have permission to run that command!");
                return true;
            }
        }
        String[] cmsCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        cmsCommand.onCommand(sender, cmsCommandArgs);
        return true;
    }
}
