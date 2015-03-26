package br.com.megasena.bean;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.megasena.sb.SBMegaSena;

@ManagedBean
public class BTOEstatisticas {

	protected PieChartModel pieModel;

	public BTOEstatisticas() {
		createPieModel();
		createCategoryModel();
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	private void createPieModel() {
		pieModel = new PieChartModel();

		SBMegaSena sbMegaSena = new SBMegaSena();
		int[][] dezenas = sbMegaSena.contaNumeros();

		for (int i = 0; i < dezenas.length; i++) {
			pieModel.set(Integer.toString(dezenas[i][0]), dezenas[i][1]);
		}

	}

	private CartesianChartModel categoryModel;

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	private void createCategoryModel() {
		categoryModel = new CartesianChartModel();
		ChartSeries dezenasChart = new ChartSeries();
		dezenasChart.setLabel("Dezenas");

		
		SBMegaSena sbMegaSena = new SBMegaSena();
		int[][] dezenas = sbMegaSena.contaNumeros();

		for (int i = 0; i < dezenas.length; i++) {
			dezenasChart.set(Integer.toString(dezenas[i][0]), dezenas[i][1]);
		}

		categoryModel.addSeries(dezenasChart);
	}

}
