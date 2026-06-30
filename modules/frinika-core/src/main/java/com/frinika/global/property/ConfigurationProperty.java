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
package com.frinika.global.property;

import java.util.HashMap;
import java.util.Map;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Configuration property.
 *
 * @author hajdam
 * @param <T> property type
 */
public class ConfigurationProperty<T> {

    @NonNull
    private static final Map<String, ConfigurationProperty<?>> PROPERTIES_BY_NAME = new HashMap<>();

    @NonNull
    private final FrinikaGlobalProperty globalProperty;
    @Nullable
    private T value;
    @NonNull
    private final Class<T> typeClass;

    public ConfigurationProperty(@NonNull Class<T> typeClass, @NonNull FrinikaGlobalProperty globalProperty, @Nullable T value) {
        this.globalProperty = globalProperty;
        this.value = value;
        this.typeClass = typeClass;

        registerProperty(globalProperty);
    }

    private void registerProperty(@NonNull FrinikaGlobalProperty globalProperty) {
        PROPERTIES_BY_NAME.put(globalProperty.getName(), this);
    }

    @NonNull
    public String getName() {
        return globalProperty.getName();
    }

    @NonNull
    public Class<T> getType() {
        return typeClass;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public void setValue(@Nullable T value) {
        this.value = value;
    }

    @Nullable
    public static ConfigurationProperty<?> findByName(@NonNull String name) {
        return PROPERTIES_BY_NAME.get(name);
    }
}
