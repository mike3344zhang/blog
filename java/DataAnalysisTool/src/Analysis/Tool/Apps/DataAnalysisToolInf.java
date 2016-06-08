package Analysis.Tool.Apps;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JInternalFrame;

import org.opencv.core.Mat;

public interface DataAnalysisToolInf {
	
    /* ***************************************************************
     *                      Global Variables                         *
     *****************************************************************/

    /* Public constant values. */
    //-------------------------------------------------------------------------
    // Preset colors.
    public static final Color BLACK      = Color.BLACK;
    public static final Color BLUE       = Color.BLUE;
    public static final Color CYAN       = Color.CYAN;
    public static final Color DARK_GRAY  = Color.DARK_GRAY;
    public static final Color GRAY       = Color.GRAY;
    public static final Color GREEN      = Color.GREEN;
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    public static final Color MAGENTA    = Color.MAGENTA;
    public static final Color ORANGE     = Color.ORANGE;
    public static final Color PINK       = Color.PINK;
    public static final Color RED        = Color.RED;
    public static final Color WHITE      = Color.WHITE;
    public static final Color YELLOW     = Color.YELLOW;

    // Camera modes.
    public static final int ORBIT_MODE = 0;
    public static final int FPS_MODE   = 1;
    public static final int AIRPLANE_MODE = 2;
    public static final int LOOK_MODE = 3;
    public static final int FIXED_MODE = 4;
    public static final int IMMERSIVE_MODE = 5;

    //static ArrayList <Capture> CaptureList = new ArrayList<Capture> (); 
    static ArrayList <JInternalFrame> FrameList = new ArrayList<JInternalFrame> ();
	static ArrayList <Mat> ImageList = new ArrayList <Mat> ();
	public BufferedImage resizeImage(BufferedImage image, int width, int height);
}
