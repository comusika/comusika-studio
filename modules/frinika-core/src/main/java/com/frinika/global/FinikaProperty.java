/*
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.frinika.global;

import java.awt.Font;
import java.io.File;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Enumeration of configuration properties.
 *
 * @author hajdam
 */
public enum FinikaProperty {

    TICKS_PER_QUARTER(128),
    SEQUENCER_PRIORITY(0),
    // used by JavaSoundVoiceServer
    // TODO: IS THIS IN MILLISECONDS? OTHERWISE CONVERSION MILLISECONDS (as in gui) <-> AUDIO_BUFFER_LENGTH is needed 
    AUDIO_BUFFER_LENGTH(512),
    MIDIIN_DEVICES_LIST(""),
    DIRECT_MONITORING(false),
    MULTIPLEXED_AUDIO(false),
    BIG_ENDIAN(false),
    // lowercase spelling for 'historic' reasons
    SAMPLE_RATE("sampleRate", 44100),
    JACK_AUTO_CONNECT(false),
    AUTOMATIC_CHECK_FOR_NEW_VERSION(true),
    // deprecated I believe PJL
    //	public static int OS_LATENCY_MILLIS = 0;
    //	public static Meta _OS_LATENCY_MILLIS;
    MAXIMIZE_WINDOW(false),
    MOUSE_NUMBER_DRAG_INTENSITY(2.0f),
    TEXT_LANE_FONT(new Font("Arial", Font.PLAIN, 8)),
    // Directories
    GROOVE_PATTERN_DIRECTORY(new File(System.getProperty("user.home"), "frinika/groovepatterns/")),
    SCRIPTS_DIRECTORY(new File(System.getProperty("user.home"), "frinika/scripts/")),
    PATCHNAME_DIRECTORY(new File(System.getProperty("user.home"), "frinika/patchname/")),
    AUDIO_DIRECTORY(new File(System.getProperty("user.home"), "frinika/audio/")),
    SOUNDFONT_DIRECTORY(new File(System.getProperty("user.home"), "frinika/soundfonts/")),
    DEFAULT_SOUNDFONT(new File(System.getProperty("user.home"), "frinika/soundfonts/8MBGMSFX.SF2")),
    // Recent files
    LAST_PROJECT_FILENAME(new ConfigurationProperty<String>("LAST_PROJECT_FILENAME", null)),
    RECENT_FILENAMES(new ConfigurationProperty<List<String>>("RECENT_FILENAMES", null));

    private final ConfigurationProperty<?> property;

    private FinikaProperty(@Nonnull ConfigurationProperty<?> property) {
        this.property = property;
    }

    private <T> FinikaProperty(@Nullable T value) {
        this.property = new ConfigurationProperty<>(getName(), value);
    }

    private <T> FinikaProperty(@Nonnull String propertyName, @Nullable T value) {
        this.property = new ConfigurationProperty<>(propertyName, value);
    }

    @Nonnull
    public String getName() {
        return property.getFieldName();
    }

    @Nonnull
    public ConfigurationProperty<?> getProperty() {
        return property;
    }
}
