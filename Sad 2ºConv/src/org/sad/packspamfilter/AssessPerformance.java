package org.sad.packspamfilter;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class AssessPerformance {
	
	public AssessPerformance(Instances pData, Classifier pEstimador) {
		try  {
			Evaluation evaluator = new Evaluation(pData);
			evaluator.crossValidateModel(pEstimador, pData, 10, new Random(3)); // Random(1): the seed=1 means "no shuffle" :-!
			double acc=evaluator.pctCorrect();
			double inc=evaluator.pctIncorrect();
			double kappa=evaluator.kappa();
			double mae=evaluator.meanAbsoluteError();    
			double rmse=evaluator.rootMeanSquaredError();
			double rae=evaluator.relativeAbsoluteError();
			double rrse=evaluator.rootRelativeSquaredError();
			double confMatrix[][]= evaluator.confusionMatrix();
		
			System.out.println("Correctly Classified Instances  " + acc);
			System.out.println("Incorrectly Classified Instances  " + inc);
			System.out.println("Kappa statistic  " + kappa);
			System.out.println("Mean absolute error  " + mae);
			System.out.println("Root mean squared error  " + rmse);
			System.out.println("Relative absolute error  " + rae);
			System.out.println("Root relative squared error  " + rrse);	
		}
		catch(Exception e1) {
			System.out.println("No se ha podido realizar el assessPerformance");
		}
	}	
	

}
