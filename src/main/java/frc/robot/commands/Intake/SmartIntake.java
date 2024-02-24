// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TalonSRXMotors;



public class SmartIntake extends Command {
  private Intake intake;
  private TalonSRXMotors talonSRXMotors;
  private boolean killed = false;
  private boolean beamH = false;
  private boolean beamL = false;
  //private Boolean beambreak;


  /** Creates a new IntakeCMD. */
  public SmartIntake (Intake intake, TalonSRXMotors talonSRXMotors) {
    this.intake = intake;
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake,talonSRXMotors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    beamH = talonSRXMotors.getFeederBeamBreak();
    beamL = talonSRXMotors.getFeederBeamBreakLow();
    System.out.println("Intake Down");
    SmartDashboard.putBoolean("Intaking", true);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!beamH && !beamL){
      intake.intakePistonDown();
      intake.intakeMotorSpeed(1);
      talonSRXMotors.setSpeedFeeder(1);
    } else if(!beamH && beamL){
      intake.intakeMotorSpeed(0);
      talonSRXMotors.setSpeedFeeder(0.2);
    }  else if(beamH && !beamL){
      intake.intakeMotorSpeed(0);
      talonSRXMotors.setSpeedFeeder(-0.2);
    }else if(beamH && beamL){
      intake.intakeMotorSpeed(0);
      talonSRXMotors.setSpeedFeeder(0);
      SmartDashboard.putBoolean("Note Got", true);
    }else{}
      
  }

  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    talonSRXMotors.setSpeedFeeder(0);
    intake.intakeMotorSpeed(0);
    SmartDashboard.putBoolean("Intaking", false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return killed;
  }
}