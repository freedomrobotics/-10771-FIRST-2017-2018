package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.teleop.Teleop;

/**
 * Created by Kevin on 5/29/2017.
 */
public class Hardware {

    public DcMotor right1, right2;
    public DcMotor left1, left2;
    HardwareMap hwMap;

    public Hardware(OpMode o) {
        hwMap = o.hardwareMap;
    }

    public void init() {
        right1 = hwMap.dcMotor.get("right1");
        right2 = hwMap.dcMotor.get("right2");
        left1 = hwMap.dcMotor.get("left1");
        left2 = hwMap.dcMotor.get("left2");
    }
}
