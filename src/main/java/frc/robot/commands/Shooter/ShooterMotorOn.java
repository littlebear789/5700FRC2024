// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TalonSRXMotors;


public class ShooterMotorOn extends Command {

  private TalonSRXMotors talonSRXMotors;
  private final Boolean motor;


  /** Creates a new SetTalonSpeed. */
  public ShooterMotorOn(TalonSRXMotors talonSRXMotors, Boolean motor) {
    this.motor = motor;
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(talonSRXMotors);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Shooter On");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (motor){
      talonSRXMotors.setShooterSpeed(1);
      
    }
    else{
      talonSRXMotors.setShooterSpeed(0);
       
    }
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


}