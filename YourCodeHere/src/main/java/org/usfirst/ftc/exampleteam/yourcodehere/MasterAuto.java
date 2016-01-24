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
    //IMU variable declaration
    ElapsedTime elapsed = new ElapsedTime();
    IBNO055IMU.Parameters parameters = new IBNO055IMU.Parameters();

    EulerAngles angles;

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
        //this.motorHook.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        //this.motorLift.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //IMU initialization
        parameters.angleunit = IBNO055IMU.ANGLEUNIT.DEGREES;
        parameters.accelunit = IBNO055IMU.ACCELUNIT.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "BNO055";
        imu = ClassFactory.createAdaFruitBNO055IMU(hardwareMap.i2cDevice.get("imu"), parameters);

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

    /////// 6220 IMU Code
    public void setAutoStartPosition (double startingAngle) throws InterruptedException
    {
        autoStartPosition.orientation = startingAngle;
    }


    //turn the robot to face a global direction
    public void turnTo(double targetAngle) throws InterruptedException
    {
        double offset = 0;
        double Δϴ;
        double power;
        boolean isTurnCompleted = false;
        double currentOrientation;
        double[] lasts = {0,0};
        double sensorDiff;
        double satisfactionCounter = 0;

        while (!isTurnCompleted)
        {
            turnFilter.update();

            currentOrientation = getCurrentGlobalOrientation();
            Δϴ = targetAngle - currentOrientation;
            //roll sensor difference. we do this to maintain the PID filter's unawareness of the transition
            lasts[1] = lasts[0];
            lasts[0] = Δϴ;
            sensorDiff = lasts[0]-lasts[1];
            //check 360-0 case
            if (Math.abs(sensorDiff) > 350)
            {
                offset -= Math.signum(sensorDiff) * 360;
            }
            Δϴ += offset;
            //check suboptimal direction case
            //should only resolve once
            if (Math.abs(Δϴ) > 180)
            {
                offset -= Math.signum(Δϴ) * 360;
            }
            turnFilter.roll(Δϴ);

            //set filtered motor powers
            power = turnFilter.getFilteredValue();
            //cap power at 1 magnitude
            if (Math.abs(power) > 1)
            {
                power = Math.signum(power);
            }
            driveWheels(-power, power);

            telemetry.addData("power:", power);
            telemetry.addData("dA:", Δϴ);



            //check if the turn is finished and the robot is settled

            if (Math.abs(Δϴ) < 3)
            {
                satisfactionCounter++;
            }
            else if (Math.abs(Δϴ) < 4)
            {
                satisfactionCounter+= 0.4;
            }
            else
            {
                satisfactionCounter = 0;
            }


            if (satisfactionCounter > 100)
            {
                isTurnCompleted = true;
            }

            telemetry.addData("satisfaction:", satisfactionCounter);
            telemetry.update();

            wait(1);
            idle();
        }
        stopAllMotors();

    }

    public void driveStraight(double distance, double direction, boolean climbers) throws InterruptedException
    {
        double offset = 0;
        double Δϴ;
        double power;
        double leftpower;
        double rightpower;
        boolean isDriveCompleted = false;
        double currentOrientation;
        double[] lasts = {0,0};
        double sensorDiff;
        double timeFactor = 0.5;

        double targetAngle = getCurrentGlobalOrientation();
        double startDistance = getDistanceTraveled();

        while (!isDriveCompleted)
        {
            turnFilter.update();

            if (timeFactor < 1)
            {
                timeFactor += .005;
            }

            currentOrientation = getCurrentGlobalOrientation();
            Δϴ = targetAngle - currentOrientation;
            //roll sensor difference. we do this to maintain the PID filter's unawareness of the transition
            lasts[1] = lasts[0];
            lasts[0] = Δϴ;
            sensorDiff = lasts[0]-lasts[1];
            //check 360-0 case
            if (Math.abs(sensorDiff) > 350)
            {
                offset -= Math.signum(sensorDiff) * 360;
            }
            Δϴ += offset;
            //check suboptimal direction case
            //should only resolve once
            if (Math.abs(Δϴ) > 180)
            {
                offset -= Math.signum(Δϴ) * 360;
            }
            turnFilter.roll(Δϴ);

            //set filtered motor powers
            power = turnFilter.getFilteredValue();
            //cap power at 0.4 magnitude
            if (Math.abs(power) > 1)
            {
                power = 1*Math.signum(power);
            }

            leftpower = 0.9 + power;
            rightpower = 0.9 - power;

            telemetry.addData("power:", power);
            telemetry.update();
            //cap power at 1 magnitude
            if (Math.abs(leftpower) > 1)
            {
                leftpower = Math.signum(leftpower);
            }

            //cap power at 1 magnitude
            if (Math.abs(rightpower) > 1)
            {
                rightpower = Math.signum(rightpower);
            }

            if(climbers)
            {
                driveClimbers(leftpower * direction * timeFactor, rightpower * direction * timeFactor);
            }
            driveWheels(leftpower * direction * timeFactor, rightpower * direction * timeFactor);

            //check if the robot should stop
            if (Math.abs(getDistanceTraveled() - startDistance) >= Math.abs(distance))
            {
                isDriveCompleted = true;
            }

            wait(1);
            idle();
        }
        stopDriveMotors();

    }

    //returns the average of the left/right encoders, giving a distance in CM
    //tested to be within 0.67% of actual, at 150cm
    private double getDistanceTraveled()
    {
        double CORRECTION_FACTOR = 1.0026;
        double avgTick = (MotorLeftBack.getCurrentPosition() + MotorRightBack.getCurrentPosition())/2;
        double realityFactor = Constants.REAR_WHEEL_DIAMETER * Math.PI / Constants.ANDYMARK_ENC_TICKS;
        return avgTick*realityFactor/CORRECTION_FACTOR;
    }

    //return the global orientation of the robot, accounting for autonomous start
    public double getCurrentGlobalOrientation()
    {
        return ((getCurrentLocalOrientation() + autoStartPosition.orientation));
    }
    //return the local orientation of the robot, relative to it's start
    public double getCurrentLocalOrientation()
    {
        angles = imu.getAngularOrientation();
        return 360-angles.heading;
    }




}
