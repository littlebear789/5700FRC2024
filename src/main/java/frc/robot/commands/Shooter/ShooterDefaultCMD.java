// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TalonSRXMotors;


public class ShooterDefaultCMD extends Command {

  private TalonSRXMotors talonSRXMotors;
  private Shooter shooter;



  /** Creates a new SetTalonSpeed. */
  public ShooterDefaultCMD(TalonSRXMotors talonSRXMotors, Shooter shooter) {
    this.talonSRXMotors = talonSRXMotors;
    this.shooter = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(talonSRXMotors,shooter);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    talonSRXMotors.setShooterSpeed(0);  
    talonSRXMotors.setSpeedFeeder(0);
    shooter.shooterPistonUp();
    System.out.println("Shooter Default");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(talonSRXMotors.getFeederBeamBreak()){
     SmartDashboard.putBoolean("Note Got", true);
    }
    else{
      SmartDashboard.putBoolean("Note Got", false);
    }
  }

}