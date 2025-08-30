package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor {
  DcMotor motor;

  public Motor(HardwareMap hardwareMap, String name) {
    this.motor = hardwareMap.dcMotor.get(name);
    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
  }

  public void setPower(double power) {
    this.motor.setPower(power);
  }

  public double getPower() {
    return this.motor.getPower();
  }

  public void resetEncoder(boolean useEncoder) {
    this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    if (useEncoder) {
      useEncoder();
    } else {
      noEncoder();
    }
  }

  public void noEncoder() {
    this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  }

  public void useEncoder() {
    this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }

  public void setTarget(double target) {
    this.motor.setTargetPosition((int) target);
  }

  public void toPosition() {
    this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
  }

  public boolean isBusy() {
    return this.motor.isBusy();
  }
  public double getPosition() {
    return this.motor.getCurrentPosition();
  }
}