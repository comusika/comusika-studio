/*
 * Created on Feb 27, 2005
 *
 * Copyright (c) 2005 Peter Johan Salomonsen (http://www.petersalomonsen.com)
 * 
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.frinika.synth.synths.sampler.settings.samplersettingversions;

import com.frinika.synth.synths.sampler.settings.SampledSoundSettings;
import com.frinika.synth.synths.sampler.settings.SamplerSettings;
import java.io.Serializable;

/**
 * @author Peter Johan Salomonsen
 *
 */
public class Sampler20050227 implements SamplerSettings,Serializable{
    private static final long serialVersionUID = 1L;

    private String soundFontName;
    private int instrumentIndex;
    private String instrumentName;
    private SampledSoundSettings[][] sampledSounds;
    private int layers = 1;
    private float freqSpread = 0;
    
    public String getSoundFontName() {
        return soundFontName;
    }
    public void setSoundFontName(String soundFontName) {
        this.soundFontName = soundFontName; 
        
    }
    public int getInstrumentIndex() {
        return instrumentIndex;
    }
    public void setInstrumentIndex(int instrumentIndex) {
        this.instrumentIndex = instrumentIndex;
    }
    
    public String getInstrumentName() {
        return instrumentName;
    }
    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }
    public SampledSoundSettings[][] getSampledSounds() {
        return sampledSounds;
    }
    public void setSampledSounds(SampledSoundSettings[][] sampledSounds) {
        this.sampledSounds = sampledSounds;
    }
    public int getLayers() {
        return layers;
    }
    public void setLayers(int layers) {
        this.layers = layers;
    }
    
    public float getFreqSpread() {
        return freqSpread;
    }
    public void setFreqSpread(float freqSpread) {
        this.freqSpread = freqSpread;
    }
}
