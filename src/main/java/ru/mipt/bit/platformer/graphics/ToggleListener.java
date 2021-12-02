package ru.mipt.bit.platformer.graphics;

/**
 * driving port
 */

public class ToggleListener {
    private boolean enabled = false;

    public void toggle() {
        enabled = !enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
