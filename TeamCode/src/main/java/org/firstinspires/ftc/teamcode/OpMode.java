package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Sample")
public class OpMode extends LinearOpMode {
    enum EncoderVals {
        CIRCUMFERENCE(Math.PI*104),
        TOTAL_GEAR_REDUCTION(30),
        COUNTS_PER_REV(500);

        private final double value;
        EncoderVals(double value) {
            this.value = value;
        }

        public double get() {
            return value;
        }
    }

    private Motor frontLeft, frontRight, backLeft, backRight, arm;
    private Servo claw;

    static final int INITIAL_DRIVE_POS = 0;
    static final int INITIAL_ARM_POS = 0;
    static final double INITIAL_CLAW_POS = 0;
    private static final int DRIVE_TIME = 2000;
    private static final int STRAFE_DISTANCE_MM = 500;
    private static final int ARM_MOVEMENT = 300;

    static final double TICKS_PER_UNIT = (EncoderVals.COUNTS_PER_REV.get()*
            EncoderVals.TOTAL_GEAR_REDUCTION.get())/EncoderVals.CIRCUMFERENCE.get();





    @Override
    public void runOpMode() throws InterruptedException {

        //2
        frontLeft = new Motor(hardwareMap, "fLeft");
        frontRight = new Motor(hardwareMap, "fRight");
        backLeft = new Motor(hardwareMap, "bLeft");
        backRight = new Motor(hardwareMap, "bRight");
        arm = new Motor(hardwareMap, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        frontLeft.useEncoder();
        frontRight.useEncoder();
        backLeft.useEncoder();
        backRight.useEncoder();
        arm.useEncoder();

        //3
        frontLeft.setTarget(INITIAL_DRIVE_POS);
        frontLeft.toPosition();
        frontRight.setTarget(INITIAL_DRIVE_POS);
        frontRight.toPosition();
        backLeft.setTarget(INITIAL_DRIVE_POS);
        backLeft.toPosition();
        backRight.setTarget(INITIAL_DRIVE_POS);
        backRight.toPosition();
        claw.setPosition(INITIAL_CLAW_POS);
        arm.setTarget(INITIAL_ARM_POS);
        arm.toPosition();

        waitForStart();

        //4
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
        sleep(DRIVE_TIME);
        stopDrive();
        sleep(5000);
        frontRight.setPower(-.5);
        frontLeft.setPower(-.5);
        backRight.setPower(-.5);
        backLeft.setPower(-.5);
        sleep(DRIVE_TIME);
        stopDrive();

        //5
        frontRight.setTarget(frontRight.getPosition() - STRAFE_DISTANCE_MM*TICKS_PER_UNIT);
        frontLeft.setTarget(frontLeft.getPosition() + STRAFE_DISTANCE_MM*TICKS_PER_UNIT);
        backLeft.setTarget(backLeft.getPosition() - STRAFE_DISTANCE_MM*TICKS_PER_UNIT);
        backRight.setTarget(backRight.getPosition() + STRAFE_DISTANCE_MM*TICKS_PER_UNIT);
        frontRight.toPosition();
        frontLeft.toPosition();
        backRight.toPosition();
        backLeft.toPosition();
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);

        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy()) {
            idle();
        }

        stopDrive();

        //6
        arm.setTarget(ARM_MOVEMENT);
        arm.toPosition();
        arm.setPower(.5);
        while (arm.isBusy()) {
            idle();
        }
        arm.setPower(0);

        //7
        claw.setPosition(1);
        sleep(1000);
        claw.setPosition(0);

    }

    private void stopDrive() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }
}
