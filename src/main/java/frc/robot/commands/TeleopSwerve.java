package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.States;


public class TeleopSwerve extends Command {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private BooleanSupplier dampenSup;


    private final double kP = 2.1;
	private final double kI = 0.0;
	private final double kD = 0.05;
	Rotation2d angle = Rotation2d.fromDegrees(0);
	private double kMaxSpeed = 360;
	private double kMaxAccel = 720;
	private TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(kMaxSpeed, kMaxAccel);
	private ProfiledPIDController profiledPID = new ProfiledPIDController(kP, kI, kD, constraints);
    private double omegaDegPerSec;

    public TeleopSwerve(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, BooleanSupplier dampen) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        profiledPID.enableContinuousInput(0, 360);
        
        this.dampenSup = dampen;
    }

	@Override
	public void initialize() {
		profiledPID.reset(s_Swerve.getPose().getRotation().getDegrees());
	}


    @Override
    public void execute() {
      /* Get Values, Deadband*/
      double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband)* (dampenSup.getAsBoolean() ? 0.2 : 1);
      double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband)* (dampenSup.getAsBoolean() ? 0.2 : 1);
      double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband)* (dampenSup.getAsBoolean() ? 0.2 : 1);

        switch(States.driveState){
            case forwardHold:
                //heading lock - forward
                profiledPID.setGoal(0);
                omegaDegPerSec = profiledPID.calculate(s_Swerve.getPose().getRotation().getDegrees());
                rotationVal = Units.degreesToRadians(omegaDegPerSec);
                //System.out.println("foward LOCK");
                break;
            case backwardHold:
                //heading lock - backward
                profiledPID.setGoal(179);
                omegaDegPerSec = profiledPID.calculate(s_Swerve.getPose().getRotation().getDegrees());
                rotationVal = Units.degreesToRadians(omegaDegPerSec);
                //System.out.println("back LOCK");
                break;
            case leftHold:
                //heading lock - left
                profiledPID.setGoal(45);
                omegaDegPerSec = profiledPID.calculate(s_Swerve.getPose().getRotation().getDegrees());
                rotationVal = Units.degreesToRadians(omegaDegPerSec);
                //System.out.println("left LOCK");
                break;
            case rightHold:
                //heading lock - right
                profiledPID.setGoal(315);
                omegaDegPerSec = profiledPID.calculate(s_Swerve.getPose().getRotation().getDegrees());
                rotationVal = Units.degreesToRadians(omegaDegPerSec);
                //System.out.println("right LOCK");
                break;
            case standard:
                //normal
                rotationVal = rotationVal * Constants.Swerve.maxAngularVelocity;
                break;
        }
        
      /* Drive */
      s_Swerve.drive(
        new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
        rotationVal, 
        !robotCentricSup.getAsBoolean(), 
         true
      );
    }

}