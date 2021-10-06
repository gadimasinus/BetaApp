package betaApp.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import betaApp.dto.BetaRequest;
import betaApp.service.CalculationService;

@RestController
public class BetaApplicationController {

	@RequestMapping(path="beta", method=RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE, produces  =  MediaType.APPLICATION_JSON_VALUE  )
	public @ResponseBody List<Double> calcBeta(@RequestBody BetaRequest request) {
		System.out.println(request);
		
		try {
			CalculationService cs = new CalculationService();
			return cs.calcBeta(request);
		} catch (Exception e) {
			System.out.println(e);
	    //    return ResponseEntity.status(ResponseEntity.Status.INTERNAL_SERVER_ERROR).entity("Internal server error has occured.Please check wheather you have passed valid data or not").build();

			return new ArrayList<Double>();
		}
	}
	
}
