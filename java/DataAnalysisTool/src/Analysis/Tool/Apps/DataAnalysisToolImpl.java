/**
 * 
 */
package Analysis.Tool.Apps;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleStripArray;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameListener;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Dan
 *
 */
public class DataAnalysisToolImpl extends JFrame
							  implements DataAnalysisToolInf, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3547198646917995198L;

	// windows defination
	String windows[] = {"Stream","Depth","Curve","Contour"};

	JPanel contentPane;
    JDesktopPane desktop;
    int dw, dh;
    /**
	 * 
	 */
	public DataAnalysisToolImpl() {
		// TODO Auto-generated constructor stub
    	super("DataAnalysisTool");
        initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
        JToolBar toolBar = createToolBar();
        createMenuBar();

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(1,1,1,1));
        contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(contentPane);

        desktop = new JDesktopPane(); 	//a specialized layered pane
        contentPane.add(desktop,BorderLayout.CENTER);
        contentPane.add(toolBar, BorderLayout.NORTH);

        //Display the window.
        this.setVisible(true);

        dw = desktop.getSize().width/2;
        dh = desktop.getSize().height/2;
        for (String name: windows)
        {
        	createFrame(dw,dh,name);
        }

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        /* Set up the lone menu.
        * define file operation
        */
        JMenu fmenu = new JMenu("Files");
        fmenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fmenu);
        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        fmenu.add(menuItem);

        //Set up the first menu item.
        JMenuItem OpenItem = new JMenuItem("open");
        OpenItem.setMnemonic(KeyEvent.VK_O);
        OpenItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.ALT_MASK));
        OpenItem.setActionCommand("open");
        OpenItem.addActionListener(this);
        fmenu.add(OpenItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        fmenu.add(menuItem);
        
        /*
         * Tool menu
         */
        JMenu tmenu = new JMenu("Tools");
        tmenu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(tmenu);

        /*
         * Device menu
         */
        JMenu dmenu = new JMenu("Devices");
        dmenu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(dmenu);
        
        // select device source
        menuItem = new JMenuItem("Camera");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Camera");
        menuItem.addActionListener(new ActionListener() {
        	@Override 
            public void actionPerformed(ActionEvent event) {
        		String actionCommand = null;
        		actionCommand = event.getActionCommand();
        		if(actionCommand == "Camera")
        		{
        		}
            }
        });
        dmenu.add(menuItem);

        /*
         * Windows menu
         */
        JMenu wmenu = new JMenu("Windows");
        wmenu.setMnemonic(KeyEvent.VK_W);
        menuBar.add(wmenu);

        //Set up the first menu item.
        menuItem = new JMenuItem("Vertical");
        menuItem.setMnemonic(KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Vertical");
        menuItem.addActionListener(new ActionListener(){
        	@Override 
            public void actionPerformed(ActionEvent event) {
        		String actionCommand = null;
        		actionCommand = event.getActionCommand();
        		if(actionCommand == "Vertical")
        		{
        			
        		}
            }
        });
        wmenu.add(menuItem);
        
        setJMenuBar(menuBar);        
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        ImageIcon icon = resizeIcon("resource\\programmer_exits.png", 16,16);
        JButton exitButton = new JButton(icon);
        exitButton.setSize(16, 16);
        toolbar.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        add(toolbar, BorderLayout.NORTH);
        return toolbar;
    }

    //Create a new internal frame.
	private void createFrame(int w, int h, String...names) {
		FrameWindow frame;
        if(names.length == 0)
        	frame = new FrameWindow("Test", w, h);
        else
        	frame = new FrameWindow(names[0], w, h);
        frame.setVisible(true); //necessary as of 1.3
        this.desktop.add(frame);
        this.desktop.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        FrameList.add(frame);
	}

	private ImageIcon resizeIcon(String name, int w, int h)
    {
    	ImageIcon icon = new ImageIcon(name);
    	Image img = icon.getImage();
        Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg); 
        return newIcon;
    }

    /**
	 * @param args
	 */
/*	public static void main(String[] args) {
		// app lib initialization
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// TODO Auto-generated method stub
		new DataAnalysisToolImpl();
		//Frame frame = new MainFrame(new DataAnalysisTool(), 256, 256);
	}
*/
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = null;
		actionCommand = e.getActionCommand();
		switch(actionCommand){
			case "new":
				break;
			case "open":
				File _imagefile;
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./samples"));
				chooser.addChoosableFileFilter(new OpenFileFilter("jpeg","Photo in JPEG format") );
				chooser.addChoosableFileFilter(new OpenFileFilter("jpg","Photo in JPEG format") );
				chooser.addChoosableFileFilter(new OpenFileFilter("png","PNG image") );
				chooser.addChoosableFileFilter(new OpenFileFilter("svg","Scalable Vector Graphic") );
				int returnVal = chooser.showSaveDialog(desktop.getSelectedFrame());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					_imagefile = chooser.getSelectedFile();
				     Mat mat = Highgui.imread(_imagefile.getAbsolutePath());
				     ImageList.add(mat);

				     try{
				    	 float _scaledHeight, _scaledWidth, _imageHeight, _imageWidth;
				    	 float _scaledRate;

				    	 BufferedImage img = ImageIO.read(_imagefile);
				    	 //ImageIcon icon=new ImageIcon(img);
				    	 JInternalFrame frame = this.getFrame("Stream");
				    	 _scaledHeight = frame.size().height;
				    	 _scaledWidth = frame.size().width;
				    	 _imageHeight = img.getHeight();
				    	 _imageWidth = img.getWidth();

				    	 // get scaled rate.
				    	 if((_imageHeight / _scaledHeight) > (_imageWidth / _scaledWidth))
				    		 _scaledRate = _scaledHeight / _imageHeight;
				    	 else
				    		 _scaledRate =  _scaledWidth / _imageWidth;

				    	 // calculated scale size.
				    	 _scaledWidth = (int)(_imageWidth * _scaledRate);
				    	 _scaledHeight = (int)(_imageHeight * _scaledRate);

				    	 ImageIcon icon = new ImageIcon(resizeImage(img,(int)_scaledWidth,(int)_scaledHeight));

				    	 frame.getContentPane().removeAll();
				    	 JLabel lbl=new JLabel();
				    	 lbl.setIcon(icon);
				    	 frame.add(lbl);
				    	 frame.revalidate();
				    	 frame.repaint();
				    	 
				    	 frame = getFrame("Contour");
				       	 if(frame == null)
				       	 	return;
				       	 Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
				 	     canvas3D.paint(img.getGraphics());
				       	 frame.add("Center", canvas3D);
				         
				       	 BranchGroup scene = createSceneGraph();

				         // SimpleUniverse is a Convenience Utility class
				         SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

				         // This will move the ViewPlatform back a bit so the
				         // objects in the scene can be viewed.
				         simpleU.getViewingPlatform().setNominalViewingTransform();
				         simpleU.addBranchGraph(scene);

				     }catch(IOException ioe)
				     {
				     }
				}
				break;
			case "quit":
                System.exit(0);
				break;
			default:
				break;
		}
	}
	
	public BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type=0;
        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height,type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
	}
	
	  /////////////////////////////////////////////////
	  //
	  // create Twist visual object
	  //
	  public class Twist extends Shape3D {
	    ////////////////////////////////////////////
	    //
	    // create twist subgraph
	    //
	    public Twist() {
	      this.setGeometry(createGeometry());
	      this.setAppearance(createAppearance());
	    } // end of twist constructor

	    Geometry createGeometry() {

	      TriangleStripArray twistStrip;
	      Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);

	      // create triangle strip for twist
	      int N = 80;
	      int stripCounts[] = { N };
	      twistStrip = new TriangleStripArray(
	          N,
	          TriangleStripArray.COORDINATES | TriangleStripArray.COLOR_3,
	          stripCounts);

	      double a;
	      int v;
	      for (v = 0, a = 0.0; v < N; v += 2, a = v * 2.0 * Math.PI / (N - 2)) {
	        twistStrip.setCoordinate(v, new Point3d(0.7 * Math.sin(a) + 0.2
	            * Math.cos(a), 0.3 * Math.sin(a), 0.7 * Math.cos(a)
	            + 0.2 * Math.cos(a)));
	        twistStrip.setCoordinate(v + 1, new Point3d(0.7 * Math.sin(a)
	            - 0.2 * Math.cos(a), -0.3 * Math.sin(a), 0.7
	            * Math.cos(a) - 0.2 * Math.cos(a)));
	        twistStrip.setColor(v, blue);
	        twistStrip.setColor(v + 1, blue);
	      }

	      return twistStrip;

	    }

	    // create Appearance for Twist Strip
	    //
	    // this method creates the default Appearance for the
	    // twist strip. The commented line of code containting
	    // the setCullFace will fix the problem of half of the
	    // Twisted Strip disappearing.

	    Appearance createAppearance() {

	      Appearance twistAppear = new Appearance();
	      PolygonAttributes polyAttrib = new PolygonAttributes();
	      // polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
	      twistAppear.setPolygonAttributes(polyAttrib);

	      return twistAppear;
	    }

	  } // end of class Twist

	  /////////////////////////////////////////////////
	  //
	  // create scene graph branch group
	  //
	  public BranchGroup createSceneGraph() {

	    BranchGroup contentRoot = new BranchGroup();

	    // Create the transform group node and initialize it to the
	    // identity. Add it to the root of the subgraph.
	    TransformGroup objSpin = new TransformGroup();
	    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    contentRoot.addChild(objSpin);

	    Shape3D twist = new Twist();
	    objSpin.addChild(twist);

	    // Duplicate the twist strip geometry and set the
	    // appearance of the new Shape3D object to line mode
	    // without culling.
	    // Add the POLYGON_FILLED and POLYGON_LINE strips
	    // in the scene graph at the same point.
	    // This will show the triangles of the original Mobius strip that
	    // are clipped. The PolygonOffset is set to prevent stitching.
	    PolygonAttributes polyAttrib = new PolygonAttributes();
	    polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
	    polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
	    polyAttrib.setPolygonOffset(0.001f);
	    Appearance polyAppear = new Appearance();
	    polyAppear.setPolygonAttributes(polyAttrib);
	    objSpin.addChild(new Shape3D(twist.getGeometry(), polyAppear));

	    Alpha rotationAlpha = new Alpha(-1, 16000);

	    RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
	        objSpin);

	    // a bounding sphere specifies a region a behavior is active
	    // create a sphere centered at the origin with radius of 1
	    BoundingSphere bounds = new BoundingSphere();
	    rotator.setSchedulingBounds(bounds);
	    objSpin.addChild(rotator);

	    // make background white
	    Background background = new Background(1.0f, 1.0f, 1.0f);
	    background.setApplicationBounds(bounds);
	    contentRoot.addChild(background);

	    // Let Java 3D perform optimizations on this scene graph.
	    contentRoot.compile();

	    return contentRoot;
	  } // end of CreateSceneGraph method of TwistStripApp
	  
	  // Create a simple scene and attach it to the virtual universe
	  public void TwistStripApp() {
	    setLayout(new BorderLayout());
	    Canvas3D canvas3D = new Canvas3D(null);
	    add("Center", canvas3D);

	    BranchGroup scene = createSceneGraph();

	    // SimpleUniverse is a Convenience Utility class
	    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

	    // This will move the ViewPlatform back a bit so the
	    // objects in the scene can be viewed.
	    simpleU.getViewingPlatform().setNominalViewingTransform();

	    simpleU.addBranchGraph(scene);
	  } // end of TwistStripApp constructor
	  
	@SuppressWarnings("static-access")
	public JInternalFrame getFrame(String title)
	  {
		  for (JInternalFrame frame : this.FrameList){
			  if(frame.getTitle().toString() == title)
				  return frame;
		  }
		  return null;
	  }
}

