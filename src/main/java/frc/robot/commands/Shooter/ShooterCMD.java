// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TalonSRXMotors;


public class ShooterCMD extends Command {

  private TalonSRXMotors talonSRXMotors;



  /** Creates a new SetTalonSpeed. */
  public ShooterCMD(TalonSRXMotors talonSRXMotors) {
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(talonSRXMotors);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Shooting");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    talonSRXMotors.setSpeed1(1); 
    talonSRXMotors.setSpeed2(1);
    talonSRXMotors.setSpeed3(0.4);
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Shooting Release");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


}
