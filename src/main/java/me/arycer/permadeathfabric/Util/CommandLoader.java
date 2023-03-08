package me.arycer.permadeathfabric.Util;

import me.arycer.permadeathfabric.Commands.SetBanOnDeathCommand;
import me.arycer.permadeathfabric.Commands.GetDayCommand;
import me.arycer.permadeathfabric.Commands.ReloadConfigCommand;
import me.arycer.permadeathfabric.Commands.SetDayCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandLoader {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SetBanOnDeathCommand::register);
        CommandRegistrationCallback.EVENT.register(ReloadConfigCommand::register);
        CommandRegistrationCallback.EVENT.register(GetDayCommand::register);
        CommandRegistrationCallback.EVENT.register(SetDayCommand::register);
    }
}