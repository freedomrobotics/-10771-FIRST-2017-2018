package org.firstinspires.ftc.teamcode.framework.autonomous.autocode;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

public class AutoProgram {
    public boolean enabled = true;
    protected Autonomous autonomous;
    protected Hardware hardware;
    private String name = "";

    public AutoProgram(Autonomous a) {
        this.autonomous = a;
        this.hardware = a.getHardware();
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean e) {
        enabled = e;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void init() throws InterruptedException {}
    public void run() throws InterruptedException {}
}
