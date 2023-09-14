package su.selezzz.selezzzapiforge.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.world.level.LevelAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.selezzz.selezzzapiforge.network.StorytoolsModVariables;

public class LoadTokenProcedure {
	public static final Logger LOGGER = LogManager.getLogger(LoadTokenProcedure.class);

	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity, LevelAccessor world) {
		if (entity == null)
			return;
		try {
			String token = StringArgumentType.getString(arguments, "token");
			String tokInfo = HttpRequest.send("http://selezzzdev.pythonanywhere.com/get2/" + token);
			String[] tokInfoPieces = tokInfo.split(";");
			if (!tokInfo.equals("NULL;false")) {
				if (Objects.equals(tokInfoPieces[1], "true")) {
					if (tokInfoPieces[0].equals(StringArgumentType.getString(arguments, "username"))) {
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(Component.literal("§6§l[StoryTools] §aТокен успешно подключен!"), false);
						//if (entity instanceof Player _player && !_player.level.isClientSide())
						//	_player.displayClientMessage(Component.literal("§6§l[StoryTools] §aDEBUG " + StorytoolsModVariables.MapVariables.get(world).isTokenActivated + StorytoolsModVariables.MapVariables.get(world).token), false);
						StorytoolsModVariables.MapVariables.get(world).isTokenActivated = true;
						StorytoolsModVariables.MapVariables.get(world).token = token;
						StorytoolsModVariables.MapVariables.get(world).syncData(world);
					} else {
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(Component.literal("§6§l[StoryTools] §4Введён неверный юзернэйм"), false);
					}
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal("§6§l[StoryTools] §4Срок действия токена истёк"), false);
				}
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal("§6§l[StoryTools] §4Токен не найден!"), false);

			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
