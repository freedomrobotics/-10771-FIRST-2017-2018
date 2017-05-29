package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.teleop.Teleop;

/**
 * Created by Kevin on 5/29/2017.
 */
public class Hardware {

    public DcMotor right;
    public DcMotor left;
    HardwareMap hwMap;

    public Hardware(Teleop t) {
        hwMap = t.hardwareMap;
    }

    public void init() {
        right = hwMap.dcMotor.get("right");
        left = hwMap.dcMotor.get("left");
    }
}
