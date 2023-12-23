package org.METROH.Audio_Player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.voice.AudioProvider;

public class Audio_Player_Provider {
	final AudioPlayerManager AudioPlayerManager;
	final Scheduler scheduler;
	final AudioPlayer player;
	AudioProvider LavaPlayerAudioProvider;


	public Audio_Player_Provider(){
		// Creates AudioPlayer instances and translates URLs to AudioTrack instances
		AudioPlayerManager = new DefaultAudioPlayerManager();

		// This is an optimization strategy that Discord4J can utilize.
		// It is not important to understand
		AudioPlayerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

		// Allow playerManager to parse remote sources like YouTube links
		AudioSourceManagers.registerRemoteSources(AudioPlayerManager);

		// Create an AudioPlayer so Discord4J can receive audio data
		player = AudioPlayerManager.createPlayer();
		player.addListener(new AudioTrackScheduler(player));

		// We will be creating LavaPlayerAudioProvider in the next step
		LavaPlayerAudioProvider = new LavaPlayerAudioProvider(player);

		scheduler = new Scheduler(player);
	}

	public AudioPlayerManager getAudioPlayerManager() {
		return AudioPlayerManager;
	}

	public AudioPlayer getPlayer() {
		return player;
	}

	public AudioProvider getLavaPlayerAudioProvider() {
		return LavaPlayerAudioProvider;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}
