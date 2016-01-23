package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import org.swerverobotics.library.SynchronousOpMode;

/**
 * 417 master opmode
 */
public abstract class MasterAuto extends MasterOpMode
{

    void initialize()
    {
        super.initialize();

        // Configure the knobs of the hardware according to how you've wired your
        // robot. Here, we assume that there are no encoders connected to the motors,
        // so we inform the motor objects of that fact.
        this.motorFrontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorFrontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        this.motorCollector.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorHook.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorLift.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    void driveTo(double power, int position , boolean resetEncoders) throws InterruptedException
    {
        telemetry.log.add("starting driveto");

        if(resetEncoders)
        {
            telemetry.log.add("try reset backright");
            this.motorBackRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
        telemetry.log.add("done reset");

        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        telemetry.log.add("done set mode");

        this.motorBackRight.setTargetPosition(position);
        telemetry.log.add("done set target");

        driveForward(power);

        telemetry.log.add("done set power");
        while ( this.motorBackRight.isBusy() )
        {
            telemetry.addData("backRight", this.motorBackRight.getCurrentPosition());
            telemetry.update();
            this.idle();
        }
        telemetry.log.add("done moving");

        driveStop();

        telemetry.addData("backRight", this.motorBackRight.getCurrentPosition());
        telemetry.update();
    }
}
