WinchCommons/hc
===========
Host Controller--A Java program for a PC that assists the winch driver in the operation.  It is used to 
1.  Select parameters for the launch, e.g. the glider mass, and tension-factor.
2.  Compute the selected parameters into values needed by the master controller during the launch.
3.  Display the progress of the launch in various forms, including graphing of key parameters.
4.  Log the CAN messages for playback and analysis.

This routine deals with CAN messages in an ascii format that can be sent/received by a TCP connection, and/or a serial port-to-CAN bus gateway unit to the hardware CAN bus.

