// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TalonSRXMotors;



public class SmartIntakeOld extends Command {
  private Intake intake;
  private TalonSRXMotors talonSRXMotors;
  private boolean killed = false;
  private boolean reverse = false;
  private double reversedelay;
  //private Boolean beambreak;


  /** Creates a new IntakeCMD. */
  public SmartIntakeOld(Intake intake, TalonSRXMotors talonSRXMotors) {
    this.intake = intake;
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake,talonSRXMotors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    killed = false;
    reverse = false;
    intake.intakePistonDown();
    intake.intakeMotorSpeed(1);
    talonSRXMotors.setSpeedFeeder(0.75);
    //reversedelay = Timer.getFPGATimestamp();
    System.out.println("Intake Down, Full Speed, No Note");
    SmartDashboard.putBoolean("Intaking", true);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(reverse == false){
     if(talonSRXMotors.getFeederBeamBreak()){
        intake.intakeMotorSpeed(0);
        talonSRXMotors.setSpeedFeeder(-0.3);
        reversedelay = Timer.getFPGATimestamp() + 0.08;
        System.out.println("Note Intaked");
        SmartDashboard.putBoolean("Note Got", true);
        reverse = true;
      }
    }
    if(reverse){
      if(Timer.getFPGATimestamp() > reversedelay){
        talonSRXMotors.setSpeedFeeder(0);
      }
    }
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