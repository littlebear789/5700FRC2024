// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TalonSRXMotors;



public class SmartIntake extends Command {
  private Intake intake;
  private TalonSRXMotors talonSRXMotors;
  private boolean killed = false;
  //private Boolean beambreak;


  /** Creates a new IntakeCMD. */
  public SmartIntake(Intake intake, TalonSRXMotors talonSRXMotors) {
    this.intake = intake;
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake,talonSRXMotors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    killed = false;
    intake.intakePistonDown();
    intake.intakeMotorSpeed(1);
    talonSRXMotors.setSpeedFeeder(1);
    System.out.println("Intake Down, Full Speed, No Note");
    SmartDashboard.putBoolean("Intaking", true);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(talonSRXMotors.getFeederBeamBreak()){

      intake.intakeMotorSpeed(0);
      talonSRXMotors.setSpeedFeeder(0);
    }
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    talonSRXMotors.setSpeedFeeder(0);
    intake.intakeMotorSpeed(0);
    System.out.println("Note Intaked");
    SmartDashboard.putBoolean("Intaking", false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return killed;
  }
}