package metronomo;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Clock;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.SamplePlayer;

/**
 *
 * @author Agarimo
 */
public abstract class Metronomo {

    private AudioContext ac;
    private Clock clock;
    private Gain gain;
    private Compas compas;
    private int tempo;
    private int count;
    private int timeLapse;

    private final Sample tick;
    private final Sample tack;
    private final String audioTick = "sounds/snd2.wav";
    private final String audioTack = "sounds/snd1.wav";

    public Metronomo() {
        count = 1;
        this.tempo = 150;
        this.timeLapse = (60 * 1000) / this.tempo;
        this.compas = Compas.c3by4;
        tick = SampleManager.sample(audioTick);
        tack = SampleManager.sample(audioTack);
        init();
    }

    private void init() {
        ac = new AudioContext();
        clock = new Clock(ac, timeLapse);
        clock.setTicksPerBeat(getNoteValue());
        clock.addMessageListener(new Bead() {
            @Override
            public void messageReceived(Bead message) {
                SamplePlayer player;

                if (count == 1) {
                    player = new SamplePlayer(ac, tick);
                    gain = new Gain(ac, 1, 0.5f);
                    updateUI(count, timeLapse);
                } else {
                    player = new SamplePlayer(ac, tack);
                    gain = new Gain(ac, 1, 0.2f);
                    updateUI(count, timeLapse);

                    if (count == compas.getCount()) {
                        count = 0;
                    }
                }

                gain.addInput(player);
                ac.out.addInput(gain);
                count++;
            }
        });

        ac.out.addDependent(clock);
    }

    private int getNoteValue() {
        if (compas.getValue() == 4) {
            return 1;
        } else {
            return 2;
        }
    }

    protected abstract void updateUI(int estado, long timeLapse);

    public void setTempo(int tempo) {
        this.tempo = tempo;
        this.timeLapse = (60 * 1000) / this.tempo;
        clock.getIntervalUGen().setValue(timeLapse);
        System.out.println("TEMPO REAL: "+clock.getTempo());
    }

    public void setCompas(Compas compas) {
        this.compas = compas;
        clock.setTicksPerBeat(getNoteValue());
        count = 1;
    }

    public void start() {
        ac.start();
    }

    public void stop() {
        ac.stop();
        updateUI(0, 0);
        count = 1;
    }

    public Compas getCompas() {
        return compas;
    }

    public int getTempo() {
        return tempo;
    }

    public long getTimeLapse() {
        return this.timeLapse;
    }

    public int getCount() {
        return count;
    }

    public Clock getClock() {
        return clock;
    }
}
