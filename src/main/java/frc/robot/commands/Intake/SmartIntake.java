// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.BeamBreak;
import frc.robot.subsystems.Intake;


public class SmartIntake extends Command {
  private boolean killed = false;
  private Intake intake;
  private final BeamBreak feederBeamBreak;

  //private RobotContainer robotContainer;

  /** Creates a new IntakeCMD. */
  public SmartIntake(Intake intake) {

    this.feederBeamBreak = new BeamBreak(0);
    this.intake = intake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    killed = false;
    intake.intakePistonDown();
    intake.intakeMotorSpeed(1);
    System.out.println("Intake Down, Full Speed");
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(feederBeamBreak.get()){
      killed = true;
    }

  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return killed;
  }
}