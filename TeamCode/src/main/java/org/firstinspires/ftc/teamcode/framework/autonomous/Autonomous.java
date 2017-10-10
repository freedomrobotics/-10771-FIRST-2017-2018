package org.firstinspires.ftc.teamcode.framework.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.autonomous.autocode.AutoProgram;
import org.firstinspires.ftc.teamcode.framework.autonomous.components.AutoComponents;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Kevin on 9/1/2017.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="Grapefruit")

public class Autonomous extends LinearOpMode {

    Hardware hardware;

    @Override
    public void runOpMode() {


        ArrayList<AutoProgram> programs = new ArrayList<AutoProgram>();

        hardware = new Hardware(this);
        hardware.init();


        //REGISTER MODULES HERE
        Reflections reflections = new Reflections("org.firstinspires.ftc.teamcode.framework.autonomous.autocode"); //package to search
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(org.firstinspires.ftc.teamcode.framework.autonomous.AutoCode.class); //get annotated classes
        for (Class<?> clazz : annotated) { //loop over each annotated class
            try {
                AutoProgram a = (AutoProgram)clazz.getDeclaredConstructor(Autonomous.class).newInstance(this); //make an instance of the auto program
                a.setName(clazz.getAnnotation(org.firstinspires.ftc.teamcode.framework.autonomous.AutoCode.class).name()); // set the name of the auto program
                programs.add(a); //add it to the list of modules
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        int selected = 0;
        boolean lockedIn = false;
        boolean keyDown = false;

        while (!lockedIn) {

            //Draw all programs.
            for (int i = 0; i < programs.size(); i++) {
                String currentName = "";
                if (selected == i) {
                    currentName += "> ";
                }
                currentName += programs.get(i).getName();
                telemetry.addLine(currentName);
            }
            telemetry.update();

            //handle selection of program to run.
            if (gamepad1.dpad_up && keyDown == false) {
                selected = selected == 0 ? 0 : selected - 1;
                keyDown = true;
            } else if (gamepad1.dpad_down && keyDown == false) {
                selected = selected == programs.size() - 1 ? programs.size() - 1 : selected + 1;
                keyDown = true;
            } else {
                keyDown = false;
            }

            //handle locking in.
            if (gamepad1.start) {
                lockedIn = true;
            }
        }

        AutoProgram a = programs.get(selected); //get the program, assign it to a

        //initialize the auto program...
        try {
            new AutoComponents(this);
            AutoComponents.init(); //initialize all autonomous components
            a.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
            requestOpModeStop();
        }

        telemetry.clearAll();
        telemetry.addLine("Program " + a.getName() + " has been initialized.");
        telemetry.addLine("Ready to run...");
        telemetry.update();

        waitForStart(); //wait for auto period to start

        telemetry.clearAll();
        telemetry.addLine("Program " + a.getName() + " running...");

        //run autonomous
        try {
            AutoComponents.roboStart();
            a.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }


    /**
     * Gets the {@link Hardware} object associated with the robot.
     * @return  Hardware object
     * @see     Hardware
     */
    public Hardware getHardware() {
        return this.hardware;
    }

}
