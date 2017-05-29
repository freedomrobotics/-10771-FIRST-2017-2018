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

    public Module(Teleop t) {
        this.teleop = t;
        this.hardware = t.getHardware();
    }

    public void init() {}
    public void init_loop() {}
    public void loop() {}
    public void stop() {}
}
