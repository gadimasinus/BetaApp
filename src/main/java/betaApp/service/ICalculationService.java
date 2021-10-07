package betaApp.service;

import java.util.List;

import betaApp.dto.BetaRequest;

public interface ICalculationService {
	List<Double> calcBeta(BetaRequest request);
}
