package betaApp.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import betaApp.dto.BetaRequest;
import betaApp.service.CalculationService;

@RestController
public class BetaApplicationController {

	@RequestMapping(path="beta", method=RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE, produces  =  MediaType.APPLICATION_JSON_VALUE  )
	public ResponseEntity<?> calcBeta(@RequestBody BetaRequest request) {
		System.out.println(request);
		
		CalculationService cs = new CalculationService();
		cs.calcBeta(request);
		return ResponseEntity.ok().build();
	}
	
}
