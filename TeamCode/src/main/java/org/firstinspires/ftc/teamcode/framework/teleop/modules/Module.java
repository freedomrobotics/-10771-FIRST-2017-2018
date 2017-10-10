package org.firstinspires.ftc.teamcode.framework.teleop.modules;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.teleop.Teleop;

/**
 * Created by Kevin on 5/29/2017.
 */
public class Module {
    public boolean enabled = true;
    protected Teleop teleop;
    protected Hardware hardware;

    /**
     * Creates a module
     * @param t The teleop object.
     */
    public Module(Teleop t) {
        this.teleop = t;
        this.hardware = t.getHardware();
    }

    /**
     * Gives whether or not the module is currently enabled.
     * @return The state of the module
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets wheter or not the module is enabled. Set to false to prevent init_loop() and loop() methods
     * @param e The desired status of the module
     */
    public void setEnabled(boolean e) {
        enabled = e;
    }

    /**
     * Runs on the INIT button being pressed on the driver station
     */
    public void init() {}

    /**
     * Runs repeatedly after the init() method, but before the loop() method.
     */
    public void init_loop() {}

    /**
     * Runs repeatedly after the program has been started
     */
    public void loop() {}

    /**
     * Runs directly before the program ends. Use this to clean up things not handled by the FTC SDK
     */
    public void stop() {}
}
