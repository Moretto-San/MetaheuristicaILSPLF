import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Main {
	public static String nomeIntancia;
	public static Integer numFacilidades;
	public static Integer numCidades;
	public static Integer[][] custoConexao;
	public static Integer[] custoAberturaFacilidade;
	static int linhaAtual = 0;
	static int colunaAtual = 0;
	static WritableWorkbook workbook1;
	static WritableSheet s1;

	public static void main(String[] args) throws RowsExceededException,
			WriteException, IOException {
		String filename1 = "Resultados.xls";
		WorkbookSettings ws1 = new WorkbookSettings();
		ws1.setLocale(new Locale("en", "EN"));
		workbook1 = Workbook.createWorkbook(new File(filename1), ws1);
		s1 = workbook1.createSheet("Resultados", 0);
		Label label = new Label(colunaAtual, linhaAtual, "Resultados ILS");
		s1.addCell(label);
		linhaAtual++;
		label = new Label(colunaAtual, linhaAtual, "Instancia");
		s1.addCell(label);
		colunaAtual++;
		label = new Label(colunaAtual, linhaAtual, "Custo Otimo");
		s1.addCell(label);
		colunaAtual++;
		label = new Label(colunaAtual, linhaAtual, "Custo ILS");
		s1.addCell(label);
		linhaAtual++;
		colunaAtual=0;
		for (int i = 1; i < 10; i++) {
			String nomeArq = "Instancias/B/B1." + i;
			ils(nomeArq);
		}
		for (int i = 1; i < 10; i++) {
			String nomeArq = "Instancias/C/C1." + i;
			ils(nomeArq);
		}
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 11; j++) {
				String nomeArq = "Instancias/Dq/" + i + "/D" + i + "." + j;
				ils(nomeArq);
			}
		}
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 11; j++) {
				String nomeArq = "Instancias/Eq/" + i + "/E" + i + "." + j;
				ils(nomeArq);
			}
		}
		workbook1.write();
		workbook1.close();
		System.err.println("Fim");
	}

	public static void ils(String nomeArq) throws RowsExceededException, WriteException {
		int CustoMedio = 0;
		Label label = new Label(colunaAtual, linhaAtual, "Resultados ILS");
		for (int seed = 1; seed < 10; seed++) {
			leArqInstancia(nomeArq);
			Random random = new Random();
			random.setSeed(seed);
			Solucao corrente = new Solucao();
			corrente.ConstruirSolucao(custoConexao, custoAberturaFacilidade,
					random);
			corrente = buscaLocalRemoveFacilidade(corrente, random,
					custoConexao);
			int iter = 0;
			while (iter < 100) {
				Solucao temp = corrente.copiarSolucao();
				perturbacao(temp, random);
				temp = buscaLocalRemoveFacilidade(temp, random, custoConexao);
				if (temp.getCustoTotal() < corrente.getCustoTotal()) {
					corrente = temp.copiarSolucao();
				}
				iter++;
			}
			// System.err.println("Semente: " + seed);
			// imprimiSolucao(corrente);
			corrente.verificaNullclientesXFacilidade();
			CustoMedio += corrente.getCustoTotal();
		}
		CustoMedio = CustoMedio / 9;
		System.err.println("Instancia: " + nomeIntancia);
		System.err.println("Numero de Facilidades: " + numFacilidades
				+ " Numero de Cidades: " + numCidades);
		System.err.println("Custo Medio: " + CustoMedio);
		String nomeArqOpt = nomeArq+".opt";
		String custoOtimo = "";
		try {
			RandomAccessFile instanciaArq = new RandomAccessFile(new File(
					nomeArqOpt), "r");
			String leitura = instanciaArq.readLine();
			String[] split = leitura.split(" ");
			custoOtimo =split[split.length - 1];
			System.err.println("Custo Otimo: " + custoOtimo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label = new Label(colunaAtual, linhaAtual, nomeArq);
		s1.addCell(label);
		colunaAtual++;
		label = new Label(colunaAtual, linhaAtual, custoOtimo);
		s1.addCell(label);
		colunaAtual++;
		label = new Label(colunaAtual, linhaAtual, CustoMedio+"");
		s1.addCell(label);
		colunaAtual=0;
		linhaAtual++;
	}

	public static void leArqInstancia(String nomeArq) {
		try {
			RandomAccessFile instanciaArq = new RandomAccessFile(new File(
					nomeArq), "r");
			String leitura = instanciaArq.readLine();
			nomeIntancia = nomeArq;
			leitura = instanciaArq.readLine();
			numFacilidades = Integer.parseInt(leitura.split(" ")[0]);
			numCidades = Integer.parseInt(leitura.split(" ")[1]);
			leitura = instanciaArq.readLine();
			custoConexao = new Integer[numFacilidades][numCidades];
			custoAberturaFacilidade = new Integer[numFacilidades];
			Integer linha = 0;
			while (leitura != null) {
				String[] split = leitura.split(" ");
				custoAberturaFacilidade[linha] = Integer.parseInt(split[1]);
				for (Integer j = 0; j < numCidades; j++) {
					custoConexao[linha][j] = Integer.parseInt(split[j + 2]);
				}
				linha++;
				leitura = instanciaArq.readLine();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void imprimiSolucao(Solucao solucao) {
		// solucao.imprimiSolucao();
		System.err.println("Instancia: " + nomeIntancia);
		System.err.println("Numero de Facilidades: " + numFacilidades
				+ " Numero de Cidades: " + numCidades);
		System.err.println("Facilidades Usadas: "
				+ solucao.getFacilidadesUsadas().size());
		System.err.println("Custo total: " + solucao.getCustoTotal());
	}

	public static Solucao buscaLocalRemoveFacilidade(Solucao corrente,
			Random random, Integer[][] custoConexao) {
		Solucao temp = corrente.copiarSolucao();
		boolean melhorou = true;
		while (melhorou) {
			ArrayList<String> facilidadesUsadas = corrente
					.getFacilidadesUsadas();
			if (facilidadesUsadas.size() != 1) {
				int aleatorio = random.nextInt(facilidadesUsadas.size() - 1);
				temp = corrente.copiarSolucao();
				temp.removeFacilidadesUsada(facilidadesUsadas.get(aleatorio));
				temp.setCidadesSemFacilidade(custoConexao,
						custoAberturaFacilidade);
			}
			if (temp.getCustoTotal() < corrente.getCustoTotal()) {
				corrente = temp.copiarSolucao();

			} else {
				melhorou = false;
			}
		}
		return corrente;
	}

	public static Solucao perturbacao(Solucao corrente, Random random) {
		Solucao temp = corrente.copiarSolucao();
		ArrayList<String> facilidadesUsadas = corrente.getFacilidadesUsadas();
		int quantidade = (int) (facilidadesUsadas.size() * (0.3));
		for (int i = 0; i < quantidade; i++) {
			temp.removeFacilidadesUsada(facilidadesUsadas.get(i));
		}
		temp.reconstroiSolucao(quantidade, numFacilidades, random);
		return temp;
	}

}
