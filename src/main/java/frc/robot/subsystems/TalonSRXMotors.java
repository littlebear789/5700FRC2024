// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.Constants;

public class TalonSRXMotors extends SubsystemBase {

  private TalonSRX motor2 = new TalonSRX(Constants.motor2ID);// feeder conveyor motor
  private WPI_TalonSRX motor3 = new WPI_TalonSRX(Constants.motor3ID); //shooter motor
  private WPI_TalonSRX motor4 = new WPI_TalonSRX(Constants.motor4ID); //shooter motor

  private DigitalInput feederBeamBreak;
  private DigitalInput feederBeamBreakLow;




  /** Creates a new TalonSRXMotors. */
  public TalonSRXMotors() {
    feederBeamBreak = new DigitalInput(0);
    feederBeamBreakLow = new DigitalInput(2);

    motor3.configSelectedFeedbackSensor(
      FeedbackDevice.CTRE_MagEncoder_Relative,
      Constants.kPIDLoopIdx, 
      Constants.kTimeoutMs
    );

    motor3.setSensorPhase(true);

    motor3.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    motor3.config_kF(Constants.kPIDLoopIdx, 1023.0/35000.0, Constants.kTimeoutMs);
		motor3.config_kP(Constants.kPIDLoopIdx,0.25, Constants.kTimeoutMs);
		motor3.config_kI(Constants.kPIDLoopIdx, 0.001, Constants.kTimeoutMs);
		motor3.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);

    motor3.configNominalOutputForward(0, Constants.kTimeoutMs);
		motor3.configNominalOutputReverse(0, Constants.kTimeoutMs);
		motor3.configPeakOutputForward(1, Constants.kTimeoutMs);
		motor3.configPeakOutputReverse(-1, Constants.kTimeoutMs);

  }

  public void setShooterSpeedPID(double setPoint){
    double target = setPoint * 4096 / 600; //in RPM
    motor3.set(ControlMode.Velocity,target);
    motor4.set(ControlMode.Follower,Constants.motor3ID);
  }

  public void setSpeed1(double speed) {
    motor3.set(ControlMode.PercentOutput,speed);
    
  }

  public void setSpeed2(double speed) {
    motor4.set(ControlMode.PercentOutput,speed);
  }

  public void setShooterSpeed(double speed){
    motor3.set(ControlMode.PercentOutput,speed);
    motor4.set(ControlMode.PercentOutput,speed);
  }

  public void setSpeedFeeder(double speed) {
    motor2.set(ControlMode.PercentOutput,speed);
  }
  
  public boolean getFeederBeamBreak() {
    return !feederBeamBreak.get();
  }

  public boolean getFeederBeamBreakLow() {
    return !feederBeamBreakLow.get();
  }

  public double getShooterSpeed() {
    return motor3.getSelectedSensorVelocity(Constants.kPIDLoopIdx);
  }
  
}
