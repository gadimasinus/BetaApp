package betaApp.utility;

import java.util.List;

public class MathUtils {

	public static Double getAverage(List<Double> arr) {
		
		if(arr.size()==0) {
			return 0.00;
		}
		double total = 0;
        for(int i=0; i<arr.size(); i++){
        	total = total + arr.get(i);
        }
        return total / arr.size();
	}
}
