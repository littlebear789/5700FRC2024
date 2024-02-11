// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TalonSRXMotors;


public class ToggleTalons extends Command {

  private TalonSRXMotors talonSRXMotors;
  private final int button;
  private int toggle = 0;


  /** Creates a new SetTalonSpeed. */
  public ToggleTalons(TalonSRXMotors talonSRXMotors, int button) {
    this.button = button;
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(talonSRXMotors);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    toggle = button;
    if (toggle == 1){
      talonSRXMotors.setSpeed1(1); 
      talonSRXMotors.setSpeed2(1);
    }
    else{
      talonSRXMotors.setSpeed1(0);
      talonSRXMotors.setSpeed2(0);     
    }
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


}
