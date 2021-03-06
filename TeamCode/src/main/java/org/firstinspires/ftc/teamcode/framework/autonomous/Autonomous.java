package org.firstinspires.ftc.teamcode.framework.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.autonomous.autocode.AutoProgram;
import org.firstinspires.ftc.teamcode.framework.autonomous.components.AutoComponents;
import org.firstinspires.ftc.teamcode.framework.discovery.AnnotatedAutoCodeClassFilter;
import org.firstinspires.ftc.teamcode.framework.discovery.AnnotatedModuleClassFilter;
import org.firstinspires.ftc.teamcode.framework.discovery.Discoverer;

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

        Discoverer discoverer = Discoverer.getInstance();
        discoverer.registerFilter(AnnotatedAutoCodeClassFilter.getInstance());

        discoverer.processAllClasses();
        Set<Class<AutoProgram>> programClasses = discoverer.getAutoPrograms();

        for (Class<AutoProgram> programClass : programClasses) {
            AutoProgram p = null;
            try {
                p = programClass.getDeclaredConstructor(Autonomous.class).newInstance(this);
                String name = programClass.getAnnotation(AutoCode.class).name();
                p.setName(name);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            programs.add(p);
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
