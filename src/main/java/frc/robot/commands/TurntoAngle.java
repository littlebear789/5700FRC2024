// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;


public class TurntoAngle extends Command {

	private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;

  	private final double kP = 5;
	private final double kI = 0.5;
	private final double kD = 0;
	Rotation2d angle = Rotation2d.fromDegrees(0);
	Swerve s_Swerve = Swerve.getInstance();
	private double kMaxSpeed = 360;
	private double kMaxAccel = 720;
	private TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(kMaxSpeed, kMaxAccel);
	private ProfiledPIDController profiledPID = new ProfiledPIDController(kP, kI, kD, constraints);

  /* Creates a new Turn2Angle. */

  public TurntoAngle(Swerve s_Swerve,  DoubleSupplier translationSup, DoubleSupplier strafeSup, Rotation2d angle) {
		this.angle = angle;
		this.s_Swerve = s_Swerve;
		this.translationSup = translationSup;
        this.strafeSup = strafeSup;
		addRequirements(s_Swerve);
		profiledPID.enableContinuousInput(0, 360);

  }

  @Override
	public void initialize() {
		profiledPID.reset(s_Swerve.getPose().getRotation().getDegrees());
		profiledPID.setGoal(angle.getDegrees());
	}


	@Override
	public void execute() {

		double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);

		double omegaDegPerSec = profiledPID.calculate(s_Swerve.getPose().getRotation().getDegrees());
		s_Swerve.drive(new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), Units.degreesToRadians(omegaDegPerSec),true, true);
	}

}