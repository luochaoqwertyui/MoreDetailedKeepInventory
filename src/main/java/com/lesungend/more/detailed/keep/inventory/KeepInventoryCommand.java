package com.lesungend.more.detailed.keep.inventory;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class KeepInventoryCommand {
	private static final SimpleCommandExceptionType LIST_EMPTY = new SimpleCommandExceptionType(
			Component.translatable("commands.keepInventory.list.empty")
	);

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		var root = Commands.literal("keepInventory")
				.requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER));

		root.then(Commands.literal("add")
				.then(Commands.argument("targets", EntityArgument.players())
						.executes(KeepInventoryCommand::executeAdd)));

		root.then(Commands.literal("remove")
				.then(Commands.argument("targets", EntityArgument.players())
						.executes(KeepInventoryCommand::executeRemove)));

		root.then(Commands.literal("list")
				.executes(KeepInventoryCommand::executeList));

		dispatcher.register(root);
	}

	private static int executeAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
		int count = 0;
		for (ServerPlayer player : players) {
			UUID uuid = player.getUUID();
			String name = player.getName().getString();
			if (KeepInventoryData.addPlayer(uuid, name)) {
				count++;
			}
		}
		final int added = count;
		context.getSource().sendSuccess(
				() -> Component.translatable("commands.keepInventory.add.success", added),
				true
		);
		return count;
	}

	private static int executeRemove(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
		int count = 0;
		for (ServerPlayer player : players) {
			UUID uuid = player.getUUID();
			String name = player.getName().getString();
			if (KeepInventoryData.removePlayer(uuid, name)) {
				count++;
			}
		}
		final int removed = count;
		context.getSource().sendSuccess(
				() -> Component.translatable("commands.keepInventory.remove.success", removed),
				true
		);
		return count;
	}

	private static int executeList(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		Set<UUID> players = KeepInventoryData.getPlayers();
		if (players.isEmpty()) {
			throw LIST_EMPTY.create();
		}

		context.getSource().sendSuccess(
				() -> Component.translatable("commands.keepInventory.list.header", players.size()),
				false
		);

		for (UUID uuid : players) {
			String name = KeepInventoryData.getPlayerName(uuid);
			if (name == null) {
				name = uuid.toString();
			}
			final String displayName = name;
			context.getSource().sendSuccess(
					() -> Component.literal("  - " + displayName),
					false
			);
		}

		return players.size();
	}
}