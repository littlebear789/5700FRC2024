// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.Constants;

public class TalonSRXMotors extends SubsystemBase {

  private TalonSRX motor2 = new TalonSRX(Constants.motor2ID);// feeder conveyor motor
  private TalonSRX motor3 = new TalonSRX(Constants.motor3ID); //shooter motor
  private TalonSRX motor4 = new TalonSRX(Constants.motor4ID); //shooter motor

  //private final BeamBreak feederBeamBreak;
  private DigitalInput feederBeamBreak;


  /** Creates a new TalonSRXMotors. */
  public TalonSRXMotors() {
    feederBeamBreak = new DigitalInput(0);

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
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
