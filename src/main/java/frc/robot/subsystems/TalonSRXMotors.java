// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;

public class TalonSRXMotors extends SubsystemBase {

  private TalonSRX motor2 = new TalonSRX(Constants.motor2ID);
  private TalonSRX motor3 = new TalonSRX(Constants.motor3ID);
  private TalonSRX motor4 = new TalonSRX(Constants.motor4ID);



  /** Creates a new TalonSRXMotors. */
  public TalonSRXMotors() {
    
  }

  public void setSpeed1(double speed) {
    motor3.set(ControlMode.PercentOutput,speed);
  }

  public void setSpeed2(double speed) {
    motor4.set(ControlMode.PercentOutput,speed);
  }

  public void setSpeed3(double speed) {
    motor2.set(ControlMode.PercentOutput,speed);
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
