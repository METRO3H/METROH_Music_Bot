package org.METROH.Audio_Player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AudioTrackScheduler extends AudioEventAdapter {

  private final List<AudioTrack> queue;
  private final AudioPlayer player;

  public AudioTrackScheduler(AudioPlayer player) {
    // The queue may be modifed by different threads so guarantee memory safety
    // This does not, however, remove several race conditions currently present
    queue = Collections.synchronizedList(new LinkedList<>());
    this.player = player;
  }

  public List<AudioTrack> getQueue() {
    return queue;
  }

  public boolean play(AudioTrack track) {
    return play(track, false);
  }

  public boolean play(AudioTrack track, boolean force) {
    boolean playing = player.startTrack(track, !force);

    if (!playing) {
      queue.add(track);
    }

    System.out.println(queue);
    return playing;
  }

  public boolean skipTrackOnQueue() {
    System.out.println(queue);
    return !queue.isEmpty() && play(queue.remove(0), true);
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    // Advance the player if the track completed naturally (FINISHED) or if the track cannot play (LOAD_FAILED)
    if (endReason.mayStartNext) {
      String result =
        skipTrackOnQueue() ?
          "Se agrego correctamente a la cola\n" : "Ocurrio un error al agregar a la cola\n";
      System.out.println(result);
    }
  }
}