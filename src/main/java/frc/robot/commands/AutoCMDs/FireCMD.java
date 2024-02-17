// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCMDs;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TalonSRXMotors;
import edu.wpi.first.wpilibj.Timer;


public class FireCMD extends Command {
	private final double duration;
  private TalonSRXMotors talonSRXMotors;
	private double endTime;
	private boolean killed = false;




  /** Creates a new SetTalonSpeed. */
  public FireCMD(TalonSRXMotors talonSRXMotors) {
    this.duration = 0.75;
    this.talonSRXMotors = talonSRXMotors;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(talonSRXMotors);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    killed = false;
    endTime = Timer.getFPGATimestamp() + duration;
    System.out.println("Fire");
    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    talonSRXMotors.setShooterSpeed(1);
    talonSRXMotors.setSpeedFeeder(1);
    if(Timer.getFPGATimestamp() > endTime){
      killed = true;
    }

  }
  @Override
  public void end(boolean interrupted) {
    System.out.println("Note Shot"); 
    System.out.println("Shooter Off, Feeder Off"); 
    talonSRXMotors.setShooterSpeed(0);
    talonSRXMotors.setSpeedFeeder(0);
  }


  @Override
  public boolean isFinished() {
    return killed;
  }

}