//import java.io.FileNotFoundException;
//
//import javax.sound.midi.*;
//import java.applet.AudioClip;
//
//public class Main {
//	public static void main(String[] args) throws FileNotFoundException {
//		String hey = Logic.conversion("a b");
//		System.out.print(hey);
//
//		
//	}
//}

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

	// This is just an example - you would want to handle LineUnavailable
	// properly...
	public static void main(String[] args) throws InterruptedException, LineUnavailableException {

		String input = Logic.conversion("GONE IN A SECOND ULTIMATE DESTRUCTION GODS ARE WARRING SORROW NEVER ENDING ENDLESS CHAOS FOR AN ETERNITY WELCOME TO THE ABYSS");

		System.out.println(input);

		// sound stuff
		final int SAMPLING_RATE = 15000; // Audio sampling rate
		final int SAMPLE_SIZE = 2; // Audio sample size in bytes

		final double SHORTSAMPLESTOTAL = SAMPLING_RATE * 0.05; // Output for
																// roughly 1
																// seconds

		final double LONGSAMPLESTOTAL = SAMPLING_RATE * 0.15; // Output for
																// roughly 3
																// seconds
		double sampleSize = 2;
		for (char c : input.toCharArray()) {

			double ctSamplesTotal = 0;
			if (c == '.') {
				ctSamplesTotal = SHORTSAMPLESTOTAL;
				sampleSize = 9;
			} else if (c == '-') {
				ctSamplesTotal = LONGSAMPLESTOTAL;
				sampleSize = 3;
			} else if (c == '_') {
				ctSamplesTotal = SHORTSAMPLESTOTAL;
				sampleSize = 9;
			}
			SourceDataLine line;
			double fFreq = 800; // Frequency of the sine wave

			if (c == '_')
				fFreq = 0;

			// Position through the sine wave as a percentage (i.e. 0 to 1 is 0
			// to
			// 2*PI)
			double fCyclePosition = 0;

			// Open up audio output, using 44100hz sampling rate, 16 bit
			// samples,
			// mono,
			// and big endian byte ordering
			AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line matching " + info + " is not supported.");
				throw new LineUnavailableException();
			}

			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();

			// Make our buffer size match audio system's buffer
			ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());

			// On each pass main loop fills the available free space in the
			// audio
			// buffer
			// Main loop creates audio samples for sine wave, runs until we tell
			// the
			// thread to exit
			// Each sample is spaced 1/SAMPLING_RATE apart in time
			while (ctSamplesTotal > 0) {
				double fCycleInc = fFreq / SAMPLING_RATE; // Fraction of
															// cycle
															// between
															// samples

				cBuf.clear(); // Discard the samples from the last pass

				// Figure out how many samples we can add
				double ctSamplesThisPass = line.available() / sampleSize;
				for (int i = 0; i < ctSamplesThisPass; i++) {
					cBuf.putShort((short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * fCyclePosition)));

					fCyclePosition += fCycleInc;
					if (fCyclePosition > 1)
						fCyclePosition -= 1;
				}

				// Write sine samples to the line buffer. If the audio
				// buffer is
				// full, this will
				// block until there is room (we never write more samples
				// than
				// buffer will hold)

				line.write(cBuf.array(), 0, cBuf.position());

				ctSamplesTotal -= ctSamplesThisPass; // Update total
														// number
														// of
														// samples written

				// Wait until the buffer is at least half empty before we
				// add
				// more
				// while (line.getBufferSize() / 2 < line.available())
				// Thread.sleep(1);

			}

			line.drain();
			line.close();
		}

		// Done playing the whole waveform, now wait until the queued
		// samples
		// finish
		// playing, then clean up and exit
		// line.drain();
		// line.close();

	}
}
