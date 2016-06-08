package Analysis.Tool.Apps;

import javax.swing.JInternalFrame;

/* Used by InternalFrameDemo.java. */
@SuppressWarnings("serial")
public class FrameWindow extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;
    public FrameWindow(String name, int w, int h) {
        super(name, 
              true, 	//resizable
              true, 	//closable
              true, 	//maximizable
              true);	//iconifiable
        //...Create the GUI and put it in the window...
        ++openFrameCount;
        //...Then set the window size or call pack...
        setSize(w,h);
        //Set the window's location.
        setLocation(w*((openFrameCount-1)%2), h*((openFrameCount-1)/2));
    }
}
