package org.firstinspires.ftc.teamcode.framework.autonomous.components;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

/**
 * Created by Kevin on 9/15/2017.
 */

public class Component {
    protected Autonomous autonomous;
    protected Hardware hardware;

    public Component (Autonomous a) {
        this.autonomous = a;
        this.hardware = a.getHardware();
    }

    public void init() {}
}
