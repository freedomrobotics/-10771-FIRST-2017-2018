package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
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

    public ModernRoboticsI2cGyro gyro;

    HardwareMap hwMap;

    public Hardware(OpMode o) {
        hwMap = o.hardwareMap;
    }


    /**
     * Initializes all hardware connected to the robot.
     */
    public void init() {
        frontRight = hwMap.dcMotor.get("frontright");
        frontLeft = hwMap.dcMotor.get("frontleft");
        backRight = hwMap.dcMotor.get("backright");
        backLeft = hwMap.dcMotor.get("backleft");

        gyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro");
    }
}
