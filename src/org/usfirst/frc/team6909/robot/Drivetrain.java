package org.usfirst.frc.team6909.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends DifferentialDrive {
	private SpeedController m_leftDrive, m_rightDrive;
	private Encoder e_leftDriveEncoder, e_rightDriveEncoder;
	private ADXRS450_Gyro g_gyro;
	
	private PIDController straightPID, rotationPID;
	private double _pidSpeed, _pidRotation;
	
	public Drivetrain(SpeedController m_leftDrive, SpeedController m_rightDrive, Encoder e_leftDriveEncoder,
			Encoder e_rightDriveEncoder, ADXRS450_Gyro g_gyro) {
		super(m_leftDrive, m_rightDrive);
		
		this.m_leftDrive = m_leftDrive;
		this.m_rightDrive = m_rightDrive;
		this.e_leftDriveEncoder = e_leftDriveEncoder;
		this.e_rightDriveEncoder = e_rightDriveEncoder;
		this.g_gyro = g_gyro;
		
		straightPID = new PIDController(Constants.driveStraightP, Constants.driveStraightI, Constants.driveStraightD,
				new StraightPIDSource(), new StraightPIDOutput());
		rotationPID = new PIDController(Constants.driveRotationP, Constants.driveRotationI, Constants.driveRotationD,
				new RotationPIDSource(), new RotationPIDOutput());
	}
	
	/******************************
	 * センサー
	 * @param distance
	 ******************************/
	public double getDistance() {
		return (e_leftDriveEncoder.getDistance() + e_rightDriveEncoder.getDistance()) / 2;
	}
	
	public double getRotation() {
		return g_gyro.getAngle();
	}

	/******************************
	 * PID
	 * @param distance
	 ******************************/
	public void setRelativeStraightSetpoint(double distance) {
		double targetDistance = getDistance() + distance;
		double targetRotation = getRotation();
		straightPID.setSetpoint(targetDistance);
		rotationPID.setSetpoint(targetRotation);
	}
	
	public void enablePID() {
		if (!straightPID.isEnabled()) {
			straightPID.enable();
		}
		if (!rotationPID.isEnabled()) {
			rotationPID.enable();
		}
	}
	
	public void disablePID() {
		if (straightPID.isEnabled()) {
			straightPID.disable();
		}
		if (rotationPID.isEnabled()) {
			rotationPID.disable();
		}
	}
	
	public boolean isPIDEnabled() {
		return straightPID.isEnabled() || rotationPID.isEnabled();
	}
	
	/******************************
	 * PID用のクラス
	 * @param distance
	 ******************************/
	// ---------- Straight PID ----------
	private class StraightPIDSource implements PIDSource {
		@Override
		public double pidGet() {
			return getDistance();
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}

		@Override
		public PIDSourceType getPIDSourceType() {
			return null;
		}
	}
	private class StraightPIDOutput implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			// この関数はStraight PID用のスレッドで走る
			// 計算結果の値をメモリーのおいておいて、モーターの信号を送る
			_pidSpeed = output;
			arcadeDrive(_pidSpeed, _pidRotation);
		}
	}

	// ---------- Rotate PID ----------
	private class RotationPIDSource implements PIDSource {
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {}

		@Override
		public PIDSourceType getPIDSourceType() {
			return null;
		}

		@Override
		public double pidGet() {
			return getRotation();
		}
	}
	private class RotationPIDOutput implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			// この関数はRotation PID用のスレッドで走る
			// 計算結果の値をメモリーのおいておいて、モーターの信号を送る
			_pidRotation = output;
			arcadeDrive(_pidSpeed, _pidRotation);
		}
	}
}
