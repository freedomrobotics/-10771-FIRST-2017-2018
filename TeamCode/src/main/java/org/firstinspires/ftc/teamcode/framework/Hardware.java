package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.framework.teleop.Teleop;

/**
 * Created by Kevin on 5/29/2017.
 */
public class Hardware {

    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backRight;
    public DcMotor backLeft;

    HardwareMap hwMap;

    public Hardware(OpMode o) {
        hwMap = o.hardwareMap;
    }

    public void init() {
        frontRight = hwMap.dcMotor.get("frontright");
        frontLeft = hwMap.dcMotor.get("frontleft");
        backRight = hwMap.dcMotor.get("backright");
        backLeft = hwMap.dcMotor.get("backleft");
    }
}
