// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private final DoubleSolenoid shooter = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,Constants.shooterUp, Constants.shooterDown);

  
  /** Creates a new Intake. */
  public Shooter() {


  }

  public void shooterPistonUp(){
    this.shooter.set(DoubleSolenoid.Value.kForward);
    
  }
  
  public void shooterPistonDown(){
    this.shooter.set(DoubleSolenoid.Value.kReverse);
  }

  public void shooterPistonToggle(){
    this.shooter.toggle();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
