/**
 * 
 */

package Analysis.Tool.Image;

import java.util.ArrayList;
import org.opencv.core.Mat;

/**
 * @author danjinghuang
 *
 */
public class ImageProcessing {
	private ArrayList <Mat> images = new ArrayList <Mat>();
	
	/**
	 * 
	 */
	public ImageProcessing() {
		// TODO Auto-generated constructor stub
	}

	public ImageProcessing(Mat image) {
		this.images.add(image);
	}
	
	public ImageProcessing(ArrayList <Mat> imgs){
		this.images.addAll(imgs);
	}
	
	public void Add(Mat image){
		this.images.add(image);
	}

	public void Add(ArrayList <Mat> imgs){
		this.images.addAll(imgs);
	}

	public void EmptyImages()
	{
		this.images.clear();
	}

}
