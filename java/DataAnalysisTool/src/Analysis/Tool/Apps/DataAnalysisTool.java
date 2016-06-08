/**
 * 
 */
package Analysis.Tool.Apps;
import org.opencv.core.Core;

/**
 * @author danjinghuang
 *
 */
public final class DataAnalysisTool {
	/**
	 * 
	 */
	public DataAnalysisTool() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// app lib initialization
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// TODO Auto-generated method stub
		new DataAnalysisToolImpl();
	}
}
