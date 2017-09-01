package org.firstinspires.ftc.teamcode.framework.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.autonomous.autocode.AutoProgram;
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
        for (Class<?> clazz : annotated) {
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

        //TODO: Create some sort of interface here for selecting an autonomous program.
        AutoProgram a = programs.get(0);

        //initialize the auto program...
        a.init();

        telemetry.clearAll();
        telemetry.addLine("Program " + a.getName() + " has been initialized.");
        telemetry.addLine("Ready to run...");
        telemetry.update();

        waitForStart(); //wait for auto period to start

        telemetry.clearAll();
        telemetry.addLine("Program " + a.getName() + " running...");

        a.run(); //run autonomous.
    }

    public Hardware getHardware() {
        return this.hardware;
    }

}
