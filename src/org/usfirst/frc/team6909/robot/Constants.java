package org.usfirst.frc.team6909.robot;

public class Constants {
	// ---------- Ports ----------
	public static final int DriverPort = 0;
	public static final int OperatorPort = 1;
	
	public static final int PWMLeftFrontDrive = 0;
	public static final int PWMLeftRearDrive = 1;
	public static final int PWMRightFrontDrive = 2;
	public static final int PWMRightRearDrive = 3;
	
	public static final int DIODriveLeftEncoderA = 8;
	public static final int DIODriveLeftEncoderB = 9;
	public static final int DIODriveRightEncoderA = 10; // ?
	public static final int DIODriveRightEncoderB = 11; // ?
	
	// ---------- Encoder Unit Conversion ----------
	public static final double DriverDistancePerPulse = 7.7 * Math.PI / 10.71;
	
	// ---------- PID Constants ----------
	public static final double driveStraightP = 0;
	public static final double driveStraightI = 0;
	public static final double driveStraightD = 0;
	public static final double driveRotationP = 0;
	public static final double driveRotationI = 0;
	public static final double driveRotationD = 0;
	
}
