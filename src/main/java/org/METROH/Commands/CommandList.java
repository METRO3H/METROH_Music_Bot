package org.METROH.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandList {
	private static final Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("ping", CommandTasks::Ping_Response);
		commands.put("join", CommandTasks::Join_Channel);
		commands.put("pon", CommandTasks::Play_Music);
		commands.put("t", CommandTasks::Run_Test);
	}

	public static Map<String, Command> getMap(){
		return commands;
	}

}
