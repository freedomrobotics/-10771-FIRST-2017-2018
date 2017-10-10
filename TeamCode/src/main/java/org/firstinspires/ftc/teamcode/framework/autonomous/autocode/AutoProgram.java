package org.firstinspires.ftc.teamcode.framework.autonomous.autocode;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

public class AutoProgram {
    public boolean enabled = true;
    protected Autonomous autonomous;
    protected Hardware hardware;
    private String name = "";

    /**
     * Creates an Autonomous program
     * @param a The autonomous object.
     */
    public AutoProgram(Autonomous a) {
        this.autonomous = a;
        this.hardware = a.getHardware();
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
     * Sets the name of the autonomous program.
     * Seen in the program selection interface.
     * @param name The desired name for the autonomous program.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the desired name of the autonomous program
     * @return The name of the autonomous program (Blank if no name set).
     */
    public String getName() {
        return this.name;
    }

    /**
     * Runs on the INIT button being pressed on the driver station
     * Throw an InterruptedException to end the program (loop interrupts, errors, etc.)
     * @throws InterruptedException
     */
    public void init() throws InterruptedException {}

    /**
     * Runs when the program is started.
     * Throw an InterruptedException to end the program (loop interrupts, errors, etc.)
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {}
}
