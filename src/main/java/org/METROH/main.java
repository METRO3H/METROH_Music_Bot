package org.METROH;

public class main {


	public static void main(String[] args) {

		final String TOKEN = System.getenv("TOKEN");

		METROH_MUSIC bot = new METROH_MUSIC(TOKEN);

		bot.Connect();

		bot.On_Disconnect();

	}

}
