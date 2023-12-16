package org.METROH.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandList {
	private static final Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("ping", CommandTasks::pingResponse);
		// Aquí puedes registrar más comandos...
	}

	public static Map<String, Command> getMap(){
		return commands;
	}
	public static Command getCommand(String name) {

		return commands.get(name);
	}
}
