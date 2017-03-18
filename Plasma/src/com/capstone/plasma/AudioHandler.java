package com.capstone.plasma;


	import java.io.BufferedInputStream;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.nio.FloatBuffer;
	import java.nio.IntBuffer;

	import org.lwjgl.BufferUtils;
	import org.lwjgl.LWJGLException; 
	import org.lwjgl.openal.AL;
	import org.lwjgl.openal.AL10;
	import org.lwjgl.openal.AL11;
	import org.lwjgl.util.WaveData;

	public class AudioHandler {
	/** Position of the source sound. */
	FloatBuffer sourcePos;

	/** Velocity of the source sound. */
	FloatBuffer sourceVel;

	/** Position of the listener. */
	FloatBuffer listenerPos;

	/** Velocity of the listener. */
	FloatBuffer listenerVel;

	/** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	FloatBuffer listenerOri;

	/** Buffers hold sound data. */
	IntBuffer buffer;

	/** Sources are points emitting sound. */
	IntBuffer source;

	public AudioHandler()
	{
	  /** Buffers hold sound data. */
	  buffer = BufferUtils.createIntBuffer(1);

	  /** Sources are points emitting sound. */
	  source = BufferUtils.createIntBuffer(1);

	  /** Position of the source sound. */
	  sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f});

	  /** Velocity of the source sound. */
	  sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f });

	  /** Position of the listener. */
	  listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f });

	  /** Velocity of the listener. */
	  listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f });

	  /** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	  listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }); 
	}
	int loadALData() 
	{
	    sourcePos.rewind(); 
	    sourceVel.rewind();

	    // Load wav data into a buffer.
	    AL10.alGenBuffers(buffer);

	    if(AL10.alGetError() != AL10.AL_NO_ERROR)
	    {
	      return AL10.AL_FALSE;
	    }

	    WaveData waveFile;
	    try {
	        //FileInputStream fin=new FileInputStream("18am01.wav");
	        FileInputStream fin=new FileInputStream("music/back music.wav");
	        BufferedInputStream bin = new BufferedInputStream(fin);
	        waveFile = WaveData.create(bin);
	        AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
	        waveFile.dispose();
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    // Bind the buffer with the source.
	    AL10.alGenSources(source);

	    if (AL10.alGetError() != AL10.AL_NO_ERROR)
	    {
	      return AL10.AL_FALSE;
	    }

	    AL11.alSource(source.get(0), AL10.AL_BUFFER,   buffer);
	    AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f        );
	    AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f        );
	    AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos   );
	    AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel   );
	    //AL10.alSourcef(source.get(0), AL11.AL_SPEED_OF_SOUND,100.5f);
	    // Do another error check and return.
	    if (AL10.alGetError() == AL10.AL_NO_ERROR)
	    {
	      return AL10.AL_TRUE;
	    } 
	    return AL10.AL_FALSE;
	}
	/**
	 * void setListenerValues()
	 *
	 *  We already defined certain values for the Listener, but we need
	 *  to tell OpenAL to use that data. This function does just that.
	 */
	void setListenerValues() {

	  listenerPos.rewind();
	  listenerVel.rewind();
	  listenerOri.rewind();

	  AL10.alListener(AL10.AL_POSITION,    listenerPos);
	  AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
	  AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}


	void killALData() {
	  AL10.alDeleteSources(source);
	  AL10.alDeleteBuffers(buffer);
	}

	public void execute() {
	      // Initialize OpenAL and clear the error bit.
	      try{
	        AL.create();
	      } catch (LWJGLException le) {
	        le.printStackTrace();
	        return;
	      }
	      AL10.alGetError();

	      // Load the wav data.
	      if(loadALData() == AL10.AL_FALSE) {
	        System.out.println("Error loading data.");
	        return;
	      }  
	      setListenerValues();
	      // Loop.
	      char c = ' ';
	      while(c != 'q') {
	        try {
	         c = (char) System.in.read();
	        } catch (IOException ioe) {
	          c = 'q';
	      }
	      switch(c) {
	        // Pressing 'p' will begin playing the sample.
	        case 'p': 
	            AL10.alSourcePlay(source.get(0));
	            break;
	    // Pressing 's' will stop the sample from playing.
	        case's': AL10.alSourceStop(source.get(0)); break;

	        // Pressing 'h' will pause the sample.
	        case 'h': AL10.alSourcePause(source.get(0)); break;
	      };
	      }
	    killALData();
	 }
	

}
